<script setup>

</script>

<template>
  <router-view/>
</template>

<style>
:root {
  --primary-color: #0071e3; /* 艺龙蓝 + 苹果风格 */
  --primary-hover: #0062c3;
  --secondary-color: #f5f5f7; /* 苹果浅灰 */
  --dark-color: #1d1d1f; /* 苹果深色 */
  --gray-color: #86868b; /* 苹果灰色 */
  --light-gray: #f2f2f2;
  --card-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  --hover-shadow: 0 10px 30px rgba(0, 0, 0, 0.12);
  --transition: all 0.3s ease;
  --border-radius: 12px;
}

body {
  font-family: "SF Pro Display", "SF Pro Icons", "PingFang SC", "Helvetica Neue", Helvetica, Arial, sans-serif;
  margin: 0;
  padding: 0;
  color: var(--dark-color);
  background-color: #fff;
  line-height: 1.5;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 15px;
}

/* 通用卡片样式，苹果风格 */
.apple-card {
  background-color: #fff;
  border-radius: var(--border-radius);
  box-shadow: var(--card-shadow);
  transition: var(--transition);
  overflow: hidden;
}

.apple-card:hover {
  transform: translateY(-5px);
  box-shadow: var(--hover-shadow);
}

/* 通用标题样式 */
.section-title {
  font-size: 32px;
  font-weight: 600;
  margin: 0 0 8px 0;
  color: var(--dark-color);
}

.section-subtitle {
  font-size: 18px;
  color: var(--gray-color);
  margin: 0;
}

/* 价格标签 */
.price-tag {
  font-size: 18px;
  font-weight: 600;
  color: var(--primary-color);
}

/* 评分标签 */
.rating-tag {
  display: flex;
  align-items: center;
  gap: 5px;
  color: #ff9500; /* 苹果黄色 */
}

/* 按钮样式 */
.el-button {
  border-radius: 8px;
}

.el-button--primary {
  background-color: var(--primary-color) !important;
  border-color: var(--primary-color) !important;
}

.el-button--primary:hover, 
.el-button--primary:focus {
  background-color: var(--primary-hover) !important;
  border-color: var(--primary-hover) !important;
}

/* 输入框样式 */
.el-input__wrapper {
  border-radius: 8px !important;
}

/* 卡片样式 */
.el-card {
  border-radius: 16px !important;
  border: none !important;
  overflow: hidden;
}
</style>
