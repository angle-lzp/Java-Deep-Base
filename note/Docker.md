## Docker

### 1.基础指令

* 安装

```shell

#需要先卸载旧版本Docker（如果有的话）

#yum安装gcc相关
yum -y install gcc
yum -y install gcc-c++
#安装需要的软件包
yum install -y yum-utils
#添加yum仓库地址（用于加速下载对应的镜像进行安装操作）用于加速Docker CE(Community Edition)的安装过程（和后面拉取Docker镜像没有关系主要用于更新、安装Docker）
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
#更新yum软件包索引
yum makecache fast
#安装Docker CE
yum -y install docker-ce docker-ce-cli containerd.io
#启动
systemctl start docker
#测试
docker version
```

* 查看Linux系统版本

```shell
cat /etc/os-release
```

* 查看拉取镜像使用的时间

```shell
time docker pull node:latest
```

* 搜索镜像

```shell
docker search node:latest
# --limit 20 只展示前20个， 默认25
```

* 拉取镜像

```shell
docker pull node:latest
```

* 查看拉取镜像使用的时间

```shell
time docker pull node:latest
```

* 查看镜像、容器、数据卷占用的空间

```shell
docker system df
```

```shell
time docker pull node:latest
```

* 查看镜像

```shell
docker images
# -a：展示所有镜像；-q：只展示镜像ID
```

* 删除镜像

```shell
docker rmi -f 镜像ID
```

* 删除多个镜像

```shell
docker rmi -f 镜像名称:tag 镜像名称:tag
```

* 删除所有镜像

```shell
docker rmi -f ${docker images -aq}
```

* 停止容器

```shell
docker stop 容器ID或者容器名字
```

* 启动已经停止的容器

```shell
docker start 容器ID或者容器名字
```

* 重启容器

```shell
docker restart 容器ID或者容器名字
```

* 强制停止容器

```shell
docker kill 容器ID或者容器名字
```

* 一次性删除多个容器(这表示删除所有的容器)

```shell
docker rm -f ${docker ps -a -q}   #或（docker ps -a -q | xargs docker rm）
```

* 一般我们是希望容器在后台运行的

```shell
docker run -d 镜像名称[:tag]
```

* 查看容器日志

```shell
docker logs 容器ID
```

* 查看容器内的进行

```shell
docker top 容器ID
```

* 查看容器内部细节

```shell
docker inspect 容器ID
```

* 以后台的方式进入ubuntu

```shell
docker run -d unbuntu
```

* 查看启动的容器

```shell
docker ps
docker ps -a    #(查看所有的容器包括已经停止的容器)
```

* 以前台交互方式进入ubuntu

```shell
docker run -it ubuntu /bin/bash
```

* 退出容器，但不停止容器

```shell
CTRL+p+q
```

* 重新进入容器

```shell
docker exec -it 容器ID /bin/bash #（推荐）
```

* 重新进入容器的两种方式：

```text
方式一：docker exec -it 容器ID /bin/bash 会在容器中启动一个新的进程，并且可以启动新的进程，用exit退出，容器不会停止。
方式二：docker attach 容器ID 直接进入容器启动的命令终端，不会启动新进行，用exit退出，容器会停止。
```

* 从容器内拷贝文件到主机

```shell
#模板
docker cp 容器ID:容器内的完整路径 目的主机路径 

#例子
docker cp 容器ID或容器名称:/src/local/bin/a.txt /home/lucy
```

* 导出容器

```shell
docker export 容器ID>文件名.tar
```

* 导入容器

```shell
cat 文件名.tar | docker import-镜像用户/镜像名:Tag  #Tag:镜像版本号

#示例（此时导入后的是一个镜像，不是一个容器）
cat file.tar | docker import - my_user/my_image:latest
```

* 将一台服务器上的镜像上传到另外的服务器上

```shell
#1.在A服务器打包镜像(-o：用于指定输出文件的路径和名称（out）)
docker save -o imageFile.tar 镜像名称:Tag

#2.通过上传工具将打包的镜像文件上传到B服务器

#3.在B服务器上加载镜像(如果不在imageFile.tar的当前目录下执行该指令需要使用完整路径)
# (-i:用于指定输入文件的路径和名称（in）)
docker load -i imageFile.tar

#4.使用镜像
docker run -d -p 8099:8099 imageFile --name myImageFile

```

* 安装vim

```shell
#更新本地软件包索引
apt-get update

#下载安装vim指令
apt-get -y install vim
```

* 提交我们自己的镜像，使用commit

```shell
docker commit -m="add vim" -a="angelo.luo" 容器ID 要创建的目标镜像ID:[Tag]
```

* docker compose指令

```shell
# Compose常用命令
docker-compose -h                           # 查看帮助
docker-compose up                           # 启动所有docker-compose服务
docker-compose up -d                        # 启动所有docker-compose服务并后台运行
docker-compose down                         # 停止并删除容器、网络、卷、镜像。
docker-compose exec  yml里面的服务id(services的名称)         # 进入容器实例内部  docker-compose exec docker-compose.yml文件中写的服务id /bin/bash
docker-compose ps                           # 展示当前docker-compose编排过的运行的所有容器
docker-compose top                          # 展示当前docker-compose编排过的容器进程
docker-compose logs  yml里面的服务id         # 查看容器输出日志
docker-compose config                       # 检查配置
docker-compose config -q                    # 检查配置，有问题才有输出
docker-compose restart                      # 重启服务
docker-compose start                        # 启动服务
docker-compose stop                         # 停止服务
```

* 查看docker-compose(docker compose、podman-compose(如果是这种方式直接替换就可以))中的信息

```shell
#启动docker compose
docker-compose up -d

#停止docker compose
docker-compose down

#查看指定镜像的log信息（-f：实时读取；容器ID：docker-compose.yml中services下面的镜像别名）
docker-compose logs -f 容器ID

```

* 查看volume（容器卷）列表

```shell
docker volume ls

```

* 查看指定volume（容器卷）信息

```shell
docker volume inspect volumeName
```

* 删除指定volume（容器卷）（当你手动删除容器卷目录的话，那么volume也要被删除）

```shell
docker volume rm volumeName
```

* 构建自己的镜像私服

```shell
#1，下载镜像Docker Registry
dockers pull registry

#2，运行私有库Registry，相当于我们本地有一个Docker Hub
docker run -d -v -p 5000:5000 /home/making/myregistr:/tmp/registry --privileged=true registry

#启动了私有镜像服务可以查看私服库里面有什么镜像
curl -XGET http://IP:5000/v2/_catelog

#3，提交我们自己修改好的镜像
docker commit -m="ubuntu add ifconfig" -a="angelo.luo" 容器ID 镜像名称:[Tag]

#4，将新的镜像修改成符合私服规范的Tag
docker tag 镜像名称:Tag IP:Port/私服库里面该镜像的名称:Tag

#例子：
docker tag newUbuntu:1.3.4 127.0.0.1:5000/newUbuntu:1.3.4

#5，修改docker配置文件，使它支持http
vim /etc/docker/daemon.json

#不起效果重启docker
systemctl daemon-reload
systemctl restart docker

#在aliyun的配置下一行添加："insecure-registries": [你的ip:5000"]；例如："insecure-registries": ["127.0.0.1:5000"]
#注：docker默认不允许使用http推送镜像，通过配置选项来取消这个限制。（如果修改完后不生效，建议重启docker）

#6，将新的镜像推送到私服
docker push IP:Port/镜像名称:Tag #（IP:Port/镜像名称:Tag == >就是上面通过Tag修改后名称）

#例子
docker push 127.0.0.1:5000/newUbuntu:1.3.4

#7，再次查询是否在私服中存在新的镜像
curl -XGET http://IP:5000/v2/_catalog

#例如
curl -XGET http://127.0.0.1:5000/v2/_catalog

#8，将新的镜像拉取到本地
docker pull 127.0.0.1:5000/newUbuntu:1.3.4
```

* 在容器挂载的时候，如果需要指定容器内的只能进行读取数据（容器内只能读取挂载中的数据，不能写入数据到挂载中） （ro:read only）

```shell
#例子
docker run -d -p 8898:8898 --privileged=true -v /home/making/data:/etc/data:ro ubuntu
```

* 容器卷的继承

```shell
#1，首先创建一个父类容器卷挂载，以ubuntu为例
docker run -d -p --privileged=true 8989:8989 -v /home/making/data:/etc/data --name u1 ubuntu

#2，再创建一个子类容器卷继承父类容器卷
docker run -d -p --privileged=true 8899:8899 --volumes-from u1 --name u2 ubuntu

#我们之前创建了u1 ubuntu，相当于用来备份的硬盘A，现在我们又创建了u2 ubuntu硬盘B，继承自硬盘A，哪怕A挂了，B照样能同步数据，等硬盘A复活，A照样有挂掉这期间的数据，相当于一主二从。
```

### 2.实战操作

* 一：安装tomcat

```shell
#1，搜索tomcat
docker search tomcat

#2，拉取tomcat
docker pull tomcat

#3，查看是否拉取
docker images

#4，启动tomcat实例
docker run -it -p 8080:8080 tomcat /bin/bash

#5，查看是否启动容器
docker ps

#6，http://localhost:8080 访问发现访问不了

#7，进入tomcat容器
docker exec -it 容器ID /bin/bash

#8，进入到tomcat目录
cd /use/local/tomcat

#9，进入webapps，发现是空的，因为新版的tomcat默认把里面的数据清空了，删除这个空的webapps
cd webapps rm -rf webapps

#10，将webapps.dist更名为webapps，里面有以前的文件
mv webapps.dist webapps

#11，然后访问http://localhost:8080就可以了
```

```shell
#可以使用老版的tomcat就不需要这样更改
docker pull billygoo/tomcat8-jdk8
docker run -d -p 8080:8080 --name tomcat8 billygoo/tomcat8-jdk8
```

* 二：安装MySQL
* 简易版本

```shell
#简易版本
#1，查询mysql镜像
docker search mysql

#2，拉取镜像
docker pull mysql:5.7

#3，查询是否已经拉取
docker images

#4，创建容器(这里需要设置MySQL的密码，以后登入也是这个密码)
docker run -d -p 3306:3306 --name mysql5.7 -e MYSQL_ROOT_PASSWORD=123456 mysql:5.7

#5，查看容器是否创建成功
docker ps

#6，进入容器
docker exec -it 容器ID /bin/bash

#7，登录MySQL
mysql -uroot -p123456
```

* 实战版本

```shell
#实战版本
#1，创建mysql容器
mysql run -d -p 3306:3306 --privileged=true \
  -v /home/making/mysql/log:/var/log/mysql \
  -v /home/making/mysql/data:/var/lib/mysql \
  -v /home/making/mysql/conf:/etc/mysql/conf.d \
  -e MYSQL_ROOT_PASSWORD=123456 --name mysql5.7 mysql:5.7

#2，给mysql设置配置文件，在挂载容器卷中进行创建配置文件
cd /home/making/MySQL/conf
vim my.conf
#输入如下数据：
[client]
default_character_set=utf8
[mysqld]
collation_server = utf8_general_ci
character_set_server = utf8

#3，查看添加的数据：cat my.conf

#4，重启mysql容器实例查看字符编码情况
docker restar 容器ID

#5，进入mysql容器中
docker exec -it 容器ID /bin/bash
mysql -uroot -p123456
sql >show variables like 'character%'

#6，通过建库建表insert数据utf-8可以了，但是以前添加的数据是不可以的，后面添加的可以

#7，修改字符集操作+重启mysql容器实例。

#8，结论：docker安装完MySQL并run出容器后，建议请先修改完字符集编码后再新建mysql库-表-插数据。

```

* 安装Redis
* 简易版本

```shell
#简易版本
#1，拉取镜像
docker pull redis:6.0.8

#2，查看镜像
docker images

#3，启动容器
docker run -d -p 3306:3306 redis:6.0.8

#4，查看容器是否启动
docker ps

#5，进入容器
docker exec -it 容器ID /bin/bash

#6，进入redis客户端
>redis-cli
```

* 实战版本

```shell
#实战版本
#1，在主机上创建redis配置文件redis.conf
mkdir -p /app/redis

#将配置文件模板复制到我们新建的文件夹中
cp /home/making/redis.conf /app/redis

#修改配置文件配置
vim redis.conf

#1），开启redis验证，是否需要密码登入，可选
requirepass 123

#2），允许redis外部连接，必须注释掉：
bind 127.0.0.1    #bind 127.0.0.1

#3），daemonize no；将daemonize yes注释或者将daemonize设置为no，因为该配置为yes或和docker run中的-d参数冲突，导致容器无法启动
daemonize no

#4），开启redis持久化，可选
appendonly yes

#2，创建redis容器
docker run -d -p 6379:6379 --name redis6.0.8 --privileged=true -v /app/redis/redis.conf:/etc/redis/redis.conf -v /app/redis/data:/data
redis:6.0.8 redis-server /etc/redis/redis.conf

#3，查看容器是否创建
docker ps

#4，进入redis容器中
docker exec -it 容器ID /bin/bash

#5，开启redis客户端
> redis-cli
```

#### 3.实战常见知识

* docker-compose.yml中volumes的默认位置

```shell
#docker-compose.yml
version: "3"

services:
  db:
    image: mariadb
    command: --max-allowed-packet=64MB
    restart: always
    volumes: - db:/var/lib/mysql
    environment:
      - MYSQL_ROOT_PASSWORD=Pas3W0rd
    env_file:
      - ./db.env
      
      
#db:/var/lib/mysql中db的默认位置一般在/var/lib/docker/volumes/db
#如果不在可以使用[查看volume（容器卷）列表][查看指定volume（容器卷）信息]查看
```

* volume的名称一般是由当前docker-compose.yml文件所在目录的相对路径加上volume的名称组成
```shell
#当前文件夹matomo，volume名称db，那么容器卷名称为matomo_db

```