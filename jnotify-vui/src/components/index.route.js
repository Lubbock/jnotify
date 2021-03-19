/**
 * require.context
 * 返回一个方法
 * 第一个参数为需要查找的目录
 * 第二个参数为是否需要遍历子文件夹
 * 第三个参数为自定义规则
 */
var r = require.context('./views', false, /.vue/);
var arr = [];
//获取引入的资源['./mode1.index.vue','./mode1.second.vue']
r.keys().forEach((key) => {
  var _keyArr = key.split(".");
  if (key.indexOf("index") != -1) {
    arr.push({
      path: _keyArr[1],
      component: r(key).default //key对应的组件
    })
  } else {
    arr.push({
      path: _keyArr[1] + "/" + _keyArr[2],
      component: r(key).default

    })
  }
})
export default arr;
