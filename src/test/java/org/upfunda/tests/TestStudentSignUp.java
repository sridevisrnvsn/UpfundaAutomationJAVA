package org.upfunda.tests;

import org.testng.annotations.Test;
import org.upfunda.base.BaseTest;
import org.upfunda.pages.HomePage;
import org.upfunda.pages.SignupPage;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class TestStudentSignUp extends BaseTest {

    @Test
    public void studentSignupFlow() {

        // 1️⃣ Home Page
        HomePage homePage = new HomePage(driver);
        homePage.clickStudentSignup();

        // 2️⃣ Signup Page
        SignupPage signupPage = new SignupPage(driver);

        // Generate unique email and define password
        String email = "student_" + System.currentTimeMillis() + "@mailinator.com";
        String password = "Test@123";

        signupPage.enterEmail(email);
        signupPage.enterPassword(password);
        signupPage.clickSignup();

        // 3️⃣ Student Details Form
        signupPage.enterStudentName("Samantha");
        signupPage.selectRandomClass();
        signupPage.selectGenderFemale();
        signupPage.enterDOB("01/01/2015");
        signupPage.selectCountryIndia();
        signupPage.submitStudentDetails();

        // ✅ Attach credentials to Allure report
        String credentials = "Username: " + email + "\nPassword: " + password;
        Allure.addAttachment("Student Credentials", new ByteArrayInputStream(credentials.getBytes(StandardCharsets.UTF_8)));

        // Optional console output for local verification
        System.out.println("Student signup completed successfully.");
        System.out.println("✅ Credentials attached to Allure report:");
        System.out.println("Email/Username: " + email);
        System.out.println("Password: " + password);
    }
}