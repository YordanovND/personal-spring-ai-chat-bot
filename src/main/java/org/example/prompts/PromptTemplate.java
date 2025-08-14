// src/main/java/org/example/prompts/PromptTemplates.java
package org.example.prompts;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class PromptTemplate {
    private final ResourceLoader loader;

    public PromptTemplate(ResourceLoader loader) { this.loader = loader; }

    private String load(String name) {
        try {
            Resource r = loader.getResource("classpath:prompts/" + name + ".md");
            return StreamUtils.copyToString(r.getInputStream(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load prompt: " + name, e);
        }
    }

    public String render(String name, Map<String,String> vars) {
        return PromptRenderer.render(load(name), vars);
    }
}
