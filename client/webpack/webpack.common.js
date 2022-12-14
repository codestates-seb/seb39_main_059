// webpack.common.js
"use strict";

const path = require("path");
const dotenv = require("dotenv");
const webpack = require("webpack");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const CopyPlugin = require("copy-webpack-plugin");
const InterpolateHtmlPlugin = require("interpolate-html-plugin");
const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin');

// IMAGE_INLINE_SIZE_LIMIT라는 환경변수 사용
const imageInlineSizeLimit = process.env.IMAGE_INLINE_SIZE_LIMIT
  ? parseInt(process.env.IMAGE_INLINE_SIZE_LIMIT)
  : 1024 * 10;

module.exports = (_env) => {
  const envPath = path.join("./env", _env["env-path"] ?? ".env");
  dotenv.config({
    path: envPath,
  });
  return {
    entry: "./src/index.tsx",
    output: {
      path: path.resolve(__dirname, "../build"),
    },
    resolve: {
      extensions: [".ts", ".tsx", ".js", ".jsx", ".json"],
      plugins: [new TsconfigPathsPlugin({
        configFile: path.resolve(__dirname, "../tsconfig.json"),
        baseUrl: path.resolve(__dirname, "../")
      })],
    },
    module: {
      rules: [
        {
          test: /\.(js|jsx|ts|tsx)$/,
          use: ["babel-loader", "ts-loader"],
          exclude: /node_modules/,
        },
        {
          test: /\.(bmp|gif|jpe?g|png|webp)$/i,
          loader: "url-loader",
          options: {
            limit: imageInlineSizeLimit,
            name: "static/media/[name].[contenthash:8].[ext]",
          },
        },
        {
          test: /\.svg$/,
          use: [
            {
              loader: require.resolve('@svgr/webpack'),
              options: {
                prettier: false,
                svgo: false,
                svgoConfig: {
                  plugins: [{ removeViewBox: false }],
                },
                titleProp: true,
                ref: true,
              },
            },
            {
              loader: require.resolve('file-loader'),
              options: {
                name: 'static/media/[name].[hash].[ext]',
              },
            },
          ],
          issuer: {
            and: [/\.(ts|tsx|js|jsx|md|mdx)$/],
          },
        },
      ],
    },
    plugins: [
      new HtmlWebpackPlugin({
        template: "./public/index.html",
        filename: "index.html",
      }),
      new CopyPlugin({
        patterns: [
          {
            from: "public",
            to: ".",
            globOptions: {
              ignore: [path.resolve(__dirname, "../public/index.html")],
            },
          },
        ],
      }),
      new InterpolateHtmlPlugin({
        PUBLIC_URL: process.env.PUBLIC_URL,
        MY_KEY: process.env.MY_KEY,
      }),
      new webpack.DefinePlugin({
        "process.env.PUBLIC_URL": JSON.stringify(process.env.PUBLIC_URL),
        "process.env.APP_API": JSON.stringify(process.env.APP_API),
        "process.env.IMAGE_INLINE_SIZE_LIMIT": JSON.stringify(
          process.env.IMAGE_INLINE_SIZE_LIMIT
        ),
      }),
    ],
  };
};
