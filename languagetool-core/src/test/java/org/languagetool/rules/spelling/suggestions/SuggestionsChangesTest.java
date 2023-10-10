package org.languagetool.rules.spelling.suggestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class SuggestionsChangesTest extends TestCase {

  public void testRun() {
    SuggestionChangesTestConfig sctc = new SuggestionChangesTestConfig();
    sctc.experiments = new ArrayList<>();
    sctc.datasets = Arrays.asList(new SuggestionChangesDataset("datasetname", "path", "type", 0.5f, false, false));
    for (int i = 0; i < 5; i++) {
      Map<String, List<Object>> m = new Hashtable<>();
      for (int j = 0; j < 3; j++) m.put("key " + j, Arrays.asList("B", "L", "A", 42));
      sctc.experiments.add(new SuggestionChangesExperimentRuns("name " + i, m));
    }
    SuggestionsChanges.init(sctc, null);
    SuggestionsChanges sc = SuggestionsChanges.getInstance();
    sc.currentExperiment = null;
    assertNull(sc.currentExperiment);
    assertNotNull(sc.config);
    assertNotNull(sc.experiments);
  }
}