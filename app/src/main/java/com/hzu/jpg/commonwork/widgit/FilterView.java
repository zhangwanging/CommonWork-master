package com.hzu.jpg.commonwork.widgit;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.filterAdapter.FilterLeftAdapter;
import com.hzu.jpg.commonwork.adapter.filterAdapter.FilterOneAdapter;
import com.hzu.jpg.commonwork.adapter.filterAdapter.FilterRightAdapter;
import com.hzu.jpg.commonwork.enity.FilterData;
import com.hzu.jpg.commonwork.enity.FilterEntity;
import com.hzu.jpg.commonwork.enity.FilterTwoEntity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class FilterView extends LinearLayout implements View.OnClickListener {


    private static final String TAG = "FilterView";
    @Bind(R.id.ll_head_layout)
    LinearLayout llHeadLayout;
    @Bind(R.id.view_mask_bg)
    View viewMaskBg;
    @Bind(R.id.lv_left)
    ListView lvLeft;
    @Bind(R.id.lv_right)
    ListView lvRight;
    @Bind(R.id.ll_content_list_view)
    LinearLayout llContentListView;
    @Bind(R.id.tv_first_title)
    TextView tvFistTitle;
    @Bind(R.id.iv_first_arrow)
    ImageView ivFirstArrow;
    @Bind(R.id.ll_first)
    LinearLayout llFirst;
    @Bind(R.id.tv_second_title)
    TextView tvSecondTitle;
    @Bind(R.id.iv_second_arrow)
    ImageView ivSecondArrow;
    @Bind(R.id.ll_second)
    LinearLayout llSecond;
    @Bind(R.id.tv_third_title)
    TextView tvThirdTitle;
    @Bind(R.id.iv_third_arrow)
    ImageView ivThirdArrow;
    @Bind(R.id.ll_third)
    LinearLayout llThird;
    @Bind(R.id.tv_fourth_title)
    TextView tvFourthTitle;
    @Bind(R.id.iv_fourth_arrow)
    ImageView ivFourthArrow;
    @Bind(R.id.ll_fourth)
    LinearLayout llFourth;
    private Context mContext;
    private Activity mActivity;

    private int filterPosition = -1;
    private int lastFilterPosition = -1;
    public static final int POSITION_FIRST = 0; // 区域的编号
    public static final int POSITION_SECOND = 1; // 岗位的编号
    public static final int POSITION_THIRD = 2; // 薪资的编号
    public static final int POSITION_FOURTH = 3; // 福利的编号

    private boolean isShowing = false;
    private int panelHeight;
    private FilterData filterData;

    private FilterLeftAdapter firstLeftAdapter;
    private FilterRightAdapter firstRightAdapter;
    private FilterOneAdapter firstAdapter;

    private FilterOneAdapter secondAdapter;
    private FilterOneAdapter thirdAdapter;
    private FilterOneAdapter fourthAdapter;

    private FilterTwoEntity FirstLeftSelectedEntity; // 被选择的区域项左侧数据
    private FilterEntity FirstRightSelectedEntity; // 被选择的区域项右侧数据
    private FilterEntity firstEntity;

    private FilterEntity SecondSelectedEntity; // 被选择的岗位项
    private FilterEntity thirdSelectedEntity; // 被选择的薪资项
    private FilterEntity fourthSelectedEntity; // 被选择的福利项

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_filter_layout, this);
        ButterKnife.bind(this, view);
        initView();
        initListener();
    }

    private void initView() {
        viewMaskBg.setVisibility(GONE);
        llContentListView.setVisibility(GONE);
    }

    private void initListener() {
        llFirst.setOnClickListener(this);
        llSecond.setOnClickListener(this);
        llThird.setOnClickListener(this);
        llFourth.setOnClickListener(this);
        viewMaskBg.setOnClickListener(this);
        llContentListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_first:
                filterPosition = 0;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_second:
                filterPosition = 1;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_third:
                filterPosition = 2;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_fourth:
                filterPosition = 3;
                Log.e(TAG, "onClick: 4", null);
                if(onFilterClickListener != null){
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.view_mask_bg:
                hide();
                break;
        }
    }

    // 复位筛选的显示状态
    public void resetFilterStatus() {
        tvFistTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivFirstArrow.setImageResource(R.mipmap.home_down_arrow);

        tvSecondTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivSecondArrow.setImageResource(R.mipmap.home_down_arrow);

        tvThirdTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivThirdArrow.setImageResource(R.mipmap.home_down_arrow);

        tvFourthTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivFourthArrow.setImageResource(R.mipmap.home_down_arrow);
    }

    // 复位所有的状态
    public void resetAllStatus() {
        resetFilterStatus();
        hide();
    }

    // 第一位置有两级目录时
    private void setLocationAdapter() {
        lvLeft.setVisibility(VISIBLE);
        lvRight.setVisibility(VISIBLE);

        // 左边列表视图
        firstLeftAdapter = new FilterLeftAdapter(mContext, filterData.getFirstTwo());
        lvLeft.setAdapter(firstLeftAdapter);
        if (FirstLeftSelectedEntity == null) {
            FirstLeftSelectedEntity = filterData.getFirstTwo().get(0);
        }
        firstLeftAdapter.setSelectedEntity(FirstLeftSelectedEntity);

        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirstLeftSelectedEntity = filterData.getFirstTwo().get(position);
                firstLeftAdapter.setSelectedEntity(FirstLeftSelectedEntity);

                // 右边列表视图
                firstRightAdapter = new FilterRightAdapter(mContext, FirstLeftSelectedEntity.getList());
                lvRight.setAdapter(firstRightAdapter);
                firstRightAdapter.setSelectedEntity(FirstRightSelectedEntity);
            }
        });

        // 右边列表视图
        firstRightAdapter = new FilterRightAdapter(mContext, FirstLeftSelectedEntity.getList());
        lvRight.setAdapter(firstRightAdapter);
        firstRightAdapter.setSelectedEntity(FirstRightSelectedEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirstRightSelectedEntity = FirstLeftSelectedEntity.getList().get(position);
                firstRightAdapter.setSelectedEntity(FirstRightSelectedEntity);
                if (onItemFirstClickListener != null) {
                    onItemFirstClickListener.onItemFirstClick(FirstLeftSelectedEntity, FirstRightSelectedEntity);
                }
                hide();
            }
        });
    }

    //第一位置只有一级目录时
    private void setFirstAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        firstAdapter = new FilterOneAdapter(mContext, filterData.getFirstOne());
        lvRight.setAdapter(firstAdapter);

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirstRightSelectedEntity = filterData.getFirstOne().get(position);
                firstAdapter.setSelectedEntity(FirstRightSelectedEntity);
                if (onItemOnlyFirstClickListener != null) {
                    onItemOnlyFirstClickListener.onItemOnlyFirstClick(FirstRightSelectedEntity);
                }
                hide();
            }
        });
    }


    // 设置岗位数据
    private void setJobsAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        secondAdapter = new FilterOneAdapter(mContext, filterData.getSecond());
        lvRight.setAdapter(secondAdapter);

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SecondSelectedEntity = filterData.getSecond().get(position);
                secondAdapter.setSelectedEntity(SecondSelectedEntity);
                if (onItemSecondClickListener != null) {
                    onItemSecondClickListener.onItemSecondClick(SecondSelectedEntity);
                }
                hide();
            }
        });
    }

    // 设置薪资数据
    private void setSalaryAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);
        thirdAdapter = new FilterOneAdapter(mContext, filterData.getThird());
        lvRight.setAdapter(thirdAdapter);

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                thirdSelectedEntity = filterData.getThird().get(position);
                thirdAdapter.setSelectedEntity(thirdSelectedEntity);
                if (onItemThirdClickListener != null) {
                    onItemThirdClickListener.onItemThirdClick(thirdSelectedEntity);
                }
                hide();
            }
        });

    }

    // 设置福利数据
    private void setWelfareAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        fourthAdapter = new FilterOneAdapter(mContext, filterData.getFourth());
        lvRight.setAdapter(fourthAdapter);

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fourthSelectedEntity = filterData.getFourth().get(position);
                fourthAdapter.setSelectedEntity(fourthSelectedEntity);
                if (onItemFourthClickListener != null) {
                    onItemFourthClickListener.onItemFourthClick(fourthSelectedEntity);
                }
                hide();
            }
        });

    }

    // 动画显示
    public void show(int position) {
        if (isShowing && lastFilterPosition == position){
            hide();
            return;
        }
        resetFilterStatus();
        rotateArrowUp(position);
        rotateArrowDown(lastFilterPosition);
        lastFilterPosition = position;

        switch (position) {
            case POSITION_FIRST:
                tvFistTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimaryDark));
                ivFirstArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setFirstAdapter();
                break;
            case POSITION_SECOND:
                tvSecondTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimaryDark));
                ivSecondArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setJobsAdapter();
                break;
            case POSITION_THIRD:
                tvThirdTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimaryDark));
                ivThirdArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setSalaryAdapter();
                break;
            case POSITION_FOURTH:
                Log.e(TAG, "show: 4", null);
                tvFourthTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimaryDark));
                ivFourthArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setWelfareAdapter();
                break;
        }

        if (isShowing) return;
        isShowing = true;
        viewMaskBg.setVisibility(VISIBLE);
        llContentListView.setVisibility(VISIBLE);
        Log.e(TAG, "show: viewMaskBg+llContentListView" + viewMaskBg.isShown(),null );
        llContentListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llContentListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                panelHeight = llContentListView.getHeight();
                ObjectAnimator.ofFloat(llContentListView, "translationY", -panelHeight, 0).setDuration(200).start();
            }
        });
    }

    // 隐藏动画
    public void hide() {
        isShowing = false;
        resetFilterStatus();
        rotateArrowDown(filterPosition);
        rotateArrowDown(lastFilterPosition);
        filterPosition = -1;
        lastFilterPosition = -1;
        viewMaskBg.setVisibility(View.GONE);
        llContentListView.setVisibility(GONE);
        ObjectAnimator.ofFloat(llContentListView, "translationY", 0, -panelHeight).setDuration(200).start();
    }

    // 旋转箭头向上
    private void rotateArrowUp(int position) {
        switch (position) {
            case POSITION_FIRST:
                rotateArrowUpAnimation(ivFirstArrow);
                break;
            case POSITION_SECOND:
                rotateArrowUpAnimation(ivSecondArrow);
                break;
            case POSITION_THIRD:
                rotateArrowUpAnimation(ivThirdArrow);
                break;
            case POSITION_FOURTH:
                rotateArrowUpAnimation(ivFourthArrow);
                break;
        }
    }

    // 旋转箭头向下
    private void rotateArrowDown(int position) {
        switch (position) {
            case POSITION_FIRST:
                rotateArrowDownAnimation(ivFirstArrow);
                break;
            case POSITION_SECOND:
                rotateArrowDownAnimation(ivSecondArrow);
                break;
            case POSITION_THIRD:
                rotateArrowDownAnimation(ivThirdArrow);
                break;
            case POSITION_FOURTH:
                rotateArrowDownAnimation(ivFourthArrow);
                break;
        }
    }

    // 旋转箭头向上
    public static void rotateArrowUpAnimation(final ImageView iv) {
        if (iv == null) return;
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        iv.startAnimation(animation);
    }

    // 旋转箭头向下
    public static void rotateArrowDownAnimation(final ImageView iv) {
        if (iv == null) return;
        RotateAnimation animation = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        iv.startAnimation(animation);
    }

    // 设置筛选数据
    public void setFilterData(Activity activity, FilterData filterData) {
        this.mActivity = activity;
        this.filterData = filterData;
    }

    public void setTitles(String first, String second, String third, String fourth){
        tvFistTitle.setText(first);
        tvSecondTitle.setText(second);
        tvThirdTitle.setText(third);
        tvFourthTitle.setText(fourth);
    }

    // 是否显示
    public boolean isShowing() {
        return isShowing;
    }

    public int getFilterPosition() {
        return filterPosition;
    }

    // 筛选视图点击
    private OnFilterClickListener onFilterClickListener;

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public interface OnFilterClickListener {
        void onFilterClick(int position);
    }

    // 第一项点击 2级时
    private OnItemFirstClickListener onItemFirstClickListener;

    public void setOnItemFirstClickListener(OnItemFirstClickListener onItemFirstClickListener) {
        this.onItemFirstClickListener = onItemFirstClickListener;
    }

    public interface OnItemFirstClickListener {
        void onItemFirstClick(FilterTwoEntity leftEntity, FilterEntity rightEntity);
    }

    // 第二项点击 1级时
    private OnItemOnlyFirstClickListener onItemOnlyFirstClickListener;

    public void setOnItemOnlyFirstClickListener(OnItemOnlyFirstClickListener onItemOnlyFirstClickListener) {
        this.onItemOnlyFirstClickListener = onItemOnlyFirstClickListener;
    }

    public interface OnItemOnlyFirstClickListener {
        void onItemOnlyFirstClick(FilterEntity entity);
    }

    // 第二项点击
    private OnItemSecondClickListener onItemSecondClickListener;

    public void setOnItemSecondClickListener(OnItemSecondClickListener onItemSecondClickListener) {
        this.onItemSecondClickListener = onItemSecondClickListener;
    }

    public interface OnItemSecondClickListener {
        void onItemSecondClick(FilterEntity entity);
    }

    // 第三项点击
    private OnItemThirdClickListener onItemThirdClickListener;

    public void setOnItemThirdClickListener(OnItemThirdClickListener onItemThirdClickListener) {
        this.onItemThirdClickListener = onItemThirdClickListener;
    }

    public interface OnItemThirdClickListener {
        void onItemThirdClick(FilterEntity entity);
    }

    // 第四项点击
    private OnItemFourthClickListener onItemFourthClickListener;

    public void setOnItemFourthClickListener(OnItemFourthClickListener onItemFourthClickListener) {
        this.onItemFourthClickListener = onItemFourthClickListener;
    }

    public interface OnItemFourthClickListener {
        void onItemFourthClick(FilterEntity entity);
    }

}
