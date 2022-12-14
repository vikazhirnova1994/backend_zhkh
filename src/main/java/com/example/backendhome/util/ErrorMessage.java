package com.example.backendhome.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private  String url;
    private  ErrorType type;
    private String message;
    private LocalDateTime timestamp;
}
