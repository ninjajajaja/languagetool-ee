package org.languagetool.rules;

import java.io.IOException;
import junit.framework.TestCase;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;

public class WhiteSpaceAtBeginOfParagraphTest extends TestCase {

  WhiteSpaceAtBeginOfParagraph rule;

  public void setUp() throws Exception {
    rule = new WhiteSpaceAtBeginOfParagraph(null, true);
  }

  public void testMatch() throws IOException {
    AnalyzedTokenReadings as = new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma"));
    as.isWhitespace = true;
    rule.match(new AnalyzedSentence(new AnalyzedTokenReadings[]{new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma")),as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,as,new AnalyzedTokenReadings(new AnalyzedToken("token", "posTag", "lemma"))}));
  }
}