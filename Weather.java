import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();
        String apiKey = "YOUR_API_KEY"; // Replace with your OpenWeatherMap API key

        try {
            // Create a URL for the API request
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read and process the API response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the JSON response
            JSONObject weatherData = new JSONObject(response.toString());
            JSONObject main = weatherData.getJSONObject("main");
            double temperature = main.getDouble("temp");
            String weatherCondition = weatherData.getJSONArray("weather").getJSONObject(0).getString("description");

            // Display the temperature and weather condition to the user
            System.out.println("Current temperature in " + city + ": " + temperature + "°C");
            System.out.println("Weather condition: " + weatherCondition);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
