package de.MCmoderSD.debrid.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.MCmoderSD.debrid.objects.Download;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Provides a wrapper for the Debrid-Link API v2.
 * <p>
 * This class allows interaction with the Debrid-Link service using a provided API key.
 * Currently, it supports adding new downloads to the Debrid-Link downloader.
 * </p>
 */
@SuppressWarnings("ALL")
public class DebridAPI {

    // Constants
    private static final String ENDPOINT = "https://debrid-link.com/api/v2";
    private static final String ADD_DOWNLOAD = "/downloader/add";

    // Attributes
    private final String apiKey;
    private final ObjectMapper mapper;

    /**
     * Creates a new instance of the {@link DebridAPI}.
     *
     * @param apiKey The API key for authenticating with Debrid-Link.
     * @throws IllegalArgumentException if the provided API key is {@code null} or blank.
     */
    public DebridAPI(String apiKey) {

        // Check API Key
        if (apiKey == null || apiKey.isBlank()) throw new IllegalArgumentException("API Key is null or empty");

        // Set API Key
        this.apiKey = apiKey;

        // Initialize ObjectMapper
        mapper = new ObjectMapper();
    }

    /**
     * Sends a request to Debrid-Link to add a new download.
     *
     * @param url The URL of the file to download.
     * @return A {@link Download} object containing information about the added download.
     * @throws IllegalArgumentException if the provided URL is {@code null} or blank.
     * @throws IOException              if the API request fails or the response cannot be parsed.
     * @throws URISyntaxException       if the endpoint URI is malformed.
     */
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
        JsonNode response = mapper.readTree(inputStream);
        if (response.get("success").asBoolean()) return new Download(response.get("value"));
        else {
            String error = response.get("error").asText();
            System.err.println("API Error: " + error);
            throw new IOException("API request failed: " + error);
        }
    }
}