package org.languagetool.rules;

import java.util.regex.Pattern;
import junit.framework.TestCase;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.language.Demo;

public class AbstractSpaceBeforeRuleTest extends TestCase {

  AbstractSpaceBeforeRule spaceBeforeRule;

  public void setUp() throws Exception {
    spaceBeforeRule = new AbstractSpaceBeforeRule(null, new Demo()) {
      @Override
      protected Pattern getConjunctions() {
        return null;
      }
    };
  }

  public void testMatch() {
    spaceBeforeRule.match(new AnalyzedSentence(
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