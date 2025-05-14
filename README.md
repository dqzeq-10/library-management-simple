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
- **Microsoft SQL Server**: For production database.

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
├── init-database-mssql.sql
├── pom.xml
└── README.md
```

## Getting Started

1. **Clone the repository**:
   ```
   git clone <repository-url>
   cd library-management
   ```

2. **Database Setup**:
   - For SQL Server: Use the `init-database-mssql.sql` script to create and populate the database.
   - The script creates these tables: Books, Members, Loans, Staff, and Fines.
   - It also sets up triggers for updating book availability on loans and returns.
   - Sample data is included for testing.

3. **Run the application**:
   ```
   ./mvnw spring-boot:run
   ```

4. **Access the application**:
   Open your web browser and go to `http://localhost:8080`.

## Database Configuration

The application supports two database options:

### H2 In-Memory Database (Development/Testing)
Configure the H2 database settings in the `src/main/resources/application.properties` file.

### Microsoft SQL Server (Production)
To use SQL Server:
1. Execute the `init-database-mssql.sql` script in SQL Server Management Studio
2. Update your `application.properties` with SQL Server connection details:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=QuanLyThuVien;encrypt=false
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
spring.jpa.hibernate.ddl-auto=none
```

## SQL Script Details

The `init-database-mssql.sql` script:
- Creates the QuanLyThuVien database
- Sets up 5 tables (Books, Members, Loans, Staff, Fines)
- Creates triggers for managing book inventory
- Populates tables with sample data
- Includes examples of overdue loans for testing fine calculation

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue for any suggestions or improvements.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.