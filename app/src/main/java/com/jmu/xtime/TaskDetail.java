package com.jmu.xtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 倾城一世 on 2017/4/24.
 */

public class TaskDetail extends Activity {
    private TaskInfomationManager taskInfomationManager;
    private Long taskId;
    private int tid;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        taskId = bundle.getLong("taskId");
        System.out.println("onCreate  taskId--->"+taskId);
        setContentView(R.layout.task_detail);
        TextView textView = (TextView)this.findViewById(R.id.back_task);
        textView.setOnClickListener(backTask);
        Button btn = (Button)this.findViewById(R.id.task_detail_delete);
        builder = new AlertDialog.Builder(this);
        showMyDialog();
        btn.setOnClickListener(delButton);
        showDetail();
        showSqlLite();
    }
    View.OnClickListener backTask = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
              finish();
        }
    };
    View.OnClickListener delButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            builder.show();
        }
    };
    private void showMyDialog(){
        builder.setTitle("温馨提示");
        builder.setMessage("确定删除?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                taskInfomationManager.deleteTask(taskId);
                taskInfomationManager.updateTaskStatus(taskId,"yes");
                Intent intent  = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(TaskDetail.this,MainActivity.class);
                startActivity(intent);
         //       finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
    }
    private void showDetail(){

        taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
        System.out.println("showDetail---->"+taskId);
        HashMap<String, String> hashMap = taskInfomationManager.getTaskInformationByTaskId(taskId);
        if(hashMap.get("title").equals("发送短信")){

            TextView tel = (TextView)this.findViewById(R.id.task_detail_tel);
            tel.setText(hashMap.get("tel"));
            TextView content = (TextView)this.findViewById(R.id.task_detail_content);
            content.setText(hashMap.get("content"));

            LinearLayout linearLayout =(LinearLayout)this.findViewById(R.id.task_detail_hidden_interval);
            linearLayout.setVisibility(View.GONE);
            LinearLayout linearLayout2 =(LinearLayout)this.findViewById(R.id.task_detail_hidden_number);
            linearLayout2.setVisibility(View.GONE);

        }else if(hashMap.get("title").equals("发送GPS")){

            TextView interval = (TextView)this.findViewById(R.id.task_detail_inteval);
            interval.setText(hashMap.get("sendTimeInterval"));
            TextView number = (TextView)this.findViewById(R.id.task_detail_number);
            number.setText(hashMap.get("sendTimes"));

            LinearLayout linearLayout =(LinearLayout)this.findViewById(R.id.task_detail_hidden_content);
            linearLayout.setVisibility(View.GONE);
        }else if(hashMap.get("title").equals("定时摄像")){

            LinearLayout linearLayout =(LinearLayout)this.findViewById(R.id.task_detail_hidden_content);
            linearLayout.setVisibility(View.GONE);
            LinearLayout linearLayout1 =(LinearLayout)this.findViewById(R.id.task_detail_hidden_interval);
            linearLayout1.setVisibility(View.GONE);
            LinearLayout linearLayout2 =(LinearLayout)this.findViewById(R.id.task_detail_hidden_number);
            linearLayout2.setVisibility(View.GONE);
            LinearLayout linearLayout3 =(LinearLayout)this.findViewById(R.id.task_detail_hidden_tel);
            linearLayout3.setVisibility(View.GONE);
        }
        TextView title =(TextView)this.findViewById(R.id.task_detail_title);
        title.setText(hashMap.get("title"));
        TextView description = (TextView)this.findViewById(R.id.task_detail_description);
        description.setText(hashMap.get("description"));
        TextView time = (TextView)this.findViewById(R.id.task_detail_time);
        time.setText(hashMap.get("time"));
    }

    public void showSqlLite(){
//        Map<Integer,HashMap<String,String>> map2 = taskInfomationManager.getTasks();
//        Iterator iterator = map2.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry entry = (Map.Entry)iterator.next();
//            Object key = entry.getKey();
//            Object val = entry.getValue();
//            System.out.println(("showSqlite----->"+(Integer) key));
//            Map<String,String> map1 = (HashMap<String,String>)val;
//            Iterator iterator1 = map1.entrySet().iterator();
//            while (iterator1.hasNext()){
//                Map.Entry entry1 = (Map.Entry)iterator1.next();
//                Object object = entry1.getKey();
//                Object val2 = entry1.getValue();
//                System.out.println("showSqlite()----->"+object.toString()+"--->"+val2.toString());
//            }
//        }
        Map<Integer,HashMap<String,String>> map2 = taskInfomationManager.getTasks();
        for(int i = 0 ; i < 100 ; i++){
            HashMap<String,String> data = map2.get(i);
            if(data != null ) {
                Iterator iterator1 = data.entrySet().iterator();
                System.out.println("TASKID------->" + i);
                while (iterator1.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator1.next();
                    System.out.print(entry.getKey() + " = " + entry.getValue());
                }
                System.out.println("--------------------");
            }
        }
    }

}
