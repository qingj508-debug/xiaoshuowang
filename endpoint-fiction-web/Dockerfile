FROM node:16-alpine AS build
ARG VUE_APP_API_BASE_URL
ARG http_proxy
ARG https_proxy
ARG no_proxy
ENV no_proxy=$no_proxy,registry.npmmirror.com
ENV VUE_APP_API_BASE_URL=${VUE_APP_API_BASE_URL}
WORKDIR /app
COPY package*.json ./
# 使用国内 npm 镜像源（直连，不走代理）
RUN npm config set registry https://registry.npmmirror.com && npm ci
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
