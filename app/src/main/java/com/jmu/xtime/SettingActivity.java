package com.jmu.xtime;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

public class SettingActivity extends AppCompatActivity {

    private TextView text_setting_color;
    private TextView text_setting_clear;
    private TextView text_setting_about;
    private Button set_btn;
    private TaskInfomationManager taskInfomationManager;
    private Activity activity;
    private TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
        switchTheme(taskInfomationManager.getTheme());
        setContentView(R.layout.set);
        text_setting_color = (TextView)findViewById(R.id.text_setting_color);
        text_setting_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this,SetColorActivity.class);
           //     activity.finish();
                startActivity(intent);

            }
        });
        text_setting_clear = (TextView)findViewById(R.id.text_setting_clear);
        text_setting_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        text_setting_about = (TextView)findViewById(R.id.text_setting_about);
        text_setting_about.setOnClickListener(new View.OnClickListener() {
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
//    @Override
//    protected void onRestart() {
//        switchTheme(taskInfomationManager.getTheme());
//        setContentView(R.layout.set);
//        System.out.println("onRestart");
//        super.onRestart();
//    }
}
