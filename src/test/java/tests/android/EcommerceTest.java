package tests.android;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.utils.AndroidActions;
import com.utils.RetryUtils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidTouchAction;
import pageObjects.android.CartPage;
import pageObjects.android.FormPage;
import pageObjects.android.ProductCataloguePage;

public class EcommerceTest extends BaseTest {
	@Test(priority = 0, dataProvider = "dataProvider", retryAnalyzer = RetryUtils.class)
	public void fillForm(HashMap<String, String> input) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		formPage.setNameField(input.get("name"));
		formPage.selectGender("Female");
		formPage.selectGender("Male");
		formPage.selectCountry(input.get("country"));
		formPage.clickShopButton();
	}

	@Test(enabled = false, priority = 1)
	public void verifyErrorMessage() {
		FormPage formPage = new FormPage(driver);
		formPage.verifyErrorMeesage();
	}

	@Test(priority = 2, enabled = true)
	public void addToCart() {
		driver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Jordan 6 Rings\"));"));
		int productCount = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
		for (int i = 0; i < productCount; i++) {
			String productName = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i)
					.getText();
			if (productName.equalsIgnoreCase("Jordan 6 Rings")) {
				driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
			}
		}
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
	}

	@Test(priority = 3, enabled = true)
	public void verifyCart() {
		WebElement cartText = driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title"));
		applyWait(cartText, driver, "text", "Cart");
		String cartProduct = driver.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();
		assertEquals(cartProduct, "Jordan 6 Rings");
	}

	@Test(priority = 4, enabled = false)
	public void verifyCartTotal() throws InterruptedException {
		ProductCataloguePage productCataloguePage = new ProductCataloguePage(driver);
		Thread.sleep(2000);
		productCataloguePage.addToCartByIndex(2);
		productCataloguePage.addToCartByIndex(0);
		CartPage cartPage = productCataloguePage.clickCartButton();
		Double sumPrice = cartPage.getSumofProductPrices();
		Double cartPrice = cartPage.getCartPrice();
		assertEquals(sumPrice, cartPrice);
	}

	@Test(priority = 5, enabled = false)
	public void termsAndConditions() throws InterruptedException {
		CartPage cartPage = new CartPage(driver);
		cartPage.readTerms();
		cartPage.checkEmailNotifications();
		cartPage.proceedPurchase();
	}

	@Test(priority = 6, enabled = false)
	public void webViewOperations() throws InterruptedException {
		Thread.sleep(5000);
		Set<String> contexts = driver.getContextHandles();
		for (String context : contexts) {
			System.out.println(context);
		}
		driver.context("WEBVIEW_com.androidsample.generalstore");

	}

	@DataProvider(name = "dataProvider")
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonData(
				"C:\\Users\\aviar\\eclipse-workspace\\AppiumFrameworkBuilding\\src\\test\\java\\tests\\android\\testData\\eCommerce.json");
		return new Object[][] { { data.get(0) } };
	}
}
