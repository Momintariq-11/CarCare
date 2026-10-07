# 🚗 CarCare — Auto Workshop Management System

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![FlatLaf](https://img.shields.io/badge/FlatLaf-3.7.2-blue.svg)](https://www.formdev.com/flatlaf/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Architecture](https://img.shields.io/badge/Architecture-3--Tier-green.svg)](#architecture)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

CarCare is an enterprise desktop management solution built with **Java Swing** and **MySQL**, designed to streamline day-to-day automotive workshop operations, manage active vehicle service queues, automate customer billing, and schedule repair appointments.

Developed by **Muhammad** during a Software Engineering Internship at **KolonX**.

---

## 📌 Key Features

- **🔐 Glassmorphic Authentication**: FlatLaf-powered secure login window with native password reveal toggle, custom rounded cards, and responsive multi-resolution centering.
- **📊 Real-time Analytics Dashboard**: Live summary of 6 core business metrics (Total Customers, Active Vehicles, Cars in Queue, Bay Status, Completed Jobs, Total Revenue) plus a recent activity log.
- **🚙 Dual-Entity Vehicle & Customer Registry**: Atomic registration handling customer contact details and car specs with auto-increment relational ID binding.
- **⏱️ Live FIFO Service Bay Queue**: Dynamic queue engine managing workshop bay transitions (`Waiting` ➔ `In Service` ➔ `Completed`), live duration timers, and automated position re-indexing.
- **📅 Appointment Scheduling & 1-Click Check-In**: Service booking calendar with check-in functionality that automatically transitions scheduled appointments into the live workshop bay queue.
- **🧾 Automated Invoicing & ASCII Receipts**: Auto-generates itemized invoices upon service completion, calculates multi-tier discounts (Loyalty / Corporate), supports extra parts add-ons, and generates monospaced ASCII customer receipts.
- **🛡️ Enterprise Security**: 100% parameterized JDBC `PreparedStatement` queries to eliminate SQL injection risks across all database interactions.

---

## 🏗️ Architecture

The project strictly follows the **3-Tier Enterprise Pattern**:

```
Presentation Layer (Swing + FlatLaf 3.7.2)
       │  (Calls Business Services)
       ▼
Business Layer (Entities, DTOs & BLL Validation)
       │  (Invokes Data Access Objects)
       ▼
Data Access Layer (JDBC DAOs + PreparedStatements)
       │  (TCP Socket Connection on Port 3306)
       ▼
MySQL Database (carcare_db - 9 Relational InnoDB Tables)
```

### Relational Database Schema:
- `users`: Administrator credentials
- `customers`: Customer master records
- `vehicles`: Registered vehicles (`FK -> customers` with CASCADE)
- `mechanics`: Workshop technicians and status
- `services`: Catalog of standard workshop packages
- `appointments`: Scheduled client bookings
- `service_records`: Central work orders binding car, technician, and job log
- `service_queue`: Live FIFO queue tracking active repair bays
- `bills`: Itemized invoices (`UNIQUE FK -> service_records`)

---

## 💻 Tech Stack

- **Language:** Java (JDK 17+)
- **GUI Framework:** Java Swing
- **Look & Feel:** FlatLaf 3.7.2 Light Theme
- **Database Engine:** MySQL (InnoDB via XAMPP)
- **Database Driver:** MySQL Connector/J 26.7.0
- **Development Tool:** Apache NetBeans (Matisse GUI Builder)

---

## 🚀 Getting Started

### Prerequisites
1. **JDK 17 or higher** installed.
2. **XAMPP** or **MySQL Server** running on `localhost:3306`.
3. Required external JAR dependencies:
   - `flatlaf-3.7.2.jar`
   - `mysql-connector-j-26.7.0.jar`

### Database Setup
1. Open phpMyAdmin or MySQL CLI:
   ```sql
   CREATE DATABASE carcare_db;
   USE carcare_db;
   ```
2. Import the database dump provided in this repository:
   ```bash
   mysql -u root carcare_db < database/carcare_db.sql
   ```
3. Default admin credentials:
   - **Email:** `admin@gmail.com`
   - **Password:** `admin123`

### Build & Run via Command Line
```bash
# Compile
javac -cp "lib/flatlaf-3.7.2.jar:lib/mysql-connector-j-26.7.0.jar:build/classes" \
      -d build/classes \
      src/BusinessLayer/*.java src/DataAccessLayer/*.java src/PresentationLayer/*.java

# Run Application
java -cp "build/classes:lib/flatlaf-3.7.2.jar:lib/mysql-connector-j-26.7.0.jar" \
     PresentationLayer.LoginForm
```

---

## 👤 Author

**Muhammad**  
- GitHub: [@Momintariq-11](https://github.com/Momintariq-11)  
- Completed as part of Software Engineering Internship at **KolonX**.
