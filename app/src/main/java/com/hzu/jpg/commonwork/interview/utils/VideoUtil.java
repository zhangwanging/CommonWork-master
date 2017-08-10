package com.hzu.jpg.commonwork.interview.utils;

import android.content.Context;
import android.media.AudioManager;


/**
 * Created by ThinkPad on 2017/6/1.
 */

public class VideoUtil {
    public static boolean isHeadSetOn(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.isWiredHeadsetOn();
    }
}
