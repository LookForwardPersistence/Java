### HashMap 源码解析
~~~
查看类的成员方法及属性
alt+7
~~~
- HashMap底层以什么方式存储？


### ConcurrentHasp
可以保证线程安全，cas sychrozied

### ConcurrentSkipListMap 跳表查找
使用场景：key 有序 线程安全

### volatile 
~~~
先写保证后序线程可以读到（可见性问题）
~~~
### synchrozied同步关键字
~~~
解决线程同步问题
~~~

### 锁降级
~~~
释放读锁，获得写锁（独占），获取读锁再释放写锁
~~~

### Java 线程池
~~~
Java通过Executors提供四种线程池，分别为：
newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
~~~

### ForkJoin框架
- ForkJoin使用
~~~
public class CounterForkJoin {

    static ForkJoinPool forkJoinPool = new ForkJoinPool(3,
            ForkJoinPool.defaultForkJoinWorkerThreadFactory,
            null,
            true);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Task task= new Task(1,6);//1到4累加（不包含5）
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(task);
        int result= forkJoinTask.get();
        System.err.println("结果："+result);
    }
    static class Task extends RecursiveTask<Integer>{
        private int start;
        private int end;
        public Task(int start,int end){
            this.start=start;
            this.end=end;
        }
        @Override
        protected Integer compute() {
            //计算任务大小
            int count = end - start;

            int result=0;
            if(count<=100){
                for (int i = start; i <end ; i++) {
                    result+=i;
                }
                return result;
            }
            //继续分解任务
            int x = (end+start)/2;
            Task task=new Task(start,x);
            task.fork();

            Task task1=new Task(x,end);
            task1.fork();

            //固定写法
            result+=task.join();
            result+=task1.join();
            return result;
        }
    }
}
~~~
- ForkJoin原理实现
~~~
public static void main(String[] args) throws ExecutionException, InterruptedException {
        long begin = System.currentTimeMillis();
        //启动线程池，执行分组计算任务
        ExecutorService pool = Executors.newFixedThreadPool(4);
        // 收集任务执行结果
        List<Future> futures = new ArrayList<>();

        // 当前需要计算从1到10 0000 0000（10亿）
        int num = 100000;
        //设置分组大小
        int groupSize = 100;
        int groupCount = (num-1)/groupSize+1; //5
        for (int index = 0; index < groupCount; index++) {
            int leftIndex = groupSize*index;     //0 1 2 3 4
            int rightIndex = groupSize*(index+1);//1 2 3 4 5
                                                 //0 1 2 3 4

            Future<Integer> future = pool.submit(new Task(leftIndex,rightIndex));
            futures.add(future);
        }

        // 将最后一项失算结果加入
        int lfIndex = groupSize*groupCount;
        int rtIndex = num+1;
        Future<Integer> future = pool.submit(new Task(lfIndex,rtIndex));
        futures.add(future);
        int rl=0;
       for (Future<Integer> item:futures){
            rl+=item.get();
       }
       int t=0;
        for (int i = 1; i <100000001 ; i++) {
            t+=i;
        }
        System.out.println("耗时："+(System.currentTimeMillis()-begin));
        System.out.println("t="+t);
        System.out.println(rl);
    }


    static class Task implements Callable<Integer>{
        private int start;
        private int end;
        public Task(int start,int end){
            this.start = start;
            this.end= end;
        }
        @Override
        public Integer call() throws Exception {
            int result = 0;
            for (int i = start; i <end ; i++) {
                result+=i;
            }
            System.err.println(result);
            return result;
        }
    }
~~~
