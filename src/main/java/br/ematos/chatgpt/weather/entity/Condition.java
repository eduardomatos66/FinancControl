package br.ematos.chatgpt.weather.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Condition {
    private String text;
    private String icon;
    private int code;

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    @JsonProperty("code")
    public int getCode() {
        return code;
    }
}
