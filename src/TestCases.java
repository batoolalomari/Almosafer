import java.time.Duration;
import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCases {

	WebDriver driver = new ChromeDriver();
	String url = "https://almosafer.com/en";

	@BeforeTest
	public void Setup() {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(url);
		driver.findElement(By.className("cta__saudi")).click();
	}

	@Test
	public void verifyDefualtLanguage() {
		String expectedLanguage = "en";
		String actualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
		Assert.assertEquals(actualLanguage, expectedLanguage, "English language is not the defualt language");
	}

	@Test
	public void VerifyDefulatCurrency() {
		String expCurrency = driver.findElement(By.xpath("//button[@data-testid=\"Header__CurrencySelector\"]"))
				.getText();
		String actCurrency = "SAR";
		Assert.assertEquals(expCurrency, actCurrency, "Defualt currency is not SAR");

	}

	@Test
	public void verifyContactNumber() {
		String expectedContactNum="+966554400000";
		String actualContactNum = driver.findElement(By.tagName("strong")).getText();
		
		Assert.assertEquals(actualContactNum,expectedContactNum, "Contact number is not correct");
	}
	
	@Test
	public void verifyQetafLogo() {
		boolean actualLogo = driver.findElement(By.cssSelector(".sc-bdVaJa.bxRSiR.sc-ekulBa.eYboXF")).isDisplayed();
		boolean isQitafLogoDesplayed=true;
		Assert.assertEquals(actualLogo, isQitafLogoDesplayed, "Qetaf logo is not displayed");
	}
	
	@Test
	public void verifyHotelsTabNotSelectedByDefualt() {
		boolean isHotelsTabSelected=driver.findElement(By.id("uncontrolled-tab-example-tab-hotels")).isSelected();
		boolean expected=false;
		Assert.assertEquals(isHotelsTabSelected, expected,"Hotels tab is selected by defualt");
	}
	
	@Test
	public void verifyFlightDeparture() {
		LocalDate currenDate=LocalDate.now();
		
		int actualDepartureDay=Integer.parseInt(driver.findElement(By.cssSelector(".sc-cPuPxo.LiroG")).getText());
		int expDepartureDay=currenDate.getDayOfMonth()+1;
		
		System.out.println("DepartureDay "+actualDepartureDay);
		Assert.assertEquals(actualDepartureDay, expDepartureDay);
	}
	
	

}
