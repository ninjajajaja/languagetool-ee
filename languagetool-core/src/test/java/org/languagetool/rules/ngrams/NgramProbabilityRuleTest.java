package org.languagetool.rules.ngrams;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.FakeLanguage;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.languagemodel.LanguageModel;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;

public class NgramProbabilityRuleTest extends TestCase {
  private final NgramProbabilityRule rule = new NgramProbabilityRuleTest.FakeRule(new FakeLanguageModel(), new FakeLanguage());
  private final JLanguageTool lt = new JLanguageTool(new FakeLanguage());

  @Test
  public void testRule() throws IOException {
    rule.setMinProbability(0.5);
    assertGood(0, "Their");
    assertGood(0, "There");
    assertMatch(5, "Their are new ideas to explore.");
    assertGood(5, "There are new ideas to explore.");
    assertMatch(4, "Why is there car broken again?");
    assertGood(4, "Why is their car broken again?");
    assertGood(4, "Is this their useful test?");
    assertGood(4, "Is this there useful test?");  // error not found b/c no data
    NgramProbabilityRule rule = new NgramProbabilityRuleTest.FakeRule(new FakeLanguageModel(), new FakeLanguage()) {
    };
    assertGood(0, "Their are new ideas to explore.", rule);
    assertGood(0, "\"Their are new ideas to explore.\"", rule);
    assertGood(4, "But İm dabei gut auszusehen.");

    testGetters(rule);
  }

  private void testGetters(NgramProbabilityRule rule) {
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
  }

  private void assertMatch(int exp, String input, Rule rule) throws IOException {
    RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
    assertThat("Did not find match in: " + input, matches.length, is(exp));
  }

  private void assertMatch(int exp, String input) throws IOException {
    assertMatch(exp, input, rule);
  }

  private void assertGood(int exp, String input, Rule rule) throws IOException {
    RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
    assertThat("Got unexpected match in: " + input, matches.length, is(exp));
  }

  private void assertGood(int exp, String input) throws IOException {
    assertGood(exp, input, rule);
  }

  private static class FakeRule extends NgramProbabilityRule {
    private FakeRule(LanguageModel languageModel, Language language) {
      super(JLanguageTool.getMessageBundle(), languageModel, language);
    }
    @Override
    public String getDescription() {
      return null;
    }
  }

}