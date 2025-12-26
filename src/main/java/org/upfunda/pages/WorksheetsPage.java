package org.upfunda.pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class WorksheetsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public WorksheetsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private By mathSection = By.xpath("//h4[contains(normalize-space(),'Math')]");
    private By logicalReasoningSection = By.xpath("//h4[contains(normalize-space(),'Logical')]");
    private By mentalMathSection = By.xpath("//h4[contains(normalize-space(),'Mental')]");

    public boolean areAllSectionsDisplayed() {
        StringBuilder report = new StringBuilder();
        boolean allDisplayed = true;

        try {
            wait.until(ExpectedConditions.urlContains("worksheets"));

            boolean math = wait.until(ExpectedConditions.visibilityOfElementLocated(mathSection)).isDisplayed();
            boolean logical = wait.until(ExpectedConditions.visibilityOfElementLocated(logicalReasoningSection)).isDisplayed();
            boolean mental = wait.until(ExpectedConditions.visibilityOfElementLocated(mentalMathSection)).isDisplayed();

            if (math) report.append("✅ Math section displayed\n");
            else { report.append("❌ Math section NOT displayed\n"); allDisplayed = false; }

            if (logical) report.append("✅ Logical Reasoning section displayed\n");
            else { report.append("❌ Logical Reasoning section NOT displayed\n"); allDisplayed = false; }

            if (mental) report.append("✅ Mental Math section displayed\n");
            else { report.append("❌ Mental Math section NOT displayed\n"); allDisplayed = false; }

        } catch (TimeoutException e) {
            report.append("❌ One or more worksheet sections not found due to timeout\n");
            allDisplayed = false;
        }

        // Add the report to Allure
        Allure.addAttachment("Worksheets Sections Status", report.toString());

        return allDisplayed;
    }
}