package de.MCmoderSD.debrid.core;

import de.MCmoderSD.debrid.objects.Download;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

public class DebridAPI {

    // Constants
    private static final String ENDPOINT = "https://debrid-link.com/api/v2";
    private static final String ADD_DOWNLOAD = "/downloader/add";

    // Attributes
    private final String apiKey;
    private final ObjectMapper mapper;
    private final HttpClient httpClient;

    // Constructor
    public DebridAPI(String apiKey) {

        // Check API Key
        if (apiKey == null || apiKey.isBlank()) throw new IllegalArgumentException("API Key is null or empty");

        // Set API Key
        this.apiKey = apiKey;

        // Initialize Attributes
        mapper = new ObjectMapper();
        httpClient = HttpClient.newBuilder().build();
    }

    // Helper Method to Send HTTP Requests
    private JsonNode sendRequest(HttpRequest request) {
        try {

            // Check Request
            if (request == null) throw new IllegalArgumentException("HTTP Request must not be null");

            // Send HTTP Request
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Check Response Status
            var status = response.statusCode();
            if (status != 200) throw new IOException("HTTP Status code " + status);

            // Return Response Body
            return mapper.readTree(response.body());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to send HTTP request", e);
        }
    }

    // API Method to Add Download
    public Download addDownload(String url) {

        // Check URL
        if (url == null || url.isBlank()) throw new IllegalArgumentException("URL must not be null or empty");

        // Build HTTP Request
        var request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT + ADD_DOWNLOAD))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("url=" + encode(url, UTF_8)))
                .build();

        // Send HTTP Request and Get Response
        var response = sendRequest(request);

        // Check Success
        if (!response.has("success") || !response.get("success").asBoolean()) throw new RuntimeException("API response indicates failure: " + response);
        if (!response.has("value") || response.get("value").isNull() || response.get("value").isEmpty() || !response.get("value").isObject()) throw new RuntimeException("API response is missing expected 'value' object: " + response);

        // Inspect Value
        var value = response.get("value");
        if (!value.has("expired") || !value.get("expired").isBoolean() || value.get("expired").asBoolean())  throw new RuntimeException("API response indicates failure: " + response);

        // Return Download Object
        return new Download(value);
    }
}