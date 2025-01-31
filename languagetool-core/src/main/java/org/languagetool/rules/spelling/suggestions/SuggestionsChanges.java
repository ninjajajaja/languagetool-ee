/*
 *  LanguageTool, a natural language style checker
 *  * Copyright (C) 2018 Fabian Richter
 *  *
 *  * This library is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU Lesser General Public
 *  * License as published by the Free Software Foundation; either
 *  * version 2.1 of the License, or (at your option) any later version.
 *  *
 *  * This library is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  * Lesser General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU Lesser General Public
 *  * License along with this library; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 *  * USA
 *
 */

package org.languagetool.rules.spelling.suggestions;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

/**
 * Helper class for SuggestionChangesTest, tracks experiment configuration and results
 * Rules should use getInstance() != null -&gt; getInstance().getCurrentExperiment() in constructors to fetch relevant parameters
 * Use isRunningExperiment if no parameters are needed
 */
public class SuggestionsChanges {
  private static SuggestionsChanges instance;
  public final SuggestionChangesTestConfig config;
  public final List<SuggestionChangesExperiment> experiments;

  private final Hashtable<SuggestionChangesExperiment, Integer> correctSuggestions = new Hashtable<>();
  private final Hashtable<SuggestionChangesExperiment, Integer> notFoundSuggestions = new Hashtable<>();
  private final Hashtable<SuggestionChangesExperiment, Integer> suggestionPosSum = new Hashtable<>();
  private final Hashtable<SuggestionChangesExperiment, Integer> textSize = new Hashtable<>();
  private final Hashtable<SuggestionChangesExperiment, Long> computationTime = new Hashtable<>();
  private final Hashtable<SuggestionChangesExperiment, Integer> numSamples = new Hashtable<>();

  private final Hashtable<Pair<SuggestionChangesExperiment, SuggestionChangesDataset>, Integer>
    datasetCorrectSuggestions = new Hashtable<>();
  private final Hashtable<Pair<SuggestionChangesExperiment, SuggestionChangesDataset>, Integer>
    datasetNotFoundSuggestions = new Hashtable<>();
  private final Hashtable<Pair<SuggestionChangesExperiment, SuggestionChangesDataset>, Integer>
    datasetSuggestionPosSum = new Hashtable<>();
  private final Hashtable<Pair<SuggestionChangesExperiment, SuggestionChangesDataset>, Integer> datasetNumSamples = new Hashtable<>();
  private final Hashtable<Pair<SuggestionChangesExperiment, SuggestionChangesDataset>, Integer> datasetTextSize = new Hashtable<>();
  private final Hashtable<Pair<SuggestionChangesExperiment, SuggestionChangesDataset>, Long> datasetComputationTime = new Hashtable<>();

  public SuggestionChangesExperiment currentExperiment = null;

  SuggestionsChanges(SuggestionChangesTestConfig config, BufferedWriter reportWriter) {
    this.config = config;
    experiments = generateExperiments(config.experiments);

    Runtime.getRuntime().addShutdownHook(new Thread(new Report(reportWriter)));
  }

  /**
   * null if nothing is configured, i.e. in most normal use cases
   */
  @Nullable
  public static SuggestionsChanges getInstance() {
    return instance;
  }

  static void init(@NotNull SuggestionChangesTestConfig config, @Nullable BufferedWriter reportWriter) {
    instance = new SuggestionsChanges(config, reportWriter);
  }

  private static List<Hashtable<String, Object>> gridsearch(SortedMap<String, List<Object>> grid, List<Hashtable<String, Object>> current) {
    if (grid.isEmpty()) { // recursion exit
      return current;
    }

    String name = grid.lastKey();
    List<Object> params = grid.get(name);
    List<Hashtable<String, Object>> result = new ArrayList<>();

    if (current.isEmpty()) {
      for (Object value : params) {
        Hashtable newEntry = new Hashtable();
        newEntry.putAll(Collections.singletonMap(name, value));
        result.add(newEntry);
      }
    } else {
      for (Hashtable<String, Object> entry : current) {
        for (Object value : params) {
          Hashtable<String, Object> modified = new Hashtable<>(entry);
          modified.put(name, value);
          result.add(modified);
        }
      }
    }

    return gridsearch(grid.headMap(name), result);
  }

  private static List<SuggestionChangesExperiment> generateExperiments(List<SuggestionChangesExperimentRuns> experimentSpecs) {
    List<SuggestionChangesExperiment> experiments = new ArrayList<>();
    for (SuggestionChangesExperimentRuns spec : experimentSpecs) {

      if (spec.parameters == null) {
        experiments.add(new SuggestionChangesExperiment(spec.name, new Hashtable<>()));
      } else {
        SortedMap<String, List<Object>> params = new TreeMap<>(spec.parameters);
        List<Hashtable<String, Object>> combinations = gridsearch(params, Collections.emptyList());

        for (Hashtable<String, Object> settings : combinations) {
          experiments.add(new SuggestionChangesExperiment(spec.name, settings));
        }
      }
    }
    return experiments;
  }

  public static boolean isRunningExperiment(String name) {
    if (getInstance() == null) {
      return false;
    }
    SuggestionChangesExperiment experiment = getInstance().currentExperiment;
    return experiment != null && experiment.name.equals(name);
  }

  public void trackExperimentResult(Pair<SuggestionChangesExperiment, SuggestionChangesDataset> source,
                                    int position, int resultTextSize, long resultComputationTime) {
    numSamples.compute(source.getKey(), (ex, value) -> value == null ? 1 : value + 1);
    datasetNumSamples.compute(source, (ex, value) -> value == null ? 1 : value + 1);

    textSize.compute(source.getKey(), (ex, value) ->
      value == null ? resultTextSize : value + resultTextSize);
    datasetTextSize.compute(source, (ex, value) ->
      value == null ? resultTextSize : value + resultTextSize);

    computationTime.compute(source.getKey(), (ex, value) ->
      value == null ? resultComputationTime : value + resultComputationTime);
    datasetComputationTime.compute(source, (ex, value) ->
      value == null ? resultComputationTime : value + resultComputationTime);

    if (position == 0) {
      correctSuggestions.compute(source.getKey(), (ex, value) -> value == null ? 1 : value + 1);
      datasetCorrectSuggestions.compute(source, (ex, value) -> value == null ? 1 : value + 1);
    }
    if (position == -1) {
      notFoundSuggestions.compute(source.getKey(), (ex, value) -> value == null ? 1 : value + 1);
      datasetNotFoundSuggestions.compute(source, (ex, value) -> value == null ? 1 : value + 1);
    } else {
      suggestionPosSum.compute(source.getKey(), (ex, value) -> value == null ? position : value + position);
      datasetSuggestionPosSum.compute(source, (ex, value) -> value == null ? position : value + position);
    }
  }

  private class Report implements Runnable {

    private final BufferedWriter reportWriter;

    Report(BufferedWriter reportWriter) {
      this.reportWriter = reportWriter;
    }

    @Override
    public void run() {
      //if (reportWriter == null) {
      //  return;
      //}
      //try {
        String report = new String();
        report += "Overall report:\n\n";

        SuggestionChangesExperiment best = null;
        int bestId = -1;
        double bestAccuracy = 0.0;

        int experimentId = 0;
        for (SuggestionChangesExperiment experiment : experiments) {
          experimentId++;
          int correct = correctSuggestions.getOrDefault(experiment, 0);
          int score = suggestionPosSum.getOrDefault(experiment, 0);
          int notFound = notFoundSuggestions.getOrDefault(experiment, 0);
          int total = numSamples.getOrDefault(experiment, 0);
          double accuracy = (double) correct / total * 100.0;
          double speed = (double) textSize.getOrDefault(experiment, 0) /
            computationTime.getOrDefault(experiment, 0L) * 1000.0;
          if (accuracy > bestAccuracy) {
            best = experiment;
            bestAccuracy = accuracy;
            bestId = experimentId;
          }
          report += (String.format("Experiment #%d (%s): %d / %d correct suggestions -> %f%% accuracy;" +
            " score (less = better): %d; not found: %d; processed %f chars/second.%n",
            experimentId, experiment, correct, total, accuracy, score, notFound, speed));
        }

        report += (String.format("%nBest experiment: #%d (%s) @ %f%% accuracy%n", bestId, best, bestAccuracy));

        for (SuggestionChangesDataset dataset : config.datasets) {
          report += (String.format("%nReport for dataset: %s%n", dataset.name));
          best = null;
          bestAccuracy = 0f;
          bestId = -1;

          experimentId = 0;
          for (SuggestionChangesExperiment experiment : experiments) {
            experimentId++;
            Pair<SuggestionChangesExperiment, SuggestionChangesDataset> source = Pair.of(experiment, dataset);
            int correct = datasetCorrectSuggestions.getOrDefault(source, 0);
            int score = datasetSuggestionPosSum.getOrDefault(source, 0);
            int notFound = datasetNotFoundSuggestions.getOrDefault(source, 0);
            int total = datasetNumSamples.getOrDefault(source, 0);
            double accuracy = (double) correct / total * 100.0;
            double speed = (double) datasetTextSize.getOrDefault(source, 0) /
              datasetComputationTime.getOrDefault(source, 0L) * 1000.0;
            if (accuracy > bestAccuracy) {
              best = experiment;
              bestAccuracy = accuracy;
              bestId = experimentId;
            }
            report += (String.format("Experiment #%d (%s): %d / %d correct suggestions-> %f%% accuracy;" +
              " score (less = better): %d; not found: %d; processed %f chars/second.%n",
              experimentId, experiment, correct, total, accuracy, score, notFound, speed));
          }
          report += (String.format("%nBest experiment: #%d (%s) @ %f%% accuracy%n", bestId, best, bestAccuracy));
        }
        System.out.println(report);
        //reportWriter.write(report.toString());
        //reportWriter.close();
        // } catch (IOException e) {
        //throw new RuntimeException(e);
      //}
    }
  }


}
