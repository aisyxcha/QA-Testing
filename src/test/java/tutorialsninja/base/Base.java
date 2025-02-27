package tutorialsninja.base;

import Utils.CommonUtils;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;

import pages.AccountLogoutPage;
import pages.AccountPage;
import pages.AccountSuccessPage;
import pages.ChangePasswordPage;
import pages.EditAccountInformationPage;
import pages.LandingPage;
import pages.LoginPage;
import pages.NewsletterPage;
import pages.RegisterPage;


public class Base {
	
	WebDriver driver;
	Properties prop;
	
	public LandingPage landingPage;
	public RegisterPage registerPage;
	public AccountSuccessPage accountSuccessPage;
	public AccountPage accountPage;
	public NewsletterPage newsletterPage;
	public LoginPage loginPage;
	public EditAccountInformationPage editAccountInformationPage;
	public ChangePasswordPage changePasswordPage;
	public AccountLogoutPage accountLogoutPage;

	public WebDriver openBrowserAndApplication() {

		prop = CommonUtils.loadProperties();
		String browserName = prop.getProperty("browserName");

		if (browserName.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (browserName.equals("edge")) {
			driver = new EdgeDriver();
		} else if (browserName.equals("ie")) {
			driver = new InternetExplorerDriver();
		} else if (browserName.equals("safari")) {
			driver = new SafariDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.manage().window().maximize();
		driver.get(prop.getProperty("appURL"));
		
		return driver;

	}
	
	
	
	public WebDriver enterDetailsIntoLoginPageFields(WebDriver driver) {
		
		prop = CommonUtils.loadProperties();
		Actions actions = new Actions(driver);
		actions.sendKeys(prop.getProperty("existingEmail")).pause(Duration.ofSeconds(1))
		.sendKeys(Keys.TAB).pause(Duration.ofSeconds(1)).sendKeys(prop.getProperty("validPassword"))
		.pause(Duration.ofSeconds(1)).sendKeys(Keys.TAB).sendKeys(Keys.TAB).pause(Duration.ofSeconds(1))
		.sendKeys(Keys.ENTER).build().perform();
		
		return driver;
		
	}
	
	
	public String getPageURL(WebDriver driver) {
		return driver.getCurrentUrl();
	}

}