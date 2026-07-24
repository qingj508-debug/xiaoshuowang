<template>
  <div>
    <el-upload
      class="avatar-uploader"
      action="https://endpoint-oss1.oss-cn-guangzhou.aliyuncs.com"
      :before-upload="ossPolicy"
      :data="state.objData"
      :limit="1"
      :on-success="handleAvatarSuccess"
      :file-list="state.fileList">
      <img v-if="imageUrl" :src="imageUrl" class="avatar" />
      <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
    </el-upload>
  </div>
</template>

<script setup>
import { reactive,ref } from "vue";
import request from '@/utils/request'
import {getUUID} from '@/utils/index'
import { Plus } from '@element-plus/icons-vue'
const state = reactive({
fileList: [],
  objData: {
    ossAccessKeyId: '',
    policy: '',
    signature: '',
    key: '',
    dir: '',
    host: ''
  }
});
const  imageUrl= ref('');

const ossPolicy =  ()=>{
 
  return new Promise((resolve,reject) => {
    request.get('/thirdparty/oss/policy',{})
    .then(res => {
      state.objData.ossAccessKeyId = res.data.accessId
      state.objData.policy = res.data.policy
      state.objData.signature = res.data.signature
      state.objData.key = res.data.dir  +"/" + getUUID()+'_${filename}';
      state.objData.dir = res.data.dir
      state.objData.host = res.data.host // 直传 oss 的服务地址
      console.log("开始获取签名内容:",state.objData);
      resolve(true) // 断续上传
    })
    .catch(error => {
      console.log(error)
      reject(false) // 停止上传
    })
  })
}
const emit = defineEmits(['input']);
const handleAvatarSuccess=  (response,uploadFile) => {

  imageUrl.value = state.objData.host + '/' + state.objData.key.replace("${filename}",uploadFile.name);
  emit("input",imageUrl.value);
}

</script>
<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>