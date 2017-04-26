package com.jmu.xtime.threadcontrol;

import android.os.Handler;
import android.util.Log;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by 倾城一世 on 2017/3/21.
 */

/**
 * Created by 倾城一世 on 2017/3/19.
 */

public class XTExexutorServiceTask {
    /**   任务执行队列*/
    private static ConcurrentLinkedDeque<MyRunnable> taskQueue = null;
    /**   正在等待或者已经完成队列*/
    private static ConcurrentMap<Future,MyRunnable> taskMap =null;

    private static ExecutorService mES = null;

    private  static Object lock =new Object();

    private static boolean isNotify = true ;

    private static boolean isRunning = true;

    // private ProgressBar pb = null;

    private static Handler mHander = null;

    //    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_thread);
//        init();
//    }
//    private void init() {
//        pb = (ProgressBar)findViewById(R.id.progressBar5);
//        findViewById(R.id.button1).setOnClickListener(this);
//        findViewById(R.id.button2).setOnClickListener(this);
//        findViewById(R.id.button3).setOnClickListener(this);
//        findViewById(R.id.button4).setOnClickListener(this);
//        findViewById(R.id.button5).setOnClickListener(this);
//        taskQueue = new ConcurrentLinkedDeque<MyRunnable>();
//        taskMap = new ConcurrentHashMap<Future, MyRunnable>();
//        if (mES == null) {
//            mES = Executors.newCachedThreadPool();
//        }
//        mHander = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                pb.setProgress(msg.what);
//            }
//        };
//    }
//    @Override
//    public void onClick(View view){
//        switch (view.getId())
//        {
//            case R.id.button1:
//                start();
//                break;
//            case R.id.button2:
//                stop();
//                break;
//            case R.id.button3:
//                reload(new MyRunnable(mHander));
//                break;
//            case R.id.button4:
//                release();
//                break;
//            case R.id.button5:
//                addTask(new MyRunnable(mHander));
//                break;
//            default:
//                break;
//        }
//    }
    public static void addTask(final MyRunnable mr){
        // mHander.sendEmptyMessage(0);
        if(mES==null){
            mES = Executors.newCachedThreadPool();
            notifyWork();
        }
        if(taskQueue ==null){
            taskQueue = new ConcurrentLinkedDeque<MyRunnable>();
        }
        if(taskMap == null){
            taskMap = new ConcurrentHashMap<Future, MyRunnable>();
        }
        mES.execute(new Runnable() {
            @Override
            public void run() {
                taskQueue.add(mr);
                notifyWork();
            }
        });
        Log.i("kkk","添加了一个任务");
        // Toast.makeText(XTExecutorService2.this, "已添加一个新任务到线程池中 ！", Toast.LENGTH_SHORT).show();
    }
    public  void release(){
        // Toast.makeText(XTExecutorService2.this, "释放所有占有资源 ！", Toast.LENGTH_SHORT).show();
        mHander.sendEmptyMessage(0);
        isRunning =false;
        Iterator iterator = taskMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Future,MyRunnable> entry = (Map.Entry<Future,MyRunnable>)iterator.next();
            Future result =entry.getKey();
            if(result == null){
                continue;
            }
            result.cancel(true);
            taskMap.remove(result);
            if(mES!=null){
                mES.shutdown();
            }
            mES = null;
            taskMap = null;
            taskQueue = null;

        }
    }
    public static void reload(final MyRunnable mr){
        mHander.sendEmptyMessage(0);
        if(mES == null){
            mES = Executors.newCachedThreadPool();
            notifyWork();
        }
        if(taskQueue == null){
            taskQueue = new ConcurrentLinkedDeque<MyRunnable>();
        }
        if(taskMap == null){
            taskMap = new ConcurrentHashMap<Future, MyRunnable>();
        }
        mES.execute(new Runnable() {
            @Override
            public void run() {
                taskQueue.offer(mr);
                notifyWork();
            }
        });
        mES.execute(new Runnable() {
            @Override
            public void run() {
                if(isRunning){
                    MyRunnable myRunnable = null;
                    synchronized (lock){
                        myRunnable = taskQueue.poll();
                        if(myRunnable ==null){
                            isNotify = true;
                        }else{
                            taskMap.put(mES.submit(myRunnable),myRunnable);
                        }

                    }
                }
            }
        });

    }
    public void stop(){
        //  Toast.makeText(XTExecutorService2.this, "任务已被取消！",Toast.LENGTH_SHORT).show();
        for (MyRunnable runnable : taskMap.values()){
            runnable.setCanleTaskUnit(true);
        }
    }
    public static void start(){
        if(mES == null || taskQueue == null || taskMap ==null){
            Log.i("KKK","某资源是不是已经被释放了？");
            return ;
        }
        Log.i("KKK","线程是不是要start了？");
        mES.execute(new Runnable() {
            @Override
            public void run() {
                if(isRunning){
                    MyRunnable myRunnable = null;
                    synchronized (lock){
                        myRunnable = taskQueue.poll();
                        if(myRunnable == null){
                            isNotify = true;
                        }else{
                            taskMap.put(mES.submit(myRunnable),myRunnable);
                        }
                    }
                }
            }
        });
    }
    private static void notifyWork(){
        synchronized (lock){
            if (isNotify){
                lock.notifyAll();
                isNotify=!isNotify;
            }
        }
    }
}
