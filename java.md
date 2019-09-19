# 位移运算符
~~~
 for (int j = 1; j < 10; j++) {
            int a= 1 << j; // 左移运算服务 相当num*2~n
            int b=512>>j; // 右移运算符号 相当于 num/2~n
            System.out.println(a);
            System.err.println(b);
        }
        int c = 101;
        System.out.println(c>>>2);//无符号右移动 高位补0 c/2~n
~~~
