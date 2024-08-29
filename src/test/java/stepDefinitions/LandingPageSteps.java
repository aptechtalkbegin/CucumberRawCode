package stepDefinitions;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LandingPageSteps {
	public WebDriver driver;
	public String landingPageSearchProductResult;
	public String offersPageSearchProductResult;
	
	@Given("User is on GreenCart Landing page")
	public void user_is_on_green_cart_landing_page() {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
	  
	}
	
	@When("user searched with Shortname {string} and extracted actual name of product")
	public void user_searched_with_shortname_and_extracted_actual_name_of_product(String shortName) throws InterruptedException {
     driver.findElement(By.xpath("//input[@type='search']")).sendKeys(shortName);
     Thread.sleep(2000);
     landingPageSearchProductResult = driver.findElement(By.cssSelector("h4.product-name")).getText().split("-")[0].trim();
     
	}
	
	@Then("user searched for shortname {string} in offers page")
	public void user_searched_for_shortname_in_offers_page(String shortName) throws InterruptedException {
		driver.findElement(By.linkText("Top Deals")).click();
		Set<String> wh = driver.getWindowHandles();
		 Iterator<String> it = wh.iterator();
		 String parentWindow = it.next();
		 String childWindow = it.next();
		 driver.switchTo().window(childWindow);
		 driver.findElement(By.xpath("//input[@id='search-field']")).sendKeys(shortName);
		 Thread.sleep(2000);
		 offersPageSearchProductResult = driver.findElement(By.cssSelector("tr td:nth-child(1)")).getText();
		 
	  
	}
	
	@Then("validate product name in offers page matches with Landing Page")
	public void validate_product_name_in_offers_page_matches_with_landing_page() {
		
		Assert.assertEquals(landingPageSearchProductResult, offersPageSearchProductResult);
		driver.quit();
	
	}

}
