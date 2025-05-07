## Linux实战指令

### 0.开发中常用指令

```shell
# 1.查询指定名称的RPM包(q：查询，a：所有的，-i：显示包的信息（如版本、安装时间、描述等），l：列出包安装的所有文件路径)
rpm -qa | grep -i python

# 2.显示系统信息
uname -a

# 3.显示系统版本信息
cat /etc/os-release

# 4.显示系统版本信息
cat /etc/redhat-release

# 5.查看处理器(cup)信息
lscpu

# 6.查看物理磁盘大小信息（SIZE 列显示磁盘或分区的总大小；TYPE 列标识设备类型（disk 表示物理磁盘，part 表示分区））
lsblk -o NAME,SIZE,TYPE,MOUNTPOINT

# 7.直接显示磁盘的物理总容量，无需挂载即可查看（但还是使用lsblk好使）
fdisk -l | grep Disk

```

### 1.注册Linux系统服务（基于systemd）

1.将python脚本注册为Linux系统服务的详细步骤（基于systemd）

```shell
# 1.新建service文件
sudo vim /etc/systemd/system/python_script.service

# 2.编写服务配置（示例模板）
```

```
[Unit]
Description=My Python Service      # 服务描述
After=network.target               # 网络就绪后启动

[Service]
Type=simple
ExecStart=/usr/bin/python3 /绝对路径/your_script.py  # 必须用绝对路径
WorkingDirectory=/绝对路径/         # 脚本工作目录
Restart=always                     # 崩溃自动重启
User=your_username                 # 运行用户（建议非root）
Environment="PYTHONUNBUFFERED=1"   # 确保日志实时输出

[Install]
WantedBy=multi-user.target         # 多用户模式启动
```

```shell
# 3.重载systemd配置
sudo systemctl daemon-reload

# 4.启动服务和设置开机自启
sudo systemctl enable python_script.service
sudo systemctl start python_script.service

# 5.常用操作命令
# 查看状态
sudo systemctl status your_script.service  # 含运行日志

# 实时查看日志
sudo journalctl -u your_script.service -f  # 类似tail -f

# 重启/停止服务
sudo systemctl restart your_script.service
sudo systemctl stop your_script.service

# 6.其他service中的配置
# 1）：【日志重定向】若需将日志写入文件，在.service文件中添加
StandardOutput=file:/var/log/your_script.log
StandardError=file:/var/log/your_script_error.log

# 2）：【多进程设置】若脚本使用多进程（如 multiprocessing 库）
KillMode=process  # 防止systemd误杀子进程

# 3）：【资源限制】限制内存/CPU使用：
MemoryLimit=500M
CPUQuota=80%

```