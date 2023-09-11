package br.ematos.chatgpt.weather.controller;

import br.ematos.chatgpt.weather.api.WeatherAPI;
import br.ematos.chatgpt.weather.entity.Weather;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherAPI weatherAPI;

    @GetMapping
    public Weather getWeather() {
        return weatherAPI.retrieveWeatherInfo();
    }

    @GetMapping("/{latitude}/{longitude}")
    public Weather getWeather(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude) {
        return weatherAPI.retrieveWeatherInfo(latitude, longitude);
    }
}
