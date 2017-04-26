package com.jmu.xtime.functionservers.XTSendGPS.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jmu.xtime.MainActivity;
import com.jmu.xtime.R;
import com.jmu.xtime.functionservers.XTFunctionsGlobal.XTFunctionsGlobalData;
import com.jmu.xtime.functionservers.XTFunctionsList.Controller.XTFunctionsListController;
import com.jmu.xtime.functionservers.XTFunctionsList.Extension.XTFunctionsTools;
import com.jmu.xtime.functionservers.XTSendSMS.Controller.XTSendSMSController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 沈启金 on 2017/3/14.
 */

public class XTSendGPSController extends AppCompatActivity {
    private Button sendGPSButton = null;
    private EditText receiveGPSPhoneEdit = null;
    private EditText sendGPSInterval = null;
    private EditText sendGPSCount = null;
    private TextView textView ;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xt_functions_send_gps_layout);

        receiveGPSPhoneEdit = (EditText) findViewById(R.id.receiveGPSPhoneEditText);
        sendGPSInterval = (EditText) findViewById(R.id.sendGPSIntervalEditText);
        sendGPSCount = (EditText) findViewById(R.id.sendGPSCountEditText);

        sendGPSButton = (Button) findViewById(R.id.sendGPSButton);
        sendGPSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = receiveGPSPhoneEdit.getText().toString();
                if (!XTFunctionsTools.isMobilePhone(XTSendGPSController.this,phoneNumber)) {
                    return;
                }

                String intervalString = sendGPSInterval.getText().toString();
                String countString = sendGPSCount.getText().toString();
                int interval = 0;
                int count = 0;
                try {
                    interval = Integer.parseInt(intervalString);
                } catch (Exception e) {
                    XTFunctionsTools.showKnownAlart(XTSendGPSController.this,"请输入正整数");
                    return;
                }

                try {
                    count = Integer.parseInt(countString);
                } catch (Exception e) {
                    XTFunctionsTools.showKnownAlart(XTSendGPSController.this,"请输入正整数");
                    return;
                }

                if (count <= 0 || interval <= 0) {
                    XTFunctionsTools.showKnownAlart(XTSendGPSController.this,"请输入正整数");
                    return;
                } else {
//                    XTFunctionsGlobalData.setSendGPSCount(count);
//                    XTFunctionsGlobalData.setSendGPSInterval(interval);
//                    Log.i("MPInfo","global data :" + XTFunctionsGlobalData.getSendGPSCount() + XTFunctionsGlobalData.getSendGPSInterval());
                    sendGPS();
                }
            }
        });
        textView = (TextView)findViewById(R.id.back);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent = getIntent();
//                intent.setClass(XTSendGPSController.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                finish();
            }
        });
    }

    private void sendGPS() {
        Map<String,String> extraString = new HashMap<String,String>();
        extraString.put("tel",receiveGPSPhoneEdit.getText().toString());
        extraString.put("sendTimeInterval",sendGPSInterval.getText().toString());
        extraString.put("sendTimes",sendGPSCount.getText().toString());
        XTFunctionsTools.showDialog(this,XTSendGPSController.this, "发送GPS","sendGPS",extraString);
    }
}
