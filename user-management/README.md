# User Management Service - Documentation 

## 1. Overview

User Management microservice is responsible for the following user related operations: 
<br/>
Registration, GET all users, GET user by email, GET user by ID, Login and verify the incoming HTTP requests and attach user to them. <br/>


## 1. Key Technical Features
1. Strongly Typed backend, leveraging the power of TypeScript, NestJS and Nodejs
2. DDD style clean architecture
3. Authentication and authorization system using JWT & Passport
4. Abstract Repository Pattern in-the-box for simple data-access and abstract away data access logic.
5. Pino and Loki for Logging in Grafana
6. Prometheus for monitoring in Grafana

## 2. Architecture Overview


## 3. File Tree
apps/
┗ auth/
┃ ┣ src/
┃ ┃ ┣ eureka-client/
┃ ┃ ┣ modules/
┃ ┃ ┃ ┣ auth/
┃ ┃ ┃ ┃ ┣ guards/
┃ ┃ ┃ ┃ ┣ strategies/
┃ ┃ ┃ ┗ users/
┃ ┃ ┃   ┣ applications/
┃ ┃ ┃   ┣ controllers/
┃ ┃ ┃   ┣ domain/
┃ ┃ ┃   ┣ interfaces/
┃ ┃ ┃   ┣ repository/
┃ ┃ ┃   ┣ services/
┃ ┃ ┃   ┗ users.module.ts
┃ ┃ ┣ app.module.ts
┃ ┃ ┗ main.ts

infrastructure/
┣ src/
┃ ┣ config/
┃ ┃ ┣ config.module.ts
┃ ┃ ┣ database.config.ts
┃ ┃ ┣ index.ts
┃ ┃ ┣ typeorm.config.ts
┃ ┃ ┗ validation.schema.ts
┃ ┣ constants/
┃ ┣ database/
┃ ┃ ┣ abstract.entity.ts
┃ ┃ ┣ abstract.repository.ts
┃ ┃ ┣ database.module.ts
┃ ┃ ┗ index.ts
┃ ┣ migrations/
┃ ┣ public/
┃ ┣ swagger/
┃ ┗ index.ts

## 4. Quick Start
To Test this service independently, <br/>

Run the service: <br/>
```bash 
Docker-compose up
```

## 5.. Usage
After spinning up the docker image. Head to http://localhost:4000/api-docs where the API documention exists. <br/> 
Under each route, you will find details such as what is the body or the param needed to perform the respective API operation as well as the structure of the expected result. <br/> <br/>

### Usage Flow
1. register
2. login
- then test the rest of the API ops. 
## 6. Within the scope of this microservice and for testing purposes: 

### The Nestjs app and the Grafana Tools under the following URLS :
- NestJS App: http://localhost:4000
- Swagger: http://localhost:4000/api-docs
- Prometheus: http://localhost:9090/
- Grafana: http://localhost:3000/

## 7. At this moment, you need to manually register both Loki and Prometheus in Grafana, and as follows:  <br/>

**For Loki** : <br/>
1. Head to **Data source** 
2. Add new data source
3. Choose Loki
4. In the **Connection** section, enter our Local Loki URL : http://loki:3100 
5. Hit **Save & Test**
6. Create a Dashboard for Loki Logs
7. Under **Label filter** choose env => development 
8. Hit refresh button. Logs will appear

<br/>

**For Prometheus**: <br/>
1. Head to **Data source** 
2. Add new data source
3. Choose Prometheus.
4. In the **Connection** section, enter our Local Prometheus URL: http://prometheus:9090 
5. Hit **Save & Test**
6. To create a Dashboard to visualize Prometheus metrics coming from a nodejs app: 
- Go to **Dashoards** in the left panel. 
- Click on **New** => import => paste in **11159** which is the ID of the Node.js Dashboard.
7. You should now see rich visuals of the app. 

