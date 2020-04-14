- 初始化extjs项目
~~~
   获取项目模板之后，需要新建ExtJS项目的工作空间。工作空间的目录规定建在src/main/webapp/script目录下（目的也是为了自动化部署）。在DOS窗口执行以下命令建立工作空间。
项目所在盘符：  //如c:或者d: 或者 e:
cd web模块的路径\src\main\webapp\script //如c:\workspace\ppm\ppm-web\src\main\webapp\script
//如sencha -sdk c:\apptool\ext-6.0.0 generate workspace . （workspace 后先空格再加‘.’）
sencha -sdk apptool文件夹路径\ext-6.0.0 generate workspace . 
例如以下命令：
c:
cd c:\workspace\ppm\ppm-web\src\main\webapp\script
sencha -sdk C:\apptool\ext-6.0.0 generate workspace . 
 
  ExtJS项目建立好之后，第一次编译js下的源码，使用如下DOS命令。
在以上当前路径是c:\workspace\ppm\ppm-web\src\main\webapp\script的情况下，执行如下命令：
cd js
sencha app build
 
 
  后续有新的页面更新或在页面新引入了组件，使用以下命令刷新：
cd  web模块的路径\src\main\webapp\script\js
sencha app refresh production   
或者使用sencha app build重新编译（这条是编译命令，比sencha app refresh productiion慢，建议使用以上命令）
~~~
