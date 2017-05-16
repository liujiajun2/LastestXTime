package com.jmu.xtime;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    private RelativeLayout change_theme;
    private RelativeLayout clear_all_task;
    private RelativeLayout about_and_help;
    private Button set_btn;
    private TaskInfomationManager taskInfomationManager;
    private Activity activity;
    private TextView back;
    private String message = "退出登陆成功";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
        switchTheme(taskInfomationManager.getTheme());
        setContentView(R.layout.set);

        change_theme = (RelativeLayout)findViewById(R.id.change_theme);
        change_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this,SetColorActivity.class);
           //     activity.finish();
                startActivity(intent);

            }
        });
        clear_all_task = (RelativeLayout) findViewById(R.id.clear_all_task);
        clear_all_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = true;
                Map<Integer,HashMap<String,String>> map = taskInfomationManager.getTasks();
                if(map==null){
                    Toast.makeText(SettingActivity.this,"当前已经无任务", Toast.LENGTH_LONG).show();
                }else {
                    for(int i = 0 ; i <=taskInfomationManager.getMaxTaskId() ; i++) {
                        HashMap<String, String> map1 = map.get(i);
                        if(map1!=null){
                            flag = false;
                            int id = Integer.parseInt(map1.get("taskId"));
                            taskInfomationManager.deleteTask(id);
                            taskInfomationManager.updateTaskStatus(id,"yes");
                        }
                    }
                    if(flag) Toast.makeText(SettingActivity.this,"当前已经无任务", Toast.LENGTH_LONG).show();
                    else{
                        Toast.makeText(SettingActivity.this,"一键清空所有任务成功", Toast.LENGTH_LONG).show();
                    }
               //     Toast.makeText(SettingActivity.this,"一键清空所有任务成功", Toast.LENGTH_LONG).show();
                }
            }
        });

        about_and_help = (RelativeLayout) findViewById(R.id.about_and_help);
        about_and_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(SettingActivity.this,SetAboutActivity.class);
                    startActivity(intent);
            }
        });

        set_btn = (Button)findViewById(R.id.set_btn);
        set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this,message, Toast.LENGTH_LONG).show();
    }
});
        System.out.println("onCreate");

        back = (TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(SettingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void switchTheme(int id){
        switch (id){
            case 1:
                setTheme(R.style.AppTheme);
                return;
            case 2:
                setTheme(R.style.coolBlack);
                return;
            case 3:
                setTheme(R.style.nightBlue);
                return;
            case 4:
                setTheme(R.style.sakuraPink);
                return;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setClass(SettingActivity.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    public List<MyselfMessageInfo> getMyDatalist(){
        List<MyselfMessageInfo> listItem = new ArrayList<MyselfMessageInfo>();
        MyselfMessageInfo data1 = new MyselfMessageInfo("登录",this.getResources().getDrawable(R.drawable.login));
        MyselfMessageInfo data2 = new MyselfMessageInfo("设置",this.getResources().getDrawable(R.drawable.setting));
        listItem.add(data1);
        listItem.add(data2);
        return listItem;
    }

}
