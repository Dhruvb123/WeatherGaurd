package com.example.weatherguard.Entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
