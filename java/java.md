### 随机休眠
~~~
  Random random = new Random();
  //随机生成一个介于0到150（不包含150）的值
  int timeOut = random.nextInt(150);
  //随机休眠
  Thread.sleep(timeOut);
~~~
### RxJava 
~~~
public static void main(String[] args) throws InterruptedException {
        Single.just(new Action1().init())
                .subscribeOn(Schedulers.io())
                .subscribe(RxjavaApplication::myPrint);

        Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "ok";
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(RxjavaApplication::isSuccess);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        observerList();
        observerList2();
    }
    public static class Action1{
        public String init(){return "时间发布！";}

    }

    public static void myPrint(Object value){
        System.out.printf("[线程 %s %s]\n",Thread.currentThread().getName(),value);
    }
    public static void isSuccess(Object value){
        if("ok".equals(value)){
            System.out.println("执行通过！！！");
        }else {
            System.err.println("执行不通过！！！");
        }
    }

    //多线程
    public static void observerList() throws InterruptedException {
        List<Integer> values = Arrays.asList(1,2,3,4,5);
        Observable.from(values)
                .subscribe(RxjavaApplication::myPrint);
        Thread.sleep(100);
    }

    //多线程
    public static void observerList2() throws InterruptedException {
        List<Integer> values = Arrays.asList(1,2,3,4,5);
        Observable.from(values)
                .subscribe(
                        next->{
                             if(next>2){
                                 throw new IllegalArgumentException("非法参数！！！");
                             }
                            System.out.println("处理数据："+next);
                        },
                        error->{
                            System.err.println("执行降级！！！");
                         },
                        ()->{
                            System.out.println("执行完毕！！！");
                        }
                );
    }
~~~

### 定时器
~~~
 /**
     *  定时器,转入执行时间点
     * @param actionTime 如2020-03-05 07:30:00
     * @return release
     * @date 2020/3/4
     */
     public static boolean release(String actionTime) throws InterruptedException, ParseException {
        boolean releaseFlag=false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Date totalTime= dateFormat.parse(actionTime);
        long actTime=dateFormat.parse(actionTime).getTime();
        while(true){
            Date currentDateTime=new Date();
            System.err.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(currentDateTime));
            Thread.sleep(1000);//休眠1秒
//            totalTime = new Date(totalTime.getTime()-1000);
            if(System.currentTimeMillis()>=actTime){
                releaseFlag=true;
                break;
            }
        }
        return releaseFlag;
    }
    
    
     /**
    *  服务器网络是否通验证
    * @param ipAddress ip地址
    * @return ping
    * @date 2020/3/4
    */
   public static boolean ping(String ipAddress) throws IOException {
       int timeout=5000;
       boolean status = InetAddress.getByName(ipAddress).isReachable(timeout);
       return status;
   }

   /**
    *  应用服务是否存活
    * @param uri http://ip:port
    * @return isSurvive
    * @date 2020/3/4
    */
   public static boolean isSurvive(String uri) {
       boolean isSurvive=false;
       if(StringUtils.isEmpty(uri)){
           isSurvive=false;
       }
       String[] uriArr=uri.split("//");
       if(uriArr.length>1){
          String[] ipArr= uriArr[1].split(":");
          if(ipArr.length>1){
              String ip=ipArr[0];
              int port= Integer.parseInt(ipArr[1]);
              Socket socket = new Socket();
              try {
                  socket.connect(new InetSocketAddress(ip,port));
                  isSurvive=true;
              } catch (IOException e) {
//                 throw new Exception(e.getMessage());
                  isSurvive=false;
              }finally {
                  try {
                      socket.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          }else {
              isSurvive=false;
          }
       }else {
           isSurvive=false;
       }
       return isSurvive;
   }
~~~
- json 字符串转对象
~~~
     Gson gson = new Gson();
     gson.fromJson(jsonString,clasz)
~~~
- list转为数组
~~~
 int size = command.getAccounts().size();
     messageCommand.setAccounts((String[]) command.getAccounts().toArray(new String[size]));
~~~
- 统计执行时间
~~~
 StopWatch watch = new StopWatch();
  watch.start(); 
  //待统计执行方法
  watch.stop();
  System.err.println(watch.getTotalTimeMillis());
~~~
- 异步执行
~~~

-----------------全局变量--------------------------------
 ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("tchat-message-pool-%d").build();
    ExecutorService pool = new ThreadPoolExecutor(5, 200,0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
            
 --------------业务代码---------------------
   //异步执行日志记录
     pool.execute(new executeSaveLog(bodyCommand.getCid(),result.getMsg(),command));
 
 --------------执行类--------------------
    class executeSaveLog implements Runnable{

        private String cid;
        private String msg;
        private TChatMessageCommand command;

        public executeSaveLog() {
        }

        public executeSaveLog(String cid, String msg, TChatMessageCommand command) {
            this.cid = cid;
            this.msg = msg;
            this.command = command;
        }

        @Override
        public void run() {
            try{
                //保存消息记录
                saveMessageLog(cid,msg,command);
                //通知消息系统管理员 todo
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
~~~
