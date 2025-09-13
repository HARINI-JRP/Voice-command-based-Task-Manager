package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.util.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) { this.taskService = taskService; }

  @GetMapping
  public List<TaskDTO> list() {
    return taskService.list().stream().map(Mapper::toDto).collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskDTO> get(@PathVariable Long id) {
    return taskService.get(id)
        .map(Mapper::toDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO dto) {
    Task t = Mapper.fromDto(dto);
    Task saved = taskService.create(t);
    return ResponseEntity.created(URI.create("/api/tasks/" + saved.getId())).body(Mapper.toDto(saved));
  }

  @PutMapping("/{id}")
  public ResponseEntity<TaskDTO> update(@PathVariable Long id, @RequestBody TaskDTO dto) {
    return taskService.get(id).map(existing -> {
      existing.setTitle(dto.getTitle());
      existing.setDescription(dto.getDescription());
      existing.setCompleted(dto.isCompleted());
      existing.setDueAt(dto.getDueAt());
      Task saved = taskService.update(existing);
      return ResponseEntity.ok(Mapper.toDto(saved));
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    taskService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
