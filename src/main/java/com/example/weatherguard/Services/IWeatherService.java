package com.example.weatherguard.Services;

import com.example.weatherguard.DTO.ResponseDTO;
import com.example.weatherguard.Entity.EventRequest;
import com.example.weatherguard.Entity.WeatherResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public interface IWeatherService {
    Mono<WeatherResponse> getForecast(EventRequest req);
    ResponseDTO modifyResponse(EventRequest req, WeatherResponse response);
}
