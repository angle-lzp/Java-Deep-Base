# Git 操作指令

## 文档目录

- [1. Git 常用指令操作](#1-git-常用指令操作)
    - [1.1 Git 常用命令速查表](#11-git-常用命令速查表)
    - [1.2 第一次获取项目](#12-第一次获取项目)
    - [1.3 初始化本地仓库](#13-初始化本地仓库)
    - [1.4 配置远程仓库](#14-配置远程仓库)
    - [1.5 配置提交身份](#15-配置提交身份)
    - [1.6 查看当前状态](#16-查看当前状态)
    - [1.7 同步远程代码](#17-同步远程代码)
    - [1.8 创建和切换功能分支](#18-创建和切换功能分支)
    - [1.9 查看和筛选修改内容](#19-查看和筛选修改内容)
    - [1.10 暂存要提交的文件](#110-暂存要提交的文件)
    - [1.11 创建提交](#111-创建提交)
    - [1.12 推送到 GitHub](#112-推送到-github)
    - [1.13 查看提交历史和定位问题](#113-查看提交历史和定位问题)
    - [1.14 临时保存未完成修改](#114-临时保存未完成修改)
    - [1.15 撤销和恢复修改](#115-撤销和恢复修改)
    - [1.16 处理合并或变基冲突](#116-处理合并或变基冲突)
    - [1.17 删除和清理分支](#117-删除和清理分支)
    - [1.18 提交信息和分支命名建议](#118-提交信息和分支命名建议)
    - [1.19 常见问题排查顺序](#119-常见问题排查顺序)
    - [1.20 常见安全注意事项](#120-常见安全注意事项)
    - [1.21 最终检查清单](#121-最终检查清单)
    - [1.22 常用命令参数详解](#122-常用命令参数详解)
    - [1.23 完整提交代码流程](#123-完整提交代码流程)
- [2. 实战案例：将本地项目提交到公司 GitHub](#2-实战案例将本地项目提交到公司-github)
    - [2.1 进入项目目录](#21-进入项目目录)
    - [2.2 查看当前 Git 状态](#22-查看当前-git-状态)
    - [2.3 查看远程仓库配置](#23-查看远程仓库配置)
    - [2.4 添加或修改公司仓库 remote](#24-添加或修改公司仓库-remote)
    - [2.5 配置当前仓库的提交身份](#25-配置当前仓库的提交身份)
    - [2.6 配置默认分支和常用行为](#26-配置默认分支和常用行为)
    - [2.7 使用公司账号测试访问权限](#27-使用公司账号测试访问权限)
    - [2.8 Windows 凭据管理](#28-windows-凭据管理)
    - [2.9 首次提交本地项目](#29-首次提交本地项目)
    - [2.10 首次推送 main 分支](#210-首次推送-main-分支)
    - [2.11 处理远程已有 initial commit](#211-处理远程已有-initial-commit)
    - [2.12 常见连接和权限错误](#212-常见连接和权限错误)
    - [2.13 完整执行示例](#213-完整执行示例)
- [3. 实战案例2：Commit 回滚操作指南](#3-git-commit-回滚操作指南)
    - [3.1 场景说明](#31-场景说明)
    - [3.2 撤销 Commit，但保留暂存区（推荐）](#32-撤销-commit但保留暂存区推荐)
    - [3.3 撤销-commit并取消暂存保留代码](#33-撤销-commit并取消暂存保留代码)        
    - [3.4 撤销 Commit 并删除所有修改（危险操作）](#34-撤销-commit-并删除所有修改危险操作)
    - [3.5 已 Push 到远程仓库后的回滚](#35-已-push-到远程仓库后的回滚)
    - [3.3 撤销-commit并取消暂存保留代码](#33-撤销-commit并取消暂存保留代码)
    - [3.6 方法一：使用 Revert 回滚（推荐）](#36-方法一使用-revert-回滚推荐)
    - [3.7 方法二：强制删除远程 Commit（谨慎）](#37-方法二强制删除远程-commit谨慎)
    - [3.8 查看提交记录](#38-查看提交记录)
    - [3.9 回退到指定 Commit](#39-回退到指定-commit)
    - [3.10 HEAD 说明](#310-head-说明)
    - [3.11 常用命令速查](#311-常用命令速查)
    - [3.12 推荐使用方式](#312-推荐使用方式)
    - [3.13 Git 回滚原理示意图](#313-git-回滚原理示意图)
- [4. 实战案例3：Git 暂存除指定文件以外的所有文件](#4-git-暂存除指定文件以外的所有文件)
    - [4.1 暂存除 a.txt 以外的所有文件](#41-暂存除-atxt-以外的所有文件)
    - [4.2 检查暂存结果](#42-检查暂存结果)
    - [4.3 排除子目录中的指定文件](#43-排除子目录中的指定文件)
    - [4.4 排除多个指定文件](#44-排除多个指定文件)
    - [4.5 排除某种类型的所有文件](#45-排除某种类型的所有文件)
    - [4.6 排除整个目录](#46-排除整个目录)
    - [4.7 a.txt 已经被加入暂存区时的处理方法](#47-atxt-已经被加入暂存区时的处理方法)
    - [4.8 提交除 a.txt 之外的文件](#48-提交除-atxt-之外的文件)
    - [4.9 仅本次排除与永久忽略的区别](#49-仅本次排除与永久忽略的区别)
    - [4.10 查看暂存区中即将提交的文件](#410-查看暂存区中即将提交的文件)
    - [4.11 常用命令汇总](#411-常用命令汇总)
    - [4.12 推荐的安全操作流程](#412-推荐的安全操作流程)

目标组织仓库地址：

```text
https://github.com/IT-Service-Security/iot-camera-ai-detection.git
```

## 1. Git 常用指令操作

### 1.1 Git 常用命令速查表

| 操作顺序 | 场景 | 命令 | 用途 |
| --- | --- | --- | --- |
| 1 | 获取项目 | `git clone <repo-url>` | 从远程仓库下载项目和历史记录 |
| 2 | 初始化仓库 | `git init` | 将当前目录初始化为 Git 仓库 |
| 3 | 设置主分支 | `git branch -M main` | 将当前分支重命名为 `main` |
| 4 | 配置远程仓库 | `git remote add origin <repo-url>` | 绑定 GitHub 仓库地址 |
| 5 | 配置提交身份 | `git config user.name "Name"` | 设置当前仓库提交用户名 |
| 6 | 查看状态 | `git status -sb` | 查看当前分支和文件变化 |
| 7 | 同步远程 | `git fetch origin` | 获取远程提交但不合并 |
| 8 | 创建分支 | `git switch -c feature/name` | 创建并切换到功能分支 |
| 9 | 查看修改 | `git diff` | 查看未暂存修改 |
| 10 | 暂存文件 | `git add path/to/file` | 将指定文件放入暂存区 |
| 11 | 检查暂存 | `git diff --cached` | 确认下一次提交内容 |
| 12 | 创建提交 | `git commit -m "type: message"` | 保存本地提交 |
| 13 | 推送分支 | `git push -u origin branch` | 首次推送并建立跟踪关系 |
| 14 | 查看历史 | `git log --oneline --graph --all` | 查看提交和分支关系 |
| 15 | 临时保存 | `git stash push -u -m "说明"` | 暂存未完成修改 |

### 1.2 第一次获取项目

已经存在 GitHub 远程仓库时，推荐直接克隆：

```powershell
cd C:\IOT\Project\IoT-AI-Project
git clone https://github.com/IT-Service-Security/iot-camera-ai-detection.git
cd iot-camera-ai-detection
```

说明：

- `cd` 是 PowerShell 的目录切换命令。
- `git clone` 会下载项目文件、完整提交历史，并自动创建名为 `origin` 的远程仓库地址。
- 最后一个 `cd` 用于进入项目根目录，后续 Git 命令应在该目录执行。

### 1.3 初始化本地仓库

本地已经有项目文件、但还不是 Git 仓库时使用：

```powershell
cd C:\IOT\Project\IoT-AI-Project\iot-camera-ai-detection
git init
git branch -M main
```

说明：

- `git init` 会在当前目录创建 `.git`，让该目录成为本地仓库。
- `git branch -M main` 将当前分支强制重命名为 `main`。
- `-M` 是强制重命名参数，即使目标分支名已存在也会覆盖；小写 `-m` 在目标分支已存在时会拒绝执行。
- `main` 是目标分支名称，团队也可以根据规范使用其他主分支名称。

### 1.4 配置远程仓库

当前没有远程仓库时：

```powershell
git remote add origin https://github.com/IT-Service-Security/iot-camera-ai-detection.git
```

如果 `origin` 已存在但地址不正确：

```powershell
git remote set-url origin https://github.com/IT-Service-Security/iot-camera-ai-detection.git
```

验证远程地址：

```powershell
git remote -v
git remote show origin
```

说明：

- `remote` 表示远程仓库配置。
- `add` 表示新增远程仓库别名。
- `origin` 是远程仓库别名，通常代表默认 GitHub 仓库。
- `set-url` 表示修改已有远程仓库地址。
- `-v` 是 `--verbose`，用于显示 fetch 和 push 两个地址。

### 1.5 配置提交身份

```powershell
git config user.name "公司 GitHub 用户名"
git config user.email "公司 GitHub 邮箱"
git config --list --show-origin
```

说明：

- `git config` 用于读取或写入 Git 配置。
- `user.name` 是提交作者名称。
- `user.email` 是提交作者邮箱，建议与公司 GitHub 邮箱一致。
- 不加 `--global` 时，只影响当前仓库。
- `--list` 列出配置项，`--show-origin` 显示配置来源，例如 `.git/config` 或用户目录下的 `.gitconfig`。

### 1.6 查看当前状态

```powershell
git status
git status --short --branch
git branch -vv
```

说明：

- `git status` 显示当前分支、暂存区、工作区和未跟踪文件状态。
- `--short` 使用简洁状态格式。
- `--branch` 同时显示当前分支和远程跟踪关系。
- `-sb` 是 `--short --branch` 的缩写。
- `git branch -vv` 显示本地分支、对应远程分支以及领先或落后状态。

### 1.7 同步远程代码

只获取远程最新信息，不修改当前文件：

```powershell
git fetch origin
```

查看本地和远程差异：

```powershell
git log --oneline HEAD..origin/main
git diff HEAD..origin/main
```

拉取并使用变基同步当前分支：

```powershell
git pull --rebase origin main
```

说明：

- `fetch` 会更新本地的远程跟踪分支，例如 `origin/main`，但不会直接修改当前工作区。
- `HEAD..origin/main` 表示查看远程有、本地没有的提交或修改。
- `pull` 等于 `fetch` 加后续整合操作。
- `--rebase` 会把本地提交重新放到远程最新提交之后，通常能保持较直的提交历史。
- `origin` 是远程仓库别名，`main` 是远程分支名。

### 1.8 创建和切换功能分支

从最新 `main` 创建功能分支：

```powershell
git switch main
git pull --rebase origin main
git switch -c feature/license-plate-recognition
```

查看分支：

```powershell
git branch
git branch -a
git branch -vv
```

切换到已有分支：

```powershell
git switch feature/license-plate-recognition
```

说明：

- `switch` 用于切换分支。
- `-c` 表示创建新分支并立即切换，等价于 `--create`。
- `git branch` 查看本地分支。
- `-a` 表示 `--all`，同时显示本地分支和远程跟踪分支。
- `-vv` 显示每个本地分支的跟踪关系和最后一次提交摘要。

### 1.9 查看和筛选修改内容

```powershell
git diff
git diff --cached
git diff -- path/to/file.py
git diff --check
```

说明：

- `git diff` 查看尚未暂存的工作区修改。
- `--cached` 或 `--staged` 查看暂存区与最近一次提交之间的差异。
- `-- path/to/file.py` 将检查范围限制到指定文件。
- `--check` 检查行尾空格、空白错误等常见格式问题。

### 1.10 暂存要提交的文件

```powershell
git add path/to/file.py
git add README.md
git add .
git add -p
git restore --staged path/to/file.py
```

说明：

- `git add` 将工作区修改放入暂存区，不会创建提交。
- `git add .` 暂存当前目录及子目录下的新增、修改和删除。
- `-p` 表示 `--patch`，可以逐段选择要暂存的修改。
- `git restore --staged` 只取消暂存，不会丢失工作区修改。

### 1.11 创建提交

```powershell
git commit -m "feat: add license plate recognition"
git log --oneline -5
git commit --amend --no-edit
```

说明：

- `git commit` 将暂存区内容保存为本地提交。
- `-m` 表示 `--message`，用于直接填写提交说明。
- `git log --oneline -5` 查看最近 5 条提交。
- `--amend` 会重写最近一次提交，适合修正尚未推送的提交。
- `--no-edit` 表示沿用上一次提交说明。

### 1.12 推送到 GitHub

第一次推送当前功能分支：

```powershell
git push -u origin feature/license-plate-recognition
```

后续推送当前分支：

```powershell
git push
```

说明：

- `push` 将本地提交上传到远程仓库。
- `-u` 或 `--set-upstream` 建立本地分支与远程分支的跟踪关系。
- `origin` 是远程仓库别名。
- `feature/license-plate-recognition` 是要推送的本地分支名。

### 1.13 查看提交历史和定位问题

```powershell
git log --oneline --graph --decorate --all
git show <commit-id>
git log --follow -- path/to/file.py
git log --oneline --grep="mqtt"
git blame -L 20,40 -- path/to/file.py
```

说明：

- `--oneline` 每个提交只显示一行摘要。
- `--graph` 用字符图显示分支和合并关系。
- `--decorate` 显示分支名、标签名和远程跟踪标记。
- `--all` 同时查看所有本地分支和远程跟踪分支。
- `git show <commit-id>` 查看某个提交的详细修改。
- `git blame -L 20,40` 查看指定行范围最后由哪个提交修改。

### 1.14 临时保存未完成修改

```powershell
git stash push -u -m "wip: camera detection"
git stash list
git stash show --stat stash@{0}
git stash apply stash@{0}
git stash drop stash@{0}
```

说明：

- `stash` 临时保存未提交修改，使工作区恢复干净。
- `-u` 表示 `--include-untracked`，同时保存未跟踪文件。
- `-m` 为 stash 添加说明。
- `apply` 恢复修改但保留 stash，`drop` 删除指定 stash。

### 1.15 撤销和恢复修改

```powershell
git restore path/to/file.py
git restore .
git clean -nd
git clean -fd
```

说明：

- `git restore path/to/file.py` 撤销指定文件尚未暂存的修改。
- `git restore .` 撤销当前目录下尚未暂存的修改。
- `git clean -nd` 预览将要删除的未跟踪文件。
- `git clean -fd` 强制删除未跟踪文件，执行前必须确认预览结果。

### 1.16 处理合并或变基冲突

发生冲突后，先查看冲突文件：

```powershell
git status
```

处理完冲突后继续：

```powershell
git add path/to/resolved-file.py
git commit
```

如果正在执行 `rebase`：

```powershell
git add path/to/resolved-file.py
git rebase --continue
```

放弃当前合并或变基：

```powershell
git merge --abort
git rebase --abort
```

说明：

- 冲突文件中通常会出现 `<<<<<<<`、`=======`、`>>>>>>>` 标记。
- 删除冲突标记，只保留最终正确内容。
- `--continue` 表示冲突解决后继续 rebase。
- `--abort` 表示放弃当前操作并尽量恢复到操作前状态。

### 1.17 删除和清理分支

```powershell
git branch -d feature/license-plate-recognition
git branch -D feature/license-plate-recognition
git push origin --delete feature/license-plate-recognition
```

说明：

- `-d` 删除已经合并的本地分支。
- `-D` 强制删除本地分支，可能丢失未合并提交。
- `--delete` 删除远程分支。

### 1.18 提交信息和分支命名建议

提交信息推荐格式：

```text
<type>: <简短说明>
```

常用类型：

- `feat`：新增功能，例如 `feat: add dock occupancy detection`。
- `fix`：修复问题，例如 `fix: handle mqtt reconnect failure`。
- `docs`：修改文档，例如 `docs: update git workflow`。
- `refactor`：重构代码但不改变外部行为。
- `test`：新增或修改测试。
- `chore`：依赖、脚本或构建配置维护。

分支命名建议：

```text
feature/license-plate-recognition
fix/mqtt-reconnect
docs/github-workflow
refactor/influxdb-client
```

### 1.19 常见问题排查顺序

推送失败或提示没有权限：

```powershell
git status -sb
git remote -v
git branch -vv
git fetch origin
```

远程有本地没有的提交：

```powershell
git fetch origin
git log --oneline HEAD..origin/main
git pull --rebase origin main
git push
```

不确定当前修改是否能删除：

```powershell
git status
git diff
git diff --cached
git stash push -u -m "backup before cleanup"
```

### 1.20 常见安全注意事项

- 不要把密码、Token、API Key、私钥、`.env` 文件或生产环境配置提交到 Git。
- 提交前检查 `git diff --cached`，避免把敏感信息写入提交历史。
- 敏感信息一旦推送，应立即轮换凭据，并联系仓库管理员清理历史。
- 不要随意使用 `git push --force`；确实需要时优先使用 `git push --force-with-lease`。
- 不要对共享分支执行未经团队确认的 `rebase`、`reset` 或强制推送。

### 1.21 最终检查清单

提交前：

- [ ] 当前分支不是错误的目标分支。
- [ ] `git status` 中的文件都是本次任务相关文件。
- [ ] 已执行 `git diff` 和 `git diff --cached`。
- [ ] 已执行 `git diff --check`。
- [ ] 没有密码、Token、私钥、`.env` 或生产配置。
- [ ] 提交信息能清楚描述本次修改。

推送前：

- [ ] `git remote -v` 指向正确的公司仓库。
- [ ] 当前分支已经提交干净。
- [ ] 已同步远程最新代码，或已确认不会覆盖他人提交。
- [ ] 功能开发优先推送功能分支，而不是直接修改 `main`。
- [ ] 推送后准备创建或更新 Pull Request。

### 1.22 常用命令参数详解

- `-M`：用于 `git branch -M main`，强制重命名当前分支。
- `-m`：用于 `git commit -m "message"`，直接指定提交信息；在 `git branch -m` 中表示普通重命名分支。
- `-u`：用于 `git push -u origin branch`，建立本地分支和远程分支的跟踪关系。
- `-c`：用于 `git switch -c branch`，创建并切换到新分支。
- `-a`：用于 `git branch -a`，显示本地和远程全部分支。
- `-vv`：用于 `git branch -vv`，显示分支跟踪关系和最后提交摘要。
- `--short`：用于 `git status --short`，以简洁格式显示状态。
- `--branch`：用于 `git status --branch`，显示当前分支信息。
- `--cached`：用于 `git diff --cached`，只查看暂存区差异。
- `--rebase`：用于 `git pull --rebase`，使用变基方式同步远程提交。
- `--no-ff`：用于 `git merge --no-ff branch`，即使可以快进也保留合并提交。
- `--delete`：用于 `git push origin --delete branch`，删除远程分支。
- `--abort`：用于 `git merge --abort` 或 `git rebase --abort`，放弃当前合并或变基。

### 1.23 完整提交代码流程

以下示例覆盖初始化仓库、配置远程地址、创建分支、提交代码、推送分支、合并回主分支的完整流程：

```powershell
# 1. 进入项目目录
cd "C:\IOT\Project\IoT-AI-Project\iot-camera-ai-detection"

# 2. 初始化 Git 仓库，并统一主分支名称
git init
git branch -M main

# 3. 配置远程 GitHub 仓库
git remote add origin https://github.com/IT-Service-Security/iot-camera-ai-detection.git
git remote -v

# 4. 配置当前仓库提交身份
git config user.name "公司 GitHub 用户名"
git config user.email "公司 GitHub 邮箱"

# 5. 检查工作区状态和文件差异
git status --short --branch
git diff --check
git diff

# 6. 暂存文件，并确认即将提交的内容
git add .
git diff --cached

# 7. 创建本地提交
git commit -m "chore: initialize project"

# 8. 创建并切换到功能分支
git switch -c feature/your-feature-name

# 9. 首次推送功能分支，并建立跟踪关系
git push -u origin feature/your-feature-name

# 10. 回到 main，同步远程最新代码
git switch main
git pull --rebase origin main

# 11. 合并功能分支并推送 main
git merge feature/your-feature-name --no-ff
git push origin main
```

说明：

- 如果远程仓库已经有 `origin`，将 `git remote add origin ...` 改为 `git remote set-url origin ...`。
- 如果项目已有 Git 仓库，不需要重复执行 `git init`。
- 如果团队要求通过 Pull Request 合并，不要在本地执行 `git merge` 后直接推送 `main`，应推送功能分支后在 GitHub 页面创建 Pull Request。

## 2. 实战案例：将本地项目提交到公司 GitHub

### 2.1 进入项目目录

```powershell
cd C:\IOT\Project\IoT-AI-Project\iot-camera-ai-detection
```

说明：

- `cd` 是 PowerShell 的目录切换命令，不是 Git 参数。
- 后续命令必须在项目根目录执行，否则可能操作错误的仓库。
- 可以用 `Get-Location` 查看当前 PowerShell 路径。

```powershell
cd C:\IOT\Project\IoT-AI-Project\iot-camera-ai-detection
```

说明：

- 后续所有 Git 命令都需要在项目根目录执行。
- 如果当前 PowerShell 已经在该目录，可以跳过这一步。

### 2.2 查看当前 Git 状态

```powershell
git status --short --branch
```

说明：

- `--short` 使用简洁格式显示文件变化。
- `--branch` 同时显示当前分支名称和跟踪关系。
- 如果当前目录不是 Git 仓库，会出现 `not a git repository`，需要先执行 `git init`，或进入正确目录。

### 2.3 查看远程仓库配置

```powershell
git remote -v
```

说明：

- `origin` 通常是默认远程仓库名称。
- `(fetch)` 是拉取地址，`(push)` 是推送地址。
- 如果没有任何输出，说明当前仓库还没有配置远程仓库。

查看更详细的远程信息：

```powershell
git remote show origin
```

如果提示没有 `origin`，先执行第 2.4 节的添加命令。

### 2.4 添加或修改公司仓库 remote

当前没有远程仓库时：

```powershell
git remote add origin https://github.com/IT-Service-Security/iot-camera-ai-detection.git
```

如果 `origin` 已存在但地址不正确：

```powershell
git remote set-url origin https://github.com/IT-Service-Security/iot-camera-ai-detection.git
```

验证地址：

```powershell
git remote -v
```

预期结果：

```text
origin  https://github.com/IT-Service-Security/iot-camera-ai-detection.git (fetch)
origin  https://github.com/IT-Service-Security/iot-camera-ai-detection.git (push)
```

说明：

- `remote add` 只适用于远程名称不存在的情况。
- 如果报错 `remote origin already exists`，改用 `remote set-url`。
- 修改 remote 地址不会修改本地文件，只会改变后续 fetch 和 push 的目标。

### 2.5 配置当前仓库的提交身份

```powershell
git config user.name "公司 GitHub 用户名"
git config user.email "公司 GitHub 邮箱"
```

查看当前仓库身份和配置来源：

```powershell
git config user.name
git config user.email
git config --list --show-origin
```

说明：

- 不加 `--global` 时，只修改当前仓库的提交身份。
- 这里的身份会写入 commit 作者信息，不等于 GitHub 登录账号。
- `.git/config` 表示当前仓库配置，用户目录下的 `.gitconfig` 表示全局配置。

### 2.6 配置默认分支和常用行为

```powershell
git config init.defaultBranch main
git config pull.rebase true
git config fetch.prune true
```

说明：

- `init.defaultBranch main` 让以后新建仓库默认使用 `main`。
- `pull.rebase true` 让 `git pull` 默认使用变基；团队若规定使用 merge，应以团队规范为准。
- `fetch.prune true` 在 fetch 时清理已经被远程删除的本地远程跟踪分支。
- 这些配置默认作用于当前仓库；需要影响所有项目时才考虑加 `--global`。

### 2.7 使用公司账号测试访问权限

```powershell
git fetch origin
git branch -r
git ls-remote --heads origin
```

说明：

- `fetch` 可以验证远程地址和账号是否有读取权限。
- 如果 Git 弹出登录窗口，请使用公司 GitHub 账号完成认证。
- 如果终端要求输入密码，GitHub 这里应使用 Personal Access Token，而不是网页登录密码。
- 不要把 Token 写进 remote URL、PowerShell 历史、脚本或 Markdown 文档。
- 如果公司组织启用了 SSO/SAML，Token 还需要授权给 `IT-Service-Security` 组织。

### 2.8 Windows 凭据管理

查看 Git 使用的凭据帮助程序：

```powershell
git config --show-origin --get-all credential.helper
```

打开系统凭据管理器：

```powershell
control /name Microsoft.CredentialManager
```

说明：

- 在“Windows 凭据”中查找与 `github.com` 相关的记录。
- 只有确认需要切换账号时，才删除旧的 GitHub 凭据。
- 删除凭据后，下一次 `git fetch` 或 `git push` 会重新要求认证。
- 不建议把 Token 保存到明文 `.git-credentials` 文件中。

如果需要拒绝当前 HTTPS 凭据：

```powershell
@"
protocol=https
host=github.com

"@ | git credential reject
```

说明：

- `protocol` 和 `host` 用于准确指定需要清除的凭据。
- 最后的空行用于结束凭据输入。
- 清除凭据不会删除 GitHub 账号，也不会修改仓库文件。

### 2.9 首次提交本地项目

```powershell
git status --short
git add .
git diff --cached
git commit -m "chore: initialize project"
```

说明：

- `git add .` 会将当前目录下的新增、修改和删除放入暂存区。
- 提交前必须检查 `git diff --cached`，确认没有密码、Token、日志、虚拟环境和构建产物。
- `git commit` 只创建本地提交，不会自动上传到 GitHub。

如果项目还没有 `.gitignore`，建议先加入：

```text
.env
.venv/
venv/
__pycache__/
*.pyc
*.log
dist/
build/
```

### 2.10 首次推送 main 分支

```powershell
git branch --show-current
git branch -M main
git push -u origin main
```

说明：

- `push` 将本地提交上传到 GitHub。
- `-u` 将本地 `main` 与远程 `origin/main` 建立跟踪关系。
- 建立跟踪关系后，在当前分支上通常可以直接执行 `git push` 和 `git pull`。

### 2.11 处理远程已有 initial commit

如果推送出现：

```text
rejected: Updates were rejected because the remote contains work that you do not have locally
```

先获取远程提交：

```powershell
git fetch origin
git log --oneline HEAD..origin/main
```

确认需要保留本地和远程双方内容时：

```powershell
git merge origin/main --allow-unrelated-histories
git status
git add path/to/resolved-file
git commit
git push -u origin main
```

说明：

- `--allow-unrelated-histories` 只适用于本地和远程分别独立初始化的情况。
- 不要为了绕过错误直接执行 `git push --force`，这可能覆盖远程已有提交。
- 如果远程仓库的 initial commit 只是自动生成的 README，也应先确认团队是否允许合并两段历史。

### 2.12 常见连接和权限错误

### `error: No such remote 'origin'`

```powershell
git remote add origin https://github.com/IT-Service-Security/iot-camera-ai-detection.git
```

### `fatal: 'origin' does not appear to be a git repository`

```powershell
git rev-parse --show-toplevel
git remote -v
git ls-remote origin
```

### `Repository not found` 或 `403`

常见原因：

- remote 地址拼写错误。
- 当前认证账号没有加入 `IT-Service-Security` 组织或目标仓库。
- Token 没有 `repo` 权限，或没有完成组织 SSO 授权。
- Windows Credential Manager 中仍保存着没有权限的旧账号。

排查顺序：

```powershell
git remote -v
git fetch origin
```

然后在浏览器打开目标仓库地址，确认公司账号可以正常访问。

### `fatal: refusing to work with credential missing host field`

执行 `git credential reject` 时必须同时提供 `protocol=https` 和 `host=github.com`，可使用第 2.8 节中的 PowerShell 多行命令。

### 2.13 完整执行示例

适用于本地已经有项目文件，但还没有绑定公司远程仓库的情况：

```powershell
cd C:\IOT\Project\IoT-AI-Project\iot-camera-ai-detection

# 1. 初始化并确认主分支名称
git init
git branch -M main

# 2. 配置公司远程仓库
git remote add origin https://github.com/IT-Service-Security/iot-camera-ai-detection.git
git remote -v

# 3. 配置当前仓库的提交身份
git config user.name "公司 GitHub 用户名"
git config user.email "公司 GitHub 邮箱"

# 4. 检查并提交本地文件
git status --short --branch
git diff --check
git add .
git diff --cached
git commit -m "chore: initialize project"

# 5. 测试远程访问并首次推送
git fetch origin
git push -u origin main
```

说明：

- 如果 `git remote add origin` 报 `already exists`，改用 `git remote set-url origin ...`。
- 如果 `git fetch origin` 失败，先解决账号、权限或 SSO 问题，再继续推送。
- 如果远程已有独立提交，按照第 2.11 节处理，不要直接强制推送。

## 3. Git Commit 回滚操作指南

### 3.1 场景说明

当执行以下命令后：

```bash
git commit -m "提交说明"
```

发现本次提交存在问题，例如：

- Commit 信息填写错误
- 漏提交文件
- 提交了错误文件
- 代码需要继续修改
- 需要彻底撤销本次修改

此时可以根据实际情况选择不同的回滚方式。

---

### 3.2 撤销 Commit，但保留暂存区（推荐）

#### 3.2.1 使用场景

适用于：

- Commit 信息写错
- 漏提交文件
- 需要重新提交

#### 3.2.2 操作命令

```bash
git reset --soft HEAD~1
```

#### 3.2.3 执行效果

执行后：

```text
√ Commit 被删除
√ 代码保留
√ 暂存区保留
```

当前状态：

```bash
git status
```

输出类似：

```text
Changes to be committed
```

#### 3.2.4 重新提交

```bash
git commit -m "新的提交说明"
```

---

### 3.3 撤销 Commit，并取消暂存（保留代码）

#### 3.3.1 使用场景

适用于：

- 想重新选择提交文件
- 想重新执行 git add
- 修改部分文件后重新提交

#### 3.3.2 操作命令

```bash
git reset HEAD~1
```

或：

```bash
git reset --mixed HEAD~1
```

#### 3.3.3 执行效果

执行后：

```text
√ Commit 被删除
√ 代码保留
√ 暂存区清空
```

查看状态：

```bash
git status
```

输出类似：

```text
Changes not staged for commit
```

#### 3.3.4 重新提交

```bash
git add .
git commit -m "新的提交说明"
```

---

### 3.4 撤销 Commit 并删除所有修改（危险操作）

#### 3.4.1 使用场景

适用于：

- 本次提交完全不要
- 代码修改全部丢弃
- 需要恢复到提交前状态

#### 3.4.2 操作命令

```bash
git reset --hard HEAD~1
```

#### 3.4.3 执行效果

执行后：

```text
√ Commit 被删除
√ 代码被删除
√ 暂存区清空
```

#### 3.4.4 注意事项

⚠️ 此操作不可恢复。

执行前建议先查看提交记录：

```bash
git log --oneline
```

---

### 3.5 已 Push 到远程仓库后的回滚

#### 3.5.1 场景说明

如果已经执行：

```bash
git push origin main
```

说明提交已经同步至远程仓库。

此时推荐使用以下两种方式。

---

### 3.6 方法一：使用 Revert 回滚（推荐）

#### 3.6.1 使用场景

适用于：

- 已 Push 到远程仓库
- 团队协作项目
- 不想破坏提交历史

#### 3.6.2 操作命令

```bash
git revert HEAD
```

然后推送：

```bash
git push origin main
```

#### 3.6.3 执行效果

Git 会自动创建一个新的反向提交：

```text
Commit A
↓
Commit B（用于撤销 Commit A）
```

#### 3.6.4 优点

```text
√ 不修改提交历史
√ 团队协作安全
√ Git 官方推荐
```

---

### 3.7 方法二：强制删除远程 Commit（谨慎）

#### 3.7.1 使用场景

适用于：

- 个人项目
- 尚未有人拉取代码
- 需要完全删除该提交

#### 3.7.2 回退本地提交

```bash
git reset --hard HEAD~1
```

#### 3.7.3 强制推送到远程

```bash
git push --force origin main
```

推荐更安全方式：

```bash
git push --force-with-lease origin main
```

#### 3.7.4 注意事项

⚠️ 不推荐在以下场景使用：

- Main 分支
- Master 分支
- 多人协作项目
- 已被其他成员同步的提交

---

### 3.8 查看提交记录

#### 3.8.1 查看最近 5 条提交

```bash
git log --oneline -5
```

输出示例：

```text
3f8a2c1 Fix login bug
8d7e5f2 Update README
6b3c4d5 Initial Commit
```

#### 3.8.2 查看完整提交记录

```bash
git log
```

---

### 3.9 回退到指定 Commit

#### 3.9.1 查看 Commit ID

```bash
git log --oneline
```

例如：

```text
3f8a2c1 Fix login bug
8d7e5f2 Update README
6b3c4d5 Initial Commit
```

#### 3.9.2 保留代码回退

```bash
git reset --soft 8d7e5f2
```

#### 3.9.3 删除代码回退

```bash
git reset --hard 8d7e5f2
```

---

### 3.10 HEAD 说明

#### 3.10.1 HEAD 含义

```text
HEAD       当前提交
HEAD~1     上一次提交
HEAD~2     上两次提交
HEAD~3     上三次提交
```

#### 3.10.2 示例

撤销最近两次提交：

```bash
git reset --soft HEAD~2
```

撤销最近三次提交：

```bash
git reset --soft HEAD~3
```

---

### 3.11 常用命令速查

#### 3.11.1 查看提交记录

```bash
git log --oneline -5
```

#### 3.11.2 撤销 Commit（保留暂存）

```bash
git reset --soft HEAD~1
```

#### 3.11.3 撤销 Commit（保留代码）

```bash
git reset HEAD~1
```

#### 3.11.4 撤销 Commit（删除代码）

```bash
git reset --hard HEAD~1
```

#### 3.11.5 已 Push 的安全回滚

```bash
git revert HEAD
git push origin main
```

#### 3.11.6 已 Push 的强制回滚

```bash
git reset --hard HEAD~1
git push --force-with-lease origin main
```

---

### 3.12 推荐使用方式

对于日常开发场景：

- VS Code 项目开发
- Python 项目开发
- IoT-AI 项目开发
- GitHub 仓库管理

如果刚刚 Commit 且尚未 Push，推荐使用：

```bash
git reset --soft HEAD~1
```

推荐等级：

```text
★★★★★ git reset --soft HEAD~1
★★★★☆ git reset HEAD~1
★★★☆☆ git revert HEAD
★☆☆☆☆ git reset --hard HEAD~1
```

---

### 3.13 Git 回滚原理示意图

原始状态：

```text
A → B → C(HEAD)
```

执行：

```bash
git reset --soft HEAD~1
```

结果：

```text
A → B(HEAD)

C 的代码仍在暂存区
```

执行：

```bash
git reset HEAD~1
```

结果：

```text
A → B(HEAD)

C 的代码仍在工作区
```

执行：

```bash
git reset --hard HEAD~1
```

结果：

```text
A → B(HEAD)

C 的代码已彻底删除
```

### 4. Git 暂存除指定文件以外的所有文件

在使用 Git 提交代码时，有时需要暂存当前目录中的所有变更，但排除某个不希望提交的文件。

例如，当前目录中包含以下文件：

```text
a.txt
b.txt
c.txt
config.json
```

如果需要暂存除 `a.txt` 以外的所有文件，可以使用 Git 的排除路径语法。

#### 4.1 暂存除 a.txt 以外的所有文件

执行以下命令：

```bash
git add . -- ':!a.txt'
```

也可以使用完整的 `exclude` 写法：

```bash
git add . -- ':(exclude)a.txt'
```

以上两条命令的效果基本相同：

- 暂存当前目录及其子目录中的其他变更。
- 排除项目根目录下的 `a.txt`。
- `a.txt` 的修改仍然保留在工作区中。
- 后续执行 `git commit` 时，不会提交未暂存的 `a.txt`。

命令各部分含义如下：

```text
git add .
```

表示添加当前目录及其子目录中的变更。

```text
--
```

表示 Git 命令选项结束，后面的参数均按路径规则处理。

```text
':!a.txt'
```

表示从本次 `git add` 操作中排除 `a.txt`。

> 建议保留单引号，避免某些 Shell 将 `!` 识别为历史命令展开符。

#### 4.2 检查暂存结果

执行暂存命令后，使用以下命令检查 Git 状态：

```bash
git status
```

预期结果类似：

```text
Changes to be committed:
    modified:   b.txt
    modified:   c.txt
    modified:   config.json

Changes not staged for commit:
    modified:   a.txt
```

其中：

- `b.txt`、`c.txt` 和 `config.json` 已经进入暂存区。
- `a.txt` 仍然保留在工作区中。
- 后续执行 `git commit` 时不会提交 `a.txt`。

#### 4.3 排除子目录中的指定文件

如果需要排除的文件位于子目录中，例如：

```text
config/a.txt
```

可以执行：

```bash
git add . -- ':!config/a.txt'
```

也可以使用完整写法：

```bash
git add . -- ':(exclude)config/a.txt'
```

执行后，Git 会暂存其他文件，但不会暂存：

```text
config/a.txt
```

#### 4.4 排除多个指定文件

如果需要同时排除多个文件，例如：

```text
a.txt
config.json
logs/app.log
```

可以执行：

```bash
git add . -- ':!a.txt' ':!config.json' ':!logs/app.log'
```

也可以使用完整的 `exclude` 写法：

```bash
git add . -- \
  ':(exclude)a.txt' \
  ':(exclude)config.json' \
  ':(exclude)logs/app.log'
```

执行后，Git 会暂存其他变更，但不会暂存以下文件：

```text
a.txt
config.json
logs/app.log
```

#### 4.5 排除某种类型的所有文件

如果需要排除当前目录及其子目录中的所有 `.txt` 文件，可以执行：

```bash
git add . -- ':(glob,exclude)**/*.txt'
```

例如，以下文件都不会被暂存：

```text
a.txt
docs/readme.txt
config/example.txt
```

其他类型的文件仍然会正常进入暂存区。

如果只需要排除项目根目录中的 `.txt` 文件，可以执行：

```bash
git add . -- ':(glob,exclude)*.txt'
```

需要注意：

- `*.txt` 主要匹配当前路径层级。
- `**/*.txt` 可以匹配不同目录层级中的 `.txt` 文件。

#### 4.6 排除整个目录

如果需要排除整个 `logs` 目录及目录中的所有文件，可以执行：

```bash
git add . -- ':!logs/'
```

也可以使用完整写法：

```bash
git add . -- ':(exclude)logs/**'
```

例如，项目中存在以下文件：

```text
src/main.js
src/config.js
logs/app.log
logs/error.log
```

执行：

```bash
git add . -- ':!logs/'
```

Git 会暂存：

```text
src/main.js
src/config.js
```

但不会暂存：

```text
logs/app.log
logs/error.log
```

如果 `logs` 目录不在项目根目录，需要填写相对于 Git 仓库根目录的路径。

例如，要排除：

```text
backend/logs/
```

可以执行：

```bash
git add . -- ':!backend/logs/'
```

#### 4.7 a.txt 已经被加入暂存区时的处理方法

如果 `a.txt` 已经通过以下命令进入暂存区：

```bash
git add .
```

此时再执行：

```bash
git add . -- ':!a.txt'
```

通常不会自动把已经暂存的 `a.txt` 从暂存区移除。

应该先取消暂存：

```bash
git restore --staged a.txt
```

然后检查状态：

```bash
git status
```

`git restore --staged a.txt` 只会将 `a.txt` 从暂存区移除，不会：

- 删除本地的 `a.txt`。
- 撤销 `a.txt` 中的修改。
- 将 `a.txt` 恢复为之前的版本。

执行后，`a.txt` 的修改仍然保留在工作区中。

然后可以重新暂存其他文件：

```bash
git add . -- ':!a.txt'
```

完整操作流程如下：

```bash
git restore --staged a.txt
git add . -- ':!a.txt'
git status
```

如果已经暂存了多个不应该提交的文件，可以一次取消暂存：

```bash
git restore --staged a.txt config.json logs/app.log
```

#### 4.8 提交除 a.txt 之外的文件

确认暂存结果正确后，可以执行提交：

```bash
git commit -m "提交除 a.txt 之外的文件"
```

推荐的完整操作流程如下：

```bash
git add . -- ':!a.txt'
git status
git commit -m "提交除 a.txt 之外的文件"
```

如果 `a.txt` 已经被误加入暂存区，则执行：

```bash
git restore --staged a.txt
git add . -- ':!a.txt'
git status
git commit -m "提交除 a.txt 之外的文件"
```

执行 `git commit` 后，只有暂存区中的文件会被提交，工作区中未暂存的 `a.txt` 不会进入本次提交。

#### 4.9 仅本次排除与永久忽略的区别

执行以下命令：

```bash
git add . -- ':!a.txt'
```

只会在本次 `git add` 操作中排除 `a.txt`。

下一次如果直接执行：

```bash
git add .
```

`a.txt` 仍然可能进入暂存区。

如果希望 Git 长期忽略 `a.txt`，可以在项目根目录的 `.gitignore` 文件中添加：

```gitignore
a.txt
```

如果希望忽略某个子目录中的文件，可以添加：

```gitignore
config/a.txt
```

如果希望忽略整个目录，可以添加：

```gitignore
logs/
```

修改 `.gitignore` 后，可以检查状态：

```bash
git status
```

需要注意，如果 `a.txt` 已经被 Git 跟踪，仅在 `.gitignore` 中添加 `a.txt` 不会停止跟踪。

此时需要执行：

```bash
git rm --cached a.txt
```

然后提交 `.gitignore` 和索引变更：

```bash
git add .gitignore
git commit -m "停止跟踪 a.txt"
```

`git rm --cached a.txt` 的作用是：

- 从 Git 索引中移除 `a.txt`。
- 停止 Git 对 `a.txt` 的版本跟踪。
- 不会删除本地磁盘中的 `a.txt`。

如果需要停止跟踪整个目录，可以执行：

```bash
git rm -r --cached logs/
```

然后提交：

```bash
git add .gitignore
git commit -m "停止跟踪 logs 目录"
```

#### 4.10 查看暂存区中即将提交的文件

使用以下命令查看已经暂存的文件名称：

```bash
git diff --cached --name-only
```

示例输出：

```text
b.txt
c.txt
config.json
```

如果输出中没有 `a.txt`，说明 `a.txt` 不会进入下一次提交。

如果需要查看暂存区中的具体修改内容，可以执行：

```bash
git diff --cached
```

如果需要查看工作区中尚未暂存的修改，可以执行：

```bash
git diff
```

推荐在提交前依次执行：

```bash
git status
git diff --cached --name-only
git diff --cached
```

这样可以确认：

- 哪些文件已经进入暂存区。
- 哪些文件将进入本次提交。
- 每个文件具体修改了什么内容。

#### 4.11 常用命令汇总

暂存除 `a.txt` 以外的所有文件：

```bash
git add . -- ':!a.txt'
```

使用完整的排除语法：

```bash
git add . -- ':(exclude)a.txt'
```

排除子目录中的指定文件：

```bash
git add . -- ':!config/a.txt'
```

同时排除多个文件：

```bash
git add . -- ':!a.txt' ':!config.json' ':!logs/app.log'
```

排除所有目录层级中的 `.txt` 文件：

```bash
git add . -- ':(glob,exclude)**/*.txt'
```

排除整个 `logs` 目录：

```bash
git add . -- ':!logs/'
```

取消已经暂存的 `a.txt`：

```bash
git restore --staged a.txt
```

检查 Git 状态：

```bash
git status
```

查看暂存区中的文件：

```bash
git diff --cached --name-only
```

查看暂存区中的具体修改：

```bash
git diff --cached
```

提交已经暂存的文件：

```bash
git commit -m "提交除 a.txt 之外的文件"
```

#### 4.12 推荐的安全操作流程

如果 `a.txt` 当前还没有进入暂存区，推荐执行：

```bash
git add . -- ':!a.txt'
git status
git diff --cached --name-only
git commit -m "提交除 a.txt 之外的文件"
```

如果不确定 `a.txt` 是否已经进入暂存区，推荐执行：

```bash
git restore --staged a.txt
git add . -- ':!a.txt'
git status
git diff --cached --name-only
git commit -m "提交除 a.txt 之外的文件"
```

其中，以下命令是提交前最重要的检查步骤：

```bash
git diff --cached --name-only
```

只有该命令列出的文件会进入下一次提交。