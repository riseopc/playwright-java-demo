## 多阶段构建：先用 Maven 打包，再用官方 Playwright Java 镜像运行

FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /workspace

COPY pom.xml .
RUN mvn -q -e -B dependency:go-offline

COPY src ./src
RUN mvn -q -e -B -DskipTests package

FROM mcr.microsoft.com/playwright/java:v1.58.0-noble

WORKDIR /workspace

## 复制构建产物
ARG JAR_FILE=/workspace/target/playwright-java-demo-1.0.0.jar
COPY --from=builder ${JAR_FILE} app.jar

## 创建截图与日志目录（可与主机挂载）
RUN mkdir -p /workspace/screenshots /workspace/logs

VOLUME ["/workspace/screenshots", "/workspace/logs"]

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

