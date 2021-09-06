module.exports = {
  devServer: {
    https: false,
    // port: ,
    open: true,
    proxy: {
      '/api': {
        target: 'i5b105.p.ssafy.io/'
      }
    },
    historyApiFallback: true,
    hot: true
  },
  css: {
    requireModuleExtension: false // import 시에 경로에 .module 포함 안해도 됨.
  },

  lintOnSave: false,
  outputDir: '../backend/src/main/resources/dist'
}
