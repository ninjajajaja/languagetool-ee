/* LanguageTool, a natural language style checker
 * Copyright (C) 2005 Daniel Naber (http://www.danielnaber.de)
 * Copyright (C) 2013 Stefan Lotties
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

import gnu.trove.THashSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.Language;
import org.languagetool.rules.patterns.Match.IncludeRange;
import org.languagetool.synthesis.Synthesizer;
import org.languagetool.tools.StringTools;

import static org.languagetool.JLanguageTool.PARAGRAPH_END_TAGNAME;
import static org.languagetool.JLanguageTool.SENTENCE_END_TAGNAME;
import static org.languagetool.JLanguageTool.SENTENCE_START_TAGNAME;

/**
 * The state of a matching process. This state is <strong>NOT</strong> thread-safe.
 *
 * @author Stefan Lotties
 * @since 2.3
 */
public class MatchState {

  public final Match match;
  private final Synthesizer synthesizer;

  private AnalyzedTokenReadings formattedToken;
  private AnalyzedTokenReadings matchedToken;
  private String skippedTokens;

  public MatchState(Match match, Synthesizer synthesizer) {
    this.match = match;
    this.synthesizer = synthesizer;
    String lemma = match.lemma;
    if (!StringUtils.isEmpty(lemma)) {
      formattedToken = new AnalyzedTokenReadings(new AnalyzedToken(lemma, match.posTag, lemma), 0);
    }
  }

  public void setToken(AnalyzedTokenReadings token) {
    if (match.staticLemma) {
      this.matchedToken = token;
    } else {
      this.formattedToken = token;
    }
  }

  /**
   * Sets the token to be formatted etc. and includes the support for
   * including the skipped tokens.
   *
   * @param tokens Array of tokens
   * @param index Index of the token to be formatted
   * @param next Position of the next token (the skipped tokens are the ones between the tokens[index] and tokens[next]
   */
  public final void setToken(AnalyzedTokenReadings[] tokens, int index, int next) {
    int idx = index;
    if (index >= tokens.length) {
      // TODO: hacky workaround, find a proper solution. See EnglishPatternRuleTest.testBug()
      idx = tokens.length - 1;
    }
    setToken(tokens[idx]);
    IncludeRange includeSkipped = match.includeSkipped;
    if (next > 1 && includeSkipped != IncludeRange.NONE) {
      String sb = new String();
      if (includeSkipped == IncludeRange.FOLLOWING) {
        formattedToken = null;
      }
      for (int k = index + 1; k < index + next; k++) {
        if (tokens[k].isWhitespaceBefore()
            && !(k == index + 1 && includeSkipped == IncludeRange.FOLLOWING)) {
          sb += ' ';
        }
        sb += tokens[k].getToken();
      }
      skippedTokens = sb;
    } else {
      skippedTokens = "";
    }
  }

  public final AnalyzedTokenReadings filterReadings() {
    List<AnalyzedToken> l = new ArrayList<>();
    if (formattedToken != null) {
      if (match.staticLemma) {
        // Note: we want the token without ignored characters so we can't use matchedToken.getToken()
        matchedToken.leaveReading(new AnalyzedToken(matchedToken.getReadings().get(0).getToken(),
                match.posTag, formattedToken.getToken()));
        formattedToken = matchedToken;
      }
      // Note: we want the token without ignored characters so we can't use formattedToken.getToken()
      String token = formattedToken.getAnalyzedToken(0).getToken();
      Pattern regexMatch = match.pRegexMatch;
      String regexReplace = match.regexReplace;
      if (regexMatch != null && regexReplace != null) {
        /* only replace if it is something to replace */
        token = regexMatch.matcher(token).replaceAll(regexReplace);
      }
      token = convertCase(token, token, null);

      String posTag = match.posTag;
      if (posTag != null) {
        int numRead = formattedToken.getReadingsLength();
        if (match.postagRegexp) {
          Pattern pPosRegexMatch = match.pPosRegexMatch;
          String posTagReplace = match.posTagReplace;
          String targetPosTag;
          for (AnalyzedToken at : formattedToken.anTokReadings) {
            String testTag = at.getPOSTag();
            if (testTag != null && pPosRegexMatch.matcher(testTag).matches()) {
              targetPosTag = testTag;
              if (posTagReplace != null) {
                targetPosTag = pPosRegexMatch.matcher(targetPosTag).replaceAll(posTagReplace);
              }
              l.add(new AnalyzedToken(token, targetPosTag,
                  at.getLemma()));
              l.get(l.size() - 1).setWhitespaceBefore(formattedToken.isWhitespaceBefore());
            }
          }
          if (l.isEmpty()) {
            l.addAll(getNewToken(numRead, token));
          }
        } else {
          l.addAll(getNewToken(numRead, token));
        }
        String lemma = formattedToken.getAnalyzedToken(0).getLemma();
        if (formattedToken.isSentenceEnd()) {
          l.add(new AnalyzedToken(formattedToken.getToken(), SENTENCE_END_TAGNAME, lemma));
        }
        if (formattedToken.isParagraphEnd()) {
          l.add(new AnalyzedToken(formattedToken.getToken(), PARAGRAPH_END_TAGNAME, lemma));
        }

      }
    }
    if (l.isEmpty()) {
      return formattedToken;
    }
    final AnalyzedTokenReadings anTkRead = new AnalyzedTokenReadings(
        l.toArray(new AnalyzedToken[0]),
        formattedToken.getStartPos());
    // TODO: in case original had ignored characters we want to restore readings.token
    // but there's no setToken() available :(
//    anTkRead.setToken(formattedToken.getToken());

    anTkRead.setWhitespaceBefore(formattedToken.getWhitespaceBefore());
    if (!formattedToken.getChunkTags().isEmpty()) {
      anTkRead.setChunkTags(formattedToken.getChunkTags());
    }
    if (formattedToken.isImmunized()) {
      anTkRead.immunize(formattedToken.getImmunizationSourceLine());
    }
    return anTkRead;
  }

  /**
   * Converts case of the string token according to match element attributes.
   * @param s Token to be converted.
   * @param sample the sample string used to determine how the original string looks like (used only on case preservation)
   * @return Converted string.
   */
  String convertCase(String s, String sample, Language lang) {
    return CaseConversionHelper.convertCase(match.caseConversionType, s, sample, lang);
  }

  private List<AnalyzedToken> getNewToken(int numRead, String token) {
    String posTag = match.posTag;
    List<AnalyzedToken> list = new ArrayList<>();
    String lemma = "";
    for (AnalyzedToken at : formattedToken.anTokReadings) {
      String tempPosTag = at.getPOSTag();
      if (tempPosTag != null) {
        if (tempPosTag.equals(posTag) && at.getLemma() != null) {
          lemma = at.getLemma();
        }
        if (StringTools.isEmpty(lemma)) {
          lemma = formattedToken.getAnalyzedToken(0).getLemma();
        }
        list.add(new AnalyzedToken(token, posTag, lemma));
        list.get(list.size() - 1).setWhitespaceBefore(
            formattedToken.isWhitespaceBefore());
      }
    }
    return list;
  }

  /**
   * Gets all strings formatted using the match element.
   */
  public final String[] toFinalString(Language lang) throws IOException {
    String[] formattedString = new String[1];
    if (formattedToken != null) {
      int readingCount = formattedToken.getReadingsLength();
      formattedString[0] = formattedToken.getToken();

      Pattern pRegexMatch = match.pRegexMatch;
      String regexReplace = match.regexReplace;
      if (pRegexMatch != null) {
        if (lang != null && lang.getShortCode().equals("ar")) {
           formattedString[0] = StringTools.removeTashkeel(formattedString[0]);
        }
        formattedString[0] = pRegexMatch.matcher(formattedString[0]).replaceAll(regexReplace);
      }

      String posTag = match.posTag;
      if (posTag != null) {
        if (synthesizer == null) {
          formattedString[0] = formattedToken.getToken();
        } else if (match.postagRegexp) {
          THashSet<String> wordForms = new THashSet<>();
          boolean oneForm = false;
          for (int k = 0; k < readingCount; k++) {
            if (formattedToken.getAnalyzedToken(k).getLemma() == null) {
              String posUnique = formattedToken.getAnalyzedToken(k).getPOSTag();
              if (posUnique == null) {
                wordForms.add(formattedToken.getToken());
                oneForm = true;
              } else {
                if (SENTENCE_START_TAGNAME.equals(posUnique)
                    || SENTENCE_END_TAGNAME.equals(posUnique)
                    || PARAGRAPH_END_TAGNAME.equals(posUnique)) {
                  if (!oneForm) {
                    wordForms.add(formattedToken.getToken());
                  }
                  oneForm = true;
                } else {
                  oneForm = false;
                }
              }
            }
          }
          String targetPosTag = getTargetPosTag();
          if (!oneForm) {
            for (int i = 0; i < readingCount; i++) {
              String[] possibleWordForms = synthesizer.synthesize(
                  formattedToken.getAnalyzedToken(i), targetPosTag, true);
              if (possibleWordForms != null && possibleWordForms.length > 0) {
                wordForms.addAll(Arrays.asList(possibleWordForms));
              }
            }
          }
          if (wordForms.isEmpty()) {
            formattedString[0] = "(" + formattedToken.getToken() + ")";
          } else {
            formattedString = wordForms.toArray(new String[0]);
          }
        } else {
          TreeSet<String> wordForms = new TreeSet<>();
          for (AnalyzedToken at : formattedToken.anTokReadings) {
            String[] possibleWordForms = synthesizer.synthesize(at, posTag);
            if (possibleWordForms != null) {
              wordForms.addAll(Arrays.asList(possibleWordForms));
            }
          }
          formattedString = wordForms.toArray(new String[0]);
        }
      }
    }
    String original;
    if (match.staticLemma) {
      original = matchedToken != null ? matchedToken.getToken() : "";
    } else {
      original = formattedToken != null ? formattedToken.getToken() : "";
    }
    int formattedStringLength = formattedString.length;
    for (int i = 0; i < formattedStringLength; i++) {
      formattedString[i] = convertCase(formattedString[i], original, lang);
    }
    // TODO should case conversion happen before or after including skipped tokens?
    IncludeRange includeSkipped = match.includeSkipped;
    if (includeSkipped != IncludeRange.NONE && skippedTokens != null
        && !skippedTokens.isEmpty()) {
      String[] helper = new String[formattedStringLength];
      for (int i = 0; i < formattedStringLength; i++) {
        if (formattedString[i] == null) {
          formattedString[i] = "";
        }
        helper[i] = formattedString[i] + skippedTokens;
      }
      formattedString = helper;
    }
    if (match.checksSpelling() && lang != null) {
      List<String> formattedStringElements = Arrays.asList(formattedString);
      // tagger-based speller
      List<AnalyzedTokenReadings> analyzed = lang.getTagger().tag(
          formattedStringElements);
      for (int i = 0; i < formattedStringLength; i++) {
        AnalyzedToken analyzedToken = analyzed.get(i).getAnalyzedToken(0);
        if (analyzedToken.getLemma() == null && analyzedToken.hasNoTag()) {
          formattedString[i] = PatternRuleMatcher.MISTAKE;
        }
      }
    }
    return formattedString;
  }

  /**
   * Format POS tag using parameters already defined in the class.
   *
   * @return Formatted POS tag as String.
   */
  // FIXME: gets only the first POS tag that matches, this can be wrong
  // on the other hand, many POS tags = too many suggestions?
  // POS tags can be chosen by the synthesizer of each language: synthesizer.getTargetPosTag()
  public final String getTargetPosTag() {
    String targetPosTag = match.posTag;
    List<String> posTags = new ArrayList<>();
    Pattern pPosRegexMatch = match.pPosRegexMatch;
    String posTagReplace = match.posTagReplace;
    if (match.staticLemma) {
      for (AnalyzedToken analyzedToken : matchedToken) {
        String tst = analyzedToken.getPOSTag();
        if (tst != null && pPosRegexMatch.matcher(tst).matches()) {
          posTags.add(tst);
        }
      }
      targetPosTag = synthesizer.getTargetPosTag(posTags, targetPosTag);
      if (pPosRegexMatch != null && posTagReplace != null && !posTags.isEmpty()) {
        targetPosTag = pPosRegexMatch.matcher(targetPosTag).replaceAll(posTagReplace);
      }
    } else {
      for (AnalyzedToken analyzedToken : formattedToken) {
        String tst = analyzedToken.getPOSTag();
        if (tst != null && pPosRegexMatch.matcher(tst).matches()) {
          posTags.add(tst);
        }
      }
      targetPosTag = synthesizer.getTargetPosTag(posTags, targetPosTag);
      if (pPosRegexMatch != null && posTagReplace != null) {
        if (posTags.isEmpty()) {
          posTags.add(targetPosTag);
        }
        String sb = new String();
        int posTagLen = posTags.size();
        int l = 0;
        for (String lPosTag : posTags) {
          l++;
          lPosTag = pPosRegexMatch.matcher(lPosTag).replaceAll(posTagReplace);
          if (match.setPos) {
            lPosTag = synthesizer.getPosTagCorrection(lPosTag);
          }
          sb += lPosTag;
          if (l < posTagLen) {
            sb += '|';
          }
        }
        targetPosTag = sb;
      }
    }
    return targetPosTag;
  }

  /**
   * Method for getting the formatted match as a single string. In case of
   * multiple matches, it joins them using a regular expression operator "|".
   * @return Formatted string of the matched token.
   */
  final String toTokenString() throws IOException {
    String[] stringToFormat = toFinalString(null);
    return String.join("|", Arrays.asList(stringToFormat));
  }

}
