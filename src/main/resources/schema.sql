CREATE TABLE real_ride_event (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rideID INT,
    driverID INT,
    passengerID INT,
    pickupTime TIMESTAMP,
    pickupLocation VARCHAR(255),
    arrivalTime TIMESTAMP,
    arrivalLocation VARCHAR(255),
    status VARCHAR(50)
);
