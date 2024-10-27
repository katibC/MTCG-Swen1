package org.example;

import org.example.backend.server.Server;
import org.example.backend.utils.Router;
import org.example.backend.services.UserService;


import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(10001, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Router configureRouter() {
        Router router = new Router();

        // Routen für Registrierung und Login
        router.addService("/users", new UserService());    // Registrierung
        router.addService("/sessions", new UserService()); // Login
        //router.addService("/packages", new PackageService());

        // Fügen Sie weitere Routen hinzu, falls erforderlich

        return router;
    }

}