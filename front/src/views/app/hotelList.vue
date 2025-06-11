<script setup>
import {ref, reactive} from "vue";
import SearchCard from "~/SearchCard.vue";
import {GetDict, Search} from "^/app/hotel.js";
import {StarFilled} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";
const router = useRouter()

const state = reactive({
  ranks: [],
  prices: [],
  scores: [],
  brands: [],

  filterForm: {
    price: '',
    score: '',
    brand: ''
  },

  searchForm: {
    destination: '广州',
    checkInTime: '',
    checkOutTime: ''
  },

  total: 0,
  hotels: [],
  page: {
    current: 1,
    size: 2
  },

  loadMoreBtn: false,
})


const loadHotelData = () => {
  const params = {
    ...state.searchForm,
    ...state.filterForm,
    ...state.page
  }

  Search(params).then(res => {
    state.hotels.push(...res.data.records);
    state.total = res.data.total;
    if (state.total === state.hotels.length) {
      state.loadMoreBtn = true;
    }

  })
}

const searchFn = (searchForm) => {

  state.searchForm = searchForm

  state.hotels = []
  state.page.current = 1
  state.loadMoreBtn = false
  state.total = 0

  loadHotelData()
}

const toHotelDetailFn = (hotelId) => {
  router.push('/hotel-detail/' + hotelId + '?checkInTime=' + state.searchForm.checkInTime + '&checkOutTime=' + state.searchForm.checkOutTime)
}

const init = () => {
  GetDict().then(res => {
    state.ranks = res.data.rankDict;
    state.prices = res.data.priceDict;
    state.scores = res.data.scoreDict;
    state.brands = res.data.brandDict;
  })

  // loadHotelData()
}

init()

const loadMore = () => {
  state.page.current++;
  loadHotelData()
}
</script>


<template>
  <div class="container" style="display: grid; gap: 20px">
    <el-card shadow="never">
      <search-card :ranks="state.ranks" @search-fn="searchFn"></search-card>
    </el-card>

    <el-descriptions border class="my-desc">
      <el-descriptions-item label="筛选" label-align="center">
        <el-space>
          <strong>评分：</strong>
          <el-radio-group v-model="state.filterForm.score">
            <el-radio value="" size="large">全部</el-radio>
            <el-radio :value="item.key" size="large" v-for="item in state.scores">{{ item.value }}</el-radio>
          </el-radio-group>
        </el-space>

        <el-space>
          <strong>品牌：</strong>
          <el-radio-group v-model="state.filterForm.brand">
            <el-radio value="" size="large">全部</el-radio>
            <el-radio :value="item.key" size="large" v-for="item in state.brands">{{ item.value }}</el-radio>
          </el-radio-group>
        </el-space>

        <el-space>
          <strong>价格：</strong>
          <el-radio-group v-model="state.filterForm.price">
            <el-radio value="" size="large">全部</el-radio>
            <el-radio :value="item.key" size="large" v-for="item in state.prices">{{ item.value }}</el-radio>
          </el-radio-group>
        </el-space>

        <!--        <el-space>-->
        <!--          <strong>星级：</strong>-->
        <!--          <el-radio-group v-model="state.filterForm.rank">-->
        <!--        <el-radio value="" size="large">全部</el-radio>-->
        <!--            <el-radio :value="item.key" size="large" v-for="item in state.ranks">{{ item.value }}</el-radio>-->
        <!--          </el-radio-group>-->
        <!--        </el-space>-->
      </el-descriptions-item>
    </el-descriptions>

    <el-row :gutter="20">
      <el-col :span="6" v-for="item in state.hotels">
        <el-card shadow="never" :body-style="{ padding: '0' }" @click="toHotelDetailFn(item.id)">
          <img :src="item.image" width=100% height=200px alt="">

          <div style="padding: 20px">
            <p style="font-size: 20px; font-weight: bold;margin-bottom: 10px">{{ item.title }}</p>
            <div style="display: flex; align-items: center;">
              <span style="color: #e64340;margin-right: 10px">¥ {{ item.minPrice }}起</span>
              <el-icon>
                <StarFilled/>
              </el-icon>
              <span style="margin-left: 5px">{{ item.score }} 评分</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-empty description="抱歉噢～ 没有查询到相关酒店信息！" v-if="state.total === 0"></el-empty>

    <div style="text-align: center" v-if="state.total > 0">
      <el-button type="primary" @click="loadMore" :disabled="state.loadMoreBtn">
        {{ state.loadMoreBtn ? "没有更多了" : "加载更多" }}
      </el-button>
    </div>
  </div>
</template>

<style lang="scss">
.my-desc .el-descriptions__label {
  width: 150px;
}
</style>

