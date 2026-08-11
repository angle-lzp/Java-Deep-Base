<!--
 * @Author: Angelo
 * @Date: 2025-09-10 16:22:29
 * @version: 
 * @Descripttion: 
-->
## 1.Python基础指令

### 1.查看Python版本
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
### 2.记录当前依赖版本
```shell
pip3 freeze > requirements_backup.txt
# 同理
pip freeze > requirements_backup.txt
```

## 2.Python实操

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

# 步骤 2：重新创建虚拟环境
python3 -m venv venv

# 步骤 3：激活虚拟环境
source venv/bin/activate
# 激活成功后终端前缀会出现 (venv) 标识。

# 步骤 4：安装缺失的 paho-mqtt 库
pip3 install paho-mqtt

# 退出虚拟环境
deactivate
```

### 2.AGX Orin安装ultralytics相关依赖运行Yolo，同时使用设备GPU

#### 1. 创建虚拟环境，包冲突 
```shell
# 创建虚拟环境（会使用系统包）
python3 -m venv --system-site-packages /opt/venvs/python_env

# 启动虚拟环境
source /opt/venvs/python_env/bin/activate

# 虚拟环境卸载的包（opencv-python-headless和opencv-contrib-python不存在）
python -m pip uninstall -y \
  opencv-python \
  opencv-python-headless \
  opencv-contrib-python

# 包降级 NumPy 2.5.1 -> 1.26.4（因为当前虚拟环境的numpy和主机的Matplotlib会继续版本冲突）
python -m pip install \
  --force-reinstall \
  "numpy==1.26.4"
```

#### 2.离线安装torch
```shell
# 保留当前环境快照
python -m pip freeze > /tmp/python_env_before_torch_change.txt

# 应该可以在虚拟环境中回滚上一个版本

# 手动安装包（适配Jetpack7.2）
# 下载地址：https://github.com/Shattered217/Jetson-Orin-Wheels
torch-2.12.0-cp312-cp312-linux_aarch64.whl 
torchvision-0.27.0+78839c2-cp312-cp312-linux_aarch64.whl

# 执行指令
python -m pip install \
  /opt/packages/torch-2.12.0-cp312-cp312-linux_aarch64.whl


python -m pip install \
  "/opt/packages/torchvision-0.27.0+78839c2-cp312-cp312-linux_aarch64.whl"


# 验证是否可以使用GPU，是否适配当前AGX Orin Jetpack7.2 sm_8.7
python - <<'PY'
import torch
import torch.nn as nn

assert torch.cuda.is_available(), "CUDA unavailable"

print("GPU:", torch.cuda.get_device_name(0))
print("Capability:", torch.cuda.get_device_capability(0))
print("Architectures:", torch.cuda.get_arch_list())

model = nn.Sequential(
    nn.Conv2d(3, 32, kernel_size=3, padding=1),
    nn.ReLU(),
    nn.MaxPool2d(2),
    nn.Conv2d(32, 64, kernel_size=3, padding=1),
    nn.ReLU(),
).cuda().eval()

x = torch.randn(1, 3, 640, 640, device="cuda")

with torch.inference_mode():
    y = model(x)

torch.cuda.synchronize()

print("Convolution test OK")
print("Output shape:", y.shape)
print("Output device:", y.device)
PY


# output is ok
Convolution test OK
Output device: cuda:0

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
