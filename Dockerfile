# build jar using JDK
FROM eclipse-temurin:25 AS build
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw clean package -DskipTests

# run jar in JRE
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]