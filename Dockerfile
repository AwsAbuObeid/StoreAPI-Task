FROM openjdk:17-alpine
WORKDIR /storeAPI
COPY target/storeAPI.jar storeAPI.jar

EXPOSE 8080
CMD ["java", "-jar", "storeAPI.jar"]
