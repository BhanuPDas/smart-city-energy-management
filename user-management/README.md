# User Management App Documentation 
<br/>
Version 1
<br/>
## 1. Overview

This User Management microservice is responsible for user related operations such as: 
<br/>
Registration, GET all users, GET user by email, GET user by ID, UPDATE user by ID, DELETE user by ID. <br/> <br/>
Login, Logout <br/>
Verify the incoming HTTP requests and attach user to them.<br/>

## 2. Quick Start
Run the service: <br/>
```bash 
Docker-compose up
```

## 3. Usage
After spinning up the docker image. Head to http://localhost:4000/api-docs where the API documention exists. <br/> 
Under each route, you will find details such as what is the body or the param needed to perform the respective API operation as well as the structure of the expected result. <br/> <br/>

### Usage Flow
- register
- then test the rest of the API ops. 

## 4. Within the scope of this microservice and for testing purposes: 

### Exist the Nestjs app and the Grafana Tools under the following URLS :
- NestJS App: http://localhost:4000
- Swagger: http://localhost:4000/api-docs
- Prometheus: http://localhost:9090/
- Grafana: http://localhost:3000/

## 5. At this moment, you need to manually register both Loki and Prometheus in Grafana, and as follows:  <br/>

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

