package com.jmu.xtime.functionservers.XTFunctionsList.Extension;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;


import com.jmu.xtime.functionservers.XTFunctionsGlobal.XTFunctionsGlobalData;
import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

import java.util.Calendar;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.HashMap;
/**
 * Created by 沈启金 on 2017/3/12.
 */
import android.support.v7.app.AppCompatActivity;

public class XTFunctionsTools {

    public static void showDialog(final Context context, final String message, final String actionString, final Map<String,String> extraString) {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        new XTTimePickerDialog(context,new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);


                //代码   保存任务
                String timeStr = timeFormat(hourOfDay) + ":" + timeFormat(minute);
                HashMap<String, String> taskMap = new HashMap<String,String>();
                taskMap.put("title",message);
                taskMap.put("description", "将于 "+ timeStr + message);
                taskMap.put("time", timeStr);
                taskMap.put("taskStatus","on");
                for (String key:extraString.keySet()) {
                    taskMap.put(key, extraString.get(key));
                }
                TaskInfomationManager taskInfomationManager = new TaskInfomationManager(context);
                long taskId = taskInfomationManager.addTask(Thread.currentThread().getId(), taskMap);
                // 将任务ID插入到状态表中
                taskInfomationManager.insertTaskStatus(taskId);

                Intent intent = new Intent();
                intent.setAction(actionString);
                for (String key: taskMap.keySet()) {
                    intent.putExtra(key,taskMap.get(key));
                }
                intent.putExtra("taskId",taskId);
                for (String key: extraString.keySet()) {
                    intent.putExtra(key,extraString.get(key));
                }

                // 任务
                Map<String,String> taskInfo = new HashMap<String,String>();
                taskInfo.put("taskId",String.valueOf(taskId));
                taskInfo.put("timeStr",timeStr);
     //           insertStack(taskInfo);

                PendingIntent sender = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager alarmManager;
                alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);

                Toast.makeText(context,message+timeStr,Toast.LENGTH_SHORT).show();
            }
        },hour,minute,true).show();

    }

    private static String timeFormat(int x)
    {
        String s=""+x;
        if(s.length()==1) s="0"+s;
        return s;
    }

    private static boolean timeFormatCompare(String time1, String time2){
        String[] hourAndMinute1 = time1.split(":");
        int hour1 = Integer.valueOf(hourAndMinute1[0]);
        int minute1 = Integer.valueOf(hourAndMinute1[1]);

        String[] hourAndMinute2 = time1.split(":");
        int hour2 = Integer.valueOf(hourAndMinute2[0]);
        int minute2 = Integer.valueOf(hourAndMinute2[1]);

        if (hour1 > hour2) {
            return true;
        } else if (hour1 == hour2 && minute1 > minute2) {
            return  true;
        }

        return false;
    }

//    private static void insertStack( Map<String,String> task) {
//        Stack<Map<String,String>> taskStackTemp = new Stack<>();
//
//        while (!XTFunctionsGlobalData.getTaskStack().empty()){
//            Map<String,String> taskStackTop = XTFunctionsGlobalData.getTaskStack().peek();
//            if (timeFormatCompare(task.get("timeStr"),taskStackTop.get("timeStr"))) {
//                taskStackTemp.push(XTFunctionsGlobalData.getTaskStack().pop());
//            } else {
//                break;
//            }
//        }
//
//        XTFunctionsGlobalData.getTaskStack().push(task);
//
//        while (!taskStackTemp.empty()){
//            XTFunctionsGlobalData.getTaskStack().push(taskStackTemp.pop());
//        }
//        System.out.print("\n!!XTTool->TaskStack" + XTFunctionsGlobalData.getTaskStack() + "\n");
//    }

    public static void showKnownAlart(Context context,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);

        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public static boolean isMobilePhone(Context context, String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (!Pattern.matches(telRegex,mobiles)) {
            XTFunctionsTools.showKnownAlart(context,"请输入正确号码");
            return false;
        }
        return true;
    }
}
