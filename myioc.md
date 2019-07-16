### IOC(Inversion of controller)
#### 手写实现
~~~ 
/**
 * 1.实例化bean
 * 2.保存bean
 * 3.提供bean
 *
 *
 * Created by Dawn on 2019/7/16.
 */
public class IocContainer {
    Map<String,Object> beans = new HashMap<String,Object>();

    public Object getBean(String beanId){
        return beans.get(beanId);
    }

    public void setBeans(Class<?> clazz,String beanId,String... paramsBeanIds){
        //组装构造方法所需要的参数
        Object[] paramValues = new Object[paramsBeanIds.length];
        for (int i = 0; i <paramsBeanIds.length ; i++) {
            paramValues[i]=beans.get(paramsBeanIds[i]);
        }

        //调用构造方法实例化的Bean
        Object bean=null;
        for (Constructor<?>constructor:clazz.getConstructors()) {
            try {
                bean=constructor.newInstance(paramValues);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if(bean==null){
            throw new RuntimeException("没有课实例化的bean");
        }
        //实例化bean放入beans
        beans.put(beanId,bean);
    }
}
~~~
