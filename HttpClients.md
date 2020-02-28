### HttpClient 使用

### 版本
~~~
 <dependency>
     <groupId>org.apache.httpcomponents</groupId>
     <artifactId>httpclient</artifactId>
     <version>4.5.2</version>
 </dependency>
 <dependency>
     <groupId>org.apache.httpcomponents</groupId>
     <artifactId>httpmime</artifactId>
     <version>4.5.5</version>
 </dependency>
~~~
### 工具类
~~~
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClients.class);

    private static final int SUCCESS_CODE=200;
    /**
    * @decription get请求
    * @param url 接口地址
    * @return java.lang.String 返回json字符串
    * @author Dawn
    * @date 2019/8/6
    */
    public static String sendGet(String url) throws Exception {
        CloseableHttpClient httpClient=null;
        CloseableHttpResponse response = null;
        String result = "";
        try{
            //保持cookie
            CookieStore cookieStore = new BasicCookieStore();
            //创建 httpClient
            httpClient=HttpClients.custom().setDefaultCookieStore(cookieStore).build();

            HttpGet getMethod = new HttpGet(url);
            getMethod.setHeader("Content-Type","application/json;charset=UTF-8");
            response= httpClient.execute(getMethod);
            int statusCode = response.getStatusLine().getStatusCode();
            if(SUCCESS_CODE==statusCode){
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity,"UTF-8");
            }else {
              logger.error("HttpClient get 请求失败,StatusCode:"+statusCode);
              throw new Exception("HttpClient get 请求失败,StatusCode:"+statusCode);
            }
        }catch (Exception ex){
             logger.error("HttpClient get 请求失败："+ex.getMessage());
             throw ex;
        }finally {
            response.close();
        }

        return result;
    }

    /**
    * @decription post 请求
    * @param url 接口服务地址
    * @return java.lang.String
    * @author Dawn
    * @date 2019/8/6
    */
    public static  List<Cookie> getCookies(String url) throws Exception {

        CloseableHttpClient httpClient=null;
        CloseableHttpResponse response = null;
        List<Cookie>  cookies=null;
        try{
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpPost postMethod = new HttpPost(url);
            postMethod.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            response = httpClient.execute(postMethod);
            int statusCode = response.getStatusLine().getStatusCode();
            if(SUCCESS_CODE==statusCode){
                // 获取cookie
                cookies = cookieStore.getCookies();
            }else {
                logger.error("HttpClient 公众号登录失败");
                throw new Exception("HttpClient 公众号登录失败,StatusCode:"+statusCode);
            }
        }catch (Exception ex){
            logger.error("HttpClient 公众号登录失败"+ex);
            throw ex;
        }finally {
            response.close();
            httpClient.close();
        }
        return cookies;
    }

    public static String sendPost(String url,String loginUrl) throws Exception {
        // 获取cookie
        List<Cookie> cookies = getCookies(loginUrl);
        CloseableHttpClient httpClient =null;
        CloseableHttpResponse response = null;
        String result = "";
        try{
            if(cookies.size()<=0){
               throw new Exception("登录异常");
            }
            CookieStore cookieStore = new BasicCookieStore();
            Cookie cookieItem = cookies.get(0);
            BasicClientCookie cookie = new BasicClientCookie(cookieItem.getName(),cookieItem.getValue());
            cookie.setVersion(cookieItem.getVersion());
            cookie.setDomain(cookieItem.getDomain());
            cookieStore.addCookie(cookie);
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpPost postMethod = new HttpPost(url);
            postMethod.setHeader("Content-Type","application/json;charset=UTF-8");
            response = httpClient.execute(postMethod);
            int statusCode = response.getStatusLine().getStatusCode();
            if(SUCCESS_CODE==statusCode){
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity,"UTF-8");
            }else {
                if(statusCode==302){
                    Header header= response.getLastHeader("location");
                    System.err.println(header.getValue());
                }
                logger.error("HttpClient pos 请求失败,StatusCode:"+statusCode);
                throw new Exception("HttpClient post 请求失败,StatusCode:"+statusCode);
            }
        }catch (Exception ex){
            logger.error("HttpClient post 请求失败"+ex);
            throw ex;
        }finally {
            response.close();
            httpClient.close();
        }
        return result;
    }
    /**
    * @decription 获取json字符某个属性值
    * @param data json字符串
    * @param key  json字符串的key属性
    * @return java.lang.String 返回值
    * @author Dawn
    * @date 2019/8/6
    */
    public static String getQueryValueByKey(String data,String key){
        if(StringUtils.isEmpty(data)){
            return null;
        }
        if(!data.contains(key)){
            return null;
        }
        JSONObject jsonObject = JSONObject.fromObject(data);
        return jsonObject.getString(key);
    }
}

public static InvokeResult sendImageMsg(String uri,String filePath,String fileName) throws IOException {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost post = new HttpPost(uri);
            HttpEntity dataEntity = getMultiFileEntity(filePath,fileName);//File文件格式上传
            post.setEntity(dataEntity);
            response = httpclient.execute(post);
            return InvokeResult.success( EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return InvokeResult.failure(e.getMessage());
        } finally {
            if(response!=null){
                response.close();
            }
            if(httpclient!=null){
                httpclient.close();
            }
        }
    }

    /**
     * File文件格式上传
     */
    public static HttpEntity getMultiFileEntity(String filePath,String fileName) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File file = new File(filePath);
        builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, fileName);
        return builder.build();
    }

    /**
     * File文件格式上传（缺省）
     */
    public HttpEntity getMultiDefaultFileEntity(String filePath) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File file = new File(filePath);
        builder.addBinaryBody("file", file);
        return builder.build();
    }

    /**
     * byte数组格式上传
     */
    public HttpEntity getMultiArrayEntity(String filePath) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        byte[] byteArr = getFileArr(filePath);
        builder.addBinaryBody("file", byteArr, ContentType.DEFAULT_BINARY, "photoArr.jpg");
        return builder.build();
    }

    /**
     * byte数组格式上传（缺省）
     */
    public HttpEntity getMultiDefaultArrayEntity(String filePath) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        byte[] byteArr = getFileArr(filePath);
        builder.addBinaryBody("file", byteArr);
        return builder.build();
    }

    public byte[] getFileArr(String filePath) {
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     *  文件流格式上传
     * @param filePath 文件路径
     * @param fileName "photoStream.jpg"
     * @return getMultiStreamEntity
     * @author Dawn
     * @date 2020/2/28
     */
    public HttpEntity getMultiStreamEntity(String filePath,String fileName) throws FileNotFoundException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File file = new File(filePath);
        InputStream stream = new FileInputStream(file);
        builder.addBinaryBody("file", stream, ContentType.DEFAULT_BINARY, fileName);
        return builder.build();
    }

    /**
     * 文件流格式上传（缺省）
     */
    public HttpEntity getMultiDefaultStreamEntity(String filePath) throws FileNotFoundException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File file = new File(filePath);
        InputStream stream = new FileInputStream(file);
        builder.addBinaryBody("file", stream);
        return builder.build();
    }
~~~

### InvokeResult
~~~
public class InvokeResult<T> implements Serializable {

    private static final long serialVersionUID = -4181103794622657034L;
    /**
     * 返回结果
     * */
    private T data;

    /**
     * 异常消息
     * */
    private String errorMessage;

    /**
     * 是否有异常
     * */
    private boolean hasErrors;

    /**
     * 是否成功
     * */
    private boolean success;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static InvokeResult success(){
       InvokeResult invokeResult = new InvokeResult();
       invokeResult.success=true;
       invokeResult.hasErrors= !invokeResult.success;
       return invokeResult;
    }

    public static <T> InvokeResult<T> success(T data){
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.data = data;
        invokeResult.success = true;
        invokeResult.hasErrors=false;
        return invokeResult;
    }

    public static InvokeResult failure(String msg){
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.hasErrors= true;
        invokeResult.success = false;
        invokeResult.errorMessage=msg;
        return invokeResult;
    }

    public static <T> InvokeResult<T> failure(T data){
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.data = data;
        invokeResult.hasErrors = true;
        invokeResult.success = false;
        return invokeResult;
    }
~~~
