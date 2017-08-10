package com.hzu.jpg.commonwork.fragment.mainFragment;

import android.view.View;

import com.hzu.jpg.commonwork.R;

import me.fangx.common.ui.fragment.BaseLazyFragment;
import me.fangx.common.util.eventbus.EventCenter;

/**
 * Created by Administrator on 2017/1/21.
 */

public class MessageFragment extends BaseLazyFragment{
    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.framgent_message;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
}
