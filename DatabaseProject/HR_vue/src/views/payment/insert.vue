<template>
  <div>
    <h1>薪酬标准登记</h1>
    <el-divider></el-divider>
    <el-form :inline="true"  class="demo-form-inline">
      <el-row>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="薪酬标准编号">
          <el-input v-model="send.salaryId" placeholder="系统自动生成" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="薪酬标准名称">
          <el-input v-model="send.salaryName" placeholder="请输入内容" ></el-input>
        </el-form-item>
        <el-form-item label="薪酬总额">
          <el-input v-model="total" placeholder="自动计算" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="指定人" >
          <el-input v-model="send.mruname" ></el-input>
        </el-form-item>
        <el-form-item label="登记人" prop="native">
          <el-input v-model="booker.name" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="登记时间" prop="native" >
          <el-input :disabled="true" placeholder="自动获取系统时间" ></el-input>
        </el-form-item>
      </el-row>
    </el-form>
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
      <el-table-column label="薪酬项目金额" >
        <template slot-scope="scope">
          <el-input v-model="scope.row.money" @input="inputMoney(scope.row)" style="width:100px"></el-input>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getMNR } from '@/api/record'
import { insertPayment } from '@/api/payment'

export default {
  name:'payment_insert',
  data(){
    return{
      formInline: {
          user: '',
          region: ''
      },
      booker:'',
      send:{
        "salaryId": '',
        "salaryName": '',
        "basePay": 0,
        "mruname": "制定人",
        "subsidies": [
            {
                "subsidyId": 1,
                "salaryId": -1,
                "subsidyName": "交通补助",
                "money": 0
            },
            {
                "subsidyId": 2,
                "salaryId":-1,
                "subsidyName": "午餐补助",
                "money": 0
            },
            {
                "subsidyId": 3,
                "salaryId": -1,
                "subsidyName": "通信补助",
                "money": 0
            }
        ]
      },
      total:0,
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
      ]

    }
  },
  methods:{
    getBooker(){
      getMNR().then(response => {
        const { data } = response
        this.booker=data;
      }).catch(error => {
        console.log(error)
      })
    },
    inputMoney(row) {
        if(row.subsidyName=="基本工资"){
          this.item[4].money=row.money*0.08
          this.item[5].money=row.money*0.005
          this.item[6].money=row.money*0.02+3
          this.item[7].money=row.money*0.08
        }
        this.total=row.money*0.975+this.item[1].money+this.item[2].money+this.item[3].money-3
    },
    onSubmit() {
      this.send.salaryId=parseInt(this.send.salaryId)
      for(var i=0;i<this.send.subsidies.length;i++){
        this.send.subsidies[i].salaryId=this.send.salaryId
        this.send.subsidies[i].money=parseInt(this.item[i+1].money)
      }
      this.send.basePay=parseInt(this.item[0].money)
      if(this.send.salaryId==NaN){
        this.$message({
          message: '请输入薪酬编号id',
          type: 'error'
        });
        return false
      }
      console.log(this.send)

      insertPayment(this.send).then(response => {
        const { data } = response
        this.$message({
          message: '新建成功',
          type: 'success'
        });
      }).catch(error => {
        this.$message({
          message: '新建失败',
          type: 'error'
        });
        console.log(error)
      })
        console.log('submit!');
    }
  },
  created(){
    this.getBooker()
    this.total=this.item[0].money*0.975+this.item[1].money+this.item[2].money+this.item[3].money-3
  }
}
</script>

<style scoped>

</style>
