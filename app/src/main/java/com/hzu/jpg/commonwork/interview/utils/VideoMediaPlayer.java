package com.hzu.jpg.commonwork.interview.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.hzu.jpg.commonwork.R;

/**
 * Created by ThinkPad on 2017/5/29.
 */

public class VideoMediaPlayer {
    private MediaPlayer begin;
    private Context context;
    private boolean flag_thread=true;
    private Thread t;

    public VideoMediaPlayer(Context context) {
        this.context = context;
    }

    public void startPlayer() {
        AudioManager audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        begin = MediaPlayer.create(context, R.raw.video_start);
        begin.setAudioStreamType(audioManager.getMode());
        t=new Thread(){
            public void run(){
                while(flag_thread){
                    begin.start();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    public void stopPlayer() {
        if(begin!=null) {
            begin.reset();
            flag_thread=false;
            t.interrupt();
            begin.stop();
            begin.release();
            begin = null;
        }
    }

}
