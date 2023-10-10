/* LanguageTool, a natural language style checker
 * Copyright (C) 2020 Daniel Naber (http://www.danielnaber.de)
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
package org.languagetool.rules.spelling.morfologik;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import junit.framework.TestCase;
import org.languagetool.language.Demo;
import org.languagetool.rules.SuggestedReplacement;

public class MorfologikSpellerRuleTest extends TestCase {

  public void testMorfologikSpellerRule() throws IOException {
    MorfologikSpellerRule morfologikSpellerRule = new MorfologikSpellerRule(null, new Demo(), null) {
      @Override
      public String getFileName() {
        return null;
      }

      @Override
      public String getId() {
        return null;
      }
    };
    morfologikSpellerRule.setLocale(Locale.GERMAN);

    assertFalse(morfologikSpellerRule.onlyCaseDiffers(Collections.emptyList(), "word"));
    assertFalse(morfologikSpellerRule.onlyCaseDiffers(Collections.emptyList(), "Word"));
    assertTrue(morfologikSpellerRule.onlyCaseDiffers(Arrays.asList(new SuggestedReplacement("Word")), "word"));
    assertTrue(morfologikSpellerRule.onlyCaseDiffers(Arrays.asList(new SuggestedReplacement("word")), "word"));

    List<SuggestedReplacement> suggRepls = new ArrayList<SuggestedReplacement>();
    for (int i = 0; i < 30; i++) suggRepls.add(new SuggestedReplacement("replacement"+i, "short description"+i, "suffix"+i));
    for (int i = 0; i < 30; i++) suggRepls.add(new SuggestedReplacement("replacement"+i, "short description"+i, "suffix"+i));
    MorfologikSpellerRule.mergeSuggestionsWithSameTranslation(suggRepls);

  }

}
