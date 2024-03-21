package com.cloud.learnpro.response;

import java.util.List;

public class ApiResponse {

    private String message;

    private List<RoadMap> roadMaps;

    private RoadMap roadMap;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RoadMap> getRoadMaps() {
        return roadMaps;
    }

    public void setRoadMaps(List<RoadMap> roadMaps) {
        this.roadMaps = roadMaps;
    }

    public RoadMap getRoadMap() {
        return roadMap;
    }

    public void setRoadMap(RoadMap roadMap) {
        this.roadMap = roadMap;
    }
}
