package com.apodbackend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.apodbackend.service.NasaApodService;
import com.apodbackend.model.ApodEntry;

import java.util.List;

@RestController
@RequestMapping("/api/apod")
@CrossOrigin(origins = "http://localhost:5173")
public class ApodController {

    private final NasaApodService service;

    @Autowired
    public ApodController(NasaApodService service) {
        this.service = service;
    }

    @GetMapping("/today")
    public ResponseEntity<?> getToday() {
        try {
            ApodEntry e = service.getToday();
            return ResponseEntity.ok(e);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(502).body(java.util.Map.of("error", "Failed to fetch APOD"));
        }
    }
    

    @GetMapping("/date")
    public ResponseEntity<?> getByDate(@RequestParam String date) {
        if (date == null || date.isBlank()) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", "Missing date param"));
        }
        try {
            ApodEntry e = service.getByDate(date);
            return ResponseEntity.ok(e);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(502).body(java.util.Map.of("error", "Failed to fetch APOD by date"));
        }
    }

    
    
    @GetMapping("/range")
    public ResponseEntity<?> getRange(@RequestParam String start, @RequestParam String end) {
        if (start == null || end == null) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", "Missing start or end param"));
        }
        try {
            java.time.LocalDate s = java.time.LocalDate.parse(start);
            java.time.LocalDate e = java.time.LocalDate.parse(end);
            long days = java.time.Duration.between(s.atStartOfDay(), e.atStartOfDay()).toDays() + 1;
            if (days > 30) {
                return ResponseEntity.badRequest().body(java.util.Map.of("error", "Range too large (max 30 days)"));
            }

            List<ApodEntry> list = service.getRange(start, end);
            return ResponseEntity.ok(list);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(502).body(java.util.Map.of("error", "Failed to fetch APOD range"));
        }
    }
}
