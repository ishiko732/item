<template>
  <div>
    <h1>查询档案</h1>
    <span class="demonstration">机构</span>
    <el-cascader
      v-model="dep"
      :options="departments"
      :props="{ expandTrigger: 'hover' , children:'departments', value:'deptID', label:'name', checkStrictly: true}"
      @change="handleChange"
      width=500px>
    </el-cascader>
    <!-- <el-divider></el-divider> -->
    <span class="demonstration">职位分类</span>
    <el-select v-model="job" placeholder="请选择" @change="selectJob">
      <el-option
        v-for="item in jobtitle"
        :key="item.pcId"
        :label="item.name"
        :value="item.pcId"
        @change="handleChange">
      </el-option>
    </el-select>
    <span class="demonstration">职位名称</span>
    <el-select v-model="pos" placeholder="请选择" @change="handleChange">
      <el-option
        v-for="item in position"
        :key="item.pid"
        :label="item.name"
        :value="item.pid">
      </el-option>
    </el-select>
    <div class="block">
      <p>建档时间{{ time }}</p>
      <el-date-picker
        v-model="time"
        type="daterange"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format="yyyy-MM-dd hh:mm:ss"
        :default-time="['00:00:00', '23:59:59']">
      </el-date-picker>
    </div>
    <el-button type="primary" :loading="loading" @click.native.prevent="selectRecords">查询</el-button>

    <div v-if="records != null">
      <el-divider></el-divider>
      <el-tag>符合条件的人力资源档案总数<span>{{records.length}}</span>例</el-tag>
      <el-table
        :data="records"
        style="width: 100%">
        <el-table-column
          prop="uid"
          label="档案编号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="gender"
          label="性别">
        </el-table-column>
        <el-table-column
          prop="department.departments[0].departments[0].name"
          label="一级机构">
        </el-table-column>
        <el-table-column
          prop="department.departments[0].name"
          label="二级机构">
        </el-table-column>
        <el-table-column
          prop="department.name"
          label="三级机构">
        </el-table-column>
        <el-table-column
          prop="jobTitles"
          label="职位名称">
        </el-table-column>
        <el-table-column
          label="查询">
          <template slot-scope="scope">
            <el-link type="primary" @click="cancelDialog(scope.row)">查看明细</el-link>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog
        title="信息"
        :visible.sync="dialogVisible"
        width="30%"
        >
        <div v-for="(value, name) in people">
          {{ name }}: {{ value }}
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogVisible = false">好的</el-button>
        </span>
      </el-dialog>
    </div>



  </div>
</template>

<script>
import { department, reDepartment, register, selectJobtitle, selectPosition,selectRecord, selectClassification } from '@/api/record'
export default {
  name: "selectRecord",
  data(){
    return {
      records:null,
      departments:[],
      jobtitle:[],
      position:[],
      dep: [],
      job: "",
      pos:"",
      time: '',
      loading: false,
      dialogVisible: false,
      records:null,
      people:null,
      };
  },
  methods:{
    handleChange(value) {
      console.log(value);
    },
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
        this.jobtitle=data;
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
    getPosition(pcid){
      selectPosition(pcid).then(response => {
        const { data } = response
        this.position=data;
        this.$forceUpdate()
      }).catch(error => {
        console.log(error)
      })
    },
    selectRecords(){
      this.loading=true
      var FormData = require('form-data');
      var data = new FormData();
      if(this.dep[2]!=null){
        data.append('fid', String(this.dep[2]));
      }
      if(this.job!=""){
        data.append('pcId', String(this.job));
      }
      if(this.pos!=""){
        data.append('pid', String(this.pos));
      }
      if(this.time!=''){
        data.append('time1', String(this.time[0]));
        data.append('time2', String(this.time[1]));
      }
      selectRecord(data).then(response => {
        const { data } = response
        this.records=data;
        this.loading=false
      }).catch(error => {
        console.log(error)
        this.loading=false
      })
    },
    cancelDialog(row) {
      this.people = row
      this.dialogVisible = true
      console.log(row)
    },
    selectJob(event,item){
      this.getPosition(event);
    },

  },
  created(){
    this.getDepartment();
    this.getClassification();
    this.getPosition();
  }

}
</script>

<style scoped>
</style>
