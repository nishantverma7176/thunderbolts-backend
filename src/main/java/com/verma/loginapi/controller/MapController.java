package com.verma.loginapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.verma.loginapi.dto.LocationResponse;
import com.verma.loginapi.service.OverpassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maps")
@RequiredArgsConstructor
public class MapController {

    private final OverpassService overpassService;

    @GetMapping("/nearby")
    public List<LocationResponse> getNearbyLocations(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "5000") int radius
    ) throws JsonProcessingException {
        return overpassService.findNearbyHospitals(lat, lon, radius);
    }
}
