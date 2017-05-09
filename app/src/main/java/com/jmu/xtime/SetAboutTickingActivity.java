package com.jmu.xtime;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

public class SetAboutTickingActivity extends AppCompatActivity {


    private Activity activity;
    private TaskInfomationManager taskInfomationManager;
    private Button add_tick;
    private TextView back;
    private EditText tickingContent;
    private  String suncessMessage = "提交成功,我们已经收到你的宝贵建议";
    private String errorMessage = "提交失败,请保证你输入的内容不为空，且不包含敏感词汇";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
        switchTheme(taskInfomationManager.getTheme());
        setContentView(R.layout.setting_about_tickling);


        back = (TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        tickingContent = (EditText)findViewById(R.id.tickingContent);

        add_tick = (Button)findViewById(R.id.add_tick);
        add_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = tickingContent.getText().toString();
                if(checkContent(content)){
                    taskInfomationManager.insertFeedBack(content);
                    Toast.makeText(SetAboutTickingActivity.this,suncessMessage, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(SetAboutTickingActivity.this,errorMessage, Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public boolean checkContent(String content){
        if(content==null||content.equals("")) return false;
        return true;
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
