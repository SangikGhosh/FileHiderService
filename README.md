# **File Hider Service**

> A secure service that allows users to hide files by encrypting them, making them inaccessible to unauthorized users. This service sends OTPs for verification and ensures data integrity with a clean and user-friendly interface.

## **Table of Contents**
- [About the Project](#about-the-project)
- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Database Configuration (MyConnection.java)](#database-configuration-myconnectionjava)
  - [Email Setup (SendOTPService.java)](#email-setup-sendotpservicejava)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

### **About the Project**

The **File Hider Service** is a Java-based application that allows users to hide files securely. It includes OTP generation for email verification and integrates with MySQL to store user and file information.

---

### **Features**
- **File Hiding:** Securely hides files within the system.
- **OTP Verification:** Sends an OTP to the user's email for verification before hiding the file.
- **User Management:** Saves and verifies users in the database.
- **Database Connectivity:** Uses MySQL for storing user and file data.

---

### **Project Structure**

```
src
 └── main
     ├── java
     │   ├── dao
     │   │   ├── DataDAO.java
     │   │   └── UserDAO.java
     │   ├── db
     │   │   └── MyConnection.java
     │   ├── model
     │   │   ├── Data.java
     │   │   └── User.java
     │   ├── service
     │   │   ├── GenerateOTP.java
     │   │   ├── SendOTPService.java
     │   │   └── UserService.java
     │   └── views
     │       └── Main.java
     ├── resources
     └── test
```

---

### **Getting Started**

Follow these instructions to set up the project locally.

#### **Prerequisites**
- **Java 11** or later.
- **Maven** installed.
- **MySQL** database set up locally.

#### **Installation**

1. **Clone the repository:**
   ```bash
   git clone https://github.com/SangikGhosh/FileHiderService.git
   ```
2. **Navigate to the project directory:**
   ```bash
   cd file-hider-service
   ```
3. **Install dependencies:**
   ```bash
   mvn install
   ```
4. **Configure MySQL database:**  
   Set up a MySQL database for storing user and file information. Follow the [Database Configuration](#database-configuration-myconnectionjava) section for details.

5. **Set up your email and app password** for sending OTPs. Details can be found in the [Email Setup](#email-setup-sendotpservicejava) section.

6. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

---

### **Database Configuration (MyConnection.java)**

The `MyConnection.java` file is responsible for establishing the connection between the application and your MySQL database. You must replace the placeholder fields with your MySQL credentials.

Here’s the code:

```java
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection connection = null;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Replace with your MySQL database URL, username, and password
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME?useSSL=false", 
                "YOUR_USERNAME", 
                "YOUR_PASSWORD"
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection established successfully!");
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
```

### **Instructions for Configuring `MyConnection.java`:**
1. Open `src/main/java/db/MyConnection.java` in your IDE.
2. Update the database connection string with your MySQL database details:
   - **YOUR_DATABASE_NAME**: The name of your MySQL database (e.g., `File_hider_project`).
   - **YOUR_USERNAME**: Your MySQL username (e.g., `root`).
   - **YOUR_PASSWORD**: Your MySQL password.

---

### **Email Setup (SendOTPService.java)**

The `SendOTPService.java` class is responsible for sending OTPs to the user's email via Gmail's SMTP server. To make this work, you will need to configure your own email and an app-specific password.

Here’s the code:

```java
package service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendOTPService {
    public static void sendOTP(String email, String genOTP) {
        // Recipient's email ID needs to be mentioned.
        String to = email;

        // Sender's email ID needs to be mentioned
        String from = "YOUR_MAIL";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "APP_PASSWORD");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Message from File Hider Service");

            // Now set the actual message
            message.setText("Your One time Password for File Hider Service is " + genOTP);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
```

### **Instructions for Configuring `SendOTPService.java`:**
1. Replace the placeholder `YOUR_MAIL` with your actual Gmail address.
2. Replace `APP_PASSWORD` with your Gmail app password.

   **What is an app password?**  
   An app password is a one-time code you generate to allow less secure apps (like this Java project) to access your Gmail account. To generate an app password:
   - Go to your Google Account settings.
   - Navigate to **Security** > **Signing in to Google** > **App passwords**.
   - Follow the instructions to generate an app password.

   **Need help generating the app password?**  
   Check out this tutorial on YouTube: [How to Create a Gmail App Password](https://youtu.be/MkLX85XU5rU?si=7_2FdNt7SRQU7mgb).

---

### **Usage**

Once the project is set up and the email configuration is complete, you can run the project and start using its file hiding features. The OTP will be sent to the user's email for verification.

---

### **Contributing**

Contributions are welcome! Here’s how you can help:
1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/FeatureName`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/FeatureName`).
5. Open a Pull Request.

---

### **License**

Distributed under the MIT License. See `LICENSE.txt` for more information.

---

### **Contact**

For questions, reach out to:
- **Sangik Ghosh**: [sangik.ghosh1@gmail.com](mailto:sangik.ghosh1@gmail.com)

---

This README should now include all the necessary setup instructions, focusing on the database connection and OTP email configuration. Let me know if any other changes are needed!
