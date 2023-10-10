/*
 *  LanguageTool, a natural language style checker
 *  * Copyright (C) 2018 Fabian Richter
 *  *
 *  * This library is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU Lesser General Public
 *  * License as published by the Free Software Foundation; either
 *  * version 2.1 of the License, or (at your option) any later version.
 *  *
 *  * This library is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  * Lesser General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU Lesser General Public
 *  * License along with this library; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 *  * USA
 *
 */

package org.languagetool.rules.spelling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import junit.framework.TestCase;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.language.Demo;
import org.languagetool.rules.spelling.symspell.implementation.SuggestionStage;
import org.languagetool.rules.spelling.symspell.implementation.SymSpell;

public class SymSpellRuleTest extends TestCase {

  public void testSymSpellRule() throws IOException {
    SymSpellRule.initDefaultDictSpeller(new Demo());
    SymSpellRule.getWordList(null, null);
    SymSpellRule ssr = new SymSpellRule(null, null, null);
    ssr.filterCandidates(Arrays.asList("ah", "hallo", "i", "i", "i", "i", "i", "yuck", "i", "i", "i", "i", "i", "i"));
    ssr.match(new AnalyzedSentence(
      new AnalyzedTokenReadings[]{
        new AnalyzedTokenReadings(new AnalyzedToken("hi", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("i'm", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("a", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("cute", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("little", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("penguin", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("eh", "posTag", "lemma"))
      }
      ));
    ssr.setConsiderIgnoreWords(false);
  }

  public void testSymspell() {
    SymSpell symSpell = new SymSpell(100, 100, 10, 5);
    symSpell.createDictionaryEntry("apple", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("banana", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("carrot", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("dog", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("elephant", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("fountain", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("grape", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("house", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("icecream", 1, null);
    symSpell.createDictionaryEntry("jacket", 1, null);
    symSpell.createDictionaryEntry("kangaroo", 1, null);
    symSpell.createDictionaryEntry("lemon", 1, null);
    symSpell.createDictionaryEntry("mountain", 1, null);
    symSpell.createDictionaryEntry("noodle", 1, null);
    symSpell.createDictionaryEntry("ocean", 1, null);
    symSpell.createDictionaryEntry("penguin", 1, null);
    symSpell.createDictionaryEntry("quokka", 1, null);
    symSpell.createDictionaryEntry("rainbow", 1, null);
    symSpell.createDictionaryEntry("sunflower", 1, null);
    symSpell.createDictionaryEntry("turtle", 1, null);
    symSpell.createDictionaryEntry("coffee", 1, null);
    symSpell.createDictionaryEntry("keyboard", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("mountain", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("notebook", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("piano", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("umbrella", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("volcano", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("waterfall", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("xylophone", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("zebra", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("zebra", 1, null);
    symSpell.createDictionaryEntry("zebra", 1, new SuggestionStage(100));
    symSpell.createDictionaryEntry("zebra", 1, new SuggestionStage(100));








  }

}
