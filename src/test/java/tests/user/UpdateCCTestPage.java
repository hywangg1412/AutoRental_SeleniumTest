package tests.user;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.user.UpdateCitizenCardPage;
import pages.auth.LoginPage;
import pages.HomePage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import java.time.Duration;
import tests.BaseTest;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Upload Citizen ID Test using Page Object Model")
public class UpdateCCTestPage extends BaseTest {
    static WebDriverWait wait;
    static LoginPage loginPage;

    @BeforeAll
    static void initPage(){
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    @ParameterizedTest
    @Order(1)
    @DisplayName("Upload citizen id info with expected result from CSV")
    @CsvFileSource(resources = "/user/citizen-id-data.csv", numLinesToSkip = 1)
    void testUploadCitizenIdInfoFromCSV(String citizenIdNumber, String citizenFullName, String citizenDob, String citizenIssueDate, String citizenPlaceOfIssue, String citizenIdImagePath, String citizenIdBackImagePath, String expectedResult) {
        loginPage.navigate();
        loginPage.login("huyprogaming30@gmail.com", "Huytty66");
        HomePage homePage = new HomePage(driver);
        homePage.goToUserProfile();
        UpdateCitizenCardPage citizenCardPage = new UpdateCitizenCardPage(driver);

        citizenCardPage.editCitizenCardInfo(citizenIdNumber, citizenFullName, citizenDob, citizenIssueDate, citizenPlaceOfIssue, citizenIdImagePath, citizenIdBackImagePath, expectedResult);

        if ("success".equalsIgnoreCase(expectedResult)) {
            // assert nút Update enable (giả sử có hàm isUpdateButtonEnabled)
            assertTrue(citizenCardPage.isUpdateButtonEnabled(), "Update button should be enabled when all fields are valid");
            // Có thể bổ sung clickUpdateAndWaitForModal nếu cần
        } else {
            // Nếu có lỗi hiển thị thì pass, không cần chờ nút disable
            boolean hasError = citizenCardPage.isCitizenIdNumberErrorVisible()
                    || citizenCardPage.isCitizenFullNameErrorVisible()
                    || citizenCardPage.isCitizenDobErrorVisible()
                    || citizenCardPage.isCitizenIssueDateErrorVisible()
                    || citizenCardPage.isCitizenPlaceOfIssueErrorVisible()
                    || citizenCardPage.isCitizenIdImageErrorVisible()
                    || citizenCardPage.isCitizenIdBackImageErrorVisible();
            if (!hasError) {
                assertFalse(citizenCardPage.isUpdateButtonEnabled(), "Update button should be disabled when there is a validation error");
            } else {
                assertTrue(hasError, "Should show error message for invalid input");
            }
        }
    }
}
