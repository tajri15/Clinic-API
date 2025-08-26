# ClinicAPI - REST API Sistem Booking Konsultasi Dokter

Ini adalah backend RESTful untuk aplikasi booking dokter online. Dibangun dengan Java & Spring Boot, proyek ini mencakup autentikasi JWT, manajemen role (Admin & Pasien), dan fitur CRUD.

## 🛠️ Tech Stack

- **Backend:** Java 17+, Spring Boot 3
- **Keamanan:** Spring Security, JWT
- **Database:** PostgreSQL
- **Akses Data:** Spring Data JPA / Hibernate
- **Testing:** JUnit 5, Mockito, H2
- **Deployment:** Docker, Docker Compose

## 🚀 Cara Menjalankan

Cara termudah adalah dengan Docker Compose.

1.  Clone repositori ini.
2.  Pastikan Docker Desktop berjalan.
3.  Jalankan perintah berikut di root folder:
    ```bash
    docker-compose up --build
    ```
4.  Backend akan berjalan di `http://localhost:8080`.

## 📝 Contoh Endpoint API

-   `POST /api/auth/register`: Mendaftarkan user baru.
-   `POST /api/auth/login`: Login untuk mendapatkan token JWT.
-   `GET /api/doctors`: (Publik) Melihat semua dokter.
-   `POST /api/admin/doctors`: (Admin) Menambah dokter baru.
-   `POST /api/appointments`: (Pasien) Membuat janji temu.