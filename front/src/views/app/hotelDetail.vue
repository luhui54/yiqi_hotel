<script setup>
import {reactive, ref, onMounted} from 'vue'
import {useRoute, useRouter} from "vue-router"
import moment from "moment";
import {
  Avatar, 
  DataLine, 
  Fries, 
  HelpFilled, 
  House, 
  LocationFilled, 
  NoSmoking, 
  Pointer, 
  SuitcaseLine, 
  Switch, 
  Van,
  StarFilled
} from '@element-plus/icons-vue';
import {ElMessage} from "element-plus";
import {Detail} from "^/app/hotel.js";
import {Create} from "^/app/order.js";

const services = ref([
  {icon: SuitcaseLine, title: '行李寄存'},
  {icon: Van, title: '停车场'},
  {icon: Pointer, title: '洗衣服务'},
  {icon: House, title: '大堂吧'},
  {icon: DataLine, title: '叫车服务'},
  {icon: Avatar, title: '24小时前台'},
  {icon: HelpFilled, title: '快速入住退房'},
  {icon: Switch, title: '快递服务'},
  {icon: Fries, title: '急救包'},
  {icon: NoSmoking, title: '灭火器'}
])

const route = useRoute()
const router = useRouter()
const hotelId = route.params.hotelId

const state = reactive({
  hotel: {},
  roomTypes: [],
  loading: true,
  orderVisible: false,
  checkTime: [route.query.checkInTime, route.query.checkOutTime],
  orderForm: {
    roomTypeId: '',
    bookCheckInTime: route.query.checkInTime || '',
    bookCheckOutTime: route.query.checkOutTime || '',
    guestName: '',
    guestPhone: ''
  },
  rangeSeparator: "1晚",
  activeImageIndex: 0,
  imageList: [],
  showImageViewer: false,
  currentViewerIndex: 0
})

const init = () => {
  state.loading = true
  Detail(hotelId).then(res => {
    state.hotel = res.data.hotel
    state.roomTypes = res.data.roomTypes
    
    // 处理房型数据
    if (state.roomTypes && state.roomTypes.length > 0) {
      state.roomTypes.forEach(room => {
        // 确保quantity字段存在
        if (room.quantity === undefined && room.stock !== undefined) {
          room.quantity = room.stock
        } else if (room.quantity === undefined) {
          room.quantity = 0
        }
      })
    }
    
    // 处理图片列表
    if (state.hotel.images) {
      state.imageList = state.hotel.images.split(';').filter(img => img && img.trim() !== '')
    } else {
      state.imageList = []
    }
    
    // 预加载图片以避免闪屏
    if (state.imageList.length > 0) {
      preloadImages(state.imageList)
    }
    
    // 预加载房型图片
    if (state.roomTypes && state.roomTypes.length > 0) {
      state.roomTypes.forEach(room => {
        if (room.image) {
          preloadImage(room.image)
        }
        if (room.images) {
          const roomImages = room.images.split(';').filter(img => img && img.trim() !== '')
          preloadImages(roomImages)
        }
      })
    }
    
    state.loading = false
  })
}

// 预加载单张图片
const preloadImage = (src) => {
  if (!src) return
  const img = new Image()
  img.src = src
}

// 预加载多张图片
const preloadImages = (imageArray) => {
  if (!imageArray || !imageArray.length) return
  imageArray.forEach(src => preloadImage(src))
}

const bookFn = (roomTypeId) => {
  state.orderVisible = true
  state.orderForm.roomTypeId = roomTypeId
}

const disableDateFn = (date) => {
  return date < moment().subtract(1, 'd')
}

const changeCheckTime = (val) => {
  const count = moment(val[1]).diff(moment(val[0]), 'day')
  state.rangeSeparator = count + '晚'

  state.orderForm.bookCheckInTime = moment(val[0]).format('YYYY-MM-DD')
  state.orderForm.bookCheckOutTime = moment(val[1]).format('YYYY-MM-DD')
}

const formRef = ref()
const submitFn = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      Create(state.orderForm).then(res => {
        ElMessage.success('预订成功')
        router.push("/order-detail/" + res.data)
      })
    }
  })
}

// 自定义图片查看器
const showFullImage = (index) => {
  state.currentViewerIndex = index
  state.showImageViewer = true
}

// 关闭图片查看器
const closeImageViewer = () => {
  state.showImageViewer = false
}

// 切换到上一张图片
const prevImage = () => {
  if (state.currentViewerIndex > 0) {
    state.currentViewerIndex--
  } else {
    state.currentViewerIndex = state.imageList.length - 1
  }
}

// 切换到下一张图片
const nextImage = () => {
  if (state.currentViewerIndex < state.imageList.length - 1) {
    state.currentViewerIndex++
  } else {
    state.currentViewerIndex = 0
  }
}

// 处理房型图片查看
const showRoomImage = (room, index = 0) => {
  const roomImages = room.images ? room.images.split(';').filter(img => img && img.trim() !== '') : [room.image]
  if (roomImages.length > 0) {
    state.imageList = roomImages
    state.currentViewerIndex = index
    state.showImageViewer = true
  }
}

onMounted(() => {
  init()
})
</script>

<template>
  <div class="container hotel-detail-container">
    <el-skeleton :loading="state.loading" animated>
      <!-- 酒店基本信息 -->
      <div class="hotel-header">
        <el-row :gutter="30">
          <el-col :lg="10" :md="12" :sm="24">
            <div class="hotel-main-image apple-card" @click="showFullImage(0)">
              <img 
                :src="state.hotel.image" 
                alt="酒店主图"
                class="main-hotel-img"
              />
            </div>
          </el-col>
          <el-col :lg="14" :md="12" :sm="24">
            <div class="hotel-info-card apple-card">
              <h1 class="hotel-title">{{ state.hotel.title }}</h1>
              
              <div class="hotel-meta">
                <div class="hotel-rating">
                  <el-icon color="#ff9500"><StarFilled /></el-icon>
                  <span>{{ state.hotel.score }} 分</span>
                </div>
                <div class="hotel-rank">
                  <el-tag type="warning" effect="light">{{ state.hotel.rankStr }}</el-tag>
                </div>
              </div>
              
              <div class="hotel-info-list">
                <div class="info-item">
                  <div class="info-label">酒店电话</div>
                  <div class="info-value">{{ state.hotel.tel }}</div>
                </div>
                
                <div class="info-item">
                  <div class="info-label">酒店位置</div>
                  <div class="info-value location">
                    <el-icon color="var(--primary-color)"><LocationFilled /></el-icon>
                    <span>{{ state.hotel.location }}</span>
                  </div>
                </div>
                
                <div class="info-item">
                  <div class="info-label">酒店简介</div>
                  <div class="info-value description">{{ state.hotel.introduction }}</div>
                </div>
              </div>
              
              <div class="hotel-gallery">
                <el-scrollbar>
                  <div class="gallery-container">
                    <div 
                      v-for="(item, index) in state.imageList"
                      :key="index"
                      class="gallery-item"
                      @click="showFullImage(index)"
                    >
                      <img 
                        :src="item"
                        alt="酒店图片"
                        class="gallery-img"
                      />
                    </div>
                  </div>
                </el-scrollbar>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      
      <!-- 服务与设施 -->
      <div class="hotel-services section-card">
        <h2 class="section-title">服务与设施</h2>
        
        <div class="services-list">
          <el-row :gutter="20">
            <el-col :xs="12" :sm="8" :md="6" :lg="4" v-for="(item, index) in services" :key="index">
              <div class="service-item">
                <div class="service-icon">
                  <el-icon :size="24" color="var(--primary-color)">
                    <component :is="item.icon"></component>
                  </el-icon>
                </div>
                <span>{{ item.title }}</span>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
      
      <!-- 房型列表 -->
      <div class="hotel-room-types">
        <h2 class="section-title">可预订房型</h2>
        
        <div class="room-type-list">
          <div class="room-type-card apple-card" v-for="(item, index) in state.roomTypes" :key="index">
            <el-row :gutter="24">
              <el-col :md="6" :sm="8" :xs="24">
                <div class="room-image" @click="showRoomImage(item)">
                  <img 
                    :src="item.image" 
                    alt="房型图片"
                    class="room-img"
                  />
                </div>
              </el-col>
              <el-col :md="12" :sm="16" :xs="24">
                <div class="room-info">
                  <h3 class="room-title">{{ item.title }}</h3>
                  <div class="room-attributes">
                    <div class="attribute-item">
                      <div class="attribute-label">房间面积</div>
                      <div class="attribute-value">{{ item.area }}㎡</div>
                    </div>
                    <div class="attribute-item">
                      <div class="attribute-label">床型</div>
                      <div class="attribute-value">{{ item.bedType }}</div>
                    </div>
                    <div class="attribute-item">
                      <div class="attribute-label">宽带</div>
                      <div class="attribute-value">{{ item.internet }}</div>
                    </div>
                    <div class="attribute-item">
                      <div class="attribute-label">早餐</div>
                      <div class="attribute-value">{{ item.breakfast }}</div>
                    </div>
                  </div>
                  <div class="room-desc">{{ item.remark }}</div>
                </div>
              </el-col>
              <el-col :md="6" :xs="24">
                <div class="room-booking">
                  <div class="room-price">
                    <span class="price-value">¥{{ item.price }}</span>
                    <span class="price-unit">/晚</span>
                  </div>
                  <div class="room-stock" :class="{'no-rooms': !item.quantity || item.quantity <= 0}">
                    剩余{{ item.quantity || 0 }}间
                  </div>
                  <el-button 
                    type="primary" 
                    class="book-button" 
                    @click="bookFn(item.id)"
                    :disabled="!item.quantity || item.quantity <= 0"
                  >
                    {{ !item.quantity || item.quantity <= 0 ? '已满房' : '立即预订' }}
                  </el-button>
                </div>
              </el-col>
            </el-row>
          </div>
        </div>
      </div>
    </el-skeleton>
    
    <!-- 预订表单 -->
    <el-dialog
        v-model="state.orderVisible"
        title="酒店预订"
        width="500px"
        class="booking-dialog"
    >
      <el-form
          ref="formRef"
          :model="state.orderForm"
          label-position="top"
          :rules="{
            guestName: [{ required: true, message: '请输入入住人姓名', trigger: 'blur' }],
            guestPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
          }"
      >
        <el-form-item label="入住日期" required>
          <el-date-picker
              v-model="state.checkTime"
              type="daterange"
              :disabled-date="disableDateFn"
              :range-separator="state.rangeSeparator"
              start-placeholder="入住日期"
              end-placeholder="退房日期"
              @change="changeCheckTime"
              style="width: 100%"
              class="custom-date-picker"
          />
        </el-form-item>
        
        <el-form-item label="入住人" prop="guestName">
          <el-input 
            v-model="state.orderForm.guestName" 
            placeholder="请输入入住人姓名"
            class="booking-input"
          ></el-input>
        </el-form-item>
        
        <el-form-item label="联系电话" prop="guestPhone">
          <el-input 
            v-model="state.orderForm.guestPhone" 
            placeholder="请输入联系电话"
            class="booking-input"
          ></el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="submitFn" class="booking-submit-btn">确认预订</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    
    <!-- 自定义图片查看器 -->
    <div class="custom-image-viewer" v-if="state.showImageViewer">
      <div class="viewer-overlay" @click="closeImageViewer"></div>
      <div class="viewer-container">
        <div class="viewer-header">
          <span class="viewer-counter">{{ state.currentViewerIndex + 1 }}/{{ state.imageList.length }}</span>
          <button class="viewer-close-btn" @click="closeImageViewer">×</button>
        </div>
        <div class="viewer-content">
          <button class="viewer-nav-btn prev-btn" @click.stop="prevImage">❮</button>
          <div class="viewer-image-container">
            <img 
              :src="state.imageList[state.currentViewerIndex]" 
              alt="查看大图"
              class="viewer-image"
            />
          </div>
          <button class="viewer-nav-btn next-btn" @click.stop="nextImage">❯</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.hotel-detail-container {
  padding: 30px 15px;
}

.hotel-header {
  margin-bottom: 40px;
}

.hotel-main-image {
  height: 400px;
  overflow: hidden;
  border-radius: var(--border-radius);
  box-shadow: var(--card-shadow);
  cursor: pointer;
  position: relative;
  
  &:hover::after {
    content: '点击查看大图';
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 10px;
    background: rgba(0, 0, 0, 0.5);
    color: white;
    font-size: 14px;
    text-align: center;
  }
  
  .main-hotel-img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
  }
  
  &:hover .main-hotel-img {
    transform: scale(1.05);
  }
}

.hotel-info-card {
  height: 100%;
  padding: 25px;
  border-radius: var(--border-radius);
  box-shadow: var(--card-shadow);
  background-color: #fff;
  display: flex;
  flex-direction: column;
  
  .hotel-title {
    font-size: 28px;
    font-weight: 600;
    margin: 0 0 16px;
    color: var(--dark-color);
  }
  
  .hotel-meta {
    display: flex;
    align-items: center;
    gap: 16px;
    margin-bottom: 20px;
    
    .hotel-rating {
      display: flex;
      align-items: center;
      gap: 5px;
      font-size: 16px;
      font-weight: 500;
    }
  }
}

.hotel-info-list {
  margin-bottom: 20px;
  
  .info-item {
    margin-bottom: 16px;
    
    .info-label {
      font-size: 15px;
      font-weight: 600;
      color: var(--gray-color);
      margin-bottom: 6px;
    }
    
    .info-value {
      font-size: 16px;
      
      &.location {
        display: flex;
        align-items: center;
        gap: 6px;
      }
      
      &.description {
        line-height: 1.6;
        color: var(--gray-color);
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 3;
        overflow: hidden;
      }
    }
  }
}

.hotel-gallery {
  margin-top: auto;
  
  .gallery-container {
    display: flex;
    gap: 12px;
    
    .gallery-item {
      width: 100px;
      height: 80px;
      flex-shrink: 0;
      cursor: pointer;
      overflow: hidden;
      border-radius: 8px;
      border: 2px solid transparent;
      transition: all 0.2s ease;
      
      &:hover {
        border-color: var(--primary-color);
        transform: translateY(-3px);
      }
      
      .gallery-img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.3s ease;
      }
      
      &:hover .gallery-img {
        transform: scale(1.1);
      }
    }
  }
}

.section-card {
  background-color: #fff;
  border-radius: var(--border-radius);
  box-shadow: var(--card-shadow);
  padding: 25px;
  margin-bottom: 40px;
}

.section-title {
  font-size: 22px;
  font-weight: 600;
  margin: 0 0 20px;
  color: var(--dark-color);
  position: relative;
  padding-left: 12px;
  
  &:before {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    width: 4px;
    height: 20px;
    background-color: var(--primary-color);
    border-radius: 2px;
  }
}

.services-list {
  .service-item {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 14px 10px;
    border-radius: 10px;
    transition: all 0.3s ease;
    
    .service-icon {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 40px;
      height: 40px;
      background-color: rgba(0, 113, 227, 0.1);
      border-radius: 8px;
    }
    
    &:hover {
      background-color: var(--secondary-color);
    }
  }
}

.hotel-room-types {
  margin-bottom: 40px;
  
  .room-type-list {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }
}

.room-type-card {
  padding: 20px;
  
  .room-image {
    height: 180px;
    border-radius: 10px;
    overflow: hidden;
    cursor: pointer;
    position: relative;
    
    &:hover::after {
      content: '查看房型图片';
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      padding: 8px;
      background: rgba(0, 0, 0, 0.5);
      color: white;
      font-size: 14px;
      text-align: center;
    }
    
    .room-img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: transform 0.3s ease;
    }
    
    &:hover .room-img {
      transform: scale(1.05);
    }
  }
  
  .room-info {
    padding: 10px 10px 10px 0;
    
    .room-title {
      font-size: 20px;
      font-weight: 600;
      margin: 0 0 15px;
      color: var(--dark-color);
    }
    
    .room-attributes {
      display: flex;
      flex-wrap: wrap;
      gap: 15px 30px;
      margin-bottom: 15px;
      
      .attribute-item {
        display: flex;
        align-items: center;
        
        .attribute-label {
          color: var(--gray-color);
          margin-right: 8px;
        }
        
        .attribute-value {
          font-weight: 500;
        }
      }
    }
    
    .room-desc {
      color: var(--gray-color);
      font-size: 14px;
      line-height: 1.5;
    }
  }
  
  .room-booking {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    justify-content: center;
    height: 100%;
    padding: 20px 10px;
    
    .room-price {
      margin-bottom: 5px;
      
      .price-value {
        font-size: 26px;
        font-weight: 700;
        color: var(--primary-color);
      }
      
      .price-unit {
        font-size: 14px;
        color: var(--gray-color);
      }
    }
    
    .room-stock {
      font-size: 14px;
      color: var(--gray-color);
      margin-bottom: 15px;
      
      &.no-rooms {
        color: var(--danger-color);
        font-weight: 500;
      }
    }
    
    .book-button {
      min-width: 120px;
      
      &.is-disabled {
        opacity: 0.7;
        background-color: #aaa !important;
        border-color: #aaa !important;
        color: #fff !important;
        cursor: not-allowed;
      }
    }
  }
}

.booking-dialog {
  :deep(.el-dialog__header) {
    padding: 20px 20px 15px;
    margin-bottom: 0;
    border-bottom: 1px solid #eaeaea;
    
    .el-dialog__title {
      font-size: 22px;
      font-weight: 600;
      color: var(--dark-color);
    }
    
    .el-dialog__headerbtn {
      top: 18px;
      right: 18px;
      
      .el-dialog__close {
        font-size: 18px;
        transition: transform 0.3s ease;
        
        &:hover {
          transform: rotate(90deg);
          color: var(--primary-color);
        }
      }
    }
  }
  
  :deep(.el-dialog__body) {
    padding: 25px 20px;
  }
  
  :deep(.el-form-item__label) {
    font-weight: 600;
    font-size: 15px;
    color: var(--dark-color);
    padding-bottom: 8px;
  }
  
  :deep(.el-input__wrapper) {
    box-shadow: 0 0 0 1px #d9d9d9 inset;
    padding: 2px 12px;
    height: 44px;
    border-radius: 10px;
    transition: all 0.3s;
    
    &:hover {
      box-shadow: 0 0 0 1px var(--primary-light) inset;
    }
    
    &.is-focus {
      box-shadow: 0 0 0 2px var(--primary-color) inset !important;
    }
  }
  
  :deep(.el-form-item.is-required .el-form-item__label:before) {
    color: var(--danger-color);
  }
  
  :deep(.el-date-editor) {
    width: 100%;
    
    .el-range-separator {
      color: var(--primary-color);
      font-weight: 600;
    }
  }
  
  .booking-submit-btn {
    width: 100%;
    height: 44px;
    font-size: 16px;
    font-weight: 500;
    border-radius: 10px;
    margin-top: 10px;
    background: var(--primary-color);
    border-color: var(--primary-color);
    transition: all 0.3s ease;
    
    &:hover {
      background: var(--primary-hover);
      border-color: var(--primary-hover);
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 113, 227, 0.3);
    }
    
    &:active {
      transform: translateY(0);
    }
  }
  
  :deep(.el-overlay) {
    backdrop-filter: blur(5px);
    background-color: rgba(0, 0, 0, 0.6);
  }
  
  :deep(.el-dialog) {
    border-radius: 16px;
    overflow: hidden;
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
  }
}

// 添加动画
@keyframes dialogFadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.booking-dialog {
  :deep(.el-dialog) {
    animation: dialogFadeIn 0.3s ease forwards;
  }
}

/* 自定义图片查看器样式 */
.custom-image-viewer {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 2000;
  display: flex;
  justify-content: center;
  align-items: center;
  
  .viewer-overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.85);
    z-index: 2001;
  }
  
  .viewer-container {
    position: relative;
    z-index: 2002;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
  }
  
  .viewer-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 20px;
    color: white;
    
    .viewer-counter {
      font-size: 16px;
    }
    
    .viewer-close-btn {
      background: none;
      border: none;
      color: white;
      font-size: 30px;
      cursor: pointer;
      width: 40px;
      height: 40px;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 50%;
      transition: background-color 0.3s;
      
      &:hover {
        background-color: rgba(255, 255, 255, 0.2);
      }
    }
  }
  
  .viewer-content {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    
    .viewer-image-container {
      width: 90%;
      height: 90%;
      display: flex;
      align-items: center;
      justify-content: center;
      
      .viewer-image {
        max-width: 100%;
        max-height: 100%;
        object-fit: contain;
        animation: fadeIn 0.3s ease;
      }
    }
    
    .viewer-nav-btn {
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
      background: rgba(0, 0, 0, 0.3);
      border: none;
      color: white;
      font-size: 24px;
      width: 50px;
      height: 50px;
      border-radius: 50%;
      cursor: pointer;
      transition: background-color 0.3s;
      display: flex;
      align-items: center;
      justify-content: center;
      z-index: 2003;
      
      &:hover {
        background-color: rgba(0, 0, 0, 0.6);
      }
      
      &.prev-btn {
        left: 20px;
      }
      
      &.next-btn {
        right: 20px;
      }
    }
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@media (max-width: 768px) {
  .hotel-main-image {
    height: 300px;
    margin-bottom: 20px;
  }
  
  .hotel-info-card {
    padding: 20px;
  }
  
  .hotel-title {
    font-size: 24px !important;
  }
  
  .room-image {
    margin-bottom: 15px;
  }
  
  .room-booking {
    align-items: flex-start !important;
    padding-left: 0 !important;
    margin-top: 15px;
  }
  
  .custom-image-viewer {
    .viewer-nav-btn {
      width: 40px;
      height: 40px;
      font-size: 20px;
    }
  }
}

@media (max-width: 480px) {
  .hotel-main-image {
    height: 220px;
  }
  
  .service-item {
    padding: 10px 5px !important;
  }
  
  .custom-image-viewer {
    .viewer-nav-btn {
      width: 36px;
      height: 36px;
      font-size: 18px;
    }
    
    .viewer-image-container {
      width: 95%;
    }
  }
}
</style>