## flyio请求后端服务下载Excel文件，response返回乱码
- 请求时候必须带上 {responseType:'blob'}

- 前端
~~~ 前端
export function exportAppointList(data) {
    return request.post('/appoint/exportAppointList', data,{responseType:'blob'})
}

/*
* 文件下载
* 后台服务返回文件对象或blob文件对象时候需要调用此方法
* Added by FQH in 2019-07-06
* */
export function downloadFile(fileOrBlod,fileName){
    let url = window.URL.createObjectURL(fileOrBlod); //表示一个指定的file对象或Blob对象
    let a = document.createElement("a");
    document.body.appendChild(a);
    a.href = url;
    a.download = fileName; //命名下载名称
    a.click(); //点击触发下载
    window.URL.revokeObjectURL(url);  //下载完成进行释放
}

--页面调用
 api.exportAppointList(参数).then(res => {
                    downloadFile(res,"文件名称");
                }).catch(err=>{
                    '导出excel失败' +JSON.stringify(err);
                })
~~~ 
- 后端 Excel导出接口 采用poi组件
~~~ 后台
 response.reset();
 response.setCharacterEncoding("UTF-8");
 response.setContentType("application/vnd.ms-excel;charset=gb2312");
 response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("文件名称.xls", "UTF-8"));
 ....
 workbook.write(response.getOutputStream());
~~~

### 读取springboot框架下resource下文件
- ClassPathResource
~~~ 
 ClassPathResource template= new ClassPathResource("文件路径");
 example:
 ClassPathResource template= new ClassPathResource("/asset/detail.xls");
 注意：linux引用路径：template.getFile().getPath()
~~~

### java.net.SocketException Connection reset
- 问题：本地测试腾讯短信接口ok，部署到linux服务报 Connect reset异常

分析：java.net.SocketException: （Connection reset或者 Connect reset by peer:Socket write error）。该异常在客户端和服务器端均有可能发生，引起该异常的原因有两个，第一个就是如果一端的Socket被关闭（或主动关闭或者因为异常退出而引起的关闭），另一端仍发送数据，发送的第一个数据包引发该异常 (Connect reset by peer)。另一个是一端退出，但退出时并未关闭该连接，另一端如果在从连接中读数据则抛出该异常（Connection reset）。简单的说就是在连接断开后的读和写操作引起的。
- 解决：linux服务curl -I https://yun.tim.qq.com 查看网络是正常（ping通不代表网络是正常），发现服务访问yun.tim.qq.com被安全拦截。找安全同事放行问题即解决。


