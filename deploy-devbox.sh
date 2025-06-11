#!/bin/bash

echo "===== 艺柒酒店项目 DevBox 部署脚本 ====="
echo "该脚本将指导您完成整个项目的部署流程"

# 检查必要工具
echo "正在检查必要工具..."
if ! command -v git &> /dev/null; then
    echo "错误: git 未安装。请安装 git 后再试。"
    exit 1
fi

if ! command -v docker &> /dev/null; then
    echo "提示: Docker 未安装。将使用备选部署方案。"
    HAS_DOCKER=false
else
    HAS_DOCKER=true
fi

# 设置部署类型
echo "请选择部署方式:"
echo "1) Docker 容器化部署 (需要 Docker)"
echo "2) Vercel + Render 分离部署 (免费云服务)"

read -p "请输入选项 (1/2): " DEPLOY_TYPE

case $DEPLOY_TYPE in
    1)
        if [ "$HAS_DOCKER" = false ]; then
            echo "错误: 您选择了 Docker 部署，但系统中未安装 Docker。"
            exit 1
        fi
        
        echo "===== 开始 Docker 部署 ====="
        echo "构建 Docker 镜像..."
        docker build -t yiqi-hotel .
        
        echo "启动容器..."
        docker run -d -p 8080:8080 --name yiqi-hotel-app yiqi-hotel
        
        echo "部署完成！应用正在运行于: http://localhost:8080"
        ;;
        
    2)
        echo "===== 开始云服务部署 ====="
        
        # 后端部署到 Render
        echo "请访问 https://render.com 并注册/登录账号"
        echo "然后选择从 GitHub 导入项目，并选择包含此代码的仓库"
        echo "Render 将自动识别 render.yaml 文件并部署服务"
        
        read -p "后端部署完成后，请输入 Render 提供的 API URL (如 https://hotel-api-devbox.onrender.com): " API_URL
        
        # 前端部署到 Vercel
        echo "正在准备前端部署..."
        cd front || exit 1
        
        # 检查是否已安装 Vercel CLI
        if ! command -v vercel &> /dev/null; then
            echo "正在安装 Vercel CLI..."
            npm install -g vercel
        fi
        
        # 创建 .env 文件
        echo "VITE_APP_BASE_URL=${API_URL}/api" > .env.production
        
        echo "请登录 Vercel 账号:"
        vercel login
        
        echo "正在部署前端到 Vercel..."
        vercel --prod
        
        echo "部署完成！请使用 Vercel 提供的 URL 访问应用。"
        ;;
        
    *)
        echo "无效选项，退出部署。"
        exit 1
        ;;
esac

echo "===== 部署流程结束 =====" 