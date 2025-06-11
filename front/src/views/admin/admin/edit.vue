<script setup>
import {ref, reactive} from 'vue'
import {Info, Save} from '^/admin/admin'
import UploadImg from "~/UploadImg.vue";
import {ElMessage} from "element-plus";
import { 
  User, 
  Message, 
  Phone, 
  OfficeBuilding, 
  UserFilled, 
  Lock 
} from '@element-plus/icons-vue'

const showDrawer = ref(false)

const editForm = ref({})
const init = (id) => {
  showDrawer.value = true
  if (id) {
    Info(id).then(res => {
      editForm.value = res.data
      if (!editForm.value.avatar) editForm.value.avatar = ''
    })
  } else {
    editForm.value = {
      avatar: '',
      role: 'ADMIN'
    }
  }
}
defineExpose({init})

const rules = reactive({
  avatar: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  username: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  email: [{required: true, message: '此项是必填项', trigger: 'blur'}],
  role: [{required: true, message: '此项是必填项', trigger: 'blur'}],
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
      if (editForm.value.avatar && Array.isArray(editForm.value.avatar)) {
        editForm.value.avatar = editForm.value.avatar.join(';')
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
      :title="editForm.id ? '编辑管理员' : '新增管理员'"
      size="550px"
      :destroy-on-close="true"
  >
    <div class="admin-drawer-form">
      <div class="admin-form-header">
        <div class="admin-form-header-title">{{ editForm.id ? `编辑: ${editForm.username || ''}` : '创建新管理员' }}</div>
        <div class="admin-form-header-subtitle">请填写管理员的基本信息和权限</div>
      </div>
      
      <el-form
          ref="formRef"
          :model="editForm"
          :rules="rules"
          label-position="top"
      >
        <!-- 基本信息 -->
        <div class="admin-form-section">
          <div class="admin-form-section-title">个人信息</div>
          
          <div class="avatar-upload-section">
            <el-form-item label="头像" prop="avatar">
              <UploadImg v-model:images="editForm.avatar" :limit="1"></UploadImg>
              <div class="admin-form-tip">上传管理员头像，建议使用正方形图片</div>
            </el-form-item>
          </div>
          
          <el-form-item label="管理员名称" prop="username">
            <el-input 
              v-model="editForm.username"
              placeholder="请输入管理员名称"
            >
              <template #prefix>
                <el-icon><User /></el-icon>
              </template>
            </el-input>
            <div v-if="!editForm.id" class="password-alert">
              <el-icon><Lock /></el-icon>
              默认密码：888888，请在创建后及时修改密码
            </div>
          </el-form-item>
          
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="邮箱" prop="email">
                <el-input 
                  v-model="editForm.email"
                  placeholder="请输入邮箱地址"
                >
                  <template #prefix>
                    <el-icon><Message /></el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="手机号" prop="phone">
                <el-input 
                  v-model="editForm.phone"
                  placeholder="请输入手机号码"
                >
                  <template #prefix>
                    <el-icon><Phone /></el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        
        <!-- 权限设置 -->
        <div class="admin-form-section">
          <div class="admin-form-section-title">权限设置</div>
          
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="角色" prop="role">
                <el-select 
                  v-model="editForm.role"
                  style="width: 100%"
                  placeholder="请选择角色"
                >
                  <template #prefix>
                    <el-icon><UserFilled /></el-icon>
                  </template>
                  <el-option label="系统管理员" value="ADMIN"></el-option>
                  <el-option label="酒店管理员" value="HOTEL"></el-option>
                </el-select>
                <div class="admin-form-tip">选择管理员的系统角色</div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="管理酒店" prop="hotelId">
                <el-input 
                  v-model="editForm.hotelId"
                  placeholder="填写所管理的酒店ID"
                >
                  <template #prefix>
                    <el-icon><OfficeBuilding /></el-icon>
                  </template>
                </el-input>
                <div class="admin-form-tip">仅对酒店管理员角色有效</div>
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

.avatar-upload-section {
  margin-bottom: 20px;
}

.password-alert {
  margin-top: 8px;
  padding: 8px 12px;
  background-color: rgba(255, 149, 0, 0.1);
  border-radius: 6px;
  font-size: 13px;
  color: var(--warn-color);
  display: flex;
  align-items: center;
  
  .el-icon {
    margin-right: 8px;
    font-size: 16px;
  }
}
</style>