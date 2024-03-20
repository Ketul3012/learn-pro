package com.cloud.learnpro;

import com.cloud.learnpro.request.ApiRequest;

public class LearnProApplication {

    static StreamLambdaHandler streamLambdaHandler = new StreamLambdaHandler();

    public static void main(String[] args) {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setType("post");
        apiRequest.setEmail("ketulpatel3012@gmail.com");
        apiRequest.setMessage("Want to learn dancing");
        System.out.println(streamLambdaHandler.handleRequest(apiRequest, null).getMessage());
    }


}
