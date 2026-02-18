package com.example.weatherguard.Services;

import com.example.weatherguard.DTO.ResponseDTO;
import com.example.weatherguard.Entity.EventRequest;
import com.example.weatherguard.Entity.WeatherResponse;

import java.util.concurrent.CompletableFuture;

public interface IWeatherService {
    WeatherResponse getForecast(EventRequest req);
    ResponseDTO modifyResponse(WeatherResponse response);
}
