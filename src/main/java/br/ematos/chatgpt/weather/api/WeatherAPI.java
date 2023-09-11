package br.ematos.chatgpt.weather.api;

import br.ematos.chatgpt.weather.entity.Weather;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@AllArgsConstructor
@Slf4j
public class WeatherAPI {
    private static final String RAPID_API_KEY = "X-RapidAPI-Key";
    private static final String RAPID_API_HOST = "X-RapidAPI-Host";

    private final String apiKey;
    private final String apiHost;

    private Request buildRequest(double latitude, double longitude) {
        return new Request.Builder()
                .url("https://weatherapi-com.p.rapidapi.com/current.json?q=" + latitude + "%2C" + longitude)
                .get()
                .addHeader(RAPID_API_KEY, apiKey)
                .addHeader(RAPID_API_HOST, apiHost)
                .build();
    }

    public Weather retrieveWeatherInfo() {
        return retrieveWeatherInfo(42.98, -81.24);
    }

    public Weather retrieveWeatherInfo(double latitude, double longitude) {
        OkHttpClient client = new OkHttpClient();
        Response response = null;
        try {
            response = client.newCall(buildRequest(latitude, longitude)).execute();
            assert response.body() != null;
            String responseBody = response.body().string();
            System.out.println(responseBody);
            Weather weather = WeatherDataDeserializer.deserialize(responseBody);
            if (weather != null) {
                log.info("Location Name: " + weather.getLocation().getName() + "/" + weather.getLocation().getRegion());
                log.info("Temperature (Celsius): " + weather.getCurrent().getTempC());
                log.info("Condition Text: " + weather.getCurrent().getCondition().getText());
            }
            return weather;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
