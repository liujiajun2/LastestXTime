package com.jmu.xtime;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmu.xtime.functionservers.XTFunctionsList.Controller.XTFunctionsListController;
import com.jmu.xtime.update.Task.XSendSMS;
import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

import java.util.HashMap;

/**
 * Created by 倾城一世 on 2017/3/7.
 */
//public class MainActivity extends AppCompatActivity implements View.OnClickListener
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView topBar;
    private TextView nav_home;
    private TextView nav_task;
    private TextView nav_myself;
    private TextView add_task;

    private FrameLayout ly_content;
    private HomeFragment f1;
    private TaskFragment f2;
    private MyFragment f3;
    private AddTaskFragment f4;
    private FragmentManager fragmentManager;
    private TaskInfomationManager taskInfomationManager;
    private int themeId = 1;
    Drawable thtemeTop_Home = null;
    Drawable thtemeTop_Task = null;
    Drawable thtemeTop_Myself = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
        themeId  = taskInfomationManager.getTheme();
        switchTheme(themeId);
        setContentView(R.layout.activity_main);
        bindView();
//        TaskInfomationManager manager = new TaskInfomationManager(this.getBaseContext());
//        HashMap<String,String> map = new HashMap<String,String>();
//        map.put(XSendSMS.TASK_TYPE,XSendSMS.TASK_TYPE_SEND_SMS);
//
//        manager.addTask(10,map);
//
//        HashMap<String,String> temp =  manager.getTaskInformationByThreadId(10);
//
//        System.out.print(temp.get(XSendSMS.TASK_TYPE));

    }
//    UI组件初始化与事件绑定
    private void bindView(){
        topBar=(TextView)this.findViewById(R.id.text_top);
        nav_home=(TextView)this.findViewById(R.id.nav_home);
        nav_home.setCompoundDrawablesWithIntrinsicBounds(null, thtemeTop_Home , null, null);
        nav_task=(TextView)this.findViewById(R.id.nav_task);
        nav_task.setCompoundDrawablesWithIntrinsicBounds(null, thtemeTop_Task , null, null);
        nav_myself=(TextView)this.findViewById(R.id.nav_myself);
        nav_myself.setCompoundDrawablesWithIntrinsicBounds(null, thtemeTop_Myself , null, null);
        ly_content = (FrameLayout)this.findViewById(R.id.fragment_container);
        add_task = (TextView)this.findViewById(R.id.add_task);


        nav_home.setOnClickListener(this);
        nav_task.setOnClickListener(this);
        nav_myself.setOnClickListener(this);
        //点击加任务图标会出错
//        add_task.setOnClickListener(this);
        //进入app第一显示的是首页
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        nav_home.setSelected(true);
        f1 = new HomeFragment();
        transaction.add(R.id.fragment_container,f1);
        transaction.commit();

        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent();
                intent.setClass(MainActivity.this, XTFunctionsListController.class);
                startActivity(intent);
            }
        });
    }
    //重置所有文本选中情况
    public void selected(){
        nav_home.setSelected(false);
        nav_task.setSelected(false);
        nav_myself.setSelected(false);
    }
    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }
        if(f3!=null){
            transaction.hide(f3);
        }

    }
    @Override
    public void onClick(View v){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()){
            case R.id.nav_home:
                selected();
                nav_home.setSelected(true);
                if(f1==null){
                    f1 = new HomeFragment();
                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                break;
            case R.id.nav_task:
                selected();
                nav_task.setSelected(true);
                if(f2==null){
                    f2 = new TaskFragment();
                    transaction.add(R.id.fragment_container,f2);
                }else{
                    transaction.show(f2);
                }
                break;
            case R.id.nav_myself:
                selected();
                nav_myself.setSelected(true);
                if(f3==null){
                    f3 = new MyFragment();
                    transaction.add(R.id.fragment_container,f3);
                }else{
                    transaction.show(f3);

                }
                break;
            case R.id.add_task:
                 if(f4==null){
                     f4 = new AddTaskFragment();
                     transaction.add(R.id.fragment_container,f4);
                 }else{
                     transaction.show(f4);
                 }
        }
        transaction.commit();
    }






    public void switchTheme(int id) {
        switch (id) {
            case 1:
                thtemeTop_Home = getResources().getDrawable(R.drawable.nav_home);
                thtemeTop_Task = getResources().getDrawable(R.drawable.nav_task);
                thtemeTop_Myself = getResources().getDrawable(R.drawable.nav_myself);
                setTheme(R.style.AppTheme);
                return;
            case 2:
                thtemeTop_Home = getResources().getDrawable(R.drawable.nav_home_coolblack);
                thtemeTop_Task = getResources().getDrawable(R.drawable.nav_task_black);
                thtemeTop_Myself = getResources().getDrawable(R.drawable.nav_myself_black);
                setTheme(R.style.coolBlack);
                return;
            case 3:
                thtemeTop_Home = getResources().getDrawable(R.drawable.nav_home_blue);
                thtemeTop_Task = getResources().getDrawable(R.drawable.nav_task_blue);
                thtemeTop_Myself = getResources().getDrawable(R.drawable.nav_myself_blue);
                setTheme(R.style.nightBlue);
                return;
            case 4:
                thtemeTop_Home = getResources().getDrawable(R.drawable.nav_home_pink);
                thtemeTop_Task = getResources().getDrawable(R.drawable.nav_task_pink);
                thtemeTop_Myself = getResources().getDrawable(R.drawable.nav_myself_pink);
                setTheme(R.style.sakuraPink);
                return;
        }
    }
}
