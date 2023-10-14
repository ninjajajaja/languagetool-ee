package org.languagetool.rules;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.tagging.Tagger;

public class AbstractFindSuggestionsFilterTest extends TestCase {

  AbstractFindSuggestionsFilter findSuggestionsFilter;

  public void setUp() throws Exception {
    findSuggestionsFilter = new AbstractFindSuggestionsFilter() {
      @Override
      protected Tagger getTagger() {
        return null;
      }

      @Override
      protected List<String> getSpellingSuggestions(AnalyzedTokenReadings atr) throws IOException {
        return null;
      }
    };
  }

  public void testBools() {
    findSuggestionsFilter.bSuppressMatch("supprm");
    findSuggestionsFilter.bSuppressMatch("true");
    findSuggestionsFilter.diacriticsMode("mode");
    findSuggestionsFilter.diacriticsMode(null);
  }

  public void testAcceptRuleMatch() throws IOException {
    RuleMatch ruleMatch = new RuleMatch(new FakeRule(), 0, 10, "message");
    Hashtable map = new Hashtable<String,String>();
    map.put("Mode", "Mode");
    map.put("suppressMatch", "suppressMatch");
    map.put("removeSuggestionsRegexp", "removeSuggestionsRegexp");
    map.put("priorityPostag", "priorityPostag");
    map.put("desiredPostag", "desiredPostag");
    map.put("wordFrom", "wordFrom");
    map.put("", "");

    findSuggestionsFilter.acceptRuleMatch(ruleMatch, map, 0, new AnalyzedTokenReadings[0]);
  }

}