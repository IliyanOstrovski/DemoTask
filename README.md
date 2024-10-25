Регистър на физически лица
Overview

Регистър на физически лица (Register of Individuals) is a REST API for managing individual records, including personal information fields such as first name, middle name, last name, unique personal ID number (EGN), and age. The API includes input validation to ensure data integrity, and all endpoints are documented using OpenAPI 3.0 in Swagger UI.
Version

Current version: 1.0
API Documentation

API documentation is available through Swagger UI, providing descriptions, input requirements, and validation rules for each endpoint.

    Swagger UI URL: http://localhost:8080/swagger-ui.html (for local development)

Entity Model

The individual records are modeled with the following structure and validations:

    ID: Unique identifier, auto-generated.
    firstName: Required; must contain only letters and hyphens; no numbers allowed.
    middleName: Required; must contain only letters; no numbers allowed.
    lastName: Required; must contain only letters; no numbers allowed.
    egn: Unique; exactly 10 digits.
    age: Positive integer.

Features

    Register Individuals: Create a new entry with personal information.
    Retrieve Records: Fetch individual details by unique ID or criteria.
    Update Records: Modify an individual’s information.
    Delete Records: Remove individual entries from the registry.

Setup
Prerequisites

    Java 11 or higher
    Maven for dependency management

Installation

    Clone the repository:

    bash

git clone https://github.com/yourusername/register-of-individuals.git
cd register-of-individuals

Build the project:

bash

mvn clean install

Run the application:

bash

    mvn spring-boot:run

    Access the API Documentation:
        Swagger UI: http://localhost:8080/swagger-ui.html

Usage
Endpoints

    POST /api/individuals: Register a new individual.
    GET /api/individuals/{id}: Retrieve details of an individual by ID.
    PUT /api/individuals/{id}: Update an individual's information.
    DELETE /api/individuals/{id}: Delete an individual's record.

Example Request
Register a New Individual

http

POST /api/individuals
Content-Type: application/json

{
  "firstName": "Иван",
  "middleName": "Петров",
  "lastName": "Иванов",
  "egn": "1234567890",
  "age": 30
}

Response

json

{
  "id": 1,
  "firstName": "Иван",
  "middleName": "Петров",
  "lastName": "Иванов",
  "egn": "1234567890",
  "age": 30
}

Input Validation

The API enforces the following validations:

    firstName, middleName, lastName: Must contain only Cyrillic or Latin letters; no numbers allowed.
    egn: Must be a unique 10-digit number.
    age: Must be a non-negative integer.

Contributing

    Fork the repository
    Create your feature branch (git checkout -b feature/new-feature)
    Commit your changes (git commit -m 'Add new feature')
    Push to the branch (git push origin feature/new-feature)
    Create a new Pull Request

License

This project is licensed under the MIT License. See the LICENSE file for details.
