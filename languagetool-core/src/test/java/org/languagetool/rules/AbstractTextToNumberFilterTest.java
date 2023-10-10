package org.languagetool.rules;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import junit.framework.TestCase;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;

public class AbstractTextToNumberFilterTest extends TestCase {

  AbstractTextToNumberFilter filter;

  public void setUp() throws Exception {
    filter = new AbstractTextToNumberFilter() {
      @Override
      protected boolean isComma(String s) {
        return false;
      }

      @Override
      protected boolean isPercentage(AnalyzedTokenReadings[] patternTokens, int i) {
        return false;
      }
    };
    filter.numbers.put("cero", (float) 0);
    filter.numbers.put("medio", (float) 0.5);
    filter.numbers.put("un", (float) 1);
    filter.numbers.put("uno", (float) 1);
    filter.numbers.put("una", (float) 1);
    filter.numbers.put("dos", (float) 2);
    filter.numbers.put("tres", (float) 3);
    filter.numbers.put("cuatro", (float) 4);
    filter.numbers.put("cinco", (float) 5);
    filter.numbers.put("seis", (float) 6);
    filter.numbers.put("siete", (float) 7);
    filter.numbers.put("ocho", (float) 8);
    filter.numbers.put("nueve", (float) 9);
    filter.numbers.put("diez", (float) 10);
    filter.numbers.put("once", (float) 11);
    filter.numbers.put("doce", (float) 12);
    filter.numbers.put("trece", (float) 13);
    filter.numbers.put("catorce", (float) 14);
    filter.numbers.put("quince", (float) 15);
    filter.numbers.put("dieciséis", (float) 16);
    filter.numbers.put("diecisiete", (float) 17);
    filter.numbers.put("dieciocho", (float) 18);
    filter.numbers.put("diecinueve", (float) 19);
    filter.numbers.put("veinte", (float) 20);
    filter.numbers.put("veintiuno", (float) 21);
    filter.numbers.put("veintidós", (float) 22);
    filter.numbers.put("veintitrés", (float) 23);
    filter.numbers.put("veinticuatro", (float) 24);
    filter.numbers.put("veinticinco", (float) 25);
    filter.numbers.put("veintiséis", (float) 26);
    filter.numbers.put("veintisiete", (float) 27);
    filter.numbers.put("veintiocho", (float) 28);
    filter.numbers.put("veintinueve", (float) 29);
    filter.numbers.put("treinta", (float) 30);
    filter.numbers.put("cuarenta", (float) 40);
    filter.numbers.put("cincuenta", (float) 50);
    filter.numbers.put("sesenta", (float) 60);
    filter.numbers.put("setenta", (float) 70);
    filter.numbers.put("ochenta", (float) 80);
    filter.numbers.put("noventa", (float) 90);
    filter.numbers.put("cien", (float) 100);
    filter.numbers.put("ciento", (float) 100);
    filter.numbers.put("doscientos", (float) 200);
    filter.numbers.put("trescientos", (float) 300);
    filter.numbers.put("cuatrocientos", (float) 400);
    filter.numbers.put("quinientos", (float) 500);
    filter.numbers.put("seiscientos", (float) 600);
    filter.numbers.put("setecientos", (float) 700);
    filter.numbers.put("ochocientos", (float) 800);
    filter.numbers.put("novecientos", (float) 900);
    filter.numbers.put("doscientas", (float) 200);
    filter.numbers.put("trescientas", (float) 300);
    filter.numbers.put("cuatrocientas", (float) 400);
    filter.numbers.put("quinientas", (float) 500);
    filter.numbers.put("seiscientas", (float) 600);
    filter.numbers.put("setecientas", (float) 700);
    filter.numbers.put("ochocientas", (float) 800);
    filter.numbers.put("novecientas", (float) 900);
    filter.multipliers.put("mil", (float) 1000);
    filter.multipliers.put("millón", (float) 1000000);
    filter.multipliers.put("millones", (float) 1000000);
    filter.multipliers.put("billón", (float) 10E12);
    filter.multipliers.put("billones", (float) 10E12);
    filter.multipliers.put("trillón", (float) 10E18);
    filter.multipliers.put("trillones", (float) 10E18);
  }

  public void testAcceptRuleMatch() throws IOException {
    RuleMatch ruleMatch = new RuleMatch(new FakeRule(), 0, 10, "message");
    Hashtable map = new Hashtable<String, String>();
    filter.acceptRuleMatch(ruleMatch, map, 0, new AnalyzedTokenReadings[]{
      new AnalyzedTokenReadings(new AnalyzedToken("cien", "posTag", "lemma"), 0),
      new AnalyzedTokenReadings(new AnalyzedToken("mil", "posTag", "lemma"), 1),
      new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma"), 2),
      new AnalyzedTokenReadings(new AnalyzedToken("cien", "posTag", "lemma"), 3),
      new AnalyzedTokenReadings(new AnalyzedToken("mil", "posTag", "lemma"), 4),
      new AnalyzedTokenReadings(new AnalyzedToken("trillón", "posTag", "lemma"), 5),
      new AnalyzedTokenReadings(new AnalyzedToken("billones", "posTag", "lemma"), 6),
      new AnalyzedTokenReadings(new AnalyzedToken("seiscientas", "posTag", "lemma"), 7),
      new AnalyzedTokenReadings(new AnalyzedToken("veintitrés", "posTag", "lemma"), 8),
      new AnalyzedTokenReadings(new AnalyzedToken("doscientas", "posTag", "lemma"), 9),
      new AnalyzedTokenReadings(new AnalyzedToken("doscientas", "posTag", "lemma"), 10),
      new AnalyzedTokenReadings(new AnalyzedToken("cien", "posTag", "lemma"), 0),
      new AnalyzedTokenReadings(new AnalyzedToken("mil", "posTag", "lemma"), 1),
      new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma"), 2),
      new AnalyzedTokenReadings(new AnalyzedToken("cien", "posTag", "lemma"), 3),
      new AnalyzedTokenReadings(new AnalyzedToken("mil", "posTag", "lemma"), 4),
      new AnalyzedTokenReadings(new AnalyzedToken("trillón", "posTag", "lemma"), 5),
      new AnalyzedTokenReadings(new AnalyzedToken("billones", "posTag", "lemma"), 6),
      new AnalyzedTokenReadings(new AnalyzedToken("seiscientas", "posTag", "lemma"), 7),
      new AnalyzedTokenReadings(new AnalyzedToken("veintitrés", "posTag", "lemma"), 8),
      new AnalyzedTokenReadings(new AnalyzedToken("doscientas", "posTag", "lemma"), 9),
      new AnalyzedTokenReadings(new AnalyzedToken("doscientas", "posTag", "lemma"), 10)
    });

  }
}