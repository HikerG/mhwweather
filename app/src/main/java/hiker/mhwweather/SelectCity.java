package hiker.mhwweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.MyApplication;
import bean.City;
import util.Pinyin4jUtil;

public class SelectCity extends Activity implements View.OnClickListener{
    private ImageView mBackBtn;
    private SearchView mSearch=null;
    private ListView mList = null;
    private TextView mCity = null;
    private ArrayList<String> mSearchList=null;
    private ArrayList<String> mCodeList=null;
    private ArrayList<String> mArrayList;
    private List<City> cityList = MyApplication.getInstance().getCityList();
    //private int listSize = listcity.size();
   // private String[] city = new String[listSize];
    private String uCitycode="101010100";
    private String uCityname="北京";
    private MyApplication myApplication;
    private Map<String,String> toCode=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_city);

        mBackBtn = (ImageView) findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);

        mList=(ListView) findViewById(R.id.title_list);
        mCity = (TextView) findViewById(R.id.title_name);

        myApplication=(MyApplication)getApplication();
        mArrayList=new ArrayList<String>();
        mSearchList=new ArrayList<String>();
        mCodeList=new ArrayList<String>();
        toCode=new HashMap<>();
        for(int i=0;i<cityList.size();i++) {
            String cityName = cityList.get(i).getCity();
            mArrayList.add(cityName);
        }
        String strName;
        String strCode;
        for(City cityName:cityList){
            strCode=cityName.getNumber();
            strName=cityName.getCity();
            toCode.put(strName,strCode);
            mSearchList.add(strName);
            mCodeList.add(toCode.get(strName));
        }
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(SelectCity.this,android.R.layout.simple_list_item_1,mSearchList);
        mList.setAdapter(adapter);

        mSearch=(SearchView) findViewById(R.id.title_search);
        mSearch.setIconified(true);
        //mSearch.setQueryHint("请输入城市名");

        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    if(mSearchList!=null){
                        mSearchList.clear();
                        mCodeList.clear();
                    }
                    for(String str:toCode.keySet()){
                        if(str.contains(newText)){
                            mSearchList.add(str);
                            mCodeList.add(toCode.get(str));
                        }
                    }
                    mList.setAdapter(adapter);
                }
                return true;
            }
        });
        //        监听listview的显示，返回城市代码
        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uCitycode=mCodeList.get(position);
                Log.d("updatecode",uCitycode);
//                title更新城市名
                uCityname=mSearchList.get(position);
                mCity.setText("当前城市："+uCityname);
            }
        };
        mList.setOnItemClickListener(itemClickListener);
    }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {   //此时citycode已更新，通过intent返回给mainActivity
                case R.id.title_back:
                    Intent i=new Intent();
                    i.putExtra("cityCode",uCitycode);
                    setResult(RESULT_OK,i);
                    finish();
                    break;
                default:
                    break;
            }
        }


}