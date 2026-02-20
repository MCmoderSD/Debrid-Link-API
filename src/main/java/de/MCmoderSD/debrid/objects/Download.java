package de.MCmoderSD.debrid.objects;

import tools.jackson.databind.JsonNode;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Optional;

@SuppressWarnings("unused")
public class Download {

    // Json Attributes
    private final Timestamp created;
    private final String id;
    private final String name;
    private final String source;
    private final String downloadLink;
    private final String host;
    private final Long size;

    // Constructor
    public Download(JsonNode node) {
        created = node.has("created") ? new Timestamp(node.get("created").asLong()) : new Timestamp(System.currentTimeMillis());
        id = node.get("id").asString();
        name = node.get("name").asString();
        source = node.get("url").asString();
        downloadLink = node.get("downloadUrl").asString();
        host = node.has("host") ? node.get("host").asString() : null;
        size = node.has("size") ? node.get("size").asLong() : null;
    }

    // Convenience Method to Open Download Stream
    public InputStream openStream() throws URISyntaxException, IOException {
        return new URI(downloadLink).toURL().openStream();
    }

    // Convenience Method to Download File to Disk with Default Name
    public File toFile() {
        return toFile(new File(name));
    }

    // Convenience Method to Download File to Disk
    public File toFile(File file) {

        // Check if file is valid
        if (file == null) throw new IllegalArgumentException("File must not be null");
        if (file.isDirectory()) throw new IllegalArgumentException("File must not be a directory");

        // Download file
        try (
                var in = new BufferedInputStream(openStream());                 // Open input stream to download URL
                var out = new BufferedOutputStream(new FileOutputStream(file))  // Open output stream to target file
        ) {
            in.transferTo(out);                                                 // Transfer data from input stream to output stream
            return file;                                                        // Return the file reference after successful download
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Failed to download file", e);
        }
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

    public Optional<String> getHost() {
        return Optional.ofNullable(host);
    }

    public Optional<Long> getSize() {
        return Optional.ofNullable(size);
    }
}