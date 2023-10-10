package org.languagetool.rules;

import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.languagetool.UserConfig;

public class DictionaryMatchFilterTest extends TestCase {

  DictionaryMatchFilter filter;

  public void setUp() throws Exception {
    UserConfig userConfig = new UserConfig();
    userConfig.userSpecificSpellerWords = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
    filter = new DictionaryMatchFilter(userConfig);
  }

  public void testFilter() {
    List<RuleMatch> l = Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    filter.filter(l, null);
  }
}