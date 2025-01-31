/* LanguageTool, a natural language style checker 
 * Copyright (C) 2014 Daniel Naber (http://www.danielnaber.de)
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
package org.languagetool.rules.ngrams;

import org.junit.Test;
import org.languagetool.FakeLanguage;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.languagemodel.LanguageModel;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.languagetool.rules.patterns.bitext.BitextPatternRule;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ConfusionProbabilityRuleTest {

  private final ConfusionProbabilityRule rule = new FakeRule(new FakeLanguageModel(), new FakeLanguage());
  private final JLanguageTool lt = new JLanguageTool(new FakeLanguage());

  @Test
  public void testRule() throws IOException {
    assertGood("Their");
    assertGood("There");
    assertMatch("Their are new ideas to explore.");
    assertGood("There are new ideas to explore.");
    assertMatch("Why is there car broken again?");
    assertGood("Why is their car broken again?");
    assertGood("Is this their useful test?");
    assertGood("Is this there useful test?");  // error not found b/c no data
    ConfusionProbabilityRule ruleWithException = new FakeRule(new FakeLanguageModel(), new FakeLanguage()) {
      @Override
      protected boolean isException(String sentenceText, int startPos, int endPos) {
        return sentenceText.contains("Their are");
      }
    };
    assertGood("Their are new ideas to explore.", ruleWithException);
    assertGood("\"Their are new ideas to explore.\"", ruleWithException);
    assertGood("But İm dabei gut auszusehen.");

    testGetters(ruleWithException);
  }

  private void testGetters(ConfusionProbabilityRule rule) {
    assertNull(rule.getDescription());
    assertNotNull(rule.getId());
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
    assertNotNull(rule.getFilenames());
  }

  @Test
  public void testLocalException() throws IOException {
    ConfusionProbabilityRule rule1 = new FakeRule(new FakeLanguageModel(), new FakeLanguage()) {};
    assertMatch("Their are new ideas to explore.", rule1);
    ConfusionProbabilityRule rule2 = new FakeRule(new FakeLanguageModel(), new FakeLanguage(), Arrays.asList("their are")) {};
    assertGood("Their are new ideas to explore.", rule2);
    assertGood("And their are new ideas to explore.", rule2);
    assertGood("Their are new ideas to explore and their are new plans.", rule2);
  }

  private void assertMatch(String input, Rule rule) throws IOException {
    RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
    assertThat("Did not find match in: " + input, matches.length, is(1));
  }

  private void assertMatch(String input) throws IOException {
    assertMatch(input, rule);
  }

  private void assertGood(String input, Rule rule) throws IOException {
    RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
    assertThat("Got unexpected match in: " + input, matches.length, is(0));
  }

  private void assertGood(String input) throws IOException {
    assertGood(input, rule);
  }

  private static class FakeRule extends ConfusionProbabilityRule {
    private FakeRule(LanguageModel languageModel, Language language) {
      super(JLanguageTool.getMessageBundle(), languageModel, language);
    }
    private FakeRule(LanguageModel languageModel, Language language, List<String> exceptions) {
      super(JLanguageTool.getMessageBundle(), languageModel, language, 3, exceptions);
    }
    @Override
    public String getDescription() {
      return null;
    }
  }

}
