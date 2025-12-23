package org.upfunda.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ----------- Login Page Locators -----------
    private final By initialLoginBtn =
            By.cssSelector("button[class*='bg-[#6C5CE7]']");

    private final By usernameField =
            By.cssSelector("input[type='email']");

    private final By passwordField =
            By.cssSelector("input[type='password']");

    private final By signInBtn =
            By.xpath("//button[contains(text(),'Sign')]");

    private final By loginError =
            By.xpath("//div[contains(@class,'error') or contains(text(),'invalid')]");

    // ----------- Dashboard Locators -----------
    private final By dashboardRoot =
            By.xpath("//div[contains(@class,'dashboard')]");

    private final By welcomeText =
            By.xpath("//h1[contains(translate(.,'WELCOME','welcome'),'welcome')]");

    // ---------- Utility Methods ----------
    private WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void safeClick(By locator) {
        for (int i = 0; i < 2; i++) {
            try {
                waitForClickable(locator).click();
                return;
            } catch (StaleElementReferenceException ignored) {
            }
        }
        throw new RuntimeException("Unable to click element: " + locator);
    }

    private void safeSendKeys(By locator, String value) {
        for (int i = 0; i < 2; i++) {
            try {
                WebElement element = waitForClickable(locator);
                element.clear();
                element.sendKeys(value);
                return;
            } catch (StaleElementReferenceException ignored) {
            }
        }
        throw new RuntimeException("Unable to send keys to element: " + locator);
    }

    // ---------- Actions ----------
    public void clickInitialLogin() {
        safeClick(initialLoginBtn);
    }

    public void login(String username, String password) {
        safeSendKeys(usernameField, username);
        safeSendKeys(passwordField, password);
        safeClick(signInBtn);

        waitForLoginResult();
    }

    // ---------- Validations ----------
    private void waitForLoginResult() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(dashboardRoot),
                    ExpectedConditions.visibilityOfElementLocated(loginError)
            ));
        } catch (TimeoutException e) {
            throw new AssertionError(
                    "Login result unclear: neither dashboard nor error appeared"
            );
        }

        if (!isDashboardLoaded()) {
            throw new AssertionError("Login failed: dashboard not loaded");
        }
    }

    public boolean isDashboardLoaded() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(dashboardRoot),
                    ExpectedConditions.visibilityOfElementLocated(welcomeText)
            ));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getWelcomeText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(welcomeText)
        ).getText();
    }
}
