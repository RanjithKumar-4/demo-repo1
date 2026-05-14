package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {

    public WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void setup(@Optional("chrome")  String browser) {

        if(browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        }
        else{
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.yatra.com/");
    }

    @AfterClass
    public void tearDown() {

        driver.quit();
    }
}
// I changed in main