线程与线程池分享
Java 中的线程基础
Java 中两种实现多线程的两种方式
a.	继承Thread类
b.	实现Runnable接口
两者都需要实先run 方法，继承Thread 开销比较大，且是单继承，所以尽量使用Runnable 接口
多线程的6中状态
NEW 新建状态
RUNNABLE 运行(可运行)状态
BLOCKED 阻塞状态
TIMED_WAITING 休眠状态
WAITING 等待状态
TERMINATED 终止状态
注意：不同状态下 执行的 任务是不同的
查看jvm 的线程
方法名	作 用
activeCount()	返回此线程组中活动线程的估计数
acti veGroupCount()	返回此线程组中活动线程组的估计数
enumerate(Threadn list, boolean recurse)	把此线程组屮的所有活动线程复制到指定数组中
enumerate(ThreadGroupfl list, boolean recurse)	把对此线程组中的所有活动子组的引用复制到指定数组屮
getName()	返回此线程组的名称
getParent()	返回此线程组的父线程组

查看修改线程名称
方法名	作 用	方法名	作 用
getNameO	返回当前线程的名字	getldQ	返回当前线程的标识符
setName()	设置当前线程的名字	getThreadGroup	获得当前线程所在的线程组

查看线程的优先级

新建 线程的优先级 跟 创建线程的优先级相同，Java虚拟机将线程的优先级分成了 10级，从MIN_PRIORITY (1)到MAX_PRIORITY (10)。对于main 线程，它的优先级是NORM_PRJORITY (5)。最好不要修改线程的默认优先级。如果程序中有儿个高优先级 的线程，则调度器在选择要运行的线程时，总是优先选择这儿个线程。如果程序中还有低优先级的线程，就会 出现“饥饿”状态。即低优先级的线程基本不会运行。
方法（属性）名	作 用
MAX PRIORITY	线程可以具有的最高优先级
MIN PRIORITY	线程可以具有的最低优先级
NORM PRIORITY	分配给线程的默认优先级
getPriority()	获得线程的优先级
setPriority(int newPriority)	修改线程的优先级

使用守护线程

方法名	作 用
isDaemon()	测试一个线程是否为守护线程
setDaemon(boolean on)	将一个线程标记为守护线程或用户线程

一定要在线程运行以前也就是运行Thread类 的 start()前 设置一个线程为守护线程
只要当前JVM实例中尚存在任何一个非守护线程没有结束，守护线程就全部工作；只有当最后一个非守护线程结束时，守护线程随着JVM一同结束工作。
Daemon的作用是为其他线程的运行提供便利服务，守护线程最典型的应用就是 GC (垃圾回收器)，它就是一个很称职的守护者。

休眠当前线程
		
方法名	作 用
sleep(long millis)	让线程休眠指定的毫秒数
sleep(long millis，int nanos)	让线程休眠指定的毫秒数加纳秒数

终止指定线程
线程终止的原因有2中：run()方法执行完成 和 未捕获的异常.Thread 提供了一种 stop() 方法强行停止线程，由于不安全性 ，不建议使用。
可以采用 Boolean 值 来终止线程 结束（当 run() 方法执行 完成 之后 就代表着线程结束）


线程的插队运行
一个线程优先于其他线程运行的情况，除了设置线程的优先级高于其他线程，更直接的方式是使用 Thread 类 的 join() 方法


方法名	作 用
join()	等待调用该方法的线程终止
join(long millis)	等待调用该方法的线程终止的时间最长为millis毫秒
join(long millis, int nanos)	等待调用该方法的线程终止的时间域长为millis亳秒加nanos纳秒

注意：如果有线程中断了运行join ()方法的线程，则抛出InterruptedException。

Java中的线程同步
非同步的数据读写
一个进程中所有的线程会共享改进程的资源，如果2个或者多个线程同时修改同一个资源，其中一个线程修改后的结果 未更新到变量中，另一个线程对变量又进行修改，产生的数据会不一致 ，所以要使用同步

使用方法实现线程同步
使用方法同步，同步方法就是 有synchronized 关键字修饰的方法
注意：同步方法锁对象是 this
使用同步代码块 实现线程同步
synchronized 来修饰 语句块

synchronized (this) {// 获得Bank类的锁
    account += money;
}

使用特殊域变量实现线程同步



synchronized 能够实现代码的原子性（同步）
volatile 保证变量的可见性，不能保证变量的符合操作原子性。


使用重入锁实现线程同步
方法名	作 用
ReentrantLock()	创建一个ReentrantLock实例
lock()	获得锁
unlock()	释放锁


注意：释放锁资源 ，否则容易出现死锁状态 ，通常放在finally 中 释放


使用线程局部变量实现线程同步





方法名	作 用
Thread Local。	创建一个线程本地变量
get()	返回此线程局部变量的当前线程副本中的值
initialValueO	返回此线程局部变景的当前线程的“初始值”
set(T value)	将此线程局部变萤的当前线程副木屮的值设置为value

使用ThreadLocal() 创建一个线程的本地变量，每个使用该线程的 变量都会使用其副本，副本之间 相互独立，每个线程随意修改变量副本，不会影响其他线程


简单的线程通信 
主要是 通信内容的 同步，各个线程使用后要及时释放，否则会出现死锁

使用 ：Thread.yieId(); 让出 cpu 调度 权 ，其他线程 执行

简单的线程死锁
多线程分别 拥有不同的资源 而同时又 需要对方释放资源才能继续运行 就会发生死锁

当具备以下4个条件时，就会产生死锁：资源互斥（资源只能供一个线程使用）、请求保持（拥有资源的 线程在请求新的资源乂不释放占冇的资源）、不能剥夺（己经获得的资源在使用完成前不能剥夺）和循环等待 (各个线程对资源的耑求构成一个循环)。
解决线程死锁问题

通常破坏循环等待是最有效的方法。


Java线程进阶
使用阻塞队列实现线程同步
LinkedBlockingQueue<E>是一个基于己链接节点的、范围任意的blocking queue。此队列按FIFO (先进先出) 排序元素。



方法名	作 用
LinkedBlockingqueue()	创建一个容量为 Integer.MAX VALUE 的 LinkedBlockingQueue
put(E e)	在队尾添加一个元素，如果队列满则阻塞
size()	返回队列中的元素个数
take()	移除并返回队头元素，如果队列空则阻塞



BlockingQueue<E>接口的使用。
BlockingQueue<E>接口定义了阻塞队列的常用方法。例如，对于添加元素有add()、offer()和put()3种方法。 当队列满时，add()方法会抛出异常，offerO方法会返回false, put()方法会阻塞。读者需要根据自己的需求选择
适当的方法。

新建有返回值的线程
实现 Callable<Integer> 
接受 FutureTask<Integer> 
使用线程池优化多线程编程
大量创建短生命周期的的对象，性能非常低，所以发明了池技术

Object类中线程相关方法

Object类
wait（空参的）线程进入waiting状态（有参的）线程进入timed_waiting状态
notify
notifyAll

Demo: ProducterAndConsumer
 
哲学家就餐问题

5个哲学家 围绕着一个圆桌 坐，右手边放一根筷子，只要2根筷子才能吃饭，有的哲学家此时可能只有一支或者没有筷子，因为旁边的人在用餐，就处于等待状态，5个哲学家 都在等待 就出现了死锁


Demo : DiningPhilosophers
使用信号量实现线程的同步
Semaphore 类：计数信号量
permits 许可证数量 ， fair 是否公平 （征用时是否为 先进先出 ）
acquire();// 获得一个许可
semaphore.release();// 释放一个许可

Demo：SynchronizedBank
使用原子变量实现线程同步


Demo：SynchronizedBank2

Android中的线程与线程池
AsyncTask、IntentService、HandlerThread  区别于传统的线程，但是实质仍然是传统的线程。
线程 不是真的并行，是cpu 断切换运行来调度每个线程。
Android 得线程池 也是通过Executor 派生特定类型的 线程池，不同种类的线程池又具有各自的特性。


Android 中的AsyncTask
OnPreExecute();
异步任务执行之前调用

doInBackground(Params …params);
在线程池中执行

onProgressUpdate(Progress …values);
更新后台执行任务

onPostExecute(Result result);
doInBackground 执行后调用,result 为返回结果

限制条件：
   AsyncTask 的对象必须在主线程中创建
   Excute 方法必须在UI 线程调用
不能再程序中直接调用，onPreExecute() ,onPostExecute ,doInBackground onProgressUpdate 方法

一个AsyncTask对象只能执行一次

Android中的线程池

Android中的线程池的概率来源于Java中的Executor,然真正实现线程池的是ThreadPoolExecutor。

 

最后一个不常用的参数 主要是 无法成功执行任务时，通知调用者


Android中的线程池分类



FixedThreadPool

 

通过Executors的newFixedThreadPool方法来创建。 线程数量固定的线程池，线程处于空闲的时候并不会被回收，只有在线程池被关闭的时候才会。当所有的线程都处于活跃状态时，新任务都会处于等待状态，直到有线程空闲出来。
只有核心线程并且这些核心线程不会被回收，核心线程没有超时机制且任务队列没有大小限制，这样能够快速地响应外界的请求

CachedThreadPool

 

线程不定的线程池，只有非核心线程数， 最大线程为Integer.MAX_VALUE。当线程池中的线程都处于活动状态时，线程池会创建新的线程来处理任务，否则利用空闲的新的任务。
线程池中的空闲线程都有超时机制，超时时长为60s,超过60s后闲置线程就会被回收。
线程池中空闲线程都有超时机制, 适用于大量的耗时较少的任务

ScheduledThreadPool

 

核心线程数固定，非核心线程数没有限制，并且当非核心线程闲置时会被立即回收。
执行定时和具有固定周期的重复任务


SingleThreadExecutor


 



内部只有一个核心线程，确保所有的任务都在同一线程中按照顺序执行
统一外界任务到一个线程中，使得这些任务之间不需要处理线程同步问题

