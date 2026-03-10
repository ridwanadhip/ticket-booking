# build jar using JDK
FROM eclipse-temurin:25 AS build
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw clean package -DskipTests

# run jar in JRE
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/target/ticket-booking-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]