# Utilisez une image de base Java
FROM openjdk:11-jre-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR de votre application dans l'image
COPY target/kaddem-0.0.1-SNAPSHOT.jar kaddem-app.jar

# Exposer le port sur lequel votre application écoute
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "kaddem-app.jar"]
