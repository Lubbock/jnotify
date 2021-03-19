<template>
<div >
  <el-row :gutter="10">
    <el-col :span="18" :offset="5" >
      <el-row :inline="true" :gutter="20" >
        <el-form ref="form" label-width="120px">
          <el-card shadow="hover" v-for="(item,index) in configs" :key="index">
            <el-col :span="12"  >
              <el-divider></el-divider>
              <el-form-item label="项目名称">
                <el-input v-model="item.pjname"></el-input>
              </el-form-item>
              <el-form-item label="配置文件地址">
                <el-input v-model="item.configPath"></el-input>
              </el-form-item>
              <el-form-item label="项目配置地址">
                <el-input v-model="item.proposPath"></el-input>
              </el-form-item>
            </el-col>
          </el-card>
        </el-form>
      </el-row>
        <el-button type="primary" round @click="addConfig" class="jz">增加配置</el-button>
    </el-col>
  </el-row>

</div>

</template>

<script>

export default {
  name: 'Index',
  data() {
    return {
      configs:[{
        "pjname":"",
        "proposPath":"",
        "configPath":""
      }]
    }
  },
  methods: {
    addConfig:function (){
      this.configs.push({
        "proposPath":"",
        "configPath":""
      })
      console.log(this.configs)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-col {
  border-radius: 4px;
}

.bg-purple-dark {
  background: #99a9bf;
}

.bg-purple {
  background: #d3dce6;
}

.bg-purple-light {
  background: #e5e9f2;
}

.jz{
  position: fixed;
  right: 100px;
  top: 50%;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>
