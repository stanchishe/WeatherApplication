package example;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class WebDriverManager {
    public static @NotNull WebDriver createWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("Start-Maximized");
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return webDriver;
    }

    public static void closeActiveDriver(WebDriver activeDriver) {
        activeDriver.close();
        activeDriver.quit();
    }
}
