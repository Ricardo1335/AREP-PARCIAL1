package edu.eci.arep;

import edu.eci.arep.exceptions.WeatherServiceException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {
    public static String getCityWeather(String city) throws WeatherServiceException {
        try {
            HttpURLConnection connection;
            StringBuffer response;
            BufferedReader in;
            int responseCode;
            String inputLine;
            URL url;

            url = new URL("http://api.openweathermap.org/data/2.5"+"/weather?q="+city+"&appid=43faf20c0dbb910a930c0476504617de");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            responseCode = connection.getResponseCode();

            if (responseCode==HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                inputLine = in.readLine();
                response = new StringBuffer();

                while (inputLine!=null) {
                    response.append(inputLine);
                    inputLine = in.readLine();
                }

                in.close();
            } else {
                throw new WeatherServiceException("City not found.");
            }

            return String.valueOf(response);
        } catch (Exception e) {
            throw new WeatherServiceException(e.getMessage());
        }
    }
}
