<template>
  <div style="width: 100%; height: 100%;" id="DataTable">
    <el-table-v2 :columns="data.columns" :data="data.tableData" :width="700" :height="400" fixed/>
  </div>
</template>

<script setup>
import {nextTick, onMounted, ref, defineProps} from 'vue'
import HTTP from "@/utils/request";

const props = defineProps({
  vp: {
    type: String,
    default: ""
  }
});
const data = ref({
  columns: [],
  tableData: []
})

onMounted(() => {
  nextTick(() => {
    let cur = document.getElementById("DataTable");
    let curHeight = cur.height;
    console.log(2222, curHeight);

    HTTP.POST(`/show/list/${props.vp}`, {}).then((res) => {
      res.data.columns.forEach(e => {
        // console.debug(e)
        data.value.columns.push(e)
      })
      res.data.data.forEach(e => {
        data.value.tableData.push(e)
      })
    })
  })
})
</script>
