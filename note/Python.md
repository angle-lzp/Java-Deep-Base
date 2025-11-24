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
