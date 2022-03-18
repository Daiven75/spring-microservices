FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY cambio-service/target/cambio-service-*.jar cambio-service.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/cambio-service.jar"]