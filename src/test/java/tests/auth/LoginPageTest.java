package tests.auth;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.auth.LoginPage;
import tests.BaseTest;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Login Test using Page Object Model")
public class LoginPageTest extends BaseTest {
    static WebDriverWait wait;
    static LoginPage loginPage;

    @BeforeAll
    static void initPage(){
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    @Test
    @Order(1)
    @DisplayName("Login Test with valid credentials")
    void testLoginSuccess() {
        loginPage.navigate();
        loginPage.login("huyprogaming30@gmail.com", "Huytty66");

        boolean isLoginSuccess = driver.getCurrentUrl().contains("home")
                || loginPage.isLogoutButtonVisible();
        assertTrue(isLoginSuccess, "Login should be successful and dashboard or logout button should be visible");
    }

    @Test
    @Order(2)
    @DisplayName("Login Test with invalid credentials")
    void testLoginWithInvalidCredentials() {
        loginPage.navigate();
        loginPage.login("huyprogaming30@gmail.com", "ahskdhajshdkask");

        boolean isLoginFail = loginPage.isErrorMessageVisible();
        assertTrue(isLoginFail, "Login should fail: should not redirect to home, logout button should not be visible, and error message should be displayed");
    }

    @ParameterizedTest
    @Order(3)
    @DisplayName("Login Test from CSV with expected result")
    @org.junit.jupiter.params.provider.CsvFileSource(resources = "/auth/login-test-data.csv", numLinesToSkip = 1)
    void testLoginFromCSV(String username, String password, String expected) {
        loginPage.navigate();
        username = (username == null) ? "" : username.trim();
        password = (password == null) ? "" : password.trim();
        loginPage.login(username, password);

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after login: " + currentUrl);
        if (expected.equals("success")) {
            boolean redirectedToHome = currentUrl.contains("home") || loginPage.isLogoutButtonVisible();
            assertTrue(redirectedToHome, "Should be redirected to home page or see logout button after successful login");
        } else {
            boolean isLoginFail = loginPage.isErrorMessageVisible()
                    && !currentUrl.contains("home")
                    && !loginPage.isLogoutButtonVisible();
            assertTrue(isLoginFail, "Login should fail: should not redirect to home, logout button should not be visible, and error message should be displayed");
        }
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}