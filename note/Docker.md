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
time docker pull node:latest  #查看拉取镜像使用的时间
```

* 查看镜像、容器、数据卷占用的空间

```shell
docker system df  #查看镜像、容器、数据卷占用的空间
```

```shell
time docker pull node:latest  #查看拉取镜像使用的时间
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

* 查看容器的资源使用情况
```shell
docker stats iot-fastapi-auth(容器名称或容器ID)
```

* 以后台的方式进入ubuntudocker inspect <container_name_or_id> | jq '.[0].NetworkSettings.IPAddress'

```shell
docker run -d unbuntu
```

* 查看启动的容器

```shell
docker ps
docker ps -a    #(查看所有的容器包括已经停止的容器)
```

* 查看容器的IP
```shell
# 信息简单
docker inspect <container_name_or_id> | grep IPAddress

# 使用 docker inspect 和 jq（详细）查看特定网络的 IP 地址
docker inspect <container_name_or_id> | jq '.[0].NetworkSettings.Networks'

# 在容器内部查看IP
hostname -I
# 或者
ip addr show
```

* 启动容器的options

```text

--name="容器新名字"   为容器指定一个名称

-d：后台运行容器并返回容器ID，也即启动守护式容器（后台运行）

-i：以交互模式运行容器，通常与 -t 同时使用；
-t：为容器重新分配一个伪输入终端，通常与 -i 同时使用；
也即启动交互式容器(前台有伪终端，等待交互)；
 
-P：随机端口映射，大写P
-p：指定端口映射，小写p

```

* docker run 的三种常见启动方式

```shell
# 1. 前台运行容器
# 容器启动后直接占用当前终端，日志会直接输出到终端。
# 只要容器的主进程还在运行，容器就不会退出；按 Ctrl+C 通常会停止容器。
docker run --name my-nginx -p 8080:80 nginx

# 2. 后台运行容器
# -d 表示 detached mode，容器在后台运行，命令执行后返回容器ID。
# 适合 nginx、mysql、redis 这类长期运行的服务。
docker run -d --name my-nginx -p 8080:80 nginx

# 3. 进入可交互界面运行容器
# -i 保持标准输入打开，-t 分配一个伪终端。
# 通常用于进入 ubuntu、centos 这类系统镜像的 shell 环境。
docker run -it --name my-ubuntu ubuntu /bin/bash

# 4. 一次性运行容器，退出后自动删除
# --rm 表示容器停止后自动删除容器记录，适合临时测试命令。
docker run --rm ubuntu cat /etc/os-release

# 5. 后台运行，并设置容器自动重启
# --restart=always 表示 Docker 启动后自动拉起该容器，容器异常退出也会重启。
docker run -d --restart=always --name my-nginx -p 8080:80 nginx

# 6. 启动时传入环境变量
# -e 用于设置容器内的环境变量，常用于 MySQL、Redis、应用服务配置。
docker run -d --name mysql5.7 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql:5.7

# 7. 启动时挂载目录或文件
# -v 主机路径:容器路径，将主机目录挂载到容器内，常用于保存数据和配置文件。
docker run -d --name my-nginx -p 8080:80 -v /home/making/html:/usr/share/nginx/html nginx

# 8. 指定容器网络
# --network 用于指定容器加入哪个 Docker 网络，多个容器之间可以通过容器名通信。
docker run -d --name my-nginx --network my-net nginx

# 9. 使用宿主机网络模式
# --network host 表示容器直接使用宿主机网络，不再使用 -p 做端口映射。Linux 上常用。
docker run -d --name my-nginx --network host nginx

# 10. 指定容器启动后执行的命令
# 镜像名后面的内容会覆盖镜像默认启动命令。
docker run --rm ubuntu echo "hello docker"
```

```text
前台运行：适合临时查看容器启动日志、调试服务启动过程。当前终端会被容器占用。
后台运行：适合正式启动长期服务。容器在后台运行，可以用 docker logs、docker exec 查看或进入。
交互式运行：适合进入容器内部执行命令。退出 shell 时，如果 shell 是容器主进程，容器通常也会停止。
一次性运行：适合临时执行命令或测试镜像，配合 --rm 可以避免产生很多已停止的容器。
自动重启：适合希望服务随 Docker 自动启动，或者异常退出后自动恢复的场景。
环境变量：适合在启动时传入密码、端口、运行环境等配置。
挂载目录：适合保存数据库数据、服务配置、静态文件，避免容器删除后数据丢失。
指定网络：适合多个容器组成一套服务，例如 Web 服务连接 MySQL、Redis。
宿主机网络：适合需要直接使用宿主机端口和网络的场景，但容器网络隔离会变弱。
指定命令：适合临时覆盖镜像默认启动命令，执行完命令后容器通常就会退出。
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

* 使用 docker exec -it 在宿主机执行容器内命令

```shell
# 基本格式
docker exec -it 容器ID或容器名称 容器内命令 参数

# 进入容器的 bash 交互界面
docker exec -it my-ubuntu /bin/bash

# 如果容器内没有 bash，可以使用 sh
docker exec -it my-ubuntu /bin/sh

# 不进入容器，直接在宿主机执行容器内命令
docker exec -it my-ubuntu ls /app

# 查看容器内系统版本
docker exec -it my-ubuntu cat /etc/os-release

# 查看容器内当前用户
docker exec -it my-ubuntu whoami

# 进入 Redis 客户端
docker exec -it redis6.0.8 redis-cli

# 进入 MySQL 客户端
docker exec -it mysql5.7 mysql -uroot -p123456
```

```text
进入交互界面：docker exec -it 容器ID /bin/bash，适合进入容器后连续执行多条命令。
直接执行命令：docker exec -it 容器ID 命令 参数，适合只执行一条命令，不需要进入容器 shell。
区别：两者都是在已经运行的容器中新建进程，不会像 docker attach 一样附着到容器主进程。
注意：如果只是执行普通命令，不需要交互输入，可以省略 -it，例如 docker exec my-ubuntu ls /app。
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
# Compose常用命令(docker-compose -> docker compose)
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

* 查看Docker的数据目录
```shell
docker info | grep "Docker Root Dir"
```

* 新增用户给用户设置管理员权限
```shell
sudo adduser williamphan

# 确认是否有管理员权限
groups williamphan

# 设置管理员权限
sudo usermod -aG sudo williamphan

# 移除管理员权限
sudo deluser williamphan sudo
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

##### docker-compose.yml中volumes的默认位置

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

##### volume的名称一般是由当前docker-compose.yml文件所在目录的相对路径加上volume的名称组成

```shell
#当前文件夹matomo，volume名称db，那么容器卷名称为matomo_db

```

##### Docker默认容器存储的路径

```shell
/var/lib/docker/containers/<容器ID>/<容器ID>-json.log
```

##### 获取容器内

```shell
/var/lib/docker/containers/<容器ID>/<容器ID>-json.log
```

##### 获取容器内用户UID

```shell
docker exec 容器ID id -u 用户名
#例如
docker exec telegraf id -u telegraf
            容器名称        容器内用户名称
```

##### 检查容器启动后的状态

```shell
docker inspect -f '{{.State.Health.Status}}' emqx 
```

##### 查看容器内监控状态的指令市什么
```shell
docker inspect --format='{{json .Config.Healthcheck}}' emqx # 查看你emqx的健康检查的指令

```

##### 查看资源监控

```shell
docker stats emqx --no-stream # 查看单个服务emqx
docker stats --no-stream      #  查看所有服务

```