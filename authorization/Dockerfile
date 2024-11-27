FROM openjdk:21-jdk

WORKDIR /app

COPY /build/libs/authorization-1.0.0.jar /app/authorization-1.0.0.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/authorization-1.0.0.jar"]