# 🏦 Bank Management System

A **Java-based Bank Management System** using **MySQL**, **JDBC**, and **Object-Oriented Programming (OOP)** principles.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| **Java** | Core application logic |
| **OOP** | Encapsulation, Inheritance, Abstraction, Polymorphism |
| **MySQL / Oracle Database** | Persistent data storage |
| **JDBC** | Java Database Connectivity |
| **SQL** | Database queries |

## 📁 Project Structure

```
Bank-Management-System/
├── src/
│   ├── model/
│   │   ├── Account.java          # Abstract base class (OOP - Abstraction)
│   │   ├── SavingsAccount.java   # Extends Account (OOP - Inheritance)
│   │   └── Transaction.java      # Transaction model
│   ├── dao/
│   │   ├── AccountDAO.java       # Interface (OOP - Abstraction)
│   │   └── AccountDAOImpl.java   # JDBC implementation
│   ├── db/
│   │   └── DBConnection.java     # Database connection (Singleton pattern)
│   ├── service/
│   │   └── BankService.java      # Business logic layer
│   └── main/
│       └── BankApp.java          # Entry point / Menu UI
├── sql/
│   └── schema.sql                # MySQL / Oracle DDL scripts
└── README.md
```

## 🚀 Features

- ✅ Create new bank account
- ✅ Deposit money
- ✅ Withdraw money (with balance validation)
- ✅ Check balance
- ✅ Display full account details
- ✅ View transaction history
- ✅ PIN-protected operations
- ✅ MySQL & Oracle Database compatible

## 🔧 Setup Instructions

### 1. Database Setup (MySQL)
```sql
SOURCE sql/schema.sql;
```

### 2. Configure DB Connection
Edit `src/db/DBConnection.java`:
```java
static final String URL = "jdbc:mysql://localhost:3306/bank_db";
static final String USER = "root";
static final String PASSWORD = "your_password";
```

### 3. Add MySQL JDBC Driver
Download `mysql-connector-java.jar` and add to classpath:
```bash
javac -cp .;mysql-connector-java.jar src/**/*.java
java -cp .;mysql-connector-java.jar src/main/BankApp
```

## 📌 OOP Concepts Used

| Concept | Where Used |
|---|---|
| **Encapsulation** | Private fields in `Account.java` with getters/setters |
| **Inheritance** | `SavingsAccount` extends `Account` |
| **Abstraction** | `Account` abstract class + `AccountDAO` interface |
| **Polymorphism** | `AccountDAO` reference points to `AccountDAOImpl` |

---
*Author: Matam Rohith — SR University BTech Project*
