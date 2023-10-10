/* LanguageTool, a natural language style checker 
 * Copyright (C) 2010 Marcin Miłkowski (www.languagetool.org)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */

package org.languagetool.rules.bitext;

import org.junit.Test;
import org.languagetool.FakeLanguage;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import org.languagetool.rules.patterns.bitext.BitextPatternRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DifferentPunctuationRuleTest {

  @Test
  public void testRule() throws IOException {
    DifferentPunctuationRule rule = new DifferentPunctuationRule();
    RuleMatch[] matches;
    JLanguageTool srcLangTool = new JLanguageTool(TestTools.getDemoLanguage());
    JLanguageTool trgLangTool = new JLanguageTool(new FakeLanguage());
    rule.setSourceLanguage(TestTools.getDemoLanguage());
    // correct sentences:
    matches = rule.match(
        srcLangTool.getAnalyzedSentence("This is a test sentence!"),
        trgLangTool.getAnalyzedSentence("C'est la vie!"));
    assertEquals(0, matches.length);

    matches = rule.match(
        srcLangTool.getAnalyzedSentence("one sentence"),
        trgLangTool.getAnalyzedSentence("jedno zdanie"));
    assertEquals(0, matches.length);

    // incorrect sentences:
    matches = rule.match(
        srcLangTool.getAnalyzedSentence("This this is a test sentence."),
        trgLangTool.getAnalyzedSentence("This this is a test sentence!"));
    assertEquals(1, matches.length);
    testGetters(rule);
  }

  private void testGetters(DifferentPunctuationRule rule) {
    assertNull(rule.getCorrectBitextExamples());
    assertNotNull(rule.getMessage());
    assertNull(rule.getIncorrectBitextExamples());
    assertNotNull(rule.getDescription());
    assertNotNull(rule.getId());
    assertNotNull(rule.getSourceLanguage());
    assertNotNull(rule.getAntiPatterns());
    assertNotNull(rule.getCategory());
    assertNotNull(rule.getConfigureText());
    assertNotNull(rule.getDefaultValue());
    assertNotNull(rule.getDistanceTokens());
    assertNotNull(rule.getErrorTriggeringExamples());
    assertNotNull(rule.getLocQualityIssueType());
    assertNotNull(rule.getMaxConfigurableValue());
    assertNotNull(rule.getMinConfigurableValue());
    assertNull(rule.getSourceFile());
    assertNull(rule.getSubId());
    assertNotNull(rule.getTags());
    assertNull(rule.getUrl());
    assertNotNull(rule.getToneTags());
  }

}
