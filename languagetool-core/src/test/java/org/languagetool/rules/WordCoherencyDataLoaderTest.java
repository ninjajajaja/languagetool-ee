package org.languagetool.rules;

import java.io.IOException;
import junit.framework.TestCase;

public class WordCoherencyDataLoaderTest extends TestCase {

  WordCoherencyDataLoader loader;

  public void setUp() throws Exception {
    loader = new WordCoherencyDataLoader();
  }

  public void testLoadWords() throws IOException {
    loader.loadWords("a;b a;b a;b a;b a;b a;b a;b a;b a;b a;b a;b a;b a;b a;b a;b a;b a;b b;c b;c b;c b;c b;c b;c b;c b;c b;c b;c b;c b;c b;c b;c c;d c;d c;d c;d c;d c;d c;d c;d c;d c;d c;d c;d c;d c;d c;d d;a d;a d;a");
  }
}