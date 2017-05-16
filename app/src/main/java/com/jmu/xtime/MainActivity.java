package com.jmu.xtime;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jmu.xtime.functionservers.XTFunctionsList.Controller.XTFunctionsListController;
import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private TaskInfomationManager taskInfomationManager;
    private int themeId = 1;
    Drawable thtemeTop_Home = null;
    Drawable thtemeTop_Task = null;
    Drawable thtemeTop_Myself = null;
    private TextView title_3;

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




    private String[] mPlanetTitles;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ListView listView;
    private FunctionMessageAdapter functionAdapt;
    private List<FunctionMessageInfo> myDatalist;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private TextView introduce;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
        themeId  = taskInfomationManager.getTheme();
        switchTheme(themeId);
        setContentView(R.layout.activity_main);

   //     initDrawerLayout();
//        listView = (ListView)findViewById(R.id.left_drawer);
//        myDatalist = getMyDatalist();
//        functionAdapt = new MyselfMessageAdapter(myDatalist,this);
//        listView.setAdapter(functionAdapt);



//        mDrawerToggle = new ActionBarDrawerToggle(
//                this,                  /* host Activity */
//                mDrawerLayout,         /* DrawerLayout object */
//                R.drawable.nav_bg,  /* nav drawer image to replace 'Up' caret */
//                R.string.app_name,  /* "open drawer" description for accessibility */
//                R.string.app_name /* "close drawer" description for accessibility */
//        ) {
//            public void onDrawerClosed(View view) {
//                getActionBar().setTitle("fuck you1");
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                getActionBar().setTitle("fuck you2");
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
////
//        mPlanetTitles = new String[]{"登录","注册"};
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerList = (ListView) findViewById(R.id.left_drawer);
//
////        // Set the adapter for the list view
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, getData()));
//
//        // Set the list's click listener
////        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });



        bindView();
        detector = new GestureDetector(this);

//        TaskInfomationManager taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
    }


//    private void initDrawerLayout(){
//        drawerLayout=(DrawerLayout)super.findViewById(R.id.drawer_layout);
//
//
//        toggle=new ActionBarDrawerToggle(this,drawerLayout,
//              R.string.drawer_open
//                ,R.string.drawer_close){
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//            }
//        };
//        drawerLayout.setDrawerListener(toggle);
//    }
    private void toggleLeftSliding(){//该方法控制左侧边栏的显示和隐藏
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }else{
            drawerLayout.openDrawer(Gravity.LEFT);
        }
   }
//    private void toggleRightSliding(){//该方法控制右侧边栏的显示和隐藏
//        if(drawerLayout.isDrawerOpen(Gravity.END)){
//            drawerLayout.closeDrawer(Gravity.END);
//        }else{
//            drawerLayout.openDrawer(Gravity.END);
//        }
//    }
    public List<FunctionMessageInfo> getMyDatalist(){
        List<FunctionMessageInfo> listItem = new ArrayList<FunctionMessageInfo>();
        FunctionMessageInfo data1 = new FunctionMessageInfo("我的消息",this.getResources().getDrawable(R.mipmap.navigation_mynews));
        FunctionMessageInfo data2 = new FunctionMessageInfo("我的活动",this.getResources().getDrawable(R.mipmap.navigation_myactive));
        FunctionMessageInfo data3 = new FunctionMessageInfo("个性换肤",this.getResources().getDrawable(R.mipmap.navigation_collect));
        FunctionMessageInfo data4 = new FunctionMessageInfo("快速添加任务",this.getResources().getDrawable(R.mipmap.navigation_add));
        FunctionMessageInfo data5 = new FunctionMessageInfo("清除所有任务",this.getResources().getDrawable(R.mipmap.navigation_delete));
        FunctionMessageInfo data6 = new FunctionMessageInfo("关于与帮助",this.getResources().getDrawable(R.mipmap.navigation_about));
        listItem.add(data1);
        listItem.add(data2);
        listItem.add(data3);
        listItem.add(data4);
        listItem.add(data5);
        listItem.add(data6);

        return listItem;
    }
//    private List<String> getData() {
//        List<String> list = new ArrayList<String>();
//
//        for (int i = 0; i < 5; i++) {
//            list.add("my_test_" + i);
//        }
//        return list;
//    }
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
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        title_3 = (TextView)findViewById(R.id.title_3);
//        title_3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this,"title_3", Toast.LENGTH_LONG).show();
//            }
//        });
        introduce = (TextView) this.findViewById(R.id.introduce);
        introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this,"666666666", Toast.LENGTH_LONG).show();
                toggleLeftSliding();
            }
        });
        ImageView img_7 = (ImageView)findViewById(R.id.img_7);
        img_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });

        TextView title_7 = (TextView)findViewById(R.id.title_7);
        title_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_8 = (ImageView)findViewById(R.id.img_8);
        img_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"退出登录成功", Toast.LENGTH_LONG).show();
            }
        });

        TextView title_8 = (TextView)findViewById(R.id.title_8);
        title_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"退出登录成功", Toast.LENGTH_LONG).show();
            }
        });


        listView = (ListView)findViewById(R.id.function_listview);
        listView.setDividerHeight(0);
        myDatalist = getMyDatalist();
        functionAdapt = new FunctionMessageAdapter(myDatalist,this);
        listView.setAdapter(functionAdapt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent tempIntent = new Intent();
                switch (position){
                    case 0 :
                        Toast.makeText(MainActivity.this,"功能还未开放", Toast.LENGTH_LONG).show();
                        break;
                    case 1 :
                        Toast.makeText(MainActivity.this,"功能还未开放", Toast.LENGTH_LONG).show();
                        break;
                    case 2 :
                        tempIntent = new Intent();
                        tempIntent.setClass(MainActivity.this,SetColorActivity.class);
                        startActivity(tempIntent);
                        break;
                    case 3 :
                        tempIntent  = new Intent();
                        tempIntent.setClass(MainActivity.this, XTFunctionsListController.class);
                        startActivity(tempIntent);
                        break;
                    case 4 :
                       clearAllTask();
                        break;
                    case 5 :
                        tempIntent = new Intent();
                        tempIntent.setClass(MainActivity.this,SetAboutActivity.class);
                        startActivity(tempIntent);
                        break;

                }
            }
        });
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




    public void clearAllTask(){
        boolean flag = true;
        Map<Integer,HashMap<String,String>> map = taskInfomationManager.getTasks();
        if(map==null){
            Toast.makeText(MainActivity.this,"当前已经无任务", Toast.LENGTH_LONG).show();
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
            if(flag) Toast.makeText(MainActivity.this,"当前已经无任务", Toast.LENGTH_LONG).show();
            else{
                Toast.makeText(MainActivity.this,"一键清空所有任务成功", Toast.LENGTH_LONG).show();
            }
            //     Toast.makeText(SettingActivity.this,"一键清空所有任务成功", Toast.LENGTH_LONG).show();
        }
    }
}
