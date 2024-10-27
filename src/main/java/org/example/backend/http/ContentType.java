package org.example.backend.http;

public enum ContentType {

    TEXT("text/plain"),
    HTML("text/html"),
    JSON("application/json");
    public final String type;

    ContentType(String type) {
        this.type = type;
    }
}
