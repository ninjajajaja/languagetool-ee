/* LanguageTool, a natural language style checker
 * Copyright (C) 2006 Daniel Naber (http://www.danielnaber.de)
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
package org.languagetool.rules;

import org.apache.commons.lang3.StringUtils;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.LinguServices;
import org.languagetool.UserConfig;
import org.languagetool.tools.StringTools;

import java.util.*;
import java.util.stream.Stream;

/**
 * Checks that compounds (if in the list) are not written as separate words.
 * 
 * @author Daniel Naber, Marcin Miłkowski (refactoring)
 */
public abstract class AbstractCompoundRule extends Rule {

  final static int MAX_TERMS = 5;

  private final String withHyphenMessage;
  private final String withoutHyphenMessage;
  private final String withOrWithoutHyphenMessage;
  private final String shortDesc;
  protected final LinguServices linguServices;   // Linguistic Service of LO/OO used for LO/OO extension is null in other cases
  protected final Language lang;                 // used by LO/OO Linguistic Service 
  // if true, the first word will be uncapitalized before compared to the entries in CompoundRuleData
  protected boolean sentenceStartsWithUpperCase = true;

  @Override
  public abstract String getId();

  @Override
  public abstract String getDescription();

  @Override
  public int estimateContextForSureMatch() {
    return 1;
  }

  /** @since 3.0 */
  public abstract CompoundRuleData getCompoundRuleData();

  /**
   * @since 3.0
   */
  public AbstractCompoundRule(ResourceBundle messages, Language lang, UserConfig userConfig,
                              String withHyphenMessage, String withoutHyphenMessage, String withOrWithoutHyphenMessage) {
    this(messages, lang, userConfig, withHyphenMessage, withoutHyphenMessage, withOrWithoutHyphenMessage, null);
  }

  /**
   * @since 3.0
   */
  public AbstractCompoundRule(ResourceBundle messages, Language lang, UserConfig userConfig,
                              String withHyphenMessage, String withoutHyphenMessage, String withOrWithoutHyphenMessage,
                              String shortMessage) {
    if (messages != null) super.setCategory(Categories.MISC.getCategory(messages));
    this.withHyphenMessage = withHyphenMessage;
    this.withoutHyphenMessage = withoutHyphenMessage;
    this.withOrWithoutHyphenMessage = withOrWithoutHyphenMessage;
    this.shortDesc = shortMessage;
    setLocQualityIssueType(ITSIssueType.Misspelling);
    this.lang = lang;
    if (userConfig != null) {
      linguServices = userConfig.getLinguServices();
    } else {
      linguServices = null;
    }
  }

  @Override
  public RuleMatch[] match(AnalyzedSentence sentence) {
    List<RuleMatch> ruleMatches = new ArrayList<>();
    AnalyzedTokenReadings[] tokens = getSentenceWithImmunization(sentence).getTokensWithoutWhitespace();

    RuleMatch prevRuleMatch = null;
    ArrayDeque<AnalyzedTokenReadings> prevTokens = new ArrayDeque<>(MAX_TERMS);
    boolean containsDigits = false;
    int tokenLengthPlusMaxTerms = tokens.length + MAX_TERMS;
    for (int i = 0; i < tokenLengthPlusMaxTerms; i++) {
      AnalyzedTokenReadings token;
      // we need to extend the token list so we find matches at the end of the original list:
      if (i >= tokens.length) {
        token = new AnalyzedTokenReadings(new AnalyzedToken("", "", null), prevTokens.peek().getStartPos());
      } else {
        token = tokens[i];
      }
      if (i == 0) {
        addToQueue(token, prevTokens);
        continue;
      } else if (token.isImmunized()) {
        continue;
      }

      AnalyzedTokenReadings firstMatchToken = prevTokens.peek();
      List<String> stringsToCheck = new ArrayList<>();      // no hyphens spelling
      List<String> origStringsToCheck = new ArrayList<>();  // original upper/lowercase and hyphens spelling
      Hashtable<String, AnalyzedTokenReadings> stringToToken =
              getStringToTokenMap(prevTokens, stringsToCheck, origStringsToCheck);
      // iterate backwards over all potentially incorrect strings to make
      // sure we match longer strings first:
      for (int k = stringsToCheck.size()-1; k >= 0; k--) {
        String stringToCheck = stringsToCheck.get(k);
        String origStringToCheck = origStringsToCheck.get(k);
        String digitsRegexp = null;
        if (Stream.of(stringToCheck.split(" ")).anyMatch(s -> StringUtils.isNumeric(s))) {
            containsDigits = true;
        }
        CompoundRuleData crd = getCompoundRuleData();
        Set<String> incorrectCompounds = crd.getIncorrectCompounds();
        Set<String> dashSuggestion = crd.getDashSuggestion();
        if (incorrectCompounds.contains(stringToCheck) ||
            (containsDigits && incorrectCompounds.contains(digitsRegexp = stringToCheck.replaceAll("\\d+", "\\\\d+")))) {
          AnalyzedTokenReadings atr = stringToToken.get(stringToCheck);
          String msg = null;
          List<String> replacement = new ArrayList<>();
          if (dashSuggestion.contains(stringToCheck) && !origStringToCheck.contains(" ")) {
            // It is already joined
            break;
          }
          if (dashSuggestion.contains(stringToCheck) ||
              (containsDigits && incorrectCompounds.contains(digitsRegexp))) {
            replacement.add(origStringToCheck.replace(' ', '-'));
            msg = withHyphenMessage;
          }
          if (isNotAllUppercase(origStringToCheck) && crd.getJoinedSuggestion().contains(stringToCheck)) {
            replacement.add(mergeCompound(origStringToCheck, crd.getJoinedLowerCaseSuggestion().stream().anyMatch(s -> stringToCheck.contains(s))));
            msg = withoutHyphenMessage;
          }
          String[] parts = stringToCheck.split(" ");
          if (parts.length > 0 && parts[0].length() == 1) {
            replacement.clear();
            replacement.add(origStringToCheck.replace(' ', '-'));
            msg = withHyphenMessage;
          } else if (replacement.isEmpty() || replacement.size() == 2) {     // isEmpty shouldn't happen
            msg = withOrWithoutHyphenMessage;
          }
          replacement = filterReplacements(replacement,
            sentence.getText().substring(firstMatchToken.getStartPos(), atr.getEndPos()));
          if (replacement.isEmpty()) {
            break;
          }
          RuleMatch ruleMatch = new RuleMatch(this, sentence, firstMatchToken.getStartPos(), atr.getEndPos(), msg, shortDesc);
          ruleMatch.setSuggestedReplacements(replacement);
          // avoid duplicate matches:
          if (prevRuleMatch != null && prevRuleMatch.getFromPos() == ruleMatch.getFromPos()) {
            prevRuleMatch = ruleMatch;
            break;
          }
          prevRuleMatch = ruleMatch;
          ruleMatches.add(ruleMatch);
          break;
        }
      }
      addToQueue(token, prevTokens);
    }
    return toRuleMatchArray(ruleMatches);
  }

  protected List<String> filterReplacements(List<String> replacements, String original) {
    List<String> newReplacements = new ArrayList<String>();
    for (String replacement : replacements) {
      String newReplacement = replacement.replaceAll("\\-\\-+", "-");
      if (!newReplacement.equals(original) && isCorrectSpell(newReplacement)) {
        newReplacements.add(newReplacement);
      }
    }
    return newReplacements;
  }

  public Hashtable<String, AnalyzedTokenReadings> getStringToTokenMap(Queue<AnalyzedTokenReadings> prevTokens,
                                                                 List<String> stringsToCheck, List<String> origStringsToCheck) {
    String sb = new String();
    Hashtable<String, AnalyzedTokenReadings> stringToToken = new Hashtable<>();
    int j = 0;
    boolean isFirstSentStart = false;
    for (AnalyzedTokenReadings atr : prevTokens) {
      if (atr.isWhitespaceBefore()) {
        sb += ' ';
      }
      sb += atr.getToken();
      if (j == 0) {
        isFirstSentStart = atr.hasPosTag(JLanguageTool.SENTENCE_START_TAGNAME);
      }
      if (j >= 1 || (j == 0 && !isFirstSentStart)) {
        String stringToCheck = normalize(sb.toString());
        if (sentenceStartsWithUpperCase && isFirstSentStart) {
          stringToCheck = StringUtils.uncapitalize(stringToCheck);
        }
        stringsToCheck.add(stringToCheck);
        origStringsToCheck.add(sb.toString().trim());
        if (!stringToToken.containsKey(stringToCheck)) {
          stringToToken.put(stringToCheck, atr);
        }
      }
      j++;
    }
    return stringToToken;
  }

  private static String normalize(String inStr) {
    String str = inStr.trim();
    str = str.replace(" - ", " ");
    str = str.replace('-', ' ');
    str = str.replaceAll("\\s+", " ");
    return str;
  }

  private static boolean isNotAllUppercase(String str) {
    String[] parts = str.split(" ");
    for (String part : parts) {
      if (!"-".equals(part)) { // do not treat '-' as an upper-case word
        if (StringTools.isAllUppercase(part)) {
          return false;
        }
      }
    }
    return true;
  }

  public static String mergeCompound(String str, boolean uncapitalizeMidWords) {
    String[] stringParts = str.replaceAll("-", " ").split(" ");
    String sb = new String();
    sb += stringParts[0];
    int stringPartsLength = stringParts.length;
    for (int k = 1; k < stringPartsLength; k++) {

      sb += (uncapitalizeMidWords ? StringUtils.uncapitalize(stringParts[k]) : stringParts[k]);
    }
    return sb;
  }

  private static void addToQueue(AnalyzedTokenReadings token, ArrayDeque<AnalyzedTokenReadings> prevTokens) {
    if (prevTokens.size() == MAX_TERMS) {
      prevTokens.poll();
    }
    prevTokens.offer(token);
  }
  
  private boolean isCorrectSpell(String word) {
    if (linguServices == null) {
      return !isMisspelled(word);
    }
    return linguServices.isCorrectSpell(word, lang);
  }
  
  public boolean isMisspelled(String word) {
    return false;
  }

}
