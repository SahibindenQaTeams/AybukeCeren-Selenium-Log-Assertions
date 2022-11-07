# AybukeCeren-Selenium-Log-Assertions
## Ödevle ilgili notlar:
<sup>Bu ödevin kodları assertions packageındadır. Case1’in kodları CarCategory.java classındadır, Case2’nin kodları PopularSearch.java classındadır.  </sup>
<sup>Assertions ile ilgili kodlar try-catch statementlarındadır. Assertion fail edince catch statementında loglamayla error basılıyor, fail olan testin screesnshotı çekiliyor. </sup>

## Testler çalışmadan önce public void beforeTest() methodu:
<sup>Testbox sayfasına yönlendirme yaptım, tb 182de testler yapıldı. Ardından sahibinden.com anasayfasına yönlendirme yapılarak test yapıldı. Çerez kabul ediliyor. Bu methodun içinde try-catch statementı var; try kısmında Onur Buldu’nun youtube reklam videosunun olduğu frame bulunmaya çalışılıyor ve video kapatılıyor. Her tbta o videonun bulunmaması ihtimaline karşılık o videoyla ilgili kodu try-catch statementına koydum. </sup>

## CarCategory.java

<sup> **public void testCase1:** Anasayfada otomobil kategosine tıklandıktan sonra “Bu Kategorideki Tüm İlanlar” yazısına tıklanır. Açılan sayfada 20 ilan gösteriliyor. Bu sayfadaki ilan sayısının 20 olup olmadığı casei kontrol edildi. Kontrolü Assertions. assertEquals ile yaptım. </sup>

<sup> **public void testCase2:** Anasayfada otomobil kategorisine tıklandıktan sonra  “Bu Kategorideki Tüm İlanlar” yazısına tıklanılmaz. Bu sayfada kalınır.  (https://www.sahibinden.com/kategori/otomobil) Buradaki otomobil kategorisinde 30 tane ilan var, sayının 0 gelip gelmediği (vitrinin boş gelmediği) kontrol edildi. Kontrolü Assertions.assertNotEquals(0, size); ile yaptım. </sup>

<sup> **public void testCase3:** Anasayfada otomobil kategosine tıklandıktan sonra “Bu Kategorideki Tüm İlanlar” yazısına tıklanır. Açılan sayfada default olarak 20 ilan gösteriliyor. Sayfanın sonunda “Her sayfada 20 50 sonuç göster” şeklinde bir yazı var. 50 butonuna tıklandığında sayfada 50 ilanın gösterildiği kontrol edildi. Kontrolü Assertions ile yaptım.</sup>

<sup> **public void testCase4:** Anasayfada otomobil kategosine tıklandıktan sonra “Bu Kategorideki Tüm İlanlar” yazısına tıklanır. Açılan sayfada adres kategorisinde il seçilebiliyor. Tüm şehirlerin gelip gelmediği kontol edildi, bunun için il sayısı hesaplandı. il sayısı İstanbul (Avrupa) ve İstanbul (Anadolu)’dan dolayı 83 olarak hesaplandı. İllerin sayısının 0 olup olmadığının kontrolünü Assertions.assertNotEquals(0, size); ile yaptım. </sup>

<sup> **public void testCase5:** Anasayfada otomobil kategosine tıklandıktan sonra “Bu Kategorideki Tüm İlanlar” yazısına tıklanır. Açılan sayfadaki ilk otomobil ilanında marka, seri, model, başlık, yıl, km, renk bilgisinin dolu geldiği kontrol edildi. Bunun için Assertions.assertAll metodunda marka, seri, model, başlık, yıl, km, renk bilgisi için Assertions.assertNotNull metodu çalıştırıldı. </sup>

<sup> **public void testCase6:** Anasayfada otomobil kategosine tıklandıktan sonra “Bu Kategorideki Tüm İlanlar” yazısına tıklanır. Açılan sayfadaki ilk ilana tıklanır, ilan detay sayfasındaki ilan no ile ilan detay sayfasındaki URL’deki ilan no’nun aynı olduğu kontrol edildi.
Kontrolü Assertions.assertEquals ile yaptım. </sup>

## PopularSearch.java

<sup>Popüler arama kategorilerinden 5 tanesine tıklandığında, breacrumbta popüler arama adının doğru gelip gelmediği kontrol edildi.Popüler arama kategorileri olarak iphone12 mini, playstation5, koşu bandı, elektrikli ısıtıcı ve toyota fiyatları seçildi. Bunların xpath değerleri birbirine benziyor, sadece sayı olarak farklılık vardı. Kodda xpathte olan kısımda farklı olan sayı için value değişkenini kullandım.Toyota fiyatları kontrolü için küçük bir değişiklik yaptım. Toyota fiyatları popüler arama kategorisine tıklandığında, breadcrumbta toyota olarak gözüküyor. Bunun için kodda “Toyota Fiyatları” nda “fiyatları” kısmını kestim. Daha sonra tüm kontrolleri Assertions ile yaptım.
 </sup>
