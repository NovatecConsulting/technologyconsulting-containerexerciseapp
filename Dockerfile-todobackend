FROM openjdk:8-alpine
RUN mkdir -p /opt/todobackend
WORKDIR /opt/todobackend
COPY todobackend/target/todobackend-0.0.1-SNAPSHOT.jar /opt/todobackend
CMD ["java", "-jar", "todobackend-0.0.1-SNAPSHOT.jar"]
