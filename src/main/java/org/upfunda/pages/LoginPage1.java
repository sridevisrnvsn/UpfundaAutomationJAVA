package org.upfunda.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class LoginPage1 {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage1(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private By initialLoginBtn = By.cssSelector("button[class*='bg-[#6C5CE7]']");
    private By usernameField   = By.cssSelector("input[type='email']");
    private By passwordField   = By.cssSelector("input[type='password']");
    private By signInBtn       = By.xpath("//button[contains(text(),'Sign')]");

    // ---------- Generic Safe SendKeys ----------
    private void safeSendKeys(By locator, String value) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                WebElement element = wait.until(
                        ExpectedConditions.elementToBeClickable(locator)
                );
                element.clear();
                element.sendKeys(value);
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
        throw new RuntimeException("Unable to send keys to element: " + locator);
    }

    // ---------- Generic Safe Click ----------
    private void safeClick(By locator) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
        throw new RuntimeException("Unable to click element: " + locator);
    }

    // Click the landing page login button
    public void clickInitialLogin() {
        safeClick(initialLoginBtn);
    }

    public void login(String username, String password) {

        safeSendKeys(usernameField, username);
        safeSendKeys(passwordField, password);
        safeClick(signInBtn);

        // Post-login validation
        wait.until(ExpectedConditions.urlContains("/student-home"));
    }
}
