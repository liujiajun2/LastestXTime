package com.jmu.xtime.functionservers.XTFunctionsList.Extension.Receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.util.SparseArray;

import com.jmu.xtime.functionservers.XTFunctionsGlobal.XTFunctionsGlobalData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 沈启金 on 2017/3/12.
 */

public class XTFunctionsGetSMSBroadcastReceiver extends BroadcastReceiver {
//
//    private static final String REGEX_Message_Yes = "^*1*";
//    private static final String REGEX_Message_NO = "^*0*";

    private NotificationManager manger;
    XTFunctionsGlobalData data = new XTFunctionsGlobalData();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("MPInfo","XTimeGetSMS Receiver");
        Object[] objs = (Object[]) intent.getExtras().get("pdus");
        manger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        for (Object obj : objs) {
            byte[] pdu = (byte[]) obj;
            SmsMessage sms = SmsMessage.createFromPdu(pdu);
            // 短信的内容
            String message = sms.getMessageBody();
            // 短信的发送方
            String from = sms.getOriginatingAddress();
            analysisVerify(message);
            showNotify(context);
        }

    }

    /**
     * 解析短信并且回写，主要是提取出数字验证码并显示在输入框上
     *
     * @param message
     */
    private void analysisVerify(String message) {

        SparseArray sparseArray = data.getAnalyzeMessageSparseArray();

        int tmp = 0;
        for (int i = 0; i < 2; i++) {
            String regexString = "^[^" +  sparseArray.valueAt(1-i) + "]*["+ sparseArray.valueAt(i) + "]+.*$";
            Log.i("MPInfo","pattern = " + regexString);
            Pattern pattern = Pattern.compile(regexString);
            Matcher matcher = pattern.matcher(message);
            if (matcher.matches()) {
                switch (sparseArray.keyAt(i)) {
                    case 1:
                        tmp = data.getYesPeople();
                        data.setYesPeople(++tmp);
                        Log.i("MPInfo","yes matches");
                        tmp = 0;
                        return;
                    case 0:
                        tmp = data.getNoPeople();
                        data.setNoPeople(++tmp);
                        Log.i("MPInfo","no matches");
                        tmp = 0;
                        return;
                }
            }
        }

        tmp = data.getUnknownPeople();
        data.setUnknownPeople(++tmp);
    }

    // 通知
    private void showNotify(Context context){

        Log.i("MPInfo","showNotify");
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        //Ticker是状态栏显示的提示
        builder.setTicker("XTime");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("短信分析");
        //第二行内容 通常是通知正文
        builder.setContentText("Yes:" + String.valueOf(data.getYesPeople()) + " No:" + String.valueOf(data.getNoPeople()) + " Unknown:" + String.valueOf(data.getUnknownPeople()) );
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
        //builder.setSubText("这里显示的是通知第三行内容！");
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        //builder.setContentInfo("2");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        builder.setNumber(2);
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(com.jmu.xtime.R.mipmap.ic_launcher);
        //下拉显示的大图标
        //builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),com.jmu.xtime.R.drawable.push));
        //Intent intent = new Intent(this,SettingsActivity.class);
        //PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,0);
        //点击跳转的intent
        //builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manger.notify(1,notification);
        Log.i("MPInfo","showNotity finish");
    }
}
