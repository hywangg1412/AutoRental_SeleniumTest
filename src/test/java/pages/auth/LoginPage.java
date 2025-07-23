package pages.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;

public class LoginPage extends BasePage {
    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("loginBtn");
    private final By logoutButton = By.id("logoutBtn");
    private final By errorMsg = By.className("social-login-error");

    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

    public void navigate() {
        navigateTo("http://localhost:8080/autorental/pages/authen/SignIn.jsp");
    }

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterEmail(String email) {
        type(emailField, email);
    }

    public void enterPassword(String password) {

        type(passwordField, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    public boolean isEmailFieldDisplayed() {
        return isElementVisible(emailField);
    }
    public boolean isPasswordFieldDisplayed() {
        return isElementVisible(passwordField);
    }

    public boolean isLogoutButtonVisible() {
        return isElementVisible(logoutButton);
    }

    public boolean isErrorMessageVisible() {
        return isElementVisible(errorMsg);
    }

    public By getSuccessLocator() {
        return By.className("logoutBtn");
    }
    public By getErrorLocator() {
        return By.className("social-login-error");
    }
}