package br.ematos.chatgpt.weather.controller;

import br.ematos.chatgpt.weather.api.WeatherAPI;
import br.ematos.chatgpt.weather.entity.Weather;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/weather")
public class WeatherController {

  private final WeatherAPI weatherAPI;

  @GetMapping("/")
  public ResponseEntity<Weather> getCurrentWeather() {
    Weather currentWeather = weatherAPI.retrieveWeatherInfo();
    return ResponseEntity.ok(currentWeather);
  }

  @GetMapping("/forecast")
  public ResponseEntity<Weather> getWeatherForecast(
      @RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
    Weather forecast = weatherAPI.retrieveWeatherInfo(latitude, longitude);
    return ResponseEntity.ok(forecast);
  }
}
