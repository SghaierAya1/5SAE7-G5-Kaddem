# Utilise l'image de base OpenJDK 11 (ou une version compatible)
FROM openjdk:11-jre-slim

# Expose le port sur lequel l'application sera accessible
EXPOSE 8089

# Ajoute le JAR de votre application (nommez correctement le fichier jar)
ADD target/kaddem-0.0.1.jar kaddem-0.0.1.jar

# Spécifie la commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "/kaddem-0.0.1.jar"]