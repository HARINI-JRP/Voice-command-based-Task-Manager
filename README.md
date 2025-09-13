# Voice-Controlled Task Manager

A **Spring Boot-based Task Manager** that supports **voice commands** using Google Speech-to-Text API. It enables users to create, update, and manage tasks through natural language voice input, processed with simple NLP techniques and REST APIs.

---

## 🚀 Features
-  **Voice Command Support** – Create tasks by speaking.
-  **Basic NLP Integration** – Converts speech into structured task actions.
-  **Spring Boot REST API** – Modular and scalable backend.
-  **Persistence** – Stores tasks in a database for easy retrieval.
-  **Search & Filter** – Manage tasks by categories, priority, or deadlines.

---

## 🛠️ Tech Stack
- **Java 17**
- **Spring Boot**
- **Maven**
- **Google Speech-to-Text API**
- **H2 / MySQL** (configurable database)

---

## ⚙️ Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/TaskManager.git
   cd TaskManager
   ```

2. **Configure Google Speech-to-Text**
   - Create a Google Cloud project.
   - Enable Speech-to-Text API.
   - Download the credentials JSON file and set the path in your environment:
     ```bash
     set GOOGLE_APPLICATION_CREDENTIALS=path/to/credentials.json   # Windows
     export GOOGLE_APPLICATION_CREDENTIALS=path/to/credentials.json # Linux/Mac
     ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**
   - Base URL: `http://localhost:8080/api/tasks`
   - Example: `POST /api/tasks` with voice command converted into text.

---

## 🎯 Example Usage

**Voice Input:**
> "Add a meeting with team tomorrow at 10 AM"

**Backend Conversion → Stored Task:**
```json
{
  "title": "Meeting with team",
  "date": "2025-09-14",
  "time": "10:00 AM"
}
```

---

## 📌 Roadmap
- ✅ Basic CRUD for tasks
- ✅ Voice-to-text integration
- ⏳ Add authentication & user roles
- ⏳ Enhance NLP with advanced parsing
- ⏳ Deploy to cloud

---

## 📄 License
This project is licensed under the MIT License.
