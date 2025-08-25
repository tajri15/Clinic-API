# ClinicAPI - REST API Sistem Booking Konsultasi Dokter

Sebuah proyek backend RESTful yang dibangun menggunakan Java Spring Boot untuk mensimulasikan sistem booking jadwal konsultasi dokter online. Proyek ini mencakup autentikasi berbasis JWT, manajemen role (Admin & Pasien), dan fitur CRUD untuk mengelola data dokter dan janji temu.

## ✨ Fitur Utama

-   **Autentikasi & Otorisasi:** Registrasi dan Login aman menggunakan Spring Security & JSON Web Tokens (JWT).
-   **Role-Based Access:** Perbedaan hak akses antara `ROLE_ADMIN` dan `ROLE_PATIENT`.
-   **Manajemen Admin:** Endpoint khusus admin untuk menambah dan melihat data dokter serta jadwal praktek mereka.
-   **Fitur Pasien:**
    -   Melihat daftar dokter dan jadwal praktek yang tersedia.
    -   Membuat janji temu (booking) pada slot waktu yang valid.
    -   Melihat riwayat janji temu pribadi.
-   **Penanganan Error:** Global Exception Handler untuk response error yang bersih dan konsisten.
-   **Testing:** Unit test untuk service layer (Mockito) dan integration test untuk controller (MockMvc & H2).
-   **Kontainerisasi:** Aplikasi dan database sepenuhnya ter-kontainerisasi dengan Docker & Docker Compose untuk kemudahan setup dan deployment.

## 🛠️ Tech Stack

-   **Backend:** Java 17+, Spring Boot 3
-   **Database:** PostgreSQL
-   **Akses Data:** Spring Data JPA / Hibernate
-   **Keamanan:** Spring Security, JWT
-   **Build Tool:** Maven
-   **Testing:** JUnit 5, Mockito, H2 Database
-   **Deployment:** Docker, Docker Compose

## 🚀 Cara Menjalankan Proyek

Cara termudah untuk menjalankan proyek ini adalah dengan menggunakan Docker.

### Menggunakan Docker Compose (Cara Mudah)

1.  Pastikan Anda sudah menginstal Docker dan Docker Compose.
2.  Clone repositori ini:
    ```bash
    git clone [https://github.com/tajri15/Clinic-API.git](https://github.com/tajri15/Clinic-API.git)
    ```
3.  Masuk ke direktori proyek:
    ```bash
    cd Clinic-API
    ```
4.  Jalankan perintah berikut untuk membangun dan memulai semua service:
    ```bash
    docker-compose up --build
    ```
5.  Aplikasi akan berjalan dan siap diakses di `http://localhost:8080`.

### Secara Manual (Tanpa Docker)

1.  **Prasyarat:**
    -   Java JDK 17+
    -   Maven 3.8+
    -   PostgreSQL berjalan di `localhost:5432`.
2.  Buat database bernama `clinic_db` dengan user `admin` dan password `password`.
3.  Clone repositori ini.
4.  Jalankan aplikasi dengan perintah Maven:
    ```bash
    mvn spring-boot:run
    ```

## 📝 Contoh Penggunaan API (Menggunakan Postman/cURL)

#### 1. Registrasi Pasien Baru
```bash
# REQUEST
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
    "name": "Budi Santoso",
    "email": "budi.santoso@gmail.com",
    "password": "password123"
}

# RESPONSE: "User registered successfully!"

#### 2. Login
```bash
# REQUEST
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
    "email": "budi.santoso@gmail.com",
    "password": "password123"
}

# RESPONSE: { "token": "ey..." }

#### Membuat Janji Temu (Membutuhkan Token)
```bash
# REQUEST
POST http://localhost:8080/api/appointments
Authorization: Bearer <TOKEN_DARI_LOGIN>
Content-Type: application/json

{
  "doctorId": 1,
  "appointmentTime": "2025-09-15T10:00:00"
}

# RESPONSE: 201 Created dengan detail janji temu