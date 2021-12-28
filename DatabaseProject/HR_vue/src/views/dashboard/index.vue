<template>
  <div class="dashboard-container">
    <div class="dashboard-text">欢迎来到人力资源管理系统</div>
    <el-divider></el-divider>
    <el-descriptions title="角色信息">
      <el-descriptions-item label="档案ID">{{ uid }}</el-descriptions-item>
      <el-descriptions-item label="姓名"> {{ name }}</el-descriptions-item>
      <el-descriptions-item label="角色"> {{ role }}</el-descriptions-item>
      <el-descriptions-item label="权限列表" :contentStyle="{'text-align': 'left'}"> {{ permissions }}</el-descriptions-item>
    </el-descriptions>
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
