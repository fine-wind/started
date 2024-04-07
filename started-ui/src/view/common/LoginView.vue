<template>
  <div>
    <el-row>
      <el-col :span="8" :offset="8">
        <h1><a v-text="thisDomain.name" href="/"></a></h1>
        <h3 v-if="thisDomain.init">首次配置，将自动注册用户</h3>
        <h3 v-else>登录</h3>
        <el-form :model="form" label-width="120px">
          <el-form-item label="用户名">
            <el-input v-model="form.username"/>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" type="password" @keyup.enter="onSubmit()"/>
          </el-form-item>
          <el-form-item label="验证码" v-if="form.captcha">
            <el-input v-model="form.uuid" type="hidden"/>
            <el-row>
              <el-col :span="12">
                <el-input v-model="form.code" @keyup.enter="onSubmit()"/>
              </el-col>
              <el-col :span="12" @click="reCaptcha()">
                <el-image :src="form.img"/>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit()">登录</el-button>
          </el-form-item>
        </el-form>
        <div>
          <p>没有账号，去 <a href="/register">注册</a></p>
          <p>忘记密码了，去 <a href="/findPassword">找回密码</a></p>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import {inject, ref} from 'vue'
import HTTP from "@/utils/request";
import {ElMessage} from "element-plus";
import router from "@/routes";
import {setToken} from "@/auth";

const thisDomain = inject('thisDomain')
const form = ref({
  username: '',
  password: '',
  uuid: '',
  code: '',
  img: ''
})
setToken('')
const onSubmit = () => {
  const messageHandler1 = ElMessage.success('正在登录');
  const closeTime = Date.now() + 2000;
  HTTP.POST('/user/logIn', form.value).then((res) => {
    setTimeout(() => {
      messageHandler1.close();
      setToken(res.data)
      router.push('/')
      ElMessage.success(res.msg)
    }, Date.now() - closeTime)
  }).catch(error => {
    setTimeout(() => {
      messageHandler1.close();
      if (error?.data?.code === 412) {
        reCaptcha();
      }
      console.error(error)
    }, Date.now() - closeTime)
  })
}
const reCaptcha = () => {
  const params = {
    uuid: form.value.uuid,
    w: 100,
    _: Math.random()
  };
  HTTP.GET('/captcha.png', params).then((res) => {
    form.value.img = res.data.img;
    form.value.uuid = res.data.uuid;
  })
}
</script>
