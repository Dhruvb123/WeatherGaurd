package com.example.weatherguard.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRequest {
    private String name;
    private Location location;
    private String start_time;
    private String end_time;

    @Getter
    @Setter
    public static class Location {
        private double latitude;
        private double longitude;
    }
}
