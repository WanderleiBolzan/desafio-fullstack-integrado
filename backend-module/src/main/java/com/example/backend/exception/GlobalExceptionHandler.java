package com.example.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleBusinessError(RuntimeException ex) {
        // Retorna 400 com a mensagem da sua regra de negócio (ex: "Saldo insuficiente")
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
}