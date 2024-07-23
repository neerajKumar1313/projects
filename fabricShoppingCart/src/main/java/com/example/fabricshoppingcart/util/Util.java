package com.example.fabricshoppingcart.util;

import com.example.fabricshoppingcart.response.ApiResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class Util {
    public static ResponseEntity<ApiResponse> createResponse(Object object, String status, int statusCode) {
        ApiResponse apiResponse = ApiResponse.builder().
                data(object).
                status(status)
                .statusCode(statusCode)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(statusCode));
    }
}
