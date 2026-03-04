# -------- STAGE 1 : Build avec Maven --------
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# -------- STAGE 2 : Runtime léger --------
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/toolsUneedBack-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]