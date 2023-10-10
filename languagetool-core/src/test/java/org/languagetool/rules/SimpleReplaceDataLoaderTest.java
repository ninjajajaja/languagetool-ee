package org.languagetool.rules;

import junit.framework.TestCase;

public class SimpleReplaceDataLoaderTest extends TestCase {

  SimpleReplaceDataLoader simpleReplaceDataLoader;

    public void setUp() throws Exception {
        simpleReplaceDataLoader = new SimpleReplaceDataLoader();
    }

    public void testLoadWords() {
      simpleReplaceDataLoader.loadWords("path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path path");
    }
}