const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  productionSourceMap: false,
  assetsDir: 'static',
  outputDir: 'dict',
  devServer: {
    proxy: {
      '/API': {
        target: 'http://localhost:80',  // 后台接口域名
        ws: true,        //如果要代理 websockets，配置这个参数
        secure: false,  // 如果是https接口，需要配置这个参数
        changeOrigin: true,  //是否跨域
        pathRewrite: {
          '^/API': '/'
        }
      },
    },
    client: {
      overlay: false
    }
  }
})
