package org.upfunda.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Student Signup button (avoid absolute XPath)
    private By studentSignupBtn =
            By.xpath("//button[contains(text(),'Student')]");

    public void clickStudentSignup() {
        wait.until(ExpectedConditions.elementToBeClickable(studentSignupBtn)).click();
    }
}
