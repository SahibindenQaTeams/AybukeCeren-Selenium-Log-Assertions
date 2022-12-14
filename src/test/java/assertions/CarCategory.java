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

           System.out.println("Youtube videosu y??klenmedi.");
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Otomobil']")));
        element = driver.findElement(By.cssSelector("[title='Otomobil']")); //otomobil elementi bulundu.
        element.click();//otomobil kategorisine t??kland??.

    }


    @AfterEach
    public void afterTest(){

        driver.close();
        driver.quit();
    }

    @Test
    public void testCase1() throws IOException {

        //Otomobil sayfas?? a????ld??ktan sonra bu sayfada "Bu Kategorideki T??m ??lanlar" yaz??s??na t??klan??r.??nce elemntin g??r??n??l??rl?????? beklendi.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        //"Bu Kategorideki T??m ??lanlar" elementi bulundu.
        WebElement element = driver.findElement(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        element.click();//"Bu Kategorideki T??m ??lanlar" elementine t??kland??.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchResultsTable")));//ilanlar table'da tutuluyor. Table'??n g??r??n??l??rl?????? beklendi.
        WebElement item = driver.findElement(By.id("searchResultsTable"));//table elementi bulundu.
        List<WebElement> elements = item.findElements(By.className("searchResultsItem"));//Tabledaki ilanlar?? listede tuttum.
        //bazen sayfada 1 tane reklam olabiliyor ve de searchResultsItem class??na ait olabiliyor.
        int size = elements.size();
        boolean value = (size == 20) || (size == 21);//reklamdan dolay?? ilan say??s?? 1 fazla olabiliyor listede.
        System.out.println("T??m otomobil ilanlar??n 1. sayfadaki say??s??: " + size);

        try{
          Assertions.assertEquals(true,value); // ilan say??s??n??n bo?? gelip gelmedi??i kontrol edildi.
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

        //Otomobil sayfas?? a????ld??ktan sonra, otomobil vtrinindeki ilanlar??n gelip gelmedi??ine dair kontrol
        //vitrindeki elementleri tutan k??sm??n elementin g??r??n??l??rl?????? beklendi.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("showCaseList")));
        WebElement item = driver.findElement(By.className("showCaseList"));//vitrindeki ilanlar?? tutan element bulundu.
        List<WebElement> items = item.findElements(By.tagName("li"));//vitrindeki ianlar listte tutuldu.
        int size = items.size();//vitrindeki ilan say??s?? hesapland??.
        System.out.println("Sayfadaki otomobil ilan say??s??: " + size);

        try{
            Assertions.assertNotEquals(0,size); // ilan say??s??n??n bo?? gelip gelmedi??i kontrol edildi.
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
        //Otomobil sayfas?? a????ld??ktan sonra bu sayfada "Bu Kategorideki T??m ??lanlar" yaz??s??na t??klan??r.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        WebElement element = driver.findElement(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        element.click();
        //50 butunonun kontrol??
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div[4]/form/div/div[3]/div[3]/div[2]/ul/li[2]/a")));
        element = driver.findElement(By.xpath("/html/body/div[5]/div[4]/form/div/div[3]/div[3]/div[2]/ul/li[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div[4]/form/div/div[3]/div[3]/div[2]/ul/li[2]/a")));
        element.click();//50 butonuna t??kland??.
        wait = new WebDriverWait(driver,50);
        driver.navigate().refresh();//yeni datalar??n y??klenmesi i??in sayfay?? yeniledim.
        WebElement item = driver.findElement(By.id("searchResultsTable"));
        List<WebElement> elements = item.findElements(By.className("searchResultsItem"));//ilanlar??n tutuldu??u tabledaki ilanlar?? liste ??ektim.
        int size = elements.size();
        //ilanlar??n tuttuldu??u yerin ortas??nda bazen reklam olabiliyor, reklam da HTML kodunda ilanlarla ayn?? tip oldu??u i??in 51 say??s??n?? kabul ettim kodda.
        boolean value = (size == 50) || (size == 51);//ilan say??s??n??n 50 ya da 51 olmad?????? kontrol edildi.
        System.out.println("T??m otomobil ilanlar??n 1. sayfadaki say??s??: " + size);

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

        //Otomobil sayfas?? a????ld??ktan sonra bu sayfada "Bu Kategorideki T??m ??lanlar" yaz??s??na t??klan??r.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        WebElement element = driver.findElement(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        element.click();
        //illerin bo?? gelip gelmedi??i kontrol edilir.
        //??nce il yaz??s?? beklenir, il yaz??s?? elementi bulunur.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div[4]/form/div/div[2]/div[3]/dl/dd/ul/li[1]/a")));
        element = driver.findElement(By.xpath("/html/body/div[5]/div[4]/form/div/div[2]/div[3]/dl/dd/ul/li[1]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div[4]/form/div/div[2]/div[3]/dl/dd/ul/li[1]/a")));
        element.click();//??l yaz??s??na t??klan??r.
        //illerin tutuldu??u k??sm??n elementi beklenir.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div[4]/form/div/div[2]/div[3]/dl/dd/ul/li[1]/div/div[3]/div/div[1]/ul/div/ul")));
        WebElement item = driver.findElement(By.xpath("/html/body/div[5]/div[4]/form/div/div[2]/div[3]/dl/dd/ul/li[1]/div/div[3]/div/div[1]/ul/div/ul"));
        List<WebElement> items = item.findElements(By.tagName("li"));//iller listede tutulur.
        int size = items.size();
        System.out.println("??l say??s??: " + size);
        System.out.println("83 ????nk?? ??stanbul'a ek olarak ??stanbul(Anadolu) ve ??stanbul(Avrupa) var");

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

        //Otomobil sayfas?? a????ld??ktan sonra bu sayfada "Bu Kategorideki T??m ??lanlar" yaz??s??na t??klan??r.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        WebElement element = driver.findElement(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        element.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchResultsTable")));//sayfadaki t??m ilanlar??n tutuldu??u elementin g??r??n??l??rl??????
        WebElement item = driver.findElement(By.id("searchResultsTable"));//sayfadaki t??m ilanlar??n tutuldu??u element bulundu.
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
            System.out.println("T??klanan arac??n markas??, serisi, modeli, ilan ba??l??????, y??l??, km'si ve rengi dolu geliyor ");
            System.out.println("T??klanan ilk ilandaki arac??n markas??: " + marka);
            System.out.println("T??klanan ilk ilandaki arac??n serisi: " + seri);
            System.out.println("T??klanan ilk ilandaki arac??n modeli: " + model);
            System.out.println("T??klanan ilk ilandaki arac??n ba??l??????: " + baslik);
            System.out.println("T??klanan ilk ilandaki arac??n y??l??: " + yil);
            System.out.println("T??klanan ilk ilandaki arac??n km'si: " + km);
            System.out.println("T??klanan ilk ilandaki arac??n rengi: " + renk);
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

        //Otomobil sayfas?? a????ld??ktan sonra bu sayfada "Bu Kategorideki T??m ??lanlar" yaz??s??na t??klan??r.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        WebElement element = driver.findElement(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[2]/a")));
        element.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchResultsTable")));//ilanlar??n tutuldu??u table'??n g??r??n??l??rl??????
        WebElement item = driver.findElement(By.id("searchResultsTable"));
        List<WebElement> elements = item.findElements(By.className("searchResultsItem"));//table'dan ilanlar ??ekilerek listte tutuldu.
        WebElement ilkilan = elements.get(0);//sayfadaki ilk ilan tutuldu.
        WebElement ilkilan_link = ilkilan.findElement(By.className("classifiedTitle"));
        ilkilan_link.click();//sayfadaki ilk ilana t??klan??r.

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("classifiedId")));
        WebElement ilanNoElement = driver.findElement(By.id("classifiedId"));
        String strUrl = driver.getCurrentUrl();
        String ilanNo = ilanNoElement.getText();

        boolean check = strUrl.contains(ilanNo);//url'de ilan??n tutulup tutulmad?????? kontrol edildi.

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
