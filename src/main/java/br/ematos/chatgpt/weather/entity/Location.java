package br.ematos.chatgpt.weather.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    private String name;
    private String region;
    private String country;
    private double lat;
    private double lon;
    private String tzId;
    private long localtimeEpoch;
    private String localtime;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("lat")
    public double getLat() {
        return lat;
    }

    @JsonProperty("lon")
    public double getLon() {
        return lon;
    }

    @JsonProperty("tz_id")
    public String getTzId() {
        return tzId;
    }

    @JsonProperty("localtime_epoch")
    public long getLocaltimeEpoch() {
        return localtimeEpoch;
    }

    @JsonProperty("localtime")
    public String getLocaltime() {
        return localtime;
    }
}
