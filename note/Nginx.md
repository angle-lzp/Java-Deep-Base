1. 安装GCC编译器及构建工具
   适用于 CentOS/RHEL/Rocky/AlmaLinux 等基于YUM的系统：

Bash
# 安装GCC编译器和make工具
sudo yum install -y gcc make

# 安装开发工具组（包含autoconf、g++等）
sudo yum groupinstall -y "Development Tools"
2. 安装Nginx依赖的开发库
   Nginx编译依赖 PCRE、zlib、OpenSSL 的开发包：

Bash
sudo yum install -y pcre-devel zlib-devel openssl-devel
关键包说明：

pcre-devel：正则表达式支持
zlib-devel：GZIP压缩模块
openssl-devel：HTTPS/SSL功能
3. 重新执行configure命令
   安装完依赖后，返回Nginx源码目录重新配置：

Bash
cd /path/to/nginx-1.24.0
./configure --prefix=/usr/local/nginx \
--with-http_ssl_module \
--with-http_realip_module \
--with-http_gzip_static_module
4. 验证安装
   若配置成功，输出末尾会显示类似内容：

Configuration summary
+ using system PCRE library
+ OpenSSL library is not used
+ using system zlib library

nginx path prefix: "/usr/local/nginx"
...
随后继续执行编译：

Bash
make && sudo make install






=================================================

二、源码编译安装（适合自定义需求） 2
1. 安装依赖
   Bash
   sudo yum install -y gcc make openssl-devel pcre-devel zlib-devel
2. 下载并解压源码
   Bash
   wget http://nginx.org/download/nginx-1.25.3.tar.gz
   tar -zxvf nginx-1.25.3.tar.gz
   cd nginx-1.25.3
3. 编译配置
   Bash
   ./configure --prefix=/usr/local/nginx \
   --with-http_ssl_module \
   --with-http_realip_module \
   --with-http_gzip_static_module
   常用模块：--with-stream（TCP代理）、--with-http_v2_module（HTTP/2）

4. 编译安装
   Bash
   make && sudo make install
5. 配置系统服务
   Bash
   sudo tee /usr/lib/systemd/system/nginx.service <<EOF
   [Unit]
   Description=nginx
   After=network.target

[Service]
Type=forking
ExecStart=/usr/local/nginx/sbin/nginx
ExecReload=/usr/local/nginx/sbin/nginx -s reload
ExecStop=/usr/local/nginx/sbin/nginx -s quit
PrivateTmp=true

[Install]
WantedBy=multi-user.target
EOF

sudo systemctl daemon-reload
sudo systemctl start nginx





======================================
获取https证书

一、使用Certbot官方工具获取证书
1. 安装Certbot
   推荐通过snap安装（适用大多数Linux系统）：

Bash
sudo snap install --classic certbot      # 安装Certbot
sudo ln -s /snap/bin/certbot /usr/bin/certbot  # 创建快捷方式
2. 申请证书
   根据需求选择以下方式之一：

① 单域名证书（HTTP验证）

适用于单一域名（如example.com及www.example.com）：

Bash
sudo certbot --nginx -d example.com -d www.example.com  # 自动配置Nginx
# 或手动模式（适合非Nginx/Apache用户）
sudo certbot certonly --webroot -w /var/www/html -d example.com
Certbot会自动验证域名所有权并生成证书文件到/etc/letsencrypt/live/example.com/目录 6 5。

② 泛域名证书（DNS验证）

适用于*.example.com的所有子域名：

Bash
sudo certbot certonly --manual --preferred-challenges dns -d *.example.com -d example.com
根据提示在DNS解析中添加TXT记录（如_acme-challenge.example.com），验证后删除记录 1 3。

二、手动申请（第三方平台）
通过“来此加密”网站（2024年新版）：

访问 letsencrypt.osfipin.com。
输入域名，勾选“泛域名”和“包含根域”。
选择Let’s Encrypt渠道，完成DNS或HTTP验证。
下载证书文件（含.crt、.key和链文件） 2 4。
三、配置Web服务器（以Nginx为例）
编辑Nginx配置文件（如/etc/nginx/sites-available/example.com）：

Nginx
server {
listen 443 ssl;
server_name example.com;

    ssl_certificate /etc/letsencrypt/live/example.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/example.com/privkey.pem;
        
    # 其他配置...
}

# 强制HTTP跳转HTTPS
server {
listen 80;
server_name example.com;
return 301 https://$host$request_uri;
}
重启Nginx生效：sudo systemctl reload nginx 6 1。

四、自动续期配置
Let’s Encrypt证书有效期为90天，需设置自动续期：

Bash
# 测试续期流程
sudo certbot renew --dry-run

# 添加Cron任务（每天凌晨2点检查续期）
sudo crontab -e
添加行：0 2 * * * /usr/bin/certbot renew --quiet
注：泛域名需配合DNS API自动化脚本（如腾讯云DNSPod）实现无人值守续期 1。

五、验证与常见问题
验证HTTPS：访问 https://example.com，检查浏览器锁标志。
证书路径：证书文件默认在/etc/letsencrypt/live/目录。
错误处理：若DNS验证失败，检查TXT记录是否生效；若端口冲突，确保80/443端口未被占用 1 3。
其他免费证书备选方案
Cloudflare：自动为托管域名提供SSL（需使用其DNS服务） 9。
JoySSL：支持免费通配符证书，适合国内用户快速申请 8 11。
以上方法均通过Let’s Encrypt官方或兼容渠道实现免费HTTPS加密，推荐优先使用Certbot自动化工具以简化管理 1 