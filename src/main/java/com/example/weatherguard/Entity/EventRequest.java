package com.example.weatherguard.Entity;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRequest {
    private String name;
    @NotNull
    private Location location;

    @NotNull(message = "Start time is required")
    private String start_time;

    @NotNull(message = "End time is required")
    private String end_time;

    @Getter
    @Setter
    public static class Location {
        @NotNull(message = "Latitude is required")
        @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
        @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
        private double latitude;

        @NotNull(message = "Longitude is required")
        @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
        @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
        private double longitude;
    }
}
