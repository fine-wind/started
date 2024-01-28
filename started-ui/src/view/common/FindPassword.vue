<template>
    <div>
        <el-row>
            <el-col :span="8" :offset="8">
                <h1 v-text="thisDomain.name"></h1>
                <h3>找回密码</h3>
                <el-form :model="form" label-width="120px">
                    <el-form-item label="用户名">
                        <el-input v-model="form.username"/>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input v-model="form.password" type="password" @keyup.enter="onSubmit()"/>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="sendPwCode()">发送重置链接</el-button>
                        <el-button type="primary" @click="onSubmit()">重置密码</el-button>
                    </el-form-item>
                </el-form>
                <div>
                    <p>去<a href="/login">登录</a></p>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script setup>
import {inject, ref} from 'vue'
import HTTP from "@/utils/request";
import {ElMessage} from "element-plus";
import UTILS from "@/utils/common";

const thisDomain = inject('thisDomain')
const form = ref({
  pwCode: '',
  password: '',
})

const pwCode = UTILS.GET('pwCode');
if (pwCode) {
  form.value.pwCode = pwCode;
}

const sendPwCode = () => {
  const messageHandler = ElMessage.success('发送中');
  HTTP.POST('/user/join', form).then((res) => {
    messageHandler.close();
    ElMessage.success(res.msg + ', 请登录')
  })
}
const onSubmit = () => {
  if (thisDomain.value.init) {
    const messageHandler = ElMessage.success('注册中');
    HTTP.POST('/user/join', form).then((res) => {
      messageHandler.close();
      ElMessage.success(res.msg + ', 请登录')
    })
  }
}
</script>
