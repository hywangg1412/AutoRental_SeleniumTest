package pages.user;

import org.openqa.selenium.WebDriver;
import pages.BasePage;

public class UpdateCitizenCardPage extends BasePage {
    public UpdateCitizenCardPage(WebDriver driver) {
        super(driver);
    }

    public void editCitizenCardInfo(String citizenIdNumber, String citizenFullName, String citizenDob, String citizenIssueDate, String citizenPlaceOfIssue, String citizenIdImagePath, String citizenIdBackImagePath, String expectedResult) {
        // Tắt animation khi chạy test
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.isTestMode = true;");

        driver.findElement(org.openqa.selenium.By.id("editCitizenIdBtn")).click();

        org.openqa.selenium.WebElement idNumberInput = driver.findElement(org.openqa.selenium.By.id("citizenIdNumber"));
        org.openqa.selenium.WebElement fullNameInput = driver.findElement(org.openqa.selenium.By.id("citizenFullName"));
        org.openqa.selenium.WebElement dobInput = driver.findElement(org.openqa.selenium.By.id("citizenDob"));
        org.openqa.selenium.WebElement issueDateInput = driver.findElement(org.openqa.selenium.By.id("citizenIssueDate"));
        org.openqa.selenium.WebElement placeInput = driver.findElement(org.openqa.selenium.By.id("citizenPlaceOfIssue"));
        org.openqa.selenium.WebElement frontImageInput = driver.findElement(org.openqa.selenium.By.id("citizenIdImageInput"));
        org.openqa.selenium.WebElement backImageInput = driver.findElement(org.openqa.selenium.By.id("citizenIdBackImageInput"));

        // Citizen ID Number
        idNumberInput.clear();
        idNumberInput.sendKeys(citizenIdNumber == null ? "" : citizenIdNumber);
        triggerInputAndBlur(idNumberInput);
        sleep(20);
        if (!"success".equalsIgnoreCase(expectedResult) && citizenIdNumber != null && !citizenIdNumber.isEmpty()) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 500) {
                if (isCitizenIdNumberErrorVisible()) break;
                sleep(10);
            }
        }

        // Full Name
        fullNameInput.clear();
        fullNameInput.sendKeys(citizenFullName == null ? "" : citizenFullName);
        triggerInputAndBlur(fullNameInput);
        sleep(20);
        if (!"success".equalsIgnoreCase(expectedResult) && citizenFullName != null && !citizenFullName.isEmpty()) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 500) {
                if (isCitizenFullNameErrorVisible()) break;
                sleep(10);
            }
        }

        // Date of Birth
        dobInput.clear();
        dobInput.sendKeys(citizenDob == null ? "" : citizenDob);
        triggerInputAndBlur(dobInput);
        sleep(20);
        if (!"success".equalsIgnoreCase(expectedResult) && citizenDob != null && !citizenDob.isEmpty()) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 500) {
                if (isCitizenDobErrorVisible()) break;
                sleep(10);
            }
        }

        // Issue Date
        issueDateInput.clear();
        issueDateInput.sendKeys(citizenIssueDate == null ? "" : citizenIssueDate);
        triggerInputAndBlur(issueDateInput);
        sleep(20);
        if (!"success".equalsIgnoreCase(expectedResult) && citizenIssueDate != null && !citizenIssueDate.isEmpty()) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 500) {
                if (isCitizenIssueDateErrorVisible()) break;
                sleep(10);
            }
        }

        // Place of Issue
        placeInput.clear();
        placeInput.sendKeys(citizenPlaceOfIssue == null ? "" : citizenPlaceOfIssue);
        triggerInputAndBlur(placeInput);
        sleep(20);
        if (!"success".equalsIgnoreCase(expectedResult) && citizenPlaceOfIssue != null && !citizenPlaceOfIssue.isEmpty()) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 500) {
                if (isCitizenPlaceOfIssueErrorVisible()) break;
                sleep(10);
            }
        }

        // Front Image
        if (citizenIdImagePath != null && !citizenIdImagePath.isEmpty()) {
            frontImageInput.sendKeys(citizenIdImagePath);
        }
        if (!"success".equalsIgnoreCase(expectedResult) && (citizenIdImagePath == null || citizenIdImagePath.isEmpty() || !citizenIdImagePath.matches(".*\\.(jpg|jpeg|png|gif|bmp)$"))) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 500) {
                if (isCitizenIdImageErrorVisible()) break;
                sleep(10);
            }
        }

        // Back Image
        if (citizenIdBackImagePath != null && !citizenIdBackImagePath.isEmpty()) {
            backImageInput.sendKeys(citizenIdBackImagePath);
        }
        if (!"success".equalsIgnoreCase(expectedResult) && (citizenIdBackImagePath == null || citizenIdBackImagePath.isEmpty() || !citizenIdBackImagePath.matches(".*\\.(jpg|jpeg|png|gif|bmp)$"))) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 500) {
                if (isCitizenIdBackImageErrorVisible()) break;
                sleep(10);
            }
        }
    }

    private void triggerInputAndBlur(org.openqa.selenium.WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", element);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));", element);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isCitizenIdNumberErrorVisible() {
        org.openqa.selenium.WebElement errorDiv = driver.findElement(org.openqa.selenium.By.id("citizenIdNumberError"));
        return errorDiv.isDisplayed() && !errorDiv.getText().trim().isEmpty();
    }
    public boolean isCitizenFullNameErrorVisible() {
        org.openqa.selenium.WebElement errorDiv = driver.findElement(org.openqa.selenium.By.id("citizenFullNameError"));
        return errorDiv.isDisplayed() && !errorDiv.getText().trim().isEmpty();
    }
    public boolean isCitizenDobErrorVisible() {
        org.openqa.selenium.WebElement errorDiv = driver.findElement(org.openqa.selenium.By.id("citizenDobError"));
        return errorDiv.isDisplayed() && !errorDiv.getText().trim().isEmpty();
    }
    public boolean isCitizenIssueDateErrorVisible() {
        org.openqa.selenium.WebElement errorDiv = driver.findElement(org.openqa.selenium.By.id("citizenIssueDateError"));
        return errorDiv.isDisplayed() && !errorDiv.getText().trim().isEmpty();
    }
    public boolean isCitizenPlaceOfIssueErrorVisible() {
        org.openqa.selenium.WebElement errorDiv = driver.findElement(org.openqa.selenium.By.id("citizenPlaceOfIssueError"));
        return errorDiv.isDisplayed() && !errorDiv.getText().trim().isEmpty();
    }
    public boolean isCitizenIdImageErrorVisible() {
        org.openqa.selenium.WebElement errorDiv = driver.findElement(org.openqa.selenium.By.id("citizenIdImageError"));
        return errorDiv.isDisplayed() && !errorDiv.getText().trim().isEmpty();
    }
    public boolean isCitizenIdBackImageErrorVisible() {
        org.openqa.selenium.WebElement errorDiv = driver.findElement(org.openqa.selenium.By.id("citizenIdBackImageError"));
        return errorDiv.isDisplayed() && !errorDiv.getText().trim().isEmpty();
    }

    public boolean isUpdateButtonEnabled() {
        org.openqa.selenium.WebElement updateBtn = driver.findElement(org.openqa.selenium.By.id("saveCitizenIdBtn"));
        String disabledAttr = updateBtn.getAttribute("disabled");
        return updateBtn.isEnabled() && (disabledAttr == null || disabledAttr.equals("false"));
    }
}
