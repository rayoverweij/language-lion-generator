package simplenlg.semantics;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import simplenlg.framework.SemElement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.*;

public class Semanticon {

    // Semanticon storage
    private Set<SemElement> sems;
    private Map<String, SemElement> indexById;
    private Map<String, SemElement> indexByName;
    private Map<String, List<SemElement>> indexByCategory;

    // Constructors
    public Semanticon(String filename) {
        File file = new File(filename);
        createSemanticon(file.toURI());
    }

    public Semanticon(File file) {
        createSemanticon(file.toURI());
    }

    public Semanticon() {
        File file = new File("src/main/java/simplenlg/semantics/semanticon.xml");
        createSemanticon(file.toURI());
    }

    // Actually load the semanticon from the URI
    private void createSemanticon(URI semanticonURI) {
        sems = new HashSet<SemElement>();
        indexById = new HashMap<String, SemElement>();
        indexByName = new HashMap<String, SemElement>();
        indexByCategory = new HashMap<String, List<SemElement>>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(semanticonURI.toString());

            if(doc != null) {
                Element lexRoot = doc.getDocumentElement();
                NodeList semNodes = lexRoot.getChildNodes();
                for(int i = 0; i < semNodes.getLength(); i++) {
                    Node semNode = semNodes.item(i);
                    if(semNode.getNodeType() == Node.ELEMENT_NODE) {
                        SemElement sem = convertNodeToSem(semNode);
                        if(sem != null) {
                            sems.add(sem);
                            IndexSem(sem);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    // Create a ll-gen SemElement from a Sem node in a lexicon XML file
    protected SemElement convertNodeToSem(Node semNode) {
        if(!semNode.getNodeName().equalsIgnoreCase("sem"))
            return null;

        SemElement sem = new SemElement(this);

        NodeList nodes = semNode.getChildNodes();
        for(int i = 0; i < nodes.getLength(); i++) {
            Node featureNode = nodes.item(i);
            if(featureNode.getNodeType() == Node.ELEMENT_NODE) {
                String name = featureNode.getNodeName().trim();
                String value = featureNode.getTextContent();

                if(value != null) {
                    value = value.trim();
                }

                if(name == null) {
                    System.out.println("Error in XML semanticon node for " + sem.toString());
                    break;
                }

                if(name.equalsIgnoreCase("id")) {
                    sem.setId(value);
                } else if(name.equalsIgnoreCase("name")) {
                    sem.setName(value);
                } else if(name.equalsIgnoreCase("english")) {
                    List<String> englishList = Arrays.asList(value.split(","));
                    sem.setEnglish(englishList);
                } else if(name.equalsIgnoreCase("dutch")) {
                    List<String> dutchList = Arrays.asList(value.split(","));
                    sem.setDutch(dutchList);
                } else if(name.equalsIgnoreCase("categories")) {
                    List<String> categoryList = new ArrayList<String>();
                    NodeList categoryNodes = featureNode.getChildNodes();
                    for(int j = 0; j < categoryNodes.getLength(); j++) {
                        Node categoryNode = categoryNodes.item(j);
                        if(categoryNode.getNodeType() == Node.ELEMENT_NODE) {
                            String categoryName = categoryNode.getNodeName().trim();
                            categoryList.add(categoryName);
                        }
                    }
                    sem.setCategories(categoryList);
                }
            }
        }

        return sem;
    }

    // Add sem to internal indices
    private void IndexSem(SemElement sem) {
        // First index by id, which should be unique
        String id = sem.getId();
        if(id != null) {
            if(indexById.containsKey(id))
                System.out.println("Semanticon error: ID " + id + " occurs more than once");

            indexById.put(id, sem);
        }

        // Then index by name, which should be unique too
        String name = sem.getName();
        if(name != null) {
            if(indexByName.containsKey(name))
                System.out.println("Semanticon error: name " + name + " occurs more than once");

            indexByName.put(name, sem);
        }

        // Finally index by category
        if(sem.getCategories() != null) {
            List<String> categories = sem.getCategories();
            for (String category : categories) {
                if(indexByCategory.containsKey(category)) {
                    List<SemElement> semList = indexByCategory.get(category);
                    semList.add(sem);
                    indexByCategory.put(category, semList);
                } else {
                    List<SemElement> semList = new ArrayList<SemElement>();
                    semList.add(sem);
                    indexByCategory.put(category, semList);
                }
            }
        }
    }


    /////////// Main methods to retrieve data from the semanticon
    public SemElement getSemById(String id) {
        return indexById.get(id);
    }

    public SemElement getSem(String name) {
        return indexByName.get(name);
    }

    public List<SemElement> getSemsInCategory(String category) {
        return indexByCategory.getOrDefault(category, null);
    }
}
