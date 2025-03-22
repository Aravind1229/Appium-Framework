package tests.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.utils.AppiumUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import pageObjects.android.FormPage;

public class BaseTest extends AppiumUtils {

	public static AppiumDriverLocalService service;
	public static AndroidDriver driver;
	public FormPage formPage;

	@BeforeSuite(alwaysRun = true)
	public void startServer() throws URISyntaxException, IOException {
		Properties properties = new Properties();
		FileInputStream inputStream = new FileInputStream(
				new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\utils\\resources\\data.properties"));
		properties.load(inputStream);
		String ipAddress = properties.getProperty("ipAddress");
		String port = properties.getProperty("port");
		service = startAppiumService(ipAddress, Integer.parseInt(port));
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("Pixel 9");
		options.setApp(System.getProperty("user.dir") + "\\src\\test\\resources\\General-Store.apk");
		driver = new AndroidDriver(service.getUrl(), options);
		formPage = new FormPage(driver);
	}

	@AfterSuite(alwaysRun = true)
	public void stopServer() {
		driver.quit();
		service.stop();
	}

}
