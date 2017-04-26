package com.jmu.xtime.functionservers.XTFunctionsGlobal;

import android.app.Application;
import android.location.Location;
import android.util.SparseArray;

import com.jmu.xtime.functionservers.XTFunctionsList.Extension.Receiver.XTFunctionsGetSMSBroadcastReceiver;

/**
 * Created by 沈启金 on 2017/3/13.
 */

public class XTFunctionsGlobalData extends Application {
    private static SparseArray analyzeMessageSparseArray = new SparseArray(3);
    private static int yesPeople = 0;
    private static int noPeople = 0;
    private static int unknownPeople = 0;
    private static Location location = null;
    private static int sendGPSCount = 0;
    private static int sendGPSInterval = 0;


    private static XTFunctionsGetSMSBroadcastReceiver getSMSReceiver = new XTFunctionsGetSMSBroadcastReceiver();

    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        XTFunctionsGlobalData.location = location;
    }

    public static int getSendGPSCount() {
        return sendGPSCount;
    }

    public static void setSendGPSCount(int sendGPSCount) {
        XTFunctionsGlobalData.sendGPSCount = sendGPSCount;
    }

    public static int getSendGPSInterval() {
        return sendGPSInterval;
    }

    public static void setSendGPSInterval(int sendGPSInterval) {
        XTFunctionsGlobalData.sendGPSInterval = sendGPSInterval;
    }

    public SparseArray getAnalyzeMessageSparseArray() {
        return analyzeMessageSparseArray;
    }

    public void setAnalyzeMessageSparseArray(SparseArray analyzeMessageSparseArray) {
        this.analyzeMessageSparseArray = analyzeMessageSparseArray;
    }

    public int getYesPeople() {
        return yesPeople;
    }

    public void setYesPeople(int yesPeople) {
        this.yesPeople = yesPeople;
    }

    public int getNoPeople() {
        return noPeople;
    }

    public void setNoPeople(int noPeople) {
        this.noPeople = noPeople;
    }

    public int getUnknownPeople() {
        return unknownPeople;
    }

    public void setUnknownPeople(int unknownPeople) {
        this.unknownPeople = unknownPeople;
    }

    public XTFunctionsGetSMSBroadcastReceiver getGetSMSReceiver() {
        return getSMSReceiver;
    }

    public void setGetSMSReceiver(XTFunctionsGetSMSBroadcastReceiver getSMSReceiver) {
        this.getSMSReceiver = getSMSReceiver;
    }
}
