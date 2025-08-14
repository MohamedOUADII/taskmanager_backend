# Start from an OpenJDK image with Java 17 (or your Java version)
FROM openjdk:17-jdk-alpine

# Optional: set a working directory inside container
WORKDIR /app

# Copy the built jar file into the container
COPY target/task-manager-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (usually 8080)
EXPOSE 8080

# Command to run your jar
ENTRYPOINT ["java", "-jar", "app.jar"]
