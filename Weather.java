import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class WeatherApp {
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String API_URL = "(link unavailable)";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter city name: ");
        String city = scanner.nextLine();

        String apiUrl = API_URL + "?q=" + city + "&appid=" + API_KEY;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                String weatherDescription = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
                double temperature = jsonObject.getJSONObject("main").getDouble("temp");

                System.out.println("Weather in " + city + ": " + weatherDescription);
                System.out.println("Temperature: " + temperature + "K");
            } else {
                System.out.println("Failed to retrieve weather data");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
