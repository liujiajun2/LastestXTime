package com.jmu.xtime;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

public class SetAboutActivity extends AppCompatActivity {

    private TaskInfomationManager taskInfomationManager;
    private int themeId;
    private TextView back;
    private Activity activity;
    private TextView setting_about_tickling;
    private TextView setting_about_function;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
        themeId  = taskInfomationManager.getTheme();
        switchTheme(themeId);
        setContentView(R.layout.setting_about);
        back = (TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        setting_about_tickling = (TextView)findViewById(R.id.setting_about_tickling);
        setting_about_tickling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SetAboutActivity.this,SetAboutTickingActivity.class);
                startActivity(intent);
            }
        });


        setting_about_function = (TextView)findViewById(R.id.setting_about_function);
        setting_about_function.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SetAboutActivity.this,SetAboutFunctionActivity.class);
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
}
