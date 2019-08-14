~~~
package com.study.hc.net.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOClient {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        while (!socketChannel.finishConnect()) {
            // 没连接上,则一直等待
            Thread.yield();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入：");
        // 发送内容
        String msg = scanner.nextLine();
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        // 读取响应
        System.out.println("收到服务端响应:");
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);

        while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
            // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
            if (requestBuffer.position() > 0) break;
        }
        requestBuffer.flip();
        byte[] content = new byte[requestBuffer.limit()];
        requestBuffer.get(content);
        System.out.println(new String(content));
        scanner.close();
        socketChannel.close();
    }

}

~~~

~~~
package com.study.hc.net.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DawnNioHttpServer {

    public static Selector selector;

    // 定义线程池
    public static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(25, 25, 25,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private static ServerSocketChannel socketChannel;

    private static final int port = 8080;

    public static void main(String[] args) throws Exception {

        // serversocket
        socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress(port));

        System.out.println("NIO启动:" + port);
        // 获取一个选择器
        // 底层的事件通知机制
        // 老板娘 selector
        DawnNioHttpServer.selector = Selector.open();

        // 登记： 表示对这个通道上OP_ACCEPT事件感兴趣，并且返回一个标记
        // 此处表示，希望收到socket通道8080端口上建立连接这个通知
        SelectionKey selectionKey = socketChannel.register(DawnNioHttpServer.selector, 0);
        selectionKey.interestOps(selectionKey.OP_ACCEPT);
        
        while (true) { // 带几个美女，坐在大厅

            // 如果没有新的socket与服务器有连接或者是数据交互，这里就会等待1秒
            DawnNioHttpServer.selector.select(1000);

            // 开始处理
            Set<SelectionKey> selected = DawnNioHttpServer.selector.selectedKeys();
            Iterator<SelectionKey> iter = selected.iterator();
            while (iter.hasNext()) {
                // 获取注册在上面标记
                SelectionKey key = iter.next();

                if (key.isAcceptable()) { // 判断是否OP_ACCEPT的通知
                    // 处理连接
                    System.out.println("有新的连接啦，当前线程数量:"
                            + TonyNioHttpServer.threadPoolExecutor.getActiveCount());
                    // 有新的连接，赶紧接客
                    SocketChannel chan = socketChannel.accept();
                    // 问一下价格多少，需要什么样服务...
                    chan.configureBlocking(false);
                    // 注册一个新监听。
                    // 表示希望收到该连接上OP_READ数据传输事件的通知
                    chan.register(DawnNioHttpServer.selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) { // OP_READ
                    // 取出附着在上面的信息，也就是上面代码中附着的连接信息
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    // 处理中，不需要收到任何通知
                    key.cancel();
                    // tomcat 大保健旗舰店 有200技师，只有付钱的客户才会享受技师 泰式、保shen，
                    socketChannel.configureBlocking(false);
                    DawnNioHttpServer.threadPoolExecutor.execute(() -> {
                        try {
                            // 读取里面的内容，请注意，此处大小随意写的。
                            // tomcat中会根据Http协议中定义的长度来读取数据，或者一直读到通道内无数据为止
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            socketChannel.read(byteBuffer);
                            byteBuffer.flip(); // 转为读模式
                            String request = new String(byteBuffer.array());

                            System.out.println("收到新数据，当前线程数："
                                    + DawnNioHttpServer.threadPoolExecutor.getActiveCount()
                                    + "，请求内容：" + request);
                            // 给一个当前时间作为返回值
                            // 随意返回，不是Http的协议
                            byteBuffer.clear();
                            ByteBuffer wrap = ByteBuffer
                                    .wrap(("Dawn" + System.currentTimeMillis()).getBytes());
                            socketChannel.write(wrap);
                            wrap.clear();
                            socketChannel.configureBlocking(false);
                            // 注册一个新监听。 表示希望收到该连接上OP_READ事件的通知
                            socketChannel.register(TonyNioHttpServer.selector,
                                    SelectionKey.OP_READ);
                        } catch (Exception e) {
                            // e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + " 服务器线程处理完毕，当前线程数："
                                + threadPoolExecutor.getActiveCount());
                    });
                }
                // 取出后删除
                iter.remove();
            }
            selected.clear();
            // 过掉cancelled keys
            DawnNioHttpServer.selector.selectNow();
        }
    }
}

~~~
~~~
	<dependency>
  		<groupId>io.netty</groupId>
  		<artifactId>netty-all</artifactId>
  		<version>4.1.32.Final</version>
  	</dependency>
~~~
