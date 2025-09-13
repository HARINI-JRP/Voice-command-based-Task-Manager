package com.example.taskmanager.controller;

import com.example.taskmanager.dto.AudioRequest;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.NlpService;
import com.example.taskmanager.service.SpeechService;
import com.example.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/voice")
public class VoiceController {

  private final SpeechService speechService;
  private final NlpService nlpService;
  private final TaskService taskService;

  public VoiceController(SpeechService speechService, NlpService nlpService, TaskService taskService) {
    this.speechService = speechService;
    this.nlpService = nlpService;
    this.taskService = taskService;
  }

  @PostMapping("/transcribe")
  public ResponseEntity<?> transcribeAndAct(@RequestBody AudioRequest req) {
    try {
      String transcript;
      if (req.getBase64() != null && !req.getBase64().isEmpty()) {
        byte[] bytes = Base64.getDecoder().decode(req.getBase64());
        transcript = speechService.transcribeFromBase64(bytes, req.getLanguageCode());
      } else if (req.getGcsUri() != null && !req.getGcsUri().isEmpty()) {
        transcript = speechService.transcribeFromGcsUri(req.getGcsUri(), req.getLanguageCode());
      } else {
        return ResponseEntity.badRequest().body("Provide base64 or gcsUri audio.");
      }

      // parse
      NlpService.ParseResult result = nlpService.parse(transcript);

      switch (result.action) {
        case CREATE:
          Task created = taskService.create(result.task);
          return ResponseEntity.ok(created);
        case COMPLETE:
          return taskService.get(result.targetId)
              .map(t -> {
                t.setCompleted(true);
                taskService.update(t);
                return ResponseEntity.ok(t);
              }).orElse(ResponseEntity.notFound().build());
        case DELETE:
          taskService.delete(result.targetId);
          return ResponseEntity.ok("Deleted");
        default:
          return ResponseEntity.badRequest().body("Could not interpret command.");
      }

    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(500).body("Error: " + ex.getMessage());
    }
  }

  // For testing convenience: accept direct text (no speech) and run NLP
  @PostMapping("/text")
  public ResponseEntity<?> textCommand(@RequestParam String text) {
    NlpService.ParseResult result = nlpService.parse(text);
    switch (result.action) {
      case CREATE:
        Task created = taskService.create(result.task);
        return ResponseEntity.ok(created);
      case COMPLETE:
        return taskService.get(result.targetId)
            .map(t -> {
              t.setCompleted(true);
              taskService.update(t);
              return ResponseEntity.ok(t);
            }).orElse(ResponseEntity.notFound().build());
      case DELETE:
        taskService.delete(result.targetId);
        return ResponseEntity.ok("Deleted");
      default:
        return ResponseEntity.badRequest().body("Could not interpret command.");
    }
  }
}
