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

### 图文合并
~~~
 ImageIcon imgIcon=new ImageIcon(filePath);
        Image theImg =imgIcon.getImage();
        int width=theImg.getWidth(null)==-1?200:theImg.getWidth(null);
        int height= theImg.getHeight(null)==-1?200:theImg.getHeight(null);
        BufferedImage bimage = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g=bimage.createGraphics();
        Color mycolor = Color.black;
        g.setColor(mycolor);
//        g.setBackground(Color.ORANGE);
        g.drawImage(theImg, 0, 0, null );
        g.setFont(new Font("华文宋体",Font.ROMAN_BASELINE,36)); //字体、字型、字号
        Color blue = Color.blue;
        String title="标题";
        int xPosistion=width/10+25;
        int yPositon=height/2+80;
        int sort=80;
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i <8 ; i++) {
            int preData=yPositon+sort;
            map.put(i,preData+sort*i);
        }
        g.drawString(title,xPosistion,yPositon);
        g.dispose();
        FileOutputStream out=null;
        try
        {
            out = new FileOutputStream(outPath); //输出文件流
           // 使用推外内存
            ImageIO.setUseCache(false);
            ImageIO.write(bimage,"jpg",out);
            out.close();
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            return false;
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        }
~~~
### springboot获取文件路径
~~~
private String getFilePath(String filePath){
        File file = null;
        try {
            file = ResourceUtils.getFile(filePath);
            if (file==null){
                return "";
            }
            System.err.println(file.getPath());
            return file.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    eg： String prefixPath= getFilePath("classpath:static/images");
~~~
### poi 读取excel表格
~~~
private static final String XLS ="xls";
private static final String XLSX="xlsx";

public Workbook getWorkbook(InputStream inputStream,String fileType) {
        Workbook workbook =null;
        try {
            if(fileType.equalsIgnoreCase(XLS)){
                workbook=new HSSFWorkbook(inputStream);
            }else if (fileType.equalsIgnoreCase(XLSX)){
                workbook=new XSSFWorkbook(inputStream);
            }
        }catch (Exception ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return workbook;
    }
    


    public String getCellValue(Cell cell){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String cellValue="";
        try {

        if (cell == null) {
            return cellValue;
        }
        // 判断数据的类型
        switch (cell.getCellTypeEnum()) {
            case NUMERIC: // 数字
                if (DateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    Date date = cell.getDateCellValue();
                    if(null!=date)
                    cellValue = dateFormat.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 0) {//处理数值格式
                    cell.setCellType(CellType.STRING);
                    cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                }
                break;
            case STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: // 公式
                  cellValue = dateFormat.format(cell.getDateCellValue());
//                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: // 空值
                cellValue = null;
                break;
            case ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return cellValue;
    }
    
    public List<AnniversaryDTO> readExcel(String fileName) throws IOException {
       Workbook workbook = null;
       FileInputStream fileInputStream=null;
       String fileType = fileName.substring(fileName.indexOf(".")+1,fileName.length());
       try {
           File file = new File(fileName);
           if(!file.exists()){
               logger.error(fileName+":该文件不存在");
               return null;
           }
           fileInputStream = new FileInputStream(file);
           workbook=getWorkbook(fileInputStream,fileType);
           List<AnniversaryDTO> anniversaryDTOS = excelToAnniversary(workbook);
           return anniversaryDTOS;
       } catch (Exception e) {
           e.printStackTrace();
           logger.error("read the excel err:"+e.getMessage());
           return null;
       } finally {
           if(null!=workbook){
               workbook.close();
           }
           if(null!=fileInputStream){
               fileInputStream.close();
           }
       }
   }
~~~

- Thread.yied()
~~~
 Java线程中的Thread.yield( )方法，译为线程让步。顾名思义，就是说当一个线程使用了这个方法之后，它就会把自己CPU执行的时间让掉，
让自己或者其它的线程运行，注意是让自己或者其他线程运行，并不是单纯的让给其他线程。(使用yield()的目的是让具有相同优先级的线程之间能够适当的轮换执行)

----执行----
Ta ta = new Ta();
Tb tb = new Tb();
ta.setPriority(Thread.NORM_PRIORITY);
tb.setPriority(Thread.MAX_PRIORITY);
ta.start();
tb.start();

------
class Ta extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("TA"+i);
            Thread.yield();
        }
    }
}

class Tb extends Thread{
    public void run(){
        for (int i = 0; i <5 ; i++) {
            System.out.println("TB"+i);
            Thread.yield();
        }
    }
}

---执行结果---
TB0
TA0
TB1
TB2
TB3
TA1
TB4
TA2
TA3
TA4
~~~
- Thread 中start及run方法区别
~~~
start方法启动线程：真正使用多线程运行
run方法：普通方法，还是主线程执行
~~~

- 随机数获取
~~~
ThreadLocalRandom.current().nextInt(总量数);
~~~
~~~
  // 过滤
        List<Meata> rootMeata= meataList.stream().filter(a->a.getParentCode().equals("")).collect(Collectors.toList());
        
        //对象属性去重复
        Set<Meata> singleMeata = new TreeSet<>((o1,o2)->o1.getCode().compareTo(o2.getCode()));
        singleMeata.addAll(rootMeata);
~~~
