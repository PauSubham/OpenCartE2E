package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.testBase;

public class TC001_AccountRegistrationTest extends testBase {

	@Test(groups = {"Master", "Regression"})
	public void verify_account_registratio() {

		log.info("***   Starting TC001_AccountRegistrationTest   ***");

		try {
			HomePage homePage = new HomePage(driver);
			homePage.clickMyAccount();
			log.info("Clicked on my Account Link");
			homePage.clickRegister();
			log.info("Clicked on Register Link");

			AccountRegistrationPage registration = new AccountRegistrationPage(driver);
			log.info("Providing User Details to Register");
			registration.setFirstName(randomString().toUpperCase());
			registration.setLastName(randomString().toUpperCase());
			registration.setEmail(randomString() + "@sfss.com");
			registration.setTele(randomNumber());
			registration.setPassword(randomString() + randomNumber());
			registration.checkPolicy();
			registration.clickContinue();

			log.info("Validating Expected Message");
			String confirmMsg = registration.getConfirmationMsg();
			Assert.assertEquals(confirmMsg, "Your Account Has Been Created!");
		} catch (Exception ex) {
			log.error("Test failed...");
			log.debug("Debug logs..");
			Assert.fail();
		}

	}

}
