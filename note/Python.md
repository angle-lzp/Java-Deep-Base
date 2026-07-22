## Python基础指令

```shell
# 1.显示Python版本
python -V

# 2.查看所有依赖和依赖版本
pip list

# 3.查看特定依赖版本
pip show <package_name>
pip show 

# 4.查看包的详细信息
pip show --verbose <package_name>
pip show --verbose fastapi

```

### 1.Python创建虚拟环境
```shell
# 进入代码目录
cd /home/angelo/apps/

# 创建虚拟环境
python3 -m venv ultralytics_env

# 激活虚拟环境
source ultralytics_env/bin/activate

# 每次运行前激活虚拟环境
source /home/angelo/apps/ultralytics_env/bin/activate

# 运行您的程序
cd /home/angelo/apps/dock_occupancy/dock_occupancy_vllm_yolo
python dock_occupancy.py
```

### 2.步骤 1：安装 venv 依赖（必须加 sudo）
```shell
sudo apt update
sudo apt install -y python3.12-venv python3-pip

步骤 2：重新创建虚拟环境
python3 -m venv venv

步骤 3：激活虚拟环境
source venv/bin/activate
激活成功后终端前缀会出现 (venv) 标识。

步骤 4：安装缺失的 paho-mqtt 库
pip3 install paho-mqtt
```

## Python开发实战

### 1.无网络情况下下载pip

#### 1.1下载 pip 的 wheel 包
```shell
pip download pip
```

#### 1.2将下载的文件传输到 Linux 服务器

##### 1.3在 Linux 服务器上安装 pip：
```shell
# 安装 pip
python3 -m pip install --no-index ./pip-*.whl

# 或者使用以下命令安装
python3 pip-*.whl/pip install --no-index ./pip-*.whl
```

### 2.无网络情况下下载&安装oracledb(other depend)
```shell
# 下载
pip download oracledb influxdb-client paho-mqtt

# 安装
pip install --no-index --find-links . oracledb influxdb-client paho-mqtt
```
