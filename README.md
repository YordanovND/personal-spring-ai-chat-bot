# Personal Spring AI Chat Bot

A personalizable chatbot built with **Spring Boot** and **Spring AI**, running with **Ollama** as the LLM backend.  
This project allows you to define a **user profile** (name, job, timezone, interests) and chat with the bot via REST API.  
The bot adapts its answers based on your profile and the context of your questions.

---

## 🚀 Features
- **Spring Boot 3.4** backend.
- **Spring AI** integration with Ollama model.
- Supports **user-specific profiles**.
- Different response **scopes**:
    - `job` → Focus only on your job.
    - `interests` → Focus only on your hobbies/interests.
    - `profile` → Use job + timezone.
    - `all` → Use all profile details.
    - `auto` → Detect scope from question.
- Simple REST API for chatting.
- Flexible prompt templates in `.md` files.

---

## 📦 Requirements
- Java **21+**
- Gradle (Wrapper included)
- [Ollama](https://ollama.ai) installed and running:
  ```bash
  ollama serve
  ollama pull llama3