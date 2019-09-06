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
