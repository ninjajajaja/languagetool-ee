package org.languagetool.rules.ngrams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Test;

public class LanguageModelUtilsTest extends TestCase {

  @Test
  public void testGetContext() {
    List<String> tokens = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
    LanguageModelUtils.getContext("k", tokens, tokens, 10, 10, String::isEmpty, "z");
  }
}