<script setup>
import {ref, reactive, onMounted} from 'vue'
import {useAdminStore} from "@/stores/admin.js";
import {GetData} from "^/admin/login"
import {
  Money, 
  Tickets, 
  TrendCharts, 
  Calendar
} from '@element-plus/icons-vue'

const adminStore = useAdminStore()

const state = reactive({
  todayMap: {},
  monthMap: {},
  loading: true
})

const init = () => {
  state.loading = true
  GetData().then(res => {
    state.todayMap = res.data.todayMap
    state.monthMap = res.data.monthMap
    state.loading = false
  })
}

onMounted(() => {
  init()
})

// 获取当前日期和时间
const now = new Date()
const hours = now.getHours()
const getGreeting = () => {
  if (hours < 6) return '凌晨好'
  if (hours < 9) return '早上好'
  if (hours < 12) return '上午好'
  if (hours < 14) return '中午好'
  if (hours < 17) return '下午好'
  if (hours < 19) return '傍晚好'
  return '晚上好'
}
</script>

<template>
  <div class="dashboard-container">
    <div class="welcome-section">
      <div class="welcome-card">
        <div class="welcome-info">
          <h2 class="greeting">{{ getGreeting() }}，{{ adminStore.adminInfo.username }}</h2>
          <p class="welcome-text">欢迎回到艺柒酒旅管理系统</p>
        </div>
        <div class="welcome-image">
          <img src="@/assets/banner/1.png" alt="Welcome" class="welcome-img">
        </div>
      </div>
    </div>
    
    <div class="stats-section">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="6">
          <el-card shadow="hover" class="stats-card today-orders">
            <div class="stats-icon">
              <el-icon><tickets /></el-icon>
            </div>
            <div class="stats-info">
              <div class="stats-title">今日有效订单</div>
              <div class="stats-value">
                <el-skeleton v-if="state.loading" :rows="1" animated />
                <span v-else>{{ state.todayMap.count || 0 }}</span>
              </div>
              <div class="stats-desc">订单数量</div>
            </div>
          </el-card>
        </el-col>
        
        <el-col :xs="24" :sm="12" :md="6">
          <el-card shadow="hover" class="stats-card today-revenue">
            <div class="stats-icon">
              <el-icon><money /></el-icon>
            </div>
            <div class="stats-info">
              <div class="stats-title">今日营业额</div>
              <div class="stats-value">
                <el-skeleton v-if="state.loading" :rows="1" animated />
                <span v-else>¥{{ state.todayMap.total || 0 }}</span>
              </div>
              <div class="stats-desc">收入金额</div>
            </div>
          </el-card>
        </el-col>
        
        <el-col :xs="24" :sm="12" :md="6">
          <el-card shadow="hover" class="stats-card month-orders">
            <div class="stats-icon">
              <el-icon><calendar /></el-icon>
            </div>
            <div class="stats-info">
              <div class="stats-title">本月有效订单</div>
              <div class="stats-value">
                <el-skeleton v-if="state.loading" :rows="1" animated />
                <span v-else>{{ state.monthMap.count || 0 }}</span>
              </div>
              <div class="stats-desc">订单数量</div>
            </div>
          </el-card>
        </el-col>
        
        <el-col :xs="24" :sm="12" :md="6">
          <el-card shadow="hover" class="stats-card month-revenue">
            <div class="stats-icon">
              <el-icon><trend-charts /></el-icon>
            </div>
            <div class="stats-info">
              <div class="stats-title">本月营业额</div>
              <div class="stats-value">
                <el-skeleton v-if="state.loading" :rows="1" animated />
                <span v-else>¥{{ state.monthMap.total || 0 }}</span>
              </div>
              <div class="stats-desc">收入金额</div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    
    <div class="quick-access">
      <el-card shadow="hover" class="quick-card">
        <template #header>
          <div class="card-header">
            <h3>快捷入口</h3>
          </div>
        </template>
        <div class="quick-links">
          <el-button 
            type="primary" 
            @click="$router.push('/admin/checkIn')"
            class="quick-link-btn"
          >
            入住登记
          </el-button>
          <el-button 
            type="success" 
            @click="$router.push('/admin/order')"
            class="quick-link-btn"
          >
            订单管理
          </el-button>
          <el-button 
            type="warning" 
            @click="$router.push('/admin/room')"
            class="quick-link-btn"
          >
            房间管理
          </el-button>
          <el-button 
            type="info" 
            @click="$router.push('/admin/roomType')"
            class="quick-link-btn"
          >
            房型管理
          </el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.dashboard-container {
  .welcome-section {
    margin-bottom: 24px;
    
    .welcome-card {
      background: linear-gradient(135deg, #1890ff 0%, #0050b3 100%);
      border-radius: 12px;
      padding: 24px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      box-shadow: 0 4px 12px rgba(24, 144, 255, 0.2);
      
      .welcome-info {
        color: white;
        
        .greeting {
          font-size: 24px;
          font-weight: 600;
          margin: 0 0 8px 0;
        }
        
        .welcome-text {
          font-size: 16px;
          margin: 0;
          opacity: 0.8;
        }
      }
      
      .welcome-image {
        display: flex;
        align-items: center;
        justify-content: center;
        
        .welcome-img {
          height: 80px;
          width: 80px;
          object-fit: contain;
          border-radius: 50%;
          background-color: rgba(255, 255, 255, 0.2);
          padding: 10px;
        }
      }
    }
  }
  
  .stats-section {
    margin-bottom: 24px;
    
    .stats-card {
      height: 100%;
      display: flex;
      align-items: center;
      padding: 20px;
      transition: all 0.3s ease;
      margin-bottom: 20px;
      
      &:hover {
        transform: translateY(-5px);
      }
      
      .stats-icon {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 64px;
        height: 64px;
        border-radius: 12px;
        margin-right: 16px;
        
        .el-icon {
          font-size: 28px;
          color: white;
        }
      }
      
      .stats-info {
        flex: 1;
        
        .stats-title {
          font-size: 16px;
          color: #64748b;
          margin-bottom: 8px;
        }
        
        .stats-value {
          font-size: 28px;
          font-weight: 600;
          color: #1e293b;
          margin-bottom: 4px;
        }
        
        .stats-desc {
          font-size: 14px;
          color: #94a3b8;
        }
      }
      
      &.today-orders .stats-icon {
        background-color: #1890ff;
      }
      
      &.today-revenue .stats-icon {
        background-color: #52c41a;
      }
      
      &.month-orders .stats-icon {
        background-color: #fa8c16;
      }
      
      &.month-revenue .stats-icon {
        background-color: #722ed1;
      }
    }
  }
  
  .quick-access {
    .card-header {
      h3 {
        margin: 0;
        font-size: 18px;
        font-weight: 600;
      }
    }
    
    .quick-links {
      display: flex;
      flex-wrap: wrap;
      gap: 16px;
      
      .quick-link-btn {
        min-width: 120px;
        transition: all 0.3s ease;
        
        &:hover {
          transform: translateY(-2px);
          box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
      }
    }
  }
}

@media (max-width: 768px) {
  .welcome-card {
    flex-direction: column;
    
    .welcome-info {
      margin-bottom: 20px;
      text-align: center;
    }
  }
  
  .quick-links {
    justify-content: center;
  }
}
</style>