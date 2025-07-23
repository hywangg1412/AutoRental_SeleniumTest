package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By avatarLink = By.cssSelector("a.user-avatar");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void goToUserProfile() {
        click(avatarLink);
    }
}
