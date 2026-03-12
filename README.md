# Exam Management System

## Layihə haqqında

Exam Management System — Spring Boot əsasında hazırlanmış microservices əsaslı backend tətbiqidir. Sistem istifadəçilərin imtahanlara daxil olması, sualların seçilməsi, cavabların göndərilməsi və nəticələrin hesablanması üçün nəzərdə tutulmuşdur.

Layihə real backend arxitektura prinsiplərinə uyğun şəkildə qurulmuşdur və JWT authentication, service-to-service communication və DTO pattern kimi mexanizmləri əhatə edir.

Bu layihənin məqsədi microservices arxitekturası ilə qurulmuş imtahan idarəetmə sisteminin backend hissəsini nümayiş etdirməkdir.

---

## İstifadə olunan texnologiyalar

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* MySQL
* OpenFeign
* JWT Authentication
* Lombok
* Maven

---

## Funksionallıqlar

* İstifadəçi qeydiyyatı və login
* JWT əsaslı authentication
* İmtahanın başladılması (Exam attempt creation)
* Mövzu və çətinlik səviyyəsinə görə sual seçilməsi
* Random sual seçimi
* İstifadəçi cavablarının bulk şəkildə göndərilməsi
* Cavabların avtomatik yoxlanılması
* İmtahan nəticəsinin hesablanması
* Microservices arasında servis kommunikasiyası
* DTO və API strukturu
* Global exception handling

---

## Arxitektura

Layihədə microservices architecture istifadə olunmuşdur:

User Service → Exam Service → Question Service

Servislər bir-biri ilə OpenFeign vasitəsilə əlaqə saxlayır.

Entity və API modelləri bir-birindən DTO pattern vasitəsilə ayrılmışdır.

Spring Security autentifikasiya və avtorizasiya üçün istifadə olunur.

---

## Biznes məntiqi

Sistem aşağıdakı ssenarini dəstəkləyir:

* İstifadəçi sistemə daxil olur
* İstifadəçi imtahan başlatır
* Sistem mövzu və çətinlik səviyyəsinə uyğun sualları seçir
* İstifadəçiyə suallar təqdim olunur
* İstifadəçi cavabları göndərir
* Sistem cavabları yoxlayır
* Nəticə hesablanır və istifadəçiyə təqdim olunur

---

## Project strukturu

```
services
 ├── user-service
 ├── exam-service
 └── question-service
```

---

## Layihəni işə salmaq

### Repository-ni klonla

```
git clone https://github.com/zeynalovruslan/exam_management_system
```

### Build et

```
mvn clean install
```

### Run et

```
mvn spring-boot:run
```

və ya servisləri ayrıca işə salmaq mümkündür.

---

## Database konfiqurasiyası

Database ayarları aşağıdakı fayllarda yerləşir:

```
application.properties
```

Hər servis öz database konfiqurasiyasına malikdir.

---

## Security

Spring Security istifadə olunaraq:

* Authentication
* JWT əsaslı təhlükəsizlik
* Protected API endpoint-lər

implementasiya edilmişdir.

---

## Müəllif

Ruslan Zeynalov

Java Backend Developer
