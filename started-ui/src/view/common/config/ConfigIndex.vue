<template>
  <div>
    <h2>配置页面</h2>
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="User" name="first" v-if="false">
        <DataTable vp="user"></DataTable>
      </el-tab-pane>
      <el-tab-pane label="数据表关系" name="dataTable">
        <DataTable vp="table"></DataTable>
      </el-tab-pane>
      <el-tab-pane label="角色" name="third" v-if="false">
        <DataTable vp="role"></DataTable>
      </el-tab-pane>
      <el-tab-pane label="Task" name="fourth" v-if="false">
        <DataTable vp="task"></DataTable>
      </el-tab-pane>
      <el-tab-pane label="系统配置" name="sysConfig" v-if="false">
        <DataTable vp="sysConfig"></DataTable>
      </el-tab-pane>
      <el-tab-pane label="更多" name="other">规划中</el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import DataTable from "./DataTable.vue";
import {ref} from "vue";

const activeName = ref('dataTable')

const handleClick = (tab, event) => {
  console.log(2222222222, tab, event)
}
</script>
