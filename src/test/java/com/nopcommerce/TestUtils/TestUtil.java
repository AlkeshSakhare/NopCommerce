package com.nopcommerce.TestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nopcommerce.TestBase.TestBase;

public class TestUtil extends TestBase {

	public static final int EXPLICIT_WAIT = 1;
	public static final int IMPLICIT_WAIT = 5;
	public static final int PAGE_LOAD_TIMEOUT = 5;
	public static String TESTDATA_SHEET_PATH = userdir + "/src/test/java/com/nopcommerce/TestData/TestData.xlsx";
	public static Workbook book;
	public static Sheet sheet;
	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws;
	public static XSSFRow row;
	public static XSSFCell cell;

	public static Object[][] getTestData(String sheetName) throws InvalidFormatException {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		// System.out.println(sheet.getLastRowNum() + "--------" +
		// sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				// System.out.println(data[i][k]);
			}
		}
		return data;
	}

	public static void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
		fi = new FileInputStream(TESTDATA_SHEET_PATH);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheetName);
		row = ws.getRow(rownum);
		cell = row.createCell(colnum);
		cell.setCellValue(data);
		fo = new FileOutputStream(TESTDATA_SHEET_PATH);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}

	public static void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	public static void sendKeysClear(WebElement element, String data) {
		element.clear();
		drawBorder(element);
		element.sendKeys(data);
	}

	public static void clickHighlight(WebElement element) {
		drawBorder(element);
		element.click();
	}

	public static void sendKeysByJS(WebElement element, String data) {
		js.executeScript("arguments[0].setAttribute('value', '')", element);
		drawBorder(element);
		js.executeScript("arguments[0].setAttribute('value', '" + data + "')", element);
	}

	public static void clickElementByJS(WebElement element) {
		drawBorder(element);
		js.executeScript("arguments[0].click();", element);
	}

	public static void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 10; i++) {
			changeColor("rgb(0,200,0)", element);// 1
			changeColor(bgcolor, element);// 2
		}
	}

	public static void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}

	public static void refreshBrowserByJS() {
		js.executeScript("history.go(0)");
	}

	public static void navigationByJS() {
		js.executeScript("window.location = 'http://yahoo.com'");
	}

	public static String getTitleByJS() {
		String title = js.executeScript("return document.title;").toString();
		return title;
	}

	public static String getPageInnerText(WebDriver driver) {
		String pageText = js.executeScript("return document.documentElement.innerText;").toString();
		return pageText;
	}

	public static void scrollPageDown() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public static void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void startTcLogger(String message) {
		logger.info("********** " + " Starting " + message + " ********** ");
	}

	public static void endTcLogger(String message) {
		logger.info("********** " + " Ending " + message + " ********** ");
	}

	public static String captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String filePath = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timeStamp + ".png";
		File target = new File(filePath);
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
		return filePath;
	}

	public static void takeScreenshotAtEndOfTest() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + timeStamp + ".png"));
	}

	public String randomestring() {
		String generatedstring = RandomStringUtils.randomAlphabetic(8);
		return (generatedstring);
	}

	public static String randomeNum() {
		String generatedNumber = RandomStringUtils.randomNumeric(4);
		return (generatedNumber);
	}

	public static void downloadFile(String url) {
		try {
			URL website = new URL(url);
			ReadableByteChannel byteChannel = Channels.newChannel(website.openStream());
			FileOutputStream fileOutputStream = new FileOutputStream(userdir + "/Downloads/sample.png");
			fileOutputStream.getChannel().transferFrom(byteChannel, 0, Long.MAX_VALUE);
			fileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void waitForLoad(int waitTime) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(pageLoadCondition);
	}
}
