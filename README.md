# Receipt Processor API

A lightweight RESTful web service that processes receipts and calculates reward points based on defined business rules.

> 🔧 Built with **Java 17**, **Spring Boot 3.4.4**, and **Docker**.  
> No external database or configuration required — runs entirely in memory.

---

## Features


- `POST /receipts/process` → Submit a receipt and get a unique receipt ID.
- `GET /receipts/{id}/points` → Retrieve the number of points awarded for that receipt.
- All logic is based on a clearly defined point calculation system.

---

##  Run With Docker (No Java or Maven Needed)

> Prerequisite: Make sure Docker is installed on your system, Up and running.

## Below Steps need to be executed from the root folder of the project i.e from /demo 

### Build the project # (For Windows)

bash mvnw.cmd clean package


### Use Below Commands for building in mac

bash chmod +x mvnw

bash mvn clean package

### Build the Docker Image #

docker build -t receipt-processor .


### Run the Docker Container #

docker run -p 8080:8080 receipt-processor

API will be running on http://localhost:8080


## API Testing Instructions

You can test the API using tools like **Postman**, **curl**, or any REST client.

###  POST /receipts/process
**URL**: `http://localhost:8080/receipts/process`  
**Method**: `POST`  
**Headers**:
- `Content-Type: application/json`

**Sample JSON Body**
{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    { "shortDescription": "Mountain Dew 12PK", "price": "6.49" },
    { "shortDescription": "Emils Cheese Pizza", "price": "12.25" },
    { "shortDescription": "Knorr Creamy Chicken", "price": "1.26" },
    { "shortDescription": "Doritos Nacho Cheese", "price": "3.35" },
    { "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ", "price": "12.00" }
  ],
  "total": "35.35"
}


**Sample Success Response**
{
  "id": "e41d67d4-cf48-47bc-a4bd-9155debc2c92"
}


### GET /receipts/{id}/points

GET http://localhost:8080/receipts/e41d67d4-cf48-47bc-a4bd-9155debc2c92/points

**Success Response**

{
  "points": 28
}





