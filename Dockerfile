FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY app .

RUN chmod +x ./gradlew && ./gradlew --no-daemon shadowJar

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/build/libs/app-1.0-SNAPSHOT-all.jar app.jar

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60.0"
EXPOSE 7070

CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
