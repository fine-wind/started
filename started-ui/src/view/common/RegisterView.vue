<template>
    <div>
        <el-row>
            <el-col :span="8" :offset="8">
                <h1 v-text="thisDomain.name"></h1>
                <h3 v-if="thisDomain.init">首次配置，将自动注册用户</h3>
                <h3 v-else>注册</h3>
                <el-form :model="form" label-width="120px">
                    <el-form-item label="用户名">
                        <el-input v-model="form.username"/>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input v-model="form.password" type="password" @keyup.enter="onSubmit()"/>
                    </el-form-item>
                    <el-form-item label="验证码">
                        <el-input v-model="form.uuid" type="hidden"/>
                        <el-row>
                            <el-col :span="12">
                                <el-input v-model="form.code" @keyup.enter="onSubmit()" placeholder="请输入右侧验证码"/>
                            </el-col>
                            <el-col :span="12" @click="reCaptcha()">
                                <el-image :src="form.img"/>
                            </el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit()">注册</el-button>
                    </el-form-item>
                </el-form>
                <div>
                    <p>已有账号，去<a href="/login">登录</a></p>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script setup>
import {inject, ref} from 'vue'
import HTTP from "@/utils/request";
import {ElMessage} from "element-plus";

const thisDomain = inject('thisDomain')
const form = ref({
  username: '',
  password: '',
  uuid: '',
  code: '',
  img: ''
})
const onSubmit = () => {
  if (thisDomain.value.init) {
    const messageHandler = ElMessage.success('注册中');
    HTTP.POST('/user/join', form).then((res) => {
      messageHandler.close();
      ElMessage.success(res.msg + ', 请登录')
    })
  }
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
reCaptcha()
</script>
