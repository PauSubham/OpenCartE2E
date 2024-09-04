package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.testBase;

public class TC002_LoginTest extends testBase {

	@Test(groups = {"Master", "Sanity"})
	public void verifyLogin() {
		log.info("Starting Login Test");
		HomePage homePage = new HomePage(driver);
		homePage.clickMyAccount();
		homePage.clickLogin();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.setEmail(getUserName());
		loginPage.setPass(getPass());
		loginPage.clickLoginBtn();

		MyAccountPage myAccount = new MyAccountPage(driver);
		Assert.assertTrue(myAccount.verifyMyAccountPageExists());
	}

}
