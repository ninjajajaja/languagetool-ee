package org.languagetool.rules;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import junit.framework.TestCase;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;

public class AdaptSuggestionsFilterTest extends TestCase {

  public void testAcceptRuleMatch() throws IOException {
    AdaptSuggestionsFilter adaptSuggestionsFilter = new AdaptSuggestionsFilter();

    Hashtable<String,String> m = new Hashtable<>();
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
    adaptSuggestionsFilter.acceptRuleMatch(match, m, 1, new AnalyzedTokenReadings[]{new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma"))});

  }
}