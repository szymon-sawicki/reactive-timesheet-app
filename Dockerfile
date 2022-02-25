FROM openjdk:17
EXPOSE 8000
ADD target/app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]