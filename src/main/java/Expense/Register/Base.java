package Expense.Register;

import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Base {
	
	public static String baseURL = "http://thawing-shelf-73260.herokuapp.com";
	public static WebDriver driver = null;
	Boolean isPresent;
	private String strRandom = "";

	public static String OSDetector () {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win"))
		{
			return "Windows";
		}
		else if (os.contains("nux") || os.contains("nix"))
		{
			return "Linux";
		}
		else
		{
			return "Other";
		}
	}
	
	public static WebDriver LoadDriver(WebDriver driver, String DriverName)
	{
		if (OSDetector().toString().equals("Linux"))
		{
			if (DriverName == "F")
			{
				System.setProperty("webdriver.gecko.driver", "src/drivers/geckodriver");
				ProfilesIni profile = new ProfilesIni();
				FirefoxProfile Uprofile = profile.getProfile("default");
				Uprofile.setAcceptUntrustedCertificates(true);
				Uprofile.setAssumeUntrustedCertificateIssuer(true);
				Uprofile.setPreference("security.insecure_field_warning.contextual.enabled", false);
				DesiredCapabilities cap = DesiredCapabilities.firefox();
				cap.setCapability(FirefoxDriver.PROFILE, Uprofile);
				cap.setCapability("marionette", true);
				driver = new FirefoxDriver(cap);
				return driver;
			}
			else if (DriverName == "C")
			{
				System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver");
				driver = new ChromeDriver();
				return driver;
			}
			else 
			{
				System.out.println("Please Enter Correct Driver Name: F or C");
				driver = null;
			}
		}else if (OSDetector().toString().equals("Windows"))
		{

			if (DriverName == "F")
			{
				System.setProperty("webdriver.gecko.driver", "src/drivers/geckodriver.exe");
				ProfilesIni profile = new ProfilesIni();
				FirefoxProfile Uprofile = profile.getProfile("default");
				Uprofile.setAcceptUntrustedCertificates(true);
				Uprofile.setAssumeUntrustedCertificateIssuer(true);
				Uprofile.setPreference("security.insecure_field_warning.contextual.enabled", false);
				DesiredCapabilities cap = DesiredCapabilities.firefox();
				cap.setCapability(FirefoxDriver.PROFILE, Uprofile);
				cap.setCapability("marionette", true);
				driver = new FirefoxDriver(cap);
				return driver;
			}
			else if (DriverName == "C")
			{
				System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
				driver = new ChromeDriver();
				return driver;
			}
			else 
			{
				System.out.println("Please Enter Correct Driver Name: F or C");
				driver = null;
			}
		}
		else
		{
			System.out.println("Operating System is not supported.");
			driver.quit();
		}
		return driver;
	}
	
	public void FillUserData(WebDriver driver, String varName, String varPassword, String varConfirmPassword, Boolean varSubmit, Boolean isRegister){
		// Fill in User Data( Web Driver, User Name, Password, Confirm Password, Click on button Submit or Not, Is that data for register with confirmation password or just login) 
		if (isRegister)
		{
			driver.findElement(By.id("login")).sendKeys(varName);
			driver.findElement(By.id("password1")).sendKeys(varPassword);
			driver.findElement(By.id("password2")).sendKeys(varConfirmPassword);
			if (varSubmit == true){
				driver.findElement(By.id("submit")).click();
			} else { /*Do Nothing*/}	
		}else
		{
			driver.findElement(By.id("login")).sendKeys(varName);
			driver.findElement(By.id("password")).sendKeys(varPassword);
			if (varSubmit == true){
				driver.findElement(By.id("submit")).click();
			} else { /*Do Nothing*/}
		}
		
	}

	public boolean AssertElement(String elementId, String ExpectedMessage)
	{
	Boolean Passed = false;	
	try{
		isPresent = driver.findElement(By.id(elementId)).getText().isEmpty();
	} catch(Exception e){
		isPresent = true;
	}
	
	if (isPresent)
	{
		Passed = false;
	}else
	{
		String ActualMessage = driver.findElement(By.id(elementId)).getText().toString();
		if (ExpectedMessage.equals(ActualMessage))
		{
			Passed = true;
		}else
		{
			Passed = false;
		}	
	}
	return Passed;
	}

	public String RandomValue(int min, int max, String strPrefix, Boolean IfCategory){
		int ValueToAddedCate = ThreadLocalRandom.current().nextInt(min, max + 1);
		
		if (IfCategory){
			strRandom = strPrefix+" Cate "+ValueToAddedCate;
		}
		else
		{
			strRandom = strPrefix+" Expense "+ValueToAddedCate;
		}
				return strRandom;
	}
}
