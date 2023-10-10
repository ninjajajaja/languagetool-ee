package org.languagetool.rules.spelling;

import junit.framework.TestCase;
import org.languagetool.language.Demo;

public class RuleWithLanguageTest extends TestCase {

  public void testGetRuleGetLanguage() {

    RuleWithLanguage rwl = new RuleWithLanguage(new SymSpellRule(null, null, null), new Demo());
    assertNotNull(rwl.getRule());
    assertNotNull(rwl.getLanguage());
  }
}