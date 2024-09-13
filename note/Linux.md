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

> (1)、用户可以操作的命令或者是可执行文件  
> (2)、系统核心可调用的函数与工具等  
> (3)、一些常用的函数与数据库  
> (4)、设备文件的说明  
> (5)、设置文件或者某些文件的格式  
> (6)、游戏  
> (7)、惯例与协议等。例如Linux标准文件系统、网络协议、ASCⅡ码等说明内容  
> (8)、系统管理员可用的管理条令  
> (9)、与内核有关的文件

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
#创建文件夹：mkdir

#创建文件：touch

#删除：rm

#删除非空目录：rm -rf file目录

#删除日志：rm *log (等价于：find ./ -name "*log" -exec rm {}\;) #删除以log结尾的文件

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

> 1）批处理命令连接执行，使用 |
> 2）串联：使用分号;
> 3）前面成功，则执行后面一条，否则，不执行：&&
> 4）前面失败，则执行后一条：||

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

#注：如果需要将标准输出和标准错误输出都写入到list中，使用 2>&1 (&后面的是1(数字1，不是字母L)，并且它们是一体的，中间不能有空格)
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

> Ctl-U 删除光标到行首的所有字符,在某些设置下,删除全行
> Ctl-W 删除当前光标到前边的最近一个空格之间的字符
> Ctl-H backspace,删除光标前边的字符
> Ctl-R 匹配最相近的一个文件，然后输出

#### 2.12，综合应用

```linux
#找到a.txt中每行包含sql，但不包含mysql的记录行的总数
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
    -type d：只搜索目录
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
> -mtime：修改时间(内容被修改) 单位是天，-mmin：分钟
> -ctime：变化时间(元数据或权限变化) 单位是天，-cmin：分钟
> 注：一个是内容修改，一个是元数据或权限的变化，需要加以区分

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
#找属于某个用户的的所有文件
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

> -o：只输出匹配的文本行数据(一行一行的)
> -v：只输出没有匹配的文本行数据(一行一行的)
> -c：统计文件中包含文本的次数
> grep -c "text" a.txt  (统计text在a.txt中出现的次数)
> -n：打印匹配的行号
> -i：搜索是忽略大小写
> -l：只打印文件名称

```linux
#在多级目录中对文件夹递归搜索文本（俺们程序员的最爱）
grep "class" . -r -n    #文件或路径必须在搜索内容后面，例如："class" .(.点表示当前目录下)
```

```linux
#匹配多个模式：-e（或的意思）
grep -e "sql" -e "mysql" a.txt
```

```linux
#grep输出以0作为结尾符的文件名（-z）(-0：处理特殊字符的文件名称，然后使用rm进行删除)
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

-r 或 --recursive：递归地搜索指定目录下的所有文件和子目录。
-n 或 --line-number：输出匹配行的行号。
-P 或 --perl-regexp：使用Perl正则表达式进行模式匹配。
```

#### 3.3，xargs命令行参数转换（更多的是对文件进行操作）

```text
定义：xargs能够将输入数据转化为特定命令行参数；这样，可以配合很多命令来组合使用。比如grep，find；可以将多行输出转化成单行输出，也可以将单行输出转换成多行输出。
```

```linux
#多行转化成单行输出
cat a.txt | xargs
```

```linux
#单行转换成多行
cat b.txt | xargs -n 3

#注：-n：表示每行显示的字段数；-n 3：表示每行显示三个（默认以空格分隔）
#eg：a b c d e
#结果：a b c 
       d e
```

###### xargs参数说明

> -d：定义定界符 （默认为空格 多行的定界符为 n）  
> -n：指定输出为多行  
> -I {}：指定替换字符串，这个字符串在xargs扩展时会被替换掉,用于待执行的命令需要多个参数时  
> -0：指定0为输入定界符

```linux
# -d：定义定界符（默认为空格）：
默认情况下，xargs使用空格作为输入的定界符，将连续的参数分隔开来。通过指定-d选项，可以自定义定界符。例如，如果输入的文件名之间用逗号分隔，可以使用以下命令：
echo "file1,file2,file3" | xargs -d, ls
#注：这将执行ls file1 file2 file3，列出所有指定的文件。

# -n:-n 指定输出为多行：
默认情况下，xargs会将所有参数合并成一个命令行并执行。通过指定-n选项，可以将参数分成多个命令行进行执行。例如，下面的命令将每个文件名单独传递给rm命令：
echo "file1 file2 file3" | xargs -n 1 rm
#注：以空格(也可以是其他符号)作为分隔，每一行一个数据，然后每一行的文件名单独传给rm，执行删除操作；

# -I {}：指定替换字符串
echo "file1 file2 file3" | xargs -I {} cp {} /backup
#注：将执行cp file1 /backup、cp file2 /backup和cp file3 /backup；其实就是将第一个{}中的数据复制到第二个{}，可以理解为第二个{}是cp {} /backup中的一个占位符
    因为xargs返回的数据是要放在 cp {} /backup的中间的；如果数据就放在最后面例如：rm {} 直接使用-n 1就可以
    
# -0：指定0为输入定界符：
在某些情况下，输入的数据可能包含特殊字符或空格，导致默认的空格定界符无法正确解析。在这种情况下，可以使用-0选项指定\0作为输入定界符。
例如，假设有一个包含以空字符分隔的文件名列表的文件files.txt，可以使用以下命令处理这些文件：
xargs -0 < file.txt ls

# 注：直接在文件中使用\0，可能无法处理空字符；可以使用如下指令
printf "a.txt\0b.txt\0c.txt\0" > file.txt
```

```linux
# 示例
#读取file.txt中的数据，通过xargs变成一整行，然后从标准输入中读取参数替换成{}，然后将参数替换第二个{}，同时传递-p和-1的额外参数  
#通过-I {}是一行一行读取的，不会转换成一行
cat file.txt | xargs -I {} ./command.sh -p {} -1
```

```linux
#统计程序行数：统计每个.cpp文件中程序的总行数
find source_dir/ -type f -name "*.cpp" -print0 | xargs -0 wc -l
```

```linux
##redis通过string存储数据，通过set存储索引，需要通过索引来查询出所有的值：
./redis-cli smembers $1  | awk '{print $1}'|xargs -I {} ./redis-cli get {}
```

#### 3.4，sort排序

###### 字段说明

> -n：按数字进行排序(理解成就是按阿拉伯数字进行排序即可) vs  
> -d：按字典进行排序(应该是按ASCII码来进行比较的) vs  
> -r：逆序排序(倒序)  
> -k N：指定按第N列排序(每行文字的第几个字符：N=1：表示每一行的第一个字符)

```linux
#通过每行的第一个字符进行数字进行倒序排序
sort -nrk 1 data.txt

#忽略像空格之类的前导空白字符进行字典的升序排序（-b：空白字符）
sort -bd data.txt

#文本数据：data.txt
10 apple
5 banana
20 orange
#需要对数字进行排序(-k1：第一个字段；-k2：第二个字段，以此类推)
sort -nk -k1 data.txt
```

#### 3.5，uniq消除重复行

```linux
#消除重复行(去重)
sort unsort.txt | uniq
```

```linux
#统计各行在文件中出现的次数
sort unsort.txt | uniq -c
```

```linux
#找出重复行
sort unsort.txt | uniq -d
```

```linux
#可指定每一行中需要进行比较重复的内容：-s 开始位置(下边从0开始)，-w 比较字符
#文件名称：data.txt
sql123 34
sql456 45
mysql12 98
mysql98 67

#sql：-k1,1：表示以第一部分的第一个字符进行升序排序；uniq：去重；-s 1:从第二个字符开始；-w 3：比较后面三个字符（1，2，3）
sort -k1,1 data.txt | uniq -s 1 -w 3 
```

#### 3.6，用tr进行转换

```linux
#通用用法
echo 1234 | tr "0-9" "9876543210"
输出：8765
```

```linux
#tr删除字符
#只打印data.txt文件中不含数字的数据（相当于打印之前把数字给删除了）
cat data.txt | tr -d '0-9'
```

```linux
# -c 选项表示压缩模式，只保留指定的字符集。

#获取文件中所有数字
cat data.txt | tr -c '0-9'

#删除非数字数据,并将删除的数据用\n替换
cat data.txt | tr -d -c '0-9 \n'
```

```linux
#tr压缩字符
#tr -s压缩文本中出现的重复字符；最常用于压缩多余的空格
cat data.txt | tr -s " " 
```

###### tr中可用各种字符类

> alnum：字母和数字  
> alpha：字母  
> digit：数字  
> space：空白字符  
> lower：小写  
> upper：大写  
> cntrl：控制（非可打印）字符  
> print：可打印字符

```linux
#使用方法：tr [:class:] [:class:]
#表示将文件里面的字符小写的转化成大写：[原来类型] -> [需要类型]
cat data.txt | tr '[:lower:]' '[:upper:]'
```

#### 3.7，cut按列切分文本

```linux
#截取文件的第2列和第4列
cut -d " " -f2,4 data.txt

#注：-d " "：表示以空格进行分隔，需要标明是以什么进行分隔的；
    -f2,4：表示截取文件中的第2列和第4列；（下标从1开始）
```

```linux
#去除文件除了第3列的所有的列（只保留第3列）(complement:补充、补足)
cut -f3 --complement data.txt
```

```linux
# -d：指定定界符（分隔符）;以;作为定界符，只截取出第2个字符
cut -f2 -d ";" data.txt
```

* cut取的范围
    * N-：第N个字段到结尾
    * -M：第1个字段到第M个字段
    * N-M：N到M个字段

* cut取的单位
    * -b：以字节为单位
    * -c：以字符为单位
    * -f：以字段为单位（需要使用定界符分隔 -d，字段 一段一段的；）

```linux 
#以字段为单位 截取第2个字符到的结尾，以空格分隔
#数据：echo 1 2 3 4 5 6 > data.txt
cut -f2- -d " " data.txt

out:2 3 4 5 6
```

```linux
#以字段为单位 截取第1个字段到第3个字段
#数据：echo 1 2 3 4 5 6 > data.txt
cut -f-3 -d " " data.txt

out:1 2 3
```

```linux
#以字段为单位 截取第2个到第4个数据
#数据：echo 1 2 3 4 5 6 > data.txt
cut -f2-4 -d " " data.txt
cut -f2,4 -d " " data.txt (功能一致，同上)
out:2 3 4 
```

```linux
#以字节为单位截取第1个字节到第4个字节
#数据：echo 1 2 3 4 5 6 > data.txt
cut -c1-4 data.txt

out:1 2 (2后面还有一个空格)
```

```linux
以字符为单位截取第1到第3个字符
#数据：echo 1 2 3 4 5 6 > data.txt
cut -c1-3 data.txt

out:1 2(2后面没有字符)
```

```linux
#截取文本第5到第7列
echo string | cut -c5-7
```

#### 3.8，paste按列拼接文本

```linux
#将两个文本按列拼接在一起（a文本的第一行和b文本的第一行默认以制表符分隔然后拼接在一起）
cat file1
1
2

cat file2
colin
book

paste file1 file2
1 colin
2 book
```

```linux
#默认的定界符是制表符，可以用-d指明定界符
paste file1.txt file2.txt -d ";"

out:
1,colin
2,book
```

#### 3.9，wc统计行和字符的工具

```linux
#统计行数
wc -l data.txt

#统计单词数
wc -w data.txt

#统计字符数
wc -c data.txt
```

#### 3.10，sed文本替换利器

```text
sed的全称是Stream Editor，它是一个流编辑器，用于对输入流（文件或管道）进行基本的文本转换。
```

```linux
#首处替换
#替换每一行的第一处匹配的text将其替换为replace_text，同一行中的第2处就不会被替换
sed "s/text/replace_text/" data.txt

#默认替换后，输出替换后的内容，如果需要直接替换源文件，使用-i
sed -i "s/text/replace_text/" data.txt

#移除空白行
sed "/^$/d" data.txt

#全局替换
sed "s/text/replace_text/g" data.txt

#注：s（substitute）：替换操作
    g：表示全局的意思，全局替换；不加的话就替换每一行的第一处
```

```linux
#变量转换

#已匹配的字符串通过标记&来引用，将字符放到[]中，&相当于每个字符的占位符
echo this is an example | sed "s/\w[&]/g"

#注：\w：表示字段，这里表示单个单词
    g：全局，表示全局进行标记        
```

```linux
#字串匹配标记
#第一个匹配到括号内的内容使用标记1来替换，因为后面没有g所以不是全局的；匹配luo后面任意一个数字的数据将其替换为1
例如：luo1 smart
      luo1,luo2,angelo
文件：data.txt

#使用正则表达式：\([0-9]\)
sed "s/luo\([0-9]\)/\1/" data.txt

out：
1 smart
1,luo2,angelo

#注：\1表示引用这个捕获的数字字符。如果不使用反斜杠，1将被解释为字面上的字符"1"，而不是引用捕获的数字字符。
```

```linux
#双引号求值
#sed通常使用单引号引用；也可以使用双引号，使用双引号后，双引号会对表达式求值
sed 's/$var/HELLO/'
注：$var是提前定义好的一个属性

#当使用双引号是，我们可以在sed样式和替换字符串中指定变量
eg:
p=patten
r=replaced
echo "line con a patten" | sed "s/$p/$r/g"
$>line con a replaced

将变量p的值由变量r来进行替换
s：替换
g：全局
```

```linux
#字符串插入字符：将文本中每行内容（ABCDEFG）转换成ABC/DEFG
echo ABCDEFG | sed "s/^.\{3\}/&\//g"
out:ABC/DEFG
#注：^.\{3\}：前面的任意三个字符
    &/：&表示引用整个匹配模式，即前面匹配的三个字符，会在前面的三个字符后面加上/（&\/中的\表示转义）
```

#### 3.11，awk数据流处理工具

###### awk脚本结构

```linux
#BEGIN|END必须是要大写，Linux中区分大小写
awk 'BEGIN{ statements } statements2 END{ statements }'
```

###### 工作方式

```text
1.执行BEGIN中的语句块；
2.从文件或stdin(标准输入流)中读取一行，然后执行statements2，重复这个过程，直到文件全部被读取完毕；
3.执行END中的语句块
```

###### print打印当前行

```linux
#使用不带参数的print是，会打印当前行(statements2中的语句块)
echo -e "abc\ndefg" | awk 'BEGIN{print "start"} {print} END{print "END"}'

#第2个的{print}是没有参数的，它的参数来自于前面的"abc\ndefg"，它会打印每一行
# -e：表示启用对反斜杠转义的解释，不使用的话就会被当作普通的字符串
```

```linux
#print以逗号分个的时候，返回的参数以空格定界
echo | awk '{var1="v1";var2="v2";var3="v3";print var1,var2,var3;}'

out:(以空格分隔)
v1 v2 v3
```

```linux
#使用-拼接符的方式（以-作为定界符，也可以使用其他的符号，只要在""里面就可以）
echo | awk '{var1="v1";var2="v2";var3="v3";print var1"-"var2"-"var3;}'

out:(以-分隔)
v1-v2-v3
```

###### 特殊变量：NR NF $0 $1 $2

* NR：表示记录数量，在执行过程中对应的当前行号（第几行数据）
* NF：表示字段数量，在执行过程中对应当前行中的字段数量（有定界符来决定）
* $0：包含执行过程中当前行的文本内容
* $1：第一个字段的文本内容
* $2：第二个字段的文本内容
* $3：同理
* ...以此类推...

```linux
echo -e "line1 f1 f2 f3\nline2 f3" | awk '{print NR":"NF":"$0"-"$1"-"$2}' 

out:
1:4:line1 f1 f2 f3-line1-f1
1:2:line2 f3-line2-f3-
```

```linux
#打印每一行的第2和第3个字段
echo -e "f11 f12\nf21 f22" | awk '{print $1"-"$2}'

#读取文件（同理）
awk '{print $1"-"$2}' data.txt

out:
f11-f12
f21-f22
```

```linux
#统计文件的行数
awk 'END {print NR}' data.txt

echo -e "a\nb\nc\nd\ne" | awk 'END {print NR}'

out:
5
```

```linux
#累加每一行的第一个字段
echo -e " 1\n 2\n 3\n 4" | awk 'BEGIN{str="";print "Begin";} {str=str" "$1;} END{print str;print "End";}'

out:
Begin
1234
End
```

###### 传递外部变量

```linux
#输入来自stdin
root>data=1000
root>echo | awk '{print var}' var=$data
root>1000
```

```linux
#输入来自文件
awk '{print var}' var=$data data.txt
```

###### 用样式对awk处理的行进行过滤

```linux
文件data.txt
a\nb\nc\nd\ne\nf\ng

#行号小于5
awk 'NR < 5' 
out：a\nb\nc\nd

#行号等于1和4的打印出来，如果还有多个直接NR==5用;分隔
awk 'NR==1;NR==4 {print}' data.txt
out:a\nd

#行号在1和4之间的数据打印出来，用,隔开；{print}加不加都可以，一样会打印；
awk 'NR==1,NR==4 {print}' data.txt
out：a\nb\nc\nd
#注：如果NR==1,NR==4,NR==5,... 超过了两个，那么就取第一个的数据NR==1的数据

#包含linux文本的行（可以用正则表达式来指定，超级强大）
awk '/linux/' data.txt
out:data.txt每一行都不包含linux，所以没数据输出

#不包含linux文本的行
awk '!/linux/' data.txt
out：a\nb\nc\nd\ne\nf\ng （全部输出）
```

###### 设置定界符

```linux
#使用-F来设置定界符（默认是空格）
awk -F: '{print $NF}' /etc/passwd       有$：打印最后一个字段数据（由定界符决定）
awk -F: '{print NF}' /etc/passwd        无$：打印字段数量（由定界符决定）
```

###### 读取命令输出

```linux
#使用getline，将外部shell命令的输出读入到变量cmdout中
echo | awk '{"grep root /etc/passwd" | getline cmdout; print cmdout}'

#注：解读：通过grep在/etc/passwd中查找行中存在root数据的行，然后通过getline存储到cmdout中，最后打印cmdout；
```

###### 在awk中使用循环

```linux
#以下字符串，打印出其中的时间
#2024_04_02 20:20:08: mysqli connect failed, please check connect info
echo '2024_04_02 20:20:08: mysqli connect failed'|awk -F: '{ for(i=1;i<=3;i++) printf("%s:",$i)}'
out：2024_04_02 20:20:08:
#注：这种方式打印会在最后面多一个冒号:

#这种方式就没有最后面的冒号
echo '2024_04_02 20:20:08: mysqli connect failed'|awk -F: '{print $1 ":" $2 ":" $3; }'
out：2024_04_02 20:20:08

#时间部分和非时间部分分开打印
echo '2024_04_02 20:20:08: mysqli connect failed'|awk -F: '{print $1 ":" $2 ":" $3; print $4;}'
out:
2024_04_02 20:20:08
mysqli connect failed
```

```linux
#以逆序的形式打印行：（tac命令的实现）
#seq(sequence):用于生成数字序列的命令;seq 9：表示生成1-9的数字，会换行；
seq 9 | awk '{info[NR]=$0;num=NR} END {for(i=num;i>0;i--)print(info[i])}'
```

###### awk结合grep找到指定的服务，然后将其kill掉

```linux
ps -ef | grep msv8 | grep -v MFORWARD | awk '{print $2}' | xargs kill -9
```

###### awk实现head、tail命令

```linux
#head：输出前10行的数据
awk 'NR<=10{print}' error.log

#tail：输出后10行的数据(%求余的话得到的数据最后都是最后面的十条10N到10N+9)
awk '{info[NR%10]=$0;num=NR%10;} END{for(i=num+1;i<num+11;i++)print(info[i])}' error.log
```

###### 打印指定列

```linux
#awk方式实现，获取第6个字段的数据（下标1开始）
ls -lrt | awk '{print $6}'
```

```linux
#cut方式实现，获取第6个字段的数据（下标1开始）
ls -lrt | cut -f6
```

###### 打印指定文本区域

```linux
#确定行号，打印第2到第4行的数据，注意用的是逗号,表示的范围；如果用的是分号;则表示单个（可参考awk上面的案例）
seq 100 | awk 'NR==2,NR==4 {print}'
```

```linux
#确定文本，打印处在文本10和21之间的数据[10,21]
seq 100 | awk '/10/,/21/'

#注：会匹配每一组完整出现的[10,21]中的数据；
  例如：10 11 21 22 21：输出：10 11 21
  例如：10 11 21 22 10 34 56 21：输出：10 11 21 10 34 56 21
```

###### awk常用内建函数

```text
只能在awk中使用
```

> 1.index(string,search_string)：返回search_string在string中出现的位置;(下标从1开始)
> 2.sub(regex,replacement_str,string)：将正则表达式匹配的第一处内容替换为replacement_str;  
> 3.match(string,regex)：检查正则表达式是否能够匹配字符串  
> 4.length(string)：返回字符串长度  
> 5.substr(string,index,length)：截取字符串，string原字符串数据；index截取的下标；length：截取的长度；（下标从1开始）

```linux
#length：输出/etc/passwd中包含root行数据的长度
echo | awk '{"grep root /etc/passwd" | getline cmdout; print(length(cmdout))}'

#index
echo | awk '{print(index("str","string"));}'

#sub
echo string | awk '{sub(/s/,"luo",$0);print;}'

#match
echo | awk '{print match("string",/[a-z]+/)}'
true：返回第一个匹配的位置（下标从1开始）；匹配到了就是返回>0的
false：返回0
```

```linux
#printf类似C语言中的printf，对输出进行格式化
seq 10 | awk '{printf "->%4s\n",$1}'
out
->    1
->    2
...同理...
```

#### 3.12，迭代文件中的行、单词、字符

###### 1.迭代文件中的每一行

```linux
#while 循环法，读取每一行
cat data.txt | (while read line;do echo $line;done)
```

```linux
#awk方式
cat data.txt | awk '{print}'
```

###### 2.迭代一行中的每一个单词

```linux
#while、for循环的写法
cat data.txt | (while read line;do for word in $line;do echo -n $word;done;echo;done;)
#注：-n：表示echo不换行；读完一整行后再执行一个echo换行；

#awk方式
cat data.txt | awk '{for(i=1;i<=NF;i++)printf "->%s\t",$i;print("");}'
```

###### 3.迭代每一个字符

```linux
# ${string:start_pos:num_of_chars}：从字符串中提取一个字符；(bash文本切片）
# ${#word}:返回变量word的长度
# 使用常规的while和for方式，有问题；可以试试在其他linux系统上看看
cat data.txt | while read line; do
  for word in $line; do
    for ((i=0; i<${#word}; i++)); do
      echo ${word:$i:1}
    done
  done
done

#使用awk方式
awk '{for(i=1;i<=length($0);i++)printf substr($0,i,1);print("")}' data.txt  

#使用tr、fold方式（去除空格，打印每一个字符并换行）
cat data.txt | tr -d '[:space:]' | fold -w1 #(1：是数字1，不是L)

解析：
echo 命令用于输出文本内容。
tr -d '[:space:]' 用于删除所有空格字符。
fold -w1 用于将每行文本折叠成单个字符，每个字符占一行。（如果需要二个字符占一行可以使用-w2）
```

### 4，磁盘管理

#### 4.1，查看磁盘空间

```linux
#查看磁盘空间利用大小
df -h

#注：-h：human缩写，以易读的方式显示结果（即带单位：比如M/G，如果不加这个参数，显示的数字以B为单位）

$df -h
/opt/app/todeav/config#df -h
Filesystem            Size  Used Avail Use% Mounted on
/dev/mapper/VolGroup00-LogVol00
2.0G  711M  1.2G  38% /
/dev/mapper/vg1-lv2    20G  3.8G   15G  21% /opt/applog
/dev/mapper/vg1-lv1    20G   13G  5.6G  70% /opt/app
```

```linux
#查看当前目录所占空间大小
du -sh
out:1.0M

#注：-h：人性化显示
    -s：递归整个目录的大小
```

```linux
#查看当前目录下所有子文件夹和文件排序后的大小
#方式一：
du -sh `ls` | sort  #：ls用的是反引号``

#方式二：
for i in `ls`;do du -sh $i; done | sort   #注意done后面没有分号;
```

#### 4.2，打包/压缩

```text
在linux中打包和压缩是分两部来实现的

1.先打包   -   后压缩
2.先解压缩 -   后解包

```

###### 打包

```linux
#打包是将多个文件归到一个文件
tar -cvf etc.tar /etc     #仅打包，不压缩

# -c：打包选项
# -v：显示打包进度
# -f：使用档案文件
#注：有的系统中指定参数时不需要在前面加-，直接使用tar cvf
```

```linux
#示例：用tar实现文件夹同步，排除部分文件不同步
tar --exclude '*.svn' -cvf - /path/to/source | (cd /path/to/target; tar -xf -)
```

###### 压缩

```linux
#压缩；生成demo.txt.gz
gzip demo.txt
```

#### 4.3，解包/解压缩

###### 解包

```linux
#解包
tar -xvf demo.tar

-x：解包选项
```

```linux
#解压后缀为.tar.gz的文件：1.先解压缩 ——>xxx.tar；2.再解包 ——>xxxx.txt

#1.解压缩
gunzip demo.tar.gz

#2.解包
tar -xvf demo.tar
```

```linux
#bz2解压
tar jxvf demo.tar.bz2
#如果tar 不支持j，则同样需要分两步来解包解压缩，使用bzip2来解压，再使用tar解包:

bzip2 -d demo.tar.bz2
tar -xvf demo.tar

-d：decompose 解压缩
```

###### tar解压参数说明

> -z：解压gz文件 tar -zxvf demo.tar.gz -c target_folder  
> -j：解压bz2文件 tar -jxvf  
> -J：解压xz文件 tar -Jxvf

### 5.进程管理工具

#### 5.1，查询进程

```text
ps：ps 是Process Status（进程状态）的缩写，它是一个用于报告当前系统中运行的进程信息的命令。
```

```linux
#查询正在运行的进程信息
ps -ef

#查询归属于用户root的进程
ps -ef | grep root
ps -lu root         #作用：同上；-u：表示按用户显示进程信息
```

```text
pgrep：Process Grep（进程搜索）的缩写，它是一个用于根据进程名称或其他属性搜索进程的命令。
```

```linux
#查询进程ID（适合只记得部分进程字段）
#查询进程名中包含re的进程
pgrep -l re   #-l 选项表示只显示进程的名称，而不显示完整的命令行参数。
out：
2 kthreadd
28 ecryptfs-kthrea
29515 redis-server
```

```linux
#以完整的格式显示所有的进程
ps -ajx
```

```linux
#显示进程信息，并实时更新
top
```

```linux
#查看端口占用的进程状态
lsof -i:3306
```

```linux
#查看用户username的进程所打开的文件
lsof -u username
```

```linux
#查询init进程当前打开的文件
lsof -c init
```

```linux
#查询指定进程ID（例如：23295）打开的文件
lsof -p 23295
```

```linux
#查询指定目录下被进程开启的文件（使用+D递归目录）
lsof +d mydir1/
```

#### 5.2，终止进程

```linux
#杀死指定PID进程（PID为Process ID）
kill PID

#杀死相关进程
kill -9 3434
```

```linux
#杀死后台进程job工作（job为job number）
kill %job

#通过jobs来查看后台作业列表
[1]-  Running                 sleep 300 &
[1]就是job号

kill %1
#注意：在某些系统中，你可能需要使用 kill -9 %1 来强制终止进程。
```

#### 5.3，进程监控

```linux
#查看系统中使用cpu、使用内存最多的进程
top
(->)P

#输入top命令后，进入到交互界面；接着输入字符命令后显示相应的进程状态；
```

```text
对于进程，平时我们最常想知道的就是哪些进程占用cpu最多、占用内存最多。可以使用下面的参数
P：根据CPU使用百分比大小进行排序
M：根据驻留内存大小进行排序
i：使top不显示任何闲置或僵死的进程
```

#### 5.4，分析线程栈

```linux
#使用命令pmap，来输出进程内存的状况，可以用来分析线程堆栈
pmap PID

eg:
[/home/weber#]ps -fe| grep redis
weber    13508 13070  0 08:14 pts/0    00:00:00 grep --color=auto redis
weber    29515     1  0  2013 ?        02:55:59 ./redis-server redis.conf
[/home/weber#]pmap 29515
29515:   ./redis-server redis.conf
08048000    768K r-x--  /home/weber/soft/redis-2.6.16/src/redis-server
08108000      4K r----  /home/weber/soft/redis-2.6.16/src/redis-server
08109000     12K rw---  /home/weber/soft/redis-2.6.16/src/redis-server
```

#### 5.5，综合运用

```linux
#将用户angelo下的所有进程名称以av_开头的进程终止
ps -u angelo | awk '/av_/ {print "kill -9 " $1}' | sh
```

```linux
#将用户angelo下所有进程名中包含HOST的进程终止
ps -ef | grep angelo | grep HOST | awk '{print $2}' | xargs kill -9;
```

### 6.性能监控

```text
在使用操作系统的过程中，我们经常需要查看当前的性能如何，需要了解CPU、内存和硬盘的使用情况；
```

#### 6.1，监控CPU

```linux
#查看CPU使用率
sar -u

sar -u 1 2
Linux 2.6.35-22-generic-pae (MyVPS)     06/28/2014      _i686_  (1 CPU)

09:03:59 AM     CPU     %user     %nice   %system   %iowait    %steal     %idle
09:04:00 AM     all      0.00      0.00      0.50      0.00      0.00     99.50
09:04:01 AM     all      0.00      0.00      0.00      0.00      0.00    100.00
#后面1 2 两个参数表示监控的频率，比如：1：每秒采集一次；2：总共采集2次；

#查看CPU平均负载
sar -q 1 2
#sar指定-q后，就能查看运行队列中的进程数、系统上的进程大小、平均负载；
```

> 时间戳：报告的时间点。  
> %usr：用户进程占用的CPU百分比。  
> %nice：优先级调整为负值的用户进程占用的CPU百分比。  
> %sys：内核进程占用的CPU百分比。  
> %iowait：等待I/O操作完成的CPU百分比。  
> %steal：虚拟环境中被其他虚拟机占用的CPU百分比。  
> %idle：空闲CPU百分比。  
> %irq：处理硬件中断请求的CPU百分比。  
> %soft：处理软件中断和系统调用的CPU百分比。  
> %guest：运行在虚拟环境中的客户操作系统占用的CPU百分比。  
> %guest_nice：运行在虚拟环境中且优先级调整为负值的客户操作系统占用的CPU百分比。

#### 6.2，查询内存

```linux
#查看内存使用状况，sar指定-r之后，可以查看内存使用状况；
sar -r 1 2
09:08:48 AM kbmemfree kbmemused  %memused kbbuffers  kbcached  kbcommit   %commit  kbactive   kbinact
09:08:49 AM     17888    359784     95.26     37796     73272    507004     65.42    137400    150764
09:08:50 AM     17888    359784     95.26     37796     73272    507004     65.42    137400    150764
Average:        17888    359784     95.26     37796     73272    507004     65.42    137400    150764
```

> 时间戳：报告的时间点。  
> kbmemfree：可用的物理内存量（以KB为单位）。  
> kbmemused：已使用的物理内存量（以KB为单位）。  
> %memused：已使用的物理内存占总物理内存的百分比。  
> kbbuffers：缓冲区使用的内存量（以KB为单位）。  
> kbcached：缓存使用的内存量（以KB为单位）。  
> kbcommit：当前已分配但尚未使用的内存量（以KB为单位）。  
> kbactive：活跃内存量（以KB为单位），即最近被访问过的内存。  
> kbinact：非活跃内存量（以KB为单位），即长时间未被访问过的内存。  
> kbdirty：脏页内存量（以KB为单位），即等待写入磁盘的内存。

```linux
#查看内存使用量
free -m
```

#### 6.3，查询页面交换

```text
查看页面交换发生状况，页面发生交换使，服务器的吞吐量会大幅下降；服务器状况不良时，如果怀疑因为内存不足而导致了页面交换的发生，可以使用sar -W这个命令来确认是否发生了大量的交换；
```

```linux
sar -W 1 3  #查询交换页面的数据，每一秒查询一次，一共查询3次；
```

> 时间戳：报告的时间点。  
> pswpin/s：每秒从磁盘交换到内存的页面数。  
> pswpout/s：每秒从内存交换到磁盘的页面数。  
> pswch/s：每秒发生的交换次数。  
> free：可用的交换空间量（以KB为单位）。  
> buff：用作缓冲区的交换空间量（以KB为单位）。  
> cache：用作缓存的交换空间量（以KB为单位）。  
> inact：长时间未使用的交换空间量（以KB为单位）。  
> active：活跃的交换空间量（以KB为单位）。  
> si：每秒从磁盘读取到内存的块数。

#### 6.4，查询硬盘使用

```linux
#查看磁盘空间利用情况
df -h
```

```linux
#查询当前目录下空间的使用情况
du -sh    #-h：是人性化显示（大小默认显示Byte，加h会显示M，G等）；-s：递归整个目录的大小；
```

```linux
#查询该目录下所有文件夹的排序后的大小
du -sh `ls`
或
for i in  `ls`; do du -sh $i; done | sort
```

#### 6.5，综合应用

```text
当系统中sar不可用时，可以使用一下工具代替：
Linux中的vmstat
Unix中的prstat
```

```linux
#查看CPU、内存、使用情况：vmstat n m（n监控频率；m监控次数）
vmstat 1 3    #每一秒监控一次；总共监控3次；
```

```text
使用watch工具监控变化 当需要持续的监控应用的某个数据变化时，watch工具可以满足要求；执行watch命令后会进入一个界面，输出当前被监控的数据，一旦数据变化，便会高亮显示变化情况；
```

```linux
#操作redis时，监控内存变化
watch -d -n 1 './redis-cli info | grep memory'
(以下为watch工具中的界面内容，一旦内存变化即实时高亮显示变化)
Every 1.0s: ./redis-cli info | grep memory

used_memory:45157376
used_memory_human:43.07M
used_memory_rss:47628288
used_memory_peak:49686080
used_memory_peak_human:47.38Ms
```

### 网络工具

#### 7.1，查询网络服务和端口

```text
netstat命令用于显示各种网络相关信息，如网络连接、路由表、接口状态(interface statistic)、masquerade连接、多播成员(Multicast memberships)等等；
```

```linux
#列出所有端口（包括监听和未监听得）
netstat -a
```

```linux
#列出所有TCP端口
netstat -ta   #-t：TCP
```

```linux
#列出所有UDP端口
netstat -ua   #-u：UDP
```

```linux
#列出所有有监听的服务状态
netstat -l
```

```linux
#使用netstat工具查询端口
netstat -antp | grep 379
out:
tcp        0      0 127.0.0.1:6379          0.0.0.0:*

ps 25501      #查看特定进程状态
 PID TTY      STAT   TIME COMMAND
25501 ?        Ssl   28:21 ./redis-server ./redis.conf
```

```text
lsof（list open files）是一个列出当前系统打开文件的工具。在Linux环境下，任何事物都是以文件的形式存在，通过文件不仅仅可以访问常规数据，还可以访问网络连接和硬件。
所以如传输控制协议TCP和用户数据包协议UDP套接字等；在查询网络端口时，经常会用到这个工具。
```

```linux
#查询7902端口现在运行什么程序

#分两步：
#第一步：通过lsof查询使用该端口的进程PID
lsof -i:7902
out:
COMMAND   PID   USER   FD   TYPE    DEVICE SIZE NODE NAME
WSL     30294 tuapp    4u  IPv4 447684086       TCP 10.6.50.37:tnos-dp (LISTEN)

#第二部：使用ps工具查询进程详情
ps -ef | grep 30294
out:
tdev5  30294 26160  0 Sep10 ?        01:10:50 tdesl -k 43476
root     22781 22698  0 00:54 pts/20   00:00:00 grep 11554
```

#### 7.2，网络路由

```linux
#查看路由状态
route -n
```

```linux
#发送ping包到地址IP
ping IP
```

```linux
#探测前往地址IP的路由路径
traceroute IP
```

```linux
#DNS查询，寻找域名domain对应的IP
host domain
```

```linux
#反向DNS查询
host IP
```

#### 7.3，镜像下载

```linux
#直接下载文本或页面
wget url

#示例
wget https://github.com/rgl/redis/downloads
```

###### 常用选项

* -limit-rate：下载限速
* -o：指定日志文件；输出都写入日志
* -c：断点续传

#### 7.4，ftp、sftp、lftp、ssh

> ftp：File Transfer Protocol（文件传输协议），是一种用于在网络上进行文件传输的应用层协议。它使用客户端-服务器模型，允许用户从远程主机下载或上传文件。
>
> sftp：SSH File Transfer Protocol（安全外壳文件传输协议），是在SSH协议上实现的文件传输协议。它提供了安全的数据传输，通过加密保护数据的安全性。
>
> lftp：是一个功能强大的命令行FTP客户端，支持多种协议，包括FTP、HTTP、SFTP等。它具有许多高级功能，如断点续传、递归下载等。
>
> ssh：Secure Shell（安全外壳），是一种加密的网络传输协议，用于在不安全的网络上安全地访问远程计算机。它可以用于远程登录、执行命令、传输文件等。

```linux
#SSH登录
ssh ID@host     #ssh登录远程服务器host：远程主机的IP或者域名；ID：远程主机上的用户名

#假设你要以用户名 user123 登录到 IP 地址为 192.168.1.100 的远程主机上，你可以使用以下命令：
ssh user123@192.168.1.100

#如果你要登录到域名为 example.com 的远程主机上，并且该主机上的用户名也是 user123，你可以使用以下命令：
ssh user123@example.com
```

```linux
#ftp/sftp文件传输
sftp ID@host        #ID 是你要登录的远程主机上的用户名；host 是远程主机的 IP 地址或域名。

#假设你要以用户名 user123 登录到 IP 地址为 192.168.1.100 的远程主机上，并使用 SFTP 进行文件传输，你可以使用以下命令：
sftp user123@192.168.1.100


#如果你要登录到域名为 example.com 的远程主机上，并且该主机上的用户名也是 user123，你可以使用以下命令：
sftp user123@example.com
```

* sftp登录后，可以使用下面的命令进一步操作
    * get filename：下载文件
    * put filename：上传文件
    * ls：列出host上当前路径的所有文件
    * cd：在host上更改当前路径
    * lls：列出本地主机上当前路径的所有文件
    * lcd：在本地主机上更改当前路径

```linux
#lftp同步文件夹（类似于rsync）
lftp -u user:pass host        #user：远程主机上的用户名；pass：远程主机上对应的密码；host：远程主机的IP或域名

#假设你要以用户名 myusername 和密码 mypassword 登录到 IP 地址为 192.168.1.100 的远程主机上，并使用 FTP 进行文件传输，你可以使用以下命令：
lftp -u myusername:mypassword 192.168.1.100

#如果你要登录到域名为 example.com 的远程主机上，并且该主机上的用户名也是 myusername，密码也是 mypassword，你可以使用以下命令：
lftp -u myusername:mypassword example.com

#在成功登录后，你可以使用 mirror -n 命令来同步本地目录和远程目录。
#例如，如果你想将本地目录 /path/to/local/directory 同步到远程主机的 ~/remote_directory 目录下，你可以在 lftp 命令行中输入以下命令：
mirror -n /path/to/local/directory ~/remote_directory     #~：表示当前用户home目录下 ~/remote_directory 相当于 /home/用户名/remote_directory

lftp user@host:~> mirror -n
```

#### 7.5，网络复制

```linux
#将本地localpath指向的文件上传到远程主机的path路径上
scp localpath ID@host:path    #ID：远程主机用户名；host：远程主机IP或域名

#假设你有一个名为 example.txt 的文件，你想将其复制到远程计算机（IP 地址为 192.168.1.100）上的用户 user 的主目录下。你可以使用以下命令：
scp example.txt user@192.168.1.100:~/     #~/：表示当前用户的主目录下（/home/making/）

```

```linux
#以ssh协议，遍历下载path路径下的整个文件系统，到本地的localpath文件夹下
scp -r ID@site:path localpath       #ID：远程主机的用户名；site：远程主机主机名或IP

#假设你想将远程计算机（IP 地址为 192.168.1.100）上的用户 user 的主目录下的 example_folder 文件夹复制到本地计算机的当前工作目录下，你可以使用以下命令：
scp -r user@192.168.1.100:~/example_folder .      #~/：同理，当前远程用户的主目录下（例如：/home/making/）；.：本地主机当前目录下
```

### 8.用户管理工具

#### 8.1，用户

###### 添加用户

```linux
#创建用户名为username
useradd -m username
```