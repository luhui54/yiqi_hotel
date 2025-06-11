<script setup>
import {ref, reactive} from 'vue'
import {UpdatePass} from "^/app/user.js"
import {ElMessage} from "element-plus"
import { Lock } from '@element-plus/icons-vue'

const form = reactive({
  oldPass: '',
  newPass: '',
  confirmPass: ''
})

const rules = reactive({
  oldPass: [{required: true, message: '请输入旧密码', trigger: 'blur'}],
  newPass: [
    {required: true, message: '请输入新密码', trigger: 'blur'},
    {min: 6, message: '密码长度不能少于6个字符', trigger: 'blur'}
  ],
  confirmPass: [
    {required: true, message: '请确认新密码', trigger: 'blur'},
    {
      validator: (rule, value, callback) => {
        if (value !== form.newPass) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
})

const loading = ref(false)
const formRef = ref()
const handleSubmit = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await UpdatePass({
          oldPass: form.oldPass,
          newPass: form.newPass
        })
        ElMessage.success("密码修改成功")
        formRef.value.resetFields()
      } catch (error) {
        console.error(error)
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<template>
  <div class="password-container">
    <div class="page-header">
      <h2 class="page-title">修改密码</h2>
      <p class="page-desc">为了您的账户安全，请定期修改密码</p>
    </div>
    
    <div class="password-card">
      <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          label-position="top"
          class="password-form"
          v-loading="loading"
      >
        <div class="form-section">
          <el-form-item label="当前密码" prop="oldPass">
            <el-input 
              v-model="form.oldPass" 
              show-password 
              placeholder="请输入当前密码"
              :prefix-icon="Lock"
            />
          </el-form-item>
          
          <el-form-item label="新密码" prop="newPass">
            <el-input 
              v-model="form.newPass" 
              show-password 
              placeholder="请输入新密码，不少于6个字符"
              :prefix-icon="Lock"
            />
          </el-form-item>
          
          <el-form-item label="确认新密码" prop="confirmPass">
            <el-input 
              v-model="form.confirmPass" 
              show-password 
              placeholder="请再次输入新密码"
              :prefix-icon="Lock"
            />
          </el-form-item>
          
          <div class="password-tips">
            <h4>密码安全提示：</h4>
            <ul>
              <li>密码长度至少6位，建议使用8位以上</li>
              <li>建议使用字母、数字和特殊字符的组合</li>
              <li>不要使用与其他网站相同的密码</li>
            </ul>
          </div>
          
          <el-form-item>
            <el-button 
              type="primary" 
              @click="handleSubmit" 
              :loading="loading"
              class="submit-btn"
            >
              确认修改
            </el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.password-container {
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

.password-card {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  padding: 30px;
}

.password-form {
  max-width: 100%;
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
  
  :deep(.el-input__prefix) {
    color: #999;
  }
}

.password-tips {
  margin: 20px 0;
  padding: 15px;
  background-color: #f9f9f9;
  border-radius: 6px;
  border-left: 3px solid #0071e3;
  
  h4 {
    font-size: 14px;
    margin: 0 0 10px;
    color: #333;
  }
  
  ul {
    margin: 0;
    padding-left: 20px;
    
    li {
      font-size: 13px;
      color: #666;
      margin-bottom: 5px;
      
      &:last-child {
        margin-bottom: 0;
      }
    }
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











