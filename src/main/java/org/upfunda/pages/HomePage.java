package org.upfunda.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        // Increase wait to 15 seconds
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Robust XPath for Student Signup button
    private By studentSignupBtn = By.xpath("//button[contains(.,'Student') and not(@disabled)]");

    // Optional: handle popup/overlay that might block button
    private By popupCloseBtn = By.cssSelector(".modal-close, .overlay-close"); // adjust as per site

    public void clickStudentSignup() {
        // Close popup if present
        try {
            WebElement popup = driver.findElement(popupCloseBtn);
            if (popup.isDisplayed()) popup.click();
        } catch (Exception e) {
            // ignore if no popup
        }

        // Wait until clickable and click
        wait.until(ExpectedConditions.elementToBeClickable(studentSignupBtn)).click();
    }
}