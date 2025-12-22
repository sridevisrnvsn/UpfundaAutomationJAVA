package org.upfunda.tests;

import org.testng.annotations.Test;
import org.upfunda.base.BaseTest;
import org.upfunda.pages.HomePage;
import org.upfunda.pages.SignupPage;

public class TestStudentSignUp extends BaseTest {

    @Test
    public void studentSignupFlow() {

        // 1️⃣ Home Page
        HomePage homePage = new HomePage(driver, wait);
        homePage.clickStudentSignup();

        // 2️⃣ Signup Page
        SignupPage signupPage = new SignupPage(driver);

        String email = "student_" + System.currentTimeMillis() + "@mailinator.com";

        signupPage.enterEmail(email);
        signupPage.enterPassword("Test@123");
        signupPage.clickSignup();

        // 3️⃣ Student Details Form
        signupPage.enterStudentName("Samantha");
        signupPage.selectRandomClass();
        signupPage.selectGenderFemale();
        signupPage.enterDOB("01/01/2015");
        signupPage.selectCountryIndia();
        signupPage.submitStudentDetails();

        System.out.println("Student signup completed successfully");
    }
}
