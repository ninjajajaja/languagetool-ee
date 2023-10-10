package org.languagetool.rules.spelling.symspell.implementation;

import java.util.Hashtable;
import junit.framework.TestCase;

public class SuggestionStageTest extends TestCase {

  public void testAdd() {

    SuggestionStage suggestionStage = new SuggestionStage(100);
    for (int i = 0; i < 100; i++) {
      suggestionStage.add(i, "sugg" + i);
    }
    Hashtable pd = new Hashtable();
    for (int i = 0; i < 50; i++) pd.put(i, new String[]{});
    suggestionStage.commitTo(pd);

  }
}