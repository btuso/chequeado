FROM openjdk:8-jdk-alpine
VOLUME /images
EXPOSE 8080
ARG JAR_FILE
ENV TELEGRAM_API_TOKEN ""
ENV REQUEST_API_TOKEN ""
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
