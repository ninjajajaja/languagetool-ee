package org.languagetool.rules;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import junit.framework.TestCase;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;

public class WhitespaceCheckFilterTest extends TestCase {

  WhitespaceCheckFilter filter;

    public void setUp() throws Exception {
        filter = new WhitespaceCheckFilter();
    }

    public void testAcceptRuleMatch() {
      Hashtable map = new Hashtable<String,String>();
      map.put("whitespaceChar", " ");
      map.put("position", "1");
      map.put("nothing", "nope");
      filter.acceptRuleMatch(null, map, 0, new AnalyzedTokenReadings[]{new AnalyzedTokenReadings(new AnalyzedToken("","",""),0)});
    }
}