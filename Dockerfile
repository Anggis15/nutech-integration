# Use Maven with OpenJDK 17 to build the application
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy the pom.xml and src files to the container
COPY pom.xml .
COPY src ./src

# Run Maven build with verbose output for debugging
RUN mvn clean package -DskipTests -X

# Use OpenJDK 17 to run the application
FROM openjdk:17-ea-slim


WORKDIR /app

COPY --from=build /app/target/applyKerja-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
