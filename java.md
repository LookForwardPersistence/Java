### 位移运算符
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

### 获取unsafe 实例
~~~
Field field = Unsafe.class.getDeclaredField("theUnsafe");
field.setAccessible(true);
Unsafe unsafe = (Unsafe) field.get(null);

// 案例
public class CounterUnsafe {
    volatile int i = 0;

    private static Unsafe unsafe = null;

    //i字段的偏移量
    private static long valueOffset;

    static {
        //unsafe = Unsafe.getUnsafe();
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);

            Field fieldi = CounterUnsafe.class.getDeclaredField("i");
            valueOffset = unsafe.objectFieldOffset(fieldi);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public void add() {
        //i++;
        for (;;){
            int current = unsafe.getIntVolatile(this, valueOffset);
            if (unsafe.compareAndSwapInt(this, valueOffset, current, current+1))
                break;
        }
    }
}
~~~
### transient 
~~~
1）一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。
2）transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。变量如果是用户自定义类变量，则该类需要实现Serializable接口。
3）被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化。

总之，java 的transient关键字为我们提供了便利，你只需要实现Serilizable接口，将不需要序列化的属性前添加关键字transient，序列化对象的时候，这个属性就不会序列化到指定的目的地中。
~~~
