#!/bin/bash

# Shop API Origin 本地开发一键启动脚本
# 确保Redis和MySQL服务已通过Docker Compose启动

echo "🚀 Shop API Origin 本地开发启动脚本"
echo "======================================"

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 检查Java环境
check_java() {
    echo -e "${BLUE}🔍 检查Java环境...${NC}"
    if ! command -v java &> /dev/null; then
        echo -e "${RED}❌ Java未安装，请先安装Java 17${NC}"
        exit 1
    fi
    
    JAVA_VERSION=$(java -version 2>&1 | head -n1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [ "$JAVA_VERSION" != "17" ]; then
        echo -e "${YELLOW}⚠️  警告: 当前Java版本为 $JAVA_VERSION，推荐使用Java 17${NC}"
    else
        echo -e "${GREEN}✅ Java 17 环境正常${NC}"
    fi
}

# 检查Maven环境
check_maven() {
    echo -e "${BLUE}🔍 检查Maven环境...${NC}"
    if ! command -v mvn &> /dev/null; then
        echo -e "${RED}❌ Maven未安装，请先安装Maven${NC}"
        exit 1
    fi
    
    MVN_VERSION=$(mvn -version | head -n1 | grep -o '[0-9]\+\.[0-9]\+\.[0-9]\+')
    echo -e "${GREEN}✅ Maven $MVN_VERSION 环境正常${NC}"
}

# 检查Docker服务
check_docker_services() {
    echo -e "${BLUE}🔍 检查Docker服务状态...${NC}"
    
    # 检查MySQL
    if docker ps | grep -q "shop-mysql"; then
        echo -e "${GREEN}✅ MySQL服务运行中${NC}"
    else
        echo -e "${RED}❌ MySQL服务未运行，请先执行: docker-compose -f ../docker-compose-dev.yml up -d${NC}"
        exit 1
    fi
    
    # 检查Redis
    if docker ps | grep -q "shop-redis"; then
        echo -e "${GREEN}✅ Redis服务运行中${NC}"
    else
        echo -e "${RED}❌ Redis服务未运行，请先执行: docker-compose -f ../docker-compose-dev.yml up -d${NC}"
        exit 1
    fi
}

# 测试数据库连接
test_database_connection() {
    echo -e "${BLUE}🔍 测试数据库连接...${NC}"
    
    # 等待MySQL完全启动
    echo -e "${YELLOW}⏳ 等待MySQL服务完全启动...${NC}"
    for i in {1..30}; do
        if docker exec shop-mysql mysql -ucool -p123456 -e "SELECT 1;" &>/dev/null; then
            echo -e "${GREEN}✅ MySQL连接正常${NC}"
            break
        fi
        if [ $i -eq 30 ]; then
            echo -e "${RED}❌ MySQL连接超时${NC}"
            exit 1
        fi
        sleep 2
    done
    
    # 测试Redis连接
    if docker exec shop-redis redis-cli ping | grep -q "PONG"; then
        echo -e "${GREEN}✅ Redis连接正常${NC}"
    else
        echo -e "${RED}❌ Redis连接失败${NC}"
        exit 1
    fi
}

# 清理和编译项目
build_project() {
    echo -e "${BLUE}🔨 清理和编译项目...${NC}"
    
    # 设置正确的Java环境变量
    export JAVA_HOME=/opt/homebrew/opt/openjdk@17
    export PATH=$JAVA_HOME/bin:$PATH
    
    if mvn clean compile; then
        echo -e "${GREEN}✅ 项目编译成功${NC}"
    else
        echo -e "${RED}❌ 项目编译失败${NC}"
        exit 1
    fi
}

# 启动应用
start_application() {
    echo -e "${BLUE}🚀 启动应用服务...${NC}"
    echo -e "${YELLOW}📝 提示: 按 Ctrl+C 可停止服务${NC}"
    echo ""
    
    # 设置Spring Profile为local
    export SPRING_PROFILES_ACTIVE=local
    
    # 启动应用
    mvn spring-boot:run -Dspring-boot.run.profiles=local
}

# 主函数
main() {
    echo -e "${BLUE}开始执行启动检查...${NC}"
    echo ""
    
    # 切换到脚本所在目录
    cd "$(dirname "$0")"
    
    # 依次执行检查
    check_java
    check_maven
    check_docker_services
    test_database_connection
    build_project
    
    echo ""
    echo -e "${GREEN}🎉 所有检查通过，开始启动应用...${NC}"
    echo -e "${BLUE}📍 应用将运行在: http://localhost:8001${NC}"
    echo -e "${BLUE}📊 健康检查: http://localhost:8001/actuator/health${NC}"
    echo -e "${BLUE}📚 API文档: http://localhost:8001/swagger-ui/index.html${NC}"
    echo ""
    
    start_application
}

# 执行主函数
main "$@"
