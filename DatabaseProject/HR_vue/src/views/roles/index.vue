<template>
  <div>
    <h1>角色管理</h1>
    <el-row type="flex" class="row-bg">
      <el-col :span='1'>
        <el-input v-model="rid" placeholder="请输入角色ID"></el-input>
      </el-col>
      <el-col>
        <el-button @click='getRole(rid)'>查询</el-button>
      </el-col>
    </el-row>
    <el-divider v-if="roles != undefined"></el-divider>
    <el-descriptions title="角色信息" v-if="roles != undefined">
      <el-descriptions-item label="角色ID">{{roles.rid}}</el-descriptions-item>
      <el-descriptions-item label="角色名称">{{roles.name}}</el-descriptions-item>
      <el-descriptions-item label="权限值">{{roles.author}}</el-descriptions-item>
      <el-descriptions-item label="权限列表" :contentStyle="{'text-align': 'left'}">{{roles.permissions}}</el-descriptions-item>
  </el-descriptions>
  </div>
</template>

<script>
import { getRoleByrid } from '@/api/roles'
export default {
  name: 'roles',
  data(){
    return {
      roles:null,
      rid:1,
    }
  },
  methods:{
    getRole(rid) {
      getRoleByrid(rid).then(response => {
        const { data } = response
        this.roles=data;
      }).catch(error => {
        console.log(error)
      })
    },
  }
}
</script>

<style scoped>

</style>
