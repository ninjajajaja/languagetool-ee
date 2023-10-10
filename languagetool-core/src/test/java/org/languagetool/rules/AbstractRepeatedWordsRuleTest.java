package org.languagetool.rules;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.language.Demo;
import org.languagetool.synthesis.Synthesizer;

public class AbstractRepeatedWordsRuleTest extends TestCase {

  AbstractRepeatedWordsRule rule;

  public void setUp() throws Exception {
    rule = new AbstractRepeatedWordsRule(null, new Demo()) {
      @Override
      protected Hashtable<String, SynonymsData> getWordsToCheck() {
        Hashtable map = new Hashtable();
        List<String> l = Arrays.asList("lemma", "lemma", "lemma", "lemma", "lemma", "lemma", "lemma", "lemma", "lemma", "lemma", "lemma", "lemma", "lemma", "lemma", "lemma", "lemma", "lemma", "lemma");
        map.put("a", new SynonymsData(l, "posTag", "chunk"));
        map.put("b", new SynonymsData(l, "posTag", "chunk"));
        map.put("c", new SynonymsData(l, "posTag", "chunk"));
        map.put("d", new SynonymsData(l, "posTag", "chunk"));
        map.put("e", new SynonymsData(l, "posTag", "chunk"));
        map.put("f", new SynonymsData(l, "posTag", "chunk"));
        map.put("g", new SynonymsData(l, "posTag", "chunk"));
        map.put("h", new SynonymsData(l, "posTag", "chunk"));
        map.put("lemma", new SynonymsData(l, "posTag", "chunk"));
        return map;
      }

      @Override
      protected Synthesizer getSynthesizer() {
        return null;
      }

      @Override
      protected String getMessage() {
        return null;
      }

      @Override
      protected String getShortMessage() {
        return null;
      }

      @Override
      public String getDescription() {
        return null;
      }

      @Override
      protected boolean isException(AnalyzedTokenReadings[] tokens, int i, boolean sentStart, boolean isCapitalized, boolean isAllUppercase) {
        return false;
      }
    };
  }

  public void testMatch() throws IOException {
    AnalyzedSentence as = new AnalyzedSentence(
      new AnalyzedTokenReadings[]{
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken(".", "posTag", "lemma")),
      });

    rule.match(Arrays.asList(as,as,as,as,as,as,as,as,as,as,as,as));
  }

  public void testLoadWords() {
    rule.loadWords("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam; erat, sed diam voluptua. At vero eos et accusam et justo duo dolores; et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est; Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam; nonumy eirmod tempor invidunt ut labore et dolore magna; aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata; sanctus est Lorem ipsum dolor sit amet.");
  }

}