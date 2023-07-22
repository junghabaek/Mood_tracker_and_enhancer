# Mood_tracker_and_enhancer (Spring boot 2.7.10)
# Project Description

## Objectives:
1. to help users feel better by being more active according to the current state of mind.
2. users can keep track of their past days' mood which can give them objective information about their mood.

## Technologies:
1. Spring security - only authorized users are able to access and modify information.
2. OAuth 2.0 - to delegate authentication and authorization to google and naver.
3. JPA and MariaDB - to store and modify data in a relational database.

## Challenges:
1. Spring security - underlying concepts were not difficult, but quite a lot and understanding as a whole took some time. With testing it became more challenging where I had to set up a mock user with authentication and authorization.
2. Building screens - as a server engineer, building even simple pages were new to me and this became the motivation to learn flutter.
3. design pattern - honestly, I did not undesrtant the point of creating data transfer object and value objects in the first place. Same with the Service layers. Therefore, implementing a design pattern was confusing.
4. AWS EC2 - it was the first time using it and there were lots to learn. Even getting into a vm was a challenge.

## What I learned:
1. Design pattern - MVC pattern and why we need a pattern.
2. Spring security - authorization, authentication and how to setup who can do what. But there's lot more to learn.
3. JPA - JPA itself and why it is so useful.
4. http session - session was used to keep track of signed-in user. It was quite fun to actually utilize it.

## features to be added:
1. front-end - I recently learned flutter because this is considered better if uesd as mobile app.
2. auto-push to the server when my github is updated.

## Dependencies:
    // Spring Boot Starter Dependencies
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    // Database
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    // Lombok
    implementation("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok")


    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.hamcrest:hamcrest-library:2.2")
    testImplementation("org.springframework.security:spring-security-test")

## Installation:
1. Java 11 is required
2. Install Gradle [https://gradle.org/](https://gradle.org/)
3. navigate to the root directory of the project
4. enter gradle build
5. enter Java 'filename (ends with .jar)'
6. use web browser and navigate to localhost:8080/

# How to use:
1. users must be signed up with google or naver to use this app.
2. There are three functions: mood tracker, mood history, and task manager.
3. Mood tracker
    1. First pick a current mood level.
    2. A number of tasks will show up.
    3. check and do as many tasks as you like.
    4. if you reach the lowest level, highest leve or finish exactly two tasks, your mood will be tracked for the day.

