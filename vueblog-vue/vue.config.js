module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        // secure: false,
        pathRewrite: {
          '^/api': ''
        }
      },
      '/music': {
        target: 'http://localhost:3000',
        changeOrigin: true,
        // secure: false,
        pathRewrite: {
          '^/music': ''
        }
      },

    },
    // before: app => {}
  },

}
