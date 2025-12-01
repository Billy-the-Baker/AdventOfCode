package com.baker.billy.core;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class SimpleRequest {
    private static String getCookieFromFile() {
        String fileName = "src/main/resources/conf/cookie.txt";
        try (BufferedReader rdr = new BufferedReader(new FileReader(fileName))) {
            String cookie = rdr.readLine().trim();
            return cookie;
        } catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Could not find the specified file: " + fileName,
                    "File Not Found", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "There was an error conducting IO operations:\n" + e.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return "";
    }

    public static HttpResponse<String> sendRequest(String url) throws URISyntaxException, InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Cookie", getCookieFromFile()) // Cookie
                .header("User-Agent", "https://github.com/Billy-the-Baker/AdventOfCode by heckler82@gmail.com") // User-Agent
                .timeout(Duration.of(5, ChronoUnit.SECONDS))
                .GET()
                .build();

        return HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

    }
}
