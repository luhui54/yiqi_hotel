<script setup>
import {ref, reactive, computed} from 'vue'
import {useUserStore} from "@/stores/user.js"
import {useRoute} from 'vue-router'

const userStore = useUserStore()
const route = useRoute()

const activeMenu = computed(() => {
  const path = route.path
  if (path.includes('/profile')) return 'profile'
  if (path.includes('/updatePwd')) return 'updatePwd'
  if (path.includes('/order-list')) return 'order-list'
  return 'profile'
})
</script>

<template>
  <div class="user-center-container">
    <div class="user-center-header">
      <div class="user-info-card">
        <div class="user-avatar-wrapper">
          <el-avatar :size="90" :src="userStore.userInfo.avatar" class="user-avatar"/>
        </div>
        <div class="user-info-content">
          <h2 class="username">{{ userStore.userInfo.username }}</h2>
          <p class="user-signature">{{ userStore.userInfo.signature || '这个人很懒，什么都没留下' }}</p>
        </div>
      </div>
    </div>
    
    <div class="user-center-main">
      <div class="user-sidebar">
        <h3 class="sidebar-title">个人中心</h3>
        <div class="sidebar-menu">
          <div 
            class="menu-item" 
            :class="{ active: activeMenu === 'profile' }"
            @click="$router.push('/user/profile')"
          >
            <i class="el-icon-user"></i>
            <span>个人信息</span>
          </div>
          <div 
            class="menu-item" 
            :class="{ active: activeMenu === 'updatePwd' }"
            @click="$router.push('/user/updatePwd')"
          >
            <i class="el-icon-lock"></i>
            <span>修改密码</span>
          </div>
          <div 
            class="menu-item" 
            :class="{ active: activeMenu === 'order-list' }"
            @click="$router.push('/user/order-list')"
          >
            <i class="el-icon-tickets"></i>
            <span>我的订单</span>
          </div>
        </div>
      </div>
      
      <div class="user-content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.user-center-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px 15px 40px;
}

.user-center-header {
  margin-bottom: 30px;
}

.user-info-card {
  display: flex;
  align-items: center;
  padding: 24px;
  background: linear-gradient(135deg, #0071e3, #42a4ff);
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 113, 227, 0.15);
  color: white;
  
  .user-avatar-wrapper {
    margin-right: 24px;
    
    .user-avatar {
      border: 3px solid rgba(255, 255, 255, 0.7);
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
    }
  }
  
  .user-info-content {
    .username {
      font-size: 24px;
      font-weight: 600;
      margin: 0 0 8px;
    }
    
    .user-signature {
      font-size: 14px;
      margin: 0;
      opacity: 0.9;
    }
  }
}

.user-center-main {
  display: flex;
  gap: 24px;
}

.user-sidebar {
  width: 220px;
  flex-shrink: 0;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  
  .sidebar-title {
    font-size: 18px;
    font-weight: 600;
    margin: 0;
    padding: 16px 20px;
    border-bottom: 1px solid #f0f0f0;
    color: #333;
  }
  
  .sidebar-menu {
    padding: 15px 0;
    
    .menu-item {
      display: flex;
      align-items: center;
      padding: 14px 20px;
      cursor: pointer;
      transition: all 0.3s ease;
      color: #666;
      
      i {
        margin-right: 10px;
        font-size: 18px;
      }
      
      &:hover {
        color: #0071e3;
        background-color: rgba(0, 113, 227, 0.05);
      }
      
      &.active {
        color: #0071e3;
        background-color: rgba(0, 113, 227, 0.08);
        font-weight: 500;
        position: relative;
        
        &::before {
          content: '';
          position: absolute;
          left: 0;
          top: 0;
          bottom: 0;
          width: 3px;
          background-color: #0071e3;
        }
      }
    }
  }
}

.user-content {
  flex: 1;
  min-height: 500px;
}

@media (max-width: 768px) {
  .user-center-main {
    flex-direction: column;
  }
  
  .user-sidebar {
    width: 100%;
    margin-bottom: 20px;
  }
  
  .user-info-card {
    flex-direction: column;
    text-align: center;
    
    .user-avatar-wrapper {
      margin-right: 0;
      margin-bottom: 16px;
    }
  }
}
</style>