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


### java8 lambda

- 方法引用: 类名::方法名
~~~
构造一个该方法的闭包

表达式：
person->person.getName();
可以替换：
person::getName
表达式：
()->new HashMap<>();
可替换成：
HashMap::new
对应的参数类型是Function<T,R> T表示传入类型，R表示返回类型。
~~~
- 过滤
~~~ 
List<PunchCardInfo> punchCardInfos ;
 List<PunchCardInfo> infos = punchCardInfos.stream().filter(punchCardInfo -> punchCardInfo.getDateTime().equals(item.getDateTime())).collect(Collectors.toList());
~~~ 

- 分组统计
~~~
List<AbnormalAttendanceDTO> abnormalAttendanceDTOS
Map<String,Long> counting= abnormalAttendanceDTOS.stream().collect(Collectors.groupingBy(AbnormalAttendanceDTO::getContent,Collectors.counting()));

//遍历取key value值
for (Map.Entry<String,Long> entry:counting.entrySet()){
String key = entry.getKey();
Long value =entry.getValue();
}
~~~

- 分组
~~~
List<Zhrdk> result
Map<String,List<Zhrdk>> groupByDateList=result.stream().collect(Collectors.groupingBy(Zhrdk::getZbegda));
~~~

- 排序（降序reversed()）
~~~
punchCardInfoDTOS.stream().sorted(Comparator.comparing(PunchCardInfoDTO::getDateTime).reversed()).collect(Collectors.toList());
~~~

- 排序 （升序）
~~~
punchCardInfoDTOS.stream().sorted(Comparator.comparing(PunchCardInfoDTO::getDateTime)).collect(Collectors.toList());
~~~

- 根据对象具体属性去重
~~~
// 去重函数定义
public static <T>Predicate<T> distinctByProperty(Function<?super T,?> keyExtractor){
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t),Boolean.TRUE)==null;
    }
// :: 方法引用 类名::方法名
entryList.stream().filter(distinctByProperty(EventLog::getEquipmentId)).collect(Collectors.toList());
~~~
### 对象及json字符串互转
~~~
包名：com.alibaba.fastjson
对象转json字符串：JSONObject.toJSONString(对象)
json转对象：JSONObject.parseObject（json.toString(),对象.class）
~~~
