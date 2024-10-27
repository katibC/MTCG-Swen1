package org.example.backend.utils;

import org.example.backend.http.ContentType;
import org.example.backend.http.HttpStatus;
import org.example.backend.server.Request;
import org.example.backend.server.Response;
import org.example.backend.server.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private Socket clientSocket;
    private Router router;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public RequestHandler(Socket clientSocket, Router router) throws IOException {
        this.clientSocket = clientSocket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        this.printWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.router = router;
    }

    @Override
    public void run() {
        try {
            Response response;
            Request request = new RequestBuilder().buildRequest(this.bufferedReader);

            if (request.getPathname() == null) {
                response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\":\"Invalid request - pathname is null\"}");
            } else {
                String serviceRoute = request.getServiceRoute();
                Service service = this.router.resolve(serviceRoute);

                if (service == null) {
                    response = new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "{\"error\":\"Service not found for route: " + serviceRoute + "\"}");
                } else {
                    response = service.handleRequest(request);
                }
            }

            printWriter.write(response.get());
        } catch (IOException e) {
            System.err.println(Thread.currentThread().getName() + " Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}