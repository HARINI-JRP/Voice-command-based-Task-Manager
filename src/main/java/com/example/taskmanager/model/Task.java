package com.example.taskmanager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  @Column(length = 2000)
  private String description;

  private boolean completed = false;

  private LocalDateTime dueAt;

  // Constructors, getters, setters
  public Task() {}

  public Task(String title, String description) {
    this.title = title;
    this.description = description;
  }

  // getters and setters...
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public boolean isCompleted() { return completed; }
  public void setCompleted(boolean completed) { this.completed = completed; }
  public LocalDateTime getDueAt() { return dueAt; }
  public void setDueAt(LocalDateTime dueAt) { this.dueAt = dueAt; }
}
