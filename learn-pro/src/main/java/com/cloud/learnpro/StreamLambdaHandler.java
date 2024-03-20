package com.cloud.learnpro;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.cloud.learnpro.request.ApiRequest;
import com.cloud.learnpro.response.ApiResponse;
import com.cloud.learnpro.service.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamLambdaHandler implements RequestHandler<ApiRequest, ApiResponse> {

    private ApiService apiService = new ApiService();

    @Override
    public ApiResponse handleRequest(ApiRequest apiRequest, Context context) {
        try {
            return apiService.manageApi(apiRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
