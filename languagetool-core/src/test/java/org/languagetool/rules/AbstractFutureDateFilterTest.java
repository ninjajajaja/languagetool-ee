package org.languagetool.rules;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import org.languagetool.AnalyzedTokenReadings;

public class AbstractFutureDateFilterTest extends TestCase {

  AbstractFutureDateFilter filter;

  public void setUp() throws Exception {
    filter = new AbstractFutureDateFilter() {
      @Override
      protected int getMonth(String localizedMonth) {
        return 1;
      }

      @Override
      protected Calendar getCalendar() {
        return null;
      }
    };
  }

    public void testAcceptRuleMatch() {
    RuleMatch ruleMatch = new RuleMatch(new FakeRule(), 0, 10, "message");
    Map map = new HashMap<String,String>();
    map.put("year", "2023");
    map.put("month", "1");
    map.put("day", "1");
    map.put("weekDay", "1");

    filter.acceptRuleMatch(ruleMatch, map, 0, new AnalyzedTokenReadings[0]);
  }
}