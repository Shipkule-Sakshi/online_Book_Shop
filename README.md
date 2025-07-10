Bookshop Project

This is a simple Maven project for managing a bookshop application.

📂 Project Structure

project1/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/bookshop/...
│   └── test/
├── pom.xml
└── README.md

🛠Technologies Used

Java 11+

Maven

PostgreSQL (JDBC Driver)

jBCrypt (for password hashing)

🗃 Database Tables
author
category
book
register_users
place_order
order_items
The initial version includes full functionality for managing:

Authors
Categories
Books
(Users and orders can be added later.)

🧰 Prerequisites
JDK 11 or higher
PostgreSQL installed and running
Maven installed and configured
Database bookshop created in PostgreSQL
⚙ Setup Instructions
1. 🔧 Configure PostgreSQL
Create a PostgreSQL database:
CREATE DATABASE mydb;
Update database credentials in Java files:
Online_Book_Shop.java
static final String URL = "jdbc:postgresql://localhost:5432/mydb";
static final String USER = "postgres";
static final String PASSWORD = "sakshi";


📦 Dependencies

<dependencies>
    
    <!-- PostgreSQL JDBC Driver -->
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <version>42.7.3</version>
    </dependency>
    
    .Contact: For any questions or feedback, feel free to reach out:

Your Name : sakshi shipkule

Email: shipkulesakshi682@gmail.com

GitHub: Shipkule-Sakshi

Enjoy using the Online Book Shop! 🚀
    
