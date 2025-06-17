# Smart City Energy Management

Related Documents can be found on this page [Wiki](https://github.com/BhanuPDas/smart-city-energy-management/wiki/Smart-City-Energy-Management-Project-Documentation)

## Guidelines to Start Smart City Energy Management Application

1) Clone the GitHub Repo - branch "main"
2) Run the Docker compose file (command: docker compose up) in the root folder. This command will fetch all images from Docker Hub container registry, including the database image required by the application.
3) Once all Docker containers spin up, please verify the applications on Eureka - http://localhost:8761/ (This page should show 5 applications registered.)

**Please Note: All application images are compiled and created on a Mac - ARM64 CPU architecture, and may not be supported by other architectures .**
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
6) Dummy data are included in the script and dumped in DB at the startup to help in testing. No separate sql are needed to be executed.
7) Sample User for testing: 
    - Jon_Doe@Gmail.com/123 (username/pwd, role - city planner)
    - bhanu@test.com/123 (username/pwd, role - city planner)
    - rahul_new@test.com/123 (username/pwd, role - citizen)
    - jon_rose@test.com/123 (username/pwd, role - citizen)
8) On Consumption Report page, please scroll down to see the results. It will be displayed below the form on same page. <br/>

9) Grafana dashboards have been integrated and can be accessed at: **http://localhost:3000/**
7) Grafana dashboards have panels to show request throughput(Number of requests executed per second) and application logs of backend services. By default, Prometheus datasource is selected for request throughput; switch to the Loki datasource to view logs.
8) On logging in for the first time, Grafana asks to change the password. Default user/pwd: admin/admin

### Usage
1. City_Planner: <br/>
- Click on *Add/Edit Resident Energy Details*
  - by flling in the form, you are associating the entered details with the respective citizen ( **Owner's Email** field).
  - ***NOTE*** Start Date & End Date should be in the past.
- Now navigate back to homepage and click on View *Consumption Reports*
  - enter the citizen's email, and their respective details to generate the analytics report of the respective citizen.
  - sample data for testing:
    - Owner's Email: jon_rose@test.com
    - City: Dortmund
    - Zip Code: 44323
    - Start Date: 2024-02-01
2. Citizen: <br/>
- Click on *Add/Edit Energy Details* to add or edit existing user's energy details.
  - ***NOTE*** Start Date & End Date should be in the past.
- Click on *View Consumption Report* 
  - Fill in the form with citizen's details.
