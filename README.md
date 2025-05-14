# Library Management System

This project is a simple Library Management System built using Spring Boot. It provides functionalities to manage books, members, loans, staff, and fines in a library.

## Features

- **Book Management**: Add, edit, delete, and view books in the library.
- **Member Management**: Add, edit, delete, and view library members.
- **Loan Management**: Manage loans of books to members, including due dates and return dates.
- **Staff Management**: Manage library staff information.
- **Fine Management**: Manage fines associated with overdue loans.

## Technologies Used

- **Spring Boot**: For building the application.
- **Spring Data JPA**: For database interactions.
- **Thymeleaf**: For rendering HTML templates.
- **H2 Database**: For easy testing and development.

## Project Structure

```
library-management
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── librarymanagement
│   │   │               ├── controller
│   │   │               ├── model
│   │   │               ├── repository
│   │   │               ├── service
│   │   │               └── LibraryManagementApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
├── pom.xml
└── README.md
```

## Getting Started

1. **Clone the repository**:
   ```
   git clone <repository-url>
   cd library-management
   ```

2. **Run the application**:
   ```
   ./mvnw spring-boot:run
   ```

3. **Access the application**:
   Open your web browser and go to `http://localhost:8080`.

## Database Configuration

The application uses an H2 in-memory database for development and testing. You can configure the database settings in the `src/main/resources/application.properties` file.

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue for any suggestions or improvements.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.