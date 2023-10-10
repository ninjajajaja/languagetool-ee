package org.languagetool.rules;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import junit.framework.TestCase;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;

public class AbstractWordCoherencyRuleTest extends TestCase {

  AbstractWordCoherencyRule rule;

  public void setUp() throws Exception {
    rule = new AbstractWordCoherencyRule(null) {
      @Override
      protected Map<String, Set<String>> getWordMap() {
        Map map = new HashMap();
        for (int i = 0; i < 50; i++) {
          map.put(String.valueOf(i), new HashSet<>(Arrays.asList(";", "-", ")")));
        }
        return map;
      }

      @Override
      protected String getMessage(String word1, String word2) {
        return null;
      }

      @Override
      public String getId() {
        return null;
      }

      @Override
      public String getDescription() {
        return null;
      }
    };
  }

  public void testGetWordMap() throws IOException {
    Map<String,Set<String>> wordMap = rule.getWordMap();
    for (int i = 0; i < 50; i++) {
      wordMap.get(String.valueOf(i)).contains(";");
      wordMap.get(String.valueOf(i)).contains("-");
      wordMap.get(String.valueOf(i)).contains("(");
    }
  }

}