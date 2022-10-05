'use strict';
const path = require('path');

module.exports = (_env, prod) => {
  return {
    mode: 'development',
    devtool: 'inline-source-map',
    output: {
      filename: 'static/js/build.js',
      publicPath: '/',
    },
    devServer: {
      static: path.join(__dirname, '../build'),
      port: process.env.port ?? 3000,
      historyApiFallback: true,
      proxy: {
        '/api': { 
          target: 'https://catvillage.tk', 
          pathRewrite: { '^/api': '' },
          changeOrigin: true, 
          // secure: false,
          // logLevel: 'debug'
        },
      },
    },
    module: {
      rules: [
        {
          test: /\.css$/i,
          use: ['style-loader', 'css-loader',],
        },
      ],
    }
  }
};