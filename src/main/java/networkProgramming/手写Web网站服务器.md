# 手写网站服务器案例

**需求：模拟基于Http协议的网站服务器，使用浏览器访问自己编写的服务端程序。**然后压测看一看



## 案例分析

- 准备测试页面及图片，存放在web文件夹
- 模拟服务器端（ServerSocket）使用浏览器访问，查看页面效果
- 本案例涉及并发编程与网络编程，我们先来观察并发编程部分




## 01-服务器实现：单线程版本

**不足：**

- 一次只能处理一个HTTP请求，处理完服务就停止了，不支持多任务【既可以响应html网页，也可以响应图片】
- 因为是单线程程序，完全没有性能可言
- 端口硬编码
- ...

```java
/**
 * 单线程版本
 */
public class BsServer01 {
    public static void main(String[] args) throws IOException {
        System.out.println("服务器 启动.....  ");
        System.out.println("开启端口 : 9999.....  ");
        // 1. 创建服务端ServerSocket
        ServerSocket serverSocket = new ServerSocket(9999);
        // 2. 循环接收,建立连接
        Socket accept = serverSocket.accept();
        /*
         *3. socket对象进行读写操作
         */
        //转换流，读取浏览器请求第一行
        BufferedReader readWb = new BufferedReader(new InputStreamReader(accept.getInputStream()));
        String requst = readWb.readLine();//包含URL地址
        //取出请求资源的路径
        String[] strArr = requst.split(" ");
        System.out.println(Arrays.toString(strArr));
        String path = strArr[1].substring(1);//截取请求path
        System.out.println(path);

        //----前提请求的Path与文件相对路径的Path是相同的
        FileInputStream fis = new FileInputStream(path);
        System.out.println(fis);
        byte[] bytes= new byte[1024];
        int len = 0 ;

        //向浏览器 回写数据
        OutputStream out = accept.getOutputStream();
        out.write("HTTP/1.1 200 OK\r\n".getBytes());
        out.write("Content-Type:text/html\r\n".getBytes());
        out.write("\r\n".getBytes());
        while((len = fis.read(bytes))!=-1){
            out.write(bytes,0,len);
        }

        fis.close();
        out.close();
        readWb.close();
        accept.close();

    }
}

```

## 02-服务器实现：多线程版本

文件上传的案例中，服务器只能为客户端服务器一次，之后服务器端程序就会结束。而我们必须做到让服务器程序不能结束，时时刻刻都要为客户端服务。而且同时可以为多个客户端提供服务器，要做到一个客户端请求就要开启一个新线程。

**不足：**

- 频繁的创建线程会增加系统资源开销。为每个请求建立新的线程，线程无法复用这种操作代价过高。
- 端口硬编码
- ...

```java
/**
 * 多线程版本
 */
public class BsServer02 {
    public static void main(String[] args) throws IOException {
        System.out.println("服务器 启动.....  ");
        System.out.println("开启端口 : 9999.....  ");
        // 1. 创建服务端ServerSocket
        ServerSocket serverSocket = new ServerSocket(9999);
        // 2. 循环接收,建立连接
        while (true) {
            Socket accept = serverSocket.accept();
            /*
            3. socket对象交给子线程处理,进行读写操作
            Runnable接口中,只有一个run方法,使用lambda表达式简化格式
            */
            new Thread(() -> {
                try{
                    /*
                     *socket对象进行读写操作
                     */
                    //转换流,读取浏览器请求第一行
                    BufferedReader readWb = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                    String requst = readWb.readLine();
                    //取出请求资源的路径
                    String[] strArr = requst.split(" ");
                    System.out.println(Arrays.toString(strArr));
                    String path = strArr[1].substring(1);
                    System.out.println(path);

                    //----
                    FileInputStream fis = new FileInputStream(path);
                    System.out.println(fis);
                    byte[] bytes= new byte[1024];
                    int len = 0 ;

                    //向浏览器 回写数据
                    OutputStream out = accept.getOutputStream();
                    out.write("HTTP/1.1 200 OK\r\n".getBytes());
                    out.write("Content-Type:text/html\r\n".getBytes());
                    out.write("\r\n".getBytes());
                    while((len = fis.read(bytes))!=-1){
                        out.write(bytes,0,len);
                    }

                    fis.close();
                    out.close();
                    readWb.close();
                    accept.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
```

## 03-服务器实现：线程池版本

频繁的创建线程会增加系统资源的开销，为降低资源开销，可以利用线程池复用线程进行优化。

**不足：**

- 端口硬编码
- ...

```java
/**
 * 线程池版本
 */
public class BsServer03 {
    public static void main(String[] args) throws IOException {
        System.out.println("服务器 启动.....  ");
        System.out.println("开启端口 : 9999.....  ");
        // 创建服务端ServerSocket
        ServerSocket serverSocket = new ServerSocket(9999);
        //创建10个线程的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(200);

        while (true) {
            Socket accept = serverSocket.accept();
            //提交线程执行的任务
            executorService.submit(()->{
                try{
                    /*
                     *socket对象进行读写操作
                     */
                    //转换流,读取浏览器请求第一行
                    BufferedReader readWb = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                    String requst = readWb.readLine();
                    //取出请求资源的路径
                    String[] strArr = requst.split(" ");
                    System.out.println(Arrays.toString(strArr));
                    String path = strArr[1].substring(1);
                    System.out.println(path);

                    //----
                    FileInputStream fis = new FileInputStream(path);
                    System.out.println(fis);
                    byte[] bytes= new byte[1024];
                    int len = 0 ;

                    //向浏览器 回写数据
                    OutputStream out = accept.getOutputStream();
                    out.write("HTTP/1.1 200 OK\r\n".getBytes());
                    out.write("Content-Type:text/html\r\n".getBytes());
                    out.write("\r\n".getBytes());
                    while((len = fis.read(bytes))!=-1){
                        out.write(bytes,0,len);
                    }

                    fis.close();
                    out.close();
                    readWb.close();
                    accept.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
```

## 04-服务器实现：配置文件设置端口号

上面案例中，接口存在硬编码，如果要更换端口号只能修改源代码，程序的维护性很差。

**解决办法**：将端口号写在配置文件（xml）中，需要更换端口号，可以直接修改配置文件。当然，线程池的配置信息也可以加入到配置文件中来，这里就不做实现了。

在src目录下创建文件server.xml：

```xml
<?xml version="1.0" encoding="utf-8"?>
<server>
    <port>8080</port>
</server>
```

读取xml中的端口号：

```java
/**
 * 线程池 + 配置文件读取端口
 */
public class BsServer04 {
    public static void main(String[] args) throws DocumentException, IOException {
        //初始化端口
        //读取配置文件Server.xml中的端口号
        InputStream in = BsServer04.class.getClassLoader().getResourceAsStream("server.xml");
        //获取配置文件输入流
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(in);
        //使用SAXReader + XPath读取端口配置
        Element portEle = (Element) doc.selectSingleNode("//port");
        String port = portEle.getText();

        System.out.println("服务器 启动.....  ");
        System.out.println("开启端口 : " + Integer.valueOf(port));
        //启动web服务
        ServerSocket server = new ServerSocket(Integer.valueOf(port));
        //线程池
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        while(true){
            Socket socket = server.accept();
            //提交一个任务
            executorService.submit(()->{
                try{
                    //转换流,读取浏览器请求第一行
                    BufferedReader readWb = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String requst = readWb.readLine();
                    //取出请求资源的路径
                    String[] strArr = requst.split(" ");
                    System.out.println(Arrays.toString(strArr));
                    String path = strArr[1].substring(1);
                    System.out.println(path);

                    FileInputStream fis = new FileInputStream(path);
                    System.out.println(fis);
                    byte[] bytes= new byte[1024];
                    int len = 0 ;

                    //向浏览器 回写数据
                    OutputStream out = socket.getOutputStream();
                    out.write("HTTP/1.1 200 OK\r\n".getBytes());
                    out.write("Content-Type:text/html\r\n".getBytes());
                    out.write("\r\n".getBytes());
                    while((len = fis.read(bytes))!=-1){
                        out.write(bytes,0,len);
                    }
                    fis.close();
                    out.close();
                    readWb.close();
                    socket.close();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            });
        }
    }
}
```

导入POM依赖

```xml
<dependency>
    <groupId>org.dom4j</groupId>
    <artifactId>dom4j</artifactId>
    <version>2.1.3</version>
</dependency>
<dependency>
    <groupId>jaxen</groupId>
    <artifactId>jaxen</artifactId>
    <version>1.1.6</version>
</dependency>
```

