# --- 阶段 1: 构建 ---
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- 新增的调试命令 ---
# 递归列出 /app 目录下的所有文件和文件夹
# 这会帮助我们找到 .jar 文件的确切位置
RUN ls -R /app


# --- 阶段 2: 运行 (暂时不会执行到这里) ---
FROM openjdk:17-jdk-slim
WORKDIR /app
# 我们暂时保持这行不变，部署会在这里失败，但上面的 ls 命令会成功执行并打印日志
COPY --from=build /app/api_backend/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]