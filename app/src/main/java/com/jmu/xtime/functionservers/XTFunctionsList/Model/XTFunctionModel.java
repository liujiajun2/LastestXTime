package com.jmu.xtime.functionservers.XTFunctionsList.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by 沈启金 on 2017/3/12.
 */

public class XTFunctionModel {

    private String functionName;    // 功能名称
    private Drawable    functionIcon;   // 功能图标
    private Class functionContext;    // 上下文


    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Drawable getFunctionIcon() {
        return functionIcon;
    }

    public void setFunctionIcon(Drawable functionIcon) {
        this.functionIcon = functionIcon;
    }

    public Class getFunctionContext() {
        return functionContext;
    }

    public void setFunctionContext(Class functionContext) {
        this.functionContext = functionContext;
    }
}
