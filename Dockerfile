# --- Tahap 1: Build Project ---
# Gunakan image Maven yang sudah ada JDK-nya untuk build project
FROM maven:3.8.5-openjdk-17 AS build

# Set direktori kerja di dalam kontainer
WORKDIR /app

# Copy file pom.xml terlebih dahulu untuk caching dependensi
COPY pom.xml .

# Copy seluruh source code proyek
COPY src ./src

# Jalankan perintah Maven untuk build project dan skip tes
RUN mvn clean package -DskipTests

# --- Tahap 2: Run Application ---
# Gunakan image Java yang lebih kecil hanya untuk menjalankan aplikasi
FROM openjdk:17-jdk-slim

# Set direktori kerja
WORKDIR /app

# Copy file JAR yang sudah di-build dari tahap sebelumnya
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 agar bisa diakses dari luar kontainer
EXPOSE 8080

# Perintah untuk menjalankan aplikasi saat kontainer start
ENTRYPOINT ["java","-jar","app.jar"]