<script setup>
  import {ref, reactive} from 'vue'
  import {GetPage, Delete} from "^/admin/order.js";
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
  const editHandle = (id) => {
    editRef.value.init(id)
  }
  const handleClose = (hasChange) => {
    if (hasChange) {
      init()
    }
  }

  const delBtnStatus = ref(true)
  let selectIds = []
  const delHandle = (id) => {
    const ids = []
    if (id) {
      ids.push(id)
    } else {
      ids.push(...selectIds)
    }

    ElMessageBox.confirm('确认删除该记录吗？', '警告', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }).then(() => {
      Delete(ids).then(res => {
        ElMessage.success("删除成功")
        init()
      })
    })
  }

  const handleSelectionChange = (selects) => {
    delBtnStatus.value = selects.length === 0
    selectIds = selects.map(e => e.id)
  }

</script>

<template>
  <div>
    <el-form label-width="auto" :inline="true">
      <el-form-item>
        <el-input v-model="data.searchForm.username" placeholder="XXX字段"/>
      </el-form-item>

      <el-form-item>
        <el-button @click="init">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="editHandle(null)">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="delHandle(null)" :disabled="delBtnStatus">批量删除</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="data.pageData" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>

                  <el-table-column prop="id" label=""/>
                        <el-table-column prop="sn" label="订单编号"/>
                        <el-table-column prop="userId" label=""/>
                        <el-table-column prop="hotelId" label=""/>
                        <el-table-column prop="roomTypeId" label=""/>
                        <el-table-column prop="roomId" label=""/>
                        <el-table-column prop="roomSn" label=""/>
                        <el-table-column prop="guestName" label="入住人名字"/>
                        <el-table-column prop="guestPhone" label="入住人手机号"/>
                        <el-table-column prop="bookTime" label="预订时间"/>
                        <el-table-column prop="bookCheckInTime" label="预订入住时间"/>
                        <el-table-column prop="bookCheckOutTime" label="预订退房时间"/>
                        <el-table-column prop="realCheckInTime" label="实际入住时间"/>
                        <el-table-column prop="realCheckOutTime" label="实际退房时间"/>
                        <el-table-column prop="hotelTitle" label=""/>
                        <el-table-column prop="roomTypeName" label=""/>
                        <el-table-column prop="roomTypeImage" label=""/>
                        <el-table-column prop="price" label="价格"/>
                        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag v-if="scope.row.status === 0">正常</el-tag>
            <el-tag type="danger" v-if="scope.row.status === 1">其他</el-tag>
          </template>
        </el-table-column>
                        <el-table-column prop="createTime" label=""/>
          
      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="text" @click="editHandle(scope.row.id)">编辑</el-button>
          <el-button type="text" @click="delHandle(scope.row.id)">删除</el-button>
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