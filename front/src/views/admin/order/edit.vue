<script setup>
import {ref, reactive} from 'vue'
import {Info, CheckIn} from '^/admin/order'
import {GetPage} from '^/admin/room'
import {ElMessage} from "element-plus";

const showDrawer = ref(false)

const rooms = ref([])
const editForm = ref({})
const init = (id) => {
  showDrawer.value = true

  GetPage({size: 100}).then(res => {
    rooms.value = res.data.records
  })

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
  roomId: [{required: true, message: '此项是必填项', trigger: 'blur'}],
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
      CheckIn(editForm.value).then(res => {
        ElMessage.success('入住成功')
        closeHandle(true)
      })
    }
  })
}
</script>

<template>

  <el-drawer
      v-model="showDrawer"
      title="入住安排"
  >

    <el-form
        ref="formRef"
        style="max-width: 600px"
        :model="editForm"
        :rules="rules"
        label-width="auto"
    >

      <el-form-item label="订单编号" prop="sn">
        <el-input v-model="editForm.sn" disabled/>
      </el-form-item>

      <el-form-item label="房间类型" prop="roomTypeName">
        <el-input v-model="editForm.roomTypeName" disabled/>
      </el-form-item>

      <el-form-item label="入住人名字" prop="guestName">
        <el-input v-model="editForm.guestName" disabled/>
      </el-form-item>
      <el-form-item label="入住人手机号" prop="guestPhone">
        <el-input v-model="editForm.guestPhone" disabled/>
      </el-form-item>
      <el-form-item label="预订入住时间" prop="bookCheckInTime">
        <el-input v-model="editForm.bookCheckInTime" disabled/>
      </el-form-item>
      <el-form-item label="预订退房时间" prop="bookCheckOutTime">
        <el-input v-model="editForm.bookCheckOutTime" disabled/>
      </el-form-item>

      <el-form-item label="房间安排" prop="roomId">
        <el-select v-model="editForm.roomId" placeholder="请选择房间">
          <el-option
              v-for="item in rooms"
              :disabled="item.status === 1"
              :key="item.id"
              :label="item.sn"
              :value="item.id">
          </el-option>
        </el-select>
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