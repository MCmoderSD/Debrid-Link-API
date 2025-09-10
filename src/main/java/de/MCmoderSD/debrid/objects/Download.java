package de.MCmoderSD.debrid.objects;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Represents a single download object returned by the Debrid-Link API.
 * <p>
 * This class maps JSON attributes from the API response into strongly typed
 * Java fields, such as creation time, file name, download link, size, and host.
 * It also provides convenience methods for accessing these attributes and
 * opening an input stream to the actual download URL.
 * </p>
 */
@SuppressWarnings("ALL")
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

    /**
     * Constructs a {@link Download} object from a given JSON node.
     *
     * @param node The {@link JsonNode} containing download information.
     *             <ul>
     *                 <li><b>created</b> → Creation timestamp (milliseconds since epoch)</li>
     *                 <li><b>id</b> → Unique download ID</li>
     *                 <li><b>name</b> → File name</li>
     *                 <li><b>url</b> → Source URL</li>
     *                 <li><b>downloadUrl</b> → Direct download link</li>
     *                 <li><b>expired</b> → Expiration flag</li>
     *                 <li><b>host</b> → Host name</li>
     *                 <li><b>size</b> → File size in bytes</li>
     *             </ul>
     */
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

    /**
     * Opens an {@link InputStream} to the direct download link.
     *
     * @return An input stream for reading the file.
     * @throws URISyntaxException If the download URL is malformed.
     * @throws IOException        If the stream cannot be opened.
     */
    public InputStream openStream() throws URISyntaxException, IOException {
        return new URI(downloadLink).toURL().openStream();
    }

    /**
     * Gets the creation timestamp of the download.
     *
     * @return The creation time as a {@link Timestamp}.
     */
    public Timestamp getCreated() {
        return created;
    }

    /**
     * Gets the unique identifier of the download.
     *
     * @return The download ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the file.
     *
     * @return The file name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the original source URL of the file.
     *
     * @return The source URL.
     */
    public String getSource() {
        return source;
    }

    /**
     * Gets the direct download link provided by Debrid-Link.
     *
     * @return The download link.
     */
    public String getDownloadLink() {
        return downloadLink;
    }

    /**
     * Checks whether the download link has expired.
     *
     * @return {@code true} if expired, {@code false} otherwise, or {@code null} if unknown.
     */
    public Boolean isExpired() {
        return expired;
    }

    /**
     * Gets the host name from which the file originates.
     *
     * @return The host name, or {@code null} if not available.
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets the size of the file in bytes.
     *
     * @return The file size, or {@code null} if unknown.
     */
    public Long getSize() {
        return size;
    }
}