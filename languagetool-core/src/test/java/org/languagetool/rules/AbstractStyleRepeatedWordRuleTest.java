package org.languagetool.rules;

import junit.framework.TestCase;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;

public class AbstractStyleRepeatedWordRuleTest extends TestCase {

  public void testHasBreakToken() {
    AbstractStyleRepeatedWordRule.hasBreakToken(new AnalyzedTokenReadings[]{
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
    );
  }
}