package testCases;


import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.testBase;
import utilities.DataProviders;

public class TC003_LoginDDT extends testBase {

	@Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"Master", "DataDriven"})
	public void verify_loginDDT(String email, String pass, String status) {
		//log.info("Starting Login Test");
		HomePage homePage = new HomePage(driver);
		homePage.clickMyAccount();
		homePage.clickLogin();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.setEmail(email);
		loginPage.setPass(pass);
		loginPage.clickLoginBtn();

		MyAccountPage myAccount = new MyAccountPage(driver);
		boolean targetPage = myAccount.verifyMyAccountPageExists();

		if (status.equalsIgnoreCase("Valid")) {
			if (targetPage) {
				Assert.assertTrue(true);
				myAccount.clickLogout();
			} else {
				Assert.fail();
			}
		}

		if (status.equalsIgnoreCase("Invalid")) {
			if (targetPage) {
				myAccount.clickLogout();
				Assert.fail();
			} else {
				Assert.assertTrue(true);
			}
		}
	}

}
