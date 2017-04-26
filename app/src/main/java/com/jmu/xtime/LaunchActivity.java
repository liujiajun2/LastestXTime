package com.jmu.xtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import java.util.logging.Handler;

/**
 * Created by 倾城一世 on 2017/3/21.
 */

public class LaunchActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new splashhandler(),3000);
    }
    class splashhandler implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            LaunchActivity.this.finish();
        }
    }
}
