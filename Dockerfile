FROM maven:3.9.9-eclipse-temurin-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:24-slim-bookworm
WORKDIR /app
COPY --from=build /app/target/applyKerja-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--DDL-AUTO=update", "--PASWORD=90e782a64efb3954bf02f3d4b42b5b80", "--URL=abb07b3e6ce49452eee7f5532759ade2d2406978a603f395851497fce43e13be4790241bc369884170b131c6504a643e1b290a65b91f2f4acf0d197f0a607c708678ed451d1bc430011f34e32b1ff35d57120a81bd958fb8ab2ad05ab858722862343f4a564719cc2aea2ec673b6bc3f", "--USERNAME=f691d7c07971842c371c2a2dc899f811"]
