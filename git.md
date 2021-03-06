### gitlab 配置
~~~
ssh-keygen -t rsa -C '账号'   （回车）
~~~

### git安装配置
~~~
配置：git config --globle user.name='' 
      git config --globle user.email=''
1、mkdir 文件夹名
2、cd 文件夹名    git init
3、git add 文件夹
4、git commit -m '描述'
5、git remote add origin git@github.com:LookForwardPersistence/vueApp.git
6、git push -u origin master

7、git pull (origin master) 从远程获取最新版本并merge到本地


.推送本地分支到远程仓库
git push --set-upstream origin 分支名

~~~
- 本地合并分支
~~~
#切换到需要合并目标分支（如切换到master 合并dev分支）
git checkout master
#拉取最新分支代码
git pull origin master
#合并dev分支
git merge dev
#查看是否有冲突 有则解决冲突
git status
# 没有冲突即可提交
git push origin master
~~~
### 下载指定git项目的文件
- 点击进入需要下载的文件目录，拷贝网页的链接地址
~~~
如：
https://github.com/apache/rocketmq-externals/tree/master/rocketmq-console
~~~
- 修改链接，路由改为 trunk
~~~
如
https://github.com/apache/rocketmq-externals/trunk/rocketmq-console
~~~
- 使用svn下载

### 冲突解决
~~~
Step 1. Fetch and check out the branch for this merge request

git fetch origin
git checkout -b master origin/master
Step 2. Review the changes locally

Step 3. Merge the branch and fix any conflicts that come up

git checkout prd
git merge --no-ff master
Step 4. Push the result of the merge to GitLab

git push origin prd
Tip: You can also checkout merge requests locally by following these guidelines.
~~~

### 新增工程
~~~
…or create a new repository on the command line
echo "# javaProject" >> README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin https://github.com/LookForwardPersistence/javaProject.git
git push -u origin master

…or push an existing repository from the command line
git remote add origin https://github.com/LookForwardPersistence/javaProject.git
git push -u origin master

…or import code from another repository
You can initialize this repository with code from a Subversion, Mercurial, or TFS project.
~~~

### 撤销修改(删除)
~~~
git status
git checkout 路径（文件夹）/需要撤销的文件
~~~


### 基本操作
~~~
创建本地分支：
git branch "分支名称"

删除本地分支：
git branch -d "分支名称"

在已有分支创建分支
git checkout -b 分支名称

切换分支：
git checkout 分支名称

合并分支到主干
0、（master） git push 获取最新
1、（master）git merge dev--squash 意思是把dev分支不同于master分支的所有文件罗列出来(无论有几个提交)，然后就可以方便的git commit提交
2、（mater）git commit 提交合并
3、（mater）git push origin master

预览异常：
切换到master分支允许

查看提交信息
git log

撤销commit
git reset --hard 提交id(硬盘信息也会被撤销)
git reset --soft commitID(磁盘信息不回撤销，只撤销commit)

如果不小执行 --hard

查看标志信息
git --reflog

git reset --hard commitId

撤销add
git reset HEAD
~~~
- Check out, review, and merge locally
~~~
Step 1. Fetch and check out the branch for this merge request

git fetch origin
git checkout -b master origin/master
Step 2. Review the changes locally

Step 3. Merge the branch and fix any conflicts that come up
git checkout prd
git merge --no-ff master
Step 4. Push the result of the merge to GitLab
git push origin prd
~~~
