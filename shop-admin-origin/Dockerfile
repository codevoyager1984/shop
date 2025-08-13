FROM node:lts-alpine
WORKDIR /build
# 设置npm镜像为官方镜像（或使用国内镜像）
RUN npm config set registry https://registry.npmmirror.com
# 安装依赖
COPY package.json yarn.lock* /build/
RUN yarn install --frozen-lockfile --network-timeout 600000
# 复制源代码并构建
COPY ./ /build
RUN npm run build

FROM nginx
RUN mkdir /app
COPY --from=0 /build/dist /app
COPY --from=0 /build/nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
