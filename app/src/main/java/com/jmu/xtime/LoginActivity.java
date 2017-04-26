package com.jmu.xtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by 倾城一世 on 2017/3/21.
 */

public class LoginActivity extends Activity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView register = (TextView)findViewById(R.id.login_newuser);
        TextView back = (TextView)findViewById(R.id.back);
        TextView forgetPassword = (TextView)findViewById(R.id.login_forgetpassword);
        register.setOnClickListener(this);
        back.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
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
}
