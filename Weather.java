import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your OpenWeatherMap API key
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter city name: ");
        String city = scanner.nextLine();

        String apiUrl = API_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric"; // Use metric for Celsius

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                // Manually parse the JSON response
                String jsonResponse = response.toString();
                String weatherDescription = parseWeatherDescription(jsonResponse);
                double temperature = parseTemperature(jsonResponse);

                System.out.println("Weather in " + city + ": " + weatherDescription);
                System.out.println("Temperature: " + temperature + "°C");
            } else {
                System.out.println("Failed to retrieve weather data. Response code: " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static String parseWeatherDescription(String jsonResponse) {
        String key = "\"description\":\"";
        int startIndex = jsonResponse.indexOf(key) + key.length();
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex);
    }

    private static double parseTemperature(String jsonResponse) {
        String key = "\"temp\":";
        int startIndex = jsonResponse.indexOf(key) + key.length();
        int endIndex = jsonResponse.indexOf(",", startIndex);
        return Double.parseDouble(jsonResponse.substring(startIndex, endIndex));
    }
}
