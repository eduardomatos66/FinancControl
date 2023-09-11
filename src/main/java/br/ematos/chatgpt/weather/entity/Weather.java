package br.ematos.chatgpt.weather.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {
    private Location location;
    private Current current;

    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    @JsonProperty("current")
    public Current getCurrent() {
        return current;
    }
}