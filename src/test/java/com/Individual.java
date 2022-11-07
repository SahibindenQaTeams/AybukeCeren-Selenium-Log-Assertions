package com;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Individual {

    protected  static WebDriverWait wait;
    protected static ChromeDriver driver;
    private List<String> alanlarınId = Arrays.asList("name", "surname", "email","password");//doldurulacak alanların idlerini listte topladım.
    private List<String> alanInputları = Arrays.asList("Ceren", "Duran", "cxy" + Double.toString(Math.random()*100) + "@gmail.com", "password1234");
    int alanSayısı = alanlarınId.size(); //doldurulacak alanların sayısı

    @Test
    public void test(){

        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver");
        //System.setProperty("webdriver.chrome.driver","/Users/aybukeceren.duran/Desktop/Software Test Automation/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get("https://www.sahibinden.com/kayit"); //direct to register page of sahibinden.com
        //handle cookie
        wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onetrust-accept-btn-handler")));
        WebElement cookieAccept = driver.findElement(By.id("onetrust-accept-btn-handler"));
        cookieAccept.click();
        /*Kod tekrarı olmaması ve kodun okunulurluğunu arttırmak için
        doldurulacak alanların wait.until(ExpectedConditions.visibilityOfElementLocated) line'ını her bir element için
        yazmak yerine, for loopta tek satır olarak yazdım. Doldurulacak alanların idsini String listine koymuştum.
        Ayrıca for loopun içinde kullanıcı inputlarını gönderiyorum, inputları da ayrı bir String listine koydum*/
        WebElement element;

        for(int i =0; i<alanSayısı; i++)
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(alanlarınId.get(i))));
            element = driver.findElement(By.id(alanlarınId.get(i)));
            element.sendKeys(alanInputları.get(i));
            System.out.println(" ' " + element.getAttribute("placeholder") + " ' placeholder'ı için " + alanInputları.get(i) + " değeri girildi");
        }

        System.out.println();//konsolda okumanın kolay olması için konsolda boş line yaratmak için bu komut eklendi.
        driver.executeScript("document.getElementById('endUserLicenceAgreement').click()");
        System.out.println("Bireysel Hesap Sözleşmesi ve Ekleri kabul edildi");
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
