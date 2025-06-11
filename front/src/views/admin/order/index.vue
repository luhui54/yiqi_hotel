<script setup>
import {ref, reactive} from 'vue'
import {GetPage, Cancel, CheckOut} from "^/admin/order.js";
import {ElMessage, ElMessageBox} from "element-plus"

const data = reactive({
  searchForm: {},

  pageData: [],
  page: {
    current: 1,
    size: 10
  },
  total: 0,
})

const init = () => {
  GetPage({
    ...data.page,
    ...data.searchForm
  }).then(res => {
    data.pageData = res.data.records
    data.total = res.data.total
  })
}
init()

const handleCurrentChange = (val) => {
  data.page.current = val
  init()
}

import Edit from './edit.vue'

const editRef = ref()
const checkInHandle = (id) => {
  editRef.value.init(id)
}
const handleClose = (hasChange) => {
  if (hasChange) {
    init()
  }
}

const cancelHandle = (id) => {
  ElMessageBox.confirm('确认取消该订单吗？', '警告', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    Cancel(id).then(res => {
      ElMessage.success("取消成功")
      init()
    })
  })
}
const checkOutHandle = (id) => {
  ElMessageBox.confirm('确认执行退房操作吗？', '警告', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    CheckOut(id).then(res => {
      ElMessage.success("退房成功")
      init()
    })
  })
}

</script>

<template>
  <div>
    <el-form label-width="auto" :inline="true">
      <el-form-item>
        <el-input v-model="data.searchForm.sn" placeholder="订单号"/>
      </el-form-item>

      <el-form-item>
        <el-button @click="init">搜索</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="data.pageData" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>

      <el-table-column prop="sn" label="订单编号"/>

      <el-table-column prop="hotelTitle" label="酒店名称"/>
      <el-table-column prop="roomTypeName" label="房型"/>
      <el-table-column prop="roomSn" label="房间"/>

      <el-table-column prop="guestName" label="入住人名字"/>
      <el-table-column prop="bookCheckInTime" label="预订入住时间"/>
      <el-table-column prop="realCheckInTime" label="实际入住时间"/>
      <el-table-column prop="realCheckOutTime" label="实际退房时间"/>

      <el-table-column prop="price" label="价格"/>
      <el-table-column prop="statusStr" label="状态"></el-table-column>
      <el-table-column prop="bookTime" label="预订时间"/>

      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="text" @click="cancelHandle(scope.row.id)" v-if="scope.row.status === 1">取消</el-button>
          <el-button type="text" @click="checkInHandle(scope.row.id)" v-if="scope.row.status === 2">入住</el-button>
          <el-button type="text" @click="checkOutHandle(scope.row.id)" v-if="scope.row.status === 3">退房</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        style="margin-top: 16px;"
        background
        layout="total, prev, pager, next"
        :page-size="data.page.size"
        :total="data.total"
        @current-change="handleCurrentChange"
    />

    <Edit ref="editRef" @handleClose="handleClose"></Edit>
  </div>
</template>

<style lang="scss" scoped>

</style>