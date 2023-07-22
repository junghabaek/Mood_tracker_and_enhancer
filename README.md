# Mood Tracker and Enhancer (Spring Boot 2.7.10)
# Project Description

## Objectives:
1. To help users feel better by being more active according to their current state of mind.
2. Users can keep track of their past days' mood, which can give them objective information about their mood.

## Technologies:
1. Spring Security - Only authorized users can access and modify information.
2. OAuth 2.0 - To delegate authentication and authorization to Google and Naver.
3. JPA and MariaDB - To store and modify data in a relational database.

## Challenges:
1. Spring Security - The underlying concepts were not difficult, but understanding them as a whole took some time. Testing became more challenging when I had to set up a mock user with authentication and authorization.
2. Building screens - As a server engineer, building even simple pages was new to me, and this became the motivation to learn Flutter.
3. Design pattern - Honestly, I did not understand the point of creating data transfer objects and value objects at first. The same applied to the Service layers. Therefore, implementing a design pattern was confusing.
4. AWS EC2 - It was the first time I used it, and there was a lot to learn. Even getting into a VM was a challenge.

## What I learned:
1. Design pattern - MVC pattern and why we need patterns.
2. Spring Security - Authorization, authentication, and how to set up who can do what. But there's a lot more to learn.
3. JPA - JPA itself and why it is so useful.
4. HTTP session - Sessions were used to keep track of signed-in users. It was quite fun to actually utilize it.

## Features to be added:
1. Front-end - I recently learned Flutter because it is considered better if used as a mobile app.
2. Auto-push to the server when my GitHub is updated.

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
1. Java 11 is required.
2. Install Gradle [https://gradle.org/](https://gradle.org/).
3. Navigate to the root directory of the project.
4. Enter 'gradle build'.
5. Enter 'java filename (ends with .jar)'.
6. Use a web browser and navigate to localhost:8080/.

# How to use:
[Demo clip](https://www.youtube.com/watch?v=PEmJcGOuTnI&ab_channel=JungHaBaek)
1. Users must sign up with Google or Naver to use this app.
2. There are three functions: mood tracker, mood history, and task manager.
3. Mood tracker:
    1. First, pick a current mood level.
    2. A number of tasks will show up.
    3. Check and do as many tasks as you like.
    4. If you reach the lowest level, highest level, or finish exactly two tasks, your mood will be tracked for the day.
4. Mood history:
    1. Using the calendar, pick two dates where you want to search the past mood level.
    2. Past mood levels will show up.
5. Task manager:
    1. Users can create tasks, which will be added to the level they selected.
    2. Users can click on each task they made to modify (update or delete) it.
