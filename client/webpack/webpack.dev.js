'use strict';
const path = require('path');

module.exports = (_env, prod) => {
  return {
    mode: 'development',
    devtool: 'inline-source-map',
    output: {
      filename: 'static/js/build.js',
    },
    devServer: {
      static: path.join(__dirname, '../build'),
      port: process.env.port ?? 3000,
      historyApiFallback: true,
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