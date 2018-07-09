# Git  分布式版本控制

1.三种状态 
  * 已提交（commited）
  * 已修改（modified）
  * 已暂存（staged）

2.三个工作区
  * Git仓库
  * 工作目录
  * 暂存区域
  
3.基本命令
  * git init  某个项目开始用git管理
  * git clone 克隆项目
  * git status 文件当前状态
  * git add 开始跟踪文件，并处于暂存状态
  * git commit -m "xxx" 提交到本地仓库
  * git rm 移除文件 
  * git rm -f 删除已经修改过的文件并且放入暂存区的
  * git rm --cached filename 远程库中删除但保留在当前工作目录中
  * git mv 重命名文件
  * git commit --amend 撤销操作修改最后一次提交
  * git fetch 抓取远程仓库中本地仓库没有的。
  * git bush 提交到远程仓库
  * git branch -r -1d origin/branch-name  删除远程分支
    * push origin :branch-name
  * git branch 查看分支
  * git branch xxx 新建分支
  * git checkout xx 切换分支
  * git checkout -b xx 创建并切换分支
  * git merge 合并分支
  