package com.jmu.xtime.functionservers.XTFunctionsList.Controller;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jmu.xtime.MainActivity;
import com.jmu.xtime.R;
import com.jmu.xtime.functionservers.XTAnalyzeMessage.Controller.XTFunctionsAnalyzeMessageController;
import com.jmu.xtime.functionservers.XTFuctionTakePicture.XTFunction_Take_Picture;
import com.jmu.xtime.functionservers.XTFunctionsList.Model.XTFunctionModel;
import com.jmu.xtime.functionservers.XTSendGPS.Controller.XTSendGPSController;
import com.jmu.xtime.functionservers.XTSendSMS.Controller.XTSendSMSController;
import com.jmu.xtime.update.Task.XTakePicture;
import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by 沈启金 on 2017/3/12.
 */

public class XTFunctionsListController extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private List<XTFunctionModel> functionsListArray = null;
    private ListView functionsListView = null;
    private Intent intent;
    private int hour;
    private int minus;
    private String message;
    private TimePickerDialog timePickerDialog;
    private TextView textView ;
    private TaskInfomationManager taskInfomationManager;
    private  long numberTime  ;
    private long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.xt_functions_list_layout);
        intent = getIntent();
        if(intent!=null) {
            if ((intent.getStringExtra("message")!=null)) {
                Toast.makeText(XTFunctionsListController.this, intent.getStringExtra("message"), Toast.LENGTH_LONG).show();
            }
        }
        functionsListView = (ListView)findViewById(R.id.xtFunctionsListView);
        functionsListArray = new ArrayList<XTFunctionModel>();

        // 填充数据
        fillData();
        XTFunctionsListAdapter functionsListAdapter = new XTFunctionsListAdapter(this,functionsListArray);
        functionsListView.setAdapter(functionsListAdapter);
        functionsListView.setOnItemClickListener(this);
        textView = (TextView)findViewById(R.id.back);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.setClass(XTFunctionsListController.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    // 点击跳转至
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == 3){
            showTimePickDialog(timePickerDialog,this.getBaseContext());
            return ;
        }
        Intent intent = new Intent();
        intent.setClass( XTFunctionsListController.this,functionsListArray.get(position).getFunctionContext());//前面一个是一个Activity，后面一个是要跳转的Activity
        startActivity(intent);//开始界面的跳转函数
    }

    private void fillData() {
        /**
         * 从服务器获取
         */
        /**
         * 本地获取
         */
        // MARK: 功能一：定时发送短信
        XTFunctionModel functionModel = new XTFunctionModel();
        functionModel.setFunctionName("定时发送短息");
        functionModel.setFunctionIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.xt_functions_send_message));
        functionModel.setFunctionContext(XTSendSMSController.class);
        functionsListArray.add(functionModel);

        // MARK: 功能二：短信分析功能
        XTFunctionModel functionModel2 = new XTFunctionModel();
        functionModel2.setFunctionName("分析短信");
        functionModel2.setFunctionIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.xf_functions_analyze_message));
        functionModel2.setFunctionContext(XTFunctionsAnalyzeMessageController.class);
        functionsListArray.add(1,functionModel2);

        XTFunctionModel functionModel3 = new XTFunctionModel();
        functionModel3.setFunctionName("定时GPS");
        functionModel3.setFunctionIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.xt_functinos_gps));
        functionModel3.setFunctionContext(XTSendGPSController.class);
        functionsListArray.add(functionModel3);

        XTFunctionModel functionModel4 = new XTFunctionModel();
        functionModel4.setFunctionName("定时摄像");
        functionModel4.setFunctionIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.timg1));
//        functionModel4.setFunctionContext(XTFuction_GetLockTimeActivity.class);
        functionsListArray.add(functionModel4);
    }








    public void toastShowMessage(int sendHour,int sendMinute,int curHour,int curMinus){
        message = "摄像头将在";
        if((sendHour-curHour) == 0){
            message += (sendMinute-curMinus)+"分钟后自动进行拍摄";
        }else if((sendHour-curHour) <= 0){
            if((60-curMinus+sendMinute)>60){
                message += (23-curHour+sendHour+1) + "小时"+(sendMinute-curMinus)+"分钟后自动进行拍摄";
            }else {
                message += (23-curHour+sendHour) + "小时"+(60-curMinus+sendMinute)+"分钟后自动进行拍摄";
            }
        }else {
            if((sendMinute-curMinus)>0){
                message += (sendHour-curHour) + "小时"+(sendMinute-curMinus)+"分钟后自动进行拍摄";
            }else if(sendMinute-curMinus<0){
                message += (sendHour-1-curHour) + "小时"+(60+sendMinute-curMinus)+"分钟后自动进行拍摄";
            }
        }
        Toast.makeText(XTFunctionsListController.this,message,Toast.LENGTH_LONG).show();
    }

    public void showTimePickDialog(TimePickerDialog timePickerDialog, final Context context){

        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = new Date();
        calendar.setTime(date);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minus = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(XTFunctionsListController.this,new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, final int hourOfDay, final int minute) {


                final  HashMap<String,String > map = initConnectToDB(context);
                showSqlLite();
//                Map<Integer,HashMap<String,String>> map2 = taskInfomationManager.getTasks();
//                Iterator iterator = map2.entrySet().iterator();
//                while (iterator.hasNext()){
//                    Map.Entry entry = (Map.Entry)iterator.next();
//                    Object key = entry.getKey();
//                    Object val = entry.getValue();
//                    System.out.println(((Integer) key+" "));
//                    Map<String,String> map1 = (HashMap<String,String>)val;
//                    Iterator iterator1 = map1.entrySet().iterator();
//                    while (iterator1.hasNext()){
//                        Map.Entry entry1 = (Map.Entry)iterator1.next();
//                        Object object = entry1.getKey();
//                        Object val2 = entry1.getValue();
//                        System.out.println(object.toString()+"--->"+val2.toString());
//                    }
//                }

//                Map map = new HashMap();
//                　　Iterator iter = map.entrySet().iterator();
//                　　while (iter.hasNext()) {
//                    　　Map.Entry entry = (Map.Entry) iter.next();
//                    　　Object key = entry.getKey();
//                    　　Object val = entry.getValue();
//                    　　}
//
                insertTaskIntoSqlLite(map,hourOfDay,minute);
//                map.put(XTakePicture.TITLE,"定时摄像");
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append("将于"+hourOfDay+"点"+minute+"分开启摄像头并完成自动拍摄");
//                map.put(XTakePicture.DESCRIPTION,stringBuilder.toString());
//                stringBuilder = new StringBuilder();
//                stringBuilder.append(hourOfDay+"点"+minute+"分");
//                map.put(XTakePicture.TIME,stringBuilder.toString());
//                stringBuilder = new StringBuilder();
//                stringBuilder.append("on");
//                map.put(XTakePicture.TASK_STATUS,stringBuilder.toString());
                final Intent intent = new Intent();
                final long timeNumber  = (hourOfDay-hour)*60*60*1000+(minute-minus)*60*1000;
                numberTime = timeNumber;
                toastShowMessage(hourOfDay,minute,hour,minus);
 //               System.out.println("lalallalalala");
//                Long id =  taskInfomationManager.addTask(this.getId(),map);
                ClockThread clockThread = new ClockThread();
 //               System.out.println("lalallalalala");
                   id =  taskInfomationManager.addTask(clockThread.getId(),map);
                System.out.println("TimePickerDialog id---->"+id);
 //               System.out.println("lalallalalala");
                clockThread.start();
//                new Thread(){
//                    @Override
//                    public void run() {
//
//                       Long id =  taskInfomationManager.addTask(this.getId(),map);
//
//                        try {
//                            Thread.sleep(timeNumber);
//                            intent.putExtra("threadId",id+"");
//                            intent.setClass(XTFunctionsListController.this,XTFunction_Take_Picture.class);
//                            startActivity(intent);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
            }
        },hour,minus,true);
        timePickerDialog.show();
    }

    public HashMap<String,String> initConnectToDB( final Context context){
        taskInfomationManager = new TaskInfomationManager(context);
        HashMap<String,String > map  = new HashMap<String,String>();
        return map;
    }

    public class ClockThread extends Thread{
        public void run() {
            try {
                Thread.sleep(numberTime);
                System.out.println("ClockThread---->"+id);
                Intent intent2 = new Intent();
                System.out.println(intent2.toString());
                intent2.putExtra("taskId",id+"");
                intent2.setClass(XTFunctionsListController.this,XTFunction_Take_Picture.class);
                startActivity(intent2);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    public void showSqlLite(){
        Map<Integer,HashMap<String,String>> map2 = taskInfomationManager.getTasks();
        Iterator iterator = map2.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            System.out.println(((Integer) key+" "));
            Map<String,String> map1 = (HashMap<String,String>)val;
            Iterator iterator1 = map1.entrySet().iterator();
            while (iterator1.hasNext()){
                Map.Entry entry1 = (Map.Entry)iterator1.next();
                Object object = entry1.getKey();
                Object val2 = entry1.getValue();
                System.out.println(object.toString()+"--->"+val2.toString());
            }
        }
    }

    public  void  insertTaskIntoSqlLite(HashMap<String,String > map,final int hourOfDay, final int minute){
        map.put(XTakePicture.TITLE,"定时摄像");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("将于"+hourOfDay+"点"+minute+"分开启摄像头并完成自动拍摄");
        map.put(XTakePicture.DESCRIPTION,stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(hourOfDay+"点"+minute+"分");
        map.put(XTakePicture.TIME,stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("on");
        map.put(XTakePicture.TASK_STATUS,stringBuilder.toString());
    }
}
