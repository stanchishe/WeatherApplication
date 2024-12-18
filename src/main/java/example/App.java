package example;

import org.openqa.selenium.WebDriver;

import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        // Get user input
        String cityName;
        String countryName;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a name of a city:");
            cityName = scanner.next();
            System.out.println("Enter a name of a country");
            countryName = scanner.next();

            System.out.println("You have entered " + cityName + " in " + countryName + ".");
            System.out.println("Enter \"YES\" to continue...");
            String userAnswer = scanner.next();
            if(userAnswer.equalsIgnoreCase("yes")) break;
        }

        // Create webDriver
        WebDriver webDriver = WebDriverManager.createWebDriver();

        // Create a cityProperties object to get details
        CityProperties cityProperties = new CityProperties(webDriver, cityName, countryName);

        // Close the webDriver
        WebDriverManager.closeActiveDriver(webDriver);

        // Print the coordinates we got:
        System.out.println("Lat: " + cityProperties.getLatitude());
        System.out.println("Long: " + cityProperties.getLongitude());

        // Make the request and print results
        WeatherApi weatherApi = new WeatherApi(cityProperties.getLatitude(), cityProperties.getLongitude());
        weatherApi.printHourlyTable();

        // Print the end
        System.out.println("THE END");
    }
}
