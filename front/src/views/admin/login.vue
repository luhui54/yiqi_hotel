<script setup>
import {ref, reactive} from 'vue'
import {useAdminStore} from "@/stores/admin.js";
import router from "@/router/index.js";
import { Lock, User } from '@element-plus/icons-vue'

const adminStore = useAdminStore()

const form = reactive({
  username: 'admin',
  password: '111111'
})
const rules = reactive({
  username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
  password: [{required: true, message: '请输入密码', trigger: 'blur'}]
})

const formRef = ref()
const loading = ref(false)

const handleLogin = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      adminStore.login(form).then(res => {
        router.push("/admin")
      }).finally(() => {
        loading.value = false
      })
    }
  })
}

</script>

<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <div class="logo">
          <img src="@/assets/banner/1.png" alt="Logo" class="logo-image">
        </div>
        <h2 class="title">艺柒酒旅后台管理系统</h2>
      </div>
      
      <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          class="login-form"
      >
        <el-form-item prop="username">
          <el-input 
            v-model="form.username" 
            placeholder="用户名" 
            :prefix-icon="User"
            size="large"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input 
            type="password" 
            v-model="form.password" 
            placeholder="密码" 
            :prefix-icon="Lock"
            size="large"
            show-password
          />
        </el-form-item>
        
        <el-form-item>
          <el-button 
            type="primary" 
            @click="handleLogin" 
            :loading="loading"
            class="login-button"
            size="large"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="login-footer">
        <p>© 2000 - 2024 佛山艺柒信息技术有限公司, eQi, All Rights Reserved.</p>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #2b3a55 0%, #1e293b 100%);
  padding: 20px;
}

.login-box {
  width: 100%;
  max-width: 440px;
  background-color: #fff;
  border-radius: 16px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  overflow: hidden;
}

.login-header {
  padding: 30px 20px;
  text-align: center;
  background-color: #f8fafc;
  border-bottom: 1px solid #eaedf3;
  
  .logo {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
    
    .logo-image {
      height: 80px;
      width: auto;
      object-fit: contain;
      border-radius: 8px;
    }
  }
  
  .title {
    font-size: 22px;
    font-weight: 600;
    color: #1e293b;
    margin: 0;
  }
}

.login-form {
  padding: 30px 40px;
  
  :deep(.el-input__wrapper) {
    border-radius: 8px;
    box-shadow: 0 0 0 1px #dcdfe6;
    
    &:hover, &.is-focus {
      box-shadow: 0 0 0 1px var(--el-color-primary);
    }
  }
  
  .login-button {
    width: 100%;
    border-radius: 8px;
    font-weight: 500;
    letter-spacing: 1px;
    transition: all 0.3s ease;
    
    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 5px 15px rgba(0, 113, 227, 0.3);
    }
  }
}

.login-footer {
  padding: 15px 20px;
  text-align: center;
  border-top: 1px solid #eaedf3;
  
  p {
    font-size: 12px;
    color: #64748b;
    margin: 0;
  }
}
</style>