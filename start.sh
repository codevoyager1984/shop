#!/bin/bash

# Shop Origin 一键启动脚本
# 该脚本会构建并启动整个商城系统

echo "🚀 开始启动 Shop Origin 商城系统..."

# 检查 Docker 是否运行
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker 未运行，请先启动 Docker"
    exit 1
fi

# 检查 docker-compose 是否可用
if ! command -v docker-compose &> /dev/null && ! docker compose version &> /dev/null 2>&1; then
    echo "❌ docker-compose 未安装，请先安装 docker-compose"
    exit 1
fi

# 停止并清理之前的容器
echo "🧹 清理之前的容器..."
docker-compose down --remove-orphans

# 构建并启动服务
echo "🔨 构建并启动服务..."
docker-compose up --build -d

# 等待服务启动
echo "⏳ 等待服务启动..."
sleep 10

# 检查服务状态
echo "📊 检查服务状态..."
docker-compose ps

echo ""
echo "✅ 启动完成！"
echo ""
echo "🌐 访问地址："
echo "   前端管理系统: http://localhost"
echo "   后端API文档:  http://localhost:8001/swagger"
echo "   数据库管理:   http://localhost:8080"
echo ""
echo "📋 服务信息："
echo "   MySQL:   localhost:3306 (用户名: cool, 密码: 123456)"
echo "   Redis:   localhost:6379"
echo "   API:     localhost:8001"
echo "   Admin:   localhost:80"
echo ""
echo "🔧 常用命令："
echo "   查看日志: docker-compose logs -f [服务名]"
echo "   停止服务: docker-compose down"
echo "   重启服务: docker-compose restart [服务名]"
echo ""
