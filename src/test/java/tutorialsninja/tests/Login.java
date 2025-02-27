package tutorialsninja.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.LandingPage;
import tutorialsninja.base.Base;
import Utils.CommonUtils;

public class Login extends Base {

    WebDriver driver;
    Properties prop;

    @BeforeMethod
    public void setup() {
        driver = openBrowserAndApplication();
        prop = CommonUtils.loadProperties();
        landingPage = new LandingPage(driver);
        landingPage.clickOnMyAccount();
        loginPage = landingPage.selectLoginOption();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void verifyLoginWithValidCredentials() {
        Assert.assertTrue(loginPage.didWeNavigateToLoginPage());
        loginPage.enterEmail(prop.getProperty("existingEmail"));
        loginPage.enterPassword(prop.getProperty("validPassword"));
        accountPage = loginPage.clickOnLoginButton();
        Assert.assertTrue(accountPage.isUserLoggedIn());
        Assert.assertTrue(accountPage.didWeNavigateToAccountPage());
    }

    @Test(priority = 2)
    public void verifyLoginWithInvalidCredentials() {
        loginPage.enterEmail(CommonUtils.generateBrandNewEmail());
        loginPage.enterPassword(prop.getProperty("invalidPassword"));
        loginPage.clickOnLoginButton();
        String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
        Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
    }

    @Test(priority = 3)
    public void verifyLoginWithInvalidEmailAndValidPassword() {
        loginPage.enterEmail(CommonUtils.generateBrandNewEmail());
        loginPage.enterPassword(prop.getProperty("validPassword"));
        loginPage.clickOnLoginButton();
        String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
        Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
    }

    @Test(priority = 4)
    public void verifyLoginWithValidEmailAndInvalidPassword() {
        loginPage.enterEmail(CommonUtils.validEmailRandomizeGenerator());
        loginPage.enterPassword(prop.getProperty("invalidPassword"));
        loginPage.clickOnLoginButton();
        String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
        Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
    }

    @Test(priority = 5)
    public void verifyLoginWithoutCredentials() {
        loginPage.clickOnLoginButton();
        String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
        Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
    }

    @Test(priority = 6)
    public void verifyLoginWithInactiveCredentials() {
        loginPage.enterEmail(prop.getProperty("inactiveEmail"));
        loginPage.enterPassword(prop.getProperty("validPassword"));
        loginPage.clickOnLoginButton();
        String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
        Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
    }

    @Test(priority = 7)
    public void verifyLoggingIntoApplicationAfterChangingPassword() {
        String oldPassword = prop.getProperty("validPasswordTwo");
        String newPassword = prop.getProperty("samplePasswordTwo");
        
        loginPage.enterEmail(prop.getProperty("existingSampleEmailTwo"));
        loginPage.enterPassword(oldPassword);
        accountPage = loginPage.clickOnLoginButton();
        changePasswordPage = accountPage.clickOnChangeYourPasswordOption();
        changePasswordPage.enterPassword(newPassword);
        changePasswordPage.enterConfirmPassword(newPassword);
        accountPage = changePasswordPage.clickOnContinueButton();
        String expectedMessage = "Success: Your password has been successfully updated.";
        Assert.assertEquals(accountPage.getMessage(), expectedMessage);
        accountLogoutPage = accountPage.clickOnLogoutOption();
        accountLogoutPage.clickOnMyAccountDropMenu();
        loginPage = accountLogoutPage.selectLoginOption();
        loginPage.enterEmail(prop.getProperty("existingSampleEmailTwo"));
        loginPage.enterPassword(oldPassword);
        loginPage.clickOnLoginButton();
        String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
        Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
        loginPage.clearPassword();
        loginPage.enterPassword(newPassword);
        accountPage = loginPage.clickOnLoginButton();
        Assert.assertTrue(accountPage.isUserLoggedIn());
        CommonUtils.setProperties("validPasswordTwo", newPassword, prop);
        CommonUtils.setProperties("samplePasswordTwo", oldPassword, prop);
    }

    @Test(priority = 8)
    public void verifyCaseSensitivePasswordHandling() {
        loginPage.enterEmail(CommonUtils.validEmailRandomizeGenerator());
        loginPage.enterPassword(prop.getProperty("samplePassword"));
        loginPage.clickOnLoginButton();
        String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
        Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
    }

    @Test(priority = 9)
    public void verifyCopyingOfTextEnteredIntoPasswordField() {
        String passwordText = prop.getProperty("samplePassword");
        loginPage.enterPassword(passwordText);
        loginPage.selectPasswordFieldTextAndCopy();
        loginPage.pasteCopiedPasswordTextIntoEmailField();
        Assert.assertNotEquals(loginPage.getTextCopiedIntoEmailField(), passwordText);
    }


    @Test(priority = 10)
    public void verifyLeadingAndTrailingSpacesWhileLoginAccount() {

        String enteredEmail = "        " + prop.getProperty("existingEmail") + "        ";
        String expectedError = "Warning: No match for E-Mail Address and/or Password.";
        
        loginPage.enterEmail(enteredEmail);
        loginPage.enterPassword(prop.getProperty("validPassword"));
        loginPage.clickOnLoginButton();
        Assert.assertEquals(loginPage.getWarningMessage(), expectedError);
    }
}
