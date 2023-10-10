package org.languagetool.rules.spelling;

import junit.framework.TestCase;

public class CommonFileTypesTest extends TestCase {

  public void testGetSuffixPattern() {
    assertNotNull(CommonFileTypes.getSuffixPattern());
  }
}