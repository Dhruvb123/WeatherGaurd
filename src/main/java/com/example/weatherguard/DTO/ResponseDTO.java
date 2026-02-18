package com.example.weatherguard.DTO;

import com.example.weatherguard.Entity.EventWindow;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ResponseDTO {
    private String classification;
    private String summary;
    private List<String> reason;
    private List<EventWindow> event_window_forecast;
}
