<script setup>
import {ref, reactive, onMounted} from 'vue'
import {Info, Save} from '^/admin/hotel'
import {ElMessage} from "element-plus";
import UploadImg from "~/UploadImg.vue";
import { 
  Picture, 
  Location, 
  OfficeBuilding, 
  Phone, 
  Document, 
  Star, 
  TrendCharts 
} from '@element-plus/icons-vue'

const showDrawer = ref(false)

const rankDict = ref([])

const editForm = ref({})
const init = (id) => {
  showDrawer.value = true
  if (id) {
    Info(id).then(res => {
      editForm.value = res.data.hotel
      // 确保图片字段存在且有效
      if (!editForm.value.image) editForm.value.image = ''
      if (!editForm.value.images) editForm.value.images = ''
      
      rankDict.value = res.data.rankDict
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
  title: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  brand: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  image: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  city: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  tel: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  location: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  introduction: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  score: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  rank: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  minPrice: [{required: true, message: '此项是必填项', trigger: 'blur'}],
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
      :title="editForm.id ? '编辑酒店信息' : '新增酒店'"
      size="650px"
      :destroy-on-close="true"
  >
    <div class="admin-drawer-form">
      <div class="admin-form-header">
        <div class="admin-form-header-title">{{ editForm.id ? `编辑: ${editForm.title || ''}` : '创建新酒店' }}</div>
        <div class="admin-form-header-subtitle">请填写酒店的基本信息和详细资料</div>
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
          
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="酒店名称" prop="title">
                <el-input v-model="editForm.title" placeholder="请输入酒店名称">
                  <template #prefix>
                    <el-icon><OfficeBuilding /></el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="品牌" prop="brand">
                <el-input v-model="editForm.brand" placeholder="请输入品牌名称"/>
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="所在城市" prop="city">
                <el-input v-model="editForm.city" placeholder="请输入所在城市">
                  <template #prefix>
                    <el-icon><Location /></el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="电话" prop="tel">
                <el-input v-model="editForm.tel" placeholder="请输入联系电话">
                  <template #prefix>
                    <el-icon><Phone /></el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-form-item label="所在地址" prop="location">
            <el-input v-model="editForm.location" placeholder="请输入详细地址">
              <template #prefix>
                <el-icon><Location /></el-icon>
              </template>
            </el-input>
          </el-form-item>
        </div>
        
        <!-- 图片资料 -->
        <div class="admin-form-section">
          <div class="admin-form-section-title">图片资料</div>
          
          <el-form-item label="封面图片" prop="image">
            <UploadImg v-model:images="editForm.image" :limit="1"></UploadImg>
            <div class="admin-form-tip">上传酒店主图，建议尺寸为 1200x800 像素，JPG或PNG格式</div>
          </el-form-item>
          
          <el-form-item label="酒店图集" prop="images">
            <UploadImg v-model:images="editForm.images" :limit="6"></UploadImg>
            <div class="admin-form-tip">上传多张酒店图片，最多6张，展示酒店的各个方面</div>
          </el-form-item>
        </div>
        
        <!-- 详细信息 -->
        <div class="admin-form-section">
          <div class="admin-form-section-title">详细信息</div>
          
          <el-form-item label="简介" prop="introduction">
            <el-input 
              v-model="editForm.introduction" 
              type="textarea" 
              rows="5" 
              placeholder="请输入酒店简介，详细描述酒店特色和设施"
            />
            <div class="admin-form-tip">简洁明了地描述酒店特色、地理位置优势和设施特点</div>
          </el-form-item>
          
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="评分" prop="score">
                <el-rate 
                  v-model="editForm.score"
                  allow-half
                  show-score
                  score-template="{value}"
                ></el-rate>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="等级" prop="hotelRank">
                <el-select v-model="editForm.hotelRank" placeholder="请选择星级" style="width: 100%">
                  <el-option
                      v-for="item in rankDict"
                      :key="item.key"
                      :label="item.value"
                      :value="item.key">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="最低价格" prop="minPrice">
                <el-input v-model="editForm.minPrice" placeholder="请输入最低价格">
                  <template #prefix>¥</template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
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

:deep(.el-select) {
  width: 100%;
}

:deep(.el-rate) {
  margin-top: 5px;
}

:deep(.el-drawer__body) {
  overflow-y: auto;
}
</style>