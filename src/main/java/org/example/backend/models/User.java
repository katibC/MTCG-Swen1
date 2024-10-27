package org.example.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {

    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;

    private String token;

    public User() {
        // Default-Konstruktor für Jackson
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
