 ## Sahibinden Codecase
 
 Bu proje basit bir ad serverdir.
 - Reklam yaratılabilir
 - reklam gösterilme amacıyla istek yapılabilir
 - click veya impression isteği gönderilebilir
 
 Sizden beklenen sahibinden-backend altında AdService interfacesinin implemantastonunu yapmanızdir.
 
 Projeyi Main classindan çalistirabilirsiniz.
 Projeyi çalıştırdığınız zaman localhost:9393 ve localhost:9494 üzerinde 2 tane 
 sahibinden-backend uygulaması ayağa kalkacaktır.Testler bu adreslere istek yapmaktadır.
 Dolayısı ile testleri çalıştırdığınız zaman 2 instanceninde çalışıyor olması gerekmektedir.
 Proje içerisinde localhost:12345 üzerinden erisebileceginiz embededMongo databasesi ayağa
 kalkmaktadir. Embeded mongodan farklı bir database kullanamazsınız.Mongo database configurasyonlari
 yapılmıştır.Modellerinizi oluşturup kullanabilirsiniz. 
 
 
 sahibinden-common projesi altında request ve response objeleri tanımlanmıştır.
 
 - AdCreateRequest : Reklam yaratmak için kullanılan request objesidir
 - AdResponse :Reklam yaratildiktan sonra response olarak dönülen objedir
 - AdStatistics : Reklamla ilgili click impression gibi datalarin bulunduğu response objesidir
 - DeliveryResult : Reklam gösterme amacıyla istek yapılan getWinner methodunun response objesidir 
 AdResponse ve Deliveryid içerir
 - MatchCriteria : Kullanıcının görebileceği filtreleri içeren objedir category(VASITA , EMLAK)
 lokasyon(1..81) ,clientType(android,ios..), visitorId bilgilerini içermektedir. 
 
 
 AdController içinde değişiklik yapmayınız.çalıştırılacak olan testler tanımlanmış urllere istek 
 yapacaktır.
 internal paketinin altında da değişiklik yapmanızı gerektirecek bir durum yoktur.
 
 
 Implemente etmeniz gereken 5 adet method vardır :
 
 
 - ```AdResponse createAd(AdCreateRequest adCreateRequest);```
 
 Reklam yaratmak için kullanılır.AdCreateRequest objesini
 parametre olarak alır.Sınırlar.
 
 - Title min 10 max 30 karakter 
 - Description min 30 max 100 karakter
 - bidPrice min 50 max 300 
 - totalBudget min 10 *bidPrice olabilir
 - linkin erişilebilir bir link olması gerekmektedir
 - Title ve description badWords.txt dosyasındaki kelimeleri içeremez
 - FrequencyCapping min 6 max 24
 - location değerleri [1,81] arasında olabilir
 - ClientType null olamaz 
 - CategoryList en az 1 adet değer icermelidir 
 
 Yukarıdaki koşullar sağlanıyorsa reklam yaratılır ve AdResponse objesi cevap olarak dönülür.
 Eğer koşullar saglanmamis ise reklam kaydedilmez ve null response dönülür. 
 
 - ```DeliveryResult getWinner(MatchCriteria matchCriteria);```
 
 Match Criteriaya uyan reklamlar arasından bir reklam 
 ve bu evente ait deliveryId DeliveryResult objesi olarak 
 dönülür.
 
 - ```void processImpression(String deliveryId)```
 
 deliveryId ile eşleşen reklama impression 
 yapılmıştır.Herhangi bir cevap dönmeye gerek yoktur
 
 
 - ```void processClick(String deliveryId)```
 
 deliveryId ile eşleşen reklama click 
 yapılmıştır.Herhangi bir cevap dönmeye gerek yoktur.
 Her click aynı zamanda bir impressiondur.
 
 ```void deleteAll()```
 
 Her testten önce ilk olarak bu method çağirilmaktadir.
 Beklenen davranışı daha önceden oluşturulmuş tüm reklamların
 ,istatistiklerin ve deliverylerin silinmesidir.
 
 
 ## Testlerin çalıştırılması 
 
 ```
    mvn exec:java -pl sahibinden-tests -Dexec.mainClass="com.sahibinden.Main" 
```
 
 # Gerekli Bilgiler
 
 
 * Delivery : Bir reklam gösterme isteği geldiği zaman reklamın başına ne geldiğini tutan eventtir.
 
 * BidPrice : Reklamın tıklama başı ücretidir.
 
 Total budgetin aşilmamasina dikkat edilmelidir.
 Uygulama birden fazla instanceda çalıştığından dolayı testlerde bütçe aşımına 2 bidPricelik tolerans tanınmıştır.
 
 * FrequencyCapping : Reklamı bir kullanicin maksimum kaç kere görebileceğini belirler.
 
 Herhangi bir kullanıcının reklamı frequency cappingden fazla görmesi beklenmemektedir.
 MatchCriteria içerisinde visitorId bilgisi bulunmaktadır
 Uygulama birden fazla instanceda çalıştığından dolayı testlerde frequencyCappinge 1 gosterimlik tolerans tanınmıştır.
 
 * Impression : Reklamın kullanıcı tarafından görüntülenmesi
 
 * Click : Reklama kullanıcı tarafından tıklanmasi
 
 