package com.nopcommerce.TestCases;

import java.io.IOException;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nopcommerce.TestBase.TestBase;
import com.nopcommerce.TestPage.LoginPage;
import com.nopcommerce.TestUtils.TestUtil;

public class LoginTest extends TestBase {

	LoginPage loginPage;
	static int i = 1;

	public LoginTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
		loginPage = new LoginPage();
	}

	@Test(priority = 1)
	public void verifyLogin() {
		try {
			TestUtil.startTcLogger("verifyLogin");
			loginPage.logintoApp(username, password);
			if (!driver.getPageSource().contains("Login was unsuccessful")) {
				TestUtil.executionPassFail("verifyLogin", "Pass");
				Assert.assertTrue(true, "Login Successful");
			} else {
				TestUtil.executionPassFail("verifyLogin", "Fail");
				Assert.assertTrue(false, "Login unsuccessful");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		} finally {
			TestUtil.endTcLogger("verifyLogin");
		}
	}

	@Test(dataProvider = "getcredentialsdata", enabled = false)
	public void verifyLoginByDD(String un, String ps) throws IOException {
		try {
			TestUtil.startTcLogger("verifyLoginByDD");
			loginPage.logintoApp(un, ps);
			if (driver.getPageSource().contains("Login was unsuccessful")) {
				TestUtil.executionPassFail("verifyLoginByDD", "fail");
				TestUtil.setCellData("Login", i, 2, "Fail");
				Assert.assertTrue(false, "Login Unsuccessful");
			} else {
				TestUtil.executionPassFail("verifyLoginByDD", "Pass");
				TestUtil.setCellData("Login", i, 2, "Success");
				Assert.assertTrue(true, "Login Successful");
			}
		} finally {
			System.out.println("I: " + i);
			i++;
		}
	}

	@DataProvider
	public Object[][] getcredentialsdata() {
		Object data[][] = null;
		try {
			data = TestUtil.getTestData("Login");
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
