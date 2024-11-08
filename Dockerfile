FROM openjdk:11-jre-slim

# Expose the application port
EXPOSE 8089

# Add the JAR file into the container
ADD target/kaddem-0.0.1.jar kaddem-0.0.1.jar

# Set the entrypoint for the application
ENTRYPOINT ["java", "-jar", "/kaddem-0.0.1.jar"]
