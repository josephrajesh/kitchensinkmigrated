# Migrated Kitchensink application
Application is split into separate services for UI and backend and both are in same project.  
Clone Project from this repo and do a mvn clean compile
## Setup UI.
### Prerequisites
Node recommends V21.0.0  
npm recommends V10.2.0 
#### Install and Start
* cd to src/main/frontend folder
* run 'npm install' to install all dependencies 
* run 'npm start' to start the UI using node js
* Application should open browser at http://localhost:3000/ if not open in your browser
## Setup Backend
* This application uses Java 21, Springboot, and MongoDB in the backend
* This application can be tested using swagger
### Prerequisites
##### Java 
* Java 21, recommends to use open source azul.
* Set JAVA_HOME env variable as it may be needed for maven build
##### IDE for development 
* Recommendation IntelliJ with AI plugin (Amazon Q)
##### Maven - recommendation Apache Maven 3.9.9
* To build, can use build in IDE Maven
* Or build command line using 'mvn clean package'
##### MongoDB
* Download the zip file from mongodb
* unzip the downloaded zip file. 
* Create a directory/folder for data
* Start MongoDB using './bin/mongod -dbpath=./data/db' command 
* Install Mongo Compass UI for managing database
* Create Database 'kitchensink' and collection 'members' and insert below one document 
* {
"_id": 1,
"name": "John Smith",
"email": "john.smith@mailinator.com",
"phone_number": "2125551212"
}
### Start Application
##### Command line 
* build using 'mvn clean  package' command 
* run from the folder the jar file created using 'java -jar kitchensinkmigrated-0.0.1-SNAPSHOT.jar' command
##### IDE 
* Run KitchensinkMigratedSpringbootApplication by opening it or adding to run config, or using IDE config
#### Test Application
Use swagger endpoint to test and you should see one document you added   
http://localhost:8081/kitchensinknew/swagger-ui/index.html#/member-controller/getAllMembers