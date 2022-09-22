<template>
  <div>
    <el-table :data="emps" border stripe>
      <el-table-column type="selection" align="left" width="55">
      </el-table-column>
      <el-table-column prop="name" label="姓名" align="left" width="120" fixed>
      </el-table-column>
      <el-table-column prop="workID" label="工号" align="left" width="120">
      </el-table-column>
      <el-table-column prop="email" label="邮箱地址" align="left" width="200">
      </el-table-column>

      <el-table-column prop="phone" label="电话号码" align="left" width="120">
      </el-table-column>
      <el-table-column label="工资账套" align="center">
        <template slot-scope="scope">
          <el-tooltip placement="right">
            <div slot="content">
              <table>
                <tr>
                  <td>基本工资</td>
                  <td>{{ scope.row.s.basicSalary }}</td>
                </tr>
                <tr>
                  <td>交通补助</td>
                  <td>{{ scope.row.s.trafficSalary }}</td>
                </tr>
                <tr>
                  <td>午餐补助</td>
                  <td>{{ scope.row.s.lunchSalary }}</td>
                </tr>
                <tr>
                  <td>奖金</td>
                  <td>{{ scope.row.s.bonus }}</td>
                </tr>
                <tr>
                  <td>养老金比率</td>
                  <td>{{ scope.row.s.pensionPer }}</td>
                </tr>
                <tr>
                  <td>养老金基数</td>
                  <td>{{ scope.row.s.pensionBase }}</td>
                </tr>
                <tr>
                  <td>医疗保险比率</td>
                  <td>{{ scope.row.s.medicalPer }}</td>
                </tr>
                <tr>
                  <td>医疗保险金基数</td>
                  <td>{{ scope.row.s.medicalBase }}</td>
                </tr>
                <tr>
                  <td>公积金比率</td>
                  <td>{{ scope.row.s.accumulationFundPer }}</td>
                </tr>
                <tr>
                  <td>公积金基数</td>
                  <td>{{ scope.row.s.accumulationFundBase }}</td>
                </tr>
              </table>
            </div>
            <el-tag type="success" v-if="scope.row.s.name">{{
              scope.row.s.name
            }}</el-tag>
            <el-tag v-else type="primary">暂未设置</el-tag>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-popover
            placement="left"
            title="编辑工资账套"
            @show="showPop(scope.row.s)"
            @hide="hide(scope.row)"
            width="200"
            trigger="click"
          >
            <div>
              <el-select v-model="currentSalary" placeholder="请选择">
                <el-option
                  v-for="item in salaries"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </div>
            <el-button slot="reference" type="danger"> 修改工资账套</el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <div style="display: flex; justify-content: flex-end; margin-top: 10px">
      <el-pagination
        background
        @current-change="currentChange"
        @size-change="sizeChange"
        layout="prev, pager, next, jumper, ->, total, slot"
        :total="total"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "SalSobCfg",
  data() {
    return {
      currentSalary: null,
      salaries: [],
      emps: [],
      currentPage: 1,
      size: 10,
      total: 0,
    };
  },
  mounted() {
    this.initEmps();
    this.initSalaries();
  },
  methods: {
    hide(data) {
      if (this.currentSalary && this.currentSalary != data.s.id) {
        this.putRequest(
          "/salary-adjust/update?eid=" + data.id + "&eSid=" + this.currentSalary
        ).then((resp) => {
          if (resp) {
            this.intEmps();
          }
        });
      }
    },
    showPop(data) {
      if (data) {
        this.currentSalary = data.id;
      } else {
        this.currentSalary = null;
      }
    },
    initSalaries() {
      this.getRequest("/salary-adjust/get-All").then((resp) => {
        if (resp) {
          this.salaries = resp;
        }
      });
    },
    currentChange(page) {
      this.currentPage = page;
      this.initEmps();
    },
    sizeChange(size) {
      this.size = size;
      this.initEmps();
    },
    initEmps() {
      this.getRequest(
        "/salary-adjust/list?currentPage=" +
          this.currentPage +
          "&size=" +
          this.size
      ).then((resp) => {
        if (resp) {
          this.emps = resp.date;
          this.total = resp.total;
          console.log(resp);
        }
      });
    },
  },
};
</script>

<style>
</style>