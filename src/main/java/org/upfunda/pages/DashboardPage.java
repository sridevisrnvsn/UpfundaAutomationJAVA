import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.net.UrlChecker;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private By dashboardRoot =
            By.xpath("//div[contains(@class,'dashboard')]");

    private By welcomeText =
            By.xpath("//h1[contains(translate(., 'WELCOME', 'welcome'), 'welcome')]");

    public void waitForDashboardPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardRoot));
    }

    public void waitForWelcomeMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeText));
    }

    public boolean isDashboardLoaded() {
        waitForDashboardPage();
        waitForWelcomeMessage();
        return true;
    }

    public String getWelcomeText() {
        waitForWelcomeMessage();
        return driver.findElement(welcomeText).getText();
    }
}
