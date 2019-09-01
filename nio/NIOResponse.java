package com.study.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Dawn on 2019-09-01.
 */
public class NIOResponse {
    private static byte[] request = null;
    static {
        request = "GET / HTTP/1.1\r\n\r\n".getBytes();
    }

    private static String hostName = "www.baidu.com";
    private static int port = 80;

    public static SocketChannel createSocketChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(hostName, port));
        while (!socketChannel.finishConnect()) {
            // 没连接上,则一直等待
            Thread.yield();
        }
        ByteBuffer buffer = ByteBuffer.wrap(request);
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        return socketChannel;
    }
    public static void main(String[] args) throws IOException {
       SocketChannel socketChannel = createSocketChannel();
        // 读取响应
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
            // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
            if (requestBuffer.position() > 0) break;
        }
        requestBuffer.flip();
        byte[] content = new byte[requestBuffer.limit()];
        requestBuffer.get(content);
        String result = new String(content);
        String[] arr=result.split("\r\n");
        StringBuilder builder = new StringBuilder();
        builder.append(getValue(arr,"server"));
        System.out.println(builder.toString());
        socketChannel.close();
    }

    private static String getValue(String[] result,String filter){
        String value = "";
        for (int i = 0; i < result.length; i++) {
            if(result[i].toLowerCase().contains(filter.toLowerCase())){
                value = result[i];
                break;
            }
        }
        return value;
    }
}
