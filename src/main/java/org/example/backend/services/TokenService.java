package org.example.backend.services;

import java.util.UUID;

public class TokenService {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
