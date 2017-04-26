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
        tid = bundle.getInt("taskId");
        setContentView(R.layout.task_detail);
        TextView textView = (TextView)this.findViewById(R.id.back_task);
        textView.setOnClickListener(backTask);
        Button btn = (Button)this.findViewById(R.id.task_detail_delete);
        builder = new AlertDialog.Builder(this);
        showMyDialog();
        btn.setOnClickListener(delButton);
        showDetail();
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
                finish();
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

        HashMap<String, String> hashMap = taskInfomationManager.getTaskInformationByTaskId(tid);
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
}
