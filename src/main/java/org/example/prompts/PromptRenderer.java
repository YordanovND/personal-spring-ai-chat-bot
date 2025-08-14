// src/main/java/org/example/util/PromptRenderer.java
package org.example.prompts;

import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Map;

public class PromptRenderer {
    private static final PropertyPlaceholderHelper H =
            new PropertyPlaceholderHelper("${", "}");

    public static String render(String template, Map<String, String> vars) {
        return H.replacePlaceholders(template, vars::get);
    }
}
