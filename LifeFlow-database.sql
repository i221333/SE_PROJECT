create database LifeFlow;

use LifeFlow;

CREATE TABLE User (
  userID INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role ENUM('Admin', 'Donor', 'Patient') NOT NULL
);

CREATE TABLE Admin (
  adminID INT AUTO_INCREMENT PRIMARY KEY,
  userID INT NOT NULL,
  FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE
);

CREATE TABLE Donor (
  donorID INT AUTO_INCREMENT PRIMARY KEY,
  userID INT NOT NULL,
  bloodGroup VARCHAR(3) NOT NULL,
  FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE
);

CREATE TABLE Patient (
  patientID INT AUTO_INCREMENT PRIMARY KEY,
  userID INT NOT NULL,
  bloodGroup VARCHAR(3) NOT NULL,
  FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE
);

CREATE TABLE Appointment (
    appointmentID INT AUTO_INCREMENT PRIMARY KEY,
    donorID INT NOT NULL,
    appointmentDate TIMESTAMP NOT NULL,
    status ENUM('Scheduled', 'Completed', 'Cancelled') DEFAULT 'Scheduled',
    FOREIGN KEY (donorID) REFERENCES Donor(donorID) ON DELETE CASCADE
);

CREATE TABLE Request (
    requestID INT AUTO_INCREMENT PRIMARY KEY,
    patientID INT NOT NULL,
    bloodGroup VARCHAR(3) NOT NULL,
    quantityNeeded INT NOT NULL,
    requestDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reason VARCHAR(255),
    requestType ENUM('Urgent', 'Scheduled') NOT NULL,
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    FOREIGN KEY (patientID) REFERENCES Patient(patientID) ON DELETE CASCADE
);

CREATE TABLE Inventory (
    bloodGroup VARCHAR(3) PRIMARY KEY,
    quantity INT NOT NULL
);

ALTER TABLE Appointment MODIFY COLUMN status ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED') DEFAULT 'SCHEDULED';
ALTER TABLE Request MODIFY COLUMN status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING';
ALTER TABLE Request MODIFY COLUMN requestType ENUM('SCHEDULED', 'URGENT');

-- Users
INSERT INTO User (name, email, password, role) VALUES
('Alice Admin', 'alice@admin.com', 'pass123', 'Admin'),
('Bob Donor', 'bob@donor.com', 'pass123', 'Donor'),
('Charlie Donor', 'charlie@donor.com', 'pass123', 'Donor'),
('Diana Donor', 'diana@donor.com', 'pass123', 'Donor'),
('Eve Patient', 'eve@patient.com', 'pass123', 'Patient'),
('Frank Patient', 'frank@patient.com', 'pass123', 'Patient'),
('Grace Patient', 'grace@patient.com', 'pass123', 'Patient'),
('Henry Admin', 'henry@admin.com', 'pass123', 'Admin'),
('Ivy Donor', 'ivy@donor.com', 'pass123', 'Donor'),
('Jack Patient', 'jack@patient.com', 'pass123', 'Patient');

-- Admins (userID assumed from 1 and 8)
INSERT INTO Admin (userID) VALUES
(1), (8);

-- Donors (userID 2,3,4,9)
INSERT INTO Donor (userID, bloodGroup) VALUES
(2, 'A+'),
(3, 'B-'),
(4, 'O+'),
(9, 'AB+');

-- Patients (userID 5,6,7,10)
INSERT INTO Patient (userID, bloodGroup) VALUES
(5, 'A+'),
(6, 'O-'),
(7, 'B+'),
(10, 'AB-');

-- Appointments (donorID 1-4)
INSERT INTO Appointment (donorID, appointmentDate, status) VALUES
(1, '2025-05-01 10:00:00', 'Scheduled'),
(2, '2025-04-28 09:00:00', 'Completed'),
(3, '2025-04-30 12:00:00', 'Cancelled'),
(4, '2025-05-02 15:00:00', 'Scheduled'),
(1, '2025-04-20 11:30:00', 'Completed');

-- Requests (patientID 1-4)
INSERT INTO Request (patientID, bloodGroup, quantityNeeded, requestDate, reason, requestType, status) VALUES
(1, 'A+', 2, '2025-04-25 14:00:00', 'Surgery', 'Urgent', 'Pending'),
(2, 'O-', 1, '2025-04-26 10:30:00', 'Accident', 'Urgent', 'Approved'),
(3, 'B+', 3, '2025-04-27 13:45:00', 'Routine Checkup', 'Scheduled', 'Rejected'),
(4, 'AB-', 2, '2025-04-28 09:15:00', 'Emergency', 'Urgent', 'Pending'),
(1, 'A+', 1, '2025-04-29 16:00:00', 'Follow-up', 'Scheduled', 'Approved');

-- Inventory
INSERT INTO Inventory (bloodGroup, quantity) VALUES
('A+', 0),
('A-', 0),
('B+', 0),
('B-', 0),
('AB+', 0),
('AB-', 0),
('O+', 0),
('O-', 0);
