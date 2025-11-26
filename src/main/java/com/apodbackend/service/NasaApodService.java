package com.apodbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.apodbackend.model.ApodEntry;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service to fetch data from NASA APOD API
 */
@Service
public class NasaApodService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${nasa.api.key}")
    private String apiKey;

    private static final String BASE_URL = "https://api.nasa.gov/planetary/apod";

    @Cacheable("apod")
    public ApodEntry getToday() {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("api_key", apiKey)
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return mapToApodEntry(response);
    }

    @Cacheable(value = "apod", key = "#date")
    public ApodEntry getByDate(String date) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("api_key", apiKey)
                .queryParam("date", date)
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return mapToApodEntry(response);
    }

    public List<ApodEntry> getRange(String start, String end) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("api_key", apiKey)
                .queryParam("start_date", start)
                .queryParam("end_date", end)
                .toUriString();

        List<Map<String, Object>> responseList = restTemplate.getForObject(url, List.class);

        List<ApodEntry> result = new ArrayList<>();
        if (responseList != null) {
            for (Map<String, Object> map : responseList) {
                result.add(mapToApodEntry(map));
            }
        }
        return result;
    }

    private ApodEntry mapToApodEntry(Map<String, Object> map) {
        ApodEntry entry = new ApodEntry();
        if (map == null) return entry;

        entry.setDate((String) map.get("date"));
        entry.setTitle((String) map.get("title"));
        entry.setExplanation((String) map.get("explanation"));
        entry.setUrl((String) map.get("url"));
        entry.setHdurl((String) map.get("hdurl"));
        entry.setMediaType((String) map.get("media_type"));
        entry.setCopyright((String) map.get("copyright"));
        entry.setFetchedAt(Instant.now());
        return entry;
    }
}
