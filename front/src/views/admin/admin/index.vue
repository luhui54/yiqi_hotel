<script setup>
import {ref, reactive} from 'vue'
import {GetPage, Info, Delete, ResetPwd} from "^/admin/admin.js";
import {ElMessage, ElMessageBox} from "element-plus"

const data = reactive({
  searchForm: {},

  pageData: [],
  page: {
    current: 1,
    size: 10
  },
  total: 0,

  infoDrawer: false,
  user: {}
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
const infoHandle = (id) => {
  Info(id).then(res => {
    data.user = res.data

    data.infoDrawer = true
  })
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

const resetHandle = (id) => {
  ElMessageBox.confirm('确认重置密码为888888吗？', '警告', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    ResetPwd(id).then(res => {
      ElMessage.success("重置成功")
    })
  })
}

</script>

<template>
  <div>
    <el-form label-width="auto" :inline="true">
      <el-form-item>
        <el-input v-model="data.searchForm.username" placeholder="用户名"/>
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
      <el-table-column prop="avatar" label="头像">
        <template #default="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="role" label="角色"/>
      <el-table-column prop="hotelTitle" label="管理酒店"/>
      <el-table-column prop="email" label="email"/>
      <el-table-column prop="phone" label="手机号码"/>
      <el-table-column prop="createTime" label="创建时间"/>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="text" @click="infoHandle(scope.row.id)">查看</el-button>
          <el-button type="text" @click="editHandle(scope.row.id)">编辑</el-button>
          <el-button type="text" @click="delHandle(scope.row.id)">删除</el-button>
          <el-button type="text" @click="resetHandle(scope.row.id)">重置密码</el-button>
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

    <el-drawer
        v-model="data.infoDrawer"
        title="详情"
    >
      <el-descriptions border column="2" title="详情">
        <el-descriptions-item label="头像">
          <el-avatar :src="data.user.avatar"></el-avatar>
        </el-descriptions-item>
        <el-descriptions-item label="用户名">{{ data.user.username }}</el-descriptions-item>
        <el-descriptions-item label="手机号码">{{ data.user.phone }}</el-descriptions-item>
        <el-descriptions-item label="email">{{ data.user.email }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ data.user.createTime }}</el-descriptions-item>
        <el-descriptions-item label="最后登录">{{ data.user.lastLoginTime }}</el-descriptions-item>
      </el-descriptions>

      <el-button style="margin-top: 16px;" @click="data.infoDrawer=false">关闭</el-button>
    </el-drawer>

    <Edit ref="editRef" @handleClose="handleClose"></Edit>
  </div>
</template>

<style lang="scss" scoped>

</style>