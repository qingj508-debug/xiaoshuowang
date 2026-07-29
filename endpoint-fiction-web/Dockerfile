FROM docker.m.daocloud.io/library/node:16-alpine AS build
ARG VUE_APP_API_BASE_URL
ARG http_proxy
ARG https_proxy
ARG no_proxy
ENV no_proxy=$no_proxy,registry.npmmirror.com
ENV VUE_APP_API_BASE_URL=${VUE_APP_API_BASE_URL}
WORKDIR /app
COPY package*.json ./
# 使用国内 npm 镜像源（直连，不走代理）+ 加大超时 + 失败重试（npm 缓存在重试间累积）
RUN npm config set registry https://registry.npmmirror.com && \
    npm config set fetch-retries 5 && \
    npm config set fetch-retry-mintimeout 20000 && \
    npm config set fetch-retry-maxtimeout 120000 && \
    npm config set fetch-timeout 600000 && \
    for i in 1 2 3 4 5; do \
      npm ci && break; \
      [ $i -eq 5 ] && exit 1; \
      echo "== npm ci attempt $i failed, retrying =="; sleep 5; \
    done
COPY . .
RUN npm run build

FROM docker.m.daocloud.io/library/nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
