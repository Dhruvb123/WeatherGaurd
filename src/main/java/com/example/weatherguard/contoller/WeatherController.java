package com.example.weatherguard.contoller;

import com.example.weatherguard.DTO.ResponseDTO;
import com.example.weatherguard.Entity.APIError;
import com.example.weatherguard.Entity.EventRequest;
import com.example.weatherguard.Services.IWeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/event-forecast")
public class WeatherController {

    private final IWeatherService _weatherService;

    public WeatherController(IWeatherService weatherService){
        _weatherService = weatherService;
    }
    @PostMapping
    public CompletableFuture<ResponseEntity<Object>> getEventForecast(@RequestBody EventRequest req){

        return _weatherService.getForecast(req)
                .thenApply(response-> {

                    return ResponseEntity.ok((Object)_weatherService.modifyResponse(response));
                })
                .exceptionally(ex -> {

                    APIError error = new APIError(
                            LocalDateTime.now(),
                            500,
                            "Internal Server Error: ",
                            ex.getMessage(),
                            "api/event-forecast"
                    );

                    return ResponseEntity.status(500).body((Object)error);
                });
    }
}
