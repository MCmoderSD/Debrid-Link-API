# Debrid-Link API

## Description
The Debrid-Link API makes it possible to download fast from various file hosters.
Currently, this API only supports the `/download/add` endpoint.

You can find the official documentation [here](https://debrid-link.com/api_doc/v2/introduction).
Debrid Supports most file hosters out there, for most of them you need a premium account.
You can find the list of supported hosters [here](https://debrid-link.com/webapp/status).

## Usage

### Maven
Make sure you have my Sonatype Nexus OSS repository added to your `pom.xml` file:
```xml
<repositories>
    <repository>
        <id>Nexus</id>
        <name>Sonatype Nexus</name>
        <url>https://mcmodersd.de/nexus/repository/maven-releases/</url>
    </repository>
</repositories>
```
Add the dependency to your `pom.xml` file:
```xml
<dependency>
    <groupId>de.MCmoderSD</groupId>
    <artifactId>Debrid-Link-API</artifactId>
    <version>1.1.1</version>
</dependency>
```

### Example

```java
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
```