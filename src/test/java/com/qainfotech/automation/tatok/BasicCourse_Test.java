package com.qainfotech.automation.tatok;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;


public class BasicCourse_Test {
	static String Box1Color; //tatoc Basic course 2nd program Box1 global variable
	static String Box2Color; //tatoc Basic course 2nd program Box2 global variable
	static String driverloaction = "\\tatok\\chromedriver.exe";
	static WebDriver WebDriver1;
	static String parentwindow;
	static String tatocwindow;
	static String tokanValue;

	@Test (priority=1)
	static void launchwebdriver() {
		System.setProperty("Chromedriver", driverloaction);
		WebDriver driver = new ChromeDriver();
		WebDriver1 = driver;
	}

	@Test (priority=2)
	static void LaunchqainfotechVpn(){
		WebDriver1.get("https://vpn.qainfotech.com/");
		WebDriver1.manage().window().maximize();	
		String parentwindowhandle = WebDriver1.getWindowHandle();
		parentwindow = parentwindowhandle;
	}

	@Test (priority=3)
	static void Launchtatocurl() throws InterruptedException {
		WebDriver1.findElement(By.cssSelector("#username")).sendKeys("aakashchoudhary");
		System.out.println("Enter UserName");
		WebDriver1.findElement(By.cssSelector("#credential")).sendKeys("Cengage103");
		System.out.println("Enter Password");
		WebDriver1.findElement(By.cssSelector("#login_button")).click();
		System.out.println("Click Login");
		Thread.sleep(5000);
		WebDriver1.findElement(By.cssSelector(".fa-external-link")).click();
		System.out.println("Click on Quick Connection");
		WebDriver1.findElement(By.cssSelector("#url")).sendKeys("http://10.0.1.86/tatoc");
		System.out.println("Enter Tatoc URL");
		WebDriver1.findElement(By.xpath("//button[@ng-click='launchConnection()']")).click();

		ArrayList<String> allwindowhandels = new ArrayList<String>(WebDriver1.getWindowHandles());

		for(String window : allwindowhandels) {
			if(window!=parentwindow) {
				WebDriver1.switchTo().window(window);
			}
		}
	}

	/* Select Basic Course */
	@Test (priority=4)
	static void selectBasicCourse() throws InterruptedException {
		//Select Basic Course
		WebElement basiclink = WebDriver1.findElement(By.linkText("Basic Course"));
		basiclink.click();
	}

	/*Click on green box:*/
	@Test (priority=5)
	public static void clickOnGreenBox() {
		WebElement greenbox = WebDriver1.findElement(By.xpath("//div[@class='greenbox']"));
		greenbox.click();
		System.out.println("Greenbox clicked");
		System.out.println("1st Task Completed");
	}

	/*Match two Boxes:*/
	@Test (priority=6)
	public static void matchTwoBoxes() throws InterruptedException {
		WebDriver1.switchTo().frame(WebDriver1.findElement(By.cssSelector("#main")));
		System.out.println("Switched to main frame");
		Thread.sleep(2000);
		WebElement Box1 = WebDriver1.findElement(By.xpath("//div[text()='Box 1']"));
		String B1Color = Box1.getAttribute("class"); System.out.println(B1Color);
		Box1Color = B1Color;
		Thread.sleep(2000);
		WebDriver1.switchTo().frame(WebDriver1.findElement(By.cssSelector("#child")));
		System.out.println("Switched to Child Frame");
		WebElement Box2 = WebDriver1.findElement(By.xpath("//div[text()='Box 2']"));
		String B2Color = Box2.getAttribute("class");
		Box2Color = B2Color;
		System.out.println(B2Color);

		int flag=0;
		while(flag<1)
		{
			if(Box1Color.equals(Box2Color))
			{
				WebDriver1.switchTo().defaultContent();
				System.out.println("Switched to default frame");
				WebDriver1.switchTo().frame(WebDriver1.findElement(By.cssSelector("#main")));
				System.out.println("Switched to main frame");
				WebDriver1.findElement(By.linkText("Proceed")).click();
				System.out.println("Clicked on proceed button");
				System.out.println("2nd Task Completed");
				flag++;
			}
			else
			{
				WebDriver1.switchTo().defaultContent();
				System.out.println("Switched to default frame");
				WebDriver1.switchTo().frame(WebDriver1.findElement(By.cssSelector("#main")));
				System.out.println("Switched to main frame");
				WebDriver1.findElement(By.xpath("//a[text()='Repaint Box 2']")).click();
				System.out.println("Clicked on repaint button");
				Thread.sleep(1000);
				WebDriver1.switchTo().frame(WebDriver1.findElement(By.cssSelector("#child")));
				System.out.println("Switched to child frame");
				WebElement Box3 = WebDriver1.findElement(By.xpath("//div[text()='Box 2']"));
				String B3Color = Box3.getAttribute("class");
				Box2Color = B3Color;
			}

		}
	}

	/*Drag Dropbox:*/
	@Test (priority=7)
	public static void dragDropBox() throws InterruptedException {
		Actions action = new Actions(WebDriver1);
		action.clickAndHold(WebDriver1.findElement(By.xpath("//div[@id='dragbox']"))).moveToElement(WebDriver1.findElement(By.xpath("//div[@id='dropbox']"))).release().build().perform();
		System.out.println("Dragbox dropped");
		WebDriver1.findElement(By.linkText("Proceed")).click();
		System.out.println("Dragbox dropped Task-3 Completed");
	}

	@Test (priority=8)
	/*Launch Popup Window:*/
	public static void launchPopUpWindow() {
		String tatocwindowhandel = WebDriver1.getWindowHandle();
		System.out.println("Get Tatoc Windowhandel:" +tatocwindowhandel);
		tatocwindow = tatocwindowhandel;
		WebDriver1.findElement(By.linkText("Launch Popup Window")).click();
		System.out.println("Click on launch Popup window");
		ArrayList<String> allwindowhandel = new ArrayList<String>(WebDriver1.getWindowHandles());
		for(String window : allwindowhandel) {
			if(window!=tatocwindow||window!=parentwindow) {
				WebDriver1.switchTo().window(window);
			}
		}
		WebDriver1.findElement(By.cssSelector("#name")).sendKeys("Aakash");
		System.out.println("Type User Name");
		WebDriver1.findElement(By.cssSelector("#submit")).click();
		WebDriver1.switchTo().window(tatocwindow);
		System.out.println("Click on Submit button on popup window");
		WebDriver1.findElement(By.linkText("Proceed")).click();
		System.out.println("Dragbox dropped Task-4 Completed");
	}
	
	@Test (priority=9)
	/*Cookie Handling:*/
	public static void cookieHandling() throws InterruptedException {
				WebDriver1.findElement(By.linkText("Generate Token")).click();
				System.out.println("Clicked on Genrate Token");
				WebElement tokan = WebDriver1.findElement(By.cssSelector("#token"));
				String tokanText = tokan.getText();
				System.out.println(tokanText);
				String[] str = tokanText.split("Token: ");

				System.out.println(str[1].trim());
				tokanValue = str[1];
				System.out.println(tokanValue);
				Thread.sleep(2000);
				WebDriver1.manage().addCookie(new Cookie("Token",tokanValue.trim()));
				System.out.println("Added Cookie");

				Set<Cookie> cookies = WebDriver1.manage().getCookies(); 
				for(Cookie cook :cookies) { 
					System.out.println(cook); 
				}

				Thread.sleep(2000);
				WebDriver1.findElement(By.linkText("Proceed")).click();
				System.out.println("Dragbox dropped Task-5 Completed");
	}
	
	/*Verify End Of Basic Tatoc*/
	@Test (priority=10)
	public static void verifyEndOfBasicCourse() {
		String ExpectedTatocEndTitle = "End - T.A.T.O.C";
		String ActualEndTatocTitle= WebDriver1.getTitle();
		System.out.println(ActualEndTatocTitle);
		Assert.assertEquals(ExpectedTatocEndTitle, ActualEndTatocTitle);
	}
}
