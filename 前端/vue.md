### vue 文件下载
~~~
1.request.post('/export', data,{responseType:'blob'})
2.
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

3.获取blob数据fileBlod后调用 downloadFile(lieBlod,fileName);
~~~
