package com.jmu.xtime;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

public class SetAboutFunctionActivity extends AppCompatActivity {

    private TaskInfomationManager taskInfomationManager;
    private TextView back;
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
        switchTheme(taskInfomationManager.getTheme());
        activity = this;
        setContentView(R.layout.setting_about_function);
        back = (TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
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
