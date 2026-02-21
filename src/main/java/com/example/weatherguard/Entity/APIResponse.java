package com.example.weatherguard.Entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponse<T> {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private T body;
}
