# 使用多阶段构建
# 后端构建阶段
FROM maven:3.9-eclipse-temurin-17 AS backend-build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

# 前端构建阶段
FROM node:18-alpine AS frontend-build
WORKDIR /app
COPY front/package.json front/package-lock.json ./
RUN npm install
COPY front .
RUN npm run build

# 最终镜像
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=backend-build /app/target/*.jar app.jar
COPY --from=frontend-build /app/dist ./static

# 环境变量
ENV PORT=8080
ENV SPRING_PROFILES_ACTIVE=prod

# 暴露端口
EXPOSE 8080

# 启动命令
CMD ["java", "-jar", "app.jar"] 