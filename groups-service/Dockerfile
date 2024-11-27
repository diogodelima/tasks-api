FROM openjdk:21-jdk

WORKDIR /app

COPY /build/libs/groups-service-1.0.0.jar /app/groups-service-1.0.0.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/groups-service-1.0.0.jar"]