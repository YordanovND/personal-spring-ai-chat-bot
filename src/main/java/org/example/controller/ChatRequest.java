package org.example.controller;

public record ChatRequest(
        String message,
        String user,     // optional, default "me"
        String session,  // optional, if you add memory later
        String scope     // "job" | "interests" | "profile" | "all" | "auto"
) {}