
package com.icbc.emallim.threaddemo;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.icbc.emallim.threaddemo.avanced.Consumer;
import com.icbc.emallim.threaddemo.avanced.Producer;
import com.icbc.emallim.threaddemo.avanced.TempThread;
import com.icbc.emallim.threaddemo.base.CounterThread;
import com.icbc.emallim.threaddemo.base.EmergencyThread;
import com.icbc.emallim.threaddemo.base.RaceThread;
import com.icbc.emallim.threaddemo.base.ThreadList;
import com.icbc.emallim.threaddemo.base.ThreadState;
import com.icbc.emallim.threaddemo.base.Timer;
import com.icbc.emallim.threaddemo.base.Worker;
import com.icbc.emallim.threaddemo.sync.Bank;
import com.icbc.emallim.threaddemo.sync.Bank2;
import com.icbc.emallim.threaddemo.sync.DeadLock;
import com.icbc.emallim.threaddemo.sync.Receiver;
import com.icbc.emallim.threaddemo.sync.Sender;
import com.icbc.emallim.threaddemo.sync.Transfer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button asynctask_download;
    Button asynctask_download_cancel;
    Button thread_base;
    Button thread_state;
    Button thread_group;
    Button thread_daemon;
    Button thread_sleep;
    Button thread_stop;
    Button thread_stop_stop;
    Button thread_join;
    Button thread_no_sync;
    Button thread_sync_data;
    Button thread_msg;
    Button thread_dead;
    Button thread_product;
    Button thread_product_statue;
    Button thread_return;
    Button thread_pool;
    MyAsyncTask myAsyncTask;

    CounterThread counter;
    Bank2 bank = new Bank2();

    Thread threadq1;
    Thread threadq2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new 一次 只能运行一次 异步任务
        myAsyncTask = new MyAsyncTask();
        initView();

    }

    /**
     * findview
     */
    private void initView() {
        thread_base = findViewById(R.id.thread_base);
        thread_base.setOnClickListener(this);
        thread_group = findViewById(R.id.thread_group);
        thread_group.setOnClickListener(this);
        thread_state = findViewById(R.id.thread_state);
        thread_state.setOnClickListener(this);
        thread_daemon = findViewById(R.id.thread_daemon);
        thread_daemon.setOnClickListener(this);
        thread_sleep = findViewById(R.id.thread_sleep);
        thread_sleep.setOnClickListener(this);
        thread_stop = findViewById(R.id.thread_stop);
        thread_stop.setOnClickListener(this);
        thread_stop_stop = findViewById(R.id.thread_stop_stop);
        thread_stop_stop.setOnClickListener(this);
        thread_join = findViewById(R.id.thread_join);
        thread_join.setOnClickListener(this);
        thread_no_sync = findViewById(R.id.thread_no_sync);
        thread_no_sync.setOnClickListener(this);
        thread_sync_data = findViewById(R.id.thread_sync_data);
        thread_sync_data.setOnClickListener(this);
        thread_msg = findViewById(R.id.thread_msg);
        thread_msg.setOnClickListener(this);
        thread_dead = findViewById(R.id.thread_dead);
        thread_dead.setOnClickListener(this);
        thread_product = findViewById(R.id.thread_product);
        thread_product.setOnClickListener(this);
        thread_product_statue = findViewById(R.id.thread_product_statue);
        thread_product_statue.setOnClickListener(this);
        thread_return = findViewById(R.id.thread_return);
        thread_return.setOnClickListener(this);
        thread_pool = findViewById(R.id.thread_pool);
        thread_pool.setOnClickListener(this);


        asynctask_download = findViewById(R.id.asynctask_download);
        asynctask_download.setOnClickListener(this);
        asynctask_download_cancel = findViewById(R.id.asynctask_download_cancel);
        asynctask_download_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.asynctask_download:
                myAsyncTask.execute("");
                break;
            case R.id.asynctask_download_cancel:
                //参数  ture 允许线程中断 ，false 执行完当前线程
                //myAsyncTask.cancel(true);
                myAsyncTask.cancel(false);
                break;
            case R.id.thread_base:
                threadBase();
                break;
            case R.id.thread_state:
                ThreadState threadState =new ThreadState();
                Thread thread = new Thread(threadState);
                Log.i("threadstate","新建线程："+thread.getState());
                thread.start();
                Log.i("threadstate","启动线程："+thread.getState());
                try {
                    Thread.sleep(100);  //等待 0.1 秒 使新 线程 运行 waitForAsecond(）
                    Log.i("threadstate","计时等待"+thread.getState());
                    Thread.sleep(1000);  //等待 1秒  使新线程 运行 waitforyears()
                    Log.i("threadstate","等待线程"+thread.getState());
                    threadState.notifyNow();  //唤醒 线程
                    Log.i("threadstate","唤醒线程"+thread.getState());
                    Thread.sleep(1000);  //等待1秒  使新线程 结束
                    Log.i("threadstate","终止线程"+thread.getState());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.thread_group:
                ThreadList.startRun();
                break;
            case R.id.thread_daemon:
                Thread userThread = new Thread(new Worker()); // 创建用户线程
                Thread daemonThread = new Thread(new Timer()); // 创建守护线程
                daemonThread.setDaemon(true); // 设置守护线程
                userThread.start(); // 启动用户线程
                daemonThread.start(); // 启动守护线程
                break;
            case R.id.thread_sleep:
                Runnable run1 = new RaceThread().new Rabbit();
                Runnable run2 = new RaceThread().new Tortoise();
                Thread rabbit = new Thread(run1);
                Thread tortoise = new Thread(run2);
                rabbit.start();
                tortoise.start();
                break;
            case R.id.thread_stop:
                counter = new CounterThread();
                new Thread(counter).start();
                break;
            case R.id.thread_stop_stop:
                counter.setStopped(false);
                break;
            case R.id.thread_join:
                Thread threadjoin = new Thread(new EmergencyThread());
                threadjoin.start();

                for (int i = 1; i < 6; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("threadjoin","正常线程运行：" + i);
                    try {
                        threadjoin.join();
                        //threadjoin.join(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
                //非同步的 数据读写
            case R.id.thread_no_sync:
               /* Bank bank1 =new Bank();
                Thread thread1 = new Thread(new Transfer(bank1,"1"));
                thread1.start();
                Thread thread2 = new Thread(new Transfer(bank1,"2"));
                thread2.start();*/

                //线程局部变量同步
                Thread thread1 = new Thread(new Transfer(bank,"1"));
                thread1.start();
                Thread thread2 = new Thread(new Transfer(bank,"2"));
                thread2.start();

                break;
            case R.id.thread_sync_data:
                Log.i("threadnosync","结果："+bank.getAccount());
                break;
            case R.id.thread_msg:
                Sender sender = new Sender();
                Receiver receiver = new Receiver(sender);
                Thread st = new Thread(sender);
                Thread rt = new Thread(receiver);
                st.start();
                rt.start();
                break;
            case R.id.thread_dead:
                DeadLock d1 = new DeadLock();// 创建DeadLock对象d1
                DeadLock d2 = new DeadLock();// 创建DeadLock对象d2
                d1.flag = true; // 将d1的flag设置为true
                d2.flag = false; // 将d2的flag设置为false
                new Thread(d1).start();// 在新线程中运行d1的run()方法
                new Thread(d2).start();// 在新线程中运行d2的run()方法
                break;
            case R.id.thread_product:
                BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
                threadq1 =new Thread(new Producer(queue));
                threadq2 =new Thread(new Consumer(queue));
                threadq1.start();
                threadq2.start();
                break;
            case R.id.thread_product_statue:
                Log.i("threadproduct","threadq1 ：" +threadq1.getState() );
                Log.i("threadproduct","threadq2 ：" +threadq2.getState() );
                break;
            case R.id.thread_return:
                com.icbc.emallim.threaddemo.avanced.Bank bank = new com.icbc.emallim.threaddemo.avanced.Bank();
                com.icbc.emallim.threaddemo.avanced.Transfer transfer1 = new com.icbc.emallim.threaddemo.avanced.Transfer(bank, "1");// 创建Transfer对象
                com.icbc.emallim.threaddemo.avanced.Transfer transfer2 = new com.icbc.emallim.threaddemo.avanced.Transfer(bank, "2");// 创建Transfer对象
                //返回值
                FutureTask<Integer> task1 = new FutureTask<Integer>(transfer1);// 创建FutureTask对象
                FutureTask<Integer> task2 = new FutureTask<Integer>(transfer2);// 创建FutureTask对象
                Thread threadReturn1 = new Thread(task1);// 创建一号线程
                Thread threadReturn2 = new Thread(task2);// 创建二号线程
                threadReturn1.start();// 运行一号线程
                threadReturn2.start();// 运行二号线程
                try {
                    int thread1Result = task1.get();// 获得一号线程的计算结果
                    int thread2Result = task2.get();// 获得二号线程的计算结果
                    Log.i("threadreturn","一号计算结果是：" + thread1Result + "\n");
                    Log.i("threadreturn","二号计算结果是：" + thread2Result + "\n");
                    Log.i("threadreturn","实际的金额是：" + (thread1Result + thread2Result - 100) + "\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.thread_pool:
                Runtime run = Runtime.getRuntime();// 创建Runtime对象
                run.gc();// 运行垃圾回收器，这样可以减少误差
                long freeMemory = run.freeMemory();// 获得当前虚拟机的空闲内存
                long currentTime = System.currentTimeMillis();// 获得当前虚拟机的时间
                for (int i = 0; i < 1000; i++) {// 独立运行1000个线程
                    new Thread(new TempThread()).start();
                }
                System.out.println("独立运行1000个线程所占用的内存：" + (freeMemory - run.freeMemory()) + "字节");// 查看内存的变化
                System.out.println("独立创建1000个线程所消耗的时间：" + (System.currentTimeMillis() - currentTime) + "毫秒");// 查看时间的变化

                run.gc();// 运行垃圾回收器
                freeMemory = run.freeMemory();// 获得当前虚拟机的空闲内存
                currentTime = System.currentTimeMillis();// 获得当前虚拟机的时间
                ExecutorService executorService = Executors.newFixedThreadPool(2);// 创建线程池
                for (int i = 0; i < 1000; i++) {// 使用线程池运行1000个线程
                    executorService.submit(new TempThread());
                }
                System.out.println("使用连接池运行1000个线程所占用的内存：" + (freeMemory - run.freeMemory()) + "字节");// 查看内存的变化
                System.out.println("使用连接池创建1000个线程所消耗的时间：" + (System.currentTimeMillis() - currentTime) + "毫秒");// 查看时间的变化
                break;
            default:
                break;
        }

    }


    /**
     * 4种线程池
     */
    private void android_thread_pools(){

        Runnable command =new Runnable() {
            @Override
            public void run() {
                //android中 使用 不会抛出 InterruptedException异常
                SystemClock.sleep(2000);

            }
        };


        ExecutorService fixedThreadPool= Executors.newFixedThreadPool(4);
        fixedThreadPool.execute(command);

        ExecutorService cachedThreadPool= Executors.newCachedThreadPool();
        cachedThreadPool.execute(command);

        ScheduledExecutorService scheduledThreadPool= Executors.newScheduledThreadPool(4);
        //2000 ms 后执行 command
        scheduledThreadPool.schedule(command,2000, TimeUnit.MILLISECONDS);
        //延迟10ms后 ，每个1000ms 执行一次 command
        scheduledThreadPool.scheduleAtFixedRate(command,10,1000,TimeUnit.MILLISECONDS);

        ExecutorService singleThreadExecutor= Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(command);

    }

    /**
     * 线程基础
     */
    private void threadBase() {
        int num=0;
        for(int i=0;i<5;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i("threadbase", "---------thread 1");
                }
            }).start();
        }

        for(int i=0;i<5;i++){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i("threadbase", "---------thread 2");
                }
            }).start();
        }

        }

    /**
     * 定义 asyncTask
     * <p>
     * Params 参数类型
     * progress 进度类型
     * Result 返回结果类型
     */
    private class MyAsyncTask extends AsyncTask<String, Integer, Long> {

        /**
         * 异步任务执行前会被调用
         * 主线程执行
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("asynctask", "onPreExecute() 方法执行");
        }

        /**
         * 在异步任务执行之后 会被调用
         *
         * @param aLong doInBackground 返回结果
         */
        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Log.i("asynctask", "onPostExecute() 方法执行");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.i("asynctask", "onProgressUpdate(" + values[0] + ") 方法执行");
        }


        @Override
        protected void onCancelled(Long aLong) {
            super.onCancelled(aLong);
            Log.i("asynctask", "onCancelled(" + aLong + ") 方法执行");
        }

        /**
         * 取消异步任务的时候会被执行 此方法被调用是 onPostExecute 不会被执行
         * 主线程执行
         */
        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i("asynctask", "onCancelled() 方法执行");
        }


        /**
         * 返回值 为 onPostExecute() 参数值
         * 在线程中执行
         *
         * @param urls
         * @return
         */
        @Override
        protected Long doInBackground(String... urls) {
            Log.i("asynctask", "doInBackground() 方法执行");
            int count = 0;
            try {
                count = 0;
                int length = 1;
                while (count < 10000) {
                    count += length;
                    // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                    publishProgress(count);
                    // 模拟耗时任务
                    //Thread.sleep(50);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ((Integer) count).longValue();
        }
    }
}
