'use strict';

const { merge } = require('webpack-merge');

// 공통으로 사용할 설정
const common = require('./webpack/webpack.common');
// development mode일 때 적용할 추가 설정
const developmentConfig = require('./webpack/webpack.dev');
// production mode일 때 적용할 추가 설정
const productionConfig = require('./webpack/webpack.prod');


module.exports = (_env, argv) => {
  // mode에 따라 webpack.dev.js, webpack.prod.js를 선택
  const isDevelopment = argv.mode === 'development';
  const config = isDevelopment ? developmentConfig : productionConfig;
  return merge(common(_env, argv), config(_env, argv));
};