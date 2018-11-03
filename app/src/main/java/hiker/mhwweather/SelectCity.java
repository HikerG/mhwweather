package hiker.mhwweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
    private ListView listView = null;
    private TextView cityselected = null;
    private List<City> listcity = MyApplication.getInstance().getCityList();
    private int listSize = listcity.size();
    private String[] city = new String[listSize];
    private SearchView searchView;
    private ArrayList<String> mSearchResult = new ArrayList<>();//搜索结果 放地区名
    private Map<String,String> nameToCode = new HashMap<>();//城市名到编码
    private Map<String,String> nameToPinyin = new HashMap<>();//城市名到拼音
    private ArrayAdapter<String> adapter;
    private String returnCode = "101010100"; //默认值为北京的代码
    private TextView mcitySelect;
    private ListView mlistView;
    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);
        mBackBtn = (ImageView) findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
        cityselected = (TextView) findViewById(R.id.title_name);
        Log.i("City",listcity.get(1).getCity());
        for(int i=0;i<listSize;i++){
            city[i] = listcity.get(i).getCity();
            Log.d("City",city[i]);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, city);
        listView = findViewById(R.id.list_view);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SelectCity.this, "你已选择：" + city[i], Toast.LENGTH_SHORT).show();
                cityselected.setText("当前地区："+city[i]);
                }
        });
        ////////
        mlistView = (ListView) findViewById(R.id.list_view); //////////////
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long
                    id) {
                String returnCityName = mSearchResult.get(position);
                Toast.makeText(SelectCity.this, "你选择" + returnCityName,
                        Toast.LENGTH_SHORT).show();
                returnCode = nameToCode.get(returnCityName); //通过城市名Key，获得城市编码Value
                Log.d("Msea", returnCode);
                mcitySelect.setText("当前城市：" + //更新TextView
                        returnCityName);
            }
        });
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconified(true); //需要点击搜索图标，才展开搜索框
        searchView.setQueryHint("请输入城市名称或拼音");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) { //搜索栏不空时，执行搜索
                    if (mSearchResult != null) //清空上次搜索结果
                        mSearchResult.clear();//遍历nameToPinyin 的键值（它包含所有城市名）
                    for (String str : nameToPinyin.keySet()) {
                        if
                                (str.contains(newText)||nameToPinyin.get(str).contains(newText)) {
                            mSearchResult.add(str);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {//实际不执行，文本框一变化就自动执行搜索
                Toast.makeText(SelectCity.this, "检索中", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        initView();
    }

    protected void initView() {
        myApplication = MyApplication.getInstance();
        ArrayList<City> mCityList = (ArrayList<City>) myApplication.getCityList();
        String strName;
        String strNamePinyin;
        String strCode;
        for (City city : mCityList) {
            strCode = city.getNumber();
            strName = city.getCity();
            strNamePinyin = Pinyin4jUtil.converterToSpell(strName); //城市名解析成拼音
            nameToCode.put(strName,strCode); //城市名到城市编码
            nameToPinyin.put(strName,strNamePinyin); //城市名到拼音
            mSearchResult.add(strName); //初始状态包含全部城市
        }
        adapter = new ArrayAdapter<> //新建适配器
                (SelectCity.this, android.R.layout.simple_list_item_1, mSearchResult);
        mlistView.setAdapter(adapter); //接上适配器
    }
    ////////////

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.title_back:
                int position = listView.getCheckedItemPosition();
                String select_cityCode = listcity.get(position).getNumber();
                Intent i = new Intent();
                i.putExtra("cityCode", select_cityCode);
                setResult(RESULT_OK, i);
                Log.d("citycode",select_cityCode);
                finish();
                break;
            default:
                break;
        }
    }
}