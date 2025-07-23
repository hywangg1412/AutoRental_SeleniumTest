package tests.user;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.auth.LoginPage;
import pages.HomePage;
import pages.user.UpdateDriverLicensePage;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Duration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import tests.BaseTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Upload Driver License Test using Page Object Model")
class UpdateDLTestPage extends BaseTest {
    static WebDriverWait wait;
    static LoginPage loginPage;

    @BeforeAll
    static void initPage(){
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    @Test
    @Order(1)
    @DisplayName("Upload driver license image after login")
    void testUploadDriverLicenseImage() {
        loginPage.navigate();
        loginPage.login("huyprogaming30@gmail.com", "Huytty66");

        HomePage homePage = new HomePage(driver);
        homePage.goToUserProfile();

        UpdateDriverLicensePage userProfilePage = new UpdateDriverLicensePage(driver);
        String filePath = "D:/My_shit/49899a80e6cc343c9e480dd323285243.jpg";
        userProfilePage.uploadDriverLicenseImage(filePath);

        assertTrue(userProfilePage.isDriverLicenseImageDisplayed(), "Driver license image should be displayed after upload");
    }

    @ParameterizedTest
    @Order(2)
    @DisplayName("Upload driver license image with expected result from CSV")
    @CsvFileSource(resources = "/user/driver-license-data.csv", numLinesToSkip = 1)
    void testUploadDriverLicenseImageFromCSV(String licenseNumber, String fullName, String dob, String filePath, String expectedResult) {
        loginPage.navigate();
        loginPage.login("huyprogaming30@gmail.com", "Huytty66");
        HomePage homePage = new HomePage(driver);
        homePage.goToUserProfile();
        UpdateDriverLicensePage userProfilePage = new UpdateDriverLicensePage(driver);

        // Truyền expectedResult vào
        userProfilePage.editDriverLicenseInfo(licenseNumber, fullName, dob, filePath, expectedResult);

        if ("success".equalsIgnoreCase(expectedResult)) {
            assertTrue(userProfilePage.isUpdateButtonEnabled(), "Update button should be enabled when all fields are valid");
            userProfilePage.clickUpdateAndWaitForModal();
            assertTrue(userProfilePage.isDriverLicenseImageDisplayed(), "Driver license image should be displayed after upload");
        } else {
            // Nếu có lỗi hiển thị thì pass, không cần chờ nút disable
            boolean hasError = userProfilePage.isLicenseNumberErrorVisible()
                    || userProfilePage.isFullNameErrorVisible()
                    || userProfilePage.isDobErrorVisible();
            if (!hasError) {
                assertFalse(userProfilePage.isUpdateButtonEnabled(), "Update button should be disabled when there is a validation error");
            } else {
                assertTrue(hasError, "Should show error message for invalid input");
            }
        }
    }
}
