# Detector

Given component is resposible for verification of bank transactions.

Github: [https://github.com/BitWeb/digit_25_detector](https://github.com/BitWeb/digit_25_detector)

## Registering

For app to run you need to register. You need a team name and a fork of the detector repository.
Once you have  a team and a fork, you can register with the following CURL command:

**DO NOT FORGET TO CHANGE NAME AND GITHUB REPOSITORY URL IN BODY**

`curl -X POST 'http://localhost:8080/detectors' -H 'Content-Type: application/json' --data-raw '{"name": "YOUR TEAM NAME HERE"}'`

As a response you will get token. Put that token into src/main/java/resources/application.properties

You should be set to go. If you have issues, please feel free to ask for help. 

## Running

* Running in IDE is best option
* Running in terminal `./gradlew bootRun --args='--detector.token=<your_token>'`

## Service limitations

* Each api token is limited to 50 concurrent requests.
* Each api token is limited to 10000 pending transactions.

## Evaluation

At 07.05.2025 21:00 evaluators will start going through registered teams and executing applications in an isolated environment.
Evaluators will run a single instance of application and let it run for 3 minutes to get a baseline. 

