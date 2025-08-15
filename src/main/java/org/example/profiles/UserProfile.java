package org.example.profiles;

public record UserProfile(
        String userId,
        String name,
        String timezone,
        String role,
        String location,
        String education,
        String[] interests,
        String[] values,
        Project[] projects
) {}
