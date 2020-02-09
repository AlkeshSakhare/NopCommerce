package com.nopcommerce.TestBase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nopcommerce.TestUtils.TestUtil;
import com.nopcommerce.TestUtils.WebEventListener;
import com.paulhammant.ngwebdriver.NgWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static String browser;
	public static String url;
	public static String username;
	public static String password;
	public static Properties properties;
	public static WebDriver driver;
	public static Actions actions;
	public static JavascriptExecutor js;
	public static NgWebDriver ngWebDriver;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static Logger logger;
	public static String userdir = System.getProperty("user.dir");
	public static WebDriverWait exWait;

	public TestBase() {
		try {
			properties = new Properties();
			logger = Logger.getLogger("Generic");
			properties.load(new FileReader(userdir + "/config/config.properties"));
			PropertyConfigurator.configure(userdir + "/config/log4j.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization() {

		browser = properties.getProperty("browser");
		username = properties.getProperty("username");
		password = properties.getProperty("password");
		url = properties.getProperty("url");

		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "ie":
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			break;
		default:
			System.out.println("You have given browserName as : " + browser
					+ "  Please give valid web browser name in testng.xml");
			break;
		}
		exWait = new WebDriverWait(driver, TestUtil.EXPLICIT_WAIT);
		js = (JavascriptExecutor) driver;
		e_driver = new EventFiringWebDriver(driver);
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

	}

}
