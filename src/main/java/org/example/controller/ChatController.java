package org.example.controller;

import org.example.profiles.Project;
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

        if (q.matches(".*\\b(project|projects|portfolio|github|case study|case studies)\\b.*")) return "projects";
        if (q.matches(".*\\b(job|work|occupation|title|role|experience|resume|cv)\\b.*"))       return "job";
        if (q.matches(".*\\b(hobby|interest|interests|like|favorite|favourite)\\b.*"))          return "interests";

        return "profile";
    }

    private String systemFromScope(String scope, UserProfile p) {
        var vars = Map.of(
                "name", p.name(),
                "role", p.role(),
                "timezone", p.timezone(),
                "location", p.location(),
                "education", p.education(),
                "interests", String.join(", ", p.interests()),
                "values", String.join(", ", p.values()),
                "projects_bullets", projectsToBullets(p.projects())
        );

        return switch (scope) {
            case "job"       -> templates.render("job", vars);
            case "interests" -> templates.render("interests", vars);
            case "projects"  -> templates.render("projects", vars);
            case "profile"   -> templates.render("profile", vars);
            case "all"       -> templates.render("all", vars);
            default          -> templates.render("fallback", vars);
        };
    }

    private String projectsToBullets(Project[] projects) {
        if (projects == null || projects.length == 0) return "- (no projects listed)";
        var sb = new StringBuilder();
        for (var pr : projects) {
            sb.append("- **").append(pr.name()).append("**");
            if (pr.role() != null && !pr.role().isBlank()) sb.append(" — ").append(pr.role());
            if (pr.stack() != null && !pr.stack().isBlank()) sb.append(" · _").append(pr.stack()).append("_");
            if (pr.link() != null && !pr.link().isBlank()) sb.append(" · ").append(pr.link());
            if (pr.highlights() != null && pr.highlights().length > 0) {
                sb.append("\n  ");
                for (String h : pr.highlights()) {
                    sb.append("  • ").append(h).append("\n  ");
                }
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

}
