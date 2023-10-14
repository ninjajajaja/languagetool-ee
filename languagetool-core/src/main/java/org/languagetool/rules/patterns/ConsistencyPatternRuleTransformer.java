/* LanguageTool, a natural language style checker 
 * Copyright (C) 2023 Fabian Richter
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

import org.languagetool.AnalyzedSentence;
import org.languagetool.Language;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.SameRuleGroupFilter;
import org.languagetool.rules.TextLevelRule;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ConsistencyPatternRuleTransformer implements PatternRuleTransformer {

  protected final Language transformerLanguage;

  public ConsistencyPatternRuleTransformer(Language lang) {
    transformerLanguage = lang;
  }

  /**
   * Wrapper for loaded {@link AbstractPatternRule} instances to act as text-level rules
   */
  public class ConsistencyPatternRule extends TextLevelRule {

    protected final Language ruleLanguage;

    ConsistencyPatternRule(List<AbstractPatternRule> rules, Language lang) {
      this.rules = Collections.unmodifiableList(rules);
      this.ruleLanguage = lang;
      setPremium(rules.stream().anyMatch(r -> r.isPremium()));
    }

    private final List<AbstractPatternRule> rules;

    public List<AbstractPatternRule> getWrappedRules() {
      return rules;
    }

    @Override
    public String getId() {
      return getMainRuleId(rules.get(0).getId());
    }

    @Override
    public String getDescription() {
      return rules.get(0).getDescription();
    }
    
    @Override
    public RuleMatch[] match(List<AnalyzedSentence> sentences) throws IOException {

      Hashtable<String, Integer> countFeatures  = new Hashtable<>();
      int offsetChars = 0;
      List<RuleMatch> matches = new ArrayList<>();
      for (AnalyzedSentence s : sentences) {
        List<RuleMatch> sentenceMatches = new ArrayList<>();
        for (AbstractPatternRule rule : rules) {
          RuleMatch[] ruleMatches = rule.match(s);
          sentenceMatches.addAll(Arrays.asList(ruleMatches));
        }
        sentenceMatches = new SameRuleGroupFilter().filter(sentenceMatches);
        // we need to adjust offsets since each pattern rule returns offsets relative to the sentence, not text
        List<RuleMatch> adjustedSentenceMatches = new ArrayList<>();
        for (RuleMatch rm : sentenceMatches) {
          int fromPos = rm.getFromPos() + offsetChars;
          int toPos = rm.getToPos() + offsetChars;
          rm.setOffsetPosition(fromPos, toPos);
          adjustedSentenceMatches.add(rm);
        }
        matches.addAll(adjustedSentenceMatches);
        offsetChars += s.getText().length();
      }
      List<RuleMatch> resultMatches = new ArrayList<>();
      // count occurrences of features
      for (RuleMatch rm : matches) {
        String feature = getFeature(rm.rule.getId());
        countFeatures.put(feature, countFeatures.getOrDefault(feature,0) + 1);
      }
      if (countFeatures.size()<2) {
        // there is no inconsistency
        return resultMatches.toArray(new RuleMatch[0]);
      }
      if (countFeatures.size()>2) {
        // to be done
        return resultMatches.toArray(new RuleMatch[0]);
      }
      ArrayList<String> featuresToKeep = new ArrayList<>();
      ArrayList<String> keysList = new ArrayList<>(countFeatures.keySet());
      if (countFeatures.get(keysList.get(0)) == countFeatures.get(keysList.get(1))) {
        featuresToKeep.add(keysList.get(1));
        featuresToKeep.add(keysList.get(0));
      } else if (countFeatures.get(keysList.get(0)) > countFeatures.get(keysList.get(1))) {
        featuresToKeep.add(keysList.get(1));
      } else {
        featuresToKeep.add(keysList.get(0));
      }
      for (RuleMatch rm : matches) {
        if (featuresToKeep.contains(getFeature(rm.rule.getId()))) {
          resultMatches.add(rm);
        }
      }
      return resultMatches.toArray(new RuleMatch[0]);
    }

    @Override
    public int minToCheckParagraph() {
      // TODO: what should we use here? calculate based on min_prev_matches?
      return 0;
    }

    @Override
    public boolean supportsLanguage(Language language) {
      return language.equalsConsiderVariantsIfSpecified(this.ruleLanguage);
    }

  }

  @Override
  public TransformedRules apply(List<AbstractPatternRule> patternRules) {
    List<AbstractPatternRule> remaining = new ArrayList<>();
    Hashtable<String, List<AbstractPatternRule>> toTransform = new Hashtable<>();
    // rule id conventions:
    // PREFIX_GROUPOFRULES_FEATURE
    for (AbstractPatternRule abstractPatternRule : patternRules) {
      String ruleId = abstractPatternRule.getId();
      if (ruleId.startsWith(abstractPatternRule.getLanguage().getConsistencyRulePrefix())) {
        toTransform.compute(getMainRuleId(ruleId), (id, rules) -> {
          if (rules == null) {
            return new ArrayList<>(Collections.singletonList(abstractPatternRule));
          } else {
            rules.add(abstractPatternRule);
            return rules;
          }
        });
      } else {
        remaining.add(abstractPatternRule);
      }
    }
    List<Rule> transformed = toTransform.values().stream()
      .map(group -> new ConsistencyPatternRule(group, transformerLanguage))
      .collect(Collectors.toList());

    return new TransformedRules(remaining, transformed);
  }

  private static String getMainRuleId(String originalId) {
    String[] parts =  originalId.split("_");
    return parts[0] + "_" + parts[1];
  }

  private static String getFeature(String originalId) {
    String[] parts =  originalId.split("_");
    return parts[2];
  }

 }
