<template>
  <div class="dashboard-container">
    <div class="dashboard-text">欢迎来到人力资源管理系统</div>
    <div class="dashboard-text">UID: {{ uid }} , 姓名: {{ name }}</div>
    <div class="dashboard-text">角色: {{ role }}</div>
    <el-card class="box-card">
      权限列表
      <div v-for="permission in permissions" :key="permission" class="text item">
        {{ permission }}
      </div>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'uid',
      'role',
      'permissions'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
  .text {
    font-size: 14px;
  }

  .item {
    padding: 18px 0;
  }

  .box-card {
    width: 200px;
  }
</style>
