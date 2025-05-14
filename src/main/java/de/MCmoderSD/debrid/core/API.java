package de.MCmoderSD.debrid.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.MCmoderSD.debrid.objects.Download;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class API {

    // Constants
    private static final String ENDPOINT = "https://debrid-link.com/api/v2";
    private static final String ADD_DOWNLOAD = "/downloader/add";

    // Attributes
    private final String apiKey;
    private final ObjectMapper mapper;

    // Constructor
    public API(String apiKey) {

        // Check API Key
        if (apiKey == null || apiKey.isBlank()) throw new IllegalArgumentException("API Key is null or empty");

        // Check API Key length
        this.apiKey = apiKey;

        // Initialize ObjectMapper
        mapper = new ObjectMapper();
    }

    public Download addDownload(String url) throws IOException, URISyntaxException {

        // Check URL
        if (url == null || url.isBlank()) throw new IllegalArgumentException("URL is null or empty");

        // Open connection
        var endpoint = new URI(ENDPOINT + ADD_DOWNLOAD);
        var httpURLConnection = (HttpURLConnection) endpoint.toURL().openConnection();

        // Set request properties
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + apiKey);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setDoOutput(true);

        // Set request body
        String jsonBody = "{\"url\": \"" + url + "\"}";
        var outputStream = httpURLConnection.getOutputStream();
        byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
        outputStream.write(input, 0, input.length);

        var responseCode = httpURLConnection.getResponseCode();
        var inputStream = (responseCode >= 200 && responseCode < 300) ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream();

        // Check response code
        if (responseCode < 200 || responseCode >= 300) {
            String error = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.err.println("API Error (" + responseCode + "): " + error);
            throw new IOException("API request failed with code " + responseCode);
        }

        // Parse response
        return new Download(mapper.readTree(inputStream));
    }
}