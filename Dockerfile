FROM amazoncorretto:11-alpine-jdk // de que imagen de java partimos
MAINTAINER Ludmila // quien es el dueño
COPY target/portfolio-0.0.1-SNAPSHOT.jar portfolio.jar                   // copia el empaquetado a github
ENTRYPOINT ["java", "-jar", "/portfolio.jar"]    // es la instruccion que se va a ejecutar primero