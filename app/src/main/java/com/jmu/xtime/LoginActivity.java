package com.jmu.xtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

/**
 * Created by 倾城一世 on 2017/3/21.
 */

public class LoginActivity extends Activity implements View.OnClickListener{

    private String username;
    private String password;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TaskInfomationManager taskInfomationManager;
    private static String loginErrorMessage = "用户名或密码错误";
    private static String loginSucessMessage = "登录成功";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskInfomationManager = new TaskInfomationManager(this.getBaseContext());

        switchTheme(taskInfomationManager.getTheme());
        setContentView(R.layout.activity_login);
        TextView register = (TextView)findViewById(R.id.login_newuser);
        TextView back = (TextView)findViewById(R.id.back);
        TextView forgetPassword = (TextView)findViewById(R.id.login_forgetpassword);
        register.setOnClickListener(this);
        back.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText)findViewById(R.id.password) ;
        loginButton = (Button) findViewById(R.id.login_btnLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initeParameter();
                if(!checkStr(username,password)){
                    Toast.makeText(LoginActivity.this,loginErrorMessage, Toast.LENGTH_LONG).show();
                    return;
                }else{
                    username = username.trim();
                    password = password.trim();

                    System.out.println("username-->"+username+"password-->"+password);
                    if (taskInfomationManager.login(username,password)){
                        Toast.makeText(LoginActivity.this,loginSucessMessage, Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(LoginActivity.this,loginErrorMessage, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        TextView back =(TextView)findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

    }

    @Override
    public void onClick(View v) {

        switch ( v.getId()){
            case R.id.login_newuser:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.login_forgetpassword:
                 break;
        }
    }

    public boolean checkStr(String username,String password){

        if(username.equals("")||username==null||password.equals("")||password==null){
 //           System.out.println("herehre");
            return false;
        }
   //     System.out.println("aaaaaaaaaa");
        return  true;
    }

    public void initeParameter(){
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
    }
    public void switchTheme(int id) {
        switch (id) {
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
