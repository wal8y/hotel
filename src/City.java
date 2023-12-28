import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

class City {
    String name;
    String urlName;
    Map<String, Hotel> hotels;

    public City(String name) {
        this.name = name;
        this.urlName = name.replace(" ","%20");
        this.hotels = new HashMap();
    }

    public void addHotel(String hotelName,Float Rating) {
        this.hotels.put(hotelName, new Hotel(hotelName,Rating));
    }

    public double getTemperature() {
        try {
            String apiUrl = "http://api.weatherapi.com/v1/current.json?key=1d6fee2e75ba476da18180454231512&q="+this.urlName+"&aqi=no";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                int tempCIndex = response.indexOf("\"temp_c\"");
                int colonIndex = response.indexOf(":", tempCIndex);
                int endIndex = response.indexOf(",", colonIndex);
                String temperatureSubstring = response.substring(colonIndex + 1, endIndex);
                double temperatureCelsius = Double.parseDouble(temperatureSubstring.trim());
                connection.disconnect();
                return temperatureCelsius;
            } else{
                System.out.println("Couldn't get city temperature");
                connection.disconnect();
                return -200.0;
            }
        } catch (Exception e) {
            System.out.println("error getting this city temperature");
            return -202.0;
        }
    }
}
