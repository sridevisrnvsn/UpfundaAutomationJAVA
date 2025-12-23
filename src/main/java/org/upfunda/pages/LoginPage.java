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
    private By usernameField = By.cssSelector("input[type='email']");
    private By passwordField = By.cssSelector("input[type='password']");
    private By signInBtn = By.xpath("//button[contains(text(),'Sign')]");

    private By loginError =
            By.xpath("//div[contains(@class,'error') or contains(text(),'invalid')]");

    // ----------- Dashboard Locators -----------
    private By dashboardRoot =
            By.xpath("//div[contains(@class,'dashboard')]");

    private By welcomeText =
            By.xpath("//h1[contains(translate(., 'WELCOME', 'welcome'), 'welcome')]");

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
            } catch (StaleElementReferenceException ignored) {
            }
        }
        throw new RuntimeException("Unable to send keys to: " + locator);
    }

    // ---------- Safe Click ----------
    private void safeClick(By locator) {
        for (int i = 0; i < 2; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
                return;
            } catch (StaleElementReferenceException ignored) {
            }
        }
        throw new RuntimeException("Unable to click: " + locator);
    }

    // ---------- Landing Page Login ----------
    public void clickInitialLogin() {
        safeClick(initialLoginBtn);
    }

    // ---------- Dashboard wait ----------
    private void waitForDashboard() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(dashboardRoot),
                ExpectedConditions.visibilityOfElementLocated(welcomeText)
        ));
    }

    // ---------- Dashboard validation ----------
    public boolean isDashboardLoaded() {
        try {
            waitForDashboard();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ---------- LOGIN ----------
    public void login(String username, String password) {

        safeSendKeys(usernameField, username);
        safeSendKeys(passwordField, password);
        safeClick(signInBtn);

        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(dashboardRoot),
                    ExpectedConditions.visibilityOfElementLocated(loginError)
            ));
        } catch (TimeoutException e) {
            throw new AssertionError(
                    "Login outcome unclear: neither dashboard nor error appeared"
            );
        }

        if (!isDashboardLoaded()) {
            throw new AssertionError("Login failed: dashboard not loaded");
        }
    }

    // ---------- Optional helper ----------
    public String getWelcomeText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeText));
        return driver.findElement(welcomeText).getText();
    }
}
