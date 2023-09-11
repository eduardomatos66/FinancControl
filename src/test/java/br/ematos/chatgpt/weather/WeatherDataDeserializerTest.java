package br.ematos.chatgpt.weather;

import br.ematos.chatgpt.weather.api.WeatherDataDeserializer;
import br.ematos.chatgpt.weather.entity.Weather;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherDataDeserializerTest {

  @Test
  void deserialize() {
    String json = "{\"location\":{\"name\":\"London\",\"region\":\"Ontario\",\"country\":\"Canada\",\"lat\":42.98,\"lon\":-81.24,\"tz_id\":\"America/Toronto\",\"localtime_epoch\":1694355893,\"localtime\":\"2023-09-10 10:24\"},\"current\":{\"last_updated_epoch\":1694355300,\"last_updated\":\"2023-09-10 10:15\",\"temp_c\":17.0,\"temp_f\":62.6,\"is_day\":1,\"condition\":{\"text\":\"Light drizzle\",\"icon\":\"//cdn.weatherapi.com/weather/64x64/day/266.png\",\"code\":1153},\"wind_mph\":6.9,\"wind_kph\":11.2,\"wind_degree\":40,\"wind_dir\":\"NE\",\"pressure_mb\":1021.0,\"pressure_in\":30.16,\"precip_mm\":0.0,\"precip_in\":0.0,\"humidity\":88,\"cloud\":100,\"feelslike_c\":17.0,\"feelslike_f\":62.6,\"vis_km\":9.7,\"vis_miles\":6.0,\"uv\":4.0,\"gust_mph\":7.8,\"gust_kph\":12.6}}";
    Weather weather = WeatherDataDeserializer.deserialize(json);

    assertNotNull(weather);
    assertEquals("London", weather.getLocation().getName());
    assertEquals(17.0, weather.getCurrent().getTempC());
    assertEquals("Light drizzle", weather.getCurrent().getCondition().getText());
    assertEquals("Ontario", weather.getLocation().getRegion());
  }
}
