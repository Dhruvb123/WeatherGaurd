package com.example.weatherguard.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventWindow {
    private String time;
    private int rain_prob;
    private float wind_kmh;
}
