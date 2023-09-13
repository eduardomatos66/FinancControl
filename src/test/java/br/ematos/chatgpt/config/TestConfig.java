package br.ematos.chatgpt.config;

import br.ematos.chatgpt.weather.api.WeatherAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

  @Bean
  public WeatherAPI weatherAPI(
      @Value("${weather-api-key.token}") String key,
      @Value("${weather-api-key.host}") String host) {
    return new WeatherAPI(key, host);
  }
}
