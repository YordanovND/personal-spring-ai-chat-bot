package org.example.profiles;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserProfileService {
    private final Map<String, UserProfile> store = new ConcurrentHashMap<>();

    public UserProfileService() {
        store.put("me", new UserProfile(
                "me",
                "Nikolay Yordanov",
                "Europe/Sofia",
                "Regular Software Engineer (Full-Stack: Java/Spring/Angular) at Paysafe",
                "Sofia, Bulgaria (from Razgrad)",
                """
                Math School "Akad. Nikola Obreshkov", Razgrad — Mathematics & IT (2019);
                Sofia University, Faculty of Mathematics and Informatics — Informatics (2 years);
                University of National and World Economy (UNWE), Sofia — BSc Business Informatics & Communications (2025);
                SoftUni (2021) — Python/Django track
                """,
                new String[]{
                        "AI features", "Java", "Spring", "backend architecture", "design patterns"
                },
                new String[]{
                        "open communicator", "team-oriented", "trustworthy", "eager to learn"
                },
                new Project[]{
                        new Project(
                                "Solar Cargo (side project)",
                                "Backend contributor in a small team",
                                "Java/Spring (repo specifics), CI/CD, collaboration",
                                "https://github.com/justteshi/solar-cargo-be",
                                new String[]{
                                        "logistics helper for a solar-panel delivery company",
                                        "built collaboratively with friends as a side hustle"
                                }
                        ),
                        new Project(
                                "Interactive portfolio + chatbot",
                                "Full-stack (Angular + Spring AI + Ollama)",
                                "Angular, Spring Boot, Spring AI, Docker",
                                "https://github.com/YordanovND/personal-spring-ai-chat-bot",
                                new String[]{
                                        "chat-first profile with local LLM (Ollama)",
                                        "single Docker image serving SPA + API"
                                }
                        )
                }
        ));
    }

    public UserProfile get(String userId) {
        return store.getOrDefault(userId,
                new UserProfile(userId, "Guest", "UTC", "user", "—", "—",
                        new String[0], new String[0], new Project[0]));
    }

    public void upsert(UserProfile p) { store.put(p.userId(), p); }
}
