# 1) 빌드 스테이지
FROM gradle:7.6-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar

# 2) 런타임 스테이지
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
