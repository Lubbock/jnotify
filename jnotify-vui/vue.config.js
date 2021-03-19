module.exports = {
  devServer: {
    // 端口号
    port: 7769,
    // 配置不同的后台API地址
    proxy: {
      '/api': {
        target: 'http://127.0.0.1',
        ws: false,
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
}
