package guide;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import hiker.mhwweather.MainActivity;
import hiker.mhwweather.R;

public class Welcome extends Activity implements Runnable {

    //判断是否第一次启动程序
    private boolean isFirstUse;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        /**
         * 开启线程
         */
        new Thread(this).start();
    }

    public void run() {
        try {
            /**
             * 线程休眠时间
             */
            Thread.sleep(20);

            //SharedPreferences存储
            SharedPreferences preferences = getSharedPreferences("isFirstUse",MODE_WORLD_READABLE);

            isFirstUse = preferences.getBoolean("isFirstUse", true);

            /**
             *判断是否第一次启动程序
             *如果是第一次启动GuideActivity.class，如果不是这进入主界面
             */
            if (isFirstUse) {
                startActivity(new Intent(Welcome.this, GuideActivity.class));
            } else {
                startActivity(new Intent(Welcome.this, MainActivity.class));
            }
            finish();


            SharedPreferences.Editor editor = preferences.edit();

            editor.putBoolean("isFirstUse", false);

            editor.commit();


        } catch (InterruptedException e) {

        }
    }
}