# Cinema Management System

## Overview

The **Cinema Management System** is a Java application that simulates the operations of a real-world cinema. 
The system allows customers to view movie listings, book tickets, manage profiles, order food and drinks, and manage reservations. 
The project utilizes Object-Oriented Programming (OOP) principles and is designed for easy scalability and maintainability.

## Design
The system is built using several classes to simulate different entities within a cinema environment:
### Key Classes:
- **Customer**: Manages customer data such as name, email, phone, and transaction history.
- **Movie**: Represents the movie entity with attributes like title, duration, and genre.
- **Showtime**: Handles movie showtimes and manages available seating for different showtimes.
- **Ticket**: Represents a movie ticket, including seat type, age-based pricing, and reservation status.
- **FoodAndDrink**: Represents the menu items that can be ordered in the cinema, including pricing.
- **CinemaUI**: User interface class that facilitates interactions between the user and the system, handling input/output operations.
- **Transaction**: Handles the purchase of tickets and food items, and manages transaction records.
- **Theater**: Represents a cinema theater, which includes information like available seating, movie listings, and the theater's food and drink menu.
- **Cinema**: Core class of the system that contains the theaters, manages showtimes, and processes customer interactions.

### Additional classes and enums include:

- **Person**: (`Abstract`) class that provides basic information like name and email for inheriting classes (such as `Customer`).
- **SeatType**: (`Enum`) for managing seat categories (`Regular, VIP, Premium`) with associated prices.
- **AgePricing**: (`Enum`) that defines age-based pricing (`Adult, Child, Senior`).
- **Reservable**: (`Interface`) that enforces reservation-related methods (`reserve()` and `cancelReservation()`) (for `Ticket`).
- **Billable**: (`Interface`) that enforces billing-related methods (`processTransaction()` and `printReceipt()`) (for `Transaction`).

## Installation Instructions
1. **Clone the repository from GitHub:**
```bash
git clone https://github.com/nmhp16/2024-CS151-07-Cinema_Mangement_System
```
2. **Navigate into the project directory:**
```bash
cd 2024-CS151-07-Cinema_Mangement_System
```
3. **Compile the Java files:**
```bash
javac -d bin src/*.java
```
4. **Run the application:**
```bash
java -cp bin src/Main
```

## Usage
- **Customer Management**:
  - Add, update, and view customer profiles.
  - Validate phone numbers and manage transaction history.
  - Profile management allows updating name, email, and phone details.
  - View current and past movie reservations with the ability to update and refund

- **Movie Management**:
  - View available movies and showtimes for specific theaters.
  - Handle movie-related exceptions (`MovieNotFoundException`).
  - Includes movie details like genre, duration, and ratings.

- **Showtime Management**:
  - View and select available showtimes for a given movie.
  - Filter showtimes by movie and theater.
  - Seat selection based on seat types (`Regular`, `VIP`, `Premium`).

- **Ticket Reservation**:
  - Reserve tickets based on seat availability and age-based pricing.
  - Book and cancel ticket reservations and view seat availability.
  - Handle ticket-related exceptions (`ReservationException`).

- **Food & Drinks Ordering**:
  - Order food and drinks from the theater's available menu.
  - Add selected items to the cart and include them in the final transaction.

- **Transaction Management**:
  - Process and complete transactions, including both ticket purchases and food orders.
  - View detailed transaction history for each customer, including tickets and items ordered.
  - Handle exceptions (`TransactionException`).

- **Theater Management**:
  - Manage multiple theaters, each with its own unique set of showtimes and seating configurations.
  - Access theater details such as name, location, and available amenities.
  - Ensure that showtimes are correctly associated with the respective theater.

- **Cinema Management**:
  - Central management of theaters and movie listings within the cinema.
  - Handle the addition and removal of movies and theaters as needed.
  - Support for various operations such as updating showtimes and managing seat availability across different theaters.

## Contributions

This project was developed by the following team members:

- **Aaron Mundanilkunathil**: Implementation of key features within the `Cinema`, `Customer`, `FoodAndDrink`, `Movie` classes. Worked on JUnit tests to ensure functionality of these components.
- **Sunny Doan**: Focused on developing the `CinemaUI` class and its methods, verified edge cases and ensure effective user input handling.
- **Ania Niedzialek**: Contributed to on features for `Showtime`, `Theater`, `Ticket`, `Transaction` class, created UML Class Diagram for visualization of system architecture, worked on JUnit tests to verify features correctness. 
- **Nguyen Pham**: Collaborated on developing `CinemaUI` methods and verified edge cases. Project integration and debugging. 

