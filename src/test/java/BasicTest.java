import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.features.dutch.DutchFeature;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.Realiser;
import simplenlg.semantics.Semanticon;

public class BasicTest {
    public static void main(String[] args) {
        // Set-up
        Lexicon dutchLexicon = new simplenlg.lexicon.dutch.XMLLexicon("src/main/java/simplenlg/lexicon/dutch/dutch-lexicon-3k.xml");
        Lexicon englishLexicon = new simplenlg.lexicon.english.XMLLexicon("src/main/java/simplenlg/lexicon/default-lexicon.xml");
        NLGFactory dutchFactory = new NLGFactory(dutchLexicon);
        NLGFactory englishFactory = new NLGFactory(englishLexicon);
        Realiser realiser = new Realiser();

        SPhraseSpec dutchClause = dutchFactory.createClause();
        SPhraseSpec englishClause = englishFactory.createClause();

        // Quick create
        dutchClause.setSubject("Rayo");
        dutchClause.setVerb("ziet");
        dutchClause.setComplement("de bal");
        dutchClause.setFeature(Feature.NEGATED, true);
        dutchClause.setFeature(DutchFeature.NEGATION_AUXILIARY, "geen");
        dutchClause.addFrontModifier("trouwens");

        // Noun phrases
        NPPhraseSpec englishSubject = englishFactory.createNounPhrase("bread");
        englishSubject.setSpecifier("the");
        englishSubject.addPreModifier("crispy");
        englishClause.setSubject(englishSubject);

        // Verbs
        englishClause.setVerb("be");
        englishClause.setFeature(Feature.TENSE, Tense.PAST);
        englishClause.setFeature(Feature.NEGATED, true);

        // Complements
        englishClause.addComplement("great");

        // Front-modifiers
        englishClause.addFrontModifier("by the way");

        // Output
        String dutchOutput = realiser.realiseSentence(dutchClause);
        String englishOutput = realiser.realiseSentence(englishClause);
        System.out.println(dutchOutput);
        System.out.println(englishOutput);

        // Semanticons
        Semanticon semanticon = new Semanticon();
        System.out.println(semanticon.getSem("english").getCategories().toString());
        System.out.println(semanticon.getSemsInCategory("pers_pron").toString());
    }
}