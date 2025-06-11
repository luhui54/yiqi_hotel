# --- 阶段 1: 构建 ---
# 使用一个包含 Maven 和 Java 的镜像作为构建环境
FROM maven:3.8-openjdk-17 AS build

# 设置工作目录
WORKDIR /app

# 复制整个项目到容器中
COPY . .

# 运行 Maven 打包命令，跳过测试以加快速度
# 这会编译所有模块，并在 api_backend/target/ 目录下生成 jar 文件
RUN mvn clean package -DskipTests


# --- 阶段 2: 运行 ---
# 使用一个轻量级的 Java 运行时镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 从“构建”阶段复制最终的 jar 文件到当前阶段
# 使用通配符 * 来匹配版本号，这样更灵活
COPY --from=build /app/api_backend/target/*.jar app.jar

# Render 会通过 $PORT 环境变量提供端口，Spring Boot 会自动识别
# EXPOSE 只是一个文档性质的指令，告诉 Docker 容器监听的端口
EXPOSE 8080

# 容器启动时运行的命令
ENTRYPOINT ["java","-jar","app.jar"]