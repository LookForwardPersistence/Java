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
