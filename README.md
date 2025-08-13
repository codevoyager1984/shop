# Shop Origin 商城系统

一个基于 Spring Boot + Vue.js 的完整商城系统，支持 Docker 一键部署。

## 🏗️ 系统架构

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   前端管理系统    │    │    后端 API     │    │     数据库      │
│   (Vue.js)      │───▶│  (Spring Boot)  │───▶│  MySQL + Redis  │
│   Nginx:80      │    │     :8001       │    │  :3306 + :6379  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 📦 项目结构

```
shop-origin/
├── shop-admin-origin/     # 前端管理系统 (Vue.js + Element Plus)
├── shop-api-origin/       # 后端API服务 (Spring Boot)
├── java-uniapp-origin/    # 移动端应用 (uni-app)
├── docker-compose.yml     # Docker编排文件
├── start.sh              # 一键启动脚本
└── README.md             # 项目说明
```

## 🚀 快速开始

### 前置要求

- Docker 20.0+
- Docker Compose 2.0+
- 8GB+ 可用内存

### 一键启动

```bash
# 克隆项目
git clone <repository-url>
cd shop-origin

# 一键启动（推荐）
./start.sh

# 或者手动启动
docker-compose up --build -d
```

### 访问地址

启动成功后，可以通过以下地址访问：

| 服务 | 地址 | 说明 |
|------|------|------|
| 前端管理系统 | http://localhost | 商城管理后台 |
| API文档 | http://localhost:8001/swagger | Swagger API文档 |
| 数据库管理 | http://localhost:8080 | Adminer数据库管理工具 |

## 🔧 服务配置

### 数据库配置

- **MySQL**: `localhost:3306`
  - 数据库: `cool`
  - 用户名: `cool`
  - 密码: `123456`

- **Redis**: `localhost:6379`
  - 无密码

### 端口映射

| 服务 | 容器端口 | 主机端口 | 说明 |
|------|----------|----------|------|
| shop-admin | 80 | 80 | 前端管理系统 |
| shop-api | 8001 | 8001 | 后端API服务 |
| mysql | 3306 | 3306 | MySQL数据库 |
| redis | 6379 | 6379 | Redis缓存 |
| adminer | 8080 | 8080 | 数据库管理工具 |

## 🛠️ 开发指南

### 本地开发

如果需要本地开发，可以单独启动数据库服务：

```bash
# 只启动数据库服务
docker-compose up mysql redis -d

# 本地启动后端（需要Java 17+）
cd shop-api-origin
./mvnw spring-boot:run

# 本地启动前端（需要Node.js 16+）
cd shop-admin-origin
npm install
npm run dev
```

### 常用命令

```bash
# 查看服务状态
docker-compose ps

# 查看服务日志
docker-compose logs -f [服务名]

# 重启服务
docker-compose restart [服务名]

# 停止所有服务
docker-compose down

# 停止并删除数据卷（谨慎使用）
docker-compose down -v
```

### 数据持久化

系统使用 Docker 数据卷来持久化数据：

- `shop-mysql-data`: MySQL 数据
- `shop-redis-data`: Redis 数据  
- `shop-api-uploads`: 文件上传数据

## 🔍 故障排除

### 常见问题

1. **端口冲突**
   ```bash
   # 检查端口占用
   lsof -i :80
   lsof -i :8001
   
   # 修改 docker-compose.yml 中的端口映射
   ```

2. **内存不足**
   ```bash
   # 检查 Docker 内存限制
   docker system df
   docker system prune
   ```

3. **服务启动失败**
   ```bash
   # 查看详细日志
   docker-compose logs [服务名]
   
   # 重新构建镜像
   docker-compose build --no-cache [服务名]
   ```

### 健康检查

系统内置了健康检查机制：

```bash
# 检查服务健康状态
docker-compose ps

# 手动健康检查
curl http://localhost:8001/actuator/health
curl http://localhost
```

## 📝 更新日志

### v1.0.0
- ✅ 支持 Docker 一键部署
- ✅ 前后端分离架构
- ✅ MySQL + Redis 数据存储
- ✅ Nginx 反向代理
- ✅ 健康检查机制
- ✅ 数据持久化

## 🤝 贡献指南

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 支持

如有问题，请通过以下方式联系：

- 提交 Issue
- 发送邮件
- 加入讨论群

---

**注意**: 首次启动可能需要较长时间来下载镜像和构建服务，请耐心等待。
