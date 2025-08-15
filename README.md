# Spring AI Chat with Ollama (Dockerized)

This project is a **Spring Boot** application that integrates with **Ollama** to provide AI-powered chat capabilities using local LLMs such as **Meta-Llama-3-8B-Instruct**.  
It’s fully containerized using **Docker Compose**, so you can spin up both the application and the Ollama backend in one command.

---

## 🧠 Project Overview

The app serves a simple REST API that accepts chat prompts from the user and returns AI-generated responses from the LLM running in Ollama.  
It is designed as a **self-contained environment** — no external API keys are required because the AI runs locally inside Docker.

---

## 🛠️ Technologies Used

- **Java 21** — the core language for the backend.
- **Spring Boot 3** — application framework for building and serving APIs.
- **Spring AI** — abstraction for integrating AI models into Spring applications.
- **Gradle** — build and dependency management.
- **Ollama** — lightweight LLM runner for local AI inference.
- **Meta-Llama-3-8B-Instruct** — default AI model.
- **Docker & Docker Compose** — containerization and service orchestration.


## 🚀 Getting Started

### 1️⃣ Prerequisites
Make sure you have installed:
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Java 21](https://adoptium.net/) (only if running locally without Docker)
- [Gradle](https://gradle.org/) (only if building locally without Docker)

---

### 2️⃣ Start with Docker

```bash
# Build and start the services
docker compose up --build

