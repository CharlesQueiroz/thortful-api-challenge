# Base Image
FROM openjdk:21-slim

# Define Working Directory
WORKDIR /app

# Copy the pre-built artifact from the build pipeline
COPY build/libs/thortful-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the application's port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]