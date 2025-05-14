package de.MCmoderSD.debrid.objects;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.UUID;

@SuppressWarnings("unused")
public class Download {

    // Json Attributes
    private final Timestamp created;
    private final String id;
    private final String name;
    private final String source;
    private final String downloadLink;
    private final Boolean expired;
    private final String host;
    private final Long size;

    // Constants
    public Download(JsonNode node) {
        created = node.has("created") ? new Timestamp(node.get("created").asLong()) : new Timestamp(System.currentTimeMillis());
        id = node.has("id") ? node.get("id").asText() : UUID.randomUUID().toString();
        name = node.get("name").asText();
        source = node.get("url").asText();
        downloadLink = node.get("downloadUrl").asText();
        expired = node.has("expired") ? node.get("expired").asBoolean() : null;
        host = node.has("host") ? node.get("host").asText() : null;
        size = node.has("size") ? node.get("size").asLong() : null;
    }

    // Methods
    public InputStream openStream() throws URISyntaxException, IOException {
        return new URI(downloadLink).toURL().openStream();
    }

    // Getters
    public Timestamp getCreated() {
        return created;
    }

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

    public Boolean isExpired() {
        return expired;
    }

    public String getHost() {
        return host;
    }

    public Long getSize() {
        return size;
    }
}