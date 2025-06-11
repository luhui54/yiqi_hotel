<script setup>
  import {ref, reactive} from 'vue'
  import {Info, Save} from '^/admin/hotel'
  import {ElMessage} from "element-plus";

  const showDrawer = ref(false)

  const editForm = ref({})
  const init = (id) => {
    showDrawer.value = true
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
                    id: [{required: true, message: '此项是必填项', trigger: 'blur'}],
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
      title="新增或编辑"
  >

    <el-form
        ref="formRef"
        style="max-width: 600px"
        :model="editForm"
        :rules="rules"
        label-width="auto"
    >

                              <el-form-item label="" prop="id">
              <el-input v-model="editForm.id"/>
            </el-form-item>
                                        <el-form-item label="酒店名称" prop="title">
              <el-input v-model="editForm.title"/>
            </el-form-item>
                                        <el-form-item label="品牌" prop="brand">
              <el-input v-model="editForm.brand"/>
            </el-form-item>
                                        <el-form-item label="图片" prop="image">
              <UploadImg v-model:images="editForm.image" :limit="1"></UploadImg>
            </el-form-item>
                                        <el-form-item label="所在城市" prop="city">
              <el-input v-model="editForm.city"/>
            </el-form-item>
                                        <el-form-item label="图集" prop="images">
              <el-input v-model="editForm.images"/>
            </el-form-item>
                                        <el-form-item label="电话" prop="tel">
              <el-input v-model="editForm.tel"/>
            </el-form-item>
                                        <el-form-item label="所在地址" prop="location">
              <el-input v-model="editForm.location"/>
            </el-form-item>
                                        <el-form-item label="简介" prop="introduction">
              <el-input v-model="editForm.introduction"/>
            </el-form-item>
                                        <el-form-item label="评分" prop="score">
              <el-input v-model="editForm.score"/>
            </el-form-item>
                                        <el-form-item label="等级" prop="rank">
              <el-input v-model="editForm.rank"/>
            </el-form-item>
                                        <el-form-item label="最低价" prop="minPrice">
              <el-input v-model="editForm.minPrice"/>
            </el-form-item>
                                        <el-form-item label="创建时间" prop="createTime">
              <el-input v-model="editForm.createTime"/>
            </el-form-item>
                  
      <el-form-item>
        <el-button type="primary" @click="submitHandle">提交</el-button>
        <el-button @click="closeHandle">关闭</el-button>
      </el-form-item>

    </el-form>
  </el-drawer>
</template>

<style lang="scss" scoped>

</style>