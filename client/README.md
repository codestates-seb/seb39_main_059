# react-ts-template

## 1. 프로젝트 초기화

```bash
npm init -y
```

package.json이 생성되었습니다. 필요한 모듈을 하나씩 추가하면서 설정을 진행해보도록 하겠습니다.

## 2. 폴더 구조 세팅

모듈을 설치하기 전에 cra에서 사용하는 폴더구조를 가져와서 사용하겠습니다.

cra로 생성한 프로젝트에서 public, src, tsconfig.json을 프로젝트 폴더에 추가해줍니다.

src 폴더에서 test, report 관련 파일은 사용하지 않울 예정이어서 삭제해 주고, 필요없는 import문도 제거해 주었습니다.

## 3. react 관련 패키지 설치, type 관련 패키지 설치

```bash
npm i react react-dom
```

위 명령어를 실행했고 eslint가 설치되어있다면 App.tsx에 오류들이 보이기 시작 합니다... 우선은 이 오류를 해결해 보겠습니다.

에러는 모두 형식 선언을 찾을 수 없기 때문에 발생하는 오류 입니다. 현식 선언을 d.ts로 직접 만들어 줘도 되지많 대부분의 경우 npm에 @types/[package name]으로 검색하면 이미 만들어져 있는 경우가 많으니 사용하도록 합시다.

```bash
npm i -D @types/react @types/react-dom
```

이제 `import logo from './logo.svg';`이 부분에서만 오류가 발생하네요. 이 부분은 위에서 말했던 것처럼 형식선언을 직접 만들어줘야 합니다. 이 프로젝트에서는 마찬가지로 cra 프로젝트를 `npm run eject`하면 src 폴더에서 생성되는 react-app-env.d.ts 파일을 사용해 주었습니다.

## 4. 웹팩 초기 설정

### 4.1 webpack 관련 패키지 설치

본격적으로 웹팩 설정을 하기 전에 패키지를 설치하고 폴더구조를 잡아보도록 하겠습니다.

```
npm i -D webpack webpack-cli webpack-dev-server webpack-merge
```

우선 기본적으로 사용되는 webpack,webpack-cli,webpack-dev-server를 다운 받아 줍니다.

webpack-merge 패키지는 두 웹팩 설정을 병합(merge)하여 적용할 수 있도록 하며 여기서는 공통 설정과 모드(development/production)에 따른 설정을 병합하여 모드에 따라 다른 설정을 사용하기 위한 목적으로 사용합니다.

패키지 설치가 완료되면 다음과 같이 파일을 만들어 줍니다.

```bash
.
├── webpack
│   ├── webpack.common.js
│   ├── webpack.dev.js
│   └── webpack.prod.js
│
└── webpack.config.js
```

### 4.2 mode별 webpack 설정 작성

#### webpack.config.js

wepack을 명령어로 실행할 때 config 파일을 특정해주지 않으면 기본적으로 실행되는 파일이 webpack.donfig.js 파일입니다. webpack.config.js에서 `mode`를 받아 적절한 파일을 webpack.common.js파일과 merge하여 내보내도록 합니다.

```js
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
```

이때 config파일을 `(_env, argv) => {}` 형식으로 작성하는 이유는 CLI 환경에서 넘겨받은 값을 설정에서 사용하기 위함입니다.
함수 형태로 export 하게 되면 첫 번째 인자로 환경변수, 두 번째 인자로 CLI argument들을 받게 됩니다.

#### config/webpack.common.js

```js
"use strict";

const path = require("path");

module.exports = (_env, argv) => {
  return {
    entry: "./src/index.tsx",
    // output
    output: {
      path: path.resolve(__dirname, "../build"),
    },
    // resolve
    resolve: {
      extensions: [".ts", ".tsx", ".js", ".jsx", ".json"],
    },
  };
};
```

#### config/webpack.dev.js

```js
"use strict";

const path = require("path");

module.exports = (_env, argv) => {
  return {
    mode: "development",
    output: {
      filename: "static/js/build.js",
    },
    devServer: {
      static: path.join(__dirname, '../build'),
      port: 3000,
      historyApiFallback: true,
    },
  };
};

```

#### config/webpack.prod.js

production 모드에서는 새롭게 빌드했을 때 브라우저의 캐시가 동작하지 않도록 하기 위해 파일의 이름과 더불어 8자리의 contenthash를 포함시켰습니다.

```js
"use strict";

module.exports = (_env, argv) => {
  return {
    mode: "production",
    output: {
      filename: "static/js/[name].[contenthash:8].js",
    },
  };
};
```

## 5. Loader 설정

> Webpack의 loaders를 사용하면 파일을 전처리 할 수 있습니다. 이렇게 하면 JavaScript를 넘어 모든 정적 리소스를 번들링할 수 있습니다.

`modul`e에 `rule`을 추가하는 방식으로 webpack에게 특정 파일에 특정 로더를 사용해야할 지 알려 줄 수 있습니다.

```js
// webpack/webpack.common.js
module.exports = (_env, argv) => {
  return {
    entry: "./src/index.tsx",
    output: {/* ... */},
    module: {
      // rules
    },
    resolve: {/* ... */},
  };
};
```

### 5.1 TypeScript 관련 webpack loader 설치 및 적용

이제 loader를 적절해 설정해서 타입스크립트를 변한할 수 있도록 합시다.

TypeScript 관련 설정은 development 모드나 production 모드나 동일할 것이므로 webpack.common.js에 추가해줍니다.

TypeScript 및 관련 loader 설치

```bash
npm i -D typescript ts-loader
```

babel 및 관련 loader 설치

``` bash
# Babel & Babel Preset 설치
npm i -D @babel/core @babel/preset-env @babel/preset-react @babel/preset-typescript

# Babel Polyfill 관련 패키지 설치
npm i core-js @babel/runtime
npm i -D @babel/plugin-transform-runtime @babel/runtime-corejs3

# Babel Loader 설치
npm i -D babel-loader
```

우선 .babelrc 설정 파일을 추가해 주겠습니다

```json
{
  "presets": [
    [
      "@babel/preset-env",
      { 
        "targets": "> 1%, not dead",
        "useBuiltIns": "usage",
        "corejs": 3
      }
    ],
    "@babel/react",
    "@babel/typescript"
  ],
  "plugins": [
    [
      "@babel/plugin-transform-runtime",
      {
        "corejs":3
      }
    ]
  ]
}
```

webpack.common.js를 수정해 주겠습니다.

```js
// webpack/webpack.common.js
module.exports = (_env, argv) => {
  return {
    entry: "./src/index.tsx",
    output: {/* ... */},
    module: {
      rules: [
        {
          test: /\.(js|jsx|ts|tsx)$/,
          use: ["babel-loader", "ts-loader"],
          exclude: /node_modules/,
        },
      ],
    },
};
```

한가지 주의할 점이 있다면 use 부분에서 여러 loader를 사용해야한다면 순서에 주의해야합니다.

배열 요스의 우측부터 loader가 적용됩니다. 위와 같은 경우에는 ts-loader가 먼저 적용되고 babel-loader가 먼저 적용됩니다.

즉 typescript(es6) -> javascript(es6) -> javascript(es5)로 변환됩니다.

- babel 관련 설정은 [여기](https://tech.kakao.com/2020/12/01/frontend-growth-02/)를 참고했습니다.

### 5.2 style 관련 webpack loader 적용

먼저 css를 해석하고 bundling하기 위한 설정을 하겠습니다

webpack.prod.js에 CSS 스타일을 별도의 파일로 추출하여 삽입하는 플러그인인 `mini-css-extract-plugin`을 사용하겠습니다.
loader도 포함되어있기 때문에 사용해 주겠습니다.

```bash
npm i -D mini-css-extract-plugin
```

```js
// webpack.prod.js

const MiniCssExtractPlugin = require('mini-css-extract-plugin');

module.exports = (_env, argv) => {
  return {
    mode: "production",
    output: {/* {... */},
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
    ],
  };
};
```

위 설정을 공통적으로 적용하지 않고 production 모드에만 적용한 이유는 개발 모드에서 다른 설정을 적용해 주기 위함입니다.

developement mode에서는 개발 시 자주 바뀌는 스타일을 반영해야하기 때문에 더 빠른 style-loader를 적용하겠습니다.
style-loader는 css-loader로 css파일을 해석하고 적용해야 하므로 다음과 같이 설정해 주었습니다.

```bash
npm i -D css-loader style-loader
```

```js
"use strict";

const path = require("path");

module.exports = (_env, argv) => {
  return {
    mode: "development",
    output: {
      filename: "static/js/build.js",
    },
    devServer: {
      static: path.join(__dirname, '../build'),
      port: 3000,
      historyApiFallback: true,
    },
    module: {
      rules: [
        {
          test: /\.css$/i,
          use: ['style-loader', 'css-loader',],
        },
      ],
    },
  };
};
```

### 5.3 style 관련 webpack loader 적용

```bash
npm i -D file-loader url-loader
```

```js
// webpack.common.js
'use strict';

const path = require('path');

// IMAGE_INLINE_SIZE_LIMIT라는 환경변수 사용
const imageInlineSizeLimit = process.env.IMAGE_INLINE_SIZE_LIMIT
  ? parseInt(process.env.IMAGE_INLINE_SIZE_LIMIT)
  : 1024 * 10;

module.exports = {
  entry: '...',
  output: { /* ... */ },
  modules: {
    rules: [
      { /* ... */ },
      {
        test: /\.(bmp|gif|jpe?g|png|svg|webp)$/i,
        loader: 'url-loader',
        options: {
          limit: imageInlineSizeLimit,
          name: 'static/media/[name].[contenthash:8].[ext]',
        },
      },
    ]
  },
  resolve: {/* ... */ },
};
```

### 6. index.html build 파일에 포함시키기

src/App.tsx를 엔트리 포인트로 번들링 된 파일은 index.html에서 불려와 body에 포함되어야 합니다.

public/index.html이 그 역할을 하나, 빌드가 된 폴더에는 포함이 되지 않기 때문에 플러그인을 사용해서 포함시켜 주도록 하겠습니다.

```bash
npm i -D html-webpack-plugin
```

```js
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  plugins: [
    new HtmlWebpackPlugin({
      template: './public/index.html',
      filename: 'index.html',
    }),
  ],
};
```

### 7. 나머지 설정들...