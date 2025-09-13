package com.example.taskmanager.service;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SpeechService {

  /**
   * Transcribes base64 audio bytes (PCM16 or LINEAR16) or a GCS URI.
   * Returns best-guess transcription (first result alternative).
   */
  public String transcribeFromBase64(byte[] audioBytes, String languageCode) throws IOException {
    try (SpeechClient speechClient = SpeechClient.create()) {
      RecognitionConfig config = RecognitionConfig.newBuilder()
          .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16) // assume linear16; adjust as needed
          .setLanguageCode(languageCode)
          .setSampleRateHertz(16000)
          .build();

      RecognitionAudio audio = RecognitionAudio.newBuilder()
          .setContent(ByteString.copyFrom(audioBytes))
          .build();

      RecognizeResponse response = speechClient.recognize(config, audio);
      List<SpeechRecognitionResult> results = response.getResultsList();
      if (results.isEmpty()) return "";
      return results.get(0).getAlternativesList().get(0).getTranscript();
    }
  }

  public String transcribeFromGcsUri(String gcsUri, String languageCode) throws IOException {
    try (SpeechClient speechClient = SpeechClient.create()) {
      RecognitionConfig config = RecognitionConfig.newBuilder()
          .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
          .setLanguageCode(languageCode)
          .setSampleRateHertz(16000)
          .build();

      RecognitionAudio audio = RecognitionAudio.newBuilder()
          .setUri(gcsUri)
          .build();

      RecognizeResponse response = speechClient.recognize(config, audio);
      List<SpeechRecognitionResult> results = response.getResultsList();
      if (results.isEmpty()) return "";
      return results.get(0).getAlternativesList().get(0).getTranscript();
    }
  }
}
