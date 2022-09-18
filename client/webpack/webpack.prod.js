'use strict';

const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const webpack = require('webpack');

module.exports = (_env, prod) => {
  return {
    mode: 'production',
    output: {
      filename: 'static/js/[name].[contenthash:8].js',
    },
    module: {
      rules: [
        {
          test: /\.css$/i,
          use: [
            MiniCssExtractPlugin.loader,
            'css-loader',
          ],
        },
      ],
    },
    plugins: [
      new MiniCssExtractPlugin({
        filename: 'static/css/[name].[contenthash:8].css',
      }),
      new CleanWebpackPlugin(),
      new webpack.DefinePlugin({
        'process.env.API_URL': JSON.stringify(process.env.API_URL),
      }),
    ],
    optimization: {
      minimize: true,
      minimizer: [
        '...',
        new CssMinimizerPlugin(),
      ],
    },
  };
}
