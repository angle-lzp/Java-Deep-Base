## Git提交日志规范

### 1，提交日志格式：
<type>(<scope>): <subject>

<body>

<footer>

### 2，各部分说明
Header（必需）：

type（必需）：提交类型
scope（可选）：影响范围
subject（必需）：简短描述
Body（可选）：详细描述

Footer（可选）：关联Issue或其他元数据
### 3，提交类型规范
类型	    说明	                      使用场景示例
feat	    新功能	                    新增模块、功能、接口等
fix	      修复Bug	                    修复普通Bug
hotfix	  紧急修复	                   生产环境紧急修复
docs	    文档更新	                  README、注释、文档修改
style	    代码格式调整  	            空格、格式化、缺少分号等（不改逻辑）
refactor	代码重构	                  既不是新增功能也不是修复bug的代码变更
perf	    性能优化	                  提升性能的代码变更
test	    测试相关	                   添加或修改测试用例
chore	    构建过程或辅助工具的变动      构建脚本、依赖管理、配置文件等
revert	  回滚提交	                   撤销之前的提交

### 4，具体场景应用
#### 4.1.紧急修复：hotfix
hotfix: 紧急修复支付接口金额计算错误

由于浮点数精度问题导致支付金额计算不准确，紧急修复此问题。
在高并发情况下可能导致账务差异。

Closes #1234

#### 4.2.修复bug：fix
fix(user): 修复用户登录验证码校验失败问题

修复了用户登录时验证码大小写敏感导致校验失败的问题。
现在验证码校验忽略大小写。

Fixes #567

#### 4.3.新增功能：feat
feat(payment): 添加微信支付功能

集成微信支付SDK，支持用户通过微信进行支付操作。
- 添加微信支付接口
- 实现支付回调处理
- 添加支付状态查询功能

Resolves #890

### 5，编写规范

#### 5.1.Header部分规范：
* 限制在50个字符以内
* 使用现在时态（如：添加、修复，而不是添加了、修复了）
* 首字母小写（除非是专有名词）
* 不以句号结尾

#### 5.2.Body部分规范：
* 使用空白行与Header分隔
* 每行不超过72个字符
* 解释"为什么"做这个变更，而不只是"做了什么"
* 可以使用项目符号列举变更点

#### 5.3.Footer部分规范：
* 关联Issue使用关键词如："Closes"、"Fixes"、"Resolves"
* BREAKING CHANGE应在此部分说明

## Git基本指令

* 1，创建分支并且进入该分支

```shell
git checkout -b feature-branch

# 或者

git switch -c feature-branch
```

* 2，将当前修改临时保存到内存，然后创建分支，切回到上一个分支可重新从内存中获取修改的内容

```shell
#存储修改的内容到内存中
Git stash

#将修改的数据从内存中获取
Git stash pop
```

* 3，查看当前项目的remote到底是什么

```shell
#方式一
git config --get remote.origin.url

#方式二
git remote -v
```

* 4，项目的remote协议

```shell
git remote set-url origin "xxx.git"
```

* 5，添加远程仓库地址：# 远端名称（取的一个别名），默认是origin;仓库路径，从远端服务器获取此URL

```shell
git remote add <远端名称><仓库路径>
```

* 6，获取远程仓库的远端名称

```shell
git remote
```

* 7，把本地代码推送到远程仓库：# -f 表示强制覆盖 # --set-upstream 推送到远端的同时并建立起和远端分支的关联关系(若远程分支名和本地分支名相同，则可以只写本地分支：git
  push origin master)

```shell
git push [-f][--set-upstream][远端名称[本地分支名]:[远端分支名]]		(注意本地分支和远程分支中间有一个 ： 符号q)
```

* 8，查看本地分支和远程分支的关系

```shell
Git branch -vv
```

* 9，在未来想从远程分支拉取更新到本地分支，可以进行如下设置（在后面进行push的时候，不用写远程分支，就只写本地分支就知道更新到远程哪个分支了）

```shell
git branch --set-upstream-to=origin/other slave	  #other:远端分支；slave:本地分支
```

* 10，也可以使用下面的方式进行关联，但是这个命令实际上可能不会按你期望的那样工作，因为它通常用于设置本地分支跟踪同名的远程分支

```shell
git push -u origin slave:other             #slave:本地分支，other:远端分支
git push --set-upstream origin slave:other #slave:本地分支，other:远端分支
```

* 11，一次信息，如果没有事先设置远端分支和本地分支的关联那么直接使用git push是识别不出来的；

```text
C:\Users\Angelo.Luo\Desktop\AngeloNote>git push
fatal: The current branch slave has no upstream branch.
To push the current branch and set the remote as upstream, use

    git push --set-upstream origin slave

To have this happen automatically for branches without a tracking
upstream, see 'push.autoSetupRemote' in 'git help config'.
```

* 12，如果直接使用：git push dev命令，会在远程创建一个dev的分支进行提交

