package com.jmu.xtime.functionservers.XTFunctionsList.Extension.Receiver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by 沈启金 on 2017/3/12.
 */

public class XTFunctionsActivityEvent extends AppCompatActivity {
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("MPInfo","AlarmActivityEvent");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("闹钟时间已到！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                XTFunctionsActivityEvent.this.finish();
            }
        });
        builder.create().show();
    }
}
