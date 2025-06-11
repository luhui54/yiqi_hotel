<script setup>
import {Checked, OfficeBuilding, Search, StarFilled, Timer} from "@element-plus/icons-vue";
import {GetIndex} from "^/app/index.js";
import {reactive, ref} from "vue";
import moment from "moment";

import SearchCard from "~/SearchCard.vue";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
const router = useRouter()


const state = reactive({
  hotels: [],
  ranks: [],
  searchForm: {
    destination: '',
    keyword: '',
    rank: '',
    checkInTime: moment().format('YYYY-MM-DD'),
    checkOutTime: moment().add(1, 'days').format('YYYY-MM-DD')
  }
})

const init = async () => {
  GetIndex().then(res => {
    state.hotelList = res.data.hotelList
    state.ranks = res.data.ranks
  })
}

const toHotelDetailFn = (hotelId) => {
  router.push('/hotel-detail/' + hotelId)
}

let isWarningShown = false

const searchFn = (searchForm) => {
  if (!searchForm.destination) {
    if (!isWarningShown) {
      ElMessage.warning("请填写目的地！")
      isWarningShown = true
    }
  } else {
    // 重置警告标志，以便下次搜索时重新检查
    isWarningShown = false
    // 执行搜索逻辑
    console.log('执行搜索逻辑')
  }

  const params = new URLSearchParams(searchForm).toString()
  router.push('/hotel-list?' + params)
}

const searchByCity = (city) => {
  const searchForm = {
    destination: city,
    keyword: '',
    rank: '',
    checkInTime: state.searchForm?.checkInTime || moment().format('YYYY-MM-DD'),
    checkOutTime: state.searchForm?.checkOutTime || moment().add(1, 'days').format('YYYY-MM-DD')
  }
  searchFn(searchForm)
}

init()

</script>

<template>
  <!-- 顶部横幅 -->
  <div class="banner-container">
    <div class="banner-overlay">
      <div class="banner-content">
        <h1>发现理想的住宿体验</h1>
        <p>精选全球酒店，为您提供无与伦比的入住体验</p>
      </div>
    </div>
    <div class="banner-slider">
      <img src="@/assets/banner/1.png" alt="艺柒酒店横幅" class="banner-image">
    </div>
  </div>

  <div class="container main-content">
    <!-- 搜索卡片 -->
    <div class="search-card-container">
      <el-card class="search-card" shadow="hover">
        <div class="search-tabs">
          <div class="search-tab active">
            <el-icon class="tab-icon"><OfficeBuilding /></el-icon>
            酒店预订
          </div>
        </div>
        <search-card @searchFn="searchFn" :ranks="state.ranks"></search-card>
      </el-card>
    </div>

    <!-- 精选榜单部分 -->
    <div class="section featured-section">
      <div class="section-header">
        <h2 class="section-title">精选榜单</h2>
        <p class="section-subtitle">你喜欢的酒店类型，我们这里都有</p>
      </div>

      <el-row :gutter="24">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="item in state.hotelList" :key="item.id" class="hotel-col">
          <div class="hotel-card apple-card" @click="toHotelDetailFn(item.id)">
            <div class="hotel-image">
              <img :src="item.image" alt="酒店图片">
              <div class="hotel-badge" v-if="item.score >= 4.5">优选</div>
            </div>
            <div class="hotel-info">
              <h3 class="hotel-title">{{item.title}}</h3>
              <div class="hotel-location">
                <el-icon><OfficeBuilding /></el-icon>
                <span>{{item.location || '广州市'}}</span>
              </div>
              <div class="hotel-meta">
                <span class="price-tag">¥ {{ item.minPrice }}<small>起</small></span>
                <div class="rating-tag">
                  <el-icon><StarFilled /></el-icon>
                  <span>{{ item.score }}</span>
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 热门城市部分 -->
    <div class="section popular-cities-section">
      <div class="section-header">
        <h2 class="section-title">热门城市</h2>
        <p class="section-subtitle">不同城市，总有你喜欢的酒店</p>
      </div>

      <div class="cities-container">
        <div class="city-grid">
          <div class="city-card" @click="searchByCity('广州')">
            <div class="city-image">
              <img src="@/assets/banner/1.png" alt="广州" onerror="this.src='@/assets/banner/1.png'">
              <div class="city-overlay">
                <h3 class="city-name">广州</h3>
                <p class="city-hotels">2800+ 家酒店</p>
              </div>
            </div>
          </div>
          
          <div class="city-card" @click="searchByCity('深圳')">
            <div class="city-image">
              <img src="@/assets/banner/1.png" alt="深圳" onerror="this.src='@/assets/banner/1.png'">
              <div class="city-overlay">
                <h3 class="city-name">深圳</h3>
                <p class="city-hotels">2500+ 家酒店</p>
              </div>
            </div>
          </div>
          
          <div class="city-card" @click="searchByCity('上海')">
            <div class="city-image">
              <img src="@/assets/banner/1.png" alt="上海" onerror="this.src='@/assets/banner/1.png'">
              <div class="city-overlay">
                <h3 class="city-name">上海</h3>
                <p class="city-hotels">3200+ 家酒店</p>
              </div>
            </div>
          </div>
          
          <div class="city-card" @click="searchByCity('北京')">
            <div class="city-image">
              <img src="@/assets/banner/1.png" alt="北京" onerror="this.src='@/assets/banner/1.png'">
              <div class="city-overlay">
                <h3 class="city-name">北京</h3>
                <p class="city-hotels">3500+ 家酒店</p>
              </div>
            </div>
          </div>
          
          <div class="city-card" @click="searchByCity('杭州')">
            <div class="city-image">
              <img src="@/assets/image/1.jpg" alt="杭州" onerror="this.src='@/assets/image/1.jpg'">
              <div class="city-overlay">
                <h3 class="city-name">杭州</h3>
                <p class="city-hotels">1800+ 家酒店</p>
              </div>
            </div>
          </div>
          
          <div class="city-card" @click="searchByCity('成都')">
            <div class="city-image">
              <img src="@/assets/image/2.jpg" alt="成都" onerror="this.src='@/assets/image/2.jpg'">
              <div class="city-overlay">
                <h3 class="city-name">成都</h3>
                <p class="city-hotels">2100+ 家酒店</p>
              </div>
            </div>
          </div>
          
          <div class="city-card" @click="searchByCity('重庆')">
            <div class="city-image">
              <img src="@/assets/image/3.jpg" alt="重庆" onerror="this.src='@/assets/image/3.jpg'">
              <div class="city-overlay">
                <h3 class="city-name">重庆</h3>
                <p class="city-hotels">1900+ 家酒店</p>
              </div>
            </div>
          </div>
          
          <div class="city-card" @click="searchByCity('三亚')">
            <div class="city-image">
              <img src="@/assets/image/4.jpg" alt="三亚" onerror="this.src='@/assets/image/4.jpg'">
              <div class="city-overlay">
                <h3 class="city-name">三亚</h3>
                <p class="city-hotels">1200+ 家酒店</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 品牌优势部分 -->
    <div class="brand-section">
      <h2 class="section-title text-center">订酒店 就艺柒</h2>
      
      <div class="brand-features">
        <div class="feature-item">
          <div class="feature-icon">
            <el-icon :size="54" color="var(--primary-color)"><OfficeBuilding /></el-icon>
          </div>
          <h3>全球110万家酒店</h3>
          <p>为您提供最好的入住体验</p>
        </div>
        <div class="feature-item">
          <div class="feature-icon">
            <el-icon :size="54" color="var(--primary-color)"><Timer /></el-icon>
          </div>
          <h3>7*24h全天客服</h3>
          <p>帮您解决所有问题</p>
        </div>
        <div class="feature-item">
          <div class="feature-icon">
            <el-icon :size="54" color="var(--primary-color)"><Checked /></el-icon>
          </div>
          <h3>有房保障</h3>
          <p>让您安心入住</p>
        </div>
      </div>
    </div>
  </div>

  <!-- 页脚 -->
  <el-footer class="footer">
    <div class="footer-content">
      <span>© 2000 - 2024 佛山艺柒信息技术有限公司, eQi, Inc or its affiliates. All Rights Reserved.</span>
    </div>
  </el-footer>
</template>

<style lang="scss" scoped>
// 横幅样式
.banner-container {
  position: relative;
  height: 500px;
  overflow: hidden;
  
  .banner-slider {
    width: 100%;
    height: 100%;
  }
  
  .banner-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .banner-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background: linear-gradient(to right, rgba(0, 0, 0, 0.7) 0%, rgba(0, 0, 0, 0.4) 50%, rgba(0, 0, 0, 0.1) 100%);
    z-index: 1;
    
    .banner-content {
      max-width: 1200px;
      width: 100%;
      padding: 0 30px;
      text-align: left;
      
      h1 {
        font-size: 48px;
        font-weight: 700;
        margin-bottom: 16px;
        color: white;
        text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
        max-width: 600px;
      }
      
      p {
        font-size: 24px;
        color: white;
        max-width: 500px;
        text-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
      }
    }
  }
}

// 主内容区域
.main-content {
  margin-top: -80px;
  position: relative;
  z-index: 2;
  padding-bottom: 60px;
}

// 搜索卡片容器
.search-card-container {
  margin-bottom: 60px;
  
  .search-card {
    border-radius: 20px;
    box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
    transition: var(--transition);
    overflow: hidden;
    
    &:hover {
      box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
    }
    
    :deep(.el-card__body) {
      padding: 0;
    }
  }
  
  .search-tabs {
    display: flex;
    background-color: #f7f9fc;
    padding: 0 20px;
    border-bottom: 1px solid #eaedf3;
    
    .search-tab {
      padding: 18px 24px;
      font-size: 16px;
      font-weight: 600;
      color: var(--gray-color);
      cursor: pointer;
      position: relative;
      display: flex;
      align-items: center;
      gap: 8px;
      
      .tab-icon {
        font-size: 18px;
      }
      
      &.active {
        color: var(--primary-color);
        
        &:after {
          content: '';
          position: absolute;
          bottom: 0;
          left: 0;
          width: 100%;
          height: 3px;
          background-color: var(--primary-color);
          border-radius: 3px 3px 0 0;
        }
      }
    }
  }
}

// 精选榜单部分
.featured-section {
  margin-bottom: 60px;

  .section-header {
    margin-bottom: 32px;
    text-align: left;
  }
  
  .hotel-col {
    margin-bottom: 24px;
  }
}

// 酒店卡片样式
.hotel-card {
  height: 100%;
  cursor: pointer;
  position: relative;
  
  .hotel-image {
    height: 200px;
    overflow: hidden;
    position: relative;
    
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: transform 0.5s ease;
    }
    
    .hotel-badge {
      position: absolute;
      top: 12px;
      left: 12px;
      background-color: var(--primary-color);
      color: white;
      padding: 4px 10px;
      border-radius: 4px;
      font-size: 12px;
      font-weight: 600;
      box-shadow: 0 2px 8px rgba(0, 113, 227, 0.3);
    }
  }
  
  &:hover .hotel-image img {
    transform: scale(1.05);
  }
  
  .hotel-info {
    padding: 20px;
    
    .hotel-title {
      font-size: 18px;
      font-weight: 600;
      margin-bottom: 8px;
      color: var(--dark-color);
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    
    .hotel-location {
      display: flex;
      align-items: center;
      gap: 5px;
      color: var(--gray-color);
      font-size: 14px;
      margin-bottom: 12px;
    }
    
    .hotel-meta {
      display: flex;
      justify-content: space-between;
      align-items: center;
      
      .price-tag {
        small {
          font-weight: normal;
          font-size: 14px;
          margin-left: 2px;
        }
      }
    }
  }
}

// 品牌部分
.brand-section {
  margin: 80px 0;
  padding: 60px 0;
  background-color: var(--secondary-color);
  border-radius: 24px;
  
  .text-center {
    text-align: center;
  }
  
  .brand-features {
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
    margin-top: 40px;
    
    .feature-item {
      flex: 0 0 300px;
      text-align: center;
      padding: 20px;
      
      .feature-icon {
        margin-bottom: 20px;
        height: 80px;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      
      h3 {
        font-size: 20px;
        margin: 0 0 10px;
        color: var(--dark-color);
        font-weight: 600;
      }
      
      p {
        color: var(--gray-color);
        font-size: 16px;
        margin: 0;
      }
    }
  }
}

// 页脚
.footer {
  background-color: var(--dark-color);
  color: white;
  padding: 20px 0;
  text-align: center;
  
  .footer-content {
    max-width: 1200px;
    margin: 0 auto;
    font-size: 14px;
    padding: 0 15px;
  }
}

// 热门城市部分
.popular-cities-section {
  margin-top: 40px;
  margin-bottom: 80px;
}

.cities-container {
  margin-top: 30px;
}

.city-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
}

.city-card {
  cursor: pointer;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: var(--card-shadow);
  transition: var(--transition);
  position: relative;
  padding-bottom: 75%; /* 保持4:3的宽高比 */
  
  &:hover {
    transform: translateY(-5px);
    box-shadow: var(--hover-shadow);
    
    .city-image img {
      transform: scale(1.08);
    }
  }
  
  .city-image {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: transform 0.5s ease;
    }
    
    .city-overlay {
      position: absolute;
      bottom: 0;
      left: 0;
      width: 100%;
      padding: 20px;
      background: linear-gradient(to top, rgba(0,0,0,0.8) 0%, rgba(0,0,0,0.2) 100%);
      color: white;
      
      .city-name {
        font-size: 22px;
        font-weight: 600;
        margin: 0 0 5px 0;
        text-shadow: 0 1px 3px rgba(0,0,0,0.3);
      }
      
      .city-hotels {
        font-size: 14px;
        margin: 0;
        opacity: 0.9;
      }
    }
  }
}

@media (max-width: 1200px) {
  .city-grid {
    grid-template-columns: repeat(3, 1fr);
  }
  
  .banner-container .banner-overlay .banner-content h1 {
    font-size: 40px;
  }
}

@media (max-width: 768px) {
  .city-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .banner-container {
    height: 400px;
  }
  
  .banner-container .banner-overlay .banner-content {
    h1 {
      font-size: 32px;
    }
    
    p {
      font-size: 18px;
    }
  }
  
  .brand-section .brand-features .feature-item {
    flex: 0 0 100%;
    max-width: 300px;
    margin: 0 auto 30px;
  }
}

@media (max-width: 480px) {
  .city-grid {
    grid-template-columns: 1fr;
  }
  
  .banner-container {
    height: 350px;
  }
  
  .banner-container .banner-overlay .banner-content {
    h1 {
      font-size: 28px;
    }
    
    p {
      font-size: 16px;
    }
  }
}
</style>