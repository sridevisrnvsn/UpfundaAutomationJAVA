package org.upfunda.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ----------- Login Page Locators -----------
    private By initialLoginBtn = By.cssSelector("button[class*='bg-[#6C5CE7]']");
    private By usernameField   = By.cssSelector("input[type='email']");
    private By passwordField   = By.cssSelector("input[type='password']");
    private By signInBtn       = By.xpath("//button[contains(text(),'Sign')]");

    private By loginError =
            By.xpath("//div[contains(@class,'error') or contains(text(),'invalid')]");

    // ---------- Safe SendKeys ----------
    private void safeSendKeys(By locator, String value) {
        for (int i = 0; i < 2; i++) {
            try {
                WebElement element = wait.until(
                        ExpectedConditions.elementToBeClickable(locator)
                );
                element.clear();
                element.sendKeys(value);
                return;
            } catch (StaleElementReferenceException ignored) {}
        }
        throw new RuntimeException("Unable to send keys to: " + locator);
    }

    // ---------- Safe Click ----------
    private void safeClick(By locator) {
        for (int i = 0; i < 2; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
                return;
            } catch (StaleElementReferenceException ignored) {}
        }
        throw new RuntimeException("Unable to click: " + locator);
    }

    // ---------- Landing Page Login ----------
    public void clickInitialLogin() {
        safeClick(initialLoginBtn);
    }

    // ---------- LOGIN ----------
    public void login(String username, String password) {

        safeSendKeys(usernameField, username);
        safeSendKeys(passwordField, password);
        safeClick(signInBtn);

        try {
            // Login success = navigation OR error shown
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("student-home"),
                    ExpectedConditions.visibilityOfElementLocated(loginError)
            ));
        } catch (TimeoutException e) {
            throw new AssertionError(
                    "Login outcome unclear. URL: " + driver.getCurrentUrl()
            );
        }

        // Explicit failure case
        if (driver.findElements(loginError).size() > 0) {
            throw new AssertionError("Login failed: invalid credentials");
        }
    }
}
