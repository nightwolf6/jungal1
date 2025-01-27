package com.example.demo.Application.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TripAdvisorService {

    @Value("${tripadvisor.api.key}")
    private String apiKey;

    private final String baseUrl = "https://api.content.tripadvisor.com/api/v1/location/";

    public String getReviews(String locationId) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + locationId + "/reviews")
                .queryParam("language", "en")
                .queryParam("key", apiKey)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
