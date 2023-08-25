# Store API

This project provides a Spring Boot-based API for managing bills and calculating discounts based on customer types.

## Project Information
- Name: Store API
- Description: API for calculating discounts on bills
- Java Version: 1.8
- Framework: Spring Boot 3.1.0

## Controller

The main controller in this project is `BillsController`, which handles HTTP requests related to bill calculations and discounts. The following endpoints are available:

- `POST /api/v1/bills/payable`: Calculates discounts and payable amount for a given bill.

### Calculate Payable Amount

Calculate the payable amount for a bill with applied discounts.

**URL:** `/api/v1/bills/payable`
**Method:** POST
**Request Body:** Bill details in JSON format.

**Response:**
- Status Code: 200 OK
- Body: Calculated discounts, original amount, and new amount in JSON format.

## Models

### Bill

Represents a bill for which discounts are calculated.

- `id`: Bill ID.
- `name`: Name associated with the bill.
- `amount`: Total bill amount.
- `customerType`: Customer type associated with the bill.

### BillResponse

Represents the response after calculating discounts for a bill.

- `originalAmount`: Original bill amount.
- `appliedDiscounts`: Map of applied discounts.
- `newAmount`: Amount payable after applying discounts.

### ValidationErrorResponse

Represents a response for validation errors.

- `errorType`: Type of validation error.
- `errorMessage`: Error message describing the issue.
- `timeStamp`: Timestamp of when the error occurred.
- 
## Running the Code and Tests

1. Make sure you have Java 1.8 installed on your system.
2. Clone this repository and navigate to the project root directory.
3. Build the project using `./mvnw clean package` command.
4. Run the application using `./mvnw spring-boot:run` command.
5. Access the API on port 8080.
   
## Running Docker image

1. use command `docker pull awssaleh/store-api:latest` to get the image from docker hub. 
2. use command `docker run -p 8080:8080 --name store-api awssaleh/store-api` 
3. Access the API on port 8080.

## Generating Coverage Reports

1. After building the project, the reports are generated.
2. Coverage reports will be generated in ../storeAPI/target/site/jacoco/index.html

## Development Notes
1. Achieved code coverage 91%, jacoco report is included in the repo.
2. Only unit testing was implemented and not integration testing to save time.
3. No database was used, as it was not mentioned or hinted at in the assessment report "Technology" section.
4. Strategy design pattern was used, as it seemed appropriate and allows for easy future modification and expansion.
5. As i worked within a time crunch please excuse the few corners that were cut during the development.
6. A UML diargram is uncluded in the repo with the name UML.jpg.
7. Even though the deadline for the task was 24/8 , please note that i did some modifcations on the repo a day after , fixing some bugs in the pipline.
