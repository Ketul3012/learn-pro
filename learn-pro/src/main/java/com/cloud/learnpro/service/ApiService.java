package com.cloud.learnpro.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.cloud.learnpro.request.ApiRequest;
import com.cloud.learnpro.response.ApiResponse;
import com.cloud.learnpro.response.RoadMap;
import com.cloud.learnpro.secrets.Secrets;
import com.cloud.learnpro.secrets.SecretsManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiService {

    private final SecretsManager secretsManager = new SecretsManager();

    public List<RoadMap.RoadMapItem> parseRoadmap(String chatResponse) {
        List<RoadMap.RoadMapItem> roadmapItems = new ArrayList<>();

        String[] days = chatResponse.split("\n\n");

        for (int j = 0; j < days.length; j += 2) {
            // Extract day number
            Pattern dayNumberPattern = Pattern.compile("Day (\\d+):");
            Matcher dayNumberMatcher = dayNumberPattern.matcher(days[j]);
            if (dayNumberMatcher.find()) {
                int dayNumber = Integer.parseInt(dayNumberMatcher.group(1));

                // Extract title
                String[] lines = (days[j] + "\n" + days[j + 1]).split("\n");
                String title = lines[0].trim();

                // Extract tasks
                List<String> tasks = new ArrayList<>();
                for (int i = 1; i < lines.length; i++) {
                    String line = lines[i].trim();
                    if (line.startsWith("Tasks:")) {
                        while (i < lines.length - 1 && !lines[i + 1].startsWith("Resources:")) {
                            tasks.add(lines[i + 1].trim());
                            i++;
                        }
                    }
                }

                // Extract resources
                List<String> resources = new ArrayList<>();
                for (int i = 1; i < lines.length; i++) {
                    String line = lines[i].trim();
                    if (line.startsWith("Resources:")) {
                        while (i < lines.length - 1) {
                            resources.add(lines[i + 1].trim());
                            i++;
                        }
                    }
                }


                RoadMap.RoadMapItem roadMapItem = new RoadMap.RoadMapItem();
                roadMapItem.setDayNumber(dayNumber);
                roadMapItem.setTitle(title);
                roadMapItem.setTasks(tasks);
                roadMapItem.setResources(resources);
                roadmapItems.add(roadMapItem);
            }
        }

        return roadmapItems;
    }

    public List<RoadMap.RoadMapItem> getChatGPTResponse(String userPrompt, String email, Secrets secrets) {
        String url = "https://api.openai.com/v1/chat/completions";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + secrets.getChatGptApiKey());
            connection.setRequestProperty("Content-Type", "application/json");

            // Create the request body
            String requestBody = "{\n    \"model\": \"gpt-3.5-turbo\",\n    \"messages\": [\n        {\n            \"role\": \"system\",\n            \"content\": \"You are a helpful assistant. Please guide user who wants to learn new things by providing detailed output\"\n        },\n        {\n            \"role\": \"user\",\n            \"content\": \"Create a comprehensive learning roadmap for mastering `" + userPrompt + "`. Include resources, day-by-day tasks, and milestones. Give output day by day with day numbers, there should be no mention of week in response and output should be in way that as a programmer from text I can easily extract data like Day Number and associated message with that day. Also include references and link in daywise response. I want response to be like Day X : Title\\nlist of tasks\\nlist of resources.\"\n}\n    ]\n}";

            // Send the request
            connection.setDoOutput(true);
            connection.getOutputStream().write(requestBody.getBytes());

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.toString());
            String chatResponse = jsonNode.get("choices").get(0).get("message").get("content").asText();
            sendEmailResponse(chatResponse, email, userPrompt, secrets);
            return parseRoadmap(chatResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void sendEmailResponse(String chatResponse, String email, String prompt, Secrets secrets) {
        AmazonSimpleEmailService amazonSimpleEmailService = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(secrets.getServicesRegion()).build();
        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setDestination(new Destination().withToAddresses(email));
        sendEmailRequest.setSource(secrets.getEmailFrom());
        Message message = new Message();
        message.setSubject(new Content().withCharset("UTF-8").withData("Your roadmap for " + prompt));
        message.setBody(new Body().withText(new Content().withCharset("UTF-8").withData(chatResponse)));
        sendEmailRequest.setMessage(message);
        amazonSimpleEmailService.sendEmail(sendEmailRequest);
    }

    public ApiResponse manageApi(ApiRequest apiRequest) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse();
        Secrets secrets = secretsManager.getSecret();

        DynamoDB dynamoDB = getDynamoDB();

        if (apiRequest.getType().equals("get")) {
            apiResponse.setRoadMaps(getUserItems(apiRequest.getEmail(), dynamoDB, secrets));
            apiResponse.setMessage("Fetched data successfully");
        } else if (apiRequest.getType().equals("post")) {
            List<RoadMap.RoadMapItem> roadMapItems = getChatGPTResponse(apiRequest.getMessage(), apiRequest.getEmail(), secrets);
            createRoadMap(apiRequest.getEmail(), roadMapItems, dynamoDB, secrets);
            apiResponse.setMessage("Successfully initiated roadmap creation");
        } else {
            apiResponse.setMessage("Invalid type of request");
        }

        return apiResponse;
    }

    private void createRoadMap(String email, List<RoadMap.RoadMapItem> roadMaps, DynamoDB dynamoDB, Secrets secrets) {

        Table table = dynamoDB.getTable(secrets.getTableName());

        List<Map<String, Object>> roadMapItems = new ArrayList<>();

        for (RoadMap.RoadMapItem roadMapItem : roadMaps) {
            Map<String, Object> roadMap = new HashMap<>();
            roadMap.put("day", roadMapItem.getDayNumber());
            roadMap.put("title", roadMapItem.getTitle());
            roadMap.put("tasks", roadMapItem.getTasks());
            roadMap.put("resources", roadMapItem.getResources());
            roadMapItems.add(roadMap);
        }
        Random random = new Random();
        random.nextInt(0, Integer.MAX_VALUE);
        Item item = new Item()
                .withPrimaryKey("id", random.nextInt())
                .withString("email", email)
                .withLong("createdOn", System.currentTimeMillis())
                .withList("roadMapItems", roadMapItems);

        table.putItem(item);
    }

    private DynamoDB getDynamoDB() {
        return new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    }

    private List<RoadMap> getUserItems(String email, DynamoDB dynamoDB, Secrets secrets) {
        List<RoadMap> userItems = new ArrayList<>();
        Table table = dynamoDB.getTable(secrets.getTableName());

        ScanSpec scanSpec = new ScanSpec();
        ScanFilter scanFilter = new ScanFilter("email").eq(email);
        scanSpec.withScanFilters(scanFilter);

        ItemCollection<ScanOutcome> result = table.scan(scanSpec);

        result.iterator().forEachRemaining(item -> {
            Integer id = item.getInt("id");
            String itemEmail = item.getString("email");
            Date createdOn = new Date(item.getLong("createdOn"));
            List<RoadMap.RoadMapItem> roadMapItems = new ArrayList<>();

            List<Map<String, Object>> rawRoadMapItems = item.getList("roadMapItems");
            for (Map<String, Object> rawItem : rawRoadMapItems) {
                Integer dayNumber = (Integer) rawItem.get("day");
                String title = (String) rawItem.get("title");
                List<String> tasks = (List<String>) rawItem.getOrDefault("tasks", new ArrayList<>());
                List<String> resources = (List<String>) rawItem.getOrDefault("resources", new ArrayList<>());
                RoadMap.RoadMapItem roadMapItem = new RoadMap.RoadMapItem();
                roadMapItem.setDayNumber(dayNumber);
                roadMapItem.setTitle(title);
                roadMapItem.setTasks(tasks);
                roadMapItem.setResources(resources);
                roadMapItems.add(roadMapItem);
            }

            RoadMap roadMap = new RoadMap();
            roadMap.setId(id);
            roadMap.setEmail(itemEmail);
            roadMap.setCreatedOn(createdOn);
            roadMap.setRoadMapItems(roadMapItems);
            userItems.add(roadMap);

        });
        return userItems;
    }


}
