package com.icbc.emallim.threaddemo.base;

import java.util.ArrayList;
import java.util.List;

public class ThreadList {

    /**
     * 获取根线程组
     * @return
     */
    private static ThreadGroup getRootThreadGroups() {

        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup(); //获取当前线程组
        while (true) {
            if (rootGroup.getParent() != null) { //如果 为null  就不是根线程组
                rootGroup = rootGroup.getParent();  //获得父线程组
                break;
            }else{
                break;
            }
        }
        return rootGroup;
    }

    /**
     * 获取 指定线程组的 所有线程名称
     * @return
     */
    public static List<String>  getThreads (ThreadGroup group){
        List<String> threadList = new ArrayList<String> ();
        Thread[] threads = new Thread[group.activeCount()]; //获取 线程组的中 线程数
        int count = group.enumerate(threads, false); //复制线程到 线程数组
        for (int i = 0; i < count; i++) {
            threadList.add(group.getName() + "线程组："+ threads[i].getName());
        }
        return threadList;
    }

    /**
     *  获取所有线程组中的 所有线程
     * @param group
     * @return
     */
    public static List<String> getThreadGroups(ThreadGroup group) {//获得线程组中所有线程
        List<String> threadList = getThreads(group);    //获得给定线程组中线程名
        ThreadGroup[] groups = new ThreadGroup[group.activeGroupCount()];//创建线程组数组
        int count = group.enumerate(groups, false); //复制子线程组到线程组数据
        for (int i = 0; i < count; i++) {//遍历所有子线程组
            threadList.addAll(getThreads(groups[i]));// 利用getThreads()方法获得线程名列表
        }
        return threadList;//返回所有线程名
    }

    public static void startRun() {
        for (String string : getThreadGroups(getRootThreadGroups())) {
            System.out.println(string);//遍历输出列表中的字符串
        }
    }

}
