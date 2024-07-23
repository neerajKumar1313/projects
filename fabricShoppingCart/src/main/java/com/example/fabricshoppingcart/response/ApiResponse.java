package com.example.fabricshoppingcart.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private T data;


}
