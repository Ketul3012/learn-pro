package com.cloud.learnpro;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cloud.learnpro.request.ApiRequest;
import com.cloud.learnpro.response.ApiResponse;
import com.cloud.learnpro.service.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class StreamLambdaHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final ApiService apiService = new ApiService();

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> apiRequest, Context context) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ApiRequest apiRequest1 = objectMapper.readValue(apiRequest.getOrDefault("body", "").toString(), ApiRequest.class);
            ApiResponse apiResponse = apiService.manageApi(apiRequest1);

            Map<String, Object> response = new HashMap<>();
            response.put("statusCode", 200);
            response.put("headers", Map.of("Content-Type", "application/json"));
            response.put("body", objectMapper.writeValueAsString(apiResponse));
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
