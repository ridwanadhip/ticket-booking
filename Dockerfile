FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app

# Download dependencies
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Build the fat jar
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Analyze dependencies of the fat jar
RUN jar xf target/ticket-booking-*.jar
RUN jdeps --ignore-missing-deps -q  \
    --recursive  \
    --multi-release 25  \
    --print-module-deps  \
    --class-path 'BOOT-INF/lib/*'  \
    target/ticket-booking-*.jar > dependencies.txt

# Create a custom JRE based on dependencies
RUN jlink \
    --verbose \
    --add-modules $(cat dependencies.txt) \
    --no-header-files \
    --no-man-pages \
    --strip-debug \
    --output /custom-jre

# Copy custom JRE to run image
FROM alpine:3
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=build /custom-jre $JAVA_HOME

# Run the application fat jar
WORKDIR /app
COPY --from=build /app/target/ticket-booking-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]