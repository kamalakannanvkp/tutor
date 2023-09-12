FROM openjdk:11 as builder


# Ensure that gradle can be cached independently of other code
COPY *.gradle gradle.* gradlew /src/
COPY gradle /src/gradle
WORKDIR /src
RUN ./gradlew --version --no-daemon

COPY . .
RUN ./gradlew bootJar --no-daemon

FROM openjdk:11-jre-slim
RUN pwd && ls
COPY --from=builder /src/build/libs/tutor-1.0.0-SNAPSHOT.jar /app/
WORKDIR /app
EXPOSE 8080

CMD exec java -XX:+UseContainerSupport -XX:MaxRAMPercentage=80.0 -jar tutor-1.0.0-SNAPSHOT.jar