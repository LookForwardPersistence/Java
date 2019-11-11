Excel大表读取

- pom引入
~~~
 <dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>4.0.1</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>4.0.1</version>
</dependency>
<dependency>
    <groupId>xerces</groupId>
    <artifactId>xercesImpl</artifactId>
    <version>2.9.0</version>
</dependency>
~~~
- 抽象类
~~~
public abstract class ExcelAbastract extends DefaultHandler {
    private SharedStringsTable sst;
    private String lastContents;
    private boolean nextIsString;

    private int curRow = 0;
    private String curCellName = "";

    /**
     * 读取当前行的数据。key是单元格名称如A1,value是单元格中的值。如果单元格式空,则没有数据。
     */
    private Map<String, String> rowValueMap = new HashMap<>();

    /**
     * 处理单行数据的回调方法。
     *
     * @param curRow 当前行号
     * @param rowValueMap 当前行的值
     * @throws SQLException
     */
    public abstract void optRows(int curRow, Map<String, String> rowValueMap);

    /**
     * 读取Excel指定sheet页的数据。
     *
     * @param filePath 文件路径
     * @param sheetNum sheet页编号.从1开始。
     * @throws Exception
     */
    public void readOneSheet(String filePath, int sheetNum) throws Exception {
        OPCPackage pkg = OPCPackage.open(filePath);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = getSheetParser(sst);

        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet2 = r.getSheet("rId" + sheetNum);
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
        pkg.close();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        // c => 单元格
        if (name.equals("c")) {

            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
            String cellType = attributes.getValue("t");
            if (cellType != null && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }

        // 置空
        lastContents = "";

        /**
         * 记录当前读取单元格的名称
         */
        String cellName = attributes.getValue("r");
        if (cellName != null && !cellName.isEmpty()) {
            curCellName = cellName;
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
            } catch (Exception e) {

            }
        }

        // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
        // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
        if (name.equals("v")) {
            String value = lastContents.trim();
            value = value.equals("") ? " " : value;
            rowValueMap.put(curCellName, value);
        } else {
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                optRows(curRow, rowValueMap);
                rowValueMap.clear();
                curRow++;
            }
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }

    /**
     * 获取单个sheet页的xml解析器。
     * @param sst
     * @return
     * @throws SAXException
     */
    private XMLReader getSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }
}
~~~
- 实现类
~~~
public class ExcelAbastractImpl extends ExcelAbastract {
    /**
     * 提取列名称的正则表达式
     */
    private static final String DISTILL_COLUMN_REG = "^([A-Z]{1,})";

    /**
     * 读取excel的每一行记录。map的key是列号(A、B、C...), value是单元格的值。如果单元格是空，则没有值。
     */
    private List<Map<String, String>> dataList = new ArrayList<>();

    @Override
    public void optRows(int curRow, Map<String, String> rowValueMap) {

        Map<String, String> dataMap = new HashMap<>();
        rowValueMap.forEach((k,v)->dataMap.put(removeNum(k), v));
        dataList.add(dataMap);
    }

    /**
     * 日期数字转换为字符串。
     *
     * @param dateNum excel中存储日期的数字
     * @return 格式化后的字符串形式
     */
    public static String dateNum2Str(String dateNum) {
        Date date = HSSFDateUtil.getJavaDate(Double.parseDouble(dateNum));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 删除单元格名称中的数字，只保留列号。
     * @param cellName 单元格名称。如:A1
     * @return 列号。如：A
     */
    private String removeNum(String cellName) {
        Pattern pattern = Pattern.compile(DISTILL_COLUMN_REG);
        Matcher m = pattern.matcher(cellName);
        if (m.find()) {
            return m.group(1);
        }

        return "";
    }

    public List<Map<String, String>> getDataList() {
        return dataList;
    }
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
    public final String getDate(String num){
        if(num.isEmpty()){
            return "";
        }
        int wholeDays = Integer.parseInt(num);
        Calendar calendar = new GregorianCalendar();
        setCalendar(calendar, wholeDays, 0, false);
        return simpleDateFormat.format(calendar.getTime());
    }
    private static void setCalendar(Calendar calendar, int wholeDays,
                                    int millisecondsInDay, boolean use1904windowing) {
        int startYear = 1900;
        int dayAdjust = -1; // Excel thinks 2/29/1900 is a valid date, which it isn't
        if (use1904windowing) {
            startYear = 1904;
            dayAdjust = 1; // 1904 date windowing uses 1/2/1904 as the first day
        }
        else if (wholeDays < 61) {
            // Date is prior to 3/1/1900, so adjust because Excel thinks 2/29/1900 exists
            // If Excel date == 2/29/1900, will become 3/1/1900 in Java representation
            dayAdjust = 0;
        }
        calendar.set(startYear,0, wholeDays + dayAdjust, 0, 0, 0);
        calendar.set(GregorianCalendar.MILLISECOND, millisecondsInDay);
    }
    public static boolean isNumberic(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
~~~
- 示例
~~~
  ExcelAbastractImpl excelAbastract = new ExcelAbastractImpl();
  // 读取sheet第一页
  excelAbastract.readOneSheet(filePath,1);
  //获取数据
  excelAbastract.getDataList();
~~~
