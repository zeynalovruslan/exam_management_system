Exam Management System
Layihə haqqında

Exam Management System — Spring Boot əsasında hazırlanmış microservices arxitekturalı backend tətbiqidir. Sistem istifadəçilərin imtahanlara daxil olması, sualların seçilməsi, cavabların göndərilməsi və nəticələrin hesablanması üçün nəzərdə tutulmuşdur.

Layihə real backend sistemlərdə istifadə olunan microservices arxitekturası, JWT authentication, service-to-service communication və DTO pattern kimi prinsiplərə uyğun şəkildə qurulmuşdur.

Bu layihənin məqsədi imtahan idarəetmə sisteminin backend hissəsinin microservices yanaşması ilə necə qurulduğunu nümayiş etdirməkdir.

İstifadə olunan texnologiyalar

Java 17

Spring Boot

Spring Security

Spring Data JPA

MySQL

OpenFeign (service-to-service communication)

JWT Authentication

Lombok

Maven

Funksionallıqlar

İstifadəçi qeydiyyatı və login

JWT əsaslı authentication

İmtahanın başladılması (Exam attempt creation)

Mövzu və çətinlik səviyyəsinə görə sual seçilməsi

Random sual seçimi

İstifadəçi cavablarının bulk şəkildə göndərilməsi

Cavabların avtomatik yoxlanılması

İmtahan nəticəsinin hesablanması

Microservices arasında servis kommunikasiya

DTO əsaslı API strukturu

Global exception handling

Arxitektura

Layihə microservices architecture istifadə edir və sistem bir neçə müstəqil servisdən ibarətdir.

Servislər:

User Service

Exam Service

Question Service

Servislər bir-biri ilə OpenFeign vasitəsilə əlaqə saxlayır.

Authentication JWT token vasitəsilə həyata keçirilir.

Servislərin rolu
User Service

İstifadəçi idarəetməsi və autentifikasiya üçün istifadə olunur.

Funksiyalar:

istifadəçi qeydiyyatı

login

JWT token yaradılması

istifadəçi məlumatlarının idarə olunması

Exam Service

İmtahan prosesinin idarə olunmasına cavabdehdir.

Funksiyalar:

imtahan attempt yaradılması

question service-dən sualların alınması

istifadəçi cavablarının qəbul edilməsi

nəticənin hesablanması

Question Service

Sualların saxlanması və cavabların yoxlanılması üçün istifadə olunur.

Funksiyalar:

sualların saxlanması

random sual seçimi

cavabların yoxlanılması

nəticə üçün məlumatın qaytarılması

Biznes məntiqi

Sistem aşağıdakı ssenariyə əsasən işləyir:

İstifadəçi sistemə daxil olur

İstifadəçi imtahan başlatır

Sistem uyğun mövzu və çətinlik üzrə sualları seçir

İstifadəçiyə suallar təqdim olunur

İstifadəçi cavabları göndərir

Sistem cavabları yoxlayır

İmtahan nəticəsi hesablanır və istifadəçiyə təqdim olunur

Project strukturu
services
 ├── user-service
 ├── exam-service
 └── question-service

Hər servis ayrıca Spring Boot tətbiqi kimi işləyir.

Layihəni işə salmaq
Repository-ni klonla
git clone https://github.com/zeynalovruslan/exam_management_system
Build et
mvn clean install
Servisləri run et

Hər servis ayrıca run edilir.

cd services/user-service
mvn spring-boot:run
cd services/exam-service
mvn spring-boot:run
cd services/question-service
mvn spring-boot:run
Security

Spring Security və JWT istifadə olunaraq aşağıdakı mexanizmlər implementasiya edilmişdir:

Authentication

Token əsaslı təhlükəsizlik

Protected API endpoint-lər

Protected endpoint-lərə request göndərərkən aşağıdakı header istifadə olunur:

Authorization: Bearer TOKEN
Müəllif

Ruslan Zeynalov

Java Backend Developer 🚀
