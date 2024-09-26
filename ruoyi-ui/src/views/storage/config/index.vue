<template>
    <div class="app-container">
        <el-button type="primary" icon="el-icon-search" size="medium" @click="submitForm">保存</el-button>
        <el-card class="box-card" style="margin-top:10px" >
            <div slot="header" class="clearfix">
                <span>数据设置</span>
            </div>
            <div class="text item">
                <el-form ref="form" :model="form" :rules="rules" label-position="top">
                    <el-row :gutter="20">
                        <el-col :span="12" >
                            <el-form-item label="光伏发电时间段（峰、平时段加权平均电价（度/元））" prop="averagePrice">
                                <el-input v-model="form.averagePrice" placeholder="光伏发电时间段（峰、平时段加权平均电价（度/元））" />
                            </el-form-item>
                            <el-form-item label="首年衰减率（%）" prop="firstDecayRate">
                                <el-input v-model="form.firstDecayRate" placeholder="请输入首年衰减率（%）" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12" >
                            <el-form-item label="逐步衰减率（%）" prop="stepDecayRate">
                                <el-input v-model="form.stepDecayRate" placeholder="请输入逐步衰减率（%）" />
                            </el-form-item>
                            <el-form-item label="光伏组件功率（W）" prop="power">
                                <el-input v-model="form.power" placeholder="请输入光伏组件功率（W）" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
        </el-card>
        <el-card class="box-card" style="margin-top:10px">
            <div slot="header" class="clearfix">
                <span>年有效利用小时数设置</span>
            </div>
            <div class="text item">
                <el-form ref="hours" :model="hours" :rules="hourRules" label-position="top">
                    <el-row :gutter="20">
                        <el-col :span="6" v-for="item in areaDict">
                            <el-form-item :label="item.dictLabel" :key="item.dictCode">
                                <el-input v-model="hours[item.dictValue]"  @input="$forceUpdate()" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
        </el-card>
    </div>
</template>

<script>
import { photovoltaicAreaDict, getPhotovoltaicConfig, updatePhotovoltaicConfig } from "@/api/storage/PhotovoltaicConfig";

export default {
  name: "PhotovoltaicConfig",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 光伏参数表格数据
      PhotovoltaicConfigList: [],
      areaDict: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {
        id: 1,
        averagePrice: null,
        firstDecayRate: null,
        stepDecayRate: null,
        power: null,
        effectiveHours: [],
      },
      hours: {},
      // 表单校验
      rules: {},
      hourRules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询光伏参数列表 */
    getList() {
      this.loading = true;

      getPhotovoltaicConfig(1).then(response => {
        this.form = response.data;
    
        photovoltaicAreaDict().then(response => {
            this.areaDict = response.data;
            this.areaDict.forEach((area, index) =>{
                this.hours[area.dictValue] = this.selectEffectiveHours(this.form.effectiveHours, area.dictValue);
            });
            this.loading = false;
        });
      });
    },
    selectEffectiveHours(hours, id){
      var actions = [];
      Object.keys(hours).some((key) => {
        if (hours[key].id == id) {
          actions.push(hours[key].value);
          return true;
        }
      })
      if (actions.length === 0) {
        actions.push("");
      }
      return actions.join('');
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {

            var hours = [];
            this.areaDict.forEach((area, index) =>{
                hours.push({
                    id: area.dictValue,
                    name: area.dictLabel,
                    value: this.hours[area.dictValue]
                });
            });

            this.form.effectiveHours = hours;

            updatePhotovoltaicConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
            });
        }
      });
    }
  }
};
</script>