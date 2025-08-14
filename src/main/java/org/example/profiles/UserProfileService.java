package org.example.profiles;

import org.example.profiles.UserProfile;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserProfileService {
    private final Map<String, UserProfile> store = new ConcurrentHashMap<>();

    public UserProfileService() {
        // seed an example profile; swap with persistence later
        store.put("me", new UserProfile(
                "me", "Nikolay", "Europe/Istanbul", "Java developer",
                new String[]{"Spring", "AI", "football", "Manchester United"}));
    }

    public UserProfile get(String userId) {
        return store.getOrDefault(userId, new UserProfile(userId, "Guest", "UTC", "user", new String[0]));
    }

    public void upsert(UserProfile p) { store.put(p.userId(), p); }
}
