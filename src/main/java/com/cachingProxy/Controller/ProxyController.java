package com.cachingProxy.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class ProxyController {

    private final Map<String, String> cache;
    private final String originUrl;
    private final RestTemplate restTemplate;

    public ProxyController(Map<String, String> cache, @Value("${origin.url}") String originUrl) {
        this.cache = cache;
        this.originUrl = originUrl;
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/**")
    public ResponseEntity<String> proxyRequest(@RequestHeader Map<String, String> headers,
                                               HttpServletRequest request) {
        String path = request.getRequestURI();
        String cacheKey = originUrl + path;

        if (cache.containsKey(cacheKey)) {
            return ResponseEntity.ok()
                    .header("X-Cache", "HIT")
                    .body(cache.get(cacheKey));
        } else {
            ResponseEntity<String> response = restTemplate.getForEntity(cacheKey, String.class);
            cache.put(cacheKey, response.getBody());
            return ResponseEntity.ok()
                    .header("X-Cache", "MISS")
                    .body(response.getBody());
        }
    }

    @DeleteMapping("/clear-cache")
    public ResponseEntity<String> clearCache() {
        cache.clear();
        return ResponseEntity.ok("Cache cleared successfully.");
    }
}
