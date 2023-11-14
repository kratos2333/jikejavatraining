# Tomcat 调优
## maxThreads：最大线程数
- 每一次HTTP请求到达Web服务，Tomcat都会创建一个线程来处理该请求。 
- 最大线程数决定了Web服务容器可以同时处理多少个请求，默认值是200。
- server.tomcat.threads.max

## accept-count：最大等待连接数
- 当调用HTTP请求数达到Tomcat的最大线程数时，还有新的请求进来，这时Tomcat会将该剩余请 求放到等待队列中 
- acceptCount就是指队列能够接受的最大的等待连接数 
- 默认值是100，如果等待队列超了，新的请求会被拒绝（connection refused）
- server.tomcat.accept-count

## Max Connections：最大连接数

最大连接数是指在同一时间内，Tomcat能够接受的最大连接数。如果设置为-1，则表示不限制

默认值：

对BIO模式，默认值是Max Threads；如果使用定制的Executor执行器，哪默认值将是执行器 中Max Threads的值。 对NIO模式，Max Connections 默认值是10000 Max Connections和accept-count关系：

当连接数达到最大值Max Connections后系统会继续接收连接，但不会超过acceptCount限制
- server.tomcat.max-connections