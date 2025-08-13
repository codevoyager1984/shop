#!/bin/bash

# Shop Origin 停止脚本
# 该脚本会停止所有相关服务

echo "🛑 停止 Shop Origin 商城系统..."

# 检查 Docker 是否运行
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker 未运行"
    exit 1
fi

# 停止所有服务
echo "🔄 停止所有服务..."
docker-compose down

# 显示清理选项
echo ""
echo "✅ 服务已停止！"
echo ""
echo "🗑️  如需完全清理（删除数据卷），请运行："
echo "   docker-compose down -v"
echo ""
echo "🧹 如需清理未使用的镜像，请运行："
echo "   docker system prune"
echo ""
