# Task 2 - Library Management System with JDBC (Level 3)

This project is part of my **Java Internship with Codveda Technologies**.  
It is a console-based **Library Management System** built in Java with **JDBC** for database connectivity.

---

## Description
The system allows management of books and borrowing transactions using a **MySQL database**.  
Features include:
- **Add a book** → Store new book details in the database.  
- **List books** → View all available books with total and available copies.  
- **Borrow a book** → Decrease available copies and record a borrow transaction.  
- **Return a book** → Increase available copies and record a return transaction.  

---

## Skills Covered
- JDBC (Java Database Connectivity)  
- MySQL database design (tables for books and transactions)  
- CRUD operations using SQL queries  
- Transaction handling (borrow/return)  
- Exception handling in database operations  
- Console-based menu-driven application  

---

## Files Included
- `Book.java` → Book entity class.  
- `LibraryManagementJDBC.java` → Main program with JDBC logic.  
- `README.md` (this file).  
- `schema.sql` (optional, create tables script).  

---

## Database Setup
1. Install MySQL and create a database:
   ```sql
   CREATE DATABASE codveda_library;
   USE codveda_library;
