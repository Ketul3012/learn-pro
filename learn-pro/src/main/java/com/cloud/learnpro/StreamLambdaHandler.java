package com.cloud.learnpro;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cloud.learnpro.request.ApiRequest;
import com.cloud.learnpro.response.ApiResponse;
import com.cloud.learnpro.service.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;

public class StreamLambdaHandler implements RequestHandler<ApiRequest, ApiResponse> {

    private final ApiService apiService = new ApiService();

    @Override
    public ApiResponse handleRequest(ApiRequest apiRequest, Context context) {
        try {
            return apiService.manageApi(apiRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
