package org.example.profiles;

public record UserProfile(
        String userId,
        String name,
        String timezone,
        String role,
        String[] interests
) {}
