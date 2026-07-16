import de.MCmoderSD.debrid.core.DebridAPI;

import java.io.File;

import static java.lang.IO.println;

void main() {

    // Variables
    var apiKey = "your-api-key-here";       // Replace with your actual API key
    var downloadUrl = "download-url-here";  // Replace with the actual download URL

    // Initialize API
    var api = new DebridAPI(apiKey);

    // Add download
    var download = api.addDownload(downloadUrl);

    // Download file
    var file = download.toFile(new File(download.getName()));

    // Print file path
    println("Downloaded file: " + file.getAbsolutePath());
}