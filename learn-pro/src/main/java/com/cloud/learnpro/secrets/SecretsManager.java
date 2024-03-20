package com.cloud.learnpro.secrets;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClient;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.transform.SecretValueEntryJsonUnmarshaller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class SecretsManager {

    public Secrets getSecret() throws JsonProcessingException {
        AWSSecretsManager client = AWSSecretsManagerClient.builder().build();

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest();
        getSecretValueRequest.setSecretId("term-assignment");
        GetSecretValueResult getSecretValueResult = client.getSecretValue(getSecretValueRequest);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getSecretValueResult.getSecretString() , Secrets.class);
    }


}
