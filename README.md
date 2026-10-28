# Lost & Found System

## About the Project

The Lost & Found System is a Java-based application designed to help users report, search for, and manage lost and found items. It helps make it easier to identify missing belongings and track whether an item has been returned.

## Features

* Report lost items.
* Report found items.
* View all reported items.
* Search items by name.
* Store item details such as name, description, and location.
* Mark items as returned.
* Generate a unique ID for each reported item.
* Simple menu-driven interface.

## Technologies Used

* **Programming Language:** Java
* **Concepts:** Classes, Objects, Constructors, ArrayList, Loops, Methods, and Conditional Statements
* **Input Handling:** Scanner

## Project Structure

LostFoundSystem/
│
├── LostFoundSystem.java
└── README.md

## Prerequisites

* Java Development Kit (JDK 8 or later)
* Any Java IDE, such as IntelliJ IDEA, Eclipse, or VS Code
* Command Prompt or Terminal (optional)

## How to Run the Project

### Step 1: Clone the Repository

```bash
git clone <your-repository-url>
```

### Step 2: Open the Project

Open the project folder in your preferred Java IDE.

### Step 3: Compile the Program

Open a terminal in the project directory and execute:

```bash
javac LostFoundSystem.java
```

### Step 4: Run the Program

```bash
java LostFoundSystem
```

## How to Use

1. Run the application.
2. Select an option from the menu.
3. Enter the required item details.
4. View all reported items or search for an item by name.
5. Use the item ID to mark an item as returned.
6. Select Exit to close the application.

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

* Data is stored temporarily in an ArrayList.
* Records are lost when the application is closed.
* The application does not currently use a database.
* Item matching is based on the item name only.
* User authentication and an administrator dashboard are not included.

## Future Enhancements

* Integrate MySQL using JDBC for permanent data storage.
* Add user registration and login.
* Develop a graphical user interface using Java Swing.
* Implement intelligent matching using item descriptions, categories, and locations.
* Add image uploads for reported items.
* Include notifications when a possible match is found.
* Add an administrator dashboard to manage reports.

## Conclusion

The Lost & Found System is a beginner-friendly Java project that demonstrates object-oriented programming and basic record management. It can be extended into an intelligent platform that helps people recover their lost belongings more efficiently.

## Author

**Shubhangi Teke**
**Priti Joshi**

## License

This project is intended for educational purposes.
