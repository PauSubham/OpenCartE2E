package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver); // Invoking the Constructor of the immediate parent class
	}

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement emailAddressField;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement pwdAddressField;

	@FindBy(xpath = "//input[@value='Login']")
	WebElement loginBtn;

	public void setEmail(String email) {
		emailAddressField.sendKeys(email);
	}

	public void setPass(String pass) {
		pwdAddressField.sendKeys(pass);
		;
	}

	public void clickLoginBtn() {
		loginBtn.click();
	}

}
