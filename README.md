# A Java-based e-commerce application built using **MVC (Model-View-Controller)** architecture pattern. This application provides a complete marketplace solution with role-based access control for Customers, Admins, and Couriers.

---

## 📋 Table of Contents

- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Architecture](#-architecture)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#-installation--setup)
- [Screenshots](#-screenshots)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [Usage Guide](#-usage-guide)
- [Contributors](#-contributors)
- [License](#-license)

---

## ✨ Features

### 👤 Customer Features

- User registration and login
- Browse products by category
- Add products to shopping cart
- Update cart item quantities
- Remove items from cart
- Top up balance
- Apply promo codes during checkout
- Place orders with checkout validation
- View order history
- Edit profile information

### 👨‍💼 Admin Features

- View all products
- Edit product stock
- View all orders
- View all available couriers
- Assign orders to couriers
- Manage delivery status

### 🚚 Courier Features

- View assigned deliveries
- Update delivery status (On The Way, Delivered, Cancelled)

---

## 🛠 Technology Stack

### Core Technologies

- **Java** - Programming language (JDK 11+)
- **JavaFX** - GUI framework for desktop application (No FXML, pure Java code)
- **MySQL** - Relational database management system
- **JDBC** - Java Database Connectivity for database operations

### Development Tools

- **Eclipse IDE** - Integrated Development Environment
- **XAMPP** - Local server environment (for MySQL)
- **MySQL Connector/J** - JDBC driver for MySQL

### Architecture & Design Patterns

- **MVC (Model-View-Controller)** - Architectural pattern
- **Singleton Pattern** - For database connection management
- **Module System** - Java 9+ module system

### Libraries & Dependencies

- `javafx.controls` - JavaFX UI components
- `java.sql` - JDBC API for database operations
- `com.mysql.cj.jdbc.*` - MySQL JDBC driver

---

## 🏗 Architecture

This project follows the **MVC (Model-View-Controller)** architectural pattern:

### **Model** (`src/model/`)

- Represents data and business entities
- Handles database operations (CRUD)
- Contains: `User`, `Product`, `Order`, `CartItem`, `Promo`, `Delivery`, etc.
- **Responsibility**: Database access through JDBC, no validation, no output

### **View** (`src/view/`)

- Handles user interface and presentation
- JavaFX-based GUI components
- Contains: `LoginView`, `HomeView`, `ProductWindow`, `CartItemWindow`, etc.
- **Responsibility**: Display UI, collect user input, no business logic

### **Controller** (`src/controller/`)

- Contains business logic and validation
- Acts as mediator between Model and View
- Contains: `UserHandler`, `ProductHandler`, `OrderHandler`, `CartItemHandler`, etc.
- **Responsibility**: All validations and business logic

---

## 📦 Prerequisites

Before running this application, ensure you have the following installed:

1. **Java Development Kit (JDK) 11 or higher**

   - Download from: https://adoptium.net/ or https://www.oracle.com/java/technologies/downloads/

2. **Eclipse IDE for Java Developers**

   - Download from: https://www.eclipse.org/downloads/

3. **XAMPP** (for MySQL)

   - Download from: https://www.apachefriends.org/
   - Alternative: MySQL Server standalone

4. **MySQL JDBC Connector/J**

   - Download from: https://dev.mysql.com/downloads/connector/j/
   - Choose: Platform Independent → ZIP
   - **Note**: Pre-downloaded version available in `resources/` folder

5. **JavaFX SDK** (if not included with JDK)
   - Download from: https://openjfx.io/
   - **Note**: Pre-downloaded version available in `resources/` folder

---

## 🚀 Installation & Setup

### Step 1: Clone the Repository

```bash
git clone https://github.com/paisaltanjung11/javafx-ecommerce-mvc
```

### Step 2: Database Setup

1. **Start MySQL**

   - Open XAMPP Control Panel
   - Click **Start** on MySQL service

2. **Import Database**

   - Open phpMyAdmin: `http://localhost/phpmyadmin`
   - Create a new database (e.g. `javafx_ecommerce`)
   - Select the created database
   - Go to the **Import** tab
   - Upload and import the following file:

     ```
     database/ooad.sql
     ```

> 📝 **Note**: Update database connection details in `src/model/DatabaseConnection.java` if needed:
>
> - Database name: `javafx-ecommerce-mvc`
> - Username: `root`
> - Password: (empty by default)

### Step 3: Configure Eclipse Project

> 📦 **Resources Folder**: This project includes a `resources/` folder containing pre-downloaded dependencies:
>
> - **MySQL Connector**: `resources/mysql-connector-java-8.0.24.jar`
> - **JavaFX SDK**: `resources/openjfx-17.0.7_windows-x64_bin-sdk/` (Windows x64)
>
> If you're on a different OS, you may need to download the appropriate JavaFX SDK version from https://openjfx.io/

1. **Import Project**

   - Open Eclipse IDE
   - File → Import → General → Existing Projects into Workspace
   - Browse to `javafx-ecommerce-mvc` folder
   - Click Finish

2. **Add MySQL JDBC Connector**

   - Right-click project → Properties
   - Java Build Path → Libraries → Add External JARs
   - Navigate to `resources/` folder in project
   - Select `mysql-connector-java-8.0.24.jar`
   - Apply and Close
   - **Alternative**: If you prefer to download manually, get it from https://dev.mysql.com/downloads/connector/j/

3. **Add JavaFX SDK** (if needed)

   - Right-click project → Properties
   - Java Build Path → Libraries
   - Modulepath → Add External JARs
   - Navigate to `resources/openjfx-17.0.7_windows-x64_bin-sdk/javafx-sdk-17.0.7/lib/`
   - Add all JAR files from this folder:
     - `javafx.base.jar`
     - `javafx.controls.jar`
     - `javafx.fxml.jar`
     - `javafx.graphics.jar`
     - `javafx.media.jar`
     - `javafx.swing.jar`
     - `javafx.web.jar`
   - Apply and Close
   - **Note**: The JavaFX SDK is pre-downloaded in the `resources/` folder. If you're on a different OS, download the appropriate version from https://openjfx.io/

### Step 4: Run the Application

1. Navigate to `src/main/Main.java`
2. Right-click → Run As → Java Application
3. The application window will open

---

## 📸 Screenshots

### Application Screenshots

#### Login & Authentication

![Login Page](screenshots/login_page.png)
_Login interface for users to access the system_

#### Customer Dashboard

![Customer Home](screenshots/customer_home.png)
_Main dashboard for customers after login_

#### Product Browsing

![Product List](screenshots/product_list.png)
_Browse available products in the marketplace_

#### Shopping Cart

![Shopping Cart](screenshots/shopping_cart.png)
_View and manage items in shopping cart_

#### Checkout Process

![Checkout](screenshots/checkout.png)
_Checkout interface with promo code application_

#### Order History

![Order History](screenshots/order_history.png)
_View past orders and order details_

#### Admin Panel

![Admin Dashboard](screenshots/admin_dashboard.png)
_Admin dashboard for managing products and orders_

#### Edit Product Stock

![Edit Stock](screenshots/edit_stock.png)
_Admin interface for updating product stock_

#### Assign Courier

![Assign Courier](screenshots/assign_courier.png)
_Admin interface to assign orders to couriers_

#### Courier Dashboard

![Courier Dashboard](screenshots/courier_dashboard.png)
_Courier interface for managing assigned deliveries_

---

> 💡 **Note**: Replace the dummy placeholder images in the `screenshots/` folder with your actual application screenshots. Use descriptive filenames like `login_page.png`, `customer_home.png`, etc.

---

## 📁 Project Structure

```
javafx-ecommerce-mvc/
├── src/
│   ├── main/
│   │   └── Main.java                 # Application entry point
│   ├── model/                        # Model classes (data entities)
│   │   ├── User.java
│   │   ├── Customer.java
│   │   ├── Admin.java
│   │   ├── Courier.java
│   │   ├── Product.java
│   │   ├── CartItem.java
│   │   ├── OrderHeader.java
│   │   ├── OrderDetail.java
│   │   ├── Promo.java
│   │   ├── Delivery.java
│   │   └── DatabaseConnection.java   # Singleton DB connection
│   ├── controller/                   # Controller classes (business logic)
│   │   ├── UserHandler.java
│   │   ├── ProductHandler.java
│   │   ├── CartItemHandler.java
│   │   ├── OrderHandler.java
│   │   ├── PromoHandler.java
│   │   └── DeliveryHandler.java
│   ├── view/                         # View classes (GUI)
│   │   ├── LoginView.java
│   │   ├── RegisterView.java
│   │   ├── HomeView.java
│   │   ├── ProductWindow.java
│   │   ├── CartItemWindow.java
│   │   ├── CheckoutView.java
│   │   └── ... (other view classes)
│   ├── utils/
│   │   └── StageManager.java         # Scene management utility
│   └── module-info.java              # Java module declaration
├── database/
│   ├── schema.sql                    # Database schema
│   ├── seed_data.sql                 # Initial data
│   └── README.md                     # Database setup guide
├── resources/                        # Required libraries and dependencies
│   ├── mysql-connector-java-8.0.24.jar  # MySQL JDBC Connector
│   └── openjfx-17.0.7_windows-x64_bin-sdk/  # JavaFX SDK (Windows)
│       └── javafx-sdk-17.0.7/
│           └── lib/                  # JavaFX JAR files
├── screenshots/                      # Application screenshots
│   └── (your screenshot files here)
├── USER_GUIDE.md                     # User manual
├── Eclipse_Setup_Guide.md            # Detailed setup guide
└── README.md                         # This file
```

---

## 🗄 Database Schema

The application uses MySQL database with the following main tables:

- **users** - User accounts (Customer, Admin, Courier)
- **products** - Product catalog
- **cart_items** - Shopping cart items
- **order_headers** - Order information
- **order_details** - Order line items
- **promos** - Promotional codes
- **deliveries** - Delivery information and status

For detailed schema information, see `database/schema.sql`

---

## 📖 Usage Guide

### Default Test Accounts

After importing `seed_data.sql`, you can use these test accounts:

**Customer:**

- Email: `alice@gmail.com`
- Password: `123456`

**Admin:**

- Email: `admin@gmail.com`
- Password: `123456`

**Courier:**

- Email: `courier@gmail.com`
- Password: `123456`

---


## 📄 License

This project is created for educational purposes as a JavaFX e-commerce application demonstration.

---


## 🐛 Troubleshooting

### Common Issues

**Issue**: Database connection error

- **Solution**: Ensure XAMPP MySQL is running and database is created

**Issue**: JavaFX not found / Module resolution error

- **Solution**: Add JavaFX SDK to module path in Eclipse project properties. The JAR files are located in `resources/openjfx-17.0.7_windows-x64_bin-sdk/javafx-sdk-17.0.7/lib/`

**Issue**: MySQL Connector not found

- **Solution**: Add `mysql-connector-java-8.0.24.jar` from `resources/` folder to project build path

**Issue**: Module-info.java errors

- **Solution**: Ensure JavaFX SDK is properly added to module path (not classpath)

---
