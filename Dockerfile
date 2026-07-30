FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY app .

RUN chmod +x ./gradlew && ./gradlew --no-daemon shadowJar

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60.0"
EXPOSE 7070

CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT-all.jar"]
