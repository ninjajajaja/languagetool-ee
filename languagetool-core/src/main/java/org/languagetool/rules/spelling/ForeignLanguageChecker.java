/*
 * LanguageTool, a natural language style checker
 * Copyright (c) 2023.  Stefan Viol (https://stevio.de)
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 *  USA
 */

package org.languagetool.rules.spelling;

import lombok.extern.slf4j.Slf4j;
import org.languagetool.AnalyzedSentence;
import org.languagetool.DetectedLanguage;
import org.languagetool.Language;
import org.languagetool.language.identifier.LanguageIdentifier;
import org.languagetool.language.identifier.LanguageIdentifierService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ForeignLanguageChecker {

  private static final float ERROR_THRESHOLD = 0.45f;
  private static final int MIN_SENTENCE_THRESHOLD = 3;
  private static final float MIN_DETECTION_CONFIDENCE_FASTTEXT = 0.85f;
  private static final float MIN_DETECTION_CONFIDENCE_NGRAM = 0.350f;
  private ForeignLanguageChecker() {
  }

  public static String check(int matchesSoFar, Language language, AnalyzedSentence sentence, List<String> preferredLanguages, List<String> noopsLanguages) throws IOException {
    
    long sentenceLength = Arrays.stream(sentence.getTokensWithoutWhitespace()).filter(k -> !k.isNonWord()).count() - 1;  // -1 for the SENT_START token
    
    long timeStart = System.currentTimeMillis();
    float errorRatio = (float) matchesSoFar / sentenceLength;
    if (preferredLanguages.size() == 1 && preferredLanguages.get(0).equals(language.getShortCode())) {
      log.trace("Do not start language detection as the user only has one preferred language.");
      return null;
    }
    if (sentenceLength >= MIN_SENTENCE_THRESHOLD && errorRatio >= ERROR_THRESHOLD) {
      LanguageIdentifier langIdent = LanguageIdentifierService.INSTANCE.getInitialized();
      if (langIdent != null) {
        DetectedLanguage langDetectResults = langIdent.detectLanguage(sentence.getText(), noopsLanguages, preferredLanguages);
        //for now, we just use the result if also in preferredLanguages to prevent false positive
        if (langDetectResults != null) {
          long timeEnd = System.currentTimeMillis();
          Language detectedLanguage = langDetectResults.getDetectedLanguage();
          if (detectedLanguage != null && !detectedLanguage.getShortCode().equals(language.getShortCode()) && preferredLanguages.contains(detectedLanguage.getShortCode())) {
            //DO NEVER enable traceLevel for this class in production @LanguageTool
            log.trace("Time to find the correct language of the sentence: {} seconds", (timeEnd - timeStart) / 1000f);
            log.trace("Found '{}' sentence in '{}' text: '{}' with confidence {} from source '{}'",
                    detectedLanguage.getShortCode(),
                    language.getShortCode(),
                    sentence.getText(),
                    langDetectResults.getDetectionConfidence(),
                    langDetectResults.getDetectionSource());
            float detectionConfidence = langDetectResults.getDetectionConfidence();
            String detectionSource = langDetectResults.getDetectionSource();
            if (detectionSource.contains("fasttext") && detectionConfidence >= MIN_DETECTION_CONFIDENCE_FASTTEXT) {
              return detectedLanguage.getShortCode();
            } else if (detectionSource.contains("ngram") && detectionConfidence >= MIN_DETECTION_CONFIDENCE_NGRAM) {
              return detectedLanguage.getShortCode();
            } else {
              log.trace("Drop langDetectResults as MIN_DETECTION_CONFIDENCE was not reached: {} for sentence: {}", langDetectResults.getDetectionConfidence(), sentence.getText());
            }
          }
        }
      }
    }
    return null;
  }
}
