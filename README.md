# 🗣️ Spring AI Chat + Angular (Ollama, Dockerized)

A full-stack personal portfolio with a **chatbot front-and-center**.

- **Frontend:** Angular 16+ (standalone), built and served by Spring Boot
- **Backend:** Spring Boot 3 + Spring AI
- **LLM runtime:** Ollama (default model: `llama3`)
- **Containerization:** Docker & Docker Compose

---

## ✨ Features

- **Ask Me** chatbot (`/ask`) powered by Spring AI + Ollama
- Clean, dark UI with compact chat, typing indicator, and sticky navbar
- Single container serving **both** the Angular SPA and the API (no CORS)
- Optional local dev mode with Angular dev-server + API proxy

---

## 🚀 Quick Start (Docker)

```bash
# Build and start everything (Ollama + the app)
docker compose up --build

