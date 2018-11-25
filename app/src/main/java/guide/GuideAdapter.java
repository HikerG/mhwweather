package guide;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class GuideAdapter extends PagerAdapter {
    Context context;
    List<View> listView;


    public GuideAdapter (Context context,List<View> listView){
        this.context = context;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        if (listView != null) {
            return listView.size();
        }
        return 0;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = listView.get(position);
        container.addView(view);
        return view;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view == object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       View view = (View)object;
       container.removeView(view);
        //container.removeView(splash_list.get(position));
            ((ViewPager) container).removeView((View) object);
    }
}
