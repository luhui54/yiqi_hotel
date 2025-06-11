<script setup>
import {ref, reactive, computed} from 'vue'
import {useAdminStore} from "@/stores/admin.js";
import {
  HomeFilled, 
  Menu, 
  Setting, 
  User, 
  Tickets, 
  OfficeBuilding, 
  House, 
  List, 
  Key,
  ArrowRight
} from "@element-plus/icons-vue"
import {useRoute} from 'vue-router'

const adminStore = useAdminStore()
const route = useRoute()

const defaultActive = computed(() => route.path)
const isCollapse = ref(false)

const toggleSidebar = () => {
  isCollapse.value = !isCollapse.value
}
</script>

<template>
  <el-container class="admin-layout">
    <el-aside :width="isCollapse ? '64px' : '220px'" class="admin-sidebar">
      <div class="sidebar-header">
        <img src="@/assets/banner/1.png" alt="Logo" class="sidebar-logo">
        <h3 v-show="!isCollapse" class="sidebar-title">艺柒酒旅</h3>
      </div>
      
      <el-menu
          :default-active="defaultActive"
          :collapse="isCollapse"
          :collapse-transition="false"
          class="sidebar-menu"
          router
          unique-opened
          text-color="#fff"
          active-text-color="#fff"
          background-color="transparent"
      >
        <el-menu-item index="/admin">
          <el-icon><home-filled /></el-icon>
          <template #title>控制台</template>
        </el-menu-item>
        
        <el-sub-menu index="service">
          <template #title>
            <el-icon><tickets /></el-icon>
            <span>业务管理</span>
          </template>
          <el-menu-item index="/admin/hotel" v-if="adminStore.adminInfo.role === 'ADMIN'">
            <el-icon><office-building /></el-icon>
            <span>酒店管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/roomType">
            <el-icon><house /></el-icon>
            <span>房型管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/room">
            <el-icon><list /></el-icon>
            <span>房间管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/checkIn">
            <el-icon><key /></el-icon>
            <span>入住登记</span>
          </el-menu-item>
          <el-menu-item index="/admin/order">
            <el-icon><tickets /></el-icon>
            <span>订单管理</span>
          </el-menu-item>
        </el-sub-menu>
        
        <el-sub-menu index="system" v-if="adminStore.adminInfo.role === 'ADMIN'">
          <template #title>
            <el-icon><setting /></el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/admin/user">
            <el-icon><user /></el-icon>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/admin">
            <el-icon><setting /></el-icon>
            <span>管理员管理</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
      
      <div class="sidebar-footer">
        <el-button 
          :icon="isCollapse ? ArrowRight : Menu" 
          @click="toggleSidebar" 
          circle 
          class="collapse-btn"
        />
      </div>
    </el-aside>
    
    <el-container class="admin-content">
      <el-header class="admin-header">
        <div class="header-left">
          <h3 class="header-title">艺柒酒旅后台管理系统</h3>
        </div>
        
        <div class="header-right">
          <span class="welcome-text">欢迎，{{ adminStore.adminInfo.username }}</span>
          <el-dropdown trigger="click">
            <el-avatar 
              :size="36" 
              :src="adminStore.adminInfo.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"
              class="user-avatar"
            />
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="$router.push('/admin/updatePwd')">
                  <el-icon><key /></el-icon>
                  <span>修改密码</span>
                </el-dropdown-item>
                <el-dropdown-item divided @click="adminStore.logout()">
                  <el-icon color="#f56c6c"><HomeFilled /></el-icon>
                  <span>退出系统</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      
      <el-main class="admin-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.admin-layout {
  height: 100vh;
  width: 100vw;
  overflow: hidden;
}

.admin-sidebar {
  display: flex;
  flex-direction: column;
  background: linear-gradient(180deg, #001529 0%, #002140 100%);
  box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);
  transition: width 0.3s ease;
  overflow: hidden;
  position: relative;
  z-index: 10;
  
  .sidebar-header {
    display: flex;
    align-items: center;
    padding: 16px;
    height: 60px;
    background-color: rgba(0, 0, 0, 0.2);
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    overflow: hidden;
    
    .sidebar-logo {
      height: 32px;
      width: 32px;
      border-radius: 4px;
      object-fit: cover;
      box-shadow: 0 0 8px rgba(24, 144, 255, 0.5);
      transition: all 0.3s ease;
    }
    
    .sidebar-title {
      color: white;
      font-size: 18px;
      margin: 0 0 0 12px;
      white-space: nowrap;
      text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
      background: linear-gradient(90deg, #fff, #e6f7ff);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      transition: all 0.3s ease;
    }
  }
  
  .sidebar-menu {
    flex: 1;
    border-right: none;
    background: transparent;
    
    :deep(.el-menu--collapse) {
      width: 64px;
    }
    
    :deep(.el-menu-item) {
      height: 50px;
      line-height: 50px;
      margin: 4px 0;
      border-radius: 0 22px 22px 0;
      margin-right: 16px;
      
      .el-icon {
        font-size: 18px;
        margin-right: 10px;
      }
      
      &.is-active {
        background: linear-gradient(90deg, rgba(24, 144, 255, 0.8), rgba(24, 144, 255, 0.4));
        color: white;
        
        &::before {
          content: '';
          position: absolute;
          left: 0;
          top: 0;
          width: 4px;
          height: 100%;
          background-color: #1890ff;
        }
      }
      
      &:hover {
        background-color: rgba(255, 255, 255, 0.08);
        color: #1890ff;
      }
    }
    
    :deep(.el-sub-menu) {
      .el-sub-menu__title {
        height: 50px;
        line-height: 50px;
        margin: 4px 0;
        border-radius: 0 22px 22px 0;
        margin-right: 16px;
        
        .el-icon {
          font-size: 18px;
          margin-right: 10px;
        }
        
        &:hover {
          background-color: rgba(255, 255, 255, 0.08);
          color: #1890ff;
        }
      }
      
      &.is-active {
        > .el-sub-menu__title {
          color: #1890ff;
        }
      }
      
      .el-menu {
        background-color: rgba(0, 0, 0, 0.1);
        padding: 5px 0;
        
        .el-menu-item {
          margin-left: 4px;
          margin-right: 20px;
          height: 46px;
          line-height: 46px;
          
          &.is-active {
            background: linear-gradient(90deg, rgba(24, 144, 255, 0.6), rgba(24, 144, 255, 0.3));
          }
        }
      }
    }
  }
  
  .sidebar-footer {
    padding: 16px;
    display: flex;
    justify-content: center;
    border-top: 1px solid rgba(255, 255, 255, 0.1);
    background-color: rgba(0, 0, 0, 0.2);
    
    .collapse-btn {
      background-color: transparent;
      border: 1px solid rgba(255, 255, 255, 0.2);
      color: white;
      transition: all 0.3s ease;
      
      &:hover {
        background-color: rgba(255, 255, 255, 0.1);
        transform: rotate(180deg);
      }
    }
  }
}

.admin-content {
  height: 100%;
  overflow: hidden;
}

.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  background-color: white;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  z-index: 10;
  
  .header-left {
    .header-title {
      font-size: 18px;
      font-weight: 500;
      color: #1e293b;
      margin: 0;
    }
  }
  
  .header-right {
    display: flex;
    align-items: center;
    gap: 16px;
    
    .welcome-text {
      font-size: 14px;
      color: #64748b;
    }
    
    .user-avatar {
      cursor: pointer;
      transition: all 0.3s ease;
      
      &:hover {
        transform: scale(1.05);
        box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
      }
    }
  }
}

.admin-main {
  padding: 20px;
  background-color: #f0f2f5;
  height: calc(100vh - 60px);
  overflow-y: auto;
}

:deep(.el-dropdown-menu__item) {
  display: flex;
  align-items: center;
  gap: 8px;
}
</style>