package com.project.AirBnB.advices;


import com.project.AirBnB.exception.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceException(ResourceNotFoundException exception)
    {
        ApiError Error=ApiError.builder().status(HttpStatus.NOT_FOUND).message(exception.getMessage()).build();
        return buildErrorResponseEntity(Error);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError Error) {
        return new ResponseEntity<>(new ApiResponse<>(Error),Error.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError>handleException(Exception exception)
    {
        ApiError apiError=ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>>handleArguments(MethodArgumentNotValidException methodArgumentNotValidException)
    {
        List<String> errors=methodArgumentNotValidException
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ApiError apiError=ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input Information Not Matches Requirment")
                .errors(errors)
                .build();

        return  buildErrorResponseEntity(apiError);
    }

}
