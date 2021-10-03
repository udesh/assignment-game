# ⚽ Assignment-game ⚾

  This is a game with client react app and java spring boot app using kafka and mongodb. Spring boot has a REST API and a web socket to connect with the client app.
  
  
# 🏏 Instructions to run the application. 🎾
  
  1. Download the source folder or git clone using the url [assignment-game](https://github.com/udesh/assignment-game.git) 
  2. You need to have Kafka and MongoDB running to setup these. You can use the docker-compose to run them.
  3. Get your terminal app and go inside assignment-game folder. run, 
     ```
     docker-compose up --build
     ```
     It'll start kafka with zookeeper and mongodb. Now you have kafka and mongodb running.. 🎉
  5. Now let's start the backend server. 
     Go to the game-server folder and run 
     ```
     mvn clean install
     ````
     The test build connects to kafka and mongodb on run. If you need to skip them run `mvn clean install -DskipTests`. Now you have the spring boot app          ready to run mode.
 
  6. Let's start the spring boot app, run this command inside game-server
     ```
     mvn spring-boot:run
     ```
     It will start the spring boot app and you would get the server running on `http://localhost:8080/`. :tada:
     
     Mongo DB name which would automatically create - `game`
     
  7. Go to game-client folder and run these commands to run the react web app on `localhost:3000`.
     ``` 
     npm install 
     npm start
     ```
     Now the web app is running on `http://localhost:3000` and you can start to play. 🎉 🎉
     
     
# 🏓 Development steps followed. 🏒

1. Identify the requirements.
2. Create diagram to identify the bounded contexts, domains and relationship between them.
3. Sketch the domains and relationships among them.
4. Identify the sequence of the steps in the game.
5. Decide the technologies and concepts to follow. Mainly DDD is followed.
6. Game is the main domain I've used in modeling the use cases.
7. Identify the test cases and test scenarios and implement them.
8. Implementation.
9. Testing.
10. Fixes and enhancements.

# ⛷️Technologies and concepts followed 🎮

1. Domain Driven Design concepts. CQRS, event sourcing and event driven design approach.
2. Microservices design and flexibility to structure the implementation to such applications.
3. REST api, Kakfa messaging and web socket.
4. Mongo DB as the db.
5. Flexibility to containerise the apps using docker.

# 🏀 Implementation details 🏐

I’ve identified three bounded contexts. As accordingly the folder structure was created.
### 1. Application
Server connectivity related classes are added in this context.
### 2. Game
Game related logic and domain models, repository classes are in this folder.
### 3. Action
Client application user action related context.

**These separated contexts give the flexibility to change. As an example we can create seperate micro service applications and connect them through kafka event streams.

### Application

* This contains the game controller, Kafka  producer to send message to topic, Kafka consumer to consume topic messages, and websocket.
* The separated contexts give the flexibility to change. As an example if we want to replace web socket and REST API with gRPC we can easily do it.
* It has infrastructure configuration classes in a separate folder. 
* When sending messages to the Kafka topic it was implemented in a separate thread to handle it gracefully.


### Game

* In this package the Game and GameTurn are the domains used to model. 
* Game is the aggregate root class around which all the logic is created.
* GameService contains all the logic related to Game and GameTurn. This is the core of the game logic rules.
* Domain classes are built without any coupling. 
There are separate repositories for write and read data to Mongodb. CommandRepository to write, QueryRepository to read. Commands Query segregation is applied like that. This gives the flexibility to separate them and create event sourcing.
* Only Service classes use repository classes.
* GameServiceTest has all the tests for GamerService.
* When running them, the Kafka server needs to be running.

### Action

* This contains the LoginPlayer and PlayerAction models. 
* There are separate repositories for write and read data to db. CommandRepository to write and QueryRepository to read. Commands and Query responsibilty segregation is applied like that. This gives the flexibility to separate them and create event sourcing if needed.
* All the user actions are saved in the DB. Flexible to use as an event store, easily upgradable to more extensive version of event sourcing and CQRS.

#### Properties can be set

These can be set on application.properties file on game-server.

```
game.start.minimum.value=10
game.start.maximum.value=100
```
It indicates the number range of the game start random number.

```
game.divideBy.value=3
```
This indicates the divide by value.

### Game Flow

Login

``Login button press`` --> ``get the request to server`` --> ``validate and save login data`` --> ``create the game`` --> ``send the message to Kafka topic through producer`` --> ``Kafka consumer get the message`` --> ``write to websocket`` --> ``client show the message on client game panel.``

## Game Scenarios.

### Both players are set to play without any input 

1. Both players are automatic players - 
2. Player 1 enters name, proceeds with default Auto checked configuration.
3. Player 2 enters name, proceeds with default Auto checked configuration.
4. Player 1 presses Start.
5. The results are shown in the game panel.

https://user-images.githubusercontent.com/468711/135754456-03719377-c9bf-4948-9d4a-6cfc89286c79.mov

### One player set to play automatically, and the other with user input

1. Player 1 - Auto and Player 2 - Manual
2. Player 1 enters name, proceeds with default Auto checked configuration.
3. Player 2 enters the name, proceeds with Manual checked configuration.
4. Player 1 presses Start.
5. Player two selects one of these { 0, -1, 1 }
6. The results are shown in the game panel.


https://user-images.githubusercontent.com/468711/135754878-9fc43b26-ca4f-4706-9c86-037b621a7ee8.mp4



### Both players set to play with user input

1. Player 1 and Player 2 both select Manual
2. Player 1 and Player 2 enter one of these { 0, -1, 1 }
3. Game panel show details.



https://user-images.githubusercontent.com/468711/135755270-d8745cd5-15a2-4347-82aa-6dd0d63e7acc.mp4



Thank you 🙏 ! for reading and your feedback and suggestions are welcome..! 🙏 🙏
