<script setup>

import {Search} from "@element-plus/icons-vue";
import moment from "moment";
import {useRoute, useRouter} from "vue-router";
import {reactive, ref} from "vue";

const router = useRouter();
const route = useRoute();

const props = defineProps({
  "ranks": {
    type: Array,
    required: true,
    default: []
  }
})

const state = reactive({
  checkTime: [route.query.checkInTime, route.query.checkOutTime],
  searchForm: {
    destination: route.query.destination || '广州',
    keyword: route.query.keyword || '',
    // 酒店星级 传参 对应
    rank: Number(route.query.rank) || '',
    checkInTime: route.query.checkInTime || '',
    checkOutTime: route.query.checkOutTime || ''
  },
  isWarningShown: ref(false),
  rangeSeparator: "1晚",
})


// 禁用当前日期之前的日期 不可选状态
const disableDateFn = (date) => {
  return date < moment().subtract(1, 'days')
}

const changeCheckTimeFn = (val) => {
  const count = moment(val[1]).diff(moment(val[0]), 'day')
  state.rangeSeparator = count + '晚'

  state.searchForm.checkInTime = moment(val[0]).format("YYYY-MM-DD")
  state.searchForm.checkOutTime = moment(val[1]).format("YYYY-MM-DD")
}

const emit = defineEmits(["searchFn"])


const searchFn = () => {
  emit("searchFn", state.searchForm)
}


const init = async () => {
  if (!state.searchForm.checkInTime) {
    state.checkTime = [moment(), moment().add(1, 'days')]
  }
  changeCheckTimeFn(state.checkTime)

  if (route.name === "hotel-list" ) {
    searchFn()
  }
}

init()

</script>

<template>
  <div class="search-container">
    <div class="search-row">
      <div class="search-left-fields">
        <div class="search-group destination-group">
          <div class="search-label">
            <span class="label-text">目的地</span>
          </div>
          <el-input 
            v-model="state.searchForm.destination" 
            placeholder="请输入目的地" 
            size="large" 
            class="search-input"
          ></el-input>
        </div>
        
        <div class="search-group date-group">
          <div class="search-label">
            <span class="label-text">入住 - 退房</span>
          </div>
          <el-date-picker 
            size="large"
            :disabled-date="disableDateFn"
            v-model="state.checkTime"
            type="daterange"
            :range-separator="state.rangeSeparator"
            start-placeholder="入住日期"
            end-placeholder="退房日期"
            @change="changeCheckTimeFn"
            class="search-date-picker"
          />
        </div>
        
        <div class="search-group keyword-group">
          <div class="search-label">
            <span class="label-text">关键词</span>
          </div>
          <el-input 
            clearable 
            v-model="state.searchForm.keyword" 
            placeholder="品牌/地址/酒店名称" 
            size="large"
            class="search-input"
          ></el-input>
        </div>
        
        <div class="search-group rank-group">
          <div class="search-label">
            <span class="label-text">酒店星级</span>
          </div>
          <el-select 
            clearable 
            v-model="state.searchForm.rank" 
            placeholder="请选择酒店星级" 
            size="large"
            class="search-select"
          >
            <el-option 
              v-for="item in props.ranks"
              :key="item.key"
              :label="item.value"
              :value="item.key"
            >
            </el-option>
          </el-select>
        </div>
      </div>
      
      <div class="search-button-wrapper">
        <el-button 
          type="primary" 
          size="large" 
          :icon="Search" 
          @click="searchFn"
          class="search-button"
        >搜索</el-button>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.search-container {
  padding: 24px 30px;
  background-color: #fff;
}

.search-row {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 20px;
}

.search-left-fields {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  flex: 1;
}

.search-group {
  flex-grow: 0;
  flex-shrink: 0;
  margin-bottom: 5px;
  
  .search-label {
    margin-bottom: 10px;
    
    .label-text {
      font-size: 15px;
      font-weight: 600;
      color: var(--dark-color);
      white-space: nowrap;
      position: relative;
      
      &:after {
        content: '';
        position: absolute;
        left: 0;
        bottom: -4px;
        width: 20px;
        height: 2px;
        background-color: var(--primary-color);
        border-radius: 2px;
      }
    }
  }
}

.destination-group {
  flex-basis: 160px;
}

.date-group {
  flex-basis: 300px;
}

.keyword-group {
  flex-basis: 160px;
}

.rank-group {
  flex-basis: 160px;
}

.search-input,
.search-date-picker,
.search-select {
  width: 100%;
  
  :deep(.el-input__wrapper) {
    border-radius: 8px;
    padding: 0 15px;
    height: 46px;
    box-shadow: none;
    border: 1px solid #dcdfe6;
    transition: all 0.3s ease;
    
    &:hover, &.is-focus {
      border-color: var(--primary-color);
      box-shadow: 0 0 0 1px var(--primary-color);
    }
  }
  
  :deep(.el-input__inner) {
    height: 46px;
    font-size: 14px;
  }
  
  :deep(.el-range-separator) {
    color: var(--gray-color);
  }
  
  :deep(.el-range-input) {
    width: 42%;
  }
}

.search-button-wrapper {
  display: flex;
  align-items: flex-end;
  padding-bottom: 4px;
  
  .search-button {
    min-width: 120px;
    height: 46px;
    border-radius: 8px;
    background-color: var(--primary-color);
    border-color: var(--primary-color);
    font-weight: 500;
    transition: var(--transition);
    padding: 0 24px;
    
    &:hover {
      background-color: var(--primary-hover);
      border-color: var(--primary-hover);
      transform: translateY(-2px);
      box-shadow: 0 4px 10px rgba(0, 113, 227, 0.3);
    }
  }
}

@media (max-width: 1200px) {
  .search-left-fields {
    flex-wrap: wrap;
    justify-content: space-between;
  }
  
  .destination-group,
  .keyword-group {
    flex-basis: calc(33% - 15px);
  }
  
  .date-group {
    flex-basis: calc(33% - 15px);
  }
  
  .rank-group {
    flex-basis: calc(33% - 15px);
  }
}

@media (max-width: 992px) {
  .search-row {
    flex-direction: column;
  }
  
  .search-left-fields {
    width: 100%;
  }
  
  .destination-group,
  .date-group,
  .keyword-group, 
  .rank-group {
    flex-basis: calc(50% - 10px);
  }
  
  .search-button-wrapper {
    width: 100%;
    padding-top: 10px;
    
    .search-button {
      width: 100%;
    }
  }
}

@media (max-width: 768px) {
  .destination-group,
  .date-group,
  .keyword-group,
  .rank-group {
    flex-basis: 100%;
  }
  
  .search-container {
    padding: 20px;
  }
}
</style>