package example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CityProperties {
    private final String cityName;
    private final String countryName;
    private final WebDriver webDriver;
    private String latitude;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public CityProperties(WebDriver webDriver, String cityName, String countryName) {
        this.webDriver = webDriver;
        this.cityName = cityName;
        this.countryName = countryName;

        this.getCoordinates();
    }

    private void getCoordinates() {
        // Create our search string
        // "Sofia Bulgaria coordinates"
        String searchString = cityName + " " + countryName + " " + Constants.COORDINATES;

        // Get to the Google search page
        webDriver.get(Constants.GOOGLE_HOME);
        webDriver.findElement(By.id(Constants.GOOGLE_DECLINE)).click();

        // Enter the search string in the search box and go
        webDriver.findElement(By.name(Constants.GOOGLE_SEARCH_BOX)).sendKeys(searchString, Keys.ENTER);

        // Use the first google result
        WebElement firstResult = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.className(Constants.FIRST_SEARCH_RESULT)));
        firstResult.click();

        // Check if table is loaded
        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Constants.CITY_TABLE)));

        // Add the collected values to the global variables
        this.latitude = webDriver.findElement(By.xpath(Constants.CITY_LATITUDE)).getText().replace(",",".");
        this.longitude = webDriver.findElement(By.xpath(Constants.CITY_LONGITUDE)).getText().replace(",",".");
    }
}
