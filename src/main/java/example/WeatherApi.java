package example;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;

public class WeatherApi {
    // Doc for the API: https://open-meteo.com/
    // Example query:
    // https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&past_days=10&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m

    private final String latitude;
    private final String longitude;
    final Gson gson = new Gson();

    public WeatherApi(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void printHourlyTable() {
        // Build the URI for the HTTP request
        URI getUri;
        try {
            getUri = new URI(Constants.BASE_API_URL + Constants.API_VERSION_ONE + Constants.QUERY_DIVIDER
                    + Constants.LAT_PARAMETER_KEY + latitude
                    + Constants.REQUEST_DIVIDER
                    + Constants.LONG_PARAMETER_KEY + longitude
                    + Constants.REQUEST_DIVIDER
                    + Constants.HOURLY_KEY_VALUE_PAIR
            );
        } catch (URISyntaxException exception) {
            System.out.println(Constants.BIG_ERROR);
            System.out.println(exception.getMessage());
            throw new RuntimeException();
        }

        // Prepare the request by creating a Request, a Client and a Response objects
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(getUri)
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> httpResponse;

        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException exception) {
            System.out.println(Constants.BIG_ERROR);
            System.out.println(exception.getMessage());
            throw new RuntimeException();
        } catch (InterruptedException exception) {
            System.out.println(Constants.BIG_ERROR);
            System.out.println(exception.getMessage());
            throw new RuntimeException();
        }

        // Transform the JSON we received as string into a usable Java object
        HourlyWeatherReport hourlyWeatherReport = gson.fromJson(httpResponse.body(), HourlyWeatherReport.class);
        HashMap<String, List<String>> resultMap = hourlyWeatherReport.getHourlyReport();

        // We will add checks to make sure everything is there
        // If not... print Error
        if(resultMap.containsKey(Constants.HOURLY_KEY) && resultMap.containsKey(Constants.TEMP_KEY)) {
            for (int i = 0; i < resultMap.get(Constants.HOURLY_KEY).size(); i++) {
                System.out.println(
                        resultMap.get(Constants.HOURLY_KEY).get(i)
                        + " || "
                        + resultMap.get(Constants.TEMP_KEY).get(i)
                );
            }
        } else {
            System.out.println(Constants.BIG_ERROR);
            throw new RuntimeException();
        }
    }
}
