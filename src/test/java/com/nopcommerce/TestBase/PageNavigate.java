package com.nopcommerce.TestBase;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageNavigate extends TestBase {
	@FindBy(xpath = "//a[contains(text(),'Logout')]")
	WebElement logoutBtn;

	@FindBy(xpath = "//a[@href='#']/span[text()='Customers']")
	WebElement cutomersMenuLink;

	@FindBy(xpath = "//a[@class='menu-item-link']/span[text()='Customers']")
	WebElement cutomersLink;
}
