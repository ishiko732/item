<template>
  <div>
    <h1>薪酬发放复核</h1>
    <div >
      <el-divider></el-divider>
      <el-tag>待薪酬发放总数：<span>{{serials.length}}</span>条</el-tag>
      <el-table
        :data="serials"
        style="width: 100%">
        <el-table-column
          prop="payrollID"
          label="薪酬发放编号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="payroll.dep1"
          label="1级机构"
          width="180">
        </el-table-column>
        <el-table-column
          prop="payroll.dep2"
          label="2级机构">
        </el-table-column>
        <el-table-column
          prop="payroll.dep3"
          label="3级机构">
        </el-table-column>
        <el-table-column
          prop="payroll.count"
          label="人数">
        </el-table-column>
      <el-table-column
        prop="payroll.sum"
        label="基础薪酬总额">
      </el-table-column>
      <el-table-column
      prop="recheckTime"
      label="创建时间">
    </el-table-column>
        <el-table-column
          label="复核">
          <template slot-scope="scope">
            <el-link type="primary" @click="cancelDialog(scope.row)">复核</el-link>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="薪酬发放登记明细" :visible.sync="dialogVisible" width="90%">
      <div>薪酬发放单编号：{{serial.payrollID}}</div>
      <div>创建时间:{{serial.recheckTime}} <el-tag type="info">{{serial2.status}}</el-tag></div>
      <el-table :data="serial2.serials" >
        <el-table-column property="uid" label="档案编号" ></el-table-column>
        <el-table-column property="name" label="姓名" ></el-table-column>
        <el-table-column property="basePay" label="基本工资"></el-table-column>
        <el-table-column property="subsidies[0].money" label="交通补助"></el-table-column>
        <el-table-column property="subsidies[1].money" label="午餐补助"></el-table-column>
        <el-table-column property="subsidies[2].money" label="通信补助"></el-table-column>
        <el-table-column property="pi" label="养老保险"></el-table-column>
        <el-table-column property="ui" label="失业保险"></el-table-column>
        <el-table-column property="mi" label="医疗保险"></el-table-column>
        <el-table-column property="housingFund" label="住房公积金"></el-table-column>
        <el-table-column property="bounty" label="奖励奖金"></el-table-column>
        <el-table-column property="penalty" label="应扣罚金"></el-table-column>
      </el-table>
    <span slot="footer" class="dialog-footer">
      <div>
        <el-input
          type="textarea"
          :rows="2"
          placeholder="请输入内容"
          v-model="message">
        </el-input>
      </div>
      <el-button type="info" @click="dialogVisible = false">取消操作</el-button>
      <el-button type="danger" @click="deleteSerial">删除发放</el-button>
      <el-button type="primary" @click="checkSerial">复核通过</el-button>
    </span>
  </el-dialog>
  </div>
</template>

<script>
import { check,checkById,deleteById} from '@/api/serial'
export default {
  name: "checkSerial",
  data(){
    return {
      serials:[],
      serial:[],
      serial2:[],
      dialogVisible:false,
      send:[],
      message:''
    }
  },
  methods:{
    getSerials(){
      check().then(response => {
        const { data } = response
        this.serials=data;
      }).catch(error => {
        console.log(error)
      })
    },
    cancelDialog(row) {
      console.log(row)
      this.serial=row
      this.serial2=row.payroll
      this.dialogVisible = true
    },
    checkSerial(){
      var data ={
        'id':this.serial.rserialId,
        'message':this.message,
        'statusID':1
      }
      console.log(data)
      checkById(data).then(response => {
        const { data } = response
        this.dialogVisible = false
        this.getSerials();
        this.$message({
          message: '审批成功',
          type: 'success'
        });
      }).catch(error => {
        this.$message({
          message: '审批失败',
          type: 'error'
        });
      })
    },
    deleteSerial(){
      deleteById(this.serial.rserialId).then(response => {
        const { data } = response
        this.getSerials();
        this.dialogVisible = false
        this.$message({
          message: '删除成功',
          type: 'success'
        });
      }).catch(error => {
        this.$message({
          message: '删除失败',
          type: 'error'
        });
      })
    }
  },
  created(){
    this.getSerials();
  }
}
</script>

<style scoped>

</style>
