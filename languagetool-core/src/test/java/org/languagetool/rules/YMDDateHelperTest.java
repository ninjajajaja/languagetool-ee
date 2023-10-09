package org.languagetool.rules;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

public class YMDDateHelperTest extends TestCase {

  public void testHelper() {
    YMDDateHelper helper = new YMDDateHelper();
    Map map = new HashMap<String,String>();
    map.put("date", "2023-01-01");
    map.put("year", "2020");
    map.put("month", "6");
    map.put("day", "3");
    helper.parseDate(map);
    helper.correctDate(new RuleMatch(new FakeRule(), 0, 10, "message"), map);
  }
}