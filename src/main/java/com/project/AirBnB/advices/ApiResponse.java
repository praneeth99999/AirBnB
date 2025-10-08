package com.project.AirBnB.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

    private ApiError Error;
    @JsonFormat(pattern = "hh-mm-ss dd-MM-YYYY")
    private LocalDateTime timeStamp;
    private T data;

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError Error) {
        this();
        this.Error = Error;
    }


    public ApiResponse() {
       this.timeStamp= LocalDateTime.now();
    }
}
