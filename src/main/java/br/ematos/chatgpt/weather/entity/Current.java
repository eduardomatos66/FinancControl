package br.ematos.chatgpt.weather.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Current {
    private long lastUpdatedEpoch;
    private String lastUpdated;
    private double tempC;
    private double tempF;
    private int isDay;
    private Condition condition;
    private double windMph;
    private double windKph;
    private int windDegree;
    private String windDir;
    private double pressureMb;
    private double pressureIn;
    private double precipMm;
    private double precipIn;
    private int humidity;
    private int cloud;
    private double feelslikeC;
    private double feelslikeF;
    private double visKm;
    private double visMiles;
    private double uv;
    private double gustMph;
    private double gustKph;

    @JsonProperty("last_updated_epoch")
    public long getLastUpdatedEpoch() {
        return lastUpdatedEpoch;
    }

    @JsonProperty("last_updated")
    public String getLastUpdated() {
        return lastUpdated;
    }

    @JsonProperty("temp_c")
    public double getTempC() {
        return tempC;
    }

    @JsonProperty("temp_f")
    public double getTempF() {
        return tempF;
    }

    @JsonProperty("is_day")
    public int getIsDay() {
        return isDay;
    }

    @JsonProperty("condition")
    public Condition getCondition() {
        return condition;
    }

    @JsonProperty("wind_mph")
    public double getWindMph() {
        return windMph;
    }

    @JsonProperty("wind_kph")
    public double getWindKph() {
        return windKph;
    }

    @JsonProperty("wind_degree")
    public int getWindDegree() {
        return windDegree;
    }

    @JsonProperty("wind_dir")
    public String getWindDir() {
        return windDir;
    }

    @JsonProperty("pressure_mb")
    public double getPressureMb() {
        return pressureMb;
    }

    @JsonProperty("pressure_in")
    public double getPressureIn() {
        return pressureIn;
    }

    @JsonProperty("precip_mm")
    public double getPrecipMm() {
        return precipMm;
    }

    @JsonProperty("precip_in")
    public double getPrecipIn() {
        return precipIn;
    }

    @JsonProperty("humidity")
    public int getHumidity() {
        return humidity;
    }

    @JsonProperty("cloud")
    public int getCloud() {
        return cloud;
    }

    @JsonProperty("feelslike_c")
    public double getFeelslikeC() {
        return feelslikeC;
    }

    @JsonProperty("feelslike_f")
    public double getFeelslikeF() {
        return feelslikeF;
    }

    @JsonProperty("vis_km")
    public double getVisKm() {
        return visKm;
    }

    @JsonProperty("vis_miles")
    public double getVisMiles() {
        return visMiles;
    }

    @JsonProperty("uv")
    public double getUv() {
        return uv;
    }

    @JsonProperty("gust_mph")
    public double getGustMph() {
        return gustMph;
    }

    @JsonProperty("gust_kph")
    public double getGustKph() {
        return gustKph;
    }
}