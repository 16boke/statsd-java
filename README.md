# statsd-java
statsd的java版，实现向statsd发送各种类型的指标，包括count、gauge、set、time。
其中目前有两个实例：
1、获取每秒钟codis的dashboard的OPS数，可以很清楚的看到项目中redis集群的OPS数，默认每隔10秒存储一次
2、获取北京、上海、广州等地的PM2.5的值。这个参考网上python版的实现，采用java来重新实现了一次。每隔10分钟获取一次。
截图如下：
![image](https://github.com/16boke/statsd-java/blob/master/images/QQ截图20160616183710.png)