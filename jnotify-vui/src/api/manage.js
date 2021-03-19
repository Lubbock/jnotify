import Vue from 'vue'
import axios from "axios";

let apiBaseUrl = window._CONFIG['domianURL'] || "/kcsp-boot";
//console.log("apiBaseUrl= ",apiBaseUrl)
// 创建 axios 实例
const service = axios.create({
  //baseURL: '/jeecg-boot',
  baseURL: apiBaseUrl, // api base_url
  timeout: 9000 // 请求超时时间
})

//post
export function postAction(url,parameter) {
  return service({
    url: url,
    method:'post' ,
    data: parameter
  })
}

//post method= {post | put}
export function httpAction(url,parameter,method) {
  return service({
    url: url,
    method:method ,
    data: parameter
  })
}

//put
export function putAction(url,parameter) {
  return service({
    url: url,
    method:'put',
    data: parameter
  })
}

//get
export function getAction(url,parameter) {
  return service({
    url: url,
    method: 'get',
    params: parameter
  })
}

//deleteAction
export function deleteAction(url,parameter) {
  return service({
    url: url,
    method: 'delete',
    params: parameter
  })
}
