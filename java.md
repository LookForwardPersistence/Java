# 位移运算符
~~~
System.out.println(2<<3);//16  左运算符号 相当num*2~n
System.out.println(8>>3);//1  右运算符号 相当num/2~n
System.out.println(10&65535); //10 且运算
System.out.println(10|65535);//65535 或运算
System.out.println(6^2);//4

int bitmask = 0x000F;
int val = 0x2222;
System.out.println(val & bitmask);// prints "2"
~~~
