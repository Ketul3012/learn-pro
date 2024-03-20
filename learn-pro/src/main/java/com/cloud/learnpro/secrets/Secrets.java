package com.cloud.learnpro.secrets;

public class Secrets {

    private String chatGptApiKey;

    private String tableName;

    public String getChatGptApiKey() {
        return chatGptApiKey;
    }

    public void setChatGptApiKey(String chatGptApiKey) {
        this.chatGptApiKey = chatGptApiKey;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
