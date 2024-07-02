import java.time.Duration;
import java.time.LocalDate;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCases {

	WebDriver driver = new ChromeDriver();
	String url = "https://almosafer.com/en";
	String url2 = "https://almosafer.com/ar";
	String expectedEngilishLanguage = "en";
	String expectedArabicLanguage = "ar";
	Random rand = new Random();

	@BeforeTest
	public void Setup() {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(url);
		driver.findElement(By.className("cta__saudi")).click();
	}

	@Test(enabled = false)
	public void verifyDefualtLanguage() {
		String actualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
		Assert.assertEquals(actualLanguage, expectedEngilishLanguage, "English language is not the defualt language");
	}

	@Test(enabled = false)
	public void VerifyDefulatCurrency() {
		String expCurrency = driver.findElement(By.xpath("//button[@data-testid=\"Header__CurrencySelector\"]"))
				.getText();
		String actCurrency = "SAR";
		Assert.assertEquals(expCurrency, actCurrency, "Defualt currency is not SAR");

	}

	@Test(enabled = false)
	public void verifyContactNumber() {
		String expectedContactNum = "+966554400000";
		String actualContactNum = driver.findElement(By.tagName("strong")).getText();

		Assert.assertEquals(actualContactNum, expectedContactNum, "Contact number is not correct");
	}

	@Test(enabled = false)
	public void verifyQetafLogo() {
		boolean actualLogo = driver.findElement(By.cssSelector(".sc-bdVaJa.bxRSiR.sc-ekulBa.eYboXF")).isDisplayed();
		boolean isQitafLogoDesplayed = true;
		Assert.assertEquals(actualLogo, isQitafLogoDesplayed, "Qetaf logo is not displayed");
	}

	@Test(enabled = false)
	public void verifyHotelsTabNotSelectedByDefualt() {
		String acutal = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels")).getAttribute("aria-selected");
		String expected = "false";
		Assert.assertEquals(acutal, expected, "Hotels tab is selected by defualt");
	}

	@Test(enabled = false)
	public void verifyFlightDeparture() {
		LocalDate currenDate = LocalDate.now();

		int actualFlightDeparture = Integer.parseInt(driver
				.findElement(By.cssSelector("div[class='sc-iHhHRJ sc-kqlzXE blwiEW'] span[class='sc-cPuPxo LiroG']"))
				.getText());
		int actualFlightReturn = Integer.parseInt(driver
				.findElement(By.cssSelector("div[class='sc-iHhHRJ sc-OxbzP edzUwL'] span[class='sc-cPuPxo LiroG']"))
				.getText());

		int expDepartureDay = currenDate.plusDays(1).getDayOfMonth();
		int expReturnDay = currenDate.plusDays(2).getDayOfMonth();

		Assert.assertEquals(actualFlightDeparture, expDepartureDay);
		Assert.assertEquals(actualFlightReturn, expReturnDay);
	}

	@Test(priority = 1)
	public void verifyThatTheLanguageChanged() {
		String arrLanguage[] = { url, url2 };
		int randIndex = rand.nextInt(arrLanguage.length);
		driver.get(arrLanguage[randIndex]);

		if (driver.getCurrentUrl().contains("en")) {

			String actualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
			Assert.assertEquals(actualLanguage, expectedEngilishLanguage,
					"English language is not the defualt language");

		} else if (driver.getCurrentUrl().contains("ar")) {
			String actualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
			Assert.assertEquals(actualLanguage, expectedArabicLanguage, "Arabic language is not the defualt language");

		}
	}

	@Test(priority = 2)
	public void switchtoHotelTabAndSetLocation() {
		driver.findElement(By.id("uncontrolled-tab-example-tab-hotels")).click();

		String engAutoComlete[] = { "Jedah", "Riyadh", "Dubai" };
		String arAutoComlete[] = { "جدة", "رياض" };

		if (driver.getCurrentUrl().contains(expectedEngilishLanguage)) {
			int randIndex = rand.nextInt(engAutoComlete.length);
			WebElement ele = driver.findElement(By.xpath("//input[@data-testid=\"AutoCompleteInput\"]"));
			ele.sendKeys(engAutoComlete[randIndex]);
			ele.sendKeys(Keys.ENTER);
		} else if (driver.getCurrentUrl().contains(expectedArabicLanguage)) {
			int randIndex = rand.nextInt(arAutoComlete.length);
			WebElement ele = driver.findElement(By.xpath("//input[@data-testid=\"AutoCompleteInput\"]"));
			ele.sendKeys(arAutoComlete[randIndex]);
			ele.sendKeys(Keys.ENTER);

		}

	}

	@Test(priority = 3)
	public void selectNumOfVistorandRoom() {
		WebElement ele = driver
				.findElement(By.xpath("//select[@data-testid=\"HotelSearchBox__ReservationSelect_Select\"]"));
		Select selector = new Select(ele);
		int randIndex = rand.nextInt(2);
		selector.selectByIndex(randIndex);

		driver.findElement(By.xpath("//button[@data-testid=\"HotelSearchBox__SearchButton\"]")).click();

	}

	@Test(priority = 4)
	public void waitLoadingFullyComplete() {
		//implicitWAit
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		//exciplicitWait
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofMinutes(1));
		By ele = By.xpath("//span[@data-testid=\"HotelSearchResult__resultsFoundCount\"]");
		WebElement resultEle=wait.until(ExpectedConditions.presenceOfElementLocated(ele));
		String text=resultEle.getText();	
		Assert.assertEquals(text.contains("وجدنا")||text.contains("found"), true);
	}

}
