<template>
    <el-row style="background-color: #eadfd6;">
        <el-col :span="2" style="background-color: #c2cbcb">
            <h2 class="mb-2">首页</h2>
            <MenuView></MenuView>
        </el-col>
        <el-col :span="22" style="background-color: beige">
            <router-view></router-view>
        </el-col>
    </el-row>
</template>

<script setup>
import MenuView from "@/view/common/MenuView.vue";

</script>
<style scoped>

</style>

