package com.jmu.xtime.functionservers.XTSendSMS.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jmu.xtime.R;
import com.jmu.xtime.functionservers.XTFunctionsList.Extension.XTFunctionsTools;
import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 沈启金 on 2017/3/12.
 */

public class XTSendSMSController extends AppCompatActivity {
    private Button sendSMSButton = null;
    private TextView targetPhoneNum = null;
    private TextView targetSMSContext = null;
    private  EditText message = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskInfomationManager taskInfomationManager = new TaskInfomationManager(this.getBaseContext());
        switchTheme(taskInfomationManager.getTheme());
        setContentView(R.layout.xt_functions_send_message_layout);

        sendSMSButton = (Button)findViewById(R.id.xtSendSMSButton);
        targetPhoneNum = (TextView) findViewById(R.id.xtTargetPhoneNumber);
        targetSMSContext = (TextView) findViewById(R.id.xtTargetSMSContext);
        message = (EditText)findViewById(R.id.xtTargetSMSContext);
        TextView back = (TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sendSMSButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String phoneNumber = targetPhoneNum.getText().toString();
                String message = targetSMSContext.getText().toString();
                if (!XTFunctionsTools.isMobilePhone(XTSendSMSController.this,phoneNumber)) {
                    return;
                }
                if (message.length()>0){
                    Log.v("ROGER", "will begin sendSMS");
                    sendSMS(phoneNumber, message);
                }
                else
                    XTFunctionsTools.showKnownAlart(XTSendSMSController.this,"请输入短信内容");
            }
        });
        //对改变文本内容监听
        message.addTextChangedListener(textChange);

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
    private void sendSMS(String phoneNo, String message) {
        Map<String,String> extraString = new HashMap<String,String>();
        extraString.put("tel",targetPhoneNum.getText().toString());
        extraString.put("content",targetSMSContext.getText().toString());
        XTFunctionsTools.showDialog(XTSendSMSController.this, "发送短信","sendSMS",extraString);

    }
    private TextWatcher textChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(message.getText().toString().length()>0){
                sendSMSButton.setBackgroundColor(getResources().getColor(R.color.text_blue));
            }else{
                sendSMSButton.setBackgroundColor(getResources().getColor(R.color.xian_gray));
            }



        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };




}
