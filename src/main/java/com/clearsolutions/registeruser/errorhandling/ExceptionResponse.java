package com.clearsolutions.registeruser.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
class ExceptionResponse {
    private String message;
    private LocalDateTime dateTime;
}
