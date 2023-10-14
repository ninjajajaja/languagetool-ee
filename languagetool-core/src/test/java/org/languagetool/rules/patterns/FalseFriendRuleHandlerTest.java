package org.languagetool.rules.patterns;

import junit.framework.TestCase;
import org.languagetool.Language;
import org.languagetool.language.Demo;

public class FalseFriendRuleHandlerTest extends TestCase {

  public void testGetSuggestionMap() {

    FalseFriendRuleHandler ffrh = new FalseFriendRuleHandler(null, null, "hint");
    assertNotNull(ffrh.suggestionMap);

    ffrh.inToken = true;
    ffrh.inPattern = true;
    ffrh.elements = new String();
    for (int i = 0; i < 50; i++) {
      ffrh.characters(new char[]{ 'a', 'b', 'c', 'd'}, 0, 3);
    }
  }
}