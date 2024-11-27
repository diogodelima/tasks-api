FROM openjdk:21-jdk

WORKDIR /app

COPY /build/libs/gateway-1.0.0.jar /app/gateway-1.0.0.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/gateway-1.0.0.jar"]