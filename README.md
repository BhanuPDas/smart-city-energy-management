# Smart City Energy Management

Related Documents can be found on this page [Wiki](https://github.com/BhanuPDas/smart-city-energy-management/wiki/Smart-City-Energy-Management-Project-Documentation)

## Guidelines to Start Smart City Energy Management Application

1) Clone the GitHub Repo - branch "main"
2) Run the Docker compose file (command: docker compose up) in the root folder. This command will fetch all images from Docker Hub container registry, including the database image required by the application.
3) Once all Docker containers spin up, please verify the applications on Eureka - http://localhost:8761/ (This page should show 5 applications registered.)

**Please Note: All application images are compiled and created on a Mac ARM CPU architecture, and may not be supported on other architectures .**
[In case required, individual modules/services can be containerized using the Docker file for each service.]

## Enter the URL in the browser for API Docs for reference:

1) Smart City Management UI & Backend APIs: **http://localhost:8500/swagger-ui/index.html**
2) Energy Optimization Service APIs: **http://localhost:8510/swagger-ui/index.html**
3) Energy Analytics Service APIs: **http://localhost:8520/swagger-ui/index.html**
4) Energy Management Service APIs: **http://localhost:8530/swagger-ui/index.html**
5) User Management Service APIs: **http://localhost:4000/api-docs**

## Access Application:

1) Once all containers start, navigate to - **http://localhost:8500/register**
2) The above URL is the starting point for the application. 
3) A user can be registered either as "City Planner" or "Citizen". Accordingly, the UI screens and functionality will differ.
4) After registration, please log in - **http://localhost:8500/login**
5) Please note: At the time of adding a resident building energy source, the owner's email refers to the email address of the owner or the citizen who is the owner of the building, and it has to be a unique email ID.
6) Dummy data is included in the script and dumped in the DB at startup to help in testing. No separate SQLs needed to be executed.
7) Sample User for testing: 
    - Jon_Doe@Gmail.com/123 (username/pwd, role - city planner)
    - bhanu@test.com/123 (username/pwd, role - city planner)
    - rahul_new@test.com/123 (username/pwd, role - citizen)
    - jon_rose@test.com/123 (username/pwd, role - citizen)
8) On the Consumption Report page, please scroll down to see the results. It will be displayed below the form on the same page. <br/>

9) Grafana dashboards have been integrated and can be accessed at: **http://localhost:3000/**
7) Grafana dashboards have panels to show request throughput(Number of requests executed per second) and application logs of backend services. By default, Prometheus datasource is selected for request throughput; switch to the Loki datasource to view logs.
8) On logging in for the first time, Grafana asks to change the password. Default user/pwd: admin/admin

## Usage
1. City_Planner: <br/>
- Click on *Add/Edit Resident Energy Details*
  - By filling in the form, you are associating the entered details with the respective citizen ( **Owner's Email** field).
  - ***NOTE*** Start Date & End Date should be in the past.
- Now navigate back to the homepage and click on View *Consumption Reports*
  - Enter the citizen's email and their respective details to generate the analytics report for the respective citizen.
  - Sample data for testing:
    - Owner's Email: jon_rose@test.com
    - City: Dortmund
    - Zip Code: 44323
    - Start Date: 2024-02-01
2. Citizen: <br/>
- Click on *Add/Edit Energy Details* to add or edit an existing user's energy details.
  - ***NOTE*** Start Date & End Date should be in the past.
- Click on *View Consumption Report* 
  - Fill in the form with the citizen's details.
  

## Description of Services

1. **City Management Service**: This service acts as a Gateway, receives requests from clients, and routes them to the appropriate backend (microservice). For this project, this microservice is also integrated with UI (HTML + Thymeleaf pages) to simulate a client. This service has been developed using Java and the Spring Boot framework. [Developer: **Bhanu Pratap Das**<bhanu.das001@stud.fh-dortmund.de>]
2. **User Management Service**: This backend microservice is responsible for handling User data for registration and login. This microservice is developed using TypeScript. [Developer: **Jaafar Alkaales**<jaafar.alkaales001@stud.fh-dortmund.de>]
3. **Energy Management Service**: This backend microservice is responsible for handling Resident Building and Energy Source data. It creates/persists the data in the DB. It is developed using Java and the Spring Boot framework. [Developer: **Hadis Delbord**<hadis.delbord001@stud.fh-dortmund.de>]
4. **Energy Optimization Service**: This backend microservice provides energy optimization recommendations to the user with the role of "citizen". The microservice fetches the data from the DB, analyzes and provides recommendations based on business logic. It is developed using Java and the Spring Boot framework. [Developer: **Vida Bahrami**<vida.bahrami001@stud.fh-dortmund.de>]
5. **Energy Analytics Service**: This backend microservice provides energy consumption reports for individual resident buildings as well as neighborhoods. A neighborhood can be a zip code or a city. This microservice fetches data from the DB, analyzes and provides a report based on business logic. It is developed using Java and the Spring Boot framework. [Developer: **Amir Pakdel**<Amir.pakdel001@stud.fh-dortmund.de>]

**Note**: All services are containerized using Docker and hosted on Docker Hub (container Registry).

## Additional Tools:

1. Loki: This tool is used for logging. It aggregates logs from all microservices. Can be viewed on Grafana dashboards.
2. Prometheus: This tool scrapes request throughput metrics for observability of the application. In this project, Prometheus scrapes the number of requests executed by the gateway per second. Can be viewed on the Grafana dashboard.
3. Grafana: This tool integrates Loki and Prometheus and provides dashboards for visualization.
4. Additionally, pgAdmin can be used to check data in the DB. (host: localhost, Port: 5432, usr/pwd: admin/password, DB: postgres)
