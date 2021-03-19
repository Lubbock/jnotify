import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/index'

Vue.use(Router)

var r = require.context("../components",true,/\.route\.js/)
var arr = [];
r.keys().forEach((item)=>{
  arr = arr.concat(r(item).default);
})
export default new Router({
  mode: "hash",//模式
  routes: [{
    path: '/',
    name: 'HelloWorld',
    component: Index
  },
    ...arr
  ]
})
// export default new Router({
//   routes: [
//     {
//       path: '/',
//       name: 'HelloWorld',
//       component: HelloWorld
//     },
//     {
//       path: '/test',
//       name: 'Test',
//       component: Test
//     }
//   ]
// })
