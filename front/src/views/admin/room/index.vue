<script setup>
import {ref, reactive} from 'vue'
import {GetPage, Delete} from "^/admin/room.js";
import {ElMessage, ElMessageBox} from "element-plus"
import {
  Search,
  Plus,
  Delete as DeleteIcon,
  Edit,
  RefreshRight
} from '@element-plus/icons-vue'

const data = reactive({
  searchForm: {},
  pageData: [],
  page: {
    current: 1,
    size: 10
  },
  total: 0,
  loading: false
})

const init = () => {
  data.loading = true
  GetPage({
    ...data.page,
    ...data.searchForm
  }).then(res => {
    data.pageData = res.data.records
    data.total = res.data.total
  }).finally(() => {
    data.loading = false
  })
}
init()

const handleCurrentChange = (val) => {
  data.page.current = val
  init()
}

const resetSearch = () => {
  data.searchForm = {}
  init()
}

import EditComponent from './edit.vue'

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
  <div class="room-container">
    <el-card shadow="hover" class="search-card">
      <div class="card-header">
        <h3>房间管理</h3>
      </div>
      
      <el-form :model="data.searchForm" label-width="auto" :inline="true" class="search-form">
        <el-form-item label="酒店名称">
          <el-input 
            v-model="data.searchForm.hotelTitle" 
            placeholder="请输入酒店名称" 
            clearable
          />
        </el-form-item>

        <el-form-item class="search-buttons">
          <el-button 
            type="primary" 
            @click="init" 
            :icon="Search"
            :loading="data.loading"
          >
            搜索
          </el-button>
          <el-button @click="resetSearch" :icon="RefreshRight">重置</el-button>
        </el-form-item>
      </el-form>
      
      <div class="table-operations">
        <el-button 
          type="primary" 
          @click="editHandle(null)" 
          :icon="Plus"
        >
          新增房间
        </el-button>
        <el-button 
          type="danger" 
          @click="delHandle(null)" 
          :disabled="delBtnStatus"
          :icon="DeleteIcon"
        >
          批量删除
        </el-button>
      </div>

      <el-table 
        :data="data.pageData" 
        border 
        stripe
        v-loading="data.loading"
        class="data-table"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="hotelTitle" label="所属酒店" min-width="180" show-overflow-tooltip></el-table-column>
        <el-table-column prop="roomTypeName" label="所属房型" min-width="150" show-overflow-tooltip></el-table-column>
        <el-table-column prop="sn" label="房间号" min-width="120" align="center"></el-table-column>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.status === 0" type="success" effect="light">空闲</el-tag>
            <el-tag v-if="scope.row.status === 1" type="danger" effect="light">入住</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="160" align="center"></el-table-column>

        <el-table-column label="操作" width="150" fixed="right" align="center">
          <template #default="scope">
            <el-button 
              type="primary" 
              link 
              @click="editHandle(scope.row.id)"
              :icon="Edit"
            >
              编辑
            </el-button>
            <el-button 
              type="danger" 
              link 
              @click="delHandle(scope.row.id)"
              :icon="DeleteIcon"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <el-pagination
            background
            layout="total, prev, pager, next, jumper"
            :page-size="data.page.size"
            :total="data.total"
            :current-page="data.page.current"
            @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <EditComponent ref="editRef" @handleClose="handleClose"></EditComponent>
  </div>
</template>

<style lang="scss" scoped>
.room-container {
  .search-card {
    margin-bottom: 20px;
    
    .card-header {
      margin-bottom: 20px;
      
      h3 {
        font-size: 18px;
        font-weight: 600;
        margin: 0;
        color: #1e293b;
      }
    }
    
    .search-form {
      margin-bottom: 16px;
      
      .search-buttons {
        margin-bottom: 0;
      }
    }
    
    .table-operations {
      margin-bottom: 16px;
      display: flex;
      gap: 10px;
    }
    
    .data-table {
      margin-bottom: 16px;
      
      :deep(.el-table__header) {
        th {
          background-color: #f8fafc;
          color: #1e293b;
          font-weight: 600;
        }
      }
      
      :deep(.el-table__row) {
        transition: all 0.2s ease;
        
        &:hover {
          background-color: #f0f7ff !important;
        }
      }
    }
    
    .pagination-container {
      display: flex;
      justify-content: flex-end;
      margin-top: 20px;
    }
  }
}

@media (max-width: 768px) {
  .search-form {
    .el-form-item {
      margin-right: 0 !important;
      width: 100%;
      
      .el-input {
        width: 100%;
      }
    }
    
    .search-buttons {
      display: flex;
      justify-content: flex-end;
      margin-top: 16px;
    }
  }
  
  .table-operations {
    flex-wrap: wrap;
  }
}
</style>