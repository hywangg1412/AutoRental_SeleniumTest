package pages.car;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import pages.BasePage;

public class CarPage extends BasePage {
    public CarPage(WebDriver driver) {
        super(driver);
    }

    // Các locator cho filter (sidebar)
    private By brandCheckbox(String brand) { return By.xpath("//input[@type='checkbox' and @name='brandId' and following-sibling::span[text()='" + brand + "']]"); }
    private By categoryCheckbox(String category) { return By.xpath("//input[@type='checkbox' and @name='categoryId' and following-sibling::span[text()='" + category + "']]"); }
    private By transmissionCheckbox(String transmission) { return By.xpath("//input[@type='checkbox' and @name='transmissionTypeId' and following-sibling::span[text()='" + transmission + "']]"); }
    private By fuelCheckbox(String fuel) { return By.xpath("//input[@type='checkbox' and @name='fuelTypeId' and following-sibling::span[text()='" + fuel + "']]"); }
    private By featureCheckbox(String feature) { return By.xpath("//input[@type='checkbox' and @name='featureId' and following-sibling::span[text()='" + feature + "']]"); }
    private By minPriceInput = By.id("minPricePerHourInput");
    private By maxPriceInput = By.id("maxPricePerHourInput");
    private By applyFilterBtn = By.xpath("//form[@id='sidebarFilterForm']//button[@type='submit' and contains(@class,'btn-primary') and contains(text(),'Apply Filter')]");
    private By searchInput = By.xpath("//input[@placeholder='Search car, brand...']");
    private By searchBtn = By.xpath("//button[@title='Search']");
    private By carCard = By.xpath("//div[contains(@class,'car-wrap')][.//a[contains(text(),'Book now')]]");

    private By priceErrorMsg = By.id("priceErrorMsg");

    // Brand
    public void selectBrand(String brand) {
        By locator = brandCheckbox(brand);
        try {
            click(locator); // click thực tế vào input
        } catch (Exception e) {
            clickCheckboxJS(locator); // fallback JS nếu bị che
        }
    }
    public By getBrandCheckboxLocator(String brand) { return brandCheckbox(brand); }

    // Category
    public void selectCategory(String category) {
        By locator = categoryCheckbox(category);
        try {
            click(locator);
        } catch (Exception e) {
            clickCheckboxJS(locator);
        }
    }
    public By getCategoryCheckboxLocator(String category) { return categoryCheckbox(category); }

    // Transmission
    public void selectTransmission(String transmission) {
        By locator = transmissionCheckbox(transmission);
        try {
            click(locator);
        } catch (Exception e) {
            clickCheckboxJS(locator);
        }
    }
    public By getTransmissionCheckboxLocator(String transmission) { return transmissionCheckbox(transmission); }

    // Fuel
    public void selectFuel(String fuel) {
        By locator = fuelCheckbox(fuel);
        try {
            click(locator);
        } catch (Exception e) {
            clickCheckboxJS(locator);
        }
    }
    public By getFuelCheckboxLocator(String fuel) { return fuelCheckbox(fuel); }

    // Feature
    public void selectFeature(String feature) {
        By locator = featureCheckbox(feature);
        try {
            click(locator);
        } catch (Exception e) {
            clickCheckboxJS(locator);
        }
    }
    public By getFeatureCheckboxLocator(String feature) { return featureCheckbox(feature); }
    public void setPriceRange(String min, String max) {
        type(minPriceInput, min);
        type(maxPriceInput, max);
    }
    public void applyFilter() {
        WebElement btn = driver.findElement(applyFilterBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        try {
            btn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }
    }
    public void search(String keyword) {
        type(searchInput, keyword);
        click(searchBtn);
    }
    public int getCarCount() {
        List<WebElement> cars = driver.findElements(carCard);
        return cars.size();
    }
    public boolean isPriceErrorVisible() {
        return isElementVisible(priceErrorMsg);
    }

    public void waitForCarListUpdate() {
        // Chờ tối đa 10 giây cho ít nhất 1 card xe xuất hiện hoặc không còn loading
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.or(
                        ExpectedConditions.presenceOfAllElementsLocatedBy(carCard),
                        ExpectedConditions.invisibilityOfElementLocated(By.id("ftco-loader"))
                ));
    }

    public void clickCheckboxJS(By locator) {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript(
                "if(!arguments[0].checked){arguments[0].checked=true; arguments[0].dispatchEvent(new Event('change', {bubbles:true}));}", checkbox);
    }
}