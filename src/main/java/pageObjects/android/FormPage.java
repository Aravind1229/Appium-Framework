package pageObjects.android;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.google.common.collect.ImmutableMap;
import com.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FormPage extends AndroidActions {
	AndroidDriver driver;

	public FormPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	private WebElement nameField;

	@AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Female']")
	private WebElement femaleRadioButton;

	@AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Male']")
	private WebElement maleRadioButton;

	@AndroidFindBy(id = "android:id/text1")
	private WebElement countryDropDown;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Argentina']")
	private WebElement country;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	private WebElement shopButton;

	@AndroidFindBy(xpath = "//android.widget.Toast")
	private WebElement errorMessage;

	public void setNameField(String name) {
		nameField.sendKeys(name);
		driver.hideKeyboard();
	}

	public void selectGender(String gender) {
		if (gender.equalsIgnoreCase("Female")) {
			femaleRadioButton.click();
		} else {
			maleRadioButton.click();
		}
	}

	public void selectCountry(String country) {
		countryDropDown.click();
		scrollToText(country);
		driver.findElement(By.xpath("//android.widget.TextView[@text='" + country + "']")).click();
	}

	public void clickShopButton() {
		shopButton.click();
	}

	public void verifyErrorMeesage() {
		String message = errorMessage.getDomAttribute("name");
		System.out.println(message);
		assertEquals("Please enter your name", message);
	}

	public void setActivity() {
		JavascriptExecutor jse = driver;
		jse.executeScript("mobile: startActivity", ImmutableMap.of("intent",
				"com.androidsample.generalstore/com.androidsample.generalstore.MainActivity"));
	}
}
