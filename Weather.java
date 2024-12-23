import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your OpenWeatherMap API key

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();

        String response = getWeatherData(city);
        if (response != null) {
            parseAndDisplayWeather(response, city);
        } else {
            System.out.println("Could not retrieve weather data. Please check the city name and try again.");
        }

        scanner.close();
    }

    private static String getWeatherData(String city) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric";
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check if the response code is 200 (HTTP_OK)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } else {
                System.out.println("Error: Unable to get weather data. Response code: " + connection.getResponseCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching weather data: " + e.getMessage());
            return null;
        }

        return response.toString();
    }

    private static void parseAndDisplayWeather(String response, String city) {
        try {
            // Parse the JSON response
            JSONObject weatherData = new JSONObject(response);
            JSONObject main = weatherData.getJSONObject("main");
            double temperature = main.getDouble("temp");
            String weatherCondition = weatherData.getJSONArray("weather").getJSONObject(0).getString("description");

            // Display the temperature and weather condition to the user
            System.out.println("Current temperature in " + city + ": " + temperature + "°C");
            System.out.println("Weather condition: " + weatherCondition);
        } catch (Exception e) {
            System.out.println("An error occurred while parsing weather data: " + e.getMessage());
        }
    }
}
