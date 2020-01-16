### hash 一致性算法
~~~
public class ConsistentHash {

    //虚拟节点数
    private List<String> realNodes = new ArrayList<>();
    //物理节点数
    private int virtualNodeNum = 100;
    //物理节点与虚拟节点的映射关系
    private Map<String,List<Integer>>realToVirtualNodes = new HashMap<>();

    public ConsistentHash(int virtualNodeNum) {
        super();
        this.virtualNodeNum = virtualNodeNum;
    }
    public ConsistentHash() {
        super();
    }

    // 虚拟节点排序存储 红黑树 key存放勋节点 value存储物理节点
    SortedMap<Integer,String> sortedMap = new TreeMap<>();

    public void addServer(String node){
        //物理节点
        this.realNodes.add(node);
        // 创建虚节点
        String vNode=null;
        //虚拟节点
        List<Integer> virtualNodes = new ArrayList<>(virtualNodeNum);
        this.realToVirtualNodes.put(node,virtualNodes);
        int i =0,cout=0;
        while (cout<this.virtualNodeNum){
            i++;
            vNode = node+ "&&v-"+i;
            int hash=FNV1_32_HASH.getHash(vNode);
            //解决碰撞问题
            if(!sortedMap.containsKey(hash)){
                virtualNodes.add(hash);
                //虚拟节点上环
                sortedMap.put(hash,node);
                cout++;
            }
        }
    }

    //获取服务节点
    public String getServer(String key){
        int hash = FNV1_32_HASH.getHash(key);
        //获取大于该hash值的所有节点数量
        SortedMap<Integer,String> subMap = sortedMap.tailMap(hash);
        if(subMap.isEmpty()){
            return sortedMap.get(sortedMap.firstKey());
        }else {
            return subMap.get(subMap.firstKey());
        }
    }
    // 删除服务节点
    public void removeServer(String node){
        List<Integer> virtualNodes = realToVirtualNodes.get(node);
        // 移除环上节点
        if(virtualNodes!=null) {
            for (Integer hash : virtualNodes) {
                this.sortedMap.remove(hash);
            }
        }
        //移除物理节点与虚拟节点的对应
        this.realToVirtualNodes.remove(node);
        // 移除物理节点
        this.realNodes.remove(node);
    }

    public static void main(String[] args) {
        ConsistentHash consistentHash = new ConsistentHash(1000);
        consistentHash.addServer("10.20.110.01:8080");
        consistentHash.addServer("10.20.110.02:8080");
        consistentHash.addServer("10.20.110.03:8080");
        consistentHash.addServer("10.20.110.04:8080");
        consistentHash.addServer("10.20.110.05:8080");

        for (int i = 0; i < 20; i++) {
            System.out.println("node"+i+"对应的服务器："+consistentHash.getServer("node"+i));
        }
    }
}
~~~
### 滑动窗口限流算法
~~~
 private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WindowLimiter.class);
    private LoadingCache<Long,AtomicLong> counter = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build(new CacheLoader<Long, AtomicLong>() {
                @Override
                public AtomicLong load(Long aLong) throws Exception {
                    return new AtomicLong(0);
                }
            });
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
    //限流阈值
    private long limit =20;

    public void sildeWindow(){
        logger.info("Begin to log");
        logger.error("Begin to log");
        log.info("Begin to Slide");
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                long time = System.currentTimeMillis() / 1000;
                //每秒发送随机数量请求
                int reqs = (int) (Math.random() * 5 + 1);
                counter.get(time).addAndGet(reqs);
                long nums = 0;
                //time windows 5 s
                for (int i = 0; i < 5; i++) {
                    nums += counter.get(time - 1).get();
                }
                log.info("time=" + time + ",nums=" + nums);
                if (nums > limit) {
                    log.info("限流了，nums=" + nums);
                    logger.info("限流了，nums=" + nums);
                    logger.error("限流了，nums=" + nums);
                }

            } catch (Exception ex) {
                log.error("error:" + ex);
            } finally {
                log.info("finally");
            }
        }, 5000, 1000, TimeUnit.MILLISECONDS);
    }
~~~
### 滑动窗口算法实现2
~~~
 public static void main(String[] args) {
       long[] arr = {1,2,3,4};
        System.out.println("数组中任意的k个元素之和的最大值："+getSlidingSum(arr,3));
    }

    static long getSlidingSum(long[] arr,int k){

        if(arr==null||arr.length< k){
            return -1;
        }
        long maxSum=0;
        for(int i=0;i<k;i++){
            maxSum+=arr[i];
        }

        long sum=maxSum;
        for (int n=k;n<arr.length;n++){
            sum+=arr[n]-arr[n-k];
            maxSum=Math.max(sum,maxSum);
        }
        return maxSum;
    }
~~~
### 旋转数最小值（二分查找法）
~~~
static int getMin(int[] arr){
        int  low=0,height=arr.length-1;
        while (low<height) {
            if (arr[low] < arr[height]) return arr[low];
            int mind = (low+height)/2;
            if(arr[mind]>arr[height]){
                low=mind+1;
            }else {
                height=mind;
            }
        }
        return arr[low];
    }
~~~
