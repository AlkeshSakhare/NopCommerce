package com.nopcommerce.TestPage;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nopcommerce.TestBase.TestBase;
import com.nopcommerce.TestUtils.TestUtil;

public class LoginPage extends TestBase {

	public LoginPage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "Email")
	WebElement emailTxt;

	@FindBy(id = "Password")
	WebElement passwordTxt;

	@FindBy(xpath = "//*[contains(@value,'Log in')]")
	WebElement loginBtn;

	public void logintoApp(String user, String pass) {
		try {
			TestUtil.sendKeysClear(emailTxt, user);
			System.out.println(user);
			System.out.println(pass);
			TestUtil.sendKeysClear(passwordTxt, pass);
			TestUtil.clickHighlight(loginBtn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
