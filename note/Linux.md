## Linux指令
### 1，使用命令帮助
#### 1.1，whatis 简要说明命令的作用
```linux
#模板：

whatis command	

示例：查看cat指令的作用

whatis cat
```
#### 1.2，info 更详细的说明文档
```linux
#模板

info command	

#示例：就会看到更加详细的说明

info cat
```
#### 1.3，man 查询命令command的说明文档
###### a).在man的帮助手册中，帮助文档被分成了9个类别
>(1)、用户可以操作的命令或者是可执行文件  
    (2)、系统核心可调用的函数与工具等  
    (3)、一些常用的函数与数据库  
    (4)、设备文件的说明  
    (5)、设置文件或者某些文件的格式  
    (6)、游戏  
    (7)、惯例与协议等。例如Linux标准文件系统、网络协议、ASCⅡ，码等说明内容  
    (8)、系统管理员可用的管理条令  
    (9)、与内核有关的文件  
```linux
#模板

man command

#示例：man date
```
```linux
#当我们使用whatis printf的时候发现，在1分类和3分类都有，当我们要查看指定分类3中的帮助

man -k keyword 

示例：man 3 printf
```
```linux
#查询关键字 根据命令中部分关键字来查询命令，适用于只记住部分命令的场合

#示例：查找GNOME的config配置工具命令中包含"1"的指令（1也可以换成cat）

man -k GNOME config | grep 1
```
#### 1.4，which 查看路径
```linux
#模板

which command 

示例：which make	
>/opt/app/openav/soft/bin/make install
```
```linux
#查看程序的搜索路径：当一个系统中安装了同一个软件的多个版本，不确定使用的是哪个版本，就可以使用这个命令；

whereis command
```
### 2，文件及目录管理
#### 2.1，删除和创建
```linux
#创建：mkdir

#删除：rm

#删除非空目录：rm -rf file目录

#删除日志：rm *log (等价于：find ./ -name "*log" -exec rm {};) #删除以log结尾的文件

#移动：mv

#复制：cp 

#复制目录：cp -r source_dir dest_dir     目录有多层，所以需要加上-r表示递归

#查看当前目录下的文件个数：find ./ | wc -l
```

#### 2.2，目录切换
```linux
#找到文件/目录位置：cd

#切换当上一个工作目录：cd -

#切换当home目录：cd or cd ~

#显示当前目录：pwd

#更改当前工作路径为path：cd path  (path=/home/test)
```
#### 2.3，列出目录项
```linux
#显示当前目录下的文件：ls

#按时间排序以列表的方式显示目录项：ls -lrt
    l：以长格式显示文件信息，包括文件权限、所有者、大小、修改时间等
    r：反向排序，即倒序
    t：以修改时间进行排序，配合r使用，以修改时间进行倒序排序
    
#有些命令使用频率较高的可以建立快捷方式，在.bashrc中设置命令别名
alias lsl='ls -lrt'
alias lm='ls -al|more'      #more：以分页的方式展示列表项
alias lcn='ls | cat -n'     #cat -n：给每个列表项前面加一个编号：1  a 2 a.out 3 app 4 b 5 bin 6 config
```
**注：.bashrc在/home/你的用户名/文件夹下，以隐藏文件的方式存储；可以使用ls -a查看**
#### 2.4，查找目录及文件find/locate
```linux
#搜寻文件或目录
find ./ -name "core*" | xargs file  #输出各个文件的类型

#查找目标文件夹中是否有.o后最的文件
find ./ -name "*.o"

#递归当前目录及子目录删除所有的.o文件
find ./ -name "*.o" -exec rm {} \;

#find是实时查询，如果需要更快的查询，可以试试locate；locate会为文件系统建立索引库，如果文件更新，需要定期执行更新命令来更新索引库
#寻找包含有string的路径
locate string
#更新索引库
updatedb

#与find不同，locate不是实时查找。你需要更新数据库以获得最新的文件索引信息。
```

#### 2.5，查看文件内容
###### 查看文件：cat、vim、head、tail、more、less
```linux
#查看文件的同时显示行号
cat -n a.log

#按页显示列表内容
ls -al | more

#按页显示文件中的内容
more a.log

#只看前10行
head -n 10 a.log
同理：head -10 a.log   (-n 10 简写成：-10)

#显示文件后五行数据
tail -n 5 a.log (tail -5 a.log)

#查看两个文件之间的差别
diff file1.log file2.log

#d动态显示文本最新信息
tail -f a.log
```

#### 2.6，查找文件内容
```linux
#查找文件内容，有数据就返回一样的，没有就不返回
grap 'luo' a.log
```

#### 2.7，文件与目录权限修改
```linux
#改变文件的拥有者：chown

#改变文件读、写、执行等属性：chmod

#递归子目录修改：chown -r 用户名称 source/

#增加脚本可执行权限：chmod a+x a.log
```
#### 2.8，给文件增加别名
```linux
#创建符号软链接/硬链接

#硬链接，删除一个，将仍能找到
ln cc ccAgain

#符号链接(软链接)；删除源，另外一个无法使用
ln -s cc ccTo
```
#### 2.9，管道和重定向
>1）批处理命令连接执行，使用 |  
    2）串联：使用分号;  
    3）前面成功，则执行后面一条，否则，不执行：&&  
    4）前面失败，则执行后一条：||  