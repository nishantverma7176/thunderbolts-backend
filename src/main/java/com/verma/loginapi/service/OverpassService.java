package com.verma.loginapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.verma.loginapi.dto.LocationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OverpassService {

    private final WebClient webClient;

    public OverpassService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://overpass-api.de/api")
                .build();
    }

    public List<LocationResponse> findNearbyHospitals(double lat, double lon, int radiusMeters) throws JsonProcessingException {
        String query = """
                [out:json];
                (
                    node["amenity"="hospital"](around:%d,%f,%f);
                    node["amenity"="clinic"](around:%d,%f,%f);
                    node["amenity"="pharmacy"](around:%d,%f,%f);
                );
                out body;
                """.formatted(radiusMeters, lat, lon, radiusMeters, lat, lon, radiusMeters, lat, lon);

        String response = webClient.post()
                .uri("/interpreter")
                .bodyValue("data=" + query)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> {
                    log.error("Error during Overpass API call", e);
                    return Mono.just("{}");
                })
                .block();

        return parseOverpassResponse(response);
    }

    private List<LocationResponse> parseOverpassResponse(String json) throws JsonProcessingException {
        List<LocationResponse> results = new ArrayList<>();

        Map<?, ?> data = com.fasterxml.jackson.databind.json.JsonMapper.builder()
                .build()
                .readValue(json, Map.class);

        List<Map<String, Object>> elements = (List<Map<String, Object>>) data.get("elements");

        if (elements == null) return results;

        for (Map<String, Object> e : elements) {
            Map<String, String> tags = (Map<String, String>) e.get("tags");

            if (tags == null || !tags.containsKey("name")) continue;

            LocationResponse lr = new LocationResponse();
            lr.setName(tags.get("name"));
            lr.setLat((Double) e.get("lat"));
            lr.setLon((Double) e.get("lon"));
            lr.setType(tags.get("amenity"));

            results.add(lr);
        }

        return results;
    }
}