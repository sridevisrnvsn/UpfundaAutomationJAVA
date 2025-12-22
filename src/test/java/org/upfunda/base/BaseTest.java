package org.upfunda.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected configreader config;

    @BeforeMethod
    public void setup() {

        // Load config
        config = new configreader();

        // Initialize driver
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Open application URL
        String productionUrl = config.getProperty("url.production");
        driver.get(productionUrl);

        // Explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterMethod
    public void teardown() {

        try {
            By profileBtnLocator =
                    By.cssSelector("button[class*='rounded-full'][class*='bg-[#6C5CE7]']");

            if (!driver.findElements(profileBtnLocator).isEmpty()) {

                WebElement profileBtn = wait.until(
                        ExpectedConditions.elementToBeClickable(profileBtnLocator)
                );
                profileBtn.click();

                WebElement logoutItem = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.cssSelector("div[class*='data-[variant=destructive]']")
                        )
                );
                logoutItem.click();
            } else {
                System.out.println("User not logged in. Skipping logout.");
            }

        } catch (Exception e) {
            System.out.println("Logout skipped due to page state.");
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
