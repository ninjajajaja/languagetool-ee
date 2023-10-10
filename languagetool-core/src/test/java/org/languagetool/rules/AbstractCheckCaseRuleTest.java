package org.languagetool.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import junit.framework.TestCase;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.language.Demo;

public class AbstractCheckCaseRuleTest extends TestCase {

  AbstractCheckCaseRule checkCaseRule;

  public void setUp() {
    checkCaseRule = new AbstractCheckCaseRule(null, new Demo()) {
      @Override
      public List<String> getFileNames() {
        return Collections.emptyList();
      }

      @Override
      public String getId() {
        return null;
      }

      @Override
      public String getDescription() {
        return null;
      }

      @Override
      public String getShort() {
        return null;
      }

      @Override
      public String getMessage() {
        return null;
      }

      @Override
      public Locale getLocale() {
        return null;
      }

      public List<Map<String, SuggestionWithMessage>> getWrongWords(boolean checkingCase) {
      Map m = new HashMap<String, SuggestionWithMessage>();
      m.put("aa",new
      SuggestionWithMessage("a"));
      return Arrays.asList(m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m,m);
    }
    };
  }

  public void testMatch() {
    checkCaseRule.match(new AnalyzedSentence(
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
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
      }
    ));
  }
}