FROM openjdk:8-jdk-alpine
EXPOSE 9003
ADD ./target/dronizone-notification-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar", "--spring.config.location=classpath:/dockerConfig.properties"]
