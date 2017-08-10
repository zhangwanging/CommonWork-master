package com.hzu.jpg.commonwork.activity.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.ApplyJobActivity;
import com.hzu.jpg.commonwork.enity.SearchHistoryVo;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.utils.Utility;
import com.hzu.jpg.commonwork.utils.db.DBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView search_list;
    private EditText search_et;
    private DBManager mgr;
    private ArrayList<SearchHistoryVo> data;
    private List<SearchHistoryVo> list;
    private SearchContentAdapter adapter;
    MyHandler mHandler = null;
    private static final int UPDATE_VIEW = 1001;
    private static final int INSERT_DATA = 1002;
    private TextView search_delete_tv;
    private String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setWindowStatusBarColor(this, R.color.search_top);
        getXmlView();
        initSearchData();
    }

    private void initSearchData() {
        data = new ArrayList<>();
        data = getData();
        if (list.size() > 0) {
            search_delete_tv.setText("清空搜索历史");
            adapter = new SearchContentAdapter(SearchActivity.this, data);
            search_list.setAdapter(adapter);
            new Utility().setListViewHeightBasedOnChildren(search_list); //固定高度
        } else {
            //search_delete_tv.setVisibility(View.GONE);
            search_delete_tv.setText("暂无搜索记录");
            search_list.setVisibility(View.GONE);
        }
        search_et.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    search();
                }
                return false;
            }
        });
    }

    private void search() {
        String searchContext = search_et.getText().toString().trim();
        if (TextUtils.isEmpty(searchContext)) {
            ToastUtil.showToast("请输入搜索内容");
        } else {
            btn_search();
        }
    }

    // 获得搜索记录数据
    private ArrayList<SearchHistoryVo> getData() {
        data.clear();
        list = mgr.querySearchHistory();
        Collections.reverse(list); // 使用该方法使结果倒序显示
        for (int i = 0; i < list.size(); i++) {
            SearchHistoryVo item = new SearchHistoryVo();
            item.setContent(list.get(i).content);
            item.setSearchtime(list.get(i).searchtime);
            data.add(item);
        }
        return data;
    }

    private void initData() {
        data = getData();
        adapter = new SearchContentAdapter(SearchActivity.this, data);
        search_list.setAdapter(adapter);
        new Utility().setListViewHeightBasedOnChildren(search_list);
        if (list.size() > 0) {
            search_delete_tv.setText("清空搜索历史");
            search_delete_tv.setVisibility(View.VISIBLE);
            search_list.setVisibility(View.VISIBLE);
        } else {
            //search_delete_tv.setVisibility(View.GONE);
            search_delete_tv.setText("暂无搜索记录");
            search_list.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_close_tv:
                finish();
                break;
            case R.id.search_delete_tv: //清空历史
                mgr.deleteSearchHistory();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                mHandler.sendEmptyMessage(UPDATE_VIEW);
                break;
        }
    }

    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VIEW: // 更新搜索记录列表
                    initData();
                    break;
                case INSERT_DATA:
                default:
                    break;
            }
        }
    }

    private void btn_search() {  //搜索
        if (!search_et.getText().toString().trim().equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SearchHistoryVo item = new SearchHistoryVo();
            item.content = search_et.getText().toString();
            item.searchtime = sdf.format(new Date());
            mgr.addSearchResult(item); // 将搜索记录添加到SQLite中
            mHandler.sendEmptyMessage(UPDATE_VIEW);
        }
        Intent intent = new Intent(SearchActivity.this, ApplyJobActivity.class);
        intent.putExtra("search", search_et.getText().toString());
        startActivity(intent);
    }

    private void getXmlView() {
        mgr = new DBManager(this);
        mHandler = new MyHandler();
        //action = new JobAction(this);
        search_list = (ListView) this.findViewById(R.id.listContent);

        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SearchHistoryVo searchHistoryVo = adapter.getAllData().get(i);
                search = searchHistoryVo.getContent();
                Intent intent = new Intent(SearchActivity.this, ApplyJobActivity.class);
                intent.putExtra("search", search);
                startActivity(intent);
            }
        });

        this.findViewById(R.id.search_close_tv).setOnClickListener(this);
        search_et = (EditText) this.findViewById(R.id.search_et);
        this.findViewById(R.id.search_delete_tv).setOnClickListener(this);
        search_delete_tv = (TextView) this.findViewById(R.id.search_delete_tv);
    }

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class SearchContentAdapter extends BaseAdapter {

        private ArrayList<SearchHistoryVo> data;
        private LayoutInflater inflater;

        public SearchContentAdapter(Context context, ArrayList<SearchHistoryVo> data) {
            inflater = LayoutInflater.from(context);
            this.data = data;
        }

        public ArrayList<SearchHistoryVo> getAllData() {
            return data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImgTextWrapper wrapper;
            SearchHistoryVo searchHistoryVo = data.get(position);
            if (convertView == null) {
                wrapper = new ImgTextWrapper();
                convertView = inflater.inflate(R.layout.search_list_item, null);
                wrapper.search_delete_icon = (ImageButton) convertView.findViewById(R.id.search_delete_icon);
                wrapper.tv_context = (TextView) convertView.findViewById(R.id.tv_context);
                convertView.setTag(wrapper);
            } else {
                wrapper = (ImgTextWrapper) convertView.getTag();
            }
            wrapper.tv_context.setText(searchHistoryVo.getContent());
            wrapper.search_delete_icon.setOnClickListener(new deleteContextListener(position));
            return convertView;
        }

        class ImgTextWrapper {
            ImageButton search_delete_icon;
            TextView tv_context;
        }

        class deleteContextListener implements View.OnClickListener {

            private int position;

            private deleteContextListener(int position) {
                this.position = position;
            }

            @Override
            public void onClick(View view) {
                SearchHistoryVo searchHistoryVo = data.get(position);
                mgr.deleteSearchResultItem(searchHistoryVo);
                initData();
            }
        }
    }
}
