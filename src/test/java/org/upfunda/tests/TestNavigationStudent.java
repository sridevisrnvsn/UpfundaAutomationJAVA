package org.upfunda.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.upfunda.base.BaseTest;
import org.upfunda.pages.DashboardPage;
import org.upfunda.pages.LoginPage1;

import io.qameta.allure.Allure;

public class TestNavigationStudent extends BaseTest {

    @Test
    public void verifyStudentNavigation() {

        // Read credentials
        String username = config.getProperty("student.username");
        String password = config.getProperty("student.password");

        LoginPage1 loginPage = new LoginPage1(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        Allure.step("Login as Student user", () -> {
            loginPage.clickInitialLogin();
            loginPage.login(username, password);
        });

        Allure.step("Verify dashboard is loaded after login", () -> {
            boolean dashboardLoaded = dashboardPage.isDashboardLoaded();
            Assert.assertTrue(
                    dashboardLoaded,
                    "Dashboard did not load after student login"
            );
        });

        Allure.step("Verify welcome message is displayed on dashboard", () -> {
            String welcomeText = dashboardPage.getWelcomeText();

            Assert.assertNotNull(
                    welcomeText,
                    "Welcome message element is null on dashboard"
            );

            Assert.assertTrue(
                    welcomeText.toLowerCase().contains("welcome"),
                    "Welcome message missing on dashboard"
            );

            Allure.addAttachment(
                    "Welcome Message",
                    welcomeText
            );

            Reporter.log(
                    "Dashboard loaded successfully with welcome message: " + welcomeText,
                    true
            );
        });
    }
}
