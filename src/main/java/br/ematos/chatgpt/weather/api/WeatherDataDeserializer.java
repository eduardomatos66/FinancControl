package br.ematos.chatgpt.weather.api;

import br.ematos.chatgpt.weather.entity.Weather;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherDataDeserializer {

    public static Weather deserialize(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
