// webpack.common.js
"use strict";

const path = require("path");
const dotenv = require("dotenv");
const webpack = require("webpack");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const CopyPlugin = require("copy-webpack-plugin");
const InterpolateHtmlPlugin = require("interpolate-html-plugin");

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
      alias: {
        "@": path.resolve(__dirname, "src/"),
        "@Route": path.resolve(__dirname, "src/route/"),
        "@Pages": path.resolve(__dirname, "src/pages/"),
        "@Components": path.resolve(__dirname, "src/components/"),
        "@Style": path.resolve(__dirname, "public/scss/"),
        "@Public": path.resolve(__dirname, "public/"),
        "@Config": path.resolve(__dirname, "src/config/"),
        "@Stores": path.resolve(__dirname, "src/store/stores/"),
        "@types": path.resolve(__dirname, "../@types/"),
      },
    },
    module: {
      rules: [
        {
          test: /\.(js|jsx|ts|tsx)$/,
          use: ["babel-loader", "ts-loader"],
          exclude: /node_modules/,
        },
        {
          test: /\.(bmp|gif|jpe?g|png|svg|webp)$/i,
          loader: "url-loader",
          options: {
            limit: imageInlineSizeLimit,
            name: "static/media/[name].[contenthash:8].[ext]",
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
        "process.env.IMAGE_INLINE_SIZE_LIMIT": JSON.stringify(
          process.env.IMAGE_INLINE_SIZE_LIMIT
        ),
      }),
    ],
  };
};
