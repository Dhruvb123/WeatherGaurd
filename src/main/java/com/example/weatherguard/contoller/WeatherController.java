package com.example.weatherguard.contoller;

import com.example.weatherguard.DTO.ResponseDTO;
import com.example.weatherguard.Entity.APIError;
import com.example.weatherguard.Entity.EventRequest;
import com.example.weatherguard.Entity.WeatherResponse;
import com.example.weatherguard.Services.IWeatherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> getEventForecast(@Valid @RequestBody EventRequest req, HttpServletRequest request){
        try {
            if (!isValidTimeRange(req.getStart_time(), req.getEnd_time())) {
                return ResponseEntity.badRequest().body("Start time must be before End time");
            }
            WeatherResponse res = _weatherService.getForecast(req);
            ResponseDTO resDto = _weatherService.modifyResponse(req, res);

            return ResponseEntity.ok().body(resDto);
        }
        catch(Exception ex){
            return buildError(500, "Internal Server Error", ex.getMessage(), request.getRequestURI());
        }
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

    private ResponseEntity<APIError> buildError(int status, String error, String message, String path) {
        APIError apiError = new APIError(
                LocalDateTime.now(),
                status,
                error,
                message,
                path
        );
        return ResponseEntity.status(status).body(apiError);
    }
}
