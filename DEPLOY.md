# 艺柒酒店项目部署指南

## 方案一：使用 DevBox + Render + Vercel（免费方案）

### 前端部署 (Vercel)

1. 注册 [Vercel](https://vercel.com/) 账号

2. 安装 Vercel CLI:

```bash
npm install -g vercel
```

3. 在前端目录下登录并部署:

```bash
cd front
vercel login
vercel --prod
```

4. 部署完成后，Vercel 会提供一个域名，如 `https://yiqi-hotel-xyz.vercel.app`

### 后端部署 (Render.com)

1. 注册 [Render](https://render.com/) 账号

2. 在 Render 控制台中，点击 "New" > "Blueprint"

3. 选择从 GitHub 导入项目，并关联你的仓库

4. Render 会自动识别 `render.yaml` 文件并创建服务和数据库

5. 部署完成后，获取后端 API 地址，例如 `https://hotel-api-devbox.onrender.com`

6. 回到前端，修改 API 基础 URL:
   - 在 Vercel 控制台中设置环境变量 `VITE_APP_BASE_URL=https://hotel-api-devbox.onrender.com/api`
   - 重新部署前端

## 方案二：使用 DevBox 容器化部署（对开发者更友好）

### 使用 GitHub Codespaces (开发环境 DevBox)

1. 创建 GitHub 仓库并推送代码

2. 在仓库页面，点击 "Code" > "Open with Codespaces"

3. 在 Codespaces 中，可以直接运行项目:

```bash
# 构建并运行Docker容器
docker build -t yiqi-hotel .
docker run -p 8080:8080 yiqi-hotel
```

4. 使用 Codespaces 提供的转发 URL 访问应用

### 使用 DevBox + Railway (另一个免费选项)

1. 注册 [Railway](https://railway.app/) 账号

2. 在 Railway 控制台，创建新项目 > 从 GitHub 导入

3. 设置环境变量:

   - `PORT=8080`
   - `SPRING_PROFILES_ACTIVE=prod`
   - 添加 MySQL 数据库服务

4. Railway 会自动部署你的应用并提供一个 URL

## 常见问题

1. **跨域问题**: 确保后端配置了正确的 CORS 设置，允许前端域名访问

2. **数据库连接**: 检查数据库连接字符串和凭证是否正确配置

3. **环境变量**: 确保所有需要的环境变量都已正确设置

4. **构建失败**: 检查日志以获取详细错误信息

## 更新部署

### 前端更新

```bash
cd front
npm run build
vercel --prod
```

### 后端更新

推送代码到 GitHub 仓库，Render 会自动检测更新并重新部署
