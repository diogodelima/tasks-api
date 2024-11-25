FROM openjdk:21-jdk

WORKDIR /app

COPY /build/libs/authorization-1.0.jar /app/authorization-1.0.jar

EXPOSE 9000

CMD ["java", "-jar", "/app/authorization-1.0.jar"]