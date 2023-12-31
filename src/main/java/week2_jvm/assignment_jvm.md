# JVM 虚拟机论述题

## 题目 01- 请你用自己的语言向我介绍 Java 运行时数据区（内存区域）
堆、虚拟机栈、本地方法栈、方法区（永久代、元空间）、运行时常量池（字符串常量池）、直接内存
- 堆是jvm保存创建对象的区域分为年轻代，老年代。年轻代又分为伊甸园区和两个survivor区。除此之外还有java 8前有永久代，8之后是metaspace
- 虚拟机栈是每个方法都会创建的栈帧用来存放方法出口，局部变量等等
- 方法区是用来存放类信息，常量,静态变量等，8之前是永久代，之后是metaspace
- 运行时常量池是用来存放常量，字符串常量池本质上是哈希表
- 直接内存不属于运行时数据区，它用于提高I/O处理速度，但分配的效率很低

为什么堆内存要分年轻代和老年代？
- 因为一般对象初始在年轻代中的伊甸园区，经过复制算法的15次拷贝才会放入老年代。
  老年代和年轻代采用的gc策略不同，分区的话会让gc更精细化控制以及优化gc并提高效率。

## 题目 02- 描述一个 Java 对象的生命周期
解释一个对象的创建过程
- jvm会检查该对象是否已被加载
- 分配内存
- 设置对象头
- 调用构造方法
解释一个对象的内存分配
- 新对象通常在伊甸园区诞生，一般很快就会被垃圾回收，如果经历了15次young gc还存活就会被拷贝到老年代。
解释一个对象的销毁过程
- 一个对象会在没有引用的时候被GC标记两次后销毁，在第一次标记后它有一次机会重新获取引用从而避开被销毁。gc的算法通常有标记清除，拷贝等等
对象的 2 种访问方式是什么？
- 通过引用
- 通过反射机制
为什么需要内存担保？
- 因为大的对象在新生代放不下或者在young gc后对象太多没有办法进入survivor区，这个时候就需要将对象放入老年代

## 题目 03- 垃圾收集算法有哪些？垃圾收集器有哪些？他们的特点是什么？
ParNew 收集器
- 低延迟
- 用于年轻代
ParallelScavenge 收集器
- 注重吞吐量
- 用于年轻代
- 主要和CMS一起使用
ParallelOld 收集器
- 注重吞吐量
- 用于老年代
CMS 收集器
- 可以设置停顿STW时间
- 分阶段收集
- 低延迟
- 会产生内存碎片
G1 收集器
- 同时解决吞吐量和低延迟
- jdk 9之后的默认垃圾收集器
- 主要是针对大内存的服务器应用
- 基于region的内存布局
- 分阶段收集
- 缺点是会产生内存碎片