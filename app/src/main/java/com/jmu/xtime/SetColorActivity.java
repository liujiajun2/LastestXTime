package com.jmu.xtime;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class SetColorActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;
//    private static  String[] selectColors ={"theme_color_black","theme_color_blue.xml","theme_color_pink.xml","theme_color_white.xml"};
    private int turn = 0;
    private Button blackBtn;
    private Button blueBtn;
    private Button pinkBtn;
    private Button whiteBtn;
    private TaskInfomationManager taskInfomationManager;
    private TextView back;
    private Activity activity;
    private static  String selectCoolBlackThemeMessage = "选择酷金黑主题成功.";
    private static  String selectInnerBlueThemeMessage = "选择暗夜蓝主题成功.";
    private static  String selectSakuraPinkThemeMessage = "选择樱花粉主题成功.";
    private static  String selectEasyWhileThemeMessage = "选择简约白主题成功.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
        setTheme(R.style.coolBlack);
        setContentView(R.layout.theme_color_black);
        gestureDetector = new GestureDetector(SetColorActivity.this,onGestureListener);
        blackBtn = (Button)findViewById(R.id.theme_color_btn_black);
        blackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskInfomationManager.updateTheme(2);
                System.out.println(taskInfomationManager.getTheme());
                Toast.makeText(SetColorActivity.this,selectCoolBlackThemeMessage, Toast.LENGTH_LONG).show();
            }
        });
        setBackListenner();
    }

    private GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getY();
                    if (x > 0) {
                    //    System.out.println("向左滑");
                        leftSelectColors();
//                        setContentView(R.layout.theme_color_blue);
                    } else if (x < 0) {
       //                 System.out.println("向右滑");
                        rightSelectColors();
 //                       setContentView(R.layout.theme_color_black);
                    }
                    return true;
                }
            };


        public boolean onTouchEvent(MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }



    public void leftSelectColors(){
        if(turn!=0){
            turn--;
        }
        selectColors(turn);
    }
    public void rightSelectColors(){
        if(turn!=3){
            turn++;
        }
        selectColors(turn);
    }
    public void selectColors(int turn){
        switch (turn){
            case 0:
                selectCoolBlackTheme();
                setBackListenner();
                return;
            case 1:
                selectInnerBlueTheme();
                setBackListenner();
                return;
            case 2:
                selectSakuraPink();
                setBackListenner();
                return;
            case 3:
                selectEasyWhite();
                setBackListenner();
                return;
        }
    }


    public void setBackListenner(){
        back = (TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(SetColorActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void selectCoolBlackTheme(){
        setTheme(R.style.coolBlack);
        setContentView(R.layout.theme_color_black);
        blackBtn = (Button)findViewById(R.id.theme_color_btn_black);
        blackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskInfomationManager.updateTheme(2);
                System.out.println(taskInfomationManager.getTheme());
                Toast.makeText(SetColorActivity.this,selectCoolBlackThemeMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void selectInnerBlueTheme(){
        setTheme(R.style.nightBlue);
        setContentView(R.layout.theme_color_blue);
        blueBtn = (Button)findViewById(R.id.theme_color_btn_blue);
        blueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskInfomationManager.updateTheme(3);
                System.out.println(taskInfomationManager.getTheme());
                Toast.makeText(SetColorActivity.this,selectInnerBlueThemeMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void selectSakuraPink(){
        setTheme(R.style.sakuraPink);
        setContentView(R.layout.theme_color_pink);
        pinkBtn = (Button)findViewById(R.id.theme_color_btn_pink);
        pinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskInfomationManager.updateTheme(4);
                System.out.println(taskInfomationManager.getTheme());
                Toast.makeText(SetColorActivity.this,selectSakuraPinkThemeMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void selectEasyWhite(){
        setTheme(R.style.AppTheme);
        setContentView(R.layout.theme_color_white);
        whiteBtn = (Button)findViewById(R.id.theme_color_btn_white);
        whiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskInfomationManager.updateTheme(1);
                System.out.println(taskInfomationManager.getTheme());
                Toast.makeText(SetColorActivity.this,selectEasyWhileThemeMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setClass(SetColorActivity.this,SettingActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
