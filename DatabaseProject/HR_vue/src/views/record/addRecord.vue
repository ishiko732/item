<template>
  <div id="addRecord">
    <h1>档案登记</h1>

    <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm" :inline="true">

      <el-row>
        <el-form-item label="机构">
          <el-cascader required
          v-model="dep"
          :options="departments"
          :props="{ expandTrigger: 'hover' , children:'departments', value:'deptID', label:'name', checkStrictly: true}"
          width=500px>
          </el-cascader>
        </el-form-item>
        <el-form-item label="职位分类">
          <el-select v-model="job" placeholder="请选择" @change="selectJob" >
            <el-option
                v-for="item in jobtitle"
                :key="item.pcId"
                :label="item.name"
                :value="item.pcId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职位名称" prop="jobname">
          <el-select v-model="ruleForm.pid" placeholder="请选择" required>
            <el-option
              v-for="item in position"
              :key="item.pid"
              :label="item.name"
              :value="item.pid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职称">
          <el-select v-model="jt" placeholder="请选择" required>
            <el-option
                v-for="item in jobTitles"
                :key="item.jtId"
                :label="item.name"
                :value="item.jtId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="ruleForm.rid" placeholder="请选择" required>
            <el-option
                v-for="item in roles"
                :key="item.rid"
                :label="item.name"
                :value="item.rid">
            </el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="照片" prop="photo" required>
          <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item> -->
      </el-row>
      <el-form-item label="姓名">
        <el-input v-model="ruleForm.name" placeholder="请输入内容"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="ruleForm.password" placeholder="password" show-password></el-input>
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
      <el-form-item label="登记人" prop="native">
        <el-input v-model="booker.name" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="登记时间" prop="native">
        <el-input  :disabled="true" placeholder="自动获取系统时间"></el-input>
      </el-form-item>
      <el-row >
        <el-form-item label="简历" prop="desc" >
          <el-input type="textarea" v-model="ruleForm.resume"  :autosize="{ minRows: 4, maxRows: 10}"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item>
          <el-button :plain="true" type="primary" :loading="loading" @click.native.prevent="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-row>
    </el-form>
  </div>

</template>


<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 25px;
  color: #8c939d;
  width: 25px;
  height: 25px;
  line-height: 25px;
  text-align: center;
}

.avatar {
  width: 25px;
  height: 25px;
  display: block;
}


</style>


<script>
import { department, reDepartment, register, selectJobtitle, selectPosition,selectRecord,selectClassification,getMNR} from '@/api/record'
import { getRoleList } from '@/api/roles'
export default {
  name: 'addRecord',
  data() {
    return {
      ruleForm: {
        rid: '',
        fid: '',
        pid: '',
        name: '',
        password: '',
        image: '',
        gender: '男',
        email: '',
        phone: '',
        qq: '',
        address: '',
        post: '',
        native_: '汉族',
        birth: '',
        nation: '',
        religion: '无',
        politicalFace: '群众',
        cardID: '',
        degree: '本科',
        speciality: '',
        resume: '',
        payName: '',
        payID: '',
      },
      departments:[],
      dep: [],
      jobtitle:[],
      job:'',
      position:[],
      jobTitles:[],
      jt:'',
      segender:[
      {'name':'男'},{'name':'女'}
      ],
      degrees:[
      {'name':'研究生'},{'name':'本科'},{'name':'专科'}
      ],
      booker:null,
      roles:null,
      loading:false
    }
  },
  methods: {
    getDepartment() {
      department().then(response => {
        const { data } = response
        this.departments=data;
      }).catch(error => {
        console.log(error)
      })
    },
    getJobtitle(){
      selectJobtitle().then(response => {
        const { data } = response
        this.jobTitles=data;
      }).catch(error => {
        console.log(error)
      })
    },
    getPosition(pcid){
      selectPosition(pcid).then(response => {
        const { data } = response
        this.position=data;
        this.$forceUpdate()
      }).catch(error => {
        console.log(error)
      })
    },
    getClassification(){
      selectClassification().then(response => {
        const { data } = response
        this.jobtitle=data;
      }).catch(error => {
        console.log(error)
      })
    },
    getBooker(){
      getMNR().then(response => {
        const { data } = response
        this.booker=data;
      }).catch(error => {
        console.log(error)
      })
    },
    getRole(){
      getRoleList().then(response => {
        const { data } = response
        this.roles=data;
      }).catch(error => {
        console.log(error)
      })
    },
    submitForm(formName) {
      this.ruleForm.fid=this.dep[2]
      this.loading=true
      register(this.ruleForm).then(response => {
        this.loading=false
        this.$message({
          message: '创建成功',
          type: 'success'
        });
      }).catch(error => {
        this.loading=false
        this.$message({
          message: '提交失败',
          type: 'error'
        });
        console.log(error)
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    selectJob(event,item){
      this.getPosition(event);
    },
  },
  created(){
    this.getDepartment();
    this.getClassification();
    this.getPosition();
    this.getJobtitle();
    this.getBooker();
    this.getRole();
  }
}
</script>
