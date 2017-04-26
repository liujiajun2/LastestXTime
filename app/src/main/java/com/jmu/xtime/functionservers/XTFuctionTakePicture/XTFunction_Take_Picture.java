package com.jmu.xtime.functionservers.XTFuctionTakePicture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.jmu.xtime.R;
import com.jmu.xtime.functionservers.XTFunctionsList.Controller.XTFunctionsListController;
import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author wx 2017/3/19
 */
public class XTFunction_Take_Picture extends Activity implements SurfaceHolder.Callback , android.hardware.Camera.PreviewCallback{
    private SurfaceView surfaceView = null;
    private SurfaceHolder surfaceHolder = null;
    private android.hardware.Camera camera = null;
    private boolean previewRunning = false;
    private Button button = null;
    public static int time = 2;
    private Long id;
    private TaskInfomationManager taskInfomationManager;
    private  Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        initConnectToDB(this.getBaseContext());
//        Intent intent = this.getIntent();
//        if(intent.getStringExtra("taskId")!=null){
//            id = Long.parseLong(intent.getStringExtra("taskId"));
//            System.out.println("id---->"+id);
//        }else {
//            System.out.print("null");
//        }
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_xtfunction__take__picture);
        surfaceView = (SurfaceView) findViewById(R.id.surface_camera);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        button = (Button)findViewById(R.id.takePicture);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(camera!= null){
                    camera.takePicture(null,null,jpegCallback);
                    changeByTime(3000);
                    System.out.println("sucess");
                }
            }
        });
        System.out.println("OnCreate");
        activity = this;
    }

    public void changeByTime(long time){
        System.out.println("changeByTime");
        final Timer timer = new Timer();
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        stopCamera();
//                        prepareCamera();
//                        startCamera();
//                        timer.cancel();
                        break;
                }
                super.handleMessage(msg);
            }
        };
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(task,time);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0,int arg1,int arg2,int arg3){
        System.out.println("surfaceChanged");
        startCamera();

    }
    @Override
    public void surfaceCreated(SurfaceHolder arg0){
        initConnectToDB(this.getBaseContext());
        Intent intent = this.getIntent();
        System.out.println("intent----->"+intent.toString());
        if(intent.getStringExtra("taskId")!=null){
            id = Long.parseLong(intent.getStringExtra("taskId"));
            System.out.println("id---->"+id);
        }else {
            System.out.print("null");
        }
        System.out.println("surfaceCreated");
        prepareCamera();
    }
    @Override
    public  void surfaceDestroyed(SurfaceHolder arg0){
        System.out.println("surfaceDestroyed");
        stopCamera();
    }

    public void prepareCamera(){
        System.out.println("prepareCamera");
        camera = android.hardware.Camera.open();
        try{
            camera.setPreviewDisplay(surfaceHolder);
        }catch (IOException e){
            camera.release();
            camera = null;
        }
    }

    public void startCamera(){
        System.out.println("startCamera");
        if(previewRunning){
            camera.stopPreview();
        }
        try{
            android.hardware.Camera.Parameters parameters = camera.getParameters();
            parameters.setPictureFormat(PixelFormat.JPEG);
            camera.setParameters(parameters);
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            camera.autoFocus(null);
            takePicture();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void stopCamera(){
        System.out.println("stopCamera");
        if(camera!=null){
            camera.stopPreview();
            camera.release();
            camera = null;
            previewRunning = false;
        }
    }
    private android.hardware.Camera.PictureCallback jpegCallback = new android.hardware.Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
            String fileName = simpleDateFormat.format(date);
            final Bitmap bm = BitmapFactory.decodeByteArray(data,0,data.length);
            File file = new File("/sdcard/pictures/"+fileName+".jpg");
            try{
                file.createNewFile();
                FileOutputStream fileOS = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.JPEG,100,fileOS);
                fileOS.flush();
                fileOS.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
            System.out.print("taskId---->"+id);
            taskInfomationManager.deleteTask(id);
            activity.finish();
            Intent intent = new Intent();
            intent.putExtra("message","图片保存在/sdcard/pictures目录下,图片名称为"+fileName+".jpg");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setClass(XTFunction_Take_Picture.this,XTFunctionsListController.class);
            startActivity(intent);
    //        android.os.Process.killProcess(android.os.Process.myPid());
        }
    };
    public void onPreviewFrame(byte[] bytes, android.hardware.Camera camera){

    }
    public void takePicture(){

        button.performClick();
    }
    public void initConnectToDB(final Context context){
        taskInfomationManager = new TaskInfomationManager(context);
    }

    @Override
    protected void onDestroy() {
        System.out.println("onDestroy()");
        super.onDestroy();
    }
}
