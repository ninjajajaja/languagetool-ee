package org.languagetool.rules;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.language.Demo;
import org.languagetool.rules.spelling.hunspell.HunspellRule;

public class AbstractSuppressMisspelledSuggestionsFilterTest extends TestCase {

  AnalyzedSentence as;
  AbstractSuppressMisspelledSuggestionsFilter misspelledSuggestionsFilter;

  public void setUp() throws Exception {
    as = new AnalyzedSentence(
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
    );

    misspelledSuggestionsFilter = new AbstractSuppressMisspelledSuggestionsFilter(new Demo()) {
    };

  }

  public void testAcceptRuleMatch() throws IOException {
    Map<String,String> m = new HashMap<>();
    m.put("1", "a");
    m.put("2", "b");
    m.put("3", "c");
    m.put("4", "d");
    m.put("5", "e");
    m.put("6", "f");
    m.put("suppressMatch", "false");
    m.put("SuppressPostag", "ok");
    RuleMatch match = new RuleMatch(new FakeRule(), 0, 10, "message");
    match.setSuggestedReplacements(Arrays.asList("hello","these","are","replacements","ok","hello","these","are","replacements","ok","hello","these","are","replacements","ok","hello","these","are","replacements","ok","hello","these","are","replacements","ok","hello","these","are","replacements","ok"));
    misspelledSuggestionsFilter.acceptRuleMatch(match, m, 1, new AnalyzedTokenReadings[]{new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma"))});
  }

  public void testBSuppress() {
    misspelledSuggestionsFilter.bSuppressMatch("match");
    misspelledSuggestionsFilter.bSuppressMatch("false");
    misspelledSuggestionsFilter.bSuppressMatch(null);
  }
}