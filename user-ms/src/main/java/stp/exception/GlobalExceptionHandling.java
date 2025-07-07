package stp.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandling {
//    private Map<String, String> errors = new HashMap<>();


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception ex,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        log.error("Exception occurred: {}", ex.getClass().getSimpleName(), ex);

        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(errors)
                .timestamp(LocalDateTime.now().toString())
                .path(request.getRequestURI())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException ex,
                                                                           HttpServletRequest request,
                                                                           HttpServletResponse response) {
        log.error("Constraint violation exception");
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            String field = error.getPropertyPath().toString();
            String message = error.getMessage();
            errors.put(field, message);
        });
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(errors)
                .timestamp(LocalDateTime.now().toString())
                .path(request.getRequestURI())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                     HttpServletRequest request,
                                                                     HttpServletResponse response) {
        Map<String, String> errors = new HashMap<>();
        {
            ex.getBindingResult().getFieldErrors().forEach(error -> {
                String field = error.getField();
                String message = error.getDefaultMessage();
                errors.put(field, message);
            });
            ErrorMessage errorMessage = ErrorMessage.builder().
                    message(errors).
                    status(HttpStatus.NOT_FOUND.value()).
                    path(request.getRequestURI()).
                    timestamp(LocalDateTime.now().toString())
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
//
//        String rootMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
//        if (rootMessage != null && rootMessage.contains("unique constraint") && rootMessage.contains("email")) {
//            return ResponseEntity.badRequest().body("Bu email artıq mövcuddur.");
//        }
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serverdə xəta baş verdi.");
//    }
}
