package org.sda.consullapp;

import net.bytebuddy.asm.Advice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.sda.dao.LocationDao;
import org.sda.dao.LocationDaoImpl;
import org.sda.dao.WeatherDataDao;
import org.sda.dao.WeatherDataDaoImpl;
import org.sda.model.Location;
import org.sda.model.WeatherData;
import org.sda.services.LocationService;
import org.sda.services.LocationServiceImpl;
import org.sda.services.WeatherDataService;
import org.sda.services.WeatherDataServiceImpl;
import org.sda.util.HibernateUtil;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class WeatherDataGUI {
    private JFrame frame;
    private LocationService locationService;
    private WeatherDataService weatherDataService;

    private JTextField cityField;
    private String apiKey = "a475956f51851932d5dd2b66dce5f692";
    public WeatherDataGUI(){
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        LocationDao locationDao = new LocationDaoImpl(factory);
        locationService = new LocationServiceImpl(locationDao);

        WeatherDataDao weatherDataDao = new WeatherDataDaoImpl(factory);
        weatherDataService = new WeatherDataServiceImpl(weatherDataDao);

        frame = new JFrame("Weather Data App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel locationPanel = createLocationPanel();
        JPanel displayPanel = createDisplayPanel();
        JPanel downloadPanel = createDownloadPanel();

        tabbedPane.add("Add Location", locationPanel);
        tabbedPane.add("Display Loction", displayPanel);
        tabbedPane.add("Download Weather Data", downloadPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createDownloadPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        cityField = new JTextField();
        JButton downloadButton = new JButton("Download Weather Data");
        WeatherPanel weatherPanel = new WeatherPanel();

        downloadButton.addActionListener(e -> {
            String city = cityField.getText();
            WeatherData weatherData = retrieveWeatherDataFromAPI(city, apiKey);

            if (weatherData != null) {
                weatherPanel.updateWeatherData(weatherData);
                weatherDataService.addWeatherData(weatherData);
                JOptionPane.showMessageDialog(frame, "Weather data added successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to retrieve weather data.");
            }
        });

        panel.add(new JLabel("City:"));
        panel.add(cityField);
        panel.add(downloadButton);

        // Add the weather panel to your main panel
        panel.add(weatherPanel);

        return panel;
    }

    public WeatherData retrieveWeatherDataFromAPI(String city, String apiKey) {
        int responseCode = 0;
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

            // Open a connection to the URL
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                // Read the response from the API

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                double temperatureInKelvin = jsonResponse.getJSONObject("main").getDouble("temp");
                double temperatureInCelsius = temperatureInKelvin - 273.15; // Convert to Celsius

                double pressure = jsonResponse.getJSONObject("main").getDouble("pressure");
                double humidity = jsonResponse.getJSONObject("main").getDouble("humidity");
                double windSpeed = jsonResponse.getJSONObject("wind").getDouble("speed");
                double windDirection = jsonResponse.getJSONObject("wind").getDouble("deg");

                // Create a WeatherData object and populate it
                WeatherData weatherData = new WeatherData();
                weatherData.setTemperature(temperatureInCelsius); // Use the converted temperature in Celsius
                weatherData.setPressure(pressure);
                weatherData.setHumiduty(humidity);
                weatherData.setWindspeed(windSpeed);
                weatherData.setWindDirection(windDirection);

                return weatherData;
            } else {
                System.out.println("Failed to retrieve weather data. HTTP error code: " + responseCode);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while fetching weather data.");
            System.out.println("HTTP error code: " + responseCode);
        }

        return null; // Return null if retrieval fails
    }

    private JPanel createDisplayPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        List<Location> locations = locationService.getAllLocation();

        DefaultListModel<Location> listModel = new DefaultListModel<>();
        for (Location location: locations){
            listModel.addElement(location);
        }
        //create a JList with model
        JList<Location> locationList = new JList<>(listModel);
        //add scroll panel to a scroll in case there are to many location to list
        JScrollPane scrollPane = new JScrollPane(locationList);

        panel.add(scrollPane,BorderLayout.CENTER);

        return panel;
    }

    private JPanel createLocationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,2));
        //create text fields
        JTextField latitudeField= new JTextField();
        JTextField longitudeField= new JTextField();
        JTextField regionField =new JTextField();
        JTextField countryField= new JTextField();
        //create a Button
        JButton addButton=new JButton("Add Location");
        addButton.addActionListener(e -> {
            try {
                double latitude = Double.parseDouble(latitudeField.getText());
                double longitude = Double.parseDouble(longitudeField.getText());
                String region = regionField.getText();
                String country = countryField.getText();

                Location newLocation = new Location(latitude, longitude, region, country);

                //Add the new location
                locationService.addLocation(newLocation);

                JOptionPane.showMessageDialog(frame, "Location added successfully");
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid latitude and longitude");
            }catch (Exception ex){
                JOptionPane.showMessageDialog(frame, "Error adding location: " +  ex.getMessage());
            }
        });

        panel.add(new JLabel("Latitude: "));
        panel.add(latitudeField);
        panel.add(new JLabel("Longitude: "));
        panel.add(longitudeField);
        panel.add(new JLabel("Region: "));
        panel.add(regionField);
        panel.add(new JLabel("Country: "));
        panel.add(countryField);
        panel.add(addButton);

        return panel;
    }
    public void display() {
        frame.setVisible(true);
    }
}
