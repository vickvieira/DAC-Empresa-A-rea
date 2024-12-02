FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/SagaClienteUsuario-0.0.1-SNAPSHOT.jar SagaClienteUsuario.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "SagaClienteUsuario.jar"]