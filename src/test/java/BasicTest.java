import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.Realiser;

public class BasicTest {
    public static void main(String[] args) {
        Lexicon lexicon = new simplenlg.lexicon.dutch.XMLLexicon("src/main/java/simplenlg/lexicon/dutch/dutch-lexicon-3k.xml");
        NLGFactory factory = new NLGFactory(lexicon);
        Realiser realiser = new Realiser();
        SPhraseSpec clause = factory.createClause();

        clause.setSubject("Rayo");
        clause.setVerb("zijn");

        System.out.println(realiser.realiseSentence(clause));
    }
}