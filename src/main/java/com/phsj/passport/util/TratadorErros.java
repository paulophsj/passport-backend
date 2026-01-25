package com.phsj.passport.util;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class TratadorErros {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> tratarErro500(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("fieldName", ((FieldError) error).getField());
            errorDetails.put("defaultMessage", error.getDefaultMessage());
            errors.add(errorDetails);
        });

        response.put("message", "Erro de validação nos campos enviados.");
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleEnumException(HttpMessageNotReadableException ex) {
        Map<String, Object> response = new HashMap<>();
        String message = "Erro na leitura do JSON: Valor inválido enviado.";

        if (ex.getCause() instanceof InvalidFormatException ife) {
            if (ife.getTargetType().isEnum()) {
                String valorEnviado = ife.getValue().toString();
                String campo = ife.getPath().get(0).getPropertyName();
                String valoresAceitos = Arrays.toString(ife.getTargetType().getEnumConstants());

                message = String.format("O valor '%s' é inválido para o campo '%s'. Valores aceitos: %s",
                        valorEnviado, campo, valoresAceitos);
            }
        }

        response.put("message", message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
