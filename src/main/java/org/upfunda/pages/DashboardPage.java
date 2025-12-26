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

    // Welcome header
    private By welcomeHeader = By.xpath(
            "//h1[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'welcome') " +
                    "or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'hey')]"
    );

    // Go to Worksheets button
    private By goToWorksheetsBtn =
            By.xpath("//button[contains(.,'Worksheet')]");

    public boolean isDashboardLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("student"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeHeader));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getWelcomeText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(welcomeHeader)
        ).getText();
    }

    public void goToWorksheets() {
        wait.until(ExpectedConditions.elementToBeClickable(goToWorksheetsBtn)).click();
        wait.until(ExpectedConditions.urlContains("worksheets"));
    }
}