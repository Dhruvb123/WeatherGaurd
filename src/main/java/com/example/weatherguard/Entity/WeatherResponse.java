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
    public Hourly_Units hourlyUnits;
    public Hourly hourly;

    @Getter
    @Setter
    public static class Hourly_Units {
        public String time;
        public String temperature_2m;
    }

    @Getter
    @Setter
    public static class Hourly {
        public List<String> time;
        public List<Double> temperature_2m;
        public List<Integer> precipitation_probability;
        public List<Float> windspeed_10m;
        public List<Integer> weathercode;
    }
}
