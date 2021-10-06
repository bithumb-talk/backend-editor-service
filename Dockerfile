FROM openjdk:17-ea-11-jdk-slim
WORKDIR /server
ARG JAR_FILE=./build/libs/image-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} image.jar
EXPOSE 7010
ENTRYPOINT ["java", "-jar", "image.jar"]