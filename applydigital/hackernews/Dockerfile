FROM maven:3.8.5-openjdk-17 as builder
WORKDIR /app
COPY . .
RUN mvn dependency:resolve
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/application.jar
EXPOSE 8080

ENV POSTGRES_HOST=localhost

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "application.jar"]