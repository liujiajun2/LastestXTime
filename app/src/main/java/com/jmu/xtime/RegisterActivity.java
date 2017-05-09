package com.jmu.xtime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private String username;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String email;
    private TaskInfomationManager taskInfomationManager;
    private String registerErrorMessage;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
        switchTheme(taskInfomationManager.getTheme());
        setContentView(R.layout.activity_register);
        TextView back = (TextView)findViewById(R.id.back);
        initParameters();
        button = (Button)findViewById(R.id.register_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    initParameters();
                    String [] information = new String[]{username,password,confirmPassword,phoneNumber,email };
                    if(!checkInformation(information))return;
                    for (int i =0;i<information.length;i++){
                        information[i] = information[i].trim();
                    }
                      System.out.println("information[0]----->"+information[0]);
                    if(taskInfomationManager.isUserNameExists(information[0])){
                        Toast.makeText(RegisterActivity.this,"用户名已存在", Toast.LENGTH_LONG).show();
                        return;
                    }else {
                         System.out.println(taskInfomationManager.createUser(information[0],information[1]));
                        if(taskInfomationManager.createUser(information[0],information[1])>1){
                            Toast.makeText(RegisterActivity.this,"创建用户成功", Toast.LENGTH_LONG).show();
                            return;
                        }else{
                            Toast.makeText(RegisterActivity.this,"创建用户失败", Toast.LENGTH_LONG).show();
                            return;
                        }
                   }
          }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

    }

    public boolean checkInformation( String [] information){
            int i = 0;
            for( i = 0;i<information.length;i++){
                if(!checkStr(information[i])){
                    break;
                }
            }
        System.out.println(i);
            switch (i){
                case 0:
                    System.out.println("1");
                    Toast.makeText(RegisterActivity.this,"用户名不能为空", Toast.LENGTH_LONG).show();
                    return false;
                case 1:
                    System.out.println("2");
                    Toast.makeText(RegisterActivity.this,"密码不能为空", Toast.LENGTH_LONG).show();
                    return false;
                case 2:
                    System.out.println("3");
                    Toast.makeText(RegisterActivity.this,"确认密码不能为空", Toast.LENGTH_LONG).show();
                    return false;
                case 3:
                    System.out.println("4");
                    Toast.makeText(RegisterActivity.this,"电话不能为空", Toast.LENGTH_LONG).show();
                    return false;
                case 4:
                    System.out.println("5");
                    Toast.makeText(RegisterActivity.this,"邮箱不能为空", Toast.LENGTH_LONG).show();
                    return false;
            }
        if(!checkEmail(information[4])){
            Toast.makeText(RegisterActivity.this,"输入的邮箱格式有误", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!checkPhoneNumber(information[3])){
            Toast.makeText(RegisterActivity.this,"输入的电话格式有误", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!checkPassword(information[1],information[2])){
            Toast.makeText(RegisterActivity.this,"前后两次输入的密码不同", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkStr(String str){
        if(str.equals("")||str==null){
            return false;
        }
        return true;
    }

    public boolean checkPhoneNumber(String phoneNumber){
        try{
            Pattern regexTel = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcherTel = regexTel.matcher(phoneNumber);
            if( !matcherTel.matches()){
                return false;
            }
        }catch(Exception e) {
            return false;
        }
        return  true;
    }

    public boolean checkEmail(String email){
        try{
            String emailCheck = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regexEmail = Pattern.compile(emailCheck);
            Matcher matcherEmail = regexEmail.matcher(email);
            if( !matcherEmail.matches()){
                return false;
            }
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    public void initParameters(){
        username = ((EditText)findViewById(R.id.register_username)).getText().toString();
        password = ((EditText)findViewById(R.id.register_password)).getText().toString();
        confirmPassword = ((EditText)findViewById(R.id.register_password_confirm)).getText().toString();
        phoneNumber = ((EditText)findViewById(R.id.register_phone)).getText().toString();
        email = ((EditText)findViewById(R.id.register_email)).getText().toString();
    }

    public boolean checkPassword(String password,String confirmPassword){
        if(!password.equals(confirmPassword)){
            return false;
        }
        return true;
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
