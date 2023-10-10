package org.languagetool.rules.translation;

import java.util.Collections;
import junit.framework.TestCase;

public class TranslationEntryTest extends TestCase {

  public void testGetters() {
    TranslationEntry te = new TranslationEntry(Collections.emptyList(), Collections.emptyList(), 1);
    assertNotNull(te.l1);
    assertNotNull(te.l2);
    assertEquals(1, te.itemCount);
  }
}