package com.hzu.jpg.commonwork.event;

/**
 * Created by Azusa on 2016/3/19.
 */
public class DownLoadCompleteEvent {
    private boolean isDowned = false;

    public DownLoadCompleteEvent(boolean isDowned) {
        this.isDowned = isDowned;
    }

    public boolean isDowned() {
        return isDowned;
    }
}
