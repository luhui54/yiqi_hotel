<script setup>
import {ref, reactive} from 'vue'
import {Info, Save} from '^/admin/room'
import {GetList} from '^/admin/roomType'
import {ElMessage} from "element-plus";
import { 
  House, 
  OfficeBuilding, 
  Lock 
} from '@element-plus/icons-vue'

const showDrawer = ref(false)

const roomTypes = ref([])
const editForm = ref({})
const init = (id) => {
  showDrawer.value = true

  GetList().then(res => {
    roomTypes.value = res.data
  })

  if (id) {
    Info(id).then(res => {
      editForm.value = res.data
    })
  } else {
    editForm.value = {}
  }
}
defineExpose({init})

const rules = reactive({
  roomTypeId: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  sn: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  status: [{required: true, message: '此项是必填项', trigger: 'blur'}],
})
const emits = defineEmits(['handleClose'])

const closeHandle = (hasChange) => {
  showDrawer.value = false
  emits('handleClose', hasChange)
}

const formRef = ref()
const submitHandle = () => {
  formRef.value.validate((valid, fields) => {
    if (valid) {
      Save(editForm.value).then(res => {
        ElMessage.success('操作成功')
        closeHandle(true)
      })
    }
  })
}
</script>

<template>
  <el-drawer
      v-model="showDrawer"
      :title="editForm.id ? '编辑房间信息' : '新增房间'"
      size="550px"
      :destroy-on-close="true"
  >
    <div class="admin-drawer-form">
      <div class="admin-form-header">
        <div class="admin-form-header-title">{{ editForm.id ? `编辑: 房间号 ${editForm.sn || ''}` : '创建新房间' }}</div>
        <div class="admin-form-header-subtitle">请填写房间的基本信息</div>
      </div>
      
      <el-form
          ref="formRef"
          :model="editForm"
          :rules="rules"
          label-position="top"
      >
        <!-- 基本信息 -->
        <div class="admin-form-section">
          <div class="admin-form-section-title">房间信息</div>
          
          <el-form-item label="所属房型" prop="roomTypeId">
            <el-select 
              v-model="editForm.roomTypeId" 
              placeholder="请选择所属房型"
              style="width: 100%"
            >
              <template #prefix>
                <el-icon><OfficeBuilding /></el-icon>
              </template>
              <el-option
                  v-for="item in roomTypes"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
            <div class="admin-form-tip">选择此房间对应的房型</div>
          </el-form-item>
          
          <el-form-item label="房间号" prop="sn">
            <el-input 
              v-model="editForm.sn"
              placeholder="请输入房间号"
            >
              <template #prefix>
                <el-icon><House /></el-icon>
              </template>
            </el-input>
            <div class="admin-form-tip">输入唯一的房间编号，如：A101、B203等</div>
          </el-form-item>
          
          <el-form-item label="房间状态" prop="status">
            <el-radio-group v-model="editForm.status" class="status-radio-group">
              <el-radio :value="0" class="status-radio status-free">
                <span class="status-icon"></span>
                空闲
              </el-radio>
              <el-radio :value="1" class="status-radio status-occupied">
                <span class="status-icon"></span>
                入住
              </el-radio>
            </el-radio-group>
            <div class="admin-form-tip">选择当前房间的使用状态</div>
          </el-form-item>
        </div>
        
        <div class="admin-form-footer">
          <el-button @click="closeHandle">取消</el-button>
          <el-button type="primary" @click="submitHandle">保存</el-button>
        </div>
      </el-form>
    </div>
  </el-drawer>
</template>

<style lang="scss" scoped>
:deep(.el-form-item__label) {
  font-weight: 500;
}

:deep(.el-input__wrapper),
:deep(.el-textarea__inner) {
  box-shadow: 0 0 0 1px var(--admin-border-color) inset;
  border-radius: 8px;
  padding: 8px 12px;
  transition: all 0.3s;
  
  &:hover {
    box-shadow: 0 0 0 1px var(--primary-light) inset;
  }
  
  &.is-focus {
    box-shadow: 0 0 0 1px var(--primary-color) inset !important;
  }
}

:deep(.el-drawer__body) {
  overflow-y: auto;
}

.status-radio-group {
  display: flex;
  width: 100%;
  margin-top: 8px;
}

.status-radio {
  flex: 1;
  margin-right: 0 !important;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid var(--admin-border-color);
  border-radius: 8px;
  transition: all 0.3s;
  
  &:first-child {
    margin-right: 12px !important;
  }
  
  &.is-checked {
    border-color: var(--primary-color);
    color: var(--primary-color);
  }
  
  .status-icon {
    display: inline-block;
    width: 8px;
    height: 8px;
    border-radius: 50%;
    margin-right: 6px;
  }
}

.status-free .status-icon {
  background-color: var(--success-color);
}

.status-occupied .status-icon {
  background-color: var(--warn-color);
}
</style>