FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY book-service/target/book-service-*.jar book-service.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/book-service.jar"]