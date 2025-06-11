<script setup>
import {ref, reactive, computed} from 'vue'
import {Detail, Pay, Cancel} from "^/app/order.js";
import {useRoute, useRouter} from "vue-router"
import {ElMessage, ElMessageBox} from "element-plus";
import {
  Calendar, 
  LocationFilled, 
  Money, 
  Document, 
  Clock,
  OfficeBuilding,
  User,
  Phone,
  Back
} from '@element-plus/icons-vue';

const route = useRoute()
const router = useRouter()
const orderId = route.params.orderId

const order = ref({})
const loading = ref(true)

const init = () => {
  loading.value = true
  Detail(orderId).then(res => {
    order.value = res.data
  }).finally(() => {
    loading.value = false
  })
}

init()

const cancel = () => {
  ElMessageBox.confirm('确认取消该订单吗?', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '关闭',
        type: 'warning',
      }
  ).then(() => {
    loading.value = true
    Cancel(orderId).then(res => {
      ElMessage.success("订单取消成功")
      init()
    }).finally(() => {
      loading.value = false
    })
  })
}

const pay = () => {
  loading.value = true
  Pay(orderId).then(res => {
    ElMessage.success("支付成功")
    init()
  }).finally(() => {
    loading.value = false
  })
}

const getStatusClass = computed(() => {
  switch (Number(order.value?.status)) {
    case 1: return 'status-pending'
    case 2: return 'status-upcoming'
    case 3: return 'status-active'
    case 4: return 'status-completed'
    case 5: return 'status-cancelled'
    default: return ''
  }
})

const formatTime = (time) => {
  return time || '暂无记录'
}
</script>

<template>
  <div class="order-detail-container" v-loading="loading">
    <div class="page-header">
      <h2 class="page-title">订单详情</h2>
      <p class="page-desc">查看预订详细信息</p>
    </div>
    
    <div class="order-detail-card">
      <div class="order-status-header" :class="getStatusClass">
        <div class="status-icon">
          <el-icon v-if="order.status === 1"><Clock /></el-icon>
          <el-icon v-else-if="order.status === 2"><Calendar /></el-icon>
          <el-icon v-else-if="order.status === 3"><OfficeBuilding /></el-icon>
          <el-icon v-else-if="order.status === 4"><Document /></el-icon>
          <el-icon v-else><Document /></el-icon>
        </div>
        <div class="status-text">
          <h3>{{ order.statusStr }}</h3>
          <p v-if="order.status === 1">请尽快完成支付，以确保预订成功</p>
          <p v-else-if="order.status === 2">请按时到店，凭预订信息办理入住</p>
          <p v-else-if="order.status === 3">祝您入住愉快</p>
          <p v-else-if="order.status === 4">感谢您的选择，期待您的再次光临</p>
          <p v-else>订单已取消</p>
        </div>
      </div>
      
      <div class="order-info-section">
        <h3 class="section-title">
          <el-icon><Document /></el-icon>
          订单信息
        </h3>
        
        <div class="info-grid">
          <div class="info-item">
            <span class="info-label">订单号</span>
            <span class="info-value order-id">{{ order.sn }}</span>
          </div>
          
          <div class="info-item">
            <span class="info-label">预订时间</span>
            <span class="info-value">{{ order.bookTime }}</span>
          </div>
          
          <div class="info-item wide">
            <span class="info-label">预订酒店</span>
            <span class="info-value hotel-name">
              <el-icon><OfficeBuilding /></el-icon>
              {{ order.hotelTitle }}
            </span>
          </div>
          
          <div class="info-item">
            <span class="info-label">预订房型</span>
            <span class="info-value">{{ order.roomTypeName }}</span>
          </div>
          
          <div class="info-item">
            <span class="info-label">订单价格</span>
            <span class="info-value price">¥ {{ order.price }}</span>
          </div>
        </div>
      </div>
      
      <div class="order-info-section">
        <h3 class="section-title">
          <el-icon><User /></el-icon>
          入住信息
        </h3>
        
        <div class="info-grid">
          <div class="info-item">
            <span class="info-label">入住人</span>
            <span class="info-value">{{ order.guestName }}</span>
          </div>
          
          <div class="info-item">
            <span class="info-label">联系电话</span>
            <span class="info-value">
              <el-icon><Phone /></el-icon>
              {{ order.guestPhone }}
            </span>
          </div>
          
          <div class="info-item">
            <span class="info-label">预订入住时间</span>
            <span class="info-value">{{ order.bookCheckInTime }}</span>
          </div>
          
          <div class="info-item">
            <span class="info-label">预订退房时间</span>
            <span class="info-value">{{ order.bookCheckOutTime }}</span>
          </div>
          
          <div class="info-item">
            <span class="info-label">实际入住时间</span>
            <span class="info-value">{{ formatTime(order.realCheckInTime) }}</span>
          </div>
          
          <div class="info-item">
            <span class="info-label">实际退房时间</span>
            <span class="info-value">{{ formatTime(order.realCheckOutTime) }}</span>
          </div>
        </div>
      </div>
      
      <div class="order-actions">
        <el-button 
          @click="router.push('/user/order-list')"
          plain
        >
          <el-icon><Back /></el-icon>
          返回订单列表
        </el-button>
        
        <div class="action-buttons">
          <el-button 
            type="danger" 
            @click="cancel" 
            v-if="order.status === 1"
            plain
          >
            取消订单
          </el-button>
          <el-button 
            type="primary" 
            @click="pay" 
            v-if="order.status === 1"
          >
            立即支付
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.order-detail-container {
  padding: 10px 0;
  max-width: 1000px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 24px;
  
  .page-title {
    font-size: 22px;
    font-weight: 600;
    margin: 0 0 8px;
    color: #333;
  }
  
  .page-desc {
    font-size: 14px;
    color: #666;
    margin: 0;
  }
}

.order-detail-card {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  overflow: hidden;
}

.order-status-header {
  display: flex;
  align-items: center;
  padding: 24px;
  color: white;
  
  &.status-pending {
    background: linear-gradient(135deg, #ff9500, #ff7500);
    
    .status-icon {
      background-color: rgba(255, 255, 255, 0.2);
    }
  }
  
  &.status-upcoming {
    background: linear-gradient(135deg, #0071e3, #42a4ff);
    
    .status-icon {
      background-color: rgba(255, 255, 255, 0.2);
    }
  }
  
  &.status-active {
    background: linear-gradient(135deg, #34c759, #34d979);
    
    .status-icon {
      background-color: rgba(255, 255, 255, 0.2);
    }
  }
  
  &.status-completed {
    background: linear-gradient(135deg, #5856d6, #7a79e9);
    
    .status-icon {
      background-color: rgba(255, 255, 255, 0.2);
    }
  }
  
  &.status-cancelled {
    background: linear-gradient(135deg, #ff3b30, #ff6b61);
    
    .status-icon {
      background-color: rgba(255, 255, 255, 0.2);
    }
  }
  
  .status-icon {
    width: 60px;
    height: 60px;
    border-radius: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 20px;
    
    .el-icon {
      font-size: 30px;
    }
  }
  
  .status-text {
    h3 {
      font-size: 24px;
      font-weight: 600;
      margin: 0 0 6px;
    }
    
    p {
      margin: 0;
      opacity: 0.9;
      font-size: 14px;
    }
  }
}

.order-info-section {
  padding: 24px;
  border-bottom: 1px solid #f0f0f0;
  
  .section-title {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 18px;
    font-weight: 600;
    margin: 0 0 20px;
    color: #333;
    
    .el-icon {
      color: #0071e3;
    }
  }
  
  .info-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
    
    .info-item {
      display: flex;
      flex-direction: column;
      
      &.wide {
        grid-column: 1 / -1;
      }
      
      .info-label {
        font-size: 13px;
        color: #999;
        margin-bottom: 6px;
      }
      
      .info-value {
        font-size: 15px;
        color: #333;
        display: flex;
        align-items: center;
        gap: 6px;
        
        .el-icon {
          color: #0071e3;
        }
        
        &.price {
          color: #ff3b30;
          font-weight: 600;
          font-size: 16px;
        }
        
        &.order-id {
          font-family: monospace;
          color: #666;
        }
        
        &.hotel-name {
          font-weight: 500;
        }
      }
    }
  }
}

.order-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  
  .action-buttons {
    display: flex;
    gap: 10px;
  }
}

@media (max-width: 768px) {
  .order-status-header {
    flex-direction: column;
    text-align: center;
    
    .status-icon {
      margin-right: 0;
      margin-bottom: 15px;
    }
  }
  
  .info-grid {
    grid-template-columns: 1fr !important;
  }
  
  .order-actions {
    flex-direction: column-reverse;
    gap: 15px;
    
    .el-button {
      width: 100%;
    }
    
    .action-buttons {
      width: 100%;
      
      .el-button {
        flex: 1;
      }
    }
  }
}
</style>