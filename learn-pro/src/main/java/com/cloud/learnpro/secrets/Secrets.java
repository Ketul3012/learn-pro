package com.cloud.learnpro.secrets;

public class Secrets {

    private String chatGptApiKey;

    private String tableName;

    private String emailFrom;

    private String servicesRegion;

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

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getServicesRegion() {
        return servicesRegion;
    }

    public void setServicesRegion(String servicesRegion) {
        this.servicesRegion = servicesRegion;
    }
}
