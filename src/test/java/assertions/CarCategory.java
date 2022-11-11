package assertions;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CarCategory {

    private static final Logger logger = LogManager.getLogger(CarCategory.class);
    protected static ChromeDriver driver;
    protected  static WebDriverWait wait;

    @BeforeEach
    public void beforeTest() {
        //System.setProperty("webdriver.chrome.driver","/Users/aybukeceren.duran/Desktop/Software Test Automation/chromedriver");
        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get("https://www.sahibinden.com/testbox.html"); //direct to  https://www.sahibinden.com/testbox.html
        //tb 182 bulundu. //*[@id="testBoxContainer"]/div[3]/div[45]/div[1]
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

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Otomobil']")));
        element = driver.findElement(By.cssSelector("[title='Otomobil']")); //otomobil elementi bulundu.
        element.click();//otomobil kategorisine tıklandı.

    }


    @AfterEach
    public void afterTest(){

        driver.close();
        driver.quit();
    }

    @Test
    public void testCase1() throws IOException {

        //Otomobil sayfası açıldıktan sonra bu sayfada "Bu Kategorideki Tüm İlanlar" yazısına tıklanır.Önce elemntin görünülürlüğü beklendi.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        //"Bu Kategorideki Tüm İlanlar" elementi bulundu.
        WebElement element = driver.findElement(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        element.click();//"Bu Kategorideki Tüm İlanlar" elementine tıklandı.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchResultsTable")));//ilanlar table'da tutuluyor. Table'ın görünülürlüğü beklendi.
        WebElement item = driver.findElement(By.id("searchResultsTable"));//table elementi bulundu.
        List<WebElement> elements = item.findElements(By.className("searchResultsItem"));//Tabledaki ilanları listede tuttum.
        //bazen sayfada 1 tane reklam olabiliyor ve de searchResultsItem classına ait olabiliyor.
        int size = elements.size();
        boolean value = (size == 20) || (size == 21);//reklamdan dolayı ilan sayısı 1 fazla olabiliyor listede.
        System.out.println("Tüm otomobil ilanların 1. sayfadaki sayısı: " + size);

        try{
          Assertions.assertEquals(true,value); // ilan sayısının boş gelip gelmediği kontrol edildi.
          System.out.println("Test case1 passed :)");
        }catch (AssertionError e){

            File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File DestFile=new File("/Users/aybukeceren.duran/Desktop/test.png");

            FileUtils.copyFile(imageFile, DestFile);
            logger.error("test case1 failed");
        }

    }


    @Test
    public void testCase2() throws IOException {

        //Otomobil sayfası açıldıktan sonra, otomobil vtrinindeki ilanların gelip gelmediğine dair kontrol
        //vitrindeki elementleri tutan kısmın elementin görünülürlüğü beklendi.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("showCaseList")));
        WebElement item = driver.findElement(By.className("showCaseList"));//vitrindeki ilanları tutan element bulundu.
        List<WebElement> items = item.findElements(By.tagName("li"));//vitrindeki ianlar listte tutuldu.
        int size = items.size();//vitrindeki ilan sayısı hesaplandı.
        System.out.println("Sayfadaki otomobil ilan sayısı: " + size);

        try{
            Assertions.assertNotEquals(0,size); // ilan sayısının boş gelip gelmediği kontrol edildi.
            System.out.println("Test case2 passed :)");
        }catch (AssertionError e){

            File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File DestFile=new File("/Users/aybukeceren.duran/Desktop/test.png");

            FileUtils.copyFile(imageFile, DestFile);
            logger.error("test case2 failed");
        }

    }

    @Test
    public void testCase3() throws IOException {
        //Otomobil sayfası açıldıktan sonra bu sayfada "Bu Kategorideki Tüm İlanlar" yazısına tıklanır.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        WebElement element = driver.findElement(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        element.click();
        //50 butunonun kontrolü
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div[4]/form/div/div[3]/div[3]/div[2]/ul/li[2]/a")));
        element = driver.findElement(By.xpath("/html/body/div[5]/div[4]/form/div/div[3]/div[3]/div[2]/ul/li[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div[4]/form/div/div[3]/div[3]/div[2]/ul/li[2]/a")));
        element.click();//50 butonuna tıklandı.
        wait = new WebDriverWait(driver,50);
        driver.navigate().refresh();//yeni dataların yüklenmesi için sayfayı yeniledim.
        WebElement item = driver.findElement(By.id("searchResultsTable"));
        List<WebElement> elements = item.findElements(By.className("searchResultsItem"));//ilanların tutulduğu tabledaki ilanları liste çektim.
        int size = elements.size();
        //ilanların tuttulduğu yerin ortasında bazen reklam olabiliyor, reklam da HTML kodunda ilanlarla aynı tip olduğu için 51 sayısını kabul ettim kodda.
        boolean value = (size == 50) || (size == 51);//ilan sayısının 50 ya da 51 olmadığı kontrol edildi.
        System.out.println("Tüm otomobil ilanların 1. sayfadaki sayısı: " + size);

        try{
            Assertions.assertEquals(true,value); //
            System.out.println("Test case3 passed :)");
        }catch (AssertionError e){

            File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File DestFile=new File("/Users/aybukeceren.duran/Desktop/test.png");

            FileUtils.copyFile(imageFile, DestFile);
            logger.error("test case3 failed");
        }
    }

    @Test
    public void testCase4() throws IOException {

        //Otomobil sayfası açıldıktan sonra bu sayfada "Bu Kategorideki Tüm İlanlar" yazısına tıklanır.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        WebElement element = driver.findElement(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        element.click();
        //illerin boş gelip gelmediği kontrol edilir.
        //Önce il yazısı beklenir, il yazısı elementi bulunur.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div[4]/form/div/div[2]/div[3]/dl/dd/ul/li[1]/a")));
        element = driver.findElement(By.xpath("/html/body/div[5]/div[4]/form/div/div[2]/div[3]/dl/dd/ul/li[1]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div[4]/form/div/div[2]/div[3]/dl/dd/ul/li[1]/a")));
        element.click();//İl yazısına tıklanır.
        //illerin tutulduğu kısmın elementi beklenir.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div[4]/form/div/div[2]/div[3]/dl/dd/ul/li[1]/div/div[3]/div/div[1]/ul/div/ul")));
        WebElement item = driver.findElement(By.xpath("/html/body/div[5]/div[4]/form/div/div[2]/div[3]/dl/dd/ul/li[1]/div/div[3]/div/div[1]/ul/div/ul"));
        List<WebElement> items = item.findElements(By.tagName("li"));//iller listede tutulur.
        int size = items.size();
        System.out.println("İl sayısı: " + size);
        System.out.println("83 çünkü İstanbul'a ek olarak İstanbul(Anadolu) ve İstanbul(Avrupa) var");

        try{
            Assertions.assertNotEquals(0,size);
            System.out.println("Test case4 passed :)");
        }catch (AssertionError e){

            File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File DestFile=new File("/Users/aybukeceren.duran/Desktop/test.png");

            FileUtils.copyFile(imageFile, DestFile);
            logger.error("test case4 failed");
        }
    }

    @Test
    public void testCase5() throws IOException {

        //Otomobil sayfası açıldıktan sonra bu sayfada "Bu Kategorideki Tüm İlanlar" yazısına tıklanır.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        WebElement element = driver.findElement(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        element.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchResultsTable")));//sayfadaki tüm ilanların tutulduğu elementin görünülürlüğü
        WebElement item = driver.findElement(By.id("searchResultsTable"));//sayfadaki tüm ilanların tutulduğu element bulundu.
        List<WebElement> elements = item.findElements(By.className("searchResultsItem"));
        WebElement ilkilan = elements.get(0);//sayfadaki ilk ilan bulundu.
        List<WebElement> ilanOzellikleri = ilkilan.findElements(By.className("searchResultsTagAttributeValue"));
        String marka = ilanOzellikleri.get(0).getText();
        String seri = ilanOzellikleri.get(1).getText();
        String model = ilanOzellikleri.get(2).getText();
        WebElement ilkilan_link = ilkilan.findElement(By.className("classifiedTitle"));
        String baslik = ilkilan_link.getText();
        List<WebElement> ilanOzellikleri2 = ilkilan.findElements(By.className("searchResultsAttributeValue"));
        String yil = ilanOzellikleri2.get(0).getText();
        String km  = ilanOzellikleri2.get(1).getText();
        String renk = ilanOzellikleri2.get(2).getText();

        try{
            Assertions.assertAll
                    (
                            () -> Assertions.assertNotNull(marka),
                            () -> Assertions.assertNotNull(seri),
                            () -> Assertions.assertNotNull(model),
                            () -> Assertions.assertNotNull(baslik),
                            () -> Assertions.assertNotNull(yil),
                            () -> Assertions.assertNotNull(km),
                            () -> Assertions.assertNotNull(renk)
                    );
            System.out.println();
            System.out.println("**************************");
            System.out.println("Tıklanan aracın markası, serisi, modeli, ilan başlığı, yılı, km'si ve rengi dolu geliyor ");
            System.out.println("Tıklanan ilk ilandaki aracın markası: " + marka);
            System.out.println("Tıklanan ilk ilandaki aracın serisi: " + seri);
            System.out.println("Tıklanan ilk ilandaki aracın modeli: " + model);
            System.out.println("Tıklanan ilk ilandaki aracın başlığı: " + baslik);
            System.out.println("Tıklanan ilk ilandaki aracın yılı: " + yil);
            System.out.println("Tıklanan ilk ilandaki aracın km'si: " + km);
            System.out.println("Tıklanan ilk ilandaki aracın rengi: " + renk);
            System.out.println("**************************");
            System.out.println();
            //WebElement ilkilan_link = ilkilan.findElement(By.className("classifiedTitle"));
            //ilkilan_link.click();
            System.out.println("Test case5 passed :)");
        }catch (AssertionError e){
            File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File DestFile=new File("/Users/aybukeceren.duran/Desktop/test.png");

            FileUtils.copyFile(imageFile, DestFile);
            logger.error("test case5 failed");
        }


    }

    @Test
    public void testCase6() throws IOException {

        //Otomobil sayfası açıldıktan sonra bu sayfada "Bu Kategorideki Tüm İlanlar" yazısına tıklanır.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        WebElement element = driver.findElement(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        element.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchResultsTable")));//ilanların tutulduğu table'ın görünülürlüğü
        WebElement item = driver.findElement(By.id("searchResultsTable"));
        List<WebElement> elements = item.findElements(By.className("searchResultsItem"));//table'dan ilanlar çekilerek listte tutuldu.
        WebElement ilkilan = elements.get(0);//sayfadaki ilk ilan tutuldu.
        WebElement ilkilan_link = ilkilan.findElement(By.className("classifiedTitle"));
        ilkilan_link.click();//sayfadaki ilk ilana tıklanır.

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("classifiedId")));
        WebElement ilanNoElement = driver.findElement(By.id("classifiedId"));
        String strUrl = driver.getCurrentUrl();
        String ilanNo = ilanNoElement.getText();

        boolean check = strUrl.contains(ilanNo);//url'de ilanın tutulup tutulmadığı kontrol edildi.

        try{
            Assertions.assertEquals(true,check);
            System.out.println("Test case6 passed :)");
        }catch (AssertionError e){

            File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File DestFile=new File("/Users/aybukeceren.duran/Desktop/test.png");

            FileUtils.copyFile(imageFile, DestFile);
            logger.error("test case6 failed");
        }

    }
    
    @AfterEach
    public void afterTest(){

        driver.close();
        driver.quit();
    }
}
