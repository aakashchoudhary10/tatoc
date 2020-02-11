package com.qainfotech.automation.tatok;

import static org.testng.Assert.assertEquals;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class AdvanceCourse_Test {
	static String driverloaction = "\\tatok\\chromedriver.exe";
	static WebDriver WebDriver1;
	static String parentwindow;
	static String tatocwindow;
	static String dbURL = "jdbc:mysql://10.0.1.86:3306/tatoc";
	static String Mysqlusername = "tatocuser";
	static String Mysqlpassword = "tatoc01";
	static String QueryGateName;
	static String QueryGatePassKey;
	static String SymbolName;
	static String ExpectedResultQueryGate = "Query Gate - Advanced Course - T.A.T.O.C";

	@Test (priority=1)
	public static void launchwebdriver() {
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

	/* Select Advance Course */
	@Test (priority=4)
	static void clickOnAdvanceCourse() throws InterruptedException, SQLException, ClassNotFoundException {
		WebElement basiclink = WebDriver1.findElement(By.linkText("Advanced Course"));
		basiclink.click();
		System.out.println("Click on 'Advance Course link");


	}

	/* Mouse Hover To Next button */
	@Test (priority=5)
	public static void mouseHoverToNextButton() throws InterruptedException {
		Actions action = new Actions(WebDriver1); // Created object of Action Class
		action.moveToElement(WebDriver1.findElement(By.cssSelector(".menutop.m2"))).build().perform();
		System.out.println("Move the Element to Menu 2");
		WebDriver1.findElement(By.xpath("//span[text()='Go Next']")).click();
		System.out.println("Click on Go Next button");
		Thread.sleep(2000);
	}

	/* Database Connection */
	@Test (priority=6)
	public static void dataBaseConnection() throws ClassNotFoundException, SQLException {
		SymbolName = WebDriver1.findElement(By.cssSelector("div#symboldisplay")).getText();
		System.out.println(SymbolName);
		Class.forName("com.mysql.jdbc.Driver"); //class.forName load the Driver class
		System.out.println("load the Driver class");
		Connection con = DriverManager.getConnection(dbURL, Mysqlusername, Mysqlpassword);
		System.out.println("Created Connection to Database");
		String sqlID = "select id from identity where symbol=\""+SymbolName+"\";"; //Saved Query to store testTable name in sqlStrName
		System.out.println(sqlID);
		Statement st = con.createStatement(); //You can use the Statement Object to send queries
		ResultSet rsid = st.executeQuery(sqlID);
		rsid.next();
		String ID = rsid.getString(1);
		System.out.println(ID);
		String sqlStrname = "select name from credentials where id="+ID+";"; //Saved Query to store testTable password in sqlStrPasskey
		String sqlStrpasskey = "select passkey from credentials where id="+ID+";";
		ResultSet rsName = st.executeQuery(sqlStrname);
		rsName.next();
		QueryGateName = rsName.getString(1);
		System.out.println(QueryGateName);
		ResultSet rspassword = st.executeQuery(sqlStrpasskey);
		rspassword.next();
		QueryGatePassKey = rspassword.getString(1);
		System.out.println(QueryGatePassKey);
		WebDriver1.findElement(By.cssSelector("#name")).sendKeys(QueryGateName);
		System.out.println("User Enter Name");
		WebDriver1.findElement(By.cssSelector("#passkey")).sendKeys(QueryGatePassKey);
		System.out.println("User Enter PassKey");
		WebDriver1.findElement(By.cssSelector("#submit")).click();
		System.out.println("User Click on Submit button");
	}

	/* Video Player */
	@Test (priority=7)
	public static void vedioPlayer() {
		String S = WebDriver1.getTitle();
		String str="Video Player - Advanced Course - T.A.T.O.C";
		if(S.equals(str)){
			WebDriver1.get("http://10.0.1.86/tatoc/advanced/rest/#");
		}
	}

	/* Restful */
	@Test (priority=8)
	public static void restFul() {
		String SessionIDText = WebDriver1.findElement(By.cssSelector("#session_id")).getText();
		String[] SessionIDTextSplit = SessionIDText.split("Session ID: ");
		String SessionID = SessionIDTextSplit[1].trim();
		System.out.println(SessionIDTextSplit[1]);
	}


}
