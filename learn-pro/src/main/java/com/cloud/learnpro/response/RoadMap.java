package com.cloud.learnpro.response;

import java.util.Date;
import java.util.List;

public class RoadMap {

    private Integer id;
    private String email;
    private Date createdOn;
    private List<RoadMapItem> roadMapItems;

    private String prompt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public List<RoadMapItem> getRoadMapItems() {
        return roadMapItems;
    }

    public void setRoadMapItems(List<RoadMapItem> roadMapItems) {
        this.roadMapItems = roadMapItems;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public static class RoadMapItem {

        private Integer dayNumber;
        private String title;
        private List<String> tasks;
        private List<String> resources;

        public Integer getDayNumber() {
            return dayNumber;
        }

        public void setDayNumber(Integer dayNumber) {
            this.dayNumber = dayNumber;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getTasks() {
            return tasks;
        }

        public void setTasks(List<String> tasks) {
            this.tasks = tasks;
        }

        public List<String> getResources() {
            return resources;
        }

        public void setResources(List<String> resources) {
            this.resources = resources;
        }
    }
}
