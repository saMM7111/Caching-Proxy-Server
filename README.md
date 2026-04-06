# Caching-Proxy-Server

A simple, lightweight Caching Proxy Server built with Java 17 and Spring Boot.

## Features

- **Proxy Requests:** Forwards incoming HTTP GET requests to a configured origin server.
- **Caching:** Caches the responses from the origin server. Subsequent requests for the same path return the cached response, reducing load on the origin and speeding up response times.
- **Cache Headers:** Includes an `X-Cache` header in the response indicating whether the response was a cache `HIT` or a cache `MISS`.
- **Clear Cache:** Provides an endpoint to manually clear the server cache.

## Prerequisites

- [Java 17](https://jdk.java.net/17/) or higher
- [Maven](https://maven.apache.org/) (or use the provided `mvnw` wrapper)

## Configuration

Before running the server, configure the target origin URL in your `src/main/resources/application.properties` file:

```properties
# Target origin server URL
origin.url=http://example.com
```

## Running the Application

You can run the application using the Maven wrapper included in the project:

```bash
# On Windows
./mvnw.cmd spring-boot:run

# On Linux/macOS
./mvnw spring-boot:run
```

Alternatively, you can build a JAR and run it:

```bash
./mvnw clean package
java -jar target/Caching\ Proxy-0.0.1-SNAPSHOT.jar
```

## Endpoints

### 1. Proxy Requests
- **Method:** `GET`
- **Path:** `/**` (Any path)
- **Description:** Forwards the request to `origin.url + {path}`.
- **Response Headers:** `X-Cache: HIT` (if served from cache), or `X-Cache: MISS` (if fetched from the origin).

### 2. Clear Cache
- **Method:** `DELETE`
- **Path:** `/clear-cache`
- **Description:** Clears all entries from the proxy cache.
- **Returns:** `"Cache cleared successfully."`