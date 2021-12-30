<template>
  <div>
    <h1>薪酬发放登记</h1>
    <el-button type="primary" @click="gener">生成发放</el-button>
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
          prop="dep1"
          label="1级机构"
          width="180">
        </el-table-column>
        <el-table-column
          prop="dep2"
          label="2级机构">
        </el-table-column>
        <el-table-column
          prop="dep3"
          label="3级机构">
        </el-table-column>
        <el-table-column
          prop="count"
          label="人数">
        </el-table-column>
      <el-table-column
        prop="sum"
        label="基础薪酬总额">
      </el-table-column>
        <el-table-column
          label="登记">
          <template slot-scope="scope">
            <el-link type="primary" @click="cancelDialog(scope.row)">登记</el-link>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="薪酬发放登记明细" :visible.sync="dialogVisible" width="90%" @close='handleCancle'>
      <div>薪酬发放单编号：{{serial.payrollID}}</div>
      <div>机构：{{serial.dep1}}/{{serial.dep2}}/{{serial.dep3}}</div>
      <div>总人数：{{serial.count}}，基本薪酬总额：{{serial.sum}}</div>
      <el-table :data="serial.serials">
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
        <el-table-column
          label="奖励奖金">
          <template slot-scope="scope">
            <el-input v-model="scope.row.bounty" @input="inputMoney(scope.row)" style="width:100px"></el-input>
          </template>
        </el-table-column>
        <el-table-column
        label="应扣罚金">
        <template slot-scope="scope">
          <el-input v-model="scope.row.penalty" @input="inputMoney(scope.row)" style="width:100px"></el-input>
        </template>
      </el-table-column>
      <el-table-column
      label="删除成员">
      <template slot-scope="scope">
        <el-button type="danger" @click="deleteMember(scope.$index,scope.row)">删除</el-button>
      </template>
    </el-table-column>
      </el-table>
      <!-- <div v-for="(value, name) in serial">
        {{ name }}: {{ value }}
      </div> -->
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="sendUpdate">提交</el-button>
    </span>
  </el-dialog>
  </div>
</template>

<script>
import { generalpayroll, selectPayroll, selectlpayrollByPayrollId, updatepayrollBySerialId, deletepayrollBySerialId} from '@/api/serial'
export default {
  name:"selectSerial",
  data(){
    return{
      serials:[],
      dialogVisible:false,
      serial:[],
    }
  },
  methods:{
    gener(){
      console.log("生成")
      generalpayroll().then(response => {
        const { data } = response
          this.$message({
            message: '生成成功',
            type: 'success'
          });
        this.getSerials();
      }).catch(error => {
        this.$message({
            message: '生成失败',
            type: 'error'
          });
        console.log(error)
      })
    },
    getSerials(){
      selectPayroll().then(response => {
        const { data } = response
        this.serials=data;
        console.log(this.serials);

      }).catch(error => {
        console.log(error)
      })
    },
    cancelDialog(row) {
      console.log(row)
      this.serial=row
      this.dialogVisible = true
    },
    handleCancle () {
      this.getSerials();
      this.dialogVisible = false
    },
    inputMoney(row){

    },
    deleteMember(index,row){
      // console.log(index)
      // console.log(row.serialID)
      var serialId=row.serialID
      deletepayrollBySerialId(serialId).then(response => {
        const { data } = response
        this.serial.serials.splice(index,1)
        this.$message({
          message: '删除成功：'+serialId,
          type: 'success'
        });
      }).catch(error => {
        this.$message({
          message: '删除失败：'+serialId,
          type: 'error'
        });
      })
    },
    sendUpdate(){
      var people=this.serial.serials;
      for(var i=0;i<people.length;i++){
        var user=people[i]
        if(user.bounty!=0 || user.penalty!=0){
          updatepayrollBySerialId(user).then(response => {
            const { data } = response
              this.$message({
              message: '更新成功：'+user.uid,
              type: 'success'
            });
          }).catch(error => {
            this.$message({
              message: '更新失败：'+user.uid,
              type: 'error'
            });
            console.log("error:"+user.uid)
          })
        }
      }
      this.dialogVisible=false;

    }
  },
  created(){
    this.getSerials();
  }
}
</script>

<style scoped>

</style>
