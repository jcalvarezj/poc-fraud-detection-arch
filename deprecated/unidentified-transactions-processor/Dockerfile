FROM maven:3.8.1-openjdk-11 AS maven
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests
FROM openjdk:11-jre-slim
COPY --from=maven /app/target/unidentified-transactions-processor-1.0-SNAPSHOT.jar /executable.jar
ENTRYPOINT ["java", "-jar", "/executable.jar"]
