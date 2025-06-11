<script setup>
import {ref, reactive, computed} from 'vue'
import {useUserStore} from "@/stores/user.js"
import {useRoute, useRouter} from 'vue-router'

const userStore = useUserStore()
const route = useRoute()
const router = useRouter()

const defaultActive = computed(() => route.path)

const logout = () => {
  userStore.logout()
  router.push('/')
}
</script>

<template>
  <el-container class="app-container">
    <el-header class="app-header">
      <div class="header-content">
        <div class="logo-container" @click="router.push('/')" role="button">
          <h1 class="logo">艺柒酒旅</h1>
        </div>
        
        <div class="nav-menu">
          <el-menu
            :default-active="defaultActive"
            mode="horizontal"
            :ellipsis="false"
            router
            class="main-menu"
          >
            <!-- 中间空间 -->
            <div class="flex-spacer"></div>
            
            <!-- 用户菜单 -->
            <template v-if="userStore.userToken">
              <el-sub-menu index="2" class="user-menu">
                <template #title>
                  <div class="user-info">
                    <el-avatar :size="32" :src="userStore.userInfo.avatar" class="user-avatar"/>
                    <span class="username">{{ userStore.userInfo.username }}</span>
                  </div>
                </template>
                <el-menu-item index="/user/profile">个人中心</el-menu-item>
                <el-menu-item index="/user/order-list">我的订单</el-menu-item>
                <el-menu-item @click="logout">退出</el-menu-item>
              </el-sub-menu>
            </template>
            <template v-else>
              <div class="auth-buttons">
                <el-button text @click="router.push('/login')" class="login-btn">登录</el-button>
                <el-button type="primary" @click="router.push('/register')" class="register-btn">注册</el-button>
              </div>
            </template>
          </el-menu>
        </div>
      </div>
    </el-header>
    
    <el-main class="app-main">
      <router-view/>
    </el-main>
  </el-container>
</template>

<style lang="scss" scoped>
.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.app-header {
  position: sticky;
  top: 0;
  z-index: 100;
  padding: 0;
  background-color: rgba(255, 255, 255, 0.8);
  backdrop-filter: saturate(180%) blur(20px);
  box-shadow: 0 1px 0 rgba(0, 0, 0, 0.05);

  .header-content {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
    padding: 0 20px;
  }
  
  .logo-container {
    cursor: pointer;
    
    .logo {
      font-size: 22px;
      font-weight: 600;
      color: var(--primary-color);
      letter-spacing: -0.02em;
    }
  }
  
  .nav-menu {
    flex: 1;
    
    .main-menu {
      border: none;
      background: transparent;
      display: flex;
      justify-content: flex-end;
      
      :deep(.el-menu-item),
      :deep(.el-sub-menu__title) {
        font-size: 14px;
        color: var(--dark-color);
        height: 60px;
        line-height: 60px;
        
        &:hover {
          color: var(--primary-color);
          background: transparent;
        }
      }
      
      :deep(.el-menu-item.is-active) {
        color: var(--primary-color);
        font-weight: 500;
      }
    }
  }
  
  .flex-spacer {
    flex-grow: 1;
  }
  
  .user-menu {
    .user-info {
      display: flex;
      align-items: center;
      gap: 8px;
      
      .user-avatar {
        border: 2px solid var(--primary-light);
      }
      
      .username {
        font-weight: 500;
      }
    }
  }
  
  .auth-buttons {
    display: flex;
    align-items: center;
    gap: 8px;
    
    .login-btn {
      color: var(--dark-color);
      font-weight: 500;
      
      &:hover {
        color: var(--primary-color);
      }
    }
    
    .register-btn {
      background: var(--primary-color);
      border-color: var(--primary-color);
      border-radius: 980px;
      padding: 8px 16px;
      
      &:hover {
        background: var(--primary-hover);
        border-color: var(--primary-hover);
      }
    }
  }
}

.app-main {
  flex: 1;
  padding: 0;
  width: 100%;
}
</style>