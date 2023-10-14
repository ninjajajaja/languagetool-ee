package org.languagetool.rules;

import gnu.trove.THashSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import junit.framework.TestCase;
import org.languagetool.UserConfig;
import org.languagetool.markup.AnnotatedText;

public class DictionarySpellMatchFilterTest extends TestCase {

  DictionarySpellMatchFilter filter;
  UserConfig userConfig;

  public void setUp() throws Exception {
    userConfig = new UserConfig();
    userConfig.acceptedPhrases = new THashSet(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
    filter = new DictionarySpellMatchFilter(userConfig);
  }

    public void testFilter() throws Exception {
    List<RuleMatch> l = Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    filter.filter(l, null);
  }
}