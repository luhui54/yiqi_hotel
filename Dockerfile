# --- 阶段 1: 构建 ---
# 使用一个包含 Maven 和 Java 的镜像作为构建环境
FROM maven:3.8-openjdk-17 AS build

# 设置工作目录
WORKDIR /app

# 复制整个项目到容器中
COPY . .

# 运行 Maven 打包命令
RUN mvn clean package -DskipTests


# --- 阶段 2: 运行 ---
# 使用一个轻量级的 Java 运行时镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 【核心修改】从根目录的 target 文件夹复制 .jar 文件
# 这几乎可以肯定是您项目的正确路径
COPY --from=build /app/target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 容器启动时运行的命令
ENTRYPOINT ["java","-jar","app.jar"]