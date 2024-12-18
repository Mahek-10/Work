import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp {
    public static String getWeather(String city, String apiKey) throws Exception {
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
import org.json.JSONObject;

public class WeatherParser {
    public static void parseWeatherData(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONObject main = jsonObject.getJSONObject("main");
        String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");
        // Process and display the data as needed
    }
}
