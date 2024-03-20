package com.cloud.learnpro.service;

import com.cloud.learnpro.request.ApiRequest;
import com.cloud.learnpro.response.ApiResponse;
import com.cloud.learnpro.secrets.Secrets;
import com.cloud.learnpro.secrets.SecretsManager;
import com.fasterxml.jackson.core.JsonProcessingException;
public class ApiService {

    private SecretsManager secretsManager = new SecretsManager();

    public ApiResponse manageApi(ApiRequest apiRequest) throws JsonProcessingException {

        Secrets secrets = secretsManager.getSecret();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("test");
        return apiResponse;
    }

}
