package org.example.controller;

import org.example.profiles.UserProfile;
import org.example.profiles.UserProfileService;
import org.example.prompts.PromptTemplate;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class ChatController {

    private final ChatClient chat;
    private final UserProfileService profiles;
    private final PromptTemplate templates;

    public ChatController(ChatClient chat, UserProfileService profiles, PromptTemplate templates) {
        this.chat = chat;
        this.profiles = profiles;
        this.templates = templates;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody ChatRequest req) {
        String message  = req.message() == null ? "" : req.message();
        String userId   = req.user() == null ? "me" : req.user();
        String scopeRaw = req.scope() == null ? "auto" : req.scope().toLowerCase();

        UserProfile p = profiles.get(userId);

        // explicit scope wins; otherwise infer from message
        String scope = "auto".equals(scopeRaw) ? inferScopeFromMessage(message) : scopeRaw;

        // build system prompt from templates (job.md, interests.md, profile.md, all.md, fallback.md)
        String system = systemFromScope(scope, p);

        String reply = chat
                .prompt()
                .system(system)
                .user(message)
                .call()
                .content();

        return Map.of("reply", reply, "scope", scope);
    }

    private String inferScopeFromMessage(String m) {
        if (m == null) return "profile";
        String q = m.toLowerCase();
        if (q.matches(".*\\b(job|work|occupation|title|role)\\b.*")) return "job";
        if (q.matches(".*\\b(hobby|interest|interests|like|favorite|favourite)\\b.*")) return "interests";
        return "profile";
    }

    private String systemFromScope(String scope, UserProfile p) {
        var vars = Map.of(
                "name", p.name(),
                "role", p.role(),
                "timezone", p.timezone(),
                "interests", String.join(", ", p.interests())
        );

        return switch (scope) {
            case "job"       -> templates.render("job", vars);
            case "interests" -> templates.render("interests", vars);
            case "profile"   -> templates.render("profile", vars);
            case "all"       -> templates.render("all", vars);
            default          -> templates.render("fallback", vars);
        };
    }
}
