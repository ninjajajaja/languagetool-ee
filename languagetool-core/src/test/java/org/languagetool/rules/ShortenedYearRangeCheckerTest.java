package org.languagetool.rules;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import junit.framework.TestCase;

public class ShortenedYearRangeCheckerTest extends TestCase {

  ShortenedYearRangeChecker shortenedYearRangeChecker;

  public void setUp() throws Exception {
    shortenedYearRangeChecker = new ShortenedYearRangeChecker();
  }

  public void testAcceptRuleMatch() {
    Hashtable map = new Hashtable<String,String>();
    map.put("x", "2023");
    map.put("y", "2023");
    map.put("z", "2023");
    shortenedYearRangeChecker.acceptRuleMatch(null, map, 0, null);
  }
}