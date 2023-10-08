package org.languagetool.rules;

import java.io.IOException;
import java.util.List;
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

}