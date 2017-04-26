package com.jmu.xtime.functionservers.XTAnalyzeMessage.Controller;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jmu.xtime.R;
import com.jmu.xtime.functionservers.XTFunctionsGlobal.XTFunctionsGlobalData;
import com.jmu.xtime.functionservers.XTFunctionsList.Controller.XTFunctionsListController;
import com.jmu.xtime.functionservers.XTFunctionsList.Extension.Receiver.XTFunctionsGetSMSBroadcastReceiver;

/**
 * Created by 沈启金 on 2017/3/13.
 */

public class XTFunctionsAnalyzeMessageController extends AppCompatActivity {

    private Switch switchAnylazeMessage = null;
    private EditText yesKeyWordEditText = null;
    private EditText noKeyWordEditText = null;
    private TextView textView ;
    private Intent intent;

    XTFunctionsGlobalData data = new XTFunctionsGlobalData();
    XTFunctionsGetSMSBroadcastReceiver getSMSReceiver = data.getGetSMSReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xt_functions_analyze_message_layout);

        switchAnylazeMessage = (Switch) findViewById(R.id.switchAnalyzeMessage);
        switchAnylazeMessage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub

                if (!judgeNull()) {
                    switchAnylazeMessage.setChecked(false);
                    return;
                }

                if (isChecked) {
                    try {
                        registSmsReciver();
                        Toast.makeText(XTFunctionsAnalyzeMessageController.this,"成功开启",Toast.LENGTH_LONG).show();
                    } catch (Exception ex){
                        Log.i("MPInfo",ex.getMessage());
                        Toast.makeText(XTFunctionsAnalyzeMessageController.this,"开启失败",Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        unregisterReceiver(getSMSReceiver);
                        Toast.makeText(XTFunctionsAnalyzeMessageController.this,"成功关闭",Toast.LENGTH_LONG).show();
                    } catch (Exception ex){
                        Log.i("MPInfo",ex.getMessage());
                        Toast.makeText(XTFunctionsAnalyzeMessageController.this,"关闭失败",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        textView = (TextView)findViewById(R.id.back);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(XTFunctionsAnalyzeMessageController.this, XTFunctionsListController.class);
                startActivity(intent);
            }
        });
    }

    private void registSmsReciver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        // 设置优先级 不然监听不到短信
        filter.setPriority(1000);
        // Snackbar.make(editText,"注册短信广播", Snackbar.LENGTH_LONG).show();
        Log.d("MPInfo", "registSmsReciver     ");
        registerReceiver(getSMSReceiver, filter);
    }

    private boolean judgeNull() {
        yesKeyWordEditText = (EditText) findViewById(R.id.yesKeyWordEditText);
        noKeyWordEditText = (EditText) findViewById(R.id.noKeyWordEditText);

        String yesKeyWord = yesKeyWordEditText.getText().toString();
        String noKeyWord = noKeyWordEditText.getText().toString();
        Log.i("MPInfo","yes" + yesKeyWord);
        Log.i("MPInfo","no" + noKeyWord);
        if (yesKeyWord.equals("") || yesKeyWord == null) {
            Toast.makeText(XTFunctionsAnalyzeMessageController.this,"请输入肯定关键词",Toast.LENGTH_LONG).show();
            return false;
        }
        if (noKeyWord.equals("") || noKeyWord == null) {
            Toast.makeText(XTFunctionsAnalyzeMessageController.this,"请输入否定关键词",Toast.LENGTH_LONG).show();
            return false;
        }

        // global setting
        SparseArray sparseArray = new SparseArray(2);
        sparseArray.put(1,yesKeyWord);
        sparseArray.put(0,noKeyWord);
        data.setAnalyzeMessageSparseArray(sparseArray);

        return true;
    }
}
