package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
  private final TaskRepository repo;

  public TaskService(TaskRepository repo) { this.repo = repo; }

  public Task create(Task t) { return repo.save(t); }
  public List<Task> list() { return repo.findAll(); }
  public Optional<Task> get(Long id) { return repo.findById(id); }
  public Task update(Task t) { return repo.save(t); }
  public void delete(Long id) { repo.deleteById(id); }
}
