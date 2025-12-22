package org.upfunda.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SignupPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ---------- Locators ----------
    private By emailField = By.id("email");
    private By passwordField = By.id("enterPassword");
    private By confirmPasswordField = By.id("confirmPassword");
    private By signupBtn = By.xpath("//button[@type='submit']");

    private By nameField = By.xpath("//input[@id='name']");
    private By classDropdown = By.id("class");
    private By genderDropdown = By.id("gender");
    private By dobField = By.id("dob");
    private By countryDropdown = By.id("country");
    private By submitBtn = By.xpath("//form//button[@type='submit']");

    // ---------- Actions ----------
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(emailField))
                .sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordField))
                .sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(confirmPasswordField))
                .sendKeys(password);
    }

    public void clickSignup() {
        wait.until(ExpectedConditions.elementToBeClickable(signupBtn))
                .click();
    }

    public void enterStudentName(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(nameField))
                .sendKeys(name);
    }

    public void selectRandomClass() {
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(classDropdown)
        );
        Select select = new Select(dropdown);

        List<WebElement> options = select.getOptions();
        int randomIndex = new Random().nextInt(options.size() - 1) + 1;
        select.selectByIndex(randomIndex);
    }

    public void selectGenderFemale() {
        new Select(wait.until(
                ExpectedConditions.elementToBeClickable(genderDropdown)
        )).selectByVisibleText("Female");
    }

    public void enterDOB(String dob) {
        wait.until(ExpectedConditions.elementToBeClickable(dobField))
                .sendKeys(dob);
    }

    public void selectCountryIndia() {
        new Select(wait.until(
                ExpectedConditions.elementToBeClickable(countryDropdown)
        )).selectByVisibleText("India");
    }

    public void submitStudentDetails() {
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn))
                .click();
    }
}
