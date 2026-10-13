<!--
 * @Author: Angelo
 * @Date: 2026-10-13 16:14:35
 * @version: 
 * @Descripttion: 
-->
### ufw 常用指令速查表

#### 一、基础管理

| 指令 | 作用 | 备注 |
|---|---|---|
| `sudo ufw status` | 查看防火墙状态和规则 | 加 `verbose` 看更详细，加 `numbered` 显示编号 |
| `sudo ufw enable` | 启用防火墙 | **执行前务必先放行 SSH（22端口），否则可能把自己锁在外面** |
| `sudo ufw disable` | 关闭防火墙 | 所有规则保留，只是不生效 |
| `sudo ufw reload` | 重载配置 | 修改规则文件后刷新 |
| `sudo ufw reset` | 重置所有规则并关闭 | ⚠️ 会清空自定义规则，慎用 |
| `sudo ufw version` | 查看版本 | — |

#### 二、放行规则（allow）

| 指令 | 作用 |
|---|---|
| `sudo ufw allow 22` | 放行 TCP+UDP 的 22 端口 |
| `sudo ufw allow 22/tcp` | 只放行 TCP 的 22 端口 |
| `sudo ufw allow 80,443/tcp` | 一次性放行多个端口 |
| `sudo ufw allow 1000:2000/tcp` | 放行一段连续端口范围 |
| `sudo ufw allow from 192.168.1.100` | 只允许指定 IP 访问所有端口 |
| `sudo ufw allow from 10.0.0.0/24 to any port 3306` | 只允许某网段访问指定端口 |
| `sudo ufw allow from 192.168.1.50 to any port 22 proto tcp` | IP + 端口 + 协议三重限定 |
| `sudo ufw allow in on eth0 to any port 80` | 限定特定网卡 |
| `sudo ufw allow ssh` | 用服务名（读 `/etc/services`） |
| `sudo ufw allow http` / `allow https` | 同上 |

#### 三、拒绝与删除规则

| 指令 | 作用 |
|---|---|
| `sudo ufw deny 23` | 拒绝 23 端口（明确返回拒绝） |
| `sudo ufw deny from 1.2.3.4` | 拉黑某个 IP |
| `sudo ufw reject 25` | 拒绝并回显"不可达"（比 deny 更明确） |
| `sudo ufw delete allow 8080/tcp` | 删除某条放行规则 |
| `sudo ufw delete deny 23` | 删除某条拒绝规则 |
| `sudo ufw delete 3` | 按编号删除（先用 `status numbered` 查编号） |
| `sudo ufw insert 1 allow 443/tcp` | 在指定位置插入规则（顺序很重要） |

> 规则匹配是**从上到下第一条命中即生效**。deny 规则如果想优先，需要用 `insert` 插到前面。

#### 四、默认策略

| 指令 | 作用 |
|---|---|
| `sudo ufw default deny incoming` | 默认拒绝所有入站（推荐） |
| `sudo ufw default allow outgoing` | 默认允许所有出站 |
| `sudo ufw default deny routed` | 拒绝转发流量（做网关时才需要改） |

#### 五、日志与其他

| 指令 | 作用 |
|---|---|
| `sudo ufw logging on` | 开启日志 |
| `sudo ufw logging off` | 关闭日志 |
| `sudo ufw logging low` | 设置日志级别（off/low/medium/high/full） |
| `tail -f /var/log/ufw.log` | 实时看拦截记录（路径也可能是 `/var/log/syslog`） |
| `sudo ufw app list` | 列出可用的应用配置文件 |
| `sudo ufw app info 'Nginx Full'` | 查看某个应用配置包含哪些端口 |
| `sudo ufw route allow ...` | 添加路由/转发规则（NAT、Docker 桥接场景） |

#### 六、对应 firewalld 的写法对照

| 需求 | ufw | firewalld |
|---|---|---|
| 开端口 | `ufw allow 11434/tcp` | `firewall-cmd --add-port=11434/tcp --permanent` |
| 关端口 | `ufw delete allow 11434/tcp` | `firewall-cmd --remove-port=11434/tcp --permanent` |
| 生效 | 立即生效（无需 reload） | 需 `--reload` |
| 看全部规则 | `ufw status verbose` | `firewall-cmd --list-all` |
| 开机自启 | `ufw enable` | `systemctl enable firewalld` |
| 限制来源 IP | `ufw allow from 1.2.3.4 to any port 22` | `firewall-cmd --add-rich-rule='...'` |

---

### 给你当前场景的最简操作序列

```bash
sudo ufw allow 22/tcp          # 第一步，保命
sudo ufw allow 11434/tcp       # Ollama
sudo ufw enable                # 启用
sudo ufw status verbose        # 确认
```

另外提醒：如果这台机器是云服务器，**本机 ufw 放行了也不等于外面能通**，还得去云控制台的安全组里放行 TCP 11434。需要的话告诉我云厂商，我告诉你具体入口。