package backend.services.service;

import org.springframework.stereotype.Service;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class SeverityLabelerService {

    private static final Set<String> MAJOR_KEYWORDS = new HashSet<>();
    private static final Set<String> MODERATE_KEYWORDS = new HashSet<>();
    private static final Set<String> MINOR_KEYWORDS = new HashSet<>();

    static {
        MAJOR_KEYWORDS.add("life-threatening");
        MAJOR_KEYWORDS.add("severe");
        MAJOR_KEYWORDS.add("fatal");
        MAJOR_KEYWORDS.add("death");
        MAJOR_KEYWORDS.add("emergency");
        MAJOR_KEYWORDS.add("hospitalization");
        MAJOR_KEYWORDS.add("anaphylaxis");
        MAJOR_KEYWORDS.add("severe bleeding");
        MAJOR_KEYWORDS.add("organ failure");
        MAJOR_KEYWORDS.add("cardiac arrest");
        MAJOR_KEYWORDS.add("respiratory failure");
        MAJOR_KEYWORDS.add("coma");
        MAJOR_KEYWORDS.add("seizure");
        MAJOR_KEYWORDS.add("permanent damage");
        MAJOR_KEYWORDS.add("intensive care");
        MAJOR_KEYWORDS.add("life-saving");
        MAJOR_KEYWORDS.add("surgery required");
        MAJOR_KEYWORDS.add("severe allergic reaction");
        MAJOR_KEYWORDS.add("severe hypotension");
        MAJOR_KEYWORDS.add("severe hypertension");
        MAJOR_KEYWORDS.add("severe liver injury");
        MAJOR_KEYWORDS.add("internal");
        MAJOR_KEYWORDS.add("bleeding");
        MAJOR_KEYWORDS.add("organ");
        MAJOR_KEYWORDS.add("serious");
        MAJOR_KEYWORDS.add("heart");
        MAJOR_KEYWORDS.add("renal");
        MAJOR_KEYWORDS.add("kidney");
        MAJOR_KEYWORDS.add("bleed");

        MODERATE_KEYWORDS.add("significant");
        MODERATE_KEYWORDS.add("requires medical attention");
        MODERATE_KEYWORDS.add("non-life-threatening");
        MODERATE_KEYWORDS.add("serious");
        MODERATE_KEYWORDS.add("prolonged");
        MODERATE_KEYWORDS.add("disability");
        MODERATE_KEYWORDS.add("requires intervention");
        MODERATE_KEYWORDS.add("potential hazard");
        MODERATE_KEYWORDS.add("notable risk");
        MODERATE_KEYWORDS.add("chronic");
        MODERATE_KEYWORDS.add("persistent");
        MODERATE_KEYWORDS.add("adverse effect");
        MODERATE_KEYWORDS.add("substantial impact");
        MODERATE_KEYWORDS.add("considerable");
        MODERATE_KEYWORDS.add("non-fatal");
        MODERATE_KEYWORDS.add("sustained");
        MODERATE_KEYWORDS.add("necessary treatment");
        MODERATE_KEYWORDS.add("serious but not fatal");
        MODERATE_KEYWORDS.add("requires monitoring");
        MODERATE_KEYWORDS.add("potential complication");
        MODERATE_KEYWORDS.add("serious condition");

        MINOR_KEYWORDS.add("mild");
        MINOR_KEYWORDS.add("temporary");
        MINOR_KEYWORDS.add("manageable");
        MINOR_KEYWORDS.add("minor discomfort");
        MINOR_KEYWORDS.add("slight");
        MINOR_KEYWORDS.add("minor rash");
        MINOR_KEYWORDS.add("minor headache");
        MINOR_KEYWORDS.add("minor dizziness");
        MINOR_KEYWORDS.add("transient");
        MINOR_KEYWORDS.add("mild nausea");
        MINOR_KEYWORDS.add("minor side effect");
        MINOR_KEYWORDS.add("minor allergic reaction");
        MINOR_KEYWORDS.add("manageable symptoms");
        MINOR_KEYWORDS.add("minor irritation");
        MINOR_KEYWORDS.add("mild pain");
        MINOR_KEYWORDS.add("short-term");
        MINOR_KEYWORDS.add("minor swelling");
        MINOR_KEYWORDS.add("mild fatigue");
        MINOR_KEYWORDS.add("minor inconvenience");
        MINOR_KEYWORDS.add("mild symptoms");
        MINOR_KEYWORDS.add("mild side effect");
    }

    public String labelParagraph(String paragraph) {
        try {
            // Load the OpenNLP tokenizer model from resources
            InputStream modelIn = getClass().getResourceAsStream("/en-token.bin");
            if (modelIn == null) {
                throw new IllegalArgumentException("Token model file not found!");
            }
            TokenizerModel model = new TokenizerModel(modelIn);
            Tokenizer tokenizer = new TokenizerME(model);

            // Tokenize the paragraph
            String[] tokens = tokenizer.tokenize(paragraph.toLowerCase());

            // Count matches for each severity category
            Map<String, Integer> severityCounts = new HashMap<>();
            severityCounts.put("Major", 0);
            severityCounts.put("Moderate", 0);
            severityCounts.put("Minor", 0);

            for (String token : tokens) {
                if (MAJOR_KEYWORDS.contains(token)) {
                    severityCounts.put("Major", severityCounts.get("Major") + 1);
                }
                if (MODERATE_KEYWORDS.contains(token)) {
                    severityCounts.put("Moderate", severityCounts.get("Moderate") + 1);
                }
                if (MINOR_KEYWORDS.contains(token)) {
                    severityCounts.put("Minor", severityCounts.get("Minor") + 1);
                }
            }

            modelIn.close();

            // Determine the label based on the counts
            return determineLabel(severityCounts);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    private String determineLabel(Map<String, Integer> severityCounts) {
        int majorCount = severityCounts.get("Major");
        int moderateCount = severityCounts.get("Moderate");
        int minorCount = severityCounts.get("Minor");

        if (majorCount == 0 && moderateCount == 0 && minorCount == 0) {
            return "None";
        }

        if (majorCount >= moderateCount) {
            if (moderateCount >= minorCount) return "Major";
            if (moderateCount < minorCount && majorCount < minorCount) return "Minor";
        } else if (majorCount < moderateCount) {
            if (majorCount >= minorCount) return "Moderate";
            if (majorCount < minorCount && moderateCount < minorCount) return "Minor";
        } else {
            if (moderateCount >= minorCount) return "Moderate";
        }
        return "Minor";
    }
}
