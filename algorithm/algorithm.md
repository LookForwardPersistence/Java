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