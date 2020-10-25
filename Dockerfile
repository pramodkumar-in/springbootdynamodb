FROM openjdk:8-jdk-alpine
EXPOSE 8500
ARG JAR_FILE=target/springbootdynamodb-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} springbootdynamodb.jar
ENTRYPOINT ["java","-jar","/springbootdynamodb.jar"]
