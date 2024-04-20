<template>
  <div>
    <h2>PLAN A</h2>
    <el-row>
      <el-col :span="1" v-for="dt in dates" :key="dt.date">
        <div class="date" style="" @wheel="wheelX" @click="yEvent = 0">{{ dt.date.substring(2) }}
          <br/>
          {{ UTILS.week(dt.date) }}
        </div>
        <el-button plain @click="clickAddPlan(dt.date)">添加</el-button>
        <template v-for="(event, index) in (finalDates[dt.date]||[])" :key="index">
          <el-card class="box-card" :style="{backgroundColor: event.color}" v-show="index >= yEvent">
            <el-popover :width="200" trigger="hover">
              <template #reference>
                <div @wheel="wheelY">
                  {{ event.text }}
                </div>
              </template>
              <template #default>
                <div v-html="event.text"></div>
              </template>
            </el-popover>
          </el-card>
        </template>
      </el-col>
    </el-row>
    <el-dialog v-model="dialogTableVisible" title="添加或修改">
      <el-form :model="form" label-width="120px">
        <el-form-item label="日期">
          <el-date-picker v-model="form.dt" type="date" placeholder="Pick a day" format="YYYY-MM-DD" value-format="YYYY-MM-DD" @input="changeTime"/>
        </el-form-item>
        <el-form-item label="信息">
          <el-input v-model="form.text"/>
        </el-form-item>
        <el-form-item label="标记">
          <el-color-picker v-model="form.color" size="large"/>
        </el-form-item>
        <el-form-item label="优先级">
          <el-slider v-model="form.sort" show-input/>
        </el-form-item>
      </el-form>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button>Cancel</el-button>
      </el-form-item>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, reactive} from "vue";
import HTTP from "@/utils/request";
import UTILS from "@/utils/common";
import {ElMessage} from "element-plus";
import "./css.css";

const dates = ref([])
let xDate = 0;
let yEvent = ref(0);
/* 添加 修改 */
const dialogTableVisible = ref(false)
const form = reactive({
  dt: '',
  text: '',
  color: '',
  sort: '',
})

/* 数据集合*/
const finalDates = {
  // '2023-12-05': [
  //     {
  //         text: '',
  //         color: '',
  //     }
  // ]
}
/* 初始化表头*/
HTTP.GET(`/plan/init`).then((res) => {
  res.data.forEach(function (item) {
    const dt = item.dt
    finalDates[dt] = finalDates[dt] || []
    finalDates[dt].push(item);
  });
  /* */
  for (let i = xDate - 12; i < 24 - 12; i++) {
    let date = new Date();
    let dt = UTILS.formatDate(new Date(date.setDate(date.getDate() + i)));
    dates.value.push({
      date: dt,
      events: finalDates[dt]
    })
  }
})

function wheelX(e) {
  console.debug('wheel', e)
  if (e.wheelDelta) {
    if (e.wheelDelta > 0) { //当鼠标滚轮向上滚动时
      upDates(-1)
    }
    if (e.wheelDelta < 0) {
      upDates(1)
    }
  } else if (e.detail) {
    if (e.detail < 0) { //当鼠标滚轮向上滚动时
      upDates(-1)
    }
    if (e.detail > 0) {
      upDates(1)
    }
  }
  return false;
}

function wheelY(e) {
  if (e.wheelDelta) {
    if (e.wheelDelta > 0) { //当鼠标滚轮向上滚动时
      console.debug("滚轮向上滚动时")
      upEvents(-1)
    }
    if (e.wheelDelta < 0) {
      console.debug("滚轮向下滚动时")
      upEvents(1)
    }
  }
}

/**
 * 横向时间轴
 * @param type
 */
function upDates(type) {
  xDate += type;
  if (type < 0) {
    // 向左滚动，向前处理数
    let date = new Date(dates.value[0].date);
    let dt = UTILS.formatDate(new Date(date.setDate(date.getDate() - 1)));
    dates.value.pop();
    dates.value.unshift({date: dt, events: finalDates[dt]})
    getPlanEvent(UTILS.formatDate(new Date(date.setDate(date.getDate() - 1))))
  }
  if (type > 0) {
    // 向右滚动，向前处理数
    let date = new Date(dates.value[dates.value.length - 1].date);
    let dt = UTILS.formatDate(new Date(date.setDate(date.getDate() + 1)));
    dates.value.shift()
    dates.value.push({date: dt, events: finalDates[dt]})
    getPlanEvent(UTILS.formatDate(new Date(date.setDate(date.getDate() + 1))))
  }
}

function upEvents(type) {
  yEvent.value = Math.max(0, yEvent.value + type);
  console.debug(yEvent)
}

function getPlanEvent(dt) {
  HTTP.GET(`/plan/${dt}`).then((res) => {
    finalDates[dt] = res.data
  })
}

function clickAddPlan(dt) {
  dialogTableVisible.value = true
  form.dt = dt;
  form.text = dt;
  console.debug(dt, form.text)
}

function changeTime(e) {
  this.$forceUpdate()
  console.debug(e)
}


const onSubmit = () => {
  HTTP.POST(`/plan/save`, form).then((res) => {
    ElMessage.success(res.msg)
    finalDates[form.dt] = res.data
  })
}
</script>
<style scoped>
.date {
  height: 50px;
  text-align: center;
  align-items: center;
}

</style>

