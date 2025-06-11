<script setup>
import {ref, reactive} from 'vue'
import {GetUserInfo, UpdateUserInfo} from "^/app/user.js"
import {ElMessage} from "element-plus"
import {useUserStore} from "@/stores/user.js"
import UploadImg from "~/UploadImg.vue";

const userStore = useUserStore()

const form = reactive({})
const rules = reactive({
  avatar: [{required: true, message: '请上传头像', trigger: 'blur'}],
  username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
})

const loading = ref(false)
const formRef = ref()
const handleSubmit = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const res = await UpdateUserInfo(form)
        ElMessage.success("个人信息更新成功")
        userStore.updateUserInfo(res.data)
      } catch (error) {
        console.error(error)
      } finally {
        loading.value = false
      }
    }
  })
}

const initData = () => {
  loading.value = true
  GetUserInfo().then(res => {
    Object.assign(form, res.data)
    loading.value = false
  }).catch(err => {
    loading.value = false
  })
}

initData()

const handleUpload = (images) => {
  form.avatar = images
}
</script>

<template>
  <div class="profile-container">
    <div class="page-header">
      <h2 class="page-title">个人信息</h2>
      <p class="page-desc">您可以在这里修改您的个人资料</p>
    </div>
    
    <div class="profile-card">
      <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          label-position="top"
          class="profile-form"
          v-loading="loading"
      >
        <div class="avatar-section">
          <div class="avatar-label">头像</div>
          <div class="avatar-upload">
            <el-form-item prop="avatar">
              <UploadImg :images="form.avatar" :limit="1" @handleUpload="handleUpload" class="avatar-uploader"></UploadImg>
            </el-form-item>
            <div class="avatar-tip">支持JPG、PNG格式，文件小于2MB</div>
          </div>
        </div>
        
        <div class="form-section">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="请输入您的用户名" />
          </el-form-item>
          
          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入您的手机号码" />
          </el-form-item>
          
          <el-form-item label="个人签名" prop="signature">
            <el-input 
              v-model="form.signature" 
              type="textarea" 
              :rows="3" 
              placeholder="写一段个性签名，展示自己的独特魅力"
              maxlength="100"
              show-word-limit
            />
          </el-form-item>
          
          <el-form-item>
            <el-button 
              type="primary" 
              @click="handleSubmit" 
              :loading="loading"
              class="submit-btn"
            >
              保存修改
            </el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.profile-container {
  padding: 10px 0;
}

.page-header {
  margin-bottom: 24px;
  
  .page-title {
    font-size: 22px;
    font-weight: 600;
    margin: 0 0 8px;
    color: #333;
  }
  
  .page-desc {
    font-size: 14px;
    color: #666;
    margin: 0;
  }
}

.profile-card {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  padding: 30px;
}

.profile-form {
  max-width: 100%;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  margin-bottom: 30px;
  
  .avatar-label {
    font-size: 16px;
    font-weight: 500;
    margin-bottom: 12px;
    color: #333;
  }
  
  .avatar-upload {
    display: flex;
    flex-direction: column;
    
    .avatar-uploader {
      :deep(.el-upload--picture-card) {
        width: 120px;
        height: 120px;
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
        border: 1px dashed #d9d9d9;
        background-color: #fafafa;
      }
      
      :deep(.el-upload-list--picture-card .el-upload-list__item) {
        width: 120px;
        height: 120px;
        border-radius: 50%;
      }
      
      :deep(.el-upload-list--picture-card .el-upload-list__item-thumbnail) {
        object-fit: cover;
      }
    }
    
    .avatar-tip {
      font-size: 12px;
      color: #999;
      margin-top: 8px;
    }
  }
}

.form-section {
  :deep(.el-form-item__label) {
    font-weight: 500;
    color: #333;
    padding-bottom: 8px;
  }
  
  :deep(.el-input__inner) {
    border-radius: 4px;
  }
  
  :deep(.el-textarea__inner) {
    border-radius: 4px;
  }
}

.submit-btn {
  width: 100%;
  height: 40px;
  font-size: 16px;
  margin-top: 10px;
}

@media (min-width: 768px) {
  .submit-btn {
    width: 160px;
  }
}
</style>











