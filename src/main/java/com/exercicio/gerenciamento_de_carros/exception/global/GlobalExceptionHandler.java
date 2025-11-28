package com.exercicio.gerenciamento_de_carros.exception.global;

import com.exercicio.gerenciamento_de_carros.exception.ActiveNotActiveException;
import com.exercicio.gerenciamento_de_carros.exception.EmailUniqueViolationException;
import com.exercicio.gerenciamento_de_carros.exception.EntityNotFoundException;
import com.exercicio.gerenciamento_de_carros.exception.PasswordIncorrectException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Classe GlobalExceptionHandler
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Validação de campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request,
            BindingResult result) {

        log.error("Api Error - ", ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campos invalidos", result));
    }

    //Violação de email único
    @ExceptionHandler(EmailUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> uniqueViolationException(
            RuntimeException ex,
            HttpServletRequest request) {

        log.error("Api Error - ", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    //Entidade não encontrada
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(
            RuntimeException ex,
            HttpServletRequest request) {

        log.error("Api Error - ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    //Senha incorreta
    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<ErrorMessage> passwordIncorrectException(
            RuntimeException ex,
            HttpServletRequest request) {

        log.error("Api Error - ", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    //Não está ativo
    @ExceptionHandler(ActiveNotActiveException.class)
    public ResponseEntity<ErrorMessage> activeNotActiveException(
            RuntimeException ex,
            HttpServletRequest request) {

        log.error("Api Error - ", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
    }
}

