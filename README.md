# Tutor Score Calculator

The Tutor Score Calculator is a simple microservice built on Java and this help is calculating the scores of the tutor based on the field.

## How to compile and run tests

```bash
$ ./gradlew clean build test
```

## Building the Server

To clean, build, and run tests/checks  you can run

```bash
$ ./gradlew clean build
```

To clean and create this Jar you can run

```bash
$ ./gradlew clean bootJar
```

If you want to do "everything", you can run

```bash
$ ./gradlew clean build bootJar
```

## Running Locally

### Local Server

```bash
$ ./gradlew bootRun
```

## Testing the app via Swagger URL

When the app is up and running on a given port , try accesing the swagger URL from the given link , since by default teh server
starts on 8080 , the swagger is accessible from hte given link below. Kindly chanage your port, when you spin up the spring app
on any other port other than 8080.
http://localhost:8080/swagger-ui/index.html

The api can be accessible and the tutor score can be calculated from the same.

## Testing the app From PostMan / CURL

The app can be tested from postman and curl via the given api exposed below
```bash

curl -X POST "http://localhost:8080/tutor/v1/calculate" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"kindOfExperience\": [ \"ONLINE_TUTORING\" ], \"totalExperience\": \"ABOVE_3_YEARS\", \"tutorId\": \"tutor-123456\"}"
```
The sample output for a success scenario can be like below : 

```json
{
  "id": "tutor-123456",
  "score": 3,
  "formData": {
    "tutoringExperience": "ABOVE_3_YEARS",
    "tutoringKind": "ONLINE_TUTORING"
  }
}
```


## Docker Build

```bash
$ docker build . -t tutor-service
```

## Run Docker Image 

```bash
$ docker run -p 8080:8080 tutor-service:latest
```