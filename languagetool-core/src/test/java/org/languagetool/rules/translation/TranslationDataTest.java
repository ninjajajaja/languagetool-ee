package org.languagetool.rules.translation;

import junit.framework.TestCase;

public class TranslationDataTest extends TestCase {

  public void testGetTranslations() {
    TranslationData td = new TranslationData(null, null);
    assertNull(td.getTranslations());
  }
}