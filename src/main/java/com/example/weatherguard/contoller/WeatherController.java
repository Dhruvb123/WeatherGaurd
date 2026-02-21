package com.example.weatherguard.contoller;

import com.example.weatherguard.DTO.ResponseDTO;
import com.example.weatherguard.Entity.APIResponse;
import com.example.weatherguard.Entity.EventRequest;
import com.example.weatherguard.Services.IWeatherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/event-forecast")
public class WeatherController {

    private final IWeatherService _weatherService;

    public WeatherController(IWeatherService weatherService){
        _weatherService = weatherService;
    }

    @PostMapping
    public Mono<ResponseEntity<APIResponse<Object>>> getEventForecast(@Valid @RequestBody EventRequest req) {
        if (!isValidTimeRange(req.getStart_time(), req.getEnd_time())) {
            return Mono.just(ResponseEntity.badRequest().body(
                    APIResponse.builder()
                            .status(400)
                            .message("Validation failed")
                            .error("Start time must be before End time")
                            .timestamp(LocalDateTime.now())
                            .build()
            ));
        }

        return _weatherService.getForecast(req)
                .flatMap(res -> {
                    ResponseDTO resDto = _weatherService.modifyResponse(req, res);
                    return Mono.just(ResponseEntity.ok().body(
                            APIResponse.builder()
                                    .status(200)
                                    .message("Success")
                                    .body(resDto)
                                    .timestamp(LocalDateTime.now())
                                    .build()));
                })
                .onErrorResume(ex -> Mono.just(
                        ResponseEntity.status(500).body(
                                APIResponse.builder()
                                        .status(500)
                                        .message("Internal Server Error")
                                        .error(ex.getMessage())
                                        .timestamp(LocalDateTime.now())
                                        .build()
                        )
                ));
    }

    private boolean isValidTimeRange(String startTime, String endTime) {
        try {
            LocalDateTime start = LocalDateTime.parse(startTime);
            LocalDateTime end = LocalDateTime.parse(endTime);
            return start.isBefore(end);
        } catch (Exception e) {
            return false;
        }
    }
}
