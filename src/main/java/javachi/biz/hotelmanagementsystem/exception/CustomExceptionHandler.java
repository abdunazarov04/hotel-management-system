package javachi.biz.hotelmanagementsystem.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javachi.biz.hotelmanagementsystem.dto.ErrorDto;
import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpApiResponse<Void>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest()
                .body(
                        HttpApiResponse.<Void>builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .message("Validation Failed")
                                .errorList(
                                        ex.getBindingResult().getFieldErrors().stream().map(fieldError -> {
                                            String field = fieldError.getField();
                                            String rejectionValue = String.valueOf(fieldError.getRejectedValue());
                                            String defaultMessage = fieldError.getDefaultMessage();
                                            return new ErrorDto(field, String.format("%s, %s", defaultMessage, rejectionValue));
                                        }).toList()
                                )
                                .build()
                );
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<HttpApiResponse<Void>> customValidationException(CustomValidationException ex) {
        try {

            JsonNode jsonNode = objectMapper.readTree(ex.getMessage());
            List<ErrorDto> errorList = Arrays.asList(objectMapper.convertValue(jsonNode, ErrorDto[].class));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(HttpApiResponse.<Void>builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message("Validation Failed")
                            .errorList(errorList)
                            .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<HttpApiResponse<Void>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(HttpApiResponse.<Void>builder()
                        .message(e.getMessage())
                        .code(HttpStatus.NOT_FOUND.value())
                        .build());
    }


    @ExceptionHandler(
            value = {ContentNotFoundException.class, ResourceNotFoundException.class}
    )
    public ResponseEntity<HttpApiResponse<Void>> contentNotFoundException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(HttpApiResponse.<Void>builder()
                        .message(e.getMessage())
                        .code(HttpStatus.NOT_FOUND.value())
                        .build());
    }
    @ExceptionHandler(
            value = {BadCredentialsException.class, RuntimeException.class, InvocationTargetException.class}
    )
    public ResponseEntity<HttpApiResponse<Void>> badCredentialsException(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        HttpApiResponse.<Void>builder()
                                .message(e.getMessage())
                                .code(HttpStatus.UNAUTHORIZED.value())
                                .build())
                ;
    }
}
