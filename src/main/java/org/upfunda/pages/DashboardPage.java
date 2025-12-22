package org.upfunda.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class DashboardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Robust locator – handles line breaks, dynamic names, React rendering
    private By welcomeText =
            By.xpath("//h1[contains(.,'welcome')]");

    // Optional: dashboard-specific element (use if available)
    private By dashboardRoot =
            By.xpath("//div[contains(@class,'dashboard')]");

    /**
     * Waits until dashboard page is loaded
     */
    public void waitForDashboardToLoad() {
        // Primary validation: page state
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(welcomeText),
                ExpectedConditions.visibilityOfElementLocated(dashboardRoot)
        ));
    }

    /**
     * Returns welcome message text
     */
    public String getWelcomeText() {
        waitForDashboardToLoad();
        return driver.findElement(welcomeText).getText();
    }

    /**
     * Checks if dashboard is loaded
     */
    public boolean isDashboardLoaded() {
        try {
            waitForDashboardToLoad();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
