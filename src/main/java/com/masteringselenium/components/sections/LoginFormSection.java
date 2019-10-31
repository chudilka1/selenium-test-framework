package com.masteringselenium.components.sections;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.masteringselenium.components.Readable;
import com.masteringselenium.components.Visible;
import com.masteringselenium.components.elements.buttons.SubmitButton;

import io.qameta.allure.Step;

public class LoginFormSection implements Readable, Visible {

	private static final By LOGIN_FORM_SELECTOR = By.cssSelector(".login-box-body");
	private static final By LOGIN_FORM_TEXT_SELECTOR = By.cssSelector(".login-box-msg");
	private static final By EMAIL_FIELD = By.name("_username");
	private static final By PASSWORD_FIELD = By.id("password");
	private static final By SUBMIT_BUTTON = By.cssSelector(".btn.btn-primary.btn-block");

	private WebDriver driver;

	public LoginFormSection(WebDriver driver) {
		this.driver = driver;
	}

	public SubmitButton getSubmitButton() {
		return new SubmitButton(driver, LOGIN_FORM_SELECTOR);
	}

	@Override
	public LoginFormSection verifyVisible() {
		driver.findElement(LOGIN_FORM_SELECTOR).isDisplayed();
		return this;
	}

	@Override
	public LoginFormSection verifyContainsText(String expectedText) {
		String actualResult = driver.findElement(LOGIN_FORM_TEXT_SELECTOR).getText();
		assertThat(actualResult).contains(expectedText);
		return this;
	}

	@Override
	public LoginFormSection verifyExactText(String expectedText) {
		String actualResult = driver.findElement(LOGIN_FORM_TEXT_SELECTOR).getText();
		assertThat(actualResult).isEqualToIgnoringCase(expectedText);
		return this;
	}

    /*@Override
    public LoginFormSection click() {
        driver.findElement(SUBMIT_BUTTON).click();
        return this;
    }*/

	@Step("Enter email: {0}")
	public LoginFormSection enterEmail(String email) {
		driver.findElement(EMAIL_FIELD).clear();
		driver.findElement(EMAIL_FIELD).sendKeys(email);
		return this;
	}

	public LoginFormSection enterPassword(String password) {
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		return this;
	}

	public LoginFormSection clickSubmitButton() {
		driver.findElement(SUBMIT_BUTTON).click();
		return this;
	}

	public LoginFormSection login(String email, String password) {
		this.enterEmail(email)
				.enterPassword(password)
				.clickSubmitButton();
		return this;
	}

   /* public IndexPage getIndexPage() {
        return new IndexPage(driver);
    }*/

	/*public SignInPage enterEmail(String email) {
		emailAddressField.clear();
		emailAddressField.sendKeys(email);

		return this;
	}

	public SignInPage enterPassword(String password) {
		passwordField.clear();
		passwordField.sendKeys(password);

		return this;
	}

	public void clickLoginButton() {
		signInButton.click();
	}

	public boolean alertMessageIsNotDisplayed() {
		return alertMessage.size() == 0;
	}

	public WebElement getAlertMessage(int lineNumber) {
		if(alertMessage.size() == 0) {
			throw new NullPointerException();
		}

		return alertMessage.get(lineNumber);
	}*/
}
