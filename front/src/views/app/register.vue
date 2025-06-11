<script setup>
import {ref, reactive} from 'vue'
import {useUserStore} from "@/stores/user.js";
import router from "@/router/index.js"
import {Register, GetCaptcha} from "^/app/login.js"
import {ElMessage} from 'element-plus'

const userStore = useUserStore()

const form = reactive({
  username: '',
  password: '',
  code: '',
  uuid: ''
})
const rules = reactive({
  username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
  password: [{required: true, message: '请输入密码', trigger: 'blur'}],
  code: [{required: true, message: '请输入验证码', trigger: 'blur'}]
})

const formRef = ref()
const handleRegister = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      Register(form).then(res => {
        ElMessage.success('注册成功，请登录')
        router.push("/login")
      }, err => {
        getCaptcha()
        form.code = ''
      })
    }
  })
}

const captchaImg = ref('')
const getCaptcha = () => {
  GetCaptcha().then(res => {
    form.uuid = res.data.uuid
    captchaImg.value = res.data.imgBase64
  })
}
getCaptcha()

const toLogin = () => {
  router.push('/login')
}
</script>

<template>
  <div class="register-container">
    <div class="register-content">
      <div class="register-header">
        <h1 class="register-title">创建账号</h1>
        <p class="register-subtitle">注册艺柒酒旅，开启您的精彩旅程</p>
      </div>
      
      <el-form
          ref="formRef"
          class="register-form"
          :model="form"
          :rules="rules"
          label-position="top"
      >
        <el-form-item label="用户名" prop="username">
          <el-input 
            v-model="form.username" 
            placeholder="请输入用户名" 
            size="large"
            class="register-input"
          />
        </el-form-item>
        
        <el-form-item label="密码" prop="password">
          <el-input 
            type="password" 
            v-model="form.password" 
            placeholder="请输入密码" 
            size="large"
            class="register-input"
            show-password
          />
        </el-form-item>
        
        <el-form-item label="验证码" prop="code">
          <div class="captcha-container">
            <el-input 
              v-model="form.code" 
              placeholder="请输入验证码" 
              size="large"
              class="register-input captcha-input"
            />
            <div class="captcha-image" @click="getCaptcha" title="点击刷新验证码">
              <el-image :src="captchaImg" fit="contain" class="captcha-img"></el-image>
            </div>
          </div>
        </el-form-item>
        
        <div class="register-actions">
          <el-button 
            type="primary" 
            @click="handleRegister" 
            class="register-button"
            size="large"
          >
            注册
          </el-button>
        </div>
        
        <div class="register-footer">
          <p>已有账号？<a @click="toLogin" class="login-link">立即登录</a></p>
        </div>
      </el-form>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 60px);
  background-color: var(--secondary-color);
  padding: 40px 20px;
}

.register-content {
  background-color: white;
  border-radius: 20px;
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 420px;
  padding: 40px;
}

.register-header {
  text-align: center;
  margin-bottom: 32px;
  
  .register-title {
    font-size: 32px;
    font-weight: 700;
    color: var(--dark-color);
    margin-bottom: 12px;
  }
  
  .register-subtitle {
    color: var(--gray-color);
    font-size: 16px;
  }
}

.register-form {
  :deep(.el-form-item__label) {
    font-weight: 600;
    color: var(--dark-color);
    padding-bottom: 8px;
    font-size: 15px;
  }
  
  .register-input {
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

.captcha-container {
  display: flex;
  gap: 12px;
  
  .captcha-input {
    flex: 1;
  }
  
  .captcha-image {
    width: 120px;
    height: 46px;
    cursor: pointer;
    border-radius: 10px;
    overflow: hidden;
    border: 1px solid rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
    
    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    }
    
    .captcha-img {
      width: 100%;
      height: 100%;
    }
  }
}

.register-actions {
  margin-top: 32px;
  
  .register-button {
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

.register-footer {
  margin-top: 24px;
  text-align: center;
  color: var(--gray-color);
  
  .login-link {
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
  .register-content {
    padding: 30px 20px;
  }
  
  .register-header {
    .register-title {
      font-size: 28px;
    }
  }
  
  .captcha-container {
    .captcha-image {
      width: 110px;
    }
  }
}
</style>