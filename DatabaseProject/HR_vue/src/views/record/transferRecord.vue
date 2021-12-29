<template>
  <div>
    <h1>调动档案</h1>
    <div v-if="records != null">
      <el-divider></el-divider>
      <el-tag type="danger">当前可调动的人力资源档案总数<span>{{records.length}}</span>例</el-tag>
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
          label="查看">
          <template slot-scope="scope">
            <el-link type="primary" @click="cancelDialog(scope.row)">查看</el-link>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="复核信息" :visible.sync="dialogVisible">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px"  :inline="true">
          <el-form-item label="机构">
            <el-cascader
            v-model="dep"
            :options="departments"
            :props="{ expandTrigger: 'hover' , children:'departments', value:'deptID', label:'name', checkStrictly: true}"
            @change="handleChange"
            width=500px>
          </el-cascader>
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
          <!-- <el-form-item label="职位分类" v-for="pc in jobtitle" v-if="pc.pcId == position.pcId">
            <el-input v-model="pc.name" placeholder="请输入内容" :disabled="true"></el-input>
          </el-form-item> -->
          <!-- <el-form-item label="职位名称" prop="jobname">
            <el-input v-model="position.name" placeholder="请输入内容" ></el-input>
          </el-form-item> -->
          <el-form-item label="职称">
            <el-input v-model="ruleForm.jobTitles" placeholder="请输入内容" :disabled="true"></el-input>
          </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="ruleForm.name" placeholder="请输入内容"></el-input>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="info" @click="dialogVisible = false">取 消 操 作</el-button>
        <el-button type="danger" @click="checkRecord_success">调动</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { check,checkMessageById,checkById,deleteById,getPositionByPid, selectClassification,transfer,department,selectPosition } from '@/api/record'
import { getRoleList } from '@/api/roles'
export default {
  name: "transferRecord",
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
      departments:[],
      dep: [],
      roles:[],
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
    getPosition2(pcid){
      selectPosition(pcid).then(response => {
        const { data } = response
        this.position=data;
        this.$forceUpdate()
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
    getDepartment() {
      department().then(response => {
        const { data } = response
        this.departments=data;
      }).catch(error => {
        console.log(error)
      })
    },
    getRole(){
      getRoleList().then(response => {
        const { data } = response
        this.roles=data;
        console.log("roles")
        console.log(data)
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
    selectJob(event,item){
      this.getPosition2(event);
    },
    cancelDialog(row) {
      this.ruleForm=row.user
      // console.log(row)
      this.ruleForm.id=row.ruserId
      // this.getPosition(row.user.pid)
      this.getPosition2()
      this.getDepartment()
      this.getClassification()
      this.getRole()
      this.dialogVisible = true
    },
    checkRecord_success(){
      transfer(this.ruleForm).then(response => {
        const { data } = response
        this.selectRecords(1);
        this.$message({
          message: '调动成功，审核后生效！',
          type: 'success'
        });
      }).catch(error => {
        this.$message({
          message: '调动失败',
          type: 'error'
        });
      })
      this.dialogVisible = false
      //transfer
    },
    handleChange(value) {
      console.log(value);
    },
  },
  created(){
    this.selectRecords(1);

  }
}
</script>

<style scoped>

</style>
