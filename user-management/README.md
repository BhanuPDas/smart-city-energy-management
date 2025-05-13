# User Management App Documentation 
<br/> <br/>
Version 1
<br/> <br/>
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
After spinning up the docker image. Head to http://localhost:3000/api-docs where the API documention exists. <br/> 
Under each route, you will find details such as what is the body or the param needed to perform the respective API operation as well as the structure of the expected result. <br/> <br/>

### Usage Flow
- register
- then test the rest of the API ops. 

