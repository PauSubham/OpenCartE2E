package testBase;

import org.testng.annotations.AfterMethod;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class testBase {

	public static WebDriver driver;
	public Logger log;
	public Properties properties;

	@BeforeClass(groups = { "Master", "Sanity", "Regression", "DataDriven" })
	@Parameters({ "OS", "Browser" })
	public void setup(String os, String browser) throws IOException {

		log = LogManager.getLogger(this.getClass());
		// Loading config.properties File
		FileReader file = new FileReader("./src//test//resources//config.properties");
		properties = new Properties();
		properties.load(file);

		if (properties.getProperty("Environment").equalsIgnoreCase("Remote")) {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			// Select the OS
			switch (os.toUpperCase()) {
			case "Windows":
				capabilities.setPlatform(Platform.WIN10);
				break;
			case "Mac":
				capabilities.setPlatform(Platform.MAC);
				break;
			case "Linux":
				capabilities.setPlatform(Platform.LINUX);
				break;
			default:
				capabilities.setPlatform(Platform.WIN10);
				break;
			}

			// Select the Browser
			switch (browser.toUpperCase()) {
			case "CHROME":
				capabilities.setBrowserName("chrome");
				break;
			case "EDGE":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			case "FIREFOX":
				capabilities.setBrowserName("firefox");
				break;
			default:
				capabilities.setBrowserName("chrome");
				break;
			}
			
			driver = new RemoteWebDriver(new URL ("http://localhost:4444/wd/hub"), capabilities);
		}

		if (properties.getProperty("Environment").equalsIgnoreCase("Local")) {
			switch (browser.toUpperCase()) {
			case "CHROME":
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				break;
			case "EDGE":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				break;
			case "FIREFOX":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;
			default:
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(properties.getProperty("appURL"));
	}

	@AfterMethod
	@AfterClass(groups = { "Master", "Sanity", "Regression", "DataDriven" })
	public void tearDown() {
		driver.quit();
	}

	public String getUserName() {
		return properties.getProperty("userName");
	}

	public String getPass() {
		return properties.getProperty("pass");
	}

	public String randomString() {
		return RandomStringUtils.randomAlphabetic(5);
	}

	public String randomNumber() {
		return RandomStringUtils.randomNumeric(10);
	}

	public String captureScreen(String testName) {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + timeStamp;
		File targetFile = new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}

}
