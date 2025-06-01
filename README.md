# SE_PROJECT
LifeFlow: Streamlined Blood Donation Management

## Overview

LifeFlow is a comprehensive web-based Blood Donation Management System designed to streamline the process of blood donation, inventory management, and handling blood requests. The application supports multiple user roles (donors, administrators, and patients) and provides real-time insights and notifications to ensure efficient and safe blood supply management.

## Features

### Functional Requirements

#### 1. User Management
- **Register and authenticate users:** Support for donors, and patients.
- **Admin user management:** Administrators can manage user accounts.

#### 2. Donation Management
- **Appointment scheduling:** Donors can schedule, reschedule, or cancel donation appointments.
- **Donation tracking:** Track history for each donor (date, blood type).

#### 3. Blood Inventory Management
- **Real-time inventory:** Maintain up-to-date inventory of blood units (blood type, quantity).
- **Stock alerts:** Alert administrators when stock is low.

#### 4. Search and Matching
- **Request scheduling:** Patients can request (schedule, urgent) for specific blood types.
- **Request tracking:** Track history for each patient (date, blood type).

#### 5. Notifications and Alerts
- **Reminders:** Send reminders to donors for scheduled appointments or eligibility.
- **Critical alerts:** Notify patients and administrators about blood shortages.

#### 6. Reporting and Analytics
- **Reports:** Generate reports on donation trends, blood usage, and inventory status.

## Technology Stack

- **Front-end:** HTML, CSS, JavaScript
- **Back-end:** Java (Springboot web framework)
- **Database:** (MySQL)

## Project Structure

```
SE_PROJECT/
├───blood-donation-system/
|   ├───pom.xml
|   └───src/
|       └───main/
|           ├───java/
|           │   └───com/
|           │       └───lifeflow/
|           │           └───blood_donation_system/
|           │               ├───LifeFlowApplication.java
|           │               └───backend/
|           │                   ├───controller/
|           │                   ├───entity/
|           │                   ├───repository/
|           │                   └───service/
|           └───resources/
|               ├───application.properties
|               ├───static/
|               └───templates/
├─── LifeFlow-database.sql
└─── README.md
```

## Prerequisites

  **IntelliJ Idea**  
  **Java Version** (21)  
  **LOMBOK Plugin**  

## Getting Started

#### 1. Clone the repository  
   - Clone and open the project folder in IntelliJ Idea.

#### 2. Set up the backend  
   - Configure the database and run the SQL commands in `LifeFlow-database.sql`.
   - Update the connection string in `application.properties` along with your username and password.

#### 3. Run the application  
   - Run the `LifeFlowApplication.java` file.
   - Open `localhost:8080` or the main entry point in your browser.
