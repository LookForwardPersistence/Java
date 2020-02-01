### 随机休眠
~~~
  Random random = new Random();
  //随机生成一个介于0到150（不包含150）的值
  int timeOut = random.nextInt(150);
  //随机休眠
  Thread.sleep(timeOut);
~~~
