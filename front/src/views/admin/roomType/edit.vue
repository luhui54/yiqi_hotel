<script setup>
import {ref, reactive} from 'vue'
import {Info, Save} from '^/admin/roomType'
import {ElMessage} from "element-plus";
import UploadImg from "~/UploadImg.vue";
import { 
  Picture, 
  Money, 
  SetUp, 
  Tickets, 
  OfficeBuilding, 
  Document, 
  Collection 
} from '@element-plus/icons-vue'

const showDrawer = ref(false)

const editForm = ref({})
const init = (id) => {
  showDrawer.value = true
  if (id) {
    Info(id).then(res => {
      editForm.value = res.data
      // 确保图片字段存在且有效
      if (!editForm.value.image) editForm.value.image = ''
      if (!editForm.value.images) editForm.value.images = ''
    })
  } else {
    editForm.value = {
      image: '',
      images: ''
    }
  }
}
defineExpose({init})

const rules = reactive({
  id: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  hotelId: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  name: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  image: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  price: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  bed: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  quantity: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  createTime: [{required: true, message: '此项是必填项', trigger: 'blur'}],
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
      // 确保图片字段格式正确
      if (editForm.value.image && Array.isArray(editForm.value.image)) {
        editForm.value.image = editForm.value.image.join(';')
      }
      if (editForm.value.images && Array.isArray(editForm.value.images)) {
        editForm.value.images = editForm.value.images.join(';')
      }
      
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
      :title="editForm.id ? '编辑房型信息' : '新增房型'"
      size="650px"
      :destroy-on-close="true"
  >
    <div class="admin-drawer-form">
      <div class="admin-form-header">
        <div class="admin-form-header-title">{{ editForm.id ? `编辑: ${editForm.name || ''}` : '创建新房型' }}</div>
        <div class="admin-form-header-subtitle">请填写房型的基本信息和详细资料</div>
      </div>
      
      <el-form
          ref="formRef"
          :model="editForm"
          :rules="rules"
          label-position="top"
      >
        <!-- 基本信息 -->
        <div class="admin-form-section">
          <div class="admin-form-section-title">基本信息</div>
          
          <el-form-item label="房间名称" prop="name">
            <el-input 
              v-model="editForm.name" 
              placeholder="请输入房间名称"
            >
              <template #prefix>
                <el-icon><OfficeBuilding /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="价格" prop="price">
                <el-input 
                  v-model="editForm.price"
                  placeholder="请输入房间价格"
                >
                  <template #prefix>
                    <el-icon><Money /></el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="剩余数量" prop="quantity">
                <el-input-number 
                  v-model="editForm.quantity" 
                  :min="0"
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="面积" prop="area">
                <el-input 
                  v-model="editForm.area"
                  placeholder="请输入房间面积"
                >
                  <template #append>㎡</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="床位" prop="bed">
                <el-input 
                  v-model="editForm.bed"
                  placeholder="请输入床位信息"
                >
                  <template #prefix>
                    <el-icon><SetUp /></el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        
        <!-- 图片资料 -->
        <div class="admin-form-section">
          <div class="admin-form-section-title">图片资料</div>
          
          <el-form-item label="封面图片" prop="image">
            <UploadImg v-model:images="editForm.image" :limit="1"></UploadImg>
            <div class="admin-form-tip">上传房型主图，建议尺寸为 1200x800 像素，JPG或PNG格式</div>
          </el-form-item>
          
          <el-form-item label="房间图集" prop="images">
            <UploadImg v-model:images="editForm.images" :limit="6"></UploadImg>
            <div class="admin-form-tip">上传多张房间图片，最多6张，展示房间的各个角度</div>
          </el-form-item>
        </div>
        
        <!-- 详细信息 -->
        <div class="admin-form-section">
          <div class="admin-form-section-title">详细信息</div>
          
          <el-form-item label="标签" prop="tags">
            <el-input 
              v-model="editForm.tags" 
              placeholder="多个标签用英文分号隔开，如：无烟房;靠窗;免费WiFi"
            >
              <template #prefix>
                <el-icon><Collection /></el-icon>
              </template>
            </el-input>
            <div class="admin-form-tip">添加房间特色标签，有助于客户筛选</div>
          </el-form-item>
          
          <el-form-item label="说明" prop="instruction">
            <el-input 
              v-model="editForm.instruction" 
              type="textarea" 
              rows="5"
              placeholder="请输入房间详细说明"
            />
            <div class="admin-form-tip">详细描述房间设施、特色和服务等信息</div>
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
.form-tip {
  font-size: 12px;
  color: var(--el-text-color-secondary);
  margin-top: 5px;
}

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

:deep(.el-input-number) {
  width: 100%;
  
  .el-input__wrapper {
    padding-left: 0;
    padding-right: 0;
  }
}

:deep(.el-drawer__body) {
  overflow-y: auto;
}
</style>