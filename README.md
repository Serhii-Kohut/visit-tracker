# Visit Tracker

This is a test project for tracking patient visits to doctors, built with Java, Spring Boot, and MySQL 8.

## Database Setup

### Option 1: Using Database Dump
The test database dump is located in `src/main/resources/db/doctor_visits_dump.sql`. It contains:
- 3 doctors: Олена Коваленко, Ігор Петренко, Марія Сидоренко
- 4 patients: Андрій Мельник, Софія Левицька, Василь Гнатюк, Юлія Бондар
- 6 visits with overlapping doctor-patient relationships

To restore the database:
1. Run the MySQL Docker container:
   ```bash
   docker run -d -p 3306:3306 --name mysql-doctor-visits -e MYSQL_ROOT_PASSWORD=rootpass -e MYSQL_DATABASE=doctor_visits mysql:8