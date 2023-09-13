package br.ematos.chatgpt.weather.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ematos.chatgpt.weather.api.WeatherAPI;
import br.ematos.chatgpt.weather.api.WeatherDataDeserializer;
import br.ematos.chatgpt.weather.entity.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

@SpringBootTest(classes = WeatherController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class WeatherControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private WeatherAPI weatherAPI;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetCurrentWeather() throws Exception {
    Weather currentWeather = createSampleWeather();

    when(weatherAPI.retrieveWeatherInfo()).thenReturn(currentWeather);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/weather/")
                .contentType(MediaType.APPLICATION_JSON.getMediaType()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(asJsonString(currentWeather)));
  }

  @Test
  public void testGetWeatherForecast() throws Exception {
    double latitude = 42.98;
    double longitude = -81.24;
    Weather forecast = createSampleWeather(); // Create a sample Weather object

    when(weatherAPI.retrieveWeatherInfo(latitude, longitude)).thenReturn(forecast);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/weather/forecast")
                .param("latitude", String.valueOf(latitude))
                .param("longitude", String.valueOf(longitude))
                .contentType(MediaType.APPLICATION_JSON.getMediaType()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(asJsonString(forecast)));
  }

  private Weather createSampleWeather() {
    String json =
        "{\"location\":{\"name\":\"London\",\"region\":\"Ontario\",\"country\":\"Canada\",\"lat\":42.98,\"lon\":-81.24,\"tz_id\":\"America/Toronto\",\"localtime_epoch\":1694355893,\"localtime\":\"2023-09-10 10:24\"},\"current\":{\"last_updated_epoch\":1694355300,\"last_updated\":\"2023-09-10 10:15\",\"temp_c\":17.0,\"temp_f\":62.6,\"is_day\":1,\"condition\":{\"text\":\"Light drizzle\",\"icon\":\"//cdn.weatherapi.com/weather/64x64/day/266.png\",\"code\":1153},\"wind_mph\":6.9,\"wind_kph\":11.2,\"wind_degree\":40,\"wind_dir\":\"NE\",\"pressure_mb\":1021.0,\"pressure_in\":30.16,\"precip_mm\":0.0,\"precip_in\":0.0,\"humidity\":88,\"cloud\":100,\"feelslike_c\":17.0,\"feelslike_f\":62.6,\"vis_km\":9.7,\"vis_miles\":6.0,\"uv\":4.0,\"gust_mph\":7.8,\"gust_kph\":12.6}}";
    return WeatherDataDeserializer.deserialize(json);
  }

  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
