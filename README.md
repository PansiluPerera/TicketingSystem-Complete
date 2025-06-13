
# Real-Time Ticketing System (The Ticketer)

### Created by: Pansilu Perera


## Description

The Ticketer is a real-time event ticketing system designed to streamline ticket purchasing, event management, and user interaction. This system is developed using a **React JS frontend**, a **Spring Boot backend** with **PostgreSQL** as the database, and includes a standalone **CLI-based ticketing application** for core functionality. The application supports multiple user roles, including **admins**, **vendors**, and **customers**, each with tailored features for their needs.

---

## Features

- **Frontend**: Built with React JS for a modern, interactive user interface.
- **Backend**: Powered by Spring Boot, with robust APIs for seamless communication between components.
- **Database**: PostgreSQL used for efficient data management, integrated with Spring JPA.
- **CLI Application**: A standalone command-line interface for executing main system features.
- **Role-Based Dashboards**:
  - **Admin**: Manage events, ticket sales, and user data.
  - **Vendor**: Manage ticket inventory for events.
  - **Customer**: Purchase tickets and view events in real time.

---

## Setup Instructions

### Frontend Setup
1. Install **Node.js v23.4.0**.
2. Open a terminal or CMD window and navigate to the frontend directory:
   cd /FrontEnd/ticketingsystem

Run the following command to start the frontend:
npm start

If any dependency errors occur, manually install the missing dependencies:
npm install <dependency-name>

### PostgreSQL Database Setup

Install PostgreSQL and create a new server:
Server Name: Ticketing-System
Username: postgres
Password: user

Create a database within the server:
Database Name: ticketing_system

Ensure the server runs on the default port (5432) and leave other settings as default.
After running the backend for the first time:
Insert a row in the admin_entity table with the following:
adminUserName and adminPassword.

These credentials will be used for accessing the admin dashboard in the frontend.

### Backend Setup

Open the backend project in IntelliJ IDEA (Ultimate Edition preferred).
Install the following:
The latest Java SDK.

Maven dependencies (automatically resolved by IntelliJ).
PostgreSQL drivers.

Establish a database connection:
Configure the connection to point to the ticketing_system database.

Run the backend:
Start the application via TicketingSystemApplication.java.

The backend will now be accessible:
Use tools like Postman or the frontend to interact with the APIs.

### TicketingSystem CLI Setup

Compile the RealTimeTicketingSystemCLI.java file.

Run the program:
java RealTimeTicketingSystemCLI

No additional requirements are necessary for the CLI.

## UI Controls

### Admin Dashboard

Tabs Available:
1. Create Event: Add new events to the system.
2. Start Sales: Open ticket sales for specific events.
3. View Customers: See a list of registered customers.
4. View Vendors: Manage vendor details.

### Vendor Dashboard

Features Available:
1. View available events.
2. Add tickets for events to the inventory.

### Customer Dashboard

Features Available:
1. View and select events.
2. Purchase tickets (added to a queue until ticket sales start).
3. Automatically receive tickets once the admin starts ticket sales.

### API Documentation
Please refer to the detailed API Documentation for comprehensive information on available endpoints, request parameters, and response formats.



Contact
For questions or issues please contact:

Pansilu Perera: [pansilu.20230075@iit.ac.lk]