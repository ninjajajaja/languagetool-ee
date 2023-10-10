package org.languagetool.rules;

import junit.framework.TestCase;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Demo;

public class ReadabilityRuleTest extends TestCase {

  ReadabilityRule rule;
  Demo lang;
  JLanguageTool lt;

  public void setUp() throws Exception {
    lang = new Demo();
    rule = new ReadabilityRule(TestTools.getEnglishMessages(), lang, null, false);
    lt = new JLanguageTool(lang);
  }

  public void testSimpleSyllablesCount() {
    assertEquals(3, rule.simpleSyllablesCount("kkkkkkkkkkkkkkkkkkrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrblablabla"));
  }
}