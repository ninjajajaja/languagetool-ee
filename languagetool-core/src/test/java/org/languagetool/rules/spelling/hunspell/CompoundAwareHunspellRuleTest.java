package org.languagetool.rules.spelling.hunspell;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import junit.framework.TestCase;
import org.languagetool.Language;
import org.languagetool.UserConfig;
import org.languagetool.languagemodel.LanguageModel;
import org.languagetool.rules.spelling.morfologik.MorfologikMultiSpeller;
import org.languagetool.tokenizers.CompoundWordTokenizer;

public class CompoundAwareHunspellRuleTest extends TestCase {

  public void testSortSuggestionsByQuality() {
    FakeCAHRule rule = new FakeCAHRule(null, null, null, null, null);
    assertEquals(11, rule.sortSuggestionByQuality("misisipi",
      Arrays.asList("misisipi", "misisipi", "misisipi", "misisipi", "misisipi", "misisipi", "misisipi", "misisipi", "misisipi", "misisipi", "misisipi")).size());
  }

  private class FakeCAHRule extends CompoundAwareHunspellRule {

    public FakeCAHRule(ResourceBundle messages, Language language, CompoundWordTokenizer compoundSplitter, MorfologikMultiSpeller morfoSpeller, UserConfig userConfig) {
      super(messages, language, compoundSplitter, morfoSpeller, userConfig);
    }

    public FakeCAHRule(ResourceBundle messages, Language language, CompoundWordTokenizer compoundSplitter, MorfologikMultiSpeller morfoSpeller, UserConfig userConfig, List<Language> altLanguages) {
      super(messages, language, compoundSplitter, morfoSpeller, userConfig, altLanguages);
    }

    public FakeCAHRule(ResourceBundle messages, Language language, CompoundWordTokenizer compoundSplitter, MorfologikMultiSpeller morfoSpeller, UserConfig userConfig, List<Language> altLanguages, LanguageModel languageModel) {
      super(messages, language, compoundSplitter, morfoSpeller, userConfig, altLanguages, languageModel);
    }

    @Override
    protected void filterForLanguage(List<String> suggestions) {
      // nope
    }
  }

}