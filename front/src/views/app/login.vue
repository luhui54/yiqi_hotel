<script setup>
import {ref, reactive} from 'vue'
import {useUserStore} from "@/stores/user.js";
import router from "@/router/index.js";
import {useRoute} from "vue-router";

const route = useRoute()

const userStore = useUserStore()

const form = reactive({
  username: 'yiqi',
  password: 'yiqi123456'
})
const rules = reactive({
  username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
  password: [{required: true, message: '请输入密码', trigger: 'blur'}]
})

const formRef = ref()
const handleLogin = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      userStore.login(form).then(res => {
        router.push(route.query.redirect? route.query.redirect : "/")
      })
    }
  })
}

const toRegister = () => {
  router.push('/register')
}
</script>

<template>
  <div class="login-container">
    <div class="login-content">
      <div class="login-header">
        <h1 class="login-title">欢迎回来</h1>
        <p class="login-subtitle">登录艺柒酒旅，享受精致住宿体验</p>
      </div>
      
      <el-form
          ref="formRef"
          class="login-form"
          :model="form"
          :rules="rules"
          label-position="top"
      >
        <el-form-item label="用户名" prop="username">
          <el-input 
            v-model="form.username" 
            placeholder="请输入用户名" 
            size="large"
            class="login-input"
          />
        </el-form-item>
        
        <el-form-item label="密码" prop="password">
          <el-input 
            type="password" 
            v-model="form.password" 
            placeholder="请输入密码" 
            size="large"
            class="login-input"
            show-password
          />
        </el-form-item>
        
        <div class="login-actions">
          <el-button 
            type="primary" 
            @click="handleLogin" 
            class="login-button"
            size="large"
            :loading="userStore.loading"
          >
            登录
          </el-button>
        </div>
        
        <div class="login-footer">
          <p>还没有账号？<a @click="toRegister" class="register-link">立即注册</a></p>
        </div>
      </el-form>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 60px);
  background-color: var(--secondary-color);
  padding: 40px 20px;
}

.login-content {
  background-color: white;
  border-radius: 20px;
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 420px;
  padding: 40px;
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
  
  .login-title {
    font-size: 32px;
    font-weight: 700;
    color: var(--dark-color);
    margin-bottom: 12px;
  }
  
  .login-subtitle {
    color: var(--gray-color);
    font-size: 16px;
  }
}

.login-form {
  :deep(.el-form-item__label) {
    font-weight: 600;
    color: var(--dark-color);
    padding-bottom: 8px;
    font-size: 15px;
  }
  
  .login-input {
    :deep(.el-input__wrapper) {
      border-radius: 10px;
      box-shadow: 0 0 0 1px rgba(0, 0, 0, 0.1);
      transition: all 0.3s ease;
      
      &:hover {
        box-shadow: 0 0 0 1px var(--primary-color);
      }
      
      &.is-focus {
        box-shadow: 0 0 0 2px rgba(0, 113, 227, 0.2);
      }
    }
  }
}

.login-actions {
  margin-top: 32px;
  
  .login-button {
    width: 100%;
    height: 50px;
    border-radius: 12px;
    background-color: var(--primary-color);
    border-color: var(--primary-color);
    font-weight: 600;
    font-size: 16px;
    transition: var(--transition);
    
    &:hover {
      background-color: var(--primary-hover);
      border-color: var(--primary-hover);
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 113, 227, 0.3);
    }
  }
}

.login-footer {
  margin-top: 24px;
  text-align: center;
  color: var(--gray-color);
  
  .register-link {
    color: var(--primary-color);
    cursor: pointer;
    font-weight: 500;
    margin-left: 4px;
    
    &:hover {
      text-decoration: underline;
    }
  }
}

@media (max-width: 480px) {
  .login-content {
    padding: 30px 20px;
  }
  
  .login-header {
    .login-title {
      font-size: 28px;
    }
  }
}
</style>