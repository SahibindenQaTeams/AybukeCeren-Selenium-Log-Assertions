package com;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Corporate {

    protected  static WebDriverWait wait;
    protected static ChromeDriver driver;
    private List<String> alanlarınId = Arrays.asList("name", "surname", "email","password", "phone");
    private List<String> alanInputları = Arrays.asList("Ceren", "Duran", "cxy" + Double.toString(Math.random()*100) + "@mailinator.com",
            "password1234", "2344101735");
    private List<String> selectId1 = Arrays.asList("category", "city", "town", "quarter");
    private List<String> selectId2 = Arrays.asList("taxOfficeCity", "taxOffice");
    private List<String> selectInput1 = Arrays.asList("Emlak", "İstanbul", "Kadıköy", "19 Mayıs Mh.");
    private List<String> selectInput2 = Arrays.asList("İstanbul","Kadıköy Vergi Dairesi");
    int alanSayısı = alanlarınId.size(); //doldurulacak alanların sayısı
    int selectSayısı1 = selectId1.size();
    int selectSayısı2 = selectId2.size();

    @Test
    public void test(){

        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver");
        //System.setProperty("webdriver.chrome.driver","/Users/aybukeceren.duran/Desktop/Software Test Automation/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10,TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,30);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get("https://www.sahibinden.com/kayit/kurumsal/"); //direct to register page of sahibinden.com
        /*Kod tekrarı olmaması ve kodun okunulurluğunu arttırmak için
        doldurulacak alanların wait.until(ExpectedConditions.visibilityOfElementLocated) line'ını her bir element için
        yazmak yerine, for loopta tek satır olarak yazdım. Doldurulacak alanların idsini String listine koymuştum.
        Ayrıca for loopun içinde kullanıcı inputlarını gönderiyorum, inputları da ayrı bir String listine koydum*/
        WebElement element;
        Select select;

        for(int i =0; i<alanSayısı; i++)
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(alanlarınId.get(i))));
            element = driver.findElement(By.id(alanlarınId.get(i)));
            element.sendKeys(alanInputları.get(i));
            System.out.println(" ' " + element.getAttribute("placeholder") + " ' placeholder'ı için " + alanInputları.get(i) + " değeri girildi");
        }

         System.out.println();//konsolda okumanın kolay olması için konsolda boş line yaratmak için bu komut eklendi.
        /*Aynı şekilde seçim yapılacak yerlerde de(il, ilçe,mahalle) kod tekrarı olmaması adına
          bu seçimleri for loopta yaptırıyorum.
         */
        for(int i =0; i<selectSayısı1; i++)
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(selectId1.get(i))));
            select = new Select(driver.findElement(By.id(selectId1.get(i))));
            wait.until(ExpectedConditions.elementToBeClickable(By.id(selectId1.get(i))));
            select.selectByVisibleText(selectInput1.get(i));
            System.out.println(selectInput1.get(i) + " seçildi");
        }

        System.out.println();//konsolda okumanın kolay olması için konsolda boş line yaratmak için bu komut eklendi.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('limitedCompany').click()");
        System.out.println("İşletme türü seçimi yapıldı");

        /*Vergi ile ilgili seçimleri for loopta yaptırdım.*/
        for(int i =0; i<selectSayısı2; i++)
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(selectId2.get(i))));
            select = new Select(driver.findElement(By.id(selectId2.get(i))));
            wait.until(ExpectedConditions.elementToBeClickable(By.id(selectId2.get(i))));
            select.selectByVisibleText(selectInput2.get(i));
            System.out.println("Vergiyle ilgili seçimlerde " + selectInput2.get(i) + " seçildi.");
        }

        System.out.println();//konsolda okumanın kolay olması için konsolda boş line yaratmak için bu komut eklendi.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("taxNumber")));
        element = driver.findElement(By.id("taxNumber"));
        element.sendKeys("1600776991");

        driver.executeScript("document.getElementById('endUserLicenceAgreement').click()");
        System.out.println("Kurumsal Hesap Sözleşmesi ve Ekleri kabul edildi");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signUpButton")));;
        element  = driver.findElement(By.id("signUpButton"));
        element.click();
        System.out.println("Hesap Aç butonuna tıklandı");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("viewEmail")));
        element = driver.findElement(By.id("viewEmail"));
        System.out.println();//konsolda okumanın kolay olması için konsolda boş line yaratmak için bu komut eklendi.
        System.out.println("Testte kullanıcının girdiği mail adresi: " + element.getText());

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmSubmit")));;
        element  = driver.findElement(By.id("confirmSubmit"));
        element.click();
        System.out.println("Açılan popuptaki devam et butonuna tıklandı");
        System.out.println("Bye :)");

        driver.quit();


    }
}