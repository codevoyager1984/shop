# 使用 Maven 镜像进行构建
FROM maven:3.9-eclipse-temurin-17 AS builder

# 设置工作目录
WORKDIR /build

# 复制 pom.xml 和源代码
COPY pom.xml .
COPY src ./src

# 编译项目
RUN mvn clean package -DskipTests

# 使用 GraalVM 17 作为运行时镜像
FROM ghcr.io/graalvm/graalvm-ce:latest

# 设置容器内的工作目录
WORKDIR /app

# 从构建阶段复制编译好的jar文件
COPY --from=builder /build/target/shop-api-origin-7.1.0.jar /app/shop-api-origin-7.1.0.jar

# 暴露Spring Boot应用程序运行的端口
EXPOSE 8001

# 运行Spring Boot应用程序的命令
ENTRYPOINT ["java", "-jar", "/app/shop-api-origin-7.1.0.jar", "--spring.profiles.active=prod"]
