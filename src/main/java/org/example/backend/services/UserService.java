package org.example.backend.services;

import org.example.backend.models.User;
import org.example.backend.server.Service;
import org.example.backend.server.Request;
import org.example.backend.server.Response;
import org.example.backend.http.HttpStatus;
import org.example.backend.http.ContentType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class UserService implements Service {
    private static final Map<String, User> userDatabase = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Response handleRequest(Request request) {
        switch (request.getMethod()) {
            case POST:
                if (request.getPathname().equals("/users")) {
                    // Registrierung
                    return registerUser(request);
                } else if (request.getPathname().equals("/sessions")) {
                    // Login
                    return loginUser(request);
                }
                break;
            default:
                return new Response(HttpStatus.NOT_IMPLEMENTED, ContentType.JSON, "[]");
        }
        return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "[]");
    }

    private Response registerUser(Request request) {
        try {
            System.out.println("Received body for registration: " + request.getBody());
            User user = objectMapper.readValue(request.getBody(), User.class);
            if (userDatabase.containsKey(user.getUsername())) {
                return new Response(HttpStatus.CONFLICT, ContentType.JSON, "{\"error\": \"User already exists\"}");
            }
            userDatabase.put(user.getUsername(), user);
            return new Response(HttpStatus.CREATED, ContentType.JSON, "{\"message\": \"User registered successfully\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid user data\"}");
        }
    }


    private Response loginUser(Request request) {
        try {
            User user = objectMapper.readValue(request.getBody(), User.class);
            User registeredUser = userDatabase.get(user.getUsername());
            if (registeredUser != null && registeredUser.getPassword().equals(user.getPassword())) {
                String token = TokenService.generateToken();
                return new Response(HttpStatus.OK, ContentType.JSON, "{\"token\": \"" + token + "\"}");
            }
            return new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "{\"error\": \"Invalid credentials\"}");
        } catch (Exception e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid login data\"}");
        }
    }
}
