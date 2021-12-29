<template>
  <div>
    <h1>删除标准</h1>
    <div v-if="records != null">
      <el-divider></el-divider>
      <el-tag type="danger">可删除的薪酬标准总数：<span>{{payments.length}}</span>条</el-tag>
      <el-table
        :data="payments"
        style="width: 100%">
        <el-table-column
          prop="salary.salaryId"
          label="薪酬标准编号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="salary.salaryName"
          label="薪酬标准名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="salary.basePay"
          label="基础工资">
        </el-table-column>
        <el-table-column
          prop="salary.total_Salary"
          label="薪酬总额">
        </el-table-column>
        <el-table-column
          label="复核">
          <template slot-scope="scope">
            <el-link type="primary" @click="cancelDialog(scope.row)">删除</el-link>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="薪酬标准详细内容" :visible.sync="dialogVisible">
      <el-form :inline="true"  class="demo-form-inline">
        <el-row>
          <el-form-item>
            <el-button type="danger" @click="checkPayment_success" >删除</el-button>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="薪酬标准编号">
            <el-input v-model="send.salaryId" placeholder="系统自动生成" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="薪酬标准名称">
            <el-input v-model="send.salaryName" placeholder="请输入内容" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="薪酬总额">
            <el-input v-model="send.total_Salary" placeholder="自动计算" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="指定人" >
            <el-input v-model="send.mruname" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="登记人" prop="native">
            <el-input v-model="send.registerName" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="登记时间" prop="native" >
            <el-input :disabled="true" v-model="send.registerTime" ></el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <el-divider></el-divider>
      <el-table
        :data="item"
        stripe
        width="80%"
        >
        <el-table-column
          prop="index"
          label="序号"

          >
        </el-table-column>
        <el-table-column
          prop="subsidyName"
          label="薪酬项目名称"
          >
        </el-table-column>
          <el-table-column label="薪酬项目金额" prop="money" ></el-table-column>
      </el-table>

    </el-dialog>
  </div>
</template>

<script>
import { check,checkById,deleteById} from '@/api/payment'
export default {
  name: "checkRecord",
  data(){
    return {
      payments:[],
      records:[],
      dialogVisible:false,
      send:[],
      item:[
        {
            "index":1,
            "subsidyName": "基本工资",
            "money": 5000
        },
        {
            "index":2,
            "subsidyName": "交通补助",
            "money": 300
        },
        {
            "index":3,
            "subsidyName": "午餐补助",
            "money": 500
        },
        {
            "index":4,
            "subsidyName": "通信补助",
            "money": 500
        },
        {
            "index":5,
            "subsidyName": "养老保险",
            "money": 400
        },
        {
            "index":6,
            "subsidyName": "失业保险",
            "money": 25
        },
        {
            "index":7,
            "subsidyName": "医疗保险",
            "money": 103
        },
        {
            "index":8,
            "subsidyName": "住房公积金",
            "money": 400
        }
      ],
    }
  },
  methods:{
    getPayment(){
      check(1).then(response => {
        const { data } = response
        this.payments=data;
        console.log(this.payments);

      }).catch(error => {
        console.log(error)
      })
    },
    cancelDialog(row) {
      this.send=row.salary
      this.send.rsalaryId=row.rsalaryId
      this.item[0].money=this.send.basePay
      for(var i=0;i<this.send.subsidies.length;i++){
        this.item[i+1].money=this.send.subsidies[i].money
        this.item[i+1].subsidyName=this.send.subsidies[i].subsidyName
      }
      this.item[4].money=this.send.basePay*0.08
      this.item[5].money=this.send.basePay*0.005
      this.item[6].money=this.send.basePay*0.02+3
      this.item[7].money=this.send.basePay*0.08
      this.dialogVisible = true
    },
    checkPayment_success(){
      this.dialogVisible = false
      deleteById(this.send.rsalaryId,).then(response => {
        const { data } = response
        this.getPayment()
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
    this.getPayment()

  }
}
</script>

<style scoped>

</style>
