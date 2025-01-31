/* LanguageTool, a natural language style checker
 * Copyright (C) 2015 Daniel Naber (http://www.danielnaber.de)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.rules;

import gnu.trove.THashSet;
import org.languagetool.JLanguageTool;

import java.io.IOException;
import java.util.*;

/**
 * Data about words that are compounds and should thus not be written
 * as separate words.
 * @since 3.0
 */
public class CompoundRuleData {

  public final THashSet<String> incorrectCompounds = new THashSet<>();
  private final THashSet<String> joinedSuggestion = new THashSet<>();
  private final THashSet<String> joinedLowerCaseSuggestion = new THashSet<>();
  private final THashSet<String> dashSuggestion = new THashSet<>();
  private final LineExpander expander;

  public CompoundRuleData(String path) {
    this(new String[] {path});
  }

  public CompoundRuleData(String... paths) {
    this(null, paths);
  }

  public CompoundRuleData(LineExpander expander, String... paths) {
    this.expander = expander;
//    for (String path : paths) {
//      try {
//        loadCompoundFile(path);
//      } catch (IOException e) {
//        throw new RuntimeException("Could not load compound data from " + path, e);
//      }
//    }
  }

  public THashSet<String> getIncorrectCompounds() {
    return incorrectCompounds;
  }

  public THashSet<String> getJoinedSuggestion() {
    return joinedSuggestion;
  }

  public THashSet<String> getDashSuggestion() {
    return dashSuggestion;
  }

  public THashSet<String> getJoinedLowerCaseSuggestion() {
	return joinedLowerCaseSuggestion;
  }

  private void loadCompoundFile(String path) {
    List<String> lines = Arrays.asList(path.split(" "));  //JLanguageTool.getDataBroker().getFromResourceDirAsLines(path);
    for (String line : lines) {
      if (line.isEmpty() || line.startsWith("#")) {
        continue;     // ignore comments
      }
      line = line.replaceFirst("#.*$", "").trim();
      List<String> expandedLines = new ArrayList<>();
      if (expander != null) {
        expandedLines = expander.expandLine(line);
      } else {
        expandedLines.add(line);
      }
      for (String expLine : expandedLines) {
        expLine = expLine.replace('-', ' ');  // the set contains the incorrect spellings, i.e. the ones without hyphen
        //validateLine(path, expLine);
        if (expLine.endsWith("+")) {
          expLine = removeLastCharacter(expLine);
          joinedSuggestion.add(expLine);
        } else if (expLine.endsWith("*")) {
          expLine = removeLastCharacter(expLine);
          dashSuggestion.add(expLine);
        } else if (expLine.endsWith("?")) { // github issue #779
          expLine = removeLastCharacter(expLine);
          joinedSuggestion.add(expLine);
          joinedLowerCaseSuggestion.add(expLine);
        } else if (expLine.endsWith("$")) { // github issue #779
          expLine = removeLastCharacter(expLine);
          joinedSuggestion.add(expLine);
          dashSuggestion.add(expLine);
          joinedLowerCaseSuggestion.add(expLine);
        } else {
          joinedSuggestion.add(expLine);
          dashSuggestion.add(expLine);
        }
        incorrectCompounds.add(expLine);
      }
    }
  }

  private void validateLine(String path, String line) {
    String[] parts = line.split(" ");
    if (parts.length == 1) {
      throw new IllegalArgumentException("Not a compound in file " + path + ": " + line);
    }
    if (parts.length > AbstractCompoundRule.MAX_TERMS) {
      throw new IllegalArgumentException("Too many compound parts in file " + path + ": " + line + ", maximum allowed: " + AbstractCompoundRule.MAX_TERMS);
    }
    if (incorrectCompounds.contains(line.toLowerCase())) {
      throw new IllegalArgumentException("Duplicated word in file " + path + ": " + line);
    }
  }

  private static String removeLastCharacter(String str) {
    return str.substring(0, str.length() - 1);
  }

}
