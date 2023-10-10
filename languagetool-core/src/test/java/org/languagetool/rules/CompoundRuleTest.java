package org.languagetool.rules;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;

public class CompoundRuleTest {

  private AbstractCompoundRule rule;

  @Before
  public void setup() throws IOException {
    rule = new AbstractCompoundRule(null, null, null, "withHyphenMessage", "withoutHyphenMessage", "withOrWithoutHyphenMessage") {
      @Override
      public String getId() {
        return null;
      }

      @Override
      public String getDescription() {
        return null;
      }

      @Override
      public CompoundRuleData getCompoundRuleData() {
        CompoundRuleData compoundRuleData = new CompoundRuleData("");
        compoundRuleData.incorrectCompounds.add("hallo");
        compoundRuleData.incorrectCompounds.add("hello");
        compoundRuleData.incorrectCompounds.add("hullo");
        compoundRuleData.incorrectCompounds.add("hola");
        compoundRuleData.incorrectCompounds.add("hey");
        compoundRuleData.incorrectCompounds.add("hi");
        compoundRuleData.incorrectCompounds.add("moin");
        compoundRuleData.incorrectCompounds.add("token");
        return compoundRuleData;
      }
    };
  }

  @Test
  public void testGetStringToTokenMap() {
    Queue<AnalyzedTokenReadings> q = new ArrayDeque<>();
    q.add(new AnalyzedTokenReadings(new AnalyzedToken("word", null, "lemma")));
    q.add(new AnalyzedTokenReadings(new AnalyzedToken("word", null, "lemma")));
    q.add(new AnalyzedTokenReadings(new AnalyzedToken("word", null, "lemma")));
    q.add(new AnalyzedTokenReadings(new AnalyzedToken("word", null, "lemma")));
    q.add(new AnalyzedTokenReadings(new AnalyzedToken("word", null, "lemma")));
    q.add(new AnalyzedTokenReadings(new AnalyzedToken("word", null, "lemma")));
    List<String> stringsToCheck = new ArrayList<String>(Arrays.asList("hallo", "hello", "bla", "hallo", "hello", "bla", "hallo", "hello", "bla", "hallo", "hello", "bla", "hallo", "hello", "bla"));
    List<String> origStringsToCheck = new ArrayList<String>(Arrays.asList("hallo", "hello", "bla", "hallo", "hello", "bla", "eeh", "ooh", "eeh", "ooh", "eeh", "ooh"));
    rule.getStringToTokenMap(q, stringsToCheck, origStringsToCheck);
  }

  @Test
  public void testMergeCompound() {
    String str = "hallo hier kommt ein text rein aber ohne punkt und komma und großschreibung damit das mal klar ist ich habe keine zeit dafür okay okay gut dass wir drüber gesprochen haben nicht wahr ja finde ich auch okay das wars auch schon schönen tag noch tschüss";
    rule.mergeCompound(str, false);
  }

  @Test
  public void testMatch() throws IOException {
    rule.match(new AnalyzedSentence(
      new AnalyzedTokenReadings[]{
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
        new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),
      }
    ));
  }
}
