import de.MCmoderSD.debrid.core.DebridAPI;
import de.MCmoderSD.debrid.objects.Download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

@SuppressWarnings("ALL")
public class Main {

    public static void main(String[] args) {

        // Variables
        String apiKey = "your-api-key-here";        // Replace with your actual API key
        String downloadUrl = "download-url-here";   // Replace with the actual download URL

        // Initialize API
        DebridAPI api = new DebridAPI(apiKey);

        try {

            // Add download
            Download download = api.addDownload(downloadUrl);

            // Download file into memory
            InputStream stream = download.openStream();
            byte[] data = stream.readAllBytes();

            // Save file to disk
            File file = new File(download.getName());
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data);

            // Close streams
            outputStream.close();
            stream.close();

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}