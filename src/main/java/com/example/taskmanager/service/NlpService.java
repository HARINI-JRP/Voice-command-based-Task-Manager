package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NlpService {

  /**
   * A tiny rule-based parser. It returns an action object encoded as simple enum-like strings.
   * For simplicity: try to parse commands:
   *  - "add task <title> [description]"
   *  - "create task <title>"
   *  - "complete task <id>"
   *  - "delete task <id>"
   *  - "update task <id> title <new title>"
   *
   * Returns a small result object (Action + payload).
   */
  public ParseResult parse(String text) {
    String lower = text.trim().toLowerCase();

    // 1) complete task <id>
    Pattern completePattern = Pattern.compile("complete (?:the )?task (\\d+)");
    Matcher m = completePattern.matcher(lower);
    if (m.find()) {
      Long id = Long.parseLong(m.group(1));
      return ParseResult.complete(id);
    }

    // 2) delete task <id>
    Pattern deletePattern = Pattern.compile("delete (?:the )?task (\\d+)");
    m = deletePattern.matcher(lower);
    if (m.find()) {
      Long id = Long.parseLong(m.group(1));
      return ParseResult.delete(id);
    }

    // 3) add/create task <title> (rest)
    Pattern addPattern = Pattern.compile("(?:add|create) (?:a )?task (.+)");
    m = addPattern.matcher(text); // not lower - keep case for title
    if (m.find()) {
      String title = m.group(1).trim();
      // naive: if "because" or "for" present, treat as description separator
      String description = null;
      if (title.contains(" because ")) {
        String[] parts = title.split(" because ", 2);
        title = parts[0].trim();
        description = parts[1].trim();
      } else if (title.contains(" for ")) {
        String[] parts = title.split(" for ", 2);
        title = parts[0].trim();
        description = "for " + parts[1].trim();
      }
      Task t = new Task();
      t.setTitle(title);
      t.setDescription(description);
      return ParseResult.create(t);
    }

    // 4) simple fallback: treat whole text as a task creation title
    Task t = new Task();
    t.setTitle(text.trim());
    return ParseResult.create(t);
  }

  public static class ParseResult {
    public enum Action { CREATE, COMPLETE, DELETE }
    public final Action action;
    public final Task task;
    public final Long targetId;

    private ParseResult(Action action, Task task, Long targetId) {
      this.action = action;
      this.task = task;
      this.targetId = targetId;
    }

    public static ParseResult create(Task t) { return new ParseResult(Action.CREATE, t, null); }
    public static ParseResult complete(Long id) { return new ParseResult(Action.COMPLETE, null, id); }
    public static ParseResult delete(Long id) { return new ParseResult(Action.DELETE, null, id); }
  }
}
