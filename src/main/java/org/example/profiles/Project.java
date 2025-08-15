package org.example.profiles;

public record Project(
        String name,
        String role,
        String stack,
        String link,
        String[] highlights
) {}
