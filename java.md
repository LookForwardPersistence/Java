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

# 获取unsafe 实例
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
