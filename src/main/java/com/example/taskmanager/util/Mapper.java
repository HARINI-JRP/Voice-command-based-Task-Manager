package com.example.taskmanager.util;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.model.Task;

public class Mapper {
  public static TaskDTO toDto(Task t) {
    TaskDTO dto = new TaskDTO();
    dto.setId(t.getId());
    dto.setTitle(t.getTitle());
    dto.setDescription(t.getDescription());
    dto.setCompleted(t.isCompleted());
    dto.setDueAt(t.getDueAt());
    return dto;
  }
  public static Task fromDto(TaskDTO dto) {
    Task t = new Task();
    t.setId(dto.getId());
    t.setTitle(dto.getTitle());
    t.setDescription(dto.getDescription());
    t.setCompleted(dto.isCompleted());
    t.setDueAt(dto.getDueAt());
    return t;
  }
}