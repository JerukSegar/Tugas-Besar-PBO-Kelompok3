# EventHub - Sistem Manajemen Acara dan Tiket

EventHub adalah aplikasi berbasis web yang dirancang untuk mempermudah pengelolaan acara, proses pendaftaran peserta, hingga sistem manajemen tiket dan check-in secara digital. Aplikasi ini dibuat untuk memenuhi tugas besar kuliah.

## Fitur Utama

- **Manajemen Pengguna (Users):** Sistem autentikasi untuk pengguna dan penyelenggara acara.
- **Manajemen Acara (Events):** Penyelenggara dapat membuat, mengubah, dan mengelola detail acara.
- **Pendaftaran Acara:** Peserta dapat mendaftar pada acara yang tersedia dan mendapatkan kode tiket unik.
- **Sistem Tiket & Kode QR:** Pembuatan tiket digital otomatis yang dilengkapi dengan kode QR unik untuk setiap pendaftaran.
- **Sistem Check-In:** Validasi tiket peserta di lokasi acara secara real-time oleh penyelenggara untuk meminimalkan penggandaan tiket.
- **Pelaporan (Laporan):** Penyusunan laporan data kehadiran dan pendaftaran untuk setiap acara.

## Teknologi yang Digunakan

- **Backend:** Java (Spring Boot)
- **Basis Data:** MySQL
- **Arsitektur Data:** Terintegrasi dengan Hibernate / Spring Data JPA

## Panduan Instalasi Sistem

### 1. Persiapan Tools
Pastikan perangkat sudah terinstal:
- Java JDK 17 atau lebih baru
- MySQL Server
- IDE (IntelliJ IDEA / Eclipse / VS Code)
- Browser (Google Chrome / Mozilla Firefox)

### 2. Persiapan Database
- Buka MySQL dan buat database baru dengan nama `eventhub2`.
- Import file SQL yang tersedia pada folder project ke database tersebut.

### 3. Konfigurasi Project
Buka file `application.properties` pada folder `src/main/resources` dan sesuaikan konfigurasi berikut:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/eventhub2
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8082

### 4. Menjalankan Sistem
- Buka project menggunakan IDE
- Jalankan file utama EventHubApplication.java
- Tunggu hingga server berjalan
- Buka browser dan akses http://localhost:8082

### 4. Login ke Sistem
- Daftar akun baru melalui halaman Register
- Pilih role Peserta atau Penyelenggara
- Login menggunakan email dan password yang telah didaftarkan
