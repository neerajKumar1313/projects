package com.example.fabricshoppingcart.ITTest.controller;

import com.example.fabricshoppingcart.model.Cart;
import com.example.fabricshoppingcart.model.Item;
import com.example.fabricshoppingcart.response.ApiResponse;

public class Repsonse {
    public static class ResponseCart extends ApiResponse<Cart> {
    }

    public static class ResponseItem extends ApiResponse<Item> {
    }

    public static class ResponseDelete extends ApiResponse<String> {
    }

    public static class ResponseTotalPrice extends ApiResponse<Double> {
    }
}
