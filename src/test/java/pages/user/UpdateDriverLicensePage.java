package pages.user;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

public class UpdateDriverLicensePage extends BasePage {

    public UpdateDriverLicensePage(WebDriver driver) {
        super(driver);
    }

    private final By licenseImageInput = By.id("licenseImageInput");
    private final By editDriverLicenseBtn = By.id("editDriverLicenseBtn");
    private final By saveDriverLicenseBtn = By.id("saveDriverLicenseBtn");
    private final By driverLicenseImg = By.id("driverLicenseImg");

    public void uploadDriverLicenseImage(String filePath) {
        click(editDriverLicenseBtn);
        WebElement input = driver.findElement(licenseImageInput);
        if (!input.isEnabled()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('disabled')", input);
        }
        input.sendKeys(filePath);
        click(saveDriverLicenseBtn);
    }

    public boolean isDriverLicenseImageDisplayed() {
        return isElementVisible(driverLicenseImg);
    }

    public void editDriverLicenseInfo(String licenseNumber, String fullName, String dob, String filePath, String expectedResult) {
        // Tắt animation khi chạy test
        ((JavascriptExecutor) driver).executeScript("window.isTestMode = true;");

        click(editDriverLicenseBtn);

        WebElement licenseNumberInput = driver.findElement(By.id("licenseNumber"));
        WebElement fullNameInput = driver.findElement(By.id("fullName"));
        WebElement dobInput = driver.findElement(By.id("dob"));
        WebElement fileInput = driver.findElement(licenseImageInput);

        enableInputIfDisabled(licenseNumberInput);
        enableInputIfDisabled(fullNameInput);
        enableInputIfDisabled(dobInput);
        enableInputIfDisabled(fileInput);

        // License Number
        licenseNumberInput.clear();
        licenseNumberInput.sendKeys(licenseNumber == null ? "" : licenseNumber);
        triggerInputAndBlur(licenseNumberInput);
        sleep(20);
        if (!"success".equalsIgnoreCase(expectedResult) && licenseNumber != null && !licenseNumber.isEmpty()) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 500) {
                if (isLicenseNumberErrorVisible()) break;
                sleep(10);
            }
        }

        // Full Name
        fullNameInput.clear();
        fullNameInput.sendKeys(fullName == null ? "" : fullName);
        triggerInputAndBlur(fullNameInput);
        sleep(20);
        if (!"success".equalsIgnoreCase(expectedResult) && fullName != null && !fullName.isEmpty()) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 500) {
                if (isFullNameErrorVisible()) break;
                sleep(10);
            }
        }

        // Date of Birth
        dobInput.clear();
        dobInput.sendKeys(dob == null ? "" : dob);
        triggerInputAndBlur(dobInput);
        sleep(20);
        if (!"success".equalsIgnoreCase(expectedResult) && dob != null && !dob.isEmpty()) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 500) {
                if (isDobErrorVisible()) break;
                sleep(10);
            }
        }

        if (filePath != null && !filePath.isEmpty()) {
            fileInput.sendKeys(filePath);
        }

        // Validate ảnh nếu expectedResult là fail và filePath không hợp lệ hoặc để trống
        if (!"success".equalsIgnoreCase(expectedResult) && (filePath == null || filePath.isEmpty() || !filePath.matches(".*\\.(jpg|jpeg|png|gif|bmp)$"))) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 500) {
                if (isLicenseImageErrorVisible()) break;
                sleep(10);
            }
        }

        if ("success".equalsIgnoreCase(expectedResult)) {
            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(1));
            wait.until(driver1 -> {
                WebElement b = driver1.findElement(saveDriverLicenseBtn);
                String disabledAttr = b.getAttribute("disabled");
                return (disabledAttr == null || disabledAttr.equals("false")) && b.isEnabled();
            });
        }
    }

    private void enableInputIfDisabled(WebElement element) {
        if (!element.isEnabled()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('disabled')", element);
        }
    }

    private void triggerInputAndBlur(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", element);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));", element);
    }

    public void clickUpdateAndWaitForModal() {
        WebElement updateBtn = driver.findElement(saveDriverLicenseBtn);
        updateBtn.click();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("serverToast")));
        } catch (Exception e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast")));
        }
    }

    public boolean isLicenseNumberErrorVisible() {
        WebElement errorDiv = driver.findElement(By.id("licenseNumberError"));
        return errorDiv.isDisplayed() && !errorDiv.getText().trim().isEmpty();
    }

    public boolean isFullNameErrorVisible() {
        WebElement errorDiv = driver.findElement(By.id("fullNameError"));
        return errorDiv.isDisplayed() && !errorDiv.getText().trim().isEmpty();
    }

    public boolean isDobErrorVisible() {
        WebElement errorDiv = driver.findElement(By.id("dobError"));
        return errorDiv.isDisplayed() && !errorDiv.getText().trim().isEmpty();
    }

    public boolean isLicenseImageErrorVisible() {
        WebElement errorDiv = null;
        try {
            errorDiv = driver.findElement(By.xpath(
                "//*[contains(@class,'text-danger') and (contains(text(),'file') or contains(text(),'ảnh') or contains(text(),'image') or contains(text(),'tệp') or contains(text(),'type') or contains(text(),'định dạng') or contains(text(),'format') or contains(text(),'not allowed') or contains(text(),'không hợp lệ'))]"
            ));
        } catch (Exception e) {
            return false;
        }
        return errorDiv.isDisplayed() && !errorDiv.getText().trim().isEmpty();
    }

    public boolean isUpdateButtonEnabled() {
        WebElement updateBtn = driver.findElement(saveDriverLicenseBtn);
        String disabledAttr = updateBtn.getAttribute("disabled");
        return updateBtn.isEnabled() && (disabledAttr == null || disabledAttr.equals("false"));
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}