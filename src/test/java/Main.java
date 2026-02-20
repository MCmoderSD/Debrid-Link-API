import de.MCmoderSD.debrid.core.DebridAPI;
import de.MCmoderSD.debrid.objects.Download;

import java.io.File;

void main() {

    // Variables
    String apiKey = "your-api-key-here";        // Replace with your actual API key
    String downloadUrl = "download-url-here";   // Replace with the actual download URL

    // Initialize API
    DebridAPI api = new DebridAPI(apiKey);

    // Add download
    Download download = api.addDownload(downloadUrl);

    // Download file
    File file = download.toFile(new File(download.getName()));

    // Print file path
    IO.println("Downloaded file: " + file.getAbsolutePath());
}