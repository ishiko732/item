<template>
  <div>
    <h1>复核档案</h1>
    <div v-if="records != null">
      <el-divider></el-divider>
      <el-tag>当前等待复核的人力资源档案总数<span>{{records.length}}</span>例</el-tag>
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
    <el-dialog title="复核信息" :visible.sync="dialogVisible">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px"  :inline="true">
          <el-form-item label="1级机构">
            <el-input v-if="ruleForm.department !=null" v-model="ruleForm.department.departments[0].departments[0].name" placeholder="请输入内容" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="2级机构">
            <el-input v-if="ruleForm.department !=null" v-model="ruleForm.department.departments[0].name" placeholder="请输入内容" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="3级机构">
            <el-input v-if="ruleForm.department !=null" v-model="ruleForm.department.name" placeholder="请输入内容" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="职位分类" v-for="pc in jobtitle" v-if="pc.pcId == position.pcId">
            <el-input v-model="pc.name" placeholder="请输入内容" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="职位名称" prop="jobname">
            <el-input v-model="position.name" placeholder="请输入内容" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="职称">
            <el-input v-model="ruleForm.jobTitles" placeholder="请输入内容" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="角色">
            <el-input v-if="ruleForm.role !=null" v-model="ruleForm.role.name" placeholder="请输入内容" :disabled="true"></el-input>
          </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="ruleForm.name" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="name">
          <el-select v-model="ruleForm.gender" placeholder="男">
            <el-option
                v-for="item in segender"
                :key="item.name"
                :label="item.name"
                :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="E-Mail">
          <el-input v-model="ruleForm.email" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="QQ" >
          <el-input v-model="ruleForm.qq" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="手机" >
          <el-input v-model="ruleForm.phone" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="住址" >
          <el-input v-model="ruleForm.address" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="邮编" >
          <el-input v-model="ruleForm.post" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="民族" >
          <el-input v-model="ruleForm.nation" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="宗教信仰" >
          <el-input v-model="ruleForm.religion"></el-input>
        </el-form-item>
        <el-form-item label="政治面貌" >
          <el-input v-model="ruleForm.politicalFace"></el-input>
        </el-form-item>
        <el-form-item label="学历" prop="politicalFace">
          <el-select v-model="ruleForm.degree" placeholder="">
            <el-option
                v-for="item in degrees"
                :key="item.name"
                :label="item.name"
                :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业" >
          <el-input v-model="ruleForm.speciality"></el-input>
        </el-form-item>
        <el-form-item label="身份证" >
          <el-input v-model="ruleForm.cardID" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="出生地" >
          <el-input v-model="ruleForm.native_" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="生日" >
          <el-date-picker
              v-model="ruleForm.birth"
              value-format="yyyy-MM-dd hh:mm:ss"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="开户行">
          <el-input v-model="ruleForm.payName" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="银行卡号码" prop="native">
          <el-input v-model="ruleForm.payID" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="登记人ID" prop="native">
          <el-input v-model="ruleForm.booker" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="登记时间" prop="native">
          <el-input  v-model="ruleForm.registrantTime" :disabled="true" placeholder="自动获取系统时间"></el-input>
        </el-form-item>
        <el-row >
          <el-form-item label="简历" prop="desc" >
            <el-input type="textarea" v-model="ruleForm.resume"  :autosize="{ minRows: 4, maxRows: 10}"></el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="info" @click="dialogVisible = false">取 消 操 作</el-button>
        <el-button type="warning" @click="checkRecord_success">复核通过</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { check,checkMessageById,checkById,deleteById,getPositionByPid, selectClassification } from '@/api/record'
export default {
  name: "checkRecord",
  data(){
    return {
      records:[],
      ruleForm:[],
      dialogVisible:false,
      position:[],
      jobtitle:[],
      job:'',
      segender:[
      {'name':'男'},{'name':'女'}
      ],
      degrees:[
      {'name':'研究生'},{'name':'本科'},{'name':'专科'}
      ],
    }
  },
  methods:{
    getPosition(pid){
      getPositionByPid(pid).then(response => {
        const { data } = response
        this.position=data;
        this.getClassification(this.position.pcId)
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

  }
}
</script>

<style scoped>

</style>
