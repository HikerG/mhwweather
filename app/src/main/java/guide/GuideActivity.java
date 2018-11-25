package guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import hiker.mhwweather.MainActivity;
import hiker.mhwweather.R;

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener {

    private ViewPager pagerView;
    private List<View> views;
    private GuideAdapter guideAdapter;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);

        pagerView = (ViewPager) findViewById(R.id.guidePage);

        views = new ArrayList<View>();
        views.add(LayoutInflater.from(this).inflate(R.layout.page1_guide,null));
        views.add(LayoutInflater.from(this).inflate(R.layout.page2_guide,null));
        views.add(LayoutInflater.from(this).inflate(R.layout.page3_guide,null));

        guideAdapter = new GuideAdapter(this,views);


        startBtn = (Button) LayoutInflater.from(this).inflate(R.layout.page3_guide,null).findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startbutton();
            }
        });

        pagerView.setAdapter(guideAdapter);
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
        intent.setClass(GuideActivity.this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
