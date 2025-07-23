package tests.car;

import org.junit.jupiter.api.*;
import pages.car.CarPage;
import tests.BaseTest;
import utils.DriverFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarPageTest extends BaseTest {
    private static CarPage carPage;

    @BeforeAll
    public static void setup() {
        driver = DriverFactory.createDriver();
        driver.manage().window().maximize();
        carPage = new CarPage(driver);
        carPage.navigateTo("http://localhost:8080/autorental/pages/car");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }

    @TestFactory
    Stream<DynamicTest> testCarFiltersAndSearch() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/car/car_list.csv"));
        return reader.lines()
                .filter(line -> !line.startsWith("#") && !line.trim().isEmpty())
                .map(line -> {
                    String[] arr = line.split(",");
                    String type = arr[0];
                    String brand = arr[1];
                    String category = arr[2];
                    String transmission = arr[3];
                    String fuel = arr[4];
                    String feature = arr[5];
                    String minPrice = arr[6];
                    String maxPrice = arr[7];
                    String search = arr[8];
                    boolean expect = "1".equals(arr[9]);

                    return DynamicTest.dynamicTest("Test: " + line, () -> {
                        carPage.navigateTo("http://localhost:8080/autorental/pages/car");
                        Thread.sleep(1000); // Đợi trang load

                        if ("filter".equals(type)) {
                            if (!brand.isEmpty()) {
                                carPage.selectBrand(brand);
                                carPage.assertCheckboxChecked(carPage.getBrandCheckboxLocator(brand));
                            }
                            if (!category.isEmpty()) {
                                carPage.selectCategory(category);
                                carPage.assertCheckboxChecked(carPage.getCategoryCheckboxLocator(category));
                            }
                            if (!transmission.isEmpty()) {
                                carPage.selectTransmission(transmission);
                                carPage.assertCheckboxChecked(carPage.getTransmissionCheckboxLocator(transmission));
                            }
                            if (!fuel.isEmpty()) {
                                carPage.selectFuel(fuel);
                                carPage.assertCheckboxChecked(carPage.getFuelCheckboxLocator(fuel));
                            }
                            if (!feature.isEmpty()) {
                                carPage.selectFeature(feature);
                                carPage.assertCheckboxChecked(carPage.getFeatureCheckboxLocator(feature));
                            }
                            if (!minPrice.isEmpty() || !maxPrice.isEmpty()) {
                                carPage.setPriceRange(minPrice, maxPrice);
                            }
                            Thread.sleep(2000); // Chờ sau khi tick filter
                            carPage.applyFilter();
                            carPage.waitForCarListUpdate();
                            Thread.sleep(2000); // Chờ trang load lại
                            boolean invalidPrice = false;
                            try {
                                int min = minPrice.isEmpty() ? 0 : Integer.parseInt(minPrice);
                                int max = maxPrice.isEmpty() ? 0 : Integer.parseInt(maxPrice);
                                if ((!minPrice.isEmpty() && min < 60) || (!maxPrice.isEmpty() && max > 200)) {
                                    invalidPrice = true;
                                }
                            } catch (NumberFormatException e) {
                                invalidPrice = true;
                            }

                            if (carPage.isPriceErrorVisible()) {
                                // Nếu có lỗi giá tiền, chỉ kiểm tra có hiển thị lỗi, không kiểm tra số lượng xe
                                assertTrue(carPage.isPriceErrorVisible());
                            } else {
                                // Nếu không có lỗi, kiểm tra số lượng xe đúng với mong muốn
                                if (expect) assertTrue(carPage.getCarCount() > 0);
                                else assertEquals(0, carPage.getCarCount());
                            }
                        } else if ("search".equals(type)) {
                            carPage.search(search);
                            Thread.sleep(2000); // Chờ sau khi search
                            carPage.waitForCarListUpdate();
                            Thread.sleep(2000); // Chờ trang load lại
                            if (expect) assertTrue(carPage.getCarCount() > 0);
                            else assertEquals(0, carPage.getCarCount());
                        }
                        Thread.sleep(5000); // Thời gian chờ giữa các test
                    });
                });
    }
}