#!/bin/bash

# 构建前端
echo "Building frontend..."
npm install
npm run build

# 安装vercel CLI
echo "Installing Vercel CLI..."
npm install -g vercel

# 部署到Vercel
echo "Deploying to Vercel..."
vercel --prod

echo "Deployment completed!" 