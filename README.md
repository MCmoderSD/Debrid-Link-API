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
    <version>1.1.4</version>
</dependency>
```

### Example

```java
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
```