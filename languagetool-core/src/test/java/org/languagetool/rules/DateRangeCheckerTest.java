package org.languagetool.rules;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

public class DateRangeCheckerTest extends TestCase {

  DateRangeChecker dateRangeChecker;

  public void setUp() {
    dateRangeChecker = new DateRangeChecker();
  }

  public void testAcceptRuleMatch() {
    Map map = new HashMap<String,String>();
    map.put("x", "1");
    map.put("y", "2");
    map.put("z", "3");
    dateRangeChecker.acceptRuleMatch(null, map, 0, null);
  }
}