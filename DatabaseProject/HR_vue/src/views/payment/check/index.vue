<template>
  <div>
    <h1>标准复核</h1>
    <div v-if="records != null">
      <el-divider></el-divider>
      <el-tag>薪酬标准总数：<span>{{records.length}}</span>条</el-tag>
      <el-table
        :data="records"
        style="width: 100%">
        <el-table-column
          prop="uid"
          label="档案编号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="user.name"
          label="姓名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="user.gender"
          label="性别">
        </el-table-column>
        <el-table-column
          prop="user.department.departments[0].departments[0].name"
          label="一级机构">
        </el-table-column>
        <el-table-column
          prop="user.department.departments[0].name"
          label="二级机构">
        </el-table-column>
        <el-table-column
          prop="user.department.name"
          label="三级机构">
        </el-table-column>
        <el-table-column
          prop="user.jobTitles"
          label="职位名称">
        </el-table-column>
        <el-table-column
          label="复核">
          <template slot-scope="scope">
            <el-link type="primary" @click="cancelDialog(scope.row)">复核</el-link>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { check,checkMessageById,checkById,deleteById,getPositionByPid, selectClassification } from '@/api/record'
import { getPayment_mode } from '@/api/payment'
export default {
  name: "checkRecord",
  data(){
    return {
      payments:[],
      records:[],
      dialogVisible:false,
    }
  },
  methods:{
    getPayment(){
      getPayment_mode().then(response => {
        const { data } = response
        this.payments=data;
        console.log(this.payments);

      }).catch(error => {
        console.log(error)
      })
    },
    getClassification(pcId){
      selectClassification().then(response => {
        const { data } = response
        this.jobtitle=data;
      }).catch(error => {
        console.log(error)
      })
    },
    selectRecords(id){
      check(id).then(response => {
        const { data } = response
        this.records=data;
      }).catch(error => {
        console.log(error)
      })
    },
    cancelDialog(row) {
      this.ruleForm=row.user
      // console.log(row)
      this.ruleForm.id=row.ruserId
      this.getPosition(row.user.pid);
      this.dialogVisible = true
    },
    checkRecord_success(){
      this.ruleForm.statusID=1
      this.ruleForm.status=null
      this.dialogVisible = false

      var send=this.ruleForm
      delete send["department"]
      delete send["jobTitles"]
      delete send["role"]
      delete send['registrantTime']
      checkById(send).then(response => {
        const { data } = response
        this.selectRecords(0);
        this.$message({
          message: '更新成功',
          type: 'success'
        });
      }).catch(error => {
        this.$message({
          message: '更新失败',
          type: 'error'
        });
      })
      console.log(this.ruleForm)

    }
  },
  created(){
    this.selectRecords(0);
    this.getPayment()

  }
}
</script>

<style scoped>

</style>
