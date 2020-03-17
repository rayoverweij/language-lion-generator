package simplenlg.framework;

import simplenlg.semantics.Semanticon;

import java.util.ArrayList;
import java.util.List;

public class SemElement extends NLGElement {

    // Sem values
    String id;
    String name;
    List<String> english;
    List<String> dutch;
    List<String> categories;

    // Semanticon the element comes from
    private final Semanticon semanticon;

    // Constructors
    public SemElement(Semanticon semanticon) {
        super();
        this.id = null;
        this.name = null;
        this.english = null;
        this.dutch = null;
        this.categories = null;
        this.semanticon = semanticon;
    }

    public SemElement(String id, String name, List<String> english, List<String> dutch, List<String> categories, Semanticon semanticon) {
        super();
        this.id = id;
        this.name = name;
        this.english = english;
        this.dutch = dutch;
        this.categories = categories;
        this.semanticon = semanticon;
    }

    // Getters and setters
    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public List<String> getEnglish() { return this.english; }
    public List<String> getDutch() { return this.dutch; }
    public List<String> getCategories() { return this.categories; }
    public Semanticon getSemanticon() { return this.semanticon; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEnglish(List<String> english) { this.english = english; }
    public void setDutch(List<String> dutch) { this.dutch = dutch; }
    public void setCategories(List<String> categories) { this.categories = categories; }


    // Helper methods
    @Override
    public String toString() {
        return "SemElement[" + this.id + " : " + this.name + "]";
    }

    public String toXML() {
        return null;
    }

    /**
     * This method returns an empty <code>List</code> as words do not have child
     * elements.
     */
    @Override
    public List<NLGElement> getChildren() { return new ArrayList<NLGElement>(); }

    /**
     * Check if this SemElement is equal to an object.
     *
     * @param o
     *            the object
     * @return <code>true</code> iff the object is a sem element with the same id and name
     *
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof SemElement) {
            SemElement se = (SemElement) o;

            return ((this.id == se.id || this.id.equals(se.id))
                    && (this.name == se.name || this.name.equals(se.name)));
        }

        return false;
    }
}
