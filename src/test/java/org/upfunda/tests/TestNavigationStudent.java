package org.upfunda.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.upfunda.base.BaseTest;
import org.upfunda.pages.DashboardPage;
import org.upfunda.pages.LoginPage1;

public class TestNavigationStudent extends BaseTest {

    @Test
    public void verifyStudentNavigation() {

        // 1. Read credentials from config
        String username = config.getProperty("student.username");
        String password = config.getProperty("student.password");

        // 2. Login using PageLogin
        LoginPage1 loginPage = new LoginPage1(driver);
        loginPage.clickInitialLogin();
        loginPage.login(username, password);

        // 3. Navigation tests
        DashboardPage dashboardPage = new DashboardPage(driver);

        // 4. Check if dashboard is loaded
        boolean dashboardLoaded = dashboardPage.isDashboardLoaded();
        Assert.assertTrue(dashboardLoaded, "Dashboard did not load after login");

        // 5. Check welcome message
        String welcomeText = dashboardPage.getWelcomeText();
        Assert.assertNotNull(welcomeText, "Welcome message element is null on dashboard");
        Assert.assertTrue(
                welcomeText.toLowerCase().contains("welcome"),
                "Welcome message missing on dashboard"
        );

        // 6. If both checks pass, log success
        Reporter.log("1. Dashboard is successfully loaded and welcome message is displayed: " + welcomeText, true);
    }
}
