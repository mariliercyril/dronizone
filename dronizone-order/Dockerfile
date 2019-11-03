FROM openjdk:8-jdk-alpine
EXPOSE 9001
ADD /target/dronizone-order-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]