# Bookshop Project

This is a simple Maven project for managing a bookshop application.

## 📂 Project Structure

project1/

├── src/

│   ├── main/

│   │   └── java/

│   │       └── com/bookshop/...

│   └── test/

├── pom.xml

└── README.md

## 🛠Technologies Used

Java 11+

Maven

PostgreSQL (JDBC Driver)

jBCrypt (for password hashing)

## 🗃 Database Tables

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

## 🧰 Prerequisites

JDK 11 or higher

PostgreSQL installed and running

Maven installed and configured

Database bookshop created in PostgreSQL

## ⚙ Setup Instructions ##

**1. 🔧 Configure PostgreSQL** 

Create a PostgreSQL database:

CREATE DATABASE mydb;

Update database credentials in Java files:

Online_Book_Shop.java

static final String URL = "jdbc:postgresql://localhost:5432/mydb";

static final String USER = "postgres";

static final String PASSWORD = "sakshi";


## 📦 Dependencies

<dependencies>
    
    <!-- PostgreSQL JDBC Driver -->
    
    <dependency>
    
      <groupId>org.postgresql</groupId>
      
      <artifactId>postgresql</artifactId>
      
      <version>42.7.3</version>
      
    </dependency>
    
    </dependencies>
    
**📞Contact:** 

For any questions or feedback, feel free to reach out:

**Your Name :** sakshi shipkule

Email: shipkulesakshi682@gmail.com

GitHub: Shipkule-Sakshi

<img width="335" height="811" alt="Screenshot 2025-07-10 164120" src="https://github.com/user-attachments/assets/7a47e881-1010-422d-a500-7a346adcfeff" />

<img width="630" height="816" alt="Screenshot 2025-07-10 164153" src="https://github.com/user-attachments/assets/9076b35c-3e34-4ddb-b3d9-4f8e9f123f90" />

<img width="698" height="813" alt="Screenshot 2025-07-10 164210" src="https://github.com/user-attachments/assets/71dcd19a-a936-4ec7-93fc-e8ce9c709cca" />




**Enjoy using the Online Book Shop! 🚀** 


    
