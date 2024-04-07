<template>
    <router-view></router-view>
</template>
<script setup>
import HTTP from "@/utils/request";
import {inject} from "vue";

const thisDomain = inject('thisDomain')
HTTP.GET("/").then((res) => {
  thisDomain.value = res.data;
  document.title = thisDomain.value.name;
})
</script>
