# Lost & Found System

## About the Project

The Lost & Found System is a Java-based application designed to help users report, search for, and manage lost and found items. It helps users identify missing belongings and track whether an item has been found or returned.

The application uses **Java and MySQL** to manage user information and store lost and found item records in a database.

## Features

- Report lost items.
- Report found items.
- View all reported items.
- Search items by name.
- Store item details such as name, description, and location.
- Mark items as returned.
- Generate a unique ID for each reported item.
- Store user details in a database.
- Maintain separate tables for users, lost items, and found items.
- Connect Java to MySQL using JDBC.

## Technologies Used

- **Programming Language:** Java
- **Database:** MySQL
- **Database Connectivity:** JDBC (Java Database Connectivity)
- **Concepts:** Classes, Objects, Constructors, ArrayList, Loops, Methods, and Conditional Statements
- **Input Handling:** Scanner

## Database Design

The project uses a MySQL database named `lost_found_db` to store user and item information.

The database contains three main tables.

### 1. Users Table

The `users` table stores information about registered users.

Typical fields include:

- `user_id` – Primary key that uniquely identifies each user.
- `name` – Name of the user.
- `email` – Email address of the user.
- `password` – User login password.

### 2. Lost Items Table

The `lost_items` table stores details of items reported as lost.

Typical fields include:

- `item_id` – Primary key that uniquely identifies each item.
- `item_name` – Name of the lost item.
- `description` – Description of the item.
- `location` – Location where the item was lost.
- `date_lost` – Date when the item was lost.
- `status` – Current status of the item.
- `user_id` – Foreign key referencing the user who reported the item.

### 3. Found Items Table

The `found_items` table stores details of items reported as found.

Typical fields include:

- `item_id` – Primary key that uniquely identifies each item.
- `item_name` – Name of the found item.
- `description` – Description of the item.
- `location` – Location where the item was found.
- `date_found` – Date when the item was found.
- `status` – Current status of the item.
- `user_id` – Foreign key referencing the user who reported the item.

**Note:** These are the expected fields based on the database design. Make sure the field names match your actual MySQL tables.

## Database Connection

The application connects Java to MySQL using JDBC.

**Database name:** `lost_found_db`

**JDBC URL:**

```java
jdbc:mysql://localhost:3306/lost_found_db
```

The connection class establishes communication between the Java application and the MySQL database.

Example connection code:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/lost_found_db";

    private static final String USER = "root";
    private static final String PASSWORD = "your_mysql_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

Replace `your_mysql_password` with your actual MySQL password.

### How JDBC Works

1. Java requests a connection to the MySQL database.
2. JDBC establishes the connection using the database URL and credentials.
3. SQL queries are executed to insert, retrieve, update, or delete records.
4. The results are returned to the Java application.
5. The connection is closed when it is no longer required.

## Project Structure

```text
LostFoundSystem/
│
├── LostFoundSystem.java
├── Dbconnection.java
├── database.sql
└── README.md
```

`database.sql` contains the SQL commands used to create the database and its tables. The project may contain additional Java files depending on its implementation.

## Prerequisites

- Java Development Kit (JDK 8 or later).
- MySQL Community Server.
- MySQL Connector/J JDBC driver.
- Any Java IDE, such as IntelliJ IDEA, Eclipse, or VS Code.
- Command Prompt or Terminal (optional).

## Database Setup

### Step 1: Start MySQL

Start your MySQL server and log in using MySQL Workbench or the MySQL command-line client.

### Step 2: Create the Database

Execute the following SQL commands:

```sql
CREATE DATABASE IF NOT EXISTS lost_found_db;

USE lost_found_db;
```

### Step 3: Create the Tables

Create the `users`, `lost_items`, and `found_items` tables according to the database design described above.

If the tables already exist, you do not need to create them again.

### Step 4: Configure JDBC

Add the MySQL Connector/J library to your Java project's classpath and update the database username and password in `Dbconnection.java`.

### Step 5: Test the Connection

Call `Dbconnection.getConnection()` from your Java application to verify that the database connection works.

## How to Run the Project

### Step 1: Open the Project

Open the project folder in your preferred Java IDE.

### Step 2: Configure the Database

Ensure that MySQL is running, the `lost_found_db` database and its tables exist, and the JDBC connection details are correct.

### Step 3: Compile the Program

If your main class is `LostFoundSystem.java`, run:

```bash
javac LostFoundSystem.java
```

If `Dbconnection.java` is a separate class, compile it too:

```bash
javac Dbconnection.java LostFoundSystem.java
```

Make sure the MySQL Connector/J JAR is available on the classpath when compiling and running the application.

### Step 4: Run the Program

```bash
java LostFoundSystem
```

The exact commands may vary depending on your IDE, package structure, and JDBC setup.

## How to Use

1. Run the application.
2. Select an option from the menu.
3. Enter the required item details.
4. View all reported items or search for an item by name.
5. Use the item ID to identify the item whose status needs to be updated.
6. Select Exit to close the application.

When database integration is implemented in the application, item and user records can be stored in MySQL and retrieved later.

## Sample Output

```text
===== LOST & FOUND SYSTEM =====
1. Report Lost Item
2. Report Found Item
3. View All Items
4. Search Item
5. Mark Item as Returned
6. Exit

Enter your choice: 1
Enter item name: Wallet
Enter description: Black leather wallet
Enter location: College Library

Item reported successfully!
```

## Current Limitations

- The actual functionality depends on the features implemented in the Java code.
- Database records are available only when the application successfully connects to MySQL.
- Item matching may be based on the item name only.
- User authentication and administrator functionality may require further implementation.
- Intelligent item matching and notifications are not included unless implemented separately.

## Future Enhancements

- Add secure user registration and login.
- Develop a graphical user interface using Java Swing.
- Implement intelligent matching using item descriptions, categories, and locations.
- Add image uploads for reported items.
- Include notifications when a possible match is found.
- Add an administrator dashboard to manage reports.
- Improve security through password hashing and input validation.

## Conclusion

The Lost & Found System is a Java-based project that demonstrates object-oriented programming, record management, and database connectivity. By integrating MySQL through JDBC, the system can maintain separate records for users, lost items, and found items. It can be extended with intelligent matching and additional features to help users recover their missing belongings more efficiently.

## Authors

- Shubhangi Teke
- Priti Joshi
