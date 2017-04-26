package com.jmu.xtime;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jmu.xtime.functionservers.XTFunctionsList.Controller.XTFunctionsListController;
import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

/**
 * Created by 倾城一世 on 2017/3/7.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnGestureListener{
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
    private FragmentManager transaction;

    //定义后事检测实例
    public static GestureDetector detector;

    /**
     * 坐标记记录当前是哪个fragment
     * 1 是首页
     * 2 是任务
     * 3 是我的
     * */
    public int MARK =1;
    //定义手势两点的最小距离
    final int DISTANT = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        bindView();
        detector = new GestureDetector(this);

//        TaskInfomationManager taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
    }
//    UI组件初始化与事件绑定
    private void bindView(){
        topBar=(TextView)this.findViewById(R.id.text_top);
        nav_home=(TextView)this.findViewById(R.id.nav_home);
        nav_task=(TextView)this.findViewById(R.id.nav_task);
        nav_myself=(TextView)this.findViewById(R.id.nav_myself);
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
                MARK = 1;
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
                MARK = 2;
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
                MARK = 3;
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



    //左右滑动
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
       if(MARK == 1){
           //向右滑动
           if((e2.getX()+DISTANT)<e1.getX()){
               MARK=2;
               selected();
               nav_task.setSelected(true);
               if(f2==null){
                   f2 = new TaskFragment();
                   transaction.add(R.id.fragment_container,f2);
               }else{
                   transaction.show(f2);
               }
           }else {
               return false;
           }
       }else if(MARK==2){
           //向右滑动
           if((e2.getX()+DISTANT)<e1.getX()){
               MARK = 3;
               selected();
               nav_myself.setSelected(true);
               if(f3==null){
                   f3 = new MyFragment();
                   transaction.add(R.id.fragment_container,f3);
               }else{
                   transaction.show(f3);
               }
           }//向左滑动
           else if(e2.getX()>(e1.getX()+DISTANT)){
               MARK = 1;
               selected();
               nav_home.setSelected(true);
               if(f1==null){
                   f1 = new HomeFragment();
                   transaction.add(R.id.fragment_container,f1);
               }else{
                   transaction.show(f1);
               }

           }else if(Math.abs(e2.getY()-e1.getX())>100){
               //此处防止上下滑动
               return false;

           }

       }else if(MARK==3){
           //向左滑动
           if(e2.getX()>(e1.getX()+DISTANT)){
               MARK = 2;
               selected();
               nav_task.setSelected(true);
               if(f2==null){
                   f2 = new TaskFragment();
                   transaction.add(R.id.fragment_container,f2);
               }else{
                   transaction.show(f2);
               }
           }else {
               return false;
           }
       }
        transaction.commit();
        return false;
    }
    @Override
    public boolean onDown(MotionEvent e) {
      //  Toast.makeText(getApplicationContext(),"onDown",Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //这里很关键
        return detector.onTouchEvent(event);
    }
    @Override
    public void onLongPress(MotionEvent arg0) {
      //  Toast.makeText(getApplicationContext(),"onLongPress",Toast.LENGTH_SHORT).show();
        // TODO Auto-generated method stub

    }
    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,float arg3) {
        //Toast.makeText(getApplicationContext(),"onScroll",Toast.LENGTH_SHORT).show();
        // TODO Auto-generated method stub
                 return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
          // TODO Auto-generated method stub
    }
    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
                // TODO Auto-generated method stub
        return false;
    }
}
