<template>
  <div id="MenuView">
    <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
      <el-sub-menu index="1">
        <template #title>
          <span>Navigator One</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="1-1">item one</el-menu-item>
          <el-menu-item index="1-2">item two</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="Group Two">
          <el-menu-item index="1-3">item three</el-menu-item>
        </el-menu-item-group>
        <el-sub-menu index="1-4">
          <template #title>item four</template>
          <el-menu-item index="1-4-1">item one</el-menu-item>
        </el-sub-menu>
      </el-sub-menu>
      <el-menu-item index="2">
        <router-link to="/config" replace>
          <span>配置页面</span>
        </router-link>
      </el-menu-item>
      <el-menu-item index="4">
        <router-link to="/plan" replace>
          <span>计划页面</span>
        </router-link>
      </el-menu-item>
      <el-sub-menu index="3">
        <template #title>
          <span>X-X-X</span>
        </template>
        <el-menu-item index="2">
          <router-link to="/login" replace>
            <span>登出</span>
          </router-link>
        </el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script setup>
const handleOpen = (key, keyPath) => {
  console.log("handleOpen", key, keyPath)
}
const handleClose = (key, keyPath) => {
  console.log("handleClose", key, keyPath)
}
</script>
<style scoped>
#MenuView {
  overflow-y: auto;
  height: 100%;
}
</style>
