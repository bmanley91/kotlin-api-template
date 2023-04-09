# Start builder stage
FROM amazoncorretto:17 as builder

# Set the working directory
WORKDIR /app

# Copy the Gradle files and the source code
COPY build.gradle.kts ./
COPY gradlew ./
COPY gradle gradle/
COPY src src/

# Build the application using Gradle
RUN ./gradlew clean build -x test -i

# Start main stage
FROM amazoncorretto:17

# Set the working directory
WORKDIR /app

# Copy the built application from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]