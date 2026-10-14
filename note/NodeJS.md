# Node.js 常用命令

按日常开发流程整理：先检查环境，再创建和运行项目，随后管理依赖，最后进行调试或发布。

## 1. 检查运行环境

```shell
# 查看 Node.js 版本
node --version

# 查看 npm 版本
npm --version
```

## 2. 运行 JavaScript

```shell
# 运行 JavaScript 文件
node app.js

# 直接执行一段 JavaScript
node -e "console.log(1)"

# 进入交互式 REPL，逐行试运行代码
node -i
```

## 3. 创建项目

### 使用 Vite 创建 Vue 3 项目

```shell
npm create vite@latest
```

### 初始化普通 npm 项目

```shell
# 交互式创建 package.json
npm init

# 使用默认选项快速创建 package.json
npm init -y
```

## 4. 安装和管理依赖

```shell
# 安装 package.json 中声明的全部依赖
npm install

# 安装运行时依赖（写入 dependencies）
npm install vue-router@4

# 安装开发时依赖（写入 devDependencies）
npm install vue-router@4 --save-dev

# --save 与默认安装效果相同，显式写出也可以
npm install vue-router@4 --save

# 全局安装命令行工具
npm install -g typescript

# 卸载依赖
npm uninstall express
```

## 5. 运行和构建项目

项目脚本定义在 `package.json` 的 `scripts` 字段中。

```shell
# 运行 start 脚本
npm start

# 运行自定义脚本，例如 dev、build
npm run dev
npm run build

# 运行 test 脚本
npm test
```

## 6. 查看和维护依赖

```shell
# 查看依赖树
npm list

# 查看生产依赖
npm ls --omit=dev

# 查看开发依赖
npm ls --include=dev

# 检查依赖是否有可用更新
npm outdated

# 查看指定包的信息，或搜索 npm 仓库
npm info express
npm search keyword
```

### 安装锁定版本与清理缓存

```shell
# 按 package-lock.json 执行干净安装，常用于 CI/CD
npm ci

# 清理 npm 缓存
npm cache clean --force
```

## 7. 临时执行 npm 包

`npx` 可用于执行 npm 包提供的命令，无需先全局安装。

```shell
# 执行脚手架命令
npx create-react-app my-app

# 临时使用指定包执行命令
npx --package=typescript tsc
```

## 8. 管理 Yarn 和 pnpm

Corepack 可用于管理 Yarn、pnpm 等包管理器及其版本（Node.js 16.9+）。

```shell
# 启用 Corepack
corepack enable

# 激活指定版本
corepack prepare yarn@4.0.0 --activate
corepack prepare pnpm@8.0.0 --activate
```

启用并激活相应版本后，可直接使用 `yarn` 或 `pnpm` 命令。

## 9. 调试与性能分析

```shell
# 启动调试模式，可连接 Chrome DevTools
node --inspect app.js

# 启动后在第一行暂停
node --inspect-brk app.js

# 文件变化时自动重启（Node.js 18+）
node --watch app.js

# 生成 V8 性能分析日志
node --prof app.js

# 生成堆内存分析文件
node --heap-prof app.js

# 设置 V8 老生代内存上限为 4 GB
node --max-old-space-size=4096 app.js
```

## 10. 更新版本与发布 npm 包

```shell
# 更新版本号：补丁、次版本、主版本
npm version patch
npm version minor
npm version major

# 发布包到 npm 仓库
npm publish
```

## 命令速查

| 命令 | 主要用途 |
| --- | --- |
| `node` | 运行 JavaScript、进入 REPL、调试和性能分析 |
| `npm` | 初始化项目、管理依赖、运行脚本和发布包 |
| `npx` | 临时执行 npm 包命令 |
| `corepack` | 管理 Yarn、pnpm 等包管理器的版本 |
