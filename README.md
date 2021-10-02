# Assignment-game

  This is a game with client react app and java spring boot app using kafka and mongodb
  
# Instructions to run the application.
  
  1. Download the source folder or git clone using the url [assignment-game](https://github.com/udesh/assignment-game.git) 
  2. You need to have Kafka and MongoDB running to setup these. You can use the docker-compose to run them.
  3. Get your terminal app and go inside assignment-game folder, run, 
     ```
     docker-compose up --build
     ```
     It'll start kafka with zookeeper and mongodb. Now you have kafka and mongo running.. 🎉
  5. Now let's start the backend server. 
     Go to game-server folder and run 
     ```
     mvn clean install
     ````
     The test build connect to kafka and mongodb on the run them. If you need to skip them run `mvn clean install -DskipTests`. Now you have the spring boot app          ready to run mode.
  7. Then you need to create db in mongo DB. You can use any mongo DB client to connect to mongodb. If you prefer ui - [Robo 3T](https://robomongo.org/) .
     Now create the DB called 
     ```
     game
     ```
  6. Let's start the spring boot app, run this command inside game-server
     ```
     mvn spring-boot:run
     ```
     It will start the spring boot app and you would get the server running on `http://localhost:8080/`. :tada:
  8. Go to game-client folder and run these commands to run your react web app on `localhost:3000`.
     ``` 
     npm install 
     npm start
     ```
     Now the web app is running on `http://localhost:3000` and you can start to play. 🎉 🎉
     
     
# Development steps followed.

1. Identify the requirements.
2. Create diagram to identify the domains and connections between them.
3. Sketch the domains and relationships among them.
4. Identify the sequence of the steps in the game.
5. Decide the technologies and concepts to follow. Mainly DDD is followed.
6. Game is the main domain I've used in modeling the use case.
7. Identify the test cases and test scenarios and implement them.
8. Implementation.

# Techologies and concepts followed

1. Domain Driven Design concepts. CQRS, event sourcing and event driven design approach.
2. Microservices design and flexibilyt to structure the implementation to such applications
3. REST api, Kakfa messading and web socket.
4. Mongo DB as the db.
5. Flexibilty to containerise the apps using docker.
  
