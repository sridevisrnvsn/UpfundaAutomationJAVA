package org.upfunda.tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.upfunda.base.BaseTest;
import org.upfunda.pages.LoginPage;
import org.upfunda.pages.DashboardPage;
import org.upfunda.pages.WorksheetsPage;

public class TestStudentNavigation extends BaseTest {

    @Test(description = "Login as Student, validate welcome message, and verify worksheets sections")
    @Description("Verifies student login, dashboard load, welcome message, and worksheets sections")
    public void loginStudentAndValidateDashboardAndWorksheets() {

        // --------- LOGIN ---------
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickInitialLogin();

        String username = config.getProperty("student.username");
        String password = config.getProperty("student.password");
        loginPage.login(username, password);

        // --------- DASHBOARD VALIDATION ---------
        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(
                dashboardPage.isDashboardLoaded(),
                "Dashboard is not loaded after student login"
        );

        String welcomeMessage = dashboardPage.getWelcomeText();
        Allure.addAttachment("Dashboard Welcome Message", welcomeMessage);
        Assert.assertTrue(
                welcomeMessage.toLowerCase().contains("welcome"),
                "Welcome message is not displayed correctly"
        );

        // --------- NAVIGATE TO WORKSHEETS ---------
        dashboardPage.goToWorksheets();

        // --------- WORKSHEETS VALIDATION ---------
        WorksheetsPage worksheetsPage = new WorksheetsPage(driver);
        Assert.assertTrue(
                worksheetsPage.areAllSectionsDisplayed(),
                "One or more worksheet sections (Math, Logical Reasoning, Mental Math) are missing"
        );
    }
}
