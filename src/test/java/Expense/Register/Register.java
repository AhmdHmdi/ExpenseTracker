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
	String varUsername = "TestName4";
	String varPassword = "TestPass4";
	
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
				int strValueDay = ThreadLocalRandom.current().nextInt(1, 27);
				int strValueMonth = ThreadLocalRandom.current().nextInt(1);
				int strValueYear = ThreadLocalRandom.current().nextInt(2001, 2098 );
				int strValueAmount = ThreadLocalRandom.current().nextInt(1, 9999);
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

	@Test
	public void d_createExpense_noday() throws InterruptedException{
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
				//int strValueDay = ThreadLocalRandom.current().nextInt(1, 27 + 1);
				int strValueMonth = ThreadLocalRandom.current().nextInt(1);
				int strValueYear = ThreadLocalRandom.current().nextInt(2001, 2098 );
				int strValueAmount = ThreadLocalRandom.current().nextInt(1, 9999 );
				// click on Add Expense.
				// Use time to handle loading through slow connection. 
				// Set day to empty
				driver.findElement(By.id("day")).clear();
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
				//***********************************************************
				String ErrorText = driver.findElement(By.id("day")).getAttribute("validationMessage");
				Boolean Passed= ErrorText.equals("Please enter a number.");
				//***********************************************************
				//Assertion of 'Day' Error
				
				if (Passed)
				{
					System.out.println("Assert of 'day is empty' is passed successfully.");
				}else
				{
					fail("Assert of 'day is empty' failed.");
				}
	}
	
	@Test
	public void e_createExpense_nomonth() throws InterruptedException{
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
				int strValueDay = ThreadLocalRandom.current().nextInt(1, 27);
				//int strValueMonth = ThreadLocalRandom.current().nextInt(1);
				int strValueYear = ThreadLocalRandom.current().nextInt(2001, 2098 );
				int strValueAmount = ThreadLocalRandom.current().nextInt(1, 9999 );
				// click on Add Expense.
				// Use time to handle loading through slow connection. 
				driver.findElement(By.id("day")).sendKeys(""+strValueDay);
				driver.findElement(By.id("month")).clear();
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
				//***********************************************************
				String ErrorText = driver.findElement(By.id("month")).getAttribute("validationMessage");
				Boolean Passed= ErrorText.equals("Please enter a number.");
				//***********************************************************
				//Assertion of 'month' Error
				
				if (Passed)
				{
					System.out.println("Assert of 'month is empty' is passed successfully.");
				}else
				{
					fail("Assert of 'month is empty' failed.");
				}
	}
	
	@Test
	public void f_createExpense_noyear() throws InterruptedException{
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
				int strValueDay = ThreadLocalRandom.current().nextInt(1, 27);
				int strValueMonth = ThreadLocalRandom.current().nextInt(1);
				//int strValueYear = ThreadLocalRandom.current().nextInt(2001, 2098 );
				int strValueAmount = ThreadLocalRandom.current().nextInt(1, 9999 );
				// click on Add Expense.
				// Use time to handle loading through slow connection. 
				driver.findElement(By.id("day")).sendKeys(""+strValueDay);
				driver.findElement(By.id("month")).sendKeys(""+strValueMonth);
				driver.findElement(By.id("year")).clear();
				
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
				//***********************************************************
				String ErrorText = driver.findElement(By.id("year")).getAttribute("validationMessage");
				Boolean Passed= ErrorText.equals("Please enter a number.");
				//***********************************************************
				//Assertion of 'year' Error
				
				if (Passed)
				{
					System.out.println("Assert of 'year is empty' is passed successfully.");
				}else
				{
					fail("Assert of 'year is empty' failed.");
				}
	}
	
	@Test
	public void g_createExpense_noamount() throws InterruptedException{
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
				int strValueDay = ThreadLocalRandom.current().nextInt(1, 27);
				int strValueMonth = ThreadLocalRandom.current().nextInt(1);
				int strValueYear = ThreadLocalRandom.current().nextInt(2001, 2098 );
				//int strValueAmount = ThreadLocalRandom.current().nextInt(1, 9999 );
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
				
				driver.findElement(By.id("amount")).clear();
				driver.findElement(By.id("reason")).sendKeys(strValueExpe);

				driver.findElement(By.id("submit")).click();
				
				Thread.sleep(2000);
				//***********************************************************
				String ErrorText = driver.findElement(By.id("amount")).getAttribute("validationMessage");
				Boolean Passed= ErrorText.equals("Please fill out this field.");
				//***********************************************************
				//Assertion of 'amount' Error
				
				if (Passed)
				{
					System.out.println("Assert of 'amount is empty' is passed successfully.");
				}else
				{
					fail("Assert of 'amount is empty' failed.");
				}
	}
	
	@Test
	public void h_createExpense_nodescription() throws InterruptedException{
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
				//String strValueExpe = RandomValue(10, 100, "Automated", false);
				int strValueDay = ThreadLocalRandom.current().nextInt(1, 9);
				int strValueMonth = ThreadLocalRandom.current().nextInt(1);
				int strValueYear = ThreadLocalRandom.current().nextInt(2001, 2098 );
				int strValueAmount = ThreadLocalRandom.current().nextInt(1, 9999 );
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
				driver.findElement(By.id("reason")).clear();

				driver.findElement(By.id("submit")).click();
				
				Thread.sleep(2000);
				//***********************************************************
				String ErrorText = driver.findElement(By.id("reason")).getAttribute("validationMessage");
				Boolean Passed= ErrorText.equals("Please fill out this field.");
				//***********************************************************
				//Assertion of 'reason' Error
				
				if (Passed)
				{
					System.out.println("Assert of 'reason is empty' is passed successfully.");
				}else
				{
					fail("Assert of 'reason is empty' failed.");
				}
	}

	@Test
	public void i_createExpense_dayExceedLimit() throws InterruptedException{
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
				//int strValueDay = ThreadLocalRandom.current().nextInt(1, 27 + 1);
				int strValueMonth = ThreadLocalRandom.current().nextInt(1);
				int strValueYear = ThreadLocalRandom.current().nextInt(2001, 2098 );
				int strValueAmount = ThreadLocalRandom.current().nextInt(1, 9999 );
				// click on Add Expense.
				// Use time to handle loading through slow connection. 
				// Set day to empty
				driver.findElement(By.id("day")).sendKeys("34");
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
				//***********************************************************
				String ErrorText = driver.findElement(By.id("day")).getAttribute("validationMessage");
				Boolean Passed= ErrorText.equals("Please select a value that is no more than 31.");
				//***********************************************************
				//Assertion of 'Day' Error
				
				if (Passed)
				{
					System.out.println("Assert of 'day exceed limit' is passed successfully.");
				}else
				{
					fail("Assert of 'day exceed limit' failed.");
				}
	}
	
	@Test
	public void j_createExpense_noCategory() throws InterruptedException{
				// Open Browser
				LoadBrowser();

				int ValueToAddedCate = ThreadLocalRandom.current().nextInt(845, 9989792);
				String varUsernameNoCat = "TestName3"+ValueToAddedCate;
				String varPasswordNoCat = "TestPass3"+ValueToAddedCate;
			
				driver.navigate().to("http://thawing-shelf-73260.herokuapp.com/register.jsp");

				FillUserData(driver, varUsernameNoCat, varPasswordNoCat, varPasswordNoCat, true,true);
				
				driver.navigate().to("http://thawing-shelf-73260.herokuapp.com");
				
				FillUserData(driver, varUsernameNoCat, varPasswordNoCat, varPasswordNoCat, true,false);
				
				Boolean LoginSuccess= AssertElement("editaccount",varUsernameNoCat);
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
				int strValueDay = ThreadLocalRandom.current().nextInt(1, 27);
				int strValueMonth = ThreadLocalRandom.current().nextInt(1);
				int strValueYear = ThreadLocalRandom.current().nextInt(2001, 2098 );
				int strValueAmount = ThreadLocalRandom.current().nextInt(1, 9999);
				// click on Add Expense.
				// Use time to handle loading through slow connection. 
				driver.findElement(By.id("day")).sendKeys(""+strValueDay);
				driver.findElement(By.id("month")).sendKeys(""+strValueMonth);
				driver.findElement(By.id("year")).sendKeys(""+strValueYear);
				
				//Select dropdown = new Select(driver.findElement(By.id("category")));
				
				driver.findElement(By.id("amount")).sendKeys(""+strValueAmount);
				driver.findElement(By.id("reason")).sendKeys(strValueExpe);

				driver.findElement(By.id("submit")).click();
				
				Thread.sleep(2000);
				
				//***********************************************************
				String ErrorText = driver.findElement(By.id("category")).getAttribute("validationMessage");
				Boolean Passed= ErrorText.equals("Please select an item in the list.");
				//***********************************************************
				//Assertion of 'No Category' Error
				
				if (Passed)
				{
					System.out.println("Assert of 'Category is empty' is passed successfully.");
				}else
				{
					fail("Assert of 'Category is empty' failed.");
				}
				
	}
	
	@After
	public void CloseBrowser()
	{
		driver.close();
		
	}

}
