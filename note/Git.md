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

