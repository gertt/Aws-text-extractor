FROM openjdk:17

WORKDIR /app

COPY src/main/resources/log4j.xml /app/src/main/resources/
COPY target/aws-text-extractor-0.0.1-SNAPSHOT.jar /app

EXPOSE 9191

CMD ["java", "-jar", "aws-text-extractor-0.0.1-SNAPSHOT.jar"]