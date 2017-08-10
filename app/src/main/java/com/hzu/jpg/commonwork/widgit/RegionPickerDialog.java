package com.hzu.jpg.commonwork.widgit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.filterAdapter.FilterLeftAdapter;
import com.hzu.jpg.commonwork.adapter.filterAdapter.FilterRightAdapter;
import com.hzu.jpg.commonwork.enity.FilterEntity;
import com.hzu.jpg.commonwork.enity.FilterTwoEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/16.
 */

public class RegionPickerDialog extends Dialog {


    private static final String TAG = "RegionPickerDialog";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_next)
    ImageButton btnNext;
    @Bind(R.id.list_city)
    ListView listCity;
    @Bind(R.id.list_district)
    ListView listDistrict;

    private FilterLeftAdapter leftAdapter;
    private FilterRightAdapter rightAdapter;
    List<FilterTwoEntity> jobs;
    FilterTwoEntity FirstLeftSelectedEntity;
    private FilterEntity FirstRightSelectedEntity;
    private Context mContext;
    private OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @param context
     * @param jobs
     */
    public RegionPickerDialog(Context context, List<FilterTwoEntity> jobs) {
        super(context);
        this.mContext = context;
        this.jobs = jobs;
        View inflate = getLayoutInflater().inflate(R.layout.dialog_select_region, null);
        listCity = (ListView) inflate.findViewById(R.id.list_city);
        listDistrict = (ListView) inflate.findViewById(R.id.list_district);

        setContentView(inflate);
        initView();
        initData();
    }

    private void initView() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        attributes.height = (int) (dm.heightPixels * 0.8);
        window.setAttributes(attributes);
    }


    private void initData() {
        leftAdapter = new FilterLeftAdapter(mContext, jobs);
        listCity.setAdapter(leftAdapter);
        if (FirstLeftSelectedEntity == null) {
            if (jobs != null && jobs.size() > 0) {
                FirstLeftSelectedEntity = jobs.get(0);
            }
        }
        leftAdapter.setSelectedEntity(FirstLeftSelectedEntity);
        listCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirstLeftSelectedEntity = jobs.get(position);
                leftAdapter.setSelectedEntity(FirstLeftSelectedEntity);

                // 右边列表视图
                rightAdapter = new FilterRightAdapter(mContext, FirstLeftSelectedEntity.getList());
                listDistrict.setAdapter(rightAdapter);
                rightAdapter.setSelectedEntity(FirstRightSelectedEntity);
            }
        });

        rightAdapter = new FilterRightAdapter(mContext, FirstLeftSelectedEntity.getList());
        listDistrict.setAdapter(rightAdapter);
        rightAdapter.setSelectedEntity(FirstRightSelectedEntity);
        listDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirstRightSelectedEntity = FirstLeftSelectedEntity.getList().get(position);
                rightAdapter.setSelectedEntity(FirstRightSelectedEntity);
                Log.e(TAG, "onItemClick: " + FirstRightSelectedEntity.getKey() + "--" + FirstLeftSelectedEntity.getType(), null);
                if (listener != null) {
                    listener.OnItemClick(FirstLeftSelectedEntity, FirstRightSelectedEntity);
                    RegionPickerDialog.this.cancel();
                }
            }
        });
    }


    public void showDialog() {

        Window window = getWindow();
        window.setWindowAnimations(R.style.RegionDialogAnimation);
        window.setBackgroundDrawableResource(R.color.transparent);
        setCanceledOnTouchOutside(true);
        show();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }


    @OnClick({R.id.back, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.btn_next:
                break;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void OnItemClick(FilterTwoEntity FirstLeftSelectedEntity, FilterEntity FirstRightSelectedEntity);
    }
}
