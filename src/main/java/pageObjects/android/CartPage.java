package pageObjects.android;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CartPage extends AndroidActions {
	AndroidDriver driver;

	public CartPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
	private WebElement title;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
	private List<WebElement> productPrices;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
	private WebElement cartPrice;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
	private WebElement termsButton;

	@AndroidFindBy(id = "android:id/button1")
	private WebElement termsCloseButton;

	@AndroidFindBy(className = "android.widget.CheckBox")
	private WebElement emailNotificationsCheck;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
	private WebElement proceedPurchaseButton;

	public double getSumofProductPrices() {
		int productCount = productPrices.size();
		double sum = 0;
		for (int i = 0; i < productCount; i++) {
			String amountString = productPrices.get(i).getText();
			Double price = getFormattedAmount(amountString);
			sum += price;
		}
		return sum;
	}

	public Double getCartPrice() {
		String displaySum = cartPrice.getText();
		Double totalPrice = getFormattedAmount(displaySum);
		System.out.println(totalPrice);
		return totalPrice;
	}

	public void readTerms() {
		longPress(termsButton);
		termsCloseButton.click();

	}

	public void checkEmailNotifications() {
		emailNotificationsCheck.click();
	}

	public void proceedPurchase() {
		proceedPurchaseButton.click();
	}
}
