package com.jmu.xtime.threadcontrol;


import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.jmu.xtime.functionservers.XTFunctionsList.Extension.XTFunctionsTools;

import java.util.Map;

/**
 * Created by 倾城一世 on 2017/3/19.
 */

public class MyRunnable implements Runnable {
    private Context context ;
    private String message = "发送短信";
    private String actionString ="sendSMS";
    private Map<String,String> extraString =null;
    private boolean cancleTask = false;
    private boolean cancleException = false;
    private Handler handler =null;
    public MyRunnable(Handler handler){
        this.handler=handler;
    }
    public MyRunnable(Handler handler, Context context){
        this.handler=handler;
        this.context = context;
    } public MyRunnable(Handler handler, Context context, Map<String,String> extraString){
        this.handler=handler;
        this.context = context;
        this.extraString = extraString;
    }
    @Override
    public void run() {
        Log.i("kkk","MyRunnable run() is executed");
        runBefore();
        if(!cancleTask){
            Log.i("KKK","调用MyRunnable run() 方法");
            running();
        }
        runAfter();
    }
    private void runAfter(){
        Log.i("KKK","runAfter()");

    }
    private void running() {
        Log.i("kkk", "running（）");
         System.out.println(context);
        System.out.println(message);
        System.out.println(actionString);
        System.out.println(extraString);
        Log.i("kkk", "调用showDialog方法之前");
        XTFunctionsTools.showDialog(context,message,actionString,extraString);
        Log.i("KKK","调用结束");
//        Log.i("kkk", "running（）");
//        try {
//            int prog = 0;
//            if (cancleTask == false && cancleException == false) {
//                while (prog < 101) {
//                    if ((prog > 0 || prog == 0) || prog < 70) {
//                        SystemClock.sleep(100);
//                    } else {
//                        SystemClock.sleep(300);
//                    }
//                    if (cancleTask == false) {
//
//                         handler.sendEmptyMessage(prog++);
//                        if(prog%10 == 0){
//                            Log.i("kkk", "调用prog++ =" + (prog));
//                        }
//
//                    }
//                }
//            }
//        } catch (Exception e) {
//            cancleException = true;
//        }
    }
        private void runBefore(){
        Log.i("kkk","runbefore()");
    }
    public void setCanleTaskUnit(Boolean cancleTask){
        this.cancleTask = cancleTask;
        Log.i("KKK", "点击了取消任务按钮 ！！！");

    }

}
