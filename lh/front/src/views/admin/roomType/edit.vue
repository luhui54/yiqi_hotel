<script setup>
  import {ref, reactive} from 'vue'
  import {Info, Save} from '^/admin/roomType'
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
                                        <el-form-item label="所属酒店" prop="hotelId">
              <el-input v-model="editForm.hotelId"/>
            </el-form-item>
                                        <el-form-item label="房间名称" prop="name">
              <el-input v-model="editForm.name"/>
            </el-form-item>
                                        <el-form-item label="图片" prop="image">
              <UploadImg v-model:images="editForm.image" :limit="1"></UploadImg>
            </el-form-item>
                                        <el-form-item label="图集" prop="images">
              <el-input v-model="editForm.images"/>
            </el-form-item>
                                        <el-form-item label="价格" prop="price">
              <el-input v-model="editForm.price"/>
            </el-form-item>
                                        <el-form-item label="面积" prop="area">
              <el-input v-model="editForm.area"/>
            </el-form-item>
                                        <el-form-item label="标签" prop="tags">
              <el-input v-model="editForm.tags"/>
            </el-form-item>
                                        <el-form-item label="床位" prop="bed">
              <el-input v-model="editForm.bed"/>
            </el-form-item>
                                        <el-form-item label="说明" prop="instruction">
              <el-input v-model="editForm.instruction"/>
            </el-form-item>
                                        <el-form-item label="剩余数量" prop="quantity">
              <el-input v-model="editForm.quantity"/>
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