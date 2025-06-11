<script setup>
import {ref, reactive, onMounted, onBeforeMount, nextTick, computed, watch} from 'vue'
import {Cancel, List, Pay, Dict} from '^/app/order'
import {ElMessage, ElMessageBox} from "element-plus";
import {
  Calendar, 
  LocationFilled, 
  Money, 
  Document, 
  Clock,
  OfficeBuilding
} from '@element-plus/icons-vue';

// 缓存不同状态的订单数据
const orderCache = reactive({
  '': {
    orders: [],
    total: 0,
    page: 1,
    hasMore: true,
    loaded: false
  }
});

const state = reactive({
  dicts: [],
  orders: [],
  total: 0,
  status: '',
  loading: false,
  loadBtnStatus: false,
  pageSize: 10,
  localLoading: false,
  // 添加防抖变量
  loadingMore: false,
  // 添加最后点击的标签，用于跟踪重复点击
  lastClickedStatus: '',
  // 添加重新加载标志
  forceReload: false
})

// 当前选中状态的订单数据
const currentOrderCache = computed(() => {
  // 确保缓存中有当前状态的数据结构
  if (!orderCache[state.status]) {
    orderCache[state.status] = {
      orders: [],
      total: 0,
      page: 1,
      hasMore: true,
      loaded: false
    };
  }
  return orderCache[state.status];
});

// 监听status变化，重置重新加载标志
watch(() => state.status, (newStatus, oldStatus) => {
  // 如果是重复点击同一个标签，标记为需要强制重新加载
  if (newStatus === oldStatus && newStatus === state.lastClickedStatus) {
    // 强制重新加载当前状态的数据
    state.forceReload = true;
    
    // 立即触发重新加载
    if (orderCache[newStatus]) {
      orderCache[newStatus].loaded = false;
    }
    loadOrders(true);
  }
}, { deep: true });

const loadOrders = (reset = false) => {
  // 强制重新加载或重置时清空当前状态的缓存
  if (reset || state.forceReload) {
    currentOrderCache.value.orders = [];
    currentOrderCache.value.page = 1;
    currentOrderCache.value.hasMore = true;
    currentOrderCache.value.loaded = false;
    state.forceReload = false;
  }

  // 如果已经加载过并且不是重置，直接使用缓存数据
  if (currentOrderCache.value.loaded && !reset) {
    state.orders = [...currentOrderCache.value.orders];
    state.total = currentOrderCache.value.total;
    state.loadBtnStatus = !currentOrderCache.value.hasMore;
    state.localLoading = false;
    return;
  }

  state.loading = true;
  state.localLoading = true;

  // 打印请求参数，辅助调试
  const requestParams = {
    status: state.status,
    current: currentOrderCache.value.page,
    size: state.pageSize
  };
  console.log('加载订单数据，参数：', requestParams);

  List(requestParams).then(res => {
    console.log('订单数据加载成功', res.data);
    const newOrders = res.data.records || [];
    
    if (reset) {
      currentOrderCache.value.orders = [...newOrders];
    } else {
      currentOrderCache.value.orders = [...currentOrderCache.value.orders, ...newOrders];
    }

    currentOrderCache.value.total = res.data.total;
    currentOrderCache.value.loaded = true;
    currentOrderCache.value.hasMore = currentOrderCache.value.orders.length < res.data.total;

    // 更新组件状态
    state.orders = [...currentOrderCache.value.orders];
    state.total = currentOrderCache.value.total;
    state.loadBtnStatus = !currentOrderCache.value.hasMore;
  }).catch(err => {
    console.error('加载订单数据失败:', err);
    ElMessage.error('加载订单数据失败，请重试');
  }).finally(() => {
    state.loading = false;
    state.loadingMore = false;
    // 使用短暂的延迟使过渡更平滑
    setTimeout(() => {
      state.localLoading = false;
    }, 200);
  });
}

const resetAndLoadOrders = () => {
  // 保持当前显示的订单列表，仅显示加载效果
  state.localLoading = true;
  
  // 重置状态
  state.status = '';
  state.lastClickedStatus = '';
  
  // 如果全部数据已加载，并且不需要强制重新加载，直接使用缓存
  if (orderCache[''].loaded && !state.forceReload) {
    state.orders = [...orderCache[''].orders];
    state.total = orderCache[''].total;
    state.loadBtnStatus = !orderCache[''].hasMore;
    state.localLoading = false;
  } else {
    // 否则加载全部数据
    loadOrders(true);
  }
}

const init = () => {
  Dict().then(res => {
    state.dicts = [{label: '全部', value: ''}, ...res.data.map(e => {
      return {label: e.value, value: e.key}
    })]
  });

  resetAndLoadOrders();
}

const cancel = (order) => {
  ElMessageBox.confirm('确认取消该订单吗?', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '关闭',
        type: 'warning',
      }
  ).then(() => {
    state.loading = true
    Cancel(order.id).then(res => {
      ElMessage.success("订单取消成功")
      order.status = 5
      order.statusStr = "已取消"
      
      // 更新所有缓存中的该订单
      Object.values(orderCache).forEach(cache => {
        const orderInCache = cache.orders.find(o => o.id === order.id);
        if (orderInCache) {
          orderInCache.status = 5;
          orderInCache.statusStr = "已取消";
        }
      });
      
    }).finally(() => {
      state.loading = false
    })
  })
}

const pay = (order) => {
  state.loading = true
  Pay(order.id).then(res => {
    ElMessage.success("支付成功")
    order.status = 2
    order.statusStr = '待入住'
    
    // 更新所有缓存中的该订单
    Object.values(orderCache).forEach(cache => {
      const orderInCache = cache.orders.find(o => o.id === order.id);
      if (orderInCache) {
        orderInCache.status = 2;
        orderInCache.statusStr = "待入住";
      }
    });
    
  }).finally(() => {
    state.loading = false
  })
}

const changeStatus = (val) => {
  // 显示本地加载效果但不影响内容
  state.localLoading = true;
  
  // 始终更新最后点击的标签
  const isRepeatedClick = val === state.lastClickedStatus;
  state.lastClickedStatus = val;
  
  // 处理重复点击同一个标签的情况
  if (isRepeatedClick) {
    // 强制重新加载
    state.forceReload = true;
    
    // 重置缓存中当前状态的数据
    if (orderCache[val]) {
      orderCache[val].page = 1;
      orderCache[val].loaded = false;
      orderCache[val].hasMore = true;
    }
  }
  
  // 延迟很短的时间，让加载效果显示出来，但保持现有内容不变
  setTimeout(() => {
    loadOrders(isRepeatedClick || state.forceReload);
  }, 50);
}

const loadMore = () => {
  // 防止重复点击
  if (state.loadingMore) return;
  
  // 加载更多时设置加载状态
  state.loadingMore = true;
  
  try {
    // 如果还有更多数据可以加载
    if (!state.loadBtnStatus) {
      currentOrderCache.value.page++;
      loadOrders(false);
    }
  } catch (error) {
    console.error('加载更多失败:', error);
    state.loadingMore = false;
    ElMessage.error('加载更多失败，请重试');
  }
}

const getStatusClass = (status) => {
  switch (Number(status)) {
    case 1: return 'status-pending'
    case 2: return 'status-upcoming'
    case 3: return 'status-active'
    case 4: return 'status-completed'
    case 5: return 'status-cancelled'
    default: return ''
  }
}

// 创建一个唯一key，确保组件首次渲染正确
const componentKey = ref(Date.now())

onBeforeMount(() => {
  // 确保所有状态都有对应的缓存对象
  const statusList = ['', '1', '2', '3', '4', '5'];
  statusList.forEach(status => {
    if (!orderCache[status]) {
      orderCache[status] = {
        orders: [],
        total: 0,
        page: 1,
        hasMore: true,
        loaded: false
      };
    }
  });
})

onMounted(() => {
  // 重置所有状态
  state.status = ''
  state.lastClickedStatus = ''
  state.forceReload = false
  
  // 清除缓存，确保从详情页返回时能获取最新数据
  Object.keys(orderCache).forEach(key => {
    orderCache[key].loaded = false;
  });
  
  // 初始化数据
  init()
  
  // 允许组件重新挂载，解决返回时标签状态不正确的问题
  nextTick(() => {
    componentKey.value = Date.now()
  })
})
</script>

<template>
  <div class="order-list-container" :key="componentKey">
    <div class="page-header">
      <h2 class="page-title">我的订单</h2>
      <p class="page-desc">查看您的所有酒店预订记录</p>
    </div>
    
    <div class="order-filter">
      <el-segmented 
        v-model="state.status" 
        :options="state.dicts" 
        @change="changeStatus"
        class="status-filter"
      />
    </div>
    
    <!-- 使用本地加载状态实现平滑过渡 -->
    <div class="order-list-wrapper">
      <div class="local-loading-overlay" v-if="state.localLoading">
        <el-icon class="loading-icon"><i class="el-icon-loading"></i></el-icon>
      </div>
      
      <div class="order-list" :class="{'is-loading': state.localLoading}">
        <div class="order-card" v-for="order in state.orders" :key="order.id">
          <div class="order-header">
            <div class="order-id">
              <el-icon><Document /></el-icon>
              <span>订单号：{{ order.sn }}</span>
            </div>
            <div class="order-status" :class="getStatusClass(order.status)">
              {{ order.statusStr }}
            </div>
          </div>
          
          <div class="order-content">
            <div class="hotel-info">
              <div class="hotel-name">
                <el-icon><OfficeBuilding /></el-icon>
                <span>{{ order.hotelTitle }}</span>
              </div>
              <div class="room-type">
                <span class="label">房型：</span>
                <span>{{ order.roomTypeName }}</span>
              </div>
            </div>
            
            <div class="order-details">
              <div class="detail-item">
                <el-icon><Calendar /></el-icon>
                <span>预订时间：{{ order.bookTime }}</span>
              </div>
              <div class="detail-item">
                <el-icon><Clock /></el-icon>
                <span>入住时间：{{ order.bookCheckInTime }}</span>
              </div>
              <div class="detail-item">
                <el-icon><Clock /></el-icon>
                <span>退房时间：{{ order.bookCheckOutTime }}</span>
              </div>
              <div class="detail-item price">
                <el-icon><Money /></el-icon>
                <span>订单金额：<strong>¥ {{ order.price }}</strong></span>
              </div>
            </div>
          </div>
          
          <div class="order-actions">
            <el-button 
              @click="$router.push('/order-detail/' + order.id)"
              plain
            >
              查看详情
            </el-button>
            <el-button 
              type="danger" 
              @click="cancel(order)" 
              v-if="order.status === 1"
              plain
            >
              取消订单
            </el-button>
            <el-button 
              type="primary" 
              @click="pay(order)" 
              v-if="order.status === 1"
            >
              立即支付
            </el-button>
          </div>
        </div>
        
        <el-empty 
          description="暂无订单记录" 
          v-if="state.orders.length === 0 && !state.loading && !state.localLoading"
          class="empty-state"
        />
        
        <div class="load-more" v-if="state.total > 0 && !state.loadBtnStatus">
          <el-button 
            @click="loadMore" 
            :loading="state.loading || state.loadingMore"
            class="load-more-btn"
            :disabled="state.loading || state.loadingMore"
          >
            加载更多
          </el-button>
        </div>
        
        <div class="no-more" v-if="state.loadBtnStatus && state.orders.length > 0">
          没有更多订单了
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.order-list-container {
  padding: 10px 0;
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

.order-filter {
  margin-bottom: 20px;
  
  .status-filter {
    width: 100%;
    
    :deep(.el-segmented-item) {
      flex: 1;
      min-width: 80px;
    }
    
    :deep(.el-segmented-item__button) {
      width: 100%;
      padding: 10px 0;
      font-size: 14px;
    }
    
    :deep(.el-segmented-item__button--active) {
      color: #0071e3;
      font-weight: 500;
    }
  }
}

.order-list-wrapper {
  position: relative;
  min-height: 200px;
}

.local-loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
  backdrop-filter: blur(2px);
  border-radius: 10px;
  
  .loading-icon {
    font-size: 40px;
    color: var(--primary-color);
  }
}

.order-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
  transition: opacity 0.2s ease;
  
  &.is-loading {
    opacity: 0.8;
  }
}

.order-card {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  transition: all 0.3s ease;
  
  &:hover {
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
    transform: translateY(-2px);
  }
  
  .order-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 20px;
    background-color: #f9f9f9;
    border-bottom: 1px solid #eee;
    
    .order-id {
      display: flex;
      align-items: center;
      gap: 8px;
      color: #666;
      font-size: 14px;
      
      .el-icon {
        font-size: 16px;
      }
    }
    
    .order-status {
      font-size: 14px;
      font-weight: 500;
      padding: 4px 12px;
      border-radius: 15px;
      
      &.status-pending {
        color: #ff9500;
        background-color: rgba(255, 149, 0, 0.1);
      }
      
      &.status-upcoming {
        color: #0071e3;
        background-color: rgba(0, 113, 227, 0.1);
      }
      
      &.status-active {
        color: #34c759;
        background-color: rgba(52, 199, 89, 0.1);
      }
      
      &.status-completed {
        color: #5856d6;
        background-color: rgba(88, 86, 214, 0.1);
      }
      
      &.status-cancelled {
        color: #ff3b30;
        background-color: rgba(255, 59, 48, 0.1);
      }
    }
  }
  
  .order-content {
    padding: 20px;
    
    .hotel-info {
      margin-bottom: 15px;
      
      .hotel-name {
        display: flex;
        align-items: center;
        gap: 8px;
        font-size: 16px;
        font-weight: 500;
        margin-bottom: 8px;
        color: #333;
        
        .el-icon {
          color: #0071e3;
        }
      }
      
      .room-type {
        font-size: 14px;
        color: #666;
        padding-left: 24px;
        
        .label {
          color: #999;
        }
      }
    }
    
    .order-details {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
      gap: 12px;
      
      .detail-item {
        display: flex;
        align-items: center;
        gap: 8px;
        font-size: 14px;
        color: #666;
        
        .el-icon {
          color: #999;
        }
        
        &.price {
          color: #333;
          
          strong {
            color: #ff3b30;
            font-size: 16px;
          }
          
          .el-icon {
            color: #ff3b30;
          }
        }
      }
    }
  }
  
  .order-actions {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    gap: 10px;
    padding: 15px 20px;
    border-top: 1px solid #eee;
  }
}

.empty-state {
  margin: 40px 0;
}

.load-more {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  
  .load-more-btn {
    width: 200px;
  }
}

.no-more {
  text-align: center;
  color: #999;
  font-size: 14px;
  padding: 20px 0;
}

@media (max-width: 768px) {
  .order-content {
    .order-details {
      grid-template-columns: 1fr;
    }
  }
  
  .order-actions {
    flex-wrap: wrap;
    
    .el-button {
      flex: 1;
      min-width: 120px;
    }
  }
}
</style>