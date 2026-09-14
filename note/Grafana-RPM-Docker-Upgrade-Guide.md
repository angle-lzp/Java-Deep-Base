# Grafana 漏洞修复升级操作手册（RPM 与 Docker Compose）

> RPM 为测试环境：Grafana OSS `12.4.1` → grafana_12.4.3+security-02_25720634919_linux_amd64.rpm
> Docker Compose 为生产环境：Grafana Enterprise `12.0.0` → grafana/grafana-enterprise:12.4.3-security-02

## 1. 背景说明

当前生产环境存在 Grafana 相关漏洞，已确认涉及版本包括：

- Grafana Enterprise `12.0.0`
- Grafana OSS `12.4.1`

根据漏洞扫描结果，相关修复版本要求包括：

- `12.0.0 < 12.1.10`，需要升级到 `12.1.10` 或更高版本
- `12.4.0 < 12.4.3+security-02`，需要升级到 `12.4.3+security-02` 或更高版本

建议生产环境优先升级到 Grafana 12.x 安全修复版本，例如：

```text
12.4.3+security-02 或更高的 12.x 安全版本
```

如果需要升级到 Grafana `13.x`，建议先在测试环境验证插件、数据源、告警、SSO、Dashboard 兼容性后再升级生产。

> 注意：本文分别覆盖 RPM 安装方式和 Docker Compose 启动方式。请根据服务器实际部署方式选择对应章节执行。

---

## 2. 升级前通用检查

### 2.1 确认当前版本

RPM 安装方式：

```bash
grafana-server -v
rpm -qa | grep grafana
sudo systemctl status grafana-server
```

Docker Compose 方式：

```bash
docker compose ps
docker inspect grafana --format '{{.Config.Image}}'
docker exec grafana grafana-server -v
docker exec grafana curl -s http://localhost:3000/api/health
```

如果服务器使用旧版 Compose 命令，请将 `docker compose` 替换为：

```bash
docker-compose
```

### 2.2 确认后端数据库类型

Grafana 默认使用 SQLite，数据库通常位于：

```text
/var/lib/grafana/grafana.db
```

RPM 安装方式检查：

```bash
grep -A20 '^\[database\]' /etc/grafana/grafana.ini
```

Docker Compose 方式检查：

```bash
docker exec grafana grep -A20 '^\[database\]' /etc/grafana/grafana.ini
```

如果使用 MySQL 或 PostgreSQL，升级前必须额外备份外部数据库。

### 2.3 检查插件

RPM 安装方式：

```bash
grafana-cli plugins ls
```

Docker Compose 方式：

```bash
docker exec grafana grafana-cli plugins ls
```

如果存在第三方插件、自研插件或很久没有更新的插件，升级后需要重点验证 Dashboard 是否正常显示。

---

## 3. RPM 安装方式升级步骤（RHEL）

适用于通过 `.rpm` 包、`yum` 或 `dnf` 安装的 Grafana 服务。

### 3.1 推荐升级目标

如果当前为 `12.4.1`，建议升级到：

```text
grafana-12.4.3+security_02-1.x86_64.rpm
```

如果当前为 `12.0.0`，最低需要升级到：

```text
12.1.10 或更高版本
```

为了生产环境版本统一，建议也升级到：

```text
12.4.3+security_02 或更高的 12.x 安全版本
```

### 3.2 升级前备份

```bash
BACKUP_DIR=/backup/grafana-before-upgrade-$(date +%F)
sudo mkdir -p $BACKUP_DIR/etc $BACKUP_DIR/var-lib $BACKUP_DIR/log

sudo cp -a /etc/grafana $BACKUP_DIR/etc/
sudo cp -a /var/lib/grafana $BACKUP_DIR/var-lib/
sudo cp -a /var/log/grafana $BACKUP_DIR/log/ 2>/dev/null || true

grafana-cli plugins ls | sudo tee $BACKUP_DIR/plugins.txt
```

确认备份：

```bash
sudo ls -lh $BACKUP_DIR
sudo ls -lh $BACKUP_DIR/etc/grafana
sudo ls -lh $BACKUP_DIR/var-lib/grafana
```

如果使用 SQLite，确认数据库已备份：

```bash
sudo ls -lh $BACKUP_DIR/var-lib/grafana/grafana.db
```

如果使用 MySQL，示例备份命令：

```bash
mysqldump -h <db_host> -u <db_user> -p <grafana_db_name> > $BACKUP_DIR/grafana_mysql.sql
```

如果使用 PostgreSQL，示例备份命令：

```bash
pg_dump -h <db_host> -U <db_user> <grafana_db_name> > $BACKUP_DIR/grafana_postgres.sql
```

### 3.3 查看可升级版本

如果服务器可以访问 Grafana 官方仓库：

```bash
sudo dnf clean metadata
sudo dnf --showduplicates list grafana
```

RHEL 较老版本可使用：

```bash
sudo yum clean metadata
sudo yum --showduplicates list grafana
```

### 3.4 使用仓库升级

升级到仓库最新版本：

```bash
sudo dnf update grafana
```

或旧版系统：

```bash
sudo yum update grafana
```

如果需要指定版本，以 `--showduplicates` 输出的实际版本号为准，例如：

```bash
sudo dnf install grafana-12.4.3*
```

### 3.5 使用本地 RPM 包升级（当前使用的方式）

将 RPM 包上传到服务器后，进入 RPM 所在目录：

```bash
ls -lh grafana-*.rpm
```

建议使用 `dnf` 安装本地 RPM：

```bash
sudo systemctl stop grafana-server
sudo dnf install ./grafana-12.4.3+security_02-1.x86_64.rpm
```

如果没有 `dnf`，使用 `yum`：

```bash
sudo systemctl stop grafana-server
sudo yum localinstall ./grafana-12.4.3+security_02-1.x86_64.rpm
```

也可以使用 `rpm -Uvh`：

```bash
sudo systemctl stop grafana-server
sudo rpm -Uvh grafana-12.4.3+security_02-1.x86_64.rpm
```

安装过程中看到类似输出，说明软件包层面升级成功：

```text
Updating / installing...
grafana-12.4.3+security_02-1

Cleaning up / removing...
grafana-12.4.1-1
```

### 3.6 启动并验证

```bash
sudo systemctl daemon-reload
sudo systemctl start grafana-server
sudo systemctl status grafana-server
```

确认版本：

```bash
grafana-server -v
rpm -qa | grep grafana
```

检查健康接口：

```bash
curl http://127.0.0.1:3000/api/health
```

期望返回类似：

```json
{
  "database": "ok",
  "version": "12.4.3",
  "edition": "Enterprise"
}
```

查看日志：

```bash
sudo journalctl -u grafana-server -n 200 --no-pager
```

重点确认没有以下异常：

```text
migration failed
database is locked
plugin failed
permission denied
failed to start
```

### 3.7 RPM 方式回滚

> 注意：Grafana 升级启动后可能会执行数据库 migration。回滚时不能只降级 RPM，必要时要同时恢复升级前的数据备份。

停止服务：

```bash
sudo systemctl stop grafana-server
```

安装旧版本 RPM：

```bash
sudo rpm -Uvh --oldpackage grafana-<old-version>.x86_64.rpm
```

恢复配置和数据：

```bash
sudo mv /etc/grafana /etc/grafana.failed-$(date +%F-%H%M%S)
sudo mv /var/lib/grafana /var/lib/grafana.failed-$(date +%F-%H%M%S)

sudo cp -a $BACKUP_DIR/etc/grafana /etc/
sudo cp -a $BACKUP_DIR/var-lib/grafana /var/lib/
```

启动服务：

```bash
sudo systemctl start grafana-server
sudo systemctl status grafana-server
```

---

## 4. Docker Compose 方式升级步骤

适用于通过 `docker-compose.yml` 启动的 Grafana 服务。

当前示例配置：

```yaml
services:
  grafana:
    image: grafana/grafana-enterprise:12.0.0
    container_name: grafana
    restart: unless-stopped
    ports:
      - "3000:3000"
    volumes:
      - grafana-storage:/var/lib/grafana
      - grafana-logs:/var/log/grafana
      - grafana-config:/etc/grafana
```

对应命名卷：

```text
grafana-persistent-data
grafana-persistent-logs
grafana-persistent-config
```

### 4.1 推荐升级目标

将镜像从：

```yaml
image: grafana/grafana-enterprise:12.0.0
```

升级到官方存在的安全版本，例如：

```yaml
image: grafana/grafana-enterprise:12.4.3
```

如果官方镜像仓库存在安全后缀 tag，也可以使用：

```yaml
image: grafana/grafana-enterprise:12.4.3-security-02
```

以 Docker Hub 或内部镜像仓库实际存在的 tag 为准。生产环境不要使用：

```yaml
image: grafana/grafana-enterprise:latest
```

### 4.2 升级前确认

进入 Compose 文件所在目录：

```bash
cd /path/to/your/docker-compose-dir
```

确认当前容器状态：

```bash
docker compose ps
docker inspect grafana --format '{{.Config.Image}}'
docker exec grafana grafana-server -v
docker exec grafana curl -s http://localhost:3000/api/health
```

### 4.3 备份 Docker Volume

创建备份目录：

```bash
BACKUP_DIR=/backup/grafana-docker-before-upgrade-$(date +%F)
sudo mkdir -p $BACKUP_DIR
```

备份数据卷：

```bash
docker run --rm \
  -v grafana-persistent-data:/data:ro \
  -v $BACKUP_DIR:/backup \
  alpine \
  tar czf /backup/grafana-persistent-data.tar.gz -C /data .
```

备份配置卷：

```bash
docker run --rm \
  -v grafana-persistent-config:/config:ro \
  -v $BACKUP_DIR:/backup \
  alpine \
  tar czf /backup/grafana-persistent-config.tar.gz -C /config .
```

备份日志卷，可选：

```bash
docker run --rm \
  -v grafana-persistent-logs:/logs:ro \
  -v $BACKUP_DIR:/backup \
  alpine \
  tar czf /backup/grafana-persistent-logs.tar.gz -C /logs .
```

保存插件清单：

```bash
docker exec grafana grafana-cli plugins ls | sudo tee $BACKUP_DIR/plugins.txt
```

确认备份文件：

```bash
ls -lh $BACKUP_DIR
```

说明：这里的 `alpine` 是临时工具容器，用来挂载 Docker volume 并执行 `tar` 打包。命令执行完后，临时容器会因为 `--rm` 自动删除。

### 4.4 修改 docker-compose.yml

修改前：

```yaml
image: grafana/grafana-enterprise:12.0.0
```

修改后，例如：

```yaml
image: grafana/grafana-enterprise:12.4.3
```

或：

```yaml
image: grafana/grafana-enterprise:12.4.3-security-02
```

### 4.5 拉取新镜像

```bash
docker compose pull grafana
```

如果使用旧版 Compose：

```bash
docker-compose pull grafana
```

如果出现以下报错：

```text
manifest unknown
```

说明镜像 tag 不存在，需要更换为官方或内部仓库实际存在的 tag。

可以单独测试拉取：

```bash
docker pull grafana/grafana-enterprise:12.4.3
```

### 4.6 重建容器

```bash
docker compose up -d grafana
```

旧版 Compose：

```bash
docker-compose up -d grafana
```

该命令会使用新镜像重建 Grafana 容器，并继续挂载原来的命名卷。

不要执行：

```bash
docker compose down -v
```

`-v` 会删除 Docker volume，存在数据丢失风险。

### 4.7 验证升级结果

检查容器状态：

```bash
docker compose ps
```

查看日志：

```bash
docker logs --tail=200 grafana
```

确认版本：

```bash
docker exec grafana grafana-server -v
```

检查健康接口：

```bash
docker exec grafana curl -s http://localhost:3000/api/health
curl http://127.0.0.1:3000/api/health
```

期望返回类似：

```json
{
  "database": "ok",
  "version": "12.4.3",
  "edition": "Enterprise"
}
```

### 4.8 Docker Compose 方式回滚

如果新版本启动失败，先查看日志：

```bash
docker logs --tail=300 grafana
```

将 `docker-compose.yml` 镜像改回旧版本，例如：

```yaml
image: grafana/grafana-enterprise:12.0.0
```

停止当前容器：

```bash
docker compose stop grafana
```

恢复数据卷：

```bash
docker run --rm \
  -v grafana-persistent-data:/data \
  -v $BACKUP_DIR:/backup \
  alpine \
  sh -c "rm -rf /data/* && tar xzf /backup/grafana-persistent-data.tar.gz -C /data"
```

恢复配置卷：

```bash
docker run --rm \
  -v grafana-persistent-config:/config \
  -v $BACKUP_DIR:/backup \
  alpine \
  sh -c "rm -rf /config/* && tar xzf /backup/grafana-persistent-config.tar.gz -C /config"
```

重新拉起旧版本容器：

```bash
docker compose up -d grafana
```

确认状态：

```bash
docker compose ps
docker exec grafana grafana-server -v
docker logs --tail=200 grafana
```

---

## 5. 升级后业务验证清单

升级完成后，建议至少验证以下项目：

- Grafana 页面可以正常打开
- 管理员账号可以正常登录
- 常用 Dashboard 可以正常加载
- Data sources 连接测试通过
- Alert rules 状态正常
- Contact points / Notification policies 正常
- 插件页面无明显报错
- LDAP、OAuth、SAML、OIDC 或反向代理登录正常，如果有使用
- 反向代理、HTTPS、域名访问正常，如果有使用
- API 调用和自动化脚本正常，如果有使用
- 网络组或安全平台重新扫描后漏洞关闭

---

## 6. 推荐生产执行策略

### RPM 生产环境

建议路径：

```text
备份 /etc/grafana 与 /var/lib/grafana
-> 安装 12.4.3+security_02 或更高 12.x 安全版本 RPM
-> 重启 grafana-server
-> 验证版本、服务状态、日志、Dashboard、数据源、告警
-> 安全复扫
```

### Docker Compose 生产环境

建议路径：

```text
备份 grafana-persistent-data 与 grafana-persistent-config volume
-> 修改 docker-compose.yml 镜像 tag
-> docker compose pull grafana
-> docker compose up -d grafana
-> 验证容器状态、版本、健康接口、日志、Dashboard、数据源、告警
-> 安全复扫
```

### 版本选择建议

如果目标只是修复当前漏洞，优先选择 Grafana 12.x 安全修复版本：

```text
12.4.3+security-02 或更高 12.x 安全版本
```

如果计划升级到 Grafana 13.x，例如 `13.2.1`，建议先在测试环境验证后再升级生产，重点关注：

- 第三方插件兼容性
- 自研插件兼容性
- 告警规则
- 数据源
- SSO / LDAP / OAuth
- Dashboard 展示
- 外部 API 调用
- grafana-image-renderer 等附属组件

---

## 7. 风险提醒

- 不要在生产环境使用 `latest` 镜像 tag。
- 不要执行 `docker compose down -v`，除非明确要删除 volume。
- Grafana 升级后可能执行数据库 migration，回滚时需要恢复升级前数据库或 volume。
- 如果使用外部 MySQL/PostgreSQL，必须单独备份数据库。
- 升级前后都要保存版本、日志和健康检查结果，便于审计和问题追踪。
