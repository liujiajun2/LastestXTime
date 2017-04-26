package com.jmu.xtime.functionservers.XTFunctionsList.Extension;

import android.app.TimePickerDialog;
import android.content.Context;

/**
 * Created by 沈启金 on 2017/3/12.
 */

public class XTTimePickerDialog extends TimePickerDialog {
    @Override
    protected void onStop() {
//          注释这里，onstop就不会调用回调方法
//          super.onStop();
    }

    public XTTimePickerDialog(Context context, OnTimeSetListener callBack,
                              int hourOfDay, int minute, boolean is24HourView) {
        super(context, callBack, hourOfDay, minute, is24HourView);
        // TODO Auto-generated constructor stub
    }
    public XTTimePickerDialog(Context context, int theme,
                              OnTimeSetListener callBack, int hourOfDay, int minute,
                              boolean is24HourView) {
        super(context, theme, callBack, hourOfDay, minute, is24HourView);
        // TODO Auto-generated constructor stub
    }
}
