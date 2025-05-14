package de.MCmoderSD.debrid.objects;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@SuppressWarnings("unused")
public class Download {

    // Json Attributes
    private final String id;
    private final String name;
    private final String source;
    private final String downloadLink;
    private final boolean expired;
    private final String host;

    // Constants
    public Download(JsonNode node) {
        id = node.get("id").asText();
        name = node.get("name").asText();
        source = node.get("url").asText();
        downloadLink = node.get("downloadUrl").asText();
        expired = node.get("expired").asBoolean();
        host = node.get("host").asText();
    }

    // Methods
    public InputStream openStream() throws URISyntaxException, IOException {
        return new URI(downloadLink).toURL().openStream();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public boolean isExpired() {
        return expired;
    }

    public String getHost() {
        return host;
    }
}