package guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import hiker.mhwweather.MainActivity;
import hiker.mhwweather.R;

public class AGuideActivity extends Activity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private ViewPagerAdapter vpAdapter;
    private ArrayList<View> views;

    private View view1, view2, view3;
    private Button startBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);

        initView();

        initData();
    }


    private void initView() {
        //填充布局文件
        LayoutInflater mLi = LayoutInflater.from(this);
        view1 = mLi.inflate(R.layout.page1_guide, null);
        view2 = mLi.inflate(R.layout.page2_guide, null);
        view3 = mLi.inflate(R.layout.page3_guide, null);

        //实例化ViewPager、ArrayList
        viewPager = (ViewPager) findViewById(R.id.guidePage);
        views = new ArrayList<View>();

        //将布局填充到适配器中
        vpAdapter = new ViewPagerAdapter(views);
        startBt = (Button) view3.findViewById(R.id.startBtn);
    }


    private void initData() {

        viewPager.setOnPageChangeListener(this);

        viewPager.setAdapter(vpAdapter);

        //将view1、view2、view3、view4添加到ArrayList数组中
        views.add(view1);
        views.add(view2);
        views.add(view3);

        //设置监听
        startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startbutton();
            }
        });
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {

    }

    //跳转到主界面
    private void startbutton() {
        Intent intent = new Intent();
        intent.setClass(AGuideActivity.this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}

