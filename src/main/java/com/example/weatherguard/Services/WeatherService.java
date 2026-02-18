package com.example.weatherguard.Services;

import com.example.weatherguard.DTO.ResponseDTO;
import com.example.weatherguard.Entity.EventRequest;
import com.example.weatherguard.Entity.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService implements IWeatherService{

    private final WebClient webClient;

    public WeatherService(@Value("${weather.api.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                                  .baseUrl(baseUrl)
                                  .build();
    }


    @Override
    public WeatherResponse getForecast(EventRequest req) {

        double lat = req.getLocation().getLatitude();
        double lon = req.getLocation().getLongitude();
        String start = req.getStart_time().substring(0,10);
        String end = req.getEnd_time().substring(0,10);

        WeatherResponse apiResponse =  webClient.get()
                                                .uri(uriBuilder -> uriBuilder
                                                .queryParam("latitude", lat)
                                                .queryParam("longitude", lon)
                                                .queryParam("start_date", start)
                                                .queryParam("end_date", end)
                                                .queryParam("hourly", "temperature_2m")
                                                .build())
                                        .retrieve()
                                        .bodyToMono(WeatherResponse.class)
                                        .block();

        return apiResponse;
    }

    @Override
    public ResponseDTO modifyResponse(WeatherResponse response) {
        ResponseDTO res = new ResponseDTO();

        return null;
    }
}
