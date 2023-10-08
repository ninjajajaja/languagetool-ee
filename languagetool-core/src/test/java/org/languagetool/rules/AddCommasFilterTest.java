package org.languagetool.rules;

import junit.framework.TestCase;

public class AddCommasFilterTest extends TestCase {

  public void testBSuggestSemicolon() {
    AddCommasFilter addCommasFilter = new AddCommasFilter();
    addCommasFilter.bSuggestSemicolon("hallo");
    addCommasFilter.bSuggestSemicolon("true");
    addCommasFilter.bSuggestSemicolon(null);
  }
}