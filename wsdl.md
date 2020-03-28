### 由wsdl地址生产webservice客户端代码
- cxf2.7版本需要对应jdk7
- cmd进入到cxf bin目录 wsdl2java -encoding utf-8 [-p 包名] -d 生产代码存放路径 -client/-service/-all  wsdl(网址或wsdl文件)

~~~
生产服务端或客户端webservice代码
wsdl2java.bat用法：
wsdl2java –p com.jdf –d F:\ -all xx.wsdl
-p 指定wsdl的命名空间，也就是要生成代码的包名
-d 指令要生成代码所在目录
-client 生成客户端测试web service的代码
-server 生成服务器启动web service代码
-impl 生成web service的实现代码，我们在方式一用的就是这个
-ant 生成build.xml文件
-all 生成所有开始端点代码。

Exceple:
X:\sofware\apache-cxf-3.0.16\bin>wsdl2java -p com.dawn.bom.service.plm -d E:\resources\services -all E:\resources\InfoSrv.wsdl

~~~
