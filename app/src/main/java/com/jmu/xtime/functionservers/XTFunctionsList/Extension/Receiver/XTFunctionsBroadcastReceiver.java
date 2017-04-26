package com.jmu.xtime.functionservers.XTFunctionsList.Extension.Receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.widget.Toast;

import com.jmu.xtime.functionservers.XTFunctionsGlobal.XTFunctionsGlobalData;
import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

/**
 * Created by 沈启金 on 2017/3/12.
 */

public class XTFunctionsBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("MPInfo","Alarm Receiver" + intent.getAction());
        Intent anotherIntent;
        Bundle bundle = intent.getExtras();
        //删除任务
        long taskId = bundle.getLong("taskId");
        TaskInfomationManager taskInfomationManager = new TaskInfomationManager(context);
        taskInfomationManager.getTaskInformationByTaskId((int)taskId).put("taskStatus","off");
        taskInfomationManager.deleteTask(taskId);

        switch (intent.getAction()) {
            case "openQQ":
                try{
                    String url="mqqwpa://im/chat?chat_type=wpa&uin=401967599";
                    anotherIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    anotherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(anotherIntent);
                }catch(Exception e){
                    Toast.makeText(context, "没有安装", Toast.LENGTH_LONG).show();
                    //Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.i("MPInfo",e.getMessage());
                }
                break;
            case "openMusic":
                try {
                    anotherIntent = context.getPackageManager().getLaunchIntentForPackage("com.oppo.music");
                    anotherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(anotherIntent);
                } catch (Exception e) {
                    Toast.makeText(context,"打开音乐失败",Toast.LENGTH_LONG).show();
                    Log.i("MPInfo",e.getMessage());
                }
                break;
            case "timingProgram":
                Log.i("MPInfo","timingProgramStart");
                try{
                    String programURI = bundle.getString("programURI");
                    Log.i("MPInfo",programURI);
                    anotherIntent = context.getPackageManager().getLaunchIntentForPackage(programURI);
                    anotherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(anotherIntent);
                }catch (Exception e){
                    Toast.makeText(context,"打开程序失败",Toast.LENGTH_LONG).show();
                    Log.i("MPInfo",e.getMessage());
                }
                break;
            case "sendSMS":
                String targetPhoneNumger = bundle.getString("tel");
                String targetSMSContext = bundle.getString("content");
                sendSMS(context,targetPhoneNumger,targetSMSContext);
                break;
            case "sendGPS":
                try {
                    Log.i("MPInfo","sendGPS");
                    String receiveGPSPhone = bundle.getString("tel");
                    int sendGPSCount = 1;
                    int sendGPSInterval = 1;
                    try {
                        sendGPSCount = Integer.parseInt(bundle.getString("sendTimes"));
                        sendGPSInterval = Integer.parseInt(bundle.getString("sendTimeInterval"));
                    } catch (Exception e) {
                        Log.i("MPInfo",e.getMessage());
                    }
                    getLocation(context,receiveGPSPhone,sendGPSInterval,sendGPSCount);
                    Log.i("MPInfo",receiveGPSPhone);

                } catch (Exception e) {
                    Log.i("MPInfo","SendGPS Error");
                    Toast.makeText(context,"GPS发送失败",Toast.LENGTH_LONG).show();
                    Log.i("MPInfo",e.getMessage());
                }
                break;
            default:
                Log.i("MPInfo","Alarm");
                try {
                    anotherIntent = new Intent(context,XTFunctionsActivityEvent.class);
                    anotherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(anotherIntent);
                } catch (Exception e) {
                    Toast.makeText(context,"闹钟接收失败",Toast.LENGTH_LONG).show();
                    Log.i("MPInfo",e.getMessage());
                }
        }
        Log.i("MPInfo","Receiver finish");
    }

    private void getLocation(final Context context, final String receiveGPSPhone, final int sendGPSInterval, final int sendGPSCount) {
        Log.i("MPInfo","getLocation...");
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context,"请开启GPS并授权",Toast.LENGTH_LONG).show();
            return ;
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(context,"请开启GPS并授权",Toast.LENGTH_LONG).show();
            return ;
        }

        XTFunctionsGlobalData.setSendGPSCount(sendGPSCount);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,  sendGPSInterval * 60 * 1000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Toast.makeText(context,"update send",Toast.LENGTH_LONG).show();
                int temp = XTFunctionsGlobalData.getSendGPSCount();
                if (temp > 0) {
                    Log.i("MPInfo","update count= " + temp + " interval = " + sendGPSInterval);
                    Toast.makeText(context,"GPS已发送 ",Toast.LENGTH_LONG).show();
                    sendSMS(context,receiveGPSPhone,parseGPS(location));
                    temp--;
                    XTFunctionsGlobalData.setSendGPSCount(temp);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

        if (location != null) {
            Log.i("MPInfo","location isn't null");
            sendSMS(context,receiveGPSPhone,parseGPS(location));
            Toast.makeText(context,"GPS已发送 ",Toast.LENGTH_LONG).show();
        }

        return ;
    }

    private String parseGPS(Location location) {
        return "【时间：" + location.getTime() + "】   经度：" + location.getLongitude() + "   纬度：" + location.getLatitude() + "  海拔：" + location.getAltitude();
    }

    private void sendSMS(Context context,String phoneNumber, String message) {
        try {
            Log.i("MPInfo","sendingSMS");
            Log.i("MPInfo",phoneNumber + message);
            // android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
            // sms.sendTextMessage(phoneNumber,null,message,null,null);
            Toast.makeText(context,"信息已发送",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.i("MPInfo","SendSMS Error");
            Toast.makeText(context,"发送信息失败",Toast.LENGTH_LONG).show();
            Log.i("MPInfo",e.getMessage());
        }
    }
}
