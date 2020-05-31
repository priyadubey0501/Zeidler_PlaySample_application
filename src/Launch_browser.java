import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Launch_browser 
{
   
	WebDriver driver=null;

	@BeforeClass()
	public void browser_launch()
   {
	  System.setProperty("webdriver.chrome.driver", "./exefiles/chromedriver.exe");
	  driver= new ChromeDriver();
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  driver.get("http://computer-database.herokuapp.com/computers%C2%A0228");
	  System.out.println("brower launched and navigated");
	  
   }
	
	@Test(priority=1)// verify the user has navigated to correct url
	public void PSA_page_verify()
	{
		String homepage_string = driver.findElement(By.xpath("//div//a[text()='Displaying 1 to 10 of 574']")).getText();
		System.out.println(homepage_string);
		String curentUrl = driver.getCurrentUrl();
		String home_url="http://computer-database.herokuapp.com/computers";
		
		Assert.assertEquals(home_url,curentUrl);
		System.out.println("Navigated to correct URL");
		
	}
	
	@Test(dependsOnMethods={"PSA_page_verify"} ,priority=2)//  Adding a new computer
	public void Add_computer()
	{
		driver.findElement(By.id("add")).click();
		driver.findElement(By.id("name")).sendKeys("New");
		driver.findElement(By.id("introduced")).sendKeys("2012-01-01");
		driver.findElement(By.id("discontinued")).sendKeys("2013-01-01");
		WebElement companyMenu = driver.findElement(By.id("company"));
		Select sel= new Select(companyMenu);
		sel.selectByVisibleText("IBM");
		driver.findElement(By.xpath("//input[@value='Create this computer']")).click();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String AlertMessage = driver.findElement(By.xpath("//div[@class='alert-message warning']")).getText();
		
		String Expected_alert= "Done! Computer a has been created";
		
		Assert.assertEquals(AlertMessage, Expected_alert);
		System.out.println("added a new computer succesfully");
		
		
	}
	
	
	
      @Test(dependsOnMethods={"PSA_page_verify"} ,priority=3)// filter function
      public void search_computer()
      {
    	  driver.findElement(By.id("searchbox")).sendKeys("new");
    	  driver.findElement(By.id("searchsubmit")).click();
    	  String ExpectedFilterURl="http://computer-database.herokuapp.com/computers?f=new";
    	  String actualSerchURL=driver.getCurrentUrl();
    	  Assert.assertEquals(actualSerchURL, ExpectedFilterURl);
    	  System.out.println("search complete.");
      }
	
	
	
}
