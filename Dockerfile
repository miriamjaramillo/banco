#La línea de abajo indica que basaremos nuestra imagen  
FROM openjdk:11

#Identificar al mantenedor de una imagen
MAINTAINER miriameje@gmail.com


VOLUME /tmp

EXPOSE 8081

COPY mjaramillo-persona-ec-0.0.1-SNAPSHOT.jar personamjaramillo.jar
ENTRYPOINT ["java", "-jar", "personamjaramillo.jar"]

