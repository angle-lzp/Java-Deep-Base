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

#切换当上一个工作目录：cd - or cd ..

#切换到home目录：cd or cd ~

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
#查找文件内容，有数据就返回一样的，有几行返回几行，没有就不返回
grep 'luo' a.log
#返回数据在文件中出现的行数
grep -n "luo" a.log

#查找文件夹中指定内容所在的所有文件，列出所有的文件包括内容在各个文件中的行数
# r：递归文件夹
# n：显示数据出现的行数
grep -rn 'luo' /root #(/root可以换成自己指定的文件夹目录)
> /root/a.log:5:luo
  /root/b.log:6:luo
  /root/b.log:7:luo
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
#创建符号软链接(软链接)/硬链接
#模板
ln [选项] 源文件 目标文件    #源文件：实际存在的文件；目标文件：创建链接的文件

#硬链接，删除一个，将仍能找到；删除了源文件，链接文件不受影响，更改任意一个链接文件，其他的链接文件也会被修改，但是源文件不会恢复；
ln cc ccAgain   #硬链接 ccAgain是一个单独的文件
ln 源文件.txt 链接文件.txt

#符号链接(软链接)；删除源文件，其他的链接文件也会跟着删除；重新创建相同名字的链接文件会自动生成对应的源文件，只是是全新的没有数据；
ln -s cc ccTo   #软链接格式：ccTo->cc
ln -s 源文件.txt 链接文件.txt
```
#### 2.9，管道和重定向
>1）批处理命令连接执行，使用 |  
    2）串联：使用分号;  
    3）前面成功，则执行后面一条，否则，不执行：&&  
    4）前面失败，则执行后一条：||  
```linux
#&&、||
#查看proc文件夹中的列表，存在返回suss信息，不存在返回fail信息；逻辑运算符的顺序和Java开发中是一样的 && > ||
ls /proc && echo suss!! || echo fail!!  

#同理
if ls /proc; then echo suss; else echo fail; fi
```
```linux
#重定向

#模板：
#shell指令 > 标准正确输出文件 2> 标准错误输出文件 (2>：是这种写法)

#示例：将ls -al /roots 的列表输出,正确的话写入list文件中，错误的写入到error文件中
ls -al /root2 > list 2> error

#注：如果需要将标准输出和标准错误输出都写入到list中，使用 2>$1 (&后面的是1(数字1，不是字母L)，并且它们是一体的，中间不能有空格)
ls -al /root2 > list 2>&1
#等价：
ls -al /root2 &> list
```
```linux
#清空文件
:> a.txt
```
```linux
#重定向(将数据换行追加到文件末尾)
echo aa >> a.txt

#覆盖文件中原有的数据
ehco cc > a.txt
```
#### 2.10，设置环境变量
#### 2.11，Bash快捷输入或删除
###### 快捷键
> Ctl-U   删除光标到行首的所有字符,在某些设置下,删除全行  
Ctl-W   删除当前光标到前边的最近一个空格之间的字符  
Ctl-H   backspace,删除光标前边的字符  
Ctl-R   匹配最相近的一个文件，然后输出  
#### 2.12，综合应用
```linux
#找到a.txt中每行包含sql，单不好含mysql的记录行的总数
cat -v a.txt | grep sql | grep -v mysql | wc -l

#cat -v 中的-v表示 "show nonprinting characters"，即显示文件中的非打印字符(类似于换行这些)
#grep -v 中的-v表示 "invert match"，即反转匹配，就是不匹配后面的数据
```
### 3，文本处理
#### 3.1，find文件查找
```linux
#查找txt和pdf类型的文件

find . \( -name "*.txt" -o -name "*.pdf" \) -print

#注：.：表示当前目录下以及子目录
    "\("、"\)"：是一体的不要和其他的指令连着写，否者无效
    -print：使用print函数打印出目录，不使用也是可以的，也一样会打印
```
```linux
#使用正则表达式查找.txt和.pdf文件

find . -regex ".*\(\.txt|\.pdf\)$"

#注：.：表示当前目录下以及子目录

#-iregex：可以忽略大小写的正则
```
```linux
#否定参数，查找所有非txt文件

find . ! -name "*.txt" -print
```
```linux
#指定搜索深度打印出当前目录的文件（例如：深度为1）

find . -maxdepth 1 -type f

#注：.：表示当前目录下(包括子目录以及子目录的子目录，一直往下)
    -maxdepth 1：搜索的深度为当前目录下
    -type f：只搜索文件，不包括目录和其他类型的文件
    -type d：是搜索目录
    -type l：符号链接（L）

#注：1：表示深度，如果等于1：深度就是当前目录下；如果等于2：深度就会到子文件夹中；3：深度会到子子文件夹中；往后同理
```
###### 定制搜索
* 按文件类型搜索
```linxu
#只列出所有的目录
find . -type d -print

#只列出所有的文件
find . -type f -print

#只列出所有的文件（L）
find . -type l -print

#列出所有的文件和目录
find .
```
```linux
#查找本地目录下所有的二进制文件
ls -lrt | awk '{print $9}'|xargs file|grep  ELF| awk '{print $1}'|tr -d ':'

#参考数据：二进制文件和文本文件的区分
$file redis-cli  # 二进制文件
redis-cli: ELF 64-bit LSB executable, x86-64, version 1 (SYSV), dynamically linked (uses shared libs), for GNU/Linux 2.6.9, not stripped
$file redis.pid  # 文本文件
redis.pid: ASCII text
```
* 按文件时间搜索
> -atime：访问时间(单位是天，分钟单位则是-amin，以下类似)  
-mtime：修改时间(内容被修改) 单位是天，-mmin：分钟
-ctime：变化时间(元数据或权限变化) 单位是天，-cmin：分钟
注：一个是内容修改，一个是元数据或权限的变化，需要加以区分
```linux
#最近第7天被访问的所有文件：第7天 -> 7
find . -atime 7 -type f -print
```
```linux
#最近7天内被访问过的所有文件：7天内 -> -7
find . -atime -7 -type f -print
```
```linux
#查询7天前被访问过的所有文件：7天前 -> +7
find . -atime +7 -type f -print
```
* 按文件大小搜索
```linux
#搜索文件大小大于2k的所有文件
find . -type f -size +2k

#注：c：Byte
     k：KB
     M：MB
     G：GB
```
* 按权限查找
```linux
#找具有可执行权限的所有文件
find . -type f -perm 644 -print 
```
* 按用户查找
```linux
#找具有可执行权限的所有文件
find . -type f -user username -print 
```
###### 找到后的后续动作
* 删除
```linux
#删除当前目录下所有pdf类型的文件
find . -type f -name "*.pdf" -delete

#另外一种语法
find . -type f -name "*.pdf" | xargs rm
```
* 执行动作（强大的exec）
```linux
#将当前目录下的文件的所有权变更为weber(注意有：{} \;)
find . -type f -user root -exec chown weber {} \;

#注：{}是一个特色的字符串，对于每一个匹配的文件，{}会被替换成对应的文件名
```
```linux
#在当前目录下将修改时间大于10天且后缀是pdf的所有文件复制到另外一个目录(other_dir这个目录下)
find . -type f -mtime +10 -name "*.pdf" -exec cp {} other_dir \;
```
* 结合多个命令
```linux
#如果后续需要执行多个命令，可以将多个命令写成一个脚本，然后在-exce调用时执行脚本即可
...... -exec ./command.sh {} \;

---------------------------------------------------------
eg:
#command.sh
#!/bin/bash
# 这是一个示例脚本，用于执行多个命令

# 打印当前目录
echo "当前目录："
pwd

# 列出当前目录下的所有文件和文件夹
echo "文件和文件夹列表："
ls -l

# 创建一个新文件
touch newfile.txt

# 显示新创建的文件的内容
cat newfile.txt
---------------------------------------------------------
#执行指令
find . -type f -name "*.txt" -exec ./command.sh {} \;
---------------------------------------------------------
```
* -print的定界符
```linux
#默认使用'\n'换行作为定界符

-print0 表示使用'\0'作为文件的定界符，打印出来的数据就不会换行了，这样可以搜索包含空格的文件

#eg:
find . -type f printO
```
#### 3.2，grep文本搜索
```linux
#默认访问匹配行(就是一行一行的进行匹配)

#模板
grep match_pattern file
```
###### 常用参数
> -o：只输出匹配的文本行  
> -v：只输出没有匹配的文本行  
> -c：统计文件中包含文本的次数  
    grep -c "text" a.txt  (统计text在a.txt中出现的次数)  
> -n：打印匹配的行号  
> -i：搜索是忽略大小写  
> -l：纸打印文件名称  
```linux
#在多级目录中对文件夹递归搜索文本（俺们程序员的最爱）
grep "class" . -r -n    #文件或路径必须在搜索内容后面，例如："class" .(.点表示当前目录下)
```
```linux
#匹配多个模式：-e（或的意思）
grep -e "sql" -e "mysql" a.txt
```
```linux
#grep输出以0作为结尾符的文件名（-z）(-0处理特殊字符的文件名称，然后使用rm进行删除)
grep "sql" a.txt -lz | xargs -0 rm
```
```linux
#综合应用：将多个日志文件中的所有带where条件的sql查找出来放入b文件之宏
cat LOG.* | tr a-z A-Z | grep "FROM" | grep "WHERE" > b
```
```linux
#查找中文示例：工程目录中utf-8格式和gb2312格式两种文件，要查找字的是中文；
#查找到它的utf-8编码和gb2312编码分别是E4B8ADE69687和D6D0CEC4
grep -rnP "\xE4\xB8\xAD\xE6\x96\x87|\xD6\xD0\xCE\xC4"
```
#### 3.3，xargs命令行参数转换