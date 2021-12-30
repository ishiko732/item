<template>
  <div>
    <h1>标准查询</h1>
    <div v-if="records != null">
      <el-divider></el-divider>
      <el-tag>薪酬标准总数：<span>{{payments.length}}</span>条</el-tag>
      <el-table
        :data="payments"
        style="width: 100%">
        <el-table-column
          prop="salaryId"
          label="薪酬标准编号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="salaryName"
          label="薪酬标准名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="basePay"
          label="基础工资">
        </el-table-column>
        <el-table-column
          prop="total_Salary"
          label="薪酬总额">
        </el-table-column>
        <el-table-column
          label="查看">
          <template slot-scope="scope">
            <el-link type="primary" @click="cancelDialog(scope.row)">查看详细</el-link>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog title="薪酬标准详细内容" :visible.sync="dialogVisible">
      <el-descriptions title="薪酬标准信息" column="2">
        <el-descriptions-item :label="name" v-for="(value, name) in message">{{ value }}</el-descriptions-item>
      </el-descriptions>
      <!-- <div v-for="(value, name) in message">
        {{ name }}: {{ value }}
      </div> -->
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="dialogVisible = false">好的</el-button>
    </span>
  </el-dialog>
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
      message:[],
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
    cancelDialog(row) {
      this.message=row
      this.dialogVisible = true
    },
    checkRecord_success(){
      this.dialogVisible = false
      // delete send["department"]
      // delete send["jobTitles"]
      // delete send["role"]
      // delete send['registrantTime']
      // checkById(send).then(response => {
      //   const { data } = response
      //   this.selectRecords(0);
      //   this.$message({
      //     message: '更新成功',
      //     type: 'success'
      //   });
      // }).catch(error => {
      //   this.$message({
      //     message: '更新失败',
      //     type: 'error'
      //   });
      // })

    }
  },
  created(){
    this.getPayment()

  }
}
</script>

<style scoped>

</style>
