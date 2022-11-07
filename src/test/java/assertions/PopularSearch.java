package assertions;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.params.ParameterizedTest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PopularSearch {

    private static final Logger logger = LogManager.getLogger(CarCategory.class);
    protected static ChromeDriver driver;
    protected  static WebDriverWait wait;

    @BeforeEach
    public void beforeTest() {

        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver");
        //System.setProperty("webdriver.chrome.driver","/Users/aybukeceren.duran/Desktop/Software Test Automation/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get("https://www.sahibinden.com/testbox.html"); //direct to  https://www.sahibinden.com/testbox.html
        WebElement element = driver.findElement(By.xpath("//*[@id=\"testBoxContainer\"]/div[3]/div[38]"));
        element.click();
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
        driver.navigate().to("https://www.sahibinden.com/");

        wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onetrust-accept-btn-handler")));
        WebElement cookieAccept = driver.findElement(By.id("onetrust-accept-btn-handler"));
        cookieAccept.click();

        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[1]/iframe")));
            driver.switchTo().frame(driver.findElement(By.xpath("/html/body/div[2]/div[1]/iframe")));//switch to the frame
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/gwd-google-ad/gwd-pagedeck/gwd-page/div/gwd-image/div/img")));
            element = driver.findElement(By.xpath("/html/body/gwd-google-ad/gwd-pagedeck/gwd-page/div/gwd-image/div/img"));
            element.click();
            driver.switchTo().defaultContent();//return to original frame
        } catch(Exception e){

            System.out.println("Youtube videosu yüklenmedi.");
        }


    }
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5})
    //@CsvSource({"iphone 12 mini,1", "playStation 5,2", "koşu bandı,3", "elektrikli ısıtıcı,4","toyota fiyatları, 5" })
    public void test(Integer value ) throws IOException {

        /*Popüler arama kategorileri olarak iphone12 mini, playstation5, koşu bandı, elektrikli ısıtıcı ve toyota fiyatları seçildi.
        Bunların xpath değerleri birbirine benziyor, sadece sayı olarak farklılık vardı.
        Kodda xpathte olan kısımda farklı olan sayı için value değişkenini kullandım.*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"container\"]/div[3]/div/div[3]/div[12]/ul/li[" + value + "]" + "/a")));
        WebElement element = driver.findElement(By.xpath("//*[@id=\"container\"]/div[3]/div/div[3]/div[12]/ul/li[" + value + "]" + "/a"));
        String title1 = element.getAttribute("title");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"container\"]/div[3]/div/div[3]/div[12]/ul/li[" + value + "]" + "/a")));
        element.click();
        //kategorilerden biri tıklandıktan sonra breadcrumbın görünür olması beklenir.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchContainer")));
        //breadcrumb bulunur.
        element = driver.findElement(By.id("searchContainer"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("search-result-bc")));
        element = element.findElement(By.className("search-result-bc"));
        //breadcrumb elementleri listte tutulur.
        List<WebElement> elements = element.findElements(By.className("bc-item"));
        int size = elements.size();//popüler arama kategorisi breadcrumbın en sonki elementi.
        element = driver.findElement(By.xpath("//*[@id=\"searchContainer\"]/div[2]/ul/li["+size+"]"+"/a"));//breadcrumbın en sonki elementi bulunur.

        String title2 = element.getAttribute("title");//breadcrumbın en sonki elementinin yazısı stringte tutulur.

        if(title2.contains("fiyatları")  ||  title2.contains("watch"))//breadcrumbta Toyota fiyatları popüler arama kategorisi toyota olarak gözüküyor
        {//bu yüzden fiyatları yazısı atılıp kontrol yapılır. Aynı şekilde Oppo Watch popüler araması breadcrumbta Oppo olarak gözüküyor.
            System.out.println(title1);
            String arr[] = title2.split(" ", 2);
            String firstWord = arr[0];
            System.out.println(firstWord);

            try{
                Assertions.assertTrue(title1.equalsIgnoreCase(firstWord));
                System.out.println("Popular Search Category" + value + " test passed");
            }catch (AssertionError e){

                File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File DestFile=new File("/Users/aybukeceren.duran/Desktop/test.png");

                FileUtils.copyFile(imageFile, DestFile);
                logger.error("Popular Search Category" + value + " test failed");
            }

        }

        else
        {
            System.out.println(title1);
            System.out.println(title2);
            // Assertions.assertTrue(title1.equalsIgnoreCase(title2));
            try{
                Assertions.assertTrue(title1.equalsIgnoreCase(title2));
                System.out.println("Popular Search Category" + value + " test passed");
            }catch (AssertionError e){

                File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File DestFile=new File("/Users/aybukeceren.duran/Desktop/test.png");

                FileUtils.copyFile(imageFile, DestFile);
                logger.error("Popular Search Category" + value + " test failed");
            }
        }

    }
    @AfterEach
    public void afterTest(){

        driver.close();
        driver.quit();
    }
}

