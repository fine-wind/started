<template>
  <div style="width: 100%; height: 100%;" id="DataTable">
    <el-form :inline="true" :model="data.form" class="demo-form-inline">
      <el-form-item label="Approved by">
        <el-input v-model="data.form.user" placeholder="Approved by" clearable/>
      </el-form-item>
      <el-form-item label="Activity zone">
        <el-select v-model="data.form.region" placeholder="Activity zone" clearable>
          <el-option label="Zone one" value="shanghai"/>
          <el-option label="Zone two" value="beijing"/>
        </el-select>
      </el-form-item>
      <el-form-item label="Activity time">
        <el-date-picker v-model="data.form.date" type="date" placeholder="Pick a date" clearable/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="query">查找</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="data.tableData" style="width: 100%;">
      <template v-for="item in data.columns" :key="item.key">
        <el-table-column :column-key="item.key" :prop="item.key" :label="item.title" :width="item.width"></el-table-column>
      </template>
      <el-table-column fixed="right" label="操作">
        <template #default>
          <template v-for="operate in data.operates" :key="operate.key">
            <el-button link type="primary" size="small" @click="handleClick">{{ operate.title }}</el-button>
          </template>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :total="data.pagination.total" :current-page="data.pagination.current" :page-size="data.pagination.size" layout="prev, pager, next" @currentChange="showList"/>
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
  form: {
    user: "",
    region: "",
  },
  columns: [],
  operates: [],
  tableData: [],
  pagination: {
    current: 0,
    size: [10, 20],
    total: 0
  }
})

onMounted(() => {
  nextTick(() => {
    // let cur = document.getElementById("DataTable");
    // let curHeight = cur.height;
    // console.log(2222, curHeight);
    Promise.all([columns(), count()]).then(() => {
      showList(1);
    });
  })
})

const query = () => {
  count();
  showList(1);
}

const columns = () => {
  return HTTP.POST(`/show/columns/${props.vp}`, {}).then((res) => {
    res.data.forEach(e => {
      data.value.columns.push(e)
    })
  });
};
const count = () => {
  return HTTP.POST(`/show/count/${props.vp}`, {}).then((res) => {
    data.value.pagination.total = res.data
  });
};
const showList = (currPage) => {
  data.value.pagination.current = currPage;
  data.value.tableData = []
  return HTTP.POST(`/show/list/${props.vp}`, {}).then((res) => {
    res.data.data.forEach(e => {
      data.value.tableData.push(e)
    })
    data.value.operates = []
    res.data.operates.forEach(e => {
      data.value.operates.push(e)
    })
  });
};
const handleClick = () => {
  console.debug("操作按钮被点击了")
  // let promise = import("@/view/common/RegisterView.vue");
  // console.debug(promise)
  // todo 将操作组件 注册到公共领域，然后在这里用路由的形式来控制
}
</script>
