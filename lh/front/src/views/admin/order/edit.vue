<script setup>
  import {ref, reactive} from 'vue'
  import {Info, Save} from '^/admin/order'
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
                          sn: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                          userId: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                          hotelId: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                          roomTypeId: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                                                          guestName: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                          guestPhone: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                          bookTime: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                          bookCheckInTime: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                          bookCheckOutTime: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                                                          hotelTitle: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                          roomTypeName: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                          roomTypeImage: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                          price: [{required: true, message: '此项是必填项', trigger: 'blur'}],
                          status: [{required: true, message: '此项是必填项', trigger: 'blur'}],
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
                                        <el-form-item label="订单编号" prop="sn">
              <el-input v-model="editForm.sn"/>
            </el-form-item>
                                        <el-form-item label="" prop="userId">
              <el-input v-model="editForm.userId"/>
            </el-form-item>
                                        <el-form-item label="" prop="hotelId">
              <el-input v-model="editForm.hotelId"/>
            </el-form-item>
                                        <el-form-item label="" prop="roomTypeId">
              <el-input v-model="editForm.roomTypeId"/>
            </el-form-item>
                                        <el-form-item label="" prop="roomId">
              <el-input v-model="editForm.roomId"/>
            </el-form-item>
                                        <el-form-item label="" prop="roomSn">
              <el-input v-model="editForm.roomSn"/>
            </el-form-item>
                                        <el-form-item label="入住人名字" prop="guestName">
              <el-input v-model="editForm.guestName"/>
            </el-form-item>
                                        <el-form-item label="入住人手机号" prop="guestPhone">
              <el-input v-model="editForm.guestPhone"/>
            </el-form-item>
                                        <el-form-item label="预订时间" prop="bookTime">
              <el-input v-model="editForm.bookTime"/>
            </el-form-item>
                                        <el-form-item label="预订入住时间" prop="bookCheckInTime">
              <el-input v-model="editForm.bookCheckInTime"/>
            </el-form-item>
                                        <el-form-item label="预订退房时间" prop="bookCheckOutTime">
              <el-input v-model="editForm.bookCheckOutTime"/>
            </el-form-item>
                                        <el-form-item label="实际入住时间" prop="realCheckInTime">
              <el-input v-model="editForm.realCheckInTime"/>
            </el-form-item>
                                        <el-form-item label="实际退房时间" prop="realCheckOutTime">
              <el-input v-model="editForm.realCheckOutTime"/>
            </el-form-item>
                                        <el-form-item label="" prop="hotelTitle">
              <el-input v-model="editForm.hotelTitle"/>
            </el-form-item>
                                        <el-form-item label="" prop="roomTypeName">
              <el-input v-model="editForm.roomTypeName"/>
            </el-form-item>
                                        <el-form-item label="" prop="roomTypeImage">
              <el-input v-model="editForm.roomTypeImage"/>
            </el-form-item>
                                        <el-form-item label="价格" prop="price">
              <el-input v-model="editForm.price"/>
            </el-form-item>
                                        <el-form-item label="订单状态" prop="status">
              <el-input v-model="editForm.status"/>
            </el-form-item>
                                        <el-form-item label="" prop="createTime">
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