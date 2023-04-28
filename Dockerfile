FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY ./target/app-ui-0.0.1.jar /app/myapp.jar
COPY ./src/main/resources/ /app/resources/
ENV FILE_PATH /app/resources/
CMD ["java", "-Dextenal.file=${FILE_PATH}", "-jar", "myapp.jar"]