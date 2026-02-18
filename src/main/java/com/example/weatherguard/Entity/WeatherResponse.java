package com.example.weatherguard.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {
    private double latitude;
    private double longitude;
    private double generationtime_ms;
    private int utc_offset_seconds;
    private String timezone;
    private String timezone_abbreviation;
    public double elevation;
    public HourlyUnits hourlyUnits;
    public Hourly hourly;

    @Getter
    @Setter
    public static class HourlyUnits {
        public String time;
        public String temperature_2m;
    }

    @Getter
    @Setter
    public static class Hourly {
        public List<String> time;
        public List<Double> temperature_2m;
    }
}
