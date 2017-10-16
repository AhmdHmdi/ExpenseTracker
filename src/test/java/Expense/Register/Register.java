package Expense.Register;

import static org.junit.Assert.fail;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Register extends Base {

	String NameErrorRequired = null;
	String varUsername = "TestName2";
	String varPassword = "TestPass2";
	
	public void LoadBrowser(){
		// Load WebDriver C for Chrome and F for FireFox 
		driver = LoadDriver(driver, "F");
		// Open the URL
		driver.get(baseURL);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void a_createUser() throws InterruptedException{
				// Open Browser
				LoadBrowser();

				driver.navigate().to("http://thawing-shelf-73260.herokuapp.com/register.jsp");

				FillUserData(driver, varUsername, varPassword, varPassword, true,true);
				
				Thread.sleep(2000);

				//Assertion of 'User Creation'
				Boolean Passed= AssertElement("editaccount",varUsername);
				
				if (Passed)
				{
					System.out.println("Username is created successfully.");
				}else
				{
					fail("Username isn't created properly.");
				}
	}

	@Test
	public void b_createCategory() throws InterruptedException{
				// Open Browser
				LoadBrowser();

				driver.navigate().to("http://thawing-shelf-73260.herokuapp.com");

				
				FillUserData(driver, varUsername, varPassword, varPassword, true,false);
				
				Boolean LoginSuccess= AssertElement("editaccount",varUsername);
				if (LoginSuccess)
				{
					System.out.println("Username is logged in successfully.");
				}else
				{
					fail("Username login failed");
				}
				
				Thread.sleep(2000);
				String strValueCate = RandomValue(10, 100, "Automated", true);
				// Go to categories and then click on Add Category.
				// Use time to handle loading through slow connection. 
				driver.findElement(By.id("go_list_categories")).click();
				driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);
				driver.findElement(By.id("go_add_category")).click();
				driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);
				driver.findElement(By.id("name")).sendKeys(strValueCate);
				driver.findElement(By.id("submit")).click();
				
				String bodyText = driver.findElement(By.tagName("body")).getText();
				Boolean Passed= bodyText.contains(strValueCate);
				
				//Assertion of 'Category'
				
				if (Passed)
				{
					System.out.println("Category is created successfully.");
				}else
				{
					fail("Category isn't created properly.");
				}
	}

	@Test
	public void c_createExpense() throws InterruptedException{
				// Open Browser
				LoadBrowser();

				driver.navigate().to("http://thawing-shelf-73260.herokuapp.com");

				
				FillUserData(driver, varUsername, varPassword, varPassword, true,false);
				
				Boolean LoginSuccess= AssertElement("editaccount",varUsername);
				if (LoginSuccess)
				{
					System.out.println("Username is logged in successfully.");
				}else
				{
					fail("Username login failed");
				}
				
				Thread.sleep(2000);
				driver.findElement(By.id("go_add_expense")).click();
				String strValueExpe = RandomValue(10, 100, "Automated", false);
				int strValueDay = ThreadLocalRandom.current().nextInt(1, 27 + 1);
				int strValueMonth = ThreadLocalRandom.current().nextInt(1, 9 + 1);
				int strValueYear = ThreadLocalRandom.current().nextInt(2001, 2098 + 1);
				int strValueAmount = ThreadLocalRandom.current().nextInt(1, 9999 + 1);
				// click on Add Expense.
				// Use time to handle loading through slow connection. 
				driver.findElement(By.id("day")).sendKeys(""+strValueDay);
				driver.findElement(By.id("month")).sendKeys(""+strValueMonth);
				driver.findElement(By.id("year")).sendKeys(""+strValueYear);
				
				if(driver.findElement(By.id("category")).getText().isEmpty())
				{
					b_createCategory();
				}
				Select dropdown = new Select(driver.findElement(By.id("category")));
				int listnumber = dropdown.getAllSelectedOptions().size();
				dropdown.selectByIndex(listnumber-1);
				
				driver.findElement(By.id("amount")).sendKeys(""+strValueAmount);
				driver.findElement(By.id("reason")).sendKeys(strValueExpe);

				driver.findElement(By.id("submit")).click();
				
				Thread.sleep(2000);
				
				String bodyText = driver.findElement(By.tagName("body")).getText();
				Boolean Passed= bodyText.contains(strValueExpe);
				
				//Assertion of 'Expense'
				
				if (Passed)
				{
					System.out.println("Expense is created successfully.");
				}else
				{
					fail("Expense isn't created properly.");
				}
	}

	
	@After
	public void CloseBrowser()
	{
		driver.close();
		
	}

}
