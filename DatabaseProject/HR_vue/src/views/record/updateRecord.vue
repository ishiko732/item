<template>
  <div>
    <h1>档案变更-{{uid}}</h1>

    <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm" :inline="true">

      <el-row>
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
          <el-input v-model="ruleForm.role.name" placeholder="请输入内容" :disabled="true"></el-input>
        </el-form-item>
      </el-row>
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
      <el-row>
        <el-form-item>
          <el-button :plain="true" type="primary" :loading="loading" @click.native.prevent="submitForm('ruleForm')">更新</el-button>
        </el-form-item>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { getRecord, update, selectJobtitle, getPositionByPid, selectRecord, selectClassification,selectClassificationByPcId} from '@/api/record'
export default {
  name: "updateRecord",
  data(){
    return {
      uid:this.$route.query.uid,
      ruleForm:[],
      jobtitle:[],
      job:'',
      position:[],
      segender:[
      {'name':'男'},{'name':'女'}
      ],
      degrees:[
      {'name':'研究生'},{'name':'本科'},{'name':'专科'}
      ],
      booker:[],
      roles:[],
      loading:false,
    }
  },
  methods:{
    getRecord(uid){
      getRecord(uid).then(response => {
        const { data } = response
        this.ruleForm=data;
        this.getPosition(data.pid)
      }).catch(error => {
        console.log(error)
      })
    },
    getJobtitle(){
      selectJobtitle().then(response => {
        const { data } = response
        this.jobtitle=data;
      }).catch(error => {
        console.log(error)
      })
    },
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
    submitForm(formName) {
      this.loading=true
      // this.ruleForm.department=null
      // this.ruleForm.jobTitles=null
      // this.ruleForm.role=null
      var send=this.ruleForm
      delete send["department"]
      delete send["jobTitles"]
      delete send["role"]
      delete send['registrantTime']
      update(send).then(response => {
        this.loading=false
        this.$message({
          message: '更新成功',
          type: 'success'
        });
      }).catch(error => {
        this.loading=false
        this.$message({
          message: '更新失败',
          type: 'error'
        });
        console.log(error)
      })
    },
  },
  created(){
    this.getRecord(this.uid);
  }
}
</script>

<style scoped>

</style>
