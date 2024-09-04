package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h2[normalize-space()='My Account']")
	WebElement myAccountHeadings;
	
	@FindBy (xpath = "//a[@class='list-group-item'][normalize-space()='Logout']")
	WebElement lnkLogout;

	public boolean verifyMyAccountPageExists() {
		try {
			return myAccountHeadings.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}
	
	public void clickLogout()
	{
		lnkLogout.click();
	}

}
