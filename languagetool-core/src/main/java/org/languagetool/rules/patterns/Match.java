/* LanguageTool, a natural language style checker 
 * Copyright (C) 2005 Daniel Naber (http://www.danielnaber.de)
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
package org.languagetool.rules.patterns;

import java.util.regex.Pattern;

import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.synthesis.Synthesizer;
import org.languagetool.tools.StringTools;

/**
 * A {@link Match} is the configuration of an algorithm used to match {@link AnalyzedTokenReadings}s.
 * In XML, it's the {@code <match/>} element.
 * Use {@link #createState(Synthesizer, AnalyzedTokenReadings)} and {@link #createState(Synthesizer, AnalyzedTokenReadings[], int, int)}
 * to create a {@link MatchState} used to actually match {@link AnalyzedTokenReadings}.
 *
 * @author Marcin Miłkowski
 */
public final class Match {

  /** Possible string case conversions. **/
  public enum CaseConversion {
    NONE, STARTLOWER, STARTUPPER, ALLLOWER, ALLUPPER, PRESERVE, FIRSTUPPER, NOTASHKEEL
  }

  public enum IncludeRange {
    NONE, FOLLOWING, ALL
  }

  public String posTag;
  public boolean suppressMisspelled;
  public String regexReplace;
  public String posTagReplace;
  public CaseConversion caseConversionType;
  public IncludeRange includeSkipped;
  // Pattern used to define parts of the matched token:
  public Pattern pRegexMatch;
  // True if this match element is used for formatting POS token:
  public boolean setPos;

  public boolean postagRegexp;
  // True if this match element formats a statically defined lemma which is
  // enclosed by the element, e.g., <match...>word</match>:
  public boolean staticLemma;
  public String lemma;
  public int tokenRef;
  // Pattern used to define parts of the matched POS token:
  public Pattern pPosRegexMatch;
  // True when the match is not in the suggestion:
  public boolean inMessageOnly;

  public Match(String posTag, String posTagReplace,
      boolean postagRegexp, String regexMatch,
      String regexReplace, CaseConversion caseConversionType,
      boolean setPOS,
      boolean suppressMisspelled,
      IncludeRange includeSkipped) {
    this.posTag = posTag;
    this.postagRegexp = postagRegexp;
    this.caseConversionType = caseConversionType;
    pRegexMatch = regexMatch != null ? Pattern.compile(regexMatch) : null;
    if (postagRegexp && posTag != null) {
      pPosRegexMatch = Pattern.compile(posTag);
    }
    this.regexReplace = regexReplace;
    this.posTagReplace = posTagReplace;
    this.setPos = setPOS;
    this.includeSkipped = includeSkipped;
    this.suppressMisspelled = suppressMisspelled;
  }

  /**
   * Creates a state used for actually matching a token.
   * @since 2.3
   */
  public MatchState createState(Synthesizer synthesizer, AnalyzedTokenReadings token) {
    MatchState state = new MatchState(this, synthesizer);
    state.setToken(token);
    return state;
  }

  /**
   * Creates a state used for actually matching a token.
   * @since 2.3
   */
  public MatchState createState(Synthesizer synthesizer, AnalyzedTokenReadings[] tokens, int index, int next) {
    MatchState state = new MatchState(this, synthesizer);
    state.setToken(tokens, index, next);
    return state;
  }

  /**
   * Checks if the Match element uses regexp-based form of the POS tag.
   * @return True if regexp is used in POS.
   */
  public boolean posRegExp() {
    return postagRegexp;
  }

  /**
   * Sets a base form (lemma) that will be formatted, or synthesized, using the
   * specified POS regular expressions.
   * @param lemmaString String that specifies the base form.
   */
  public void setLemmaString(String lemmaString) {
    if (!StringTools.isEmpty(lemmaString)) {
      lemma = lemmaString;
      staticLemma = true;
      postagRegexp = true;
      if (posTag != null) {
        pPosRegexMatch = Pattern.compile(posTag);
      }
    }
  }

  /**
   * Used to tell whether the Match class will spell-check the result so
   * that misspelled suggestions are suppressed.
   * @return True if this is so.
   */
  public boolean checksSpelling() {
    return suppressMisspelled;
  }

  /**
   * Used to let LT know that it should change the case of the match.
   * @return true if match converts the case of the token.
   */
  public boolean convertsCase() {
    return caseConversionType != CaseConversion.NONE;
  }
}
