# Ticket Booking Project
## 1. How to run
### A. Build using Maven

We need to build the uber jar file using following commands:
```
mvnw clean package -DskipTests
mv /target/*.jar app.jar
```
After that we need to pass necessary database secret via environment variable before running the jar file:
```
MYSQL_DATABASE=db \
MYSQL_PASSWORD=pass \
MYSQL_USER=user \
MYSQL_HOST=localhost:3306 \
java -jar app.jar
```
Or you can set the environment variable value in your shell. 

### C. Build using Docker
Before run we need to prepare a `.env` file in root projec folder that contains db secret. Example:
```
MYSQL_DATABASE=db
MYSQL_PASSWORD=pass
MYSQL_ROOT_PASSWORD=rootpass
MYSQL_USER=user
MYSQL_HOST=localhost:3306
```
After that, we only need to run docker compose using command:
```
docker compose up --build
```

## 2. Technical design
Based on requirements, we need to implement these use cases:
1. As a user i need to search available concerts. 
2. As a user i can book a ticket according to selected concert within specific hour, with limited number of tickets.

The goals of this repository is to focus on ticket booking mechanism, so we need limit the scope of the features. We 
will omit:
1. Authentication
2. Caching
3. Non important data such as performer, geolocation, tagging, etc.

This code implement basic data retrieval for concert and booking data. The details will be explained in next chapters.

We will add these constraints to improve usability during booking and searching:
1. Generalize concert data into `event`, separate location into `venue` data.
2. Each user may books more than 1 ticket, the data will called `booking`.
3. Each `event` has maximum booking limit for each user, to prevent abusement.
4. Each `event` has booking time duration, separated from actual event duration.

For the booking mechanism we will using this algorithm:
1. Check if user and event (concert) exist. If exists then continue.
2. Check if event ticket still available, booked ticket must be less than ticket limit. If available, continue.
3. Check if: `total user previous ticket + total new ticket request <= event ticket limit` . If true, continue.
4. Check if: `total user previous ticket + total new ticket request <= per user limit` . If true, continue.
5. Create `booking data` and store it to database.

## 3. Entity diagram
Here is the overview of database entity diagram:

![Entity Diagram](./docs/entity-diagram.jpg)

Basically the idea are:
1. Concert is implemented as `event` and each event performed on a location called `venue`.
2. Each user may book 1 or more tickets for each event, each successfully booked ticket has its own data in database.
3. Each booked ticket data stored in `booking` table.
4. An `event` contains the detail of the event, ticket price & limit, when it is conducted, and when it is can be booked.
5. This repository is focused on booking implementation, so we only store basic user account in `user` table
6. `venue` data mainly used for locating and limit events location. Only basic location details implemented.

## 4. API

## 5. Further ideas
1. We can improve concerts search performance by using dedicated search engine such as Elasticsearch and Manticore.
Current implementation using SQL LIKES with both prefix and suffix, which is slow for large data. By using dedicated
search engine we can implement complex searching such as: tagging search, ignore case sensitivity, search by venue, etc.
2. Implement more complex location for locating concerts. Add province, online maps coordinate, etc. Also we need to
standardize all city and country data, and store it into its own database table or save it into a static JSON file. 
3. Add caching mechanism for storing frequent search, store the cache in in-memory storage such as redis and memcache.
4. Implement payment transaction, connect `booking` data with invoice and others detail. The `status` column of `booking`
can be changed into `PAID` when user paid the transaction.
5. Add countdown for each booked ticket, maybe 30 minutes. This will encourage users to paid the ticket immediately.
when countdown is zero, the booking will be cancelled, and it will give chance to other users to create new booking.
To implement this we will create a basic worker in backend that will delete `booking` data, if the booking time exceeded
30 minutes.
6. For security purpose, we need to implement authentication to all API . Implement password system, and use JWT token
to interact between client (mobile, web, etc) and backend API. Also we need to implement rate limiting in API gateway
(nginx, kong, etc) to prevent brute force requests from script/bot during ticket booking war.