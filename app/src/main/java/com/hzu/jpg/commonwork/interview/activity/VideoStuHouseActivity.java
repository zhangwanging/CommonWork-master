package com.hzu.jpg.commonwork.interview.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.interview.service.GetStuTokenService;
import com.hzu.jpg.commonwork.interview.utils.VideoMediaPlayer;
import com.hzu.jpg.commonwork.interview.utils.VideoUtil;
import com.hzu.jpg.commonwork.interview.widgit.VideoProgressView;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.widgit.CircleImageView;
import com.shishimao.sdk.Configs;
import com.shishimao.sdk.Errors;
import com.shishimao.sdk.LocalStream;
import com.shishimao.sdk.LocalStream.StreamObserver;
import com.shishimao.sdk.RTCat;
import com.shishimao.sdk.Receiver;
import com.shishimao.sdk.RemoteStream;
import com.shishimao.sdk.Sender;
import com.shishimao.sdk.Session;
import com.shishimao.sdk.apprtc.AppRTCAudioManager;
import com.shishimao.sdk.tools.L;
import com.shishimao.sdk.view.VideoPlayer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class VideoStuHouseActivity extends AppCompatActivity {

    private final static String TAG = "VideoHouse";

    //界面控件
    @Bind(R.id.local_video_render)
    VideoPlayer localVideoRenderer;
    @Bind(R.id.local_video_render_me)
    VideoPlayer localVideoRendererme;
    @Bind(R.id.bt_shutdown)
    CircleImageView btShutdown;
    @Bind(R.id.view_video_progress)
    VideoProgressView videoProgressView;

    //RTCat变量
    private RTCat cat;
    private LocalStream localStream;
    private Session session;

    //控制变量
    private volatile boolean thread_flag = true; //控制请求status线程
    private String statusCurrent = ""; //当前连接状态

    //数据
    private static String status = "";
    private String studentToken = "";

    private VideoMediaPlayer videoMediaPlayer;
    private AudioManager audioManager;

    private Thread thread;
    private boolean flagTakePhone;
    private boolean flagInSessionOneTime;

    private String companyName = "";


    static class MyHandler extends Handler {
        WeakReference<VideoStuHouseActivity> weakReference;

        MyHandler(VideoStuHouseActivity activity) {
            weakReference = new WeakReference<VideoStuHouseActivity>(activity);
        }

        public void handleMessage(Message msg) {
            final VideoStuHouseActivity activity = weakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        activity.videoProgressView.setText("后台人员正在打开房间...");
                        activity.videoProgressView.setVisible(View.VISIBLE);
                        break;
                    case 2:
                        activity.videoProgressView.setText("后台人员忙碌中，请您稍等一会~");
                        activity.videoProgressView.setVisible(View.VISIBLE);
                        break;
                    case 3:
                        ToastUtil.showToast_center("本次的视频面试到这里结束了，谢谢您的参加~");
                        break;
                    case 5:
                        ToastUtil.showToast_center("对方退出了视频房间~");
                        break;
                    case 6:
                        activity.videoProgressView.setText("   您已经进入房间，\n请耐心等待对方进入...");
                        activity.videoProgressView.setVisible(View.VISIBLE);
                        break;
                    case 7:
                        activity.videoProgressView.setVisible(View.INVISIBLE);
                        break;
                    case 8:
                        ToastUtil.showToast_center("您已退出视频房间~");

                }
            }
        }
    }

    private final MyHandler handler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keepSreenOn();
        transparent();
        setContentView(R.layout.activity_video_house);
        ButterKnife.bind(this);
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        init();
        updateStatus();
    }

    private void keepSreenOn() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void transparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
    }

    private void calling() {
        videoMediaPlayer = new VideoMediaPlayer(this);
        videoMediaPlayer.startPlayer();
    }

    private void init() {
        iniVideoWaining();
        Intent intent = getIntent();
        status = intent.getStringExtra("status");
        studentToken = intent.getStringExtra("studentToken");

        Log.e(TAG, "status=" + status + " studentToken=" + studentToken);

        btShutdown.setImageResource(R.mipmap.video_on);

        localVideoRendererme.setZOrderOnTop(true);

        flagTakePhone = true;
        flagInSessionOneTime = true;

        //初始化设置
        if (VideoUtil.isHeadSetOn(this)) {
            cat = new RTCat(this, true, true, true, true, AppRTCAudioManager.AudioDevice.WIRED_HEADSET, RTCat.CodecSupported.H264, L.VERBOSE);
        } else {
            cat = new RTCat(this, true, true, true, true, AppRTCAudioManager.AudioDevice.SPEAKER_PHONE, RTCat.CodecSupported.H264, L.VERBOSE);
        }
        cat.addObserver(new RTCat.RTCatObserver() {
            @Override
            public void init() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Cat初始化完成
                        Log.e(TAG, "RTCat initialize finish");
                        initPlayer();
                    }
                });
            }
        });
        cat.init();
        calling();
    }

    private void initPlayer() {

        cat.initVideoPlayer(localVideoRenderer);
        cat.initVideoPlayer(localVideoRendererme);
        //本地流初始化
        localStream = cat.createStream(true, true, 15, RTCat.VideoFormat.Lv9, LocalStream.CameraFacing.FRONT);

        localStream.addObserver(new StreamObserver() {

            @Override
            public void error(Errors errors) {
                Log.e(TAG, "localString.StreamObserver.error=" + errors.message);
                ToastUtil.showToast("本机视频初始化失败呀!");
                VideoStuHouseActivity.this.finish();
            }

            @Override
            public void afterSwitch(boolean isFrontCamera) {
            }

            @Override
            public void accepted() {
                Log.e(TAG, "localString.StreamObserver.accepted");
                if (localStream != null)
                    localStream.play(localVideoRendererme);
            }
        });
        localStream.init();
    }

    private synchronized void initSession() {
        Log.e(TAG, "studentToken=" + studentToken);
        if(cat!=null) {
            session = cat.createSession(studentToken, Session.SessionType.P2P);
            SessionHandler sh = new SessionHandler();
            session.addObserver(sh);
        }
    }

    private void chatting() {
        Log.e(TAG, "call chatting seccessfully!");
        thread = new Thread() {
            @Override
            public void run() {
                initSession();
                while (thread_flag) {
                    Log.e(TAG, "currentThreadID=" + Thread.currentThread().getId());
                    synchronized (VideoStuHouseActivity.class) {
                        if (!status.equals(statusCurrent)) {
                            statusCurrent = status;
                            if (status.equals("start")) {
                                Log.e(TAG, "starting...");
                                sendMessage(1);
                                inSession();
                            } else if (status.equals("wait") || status.equals("noaccept") || status.equals("closed")) {
                                Log.e(TAG, "only student can receive the signal 'closed'...");
                                sendMessage(3);
                                VideoStuHouseActivity.this.finish();
                                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                            }
                        }
                    }
                    updateStatus();
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    private void sendMessage(int i) {
        Message msg = handler.obtainMessage();
        msg.what = i;
        handler.sendMessage(msg);
    }

    private void updateStatus() {
        OkHttpUtils.post()
                .url(Config.URL_GET_VIDEO_STATUS)
                .addParams("studentId", MyApplication.user.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showNetError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");
                            Log.e(TAG, "updateStatus=" + status);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private synchronized void inSession() {
        Log.e(TAG, "inSession session'state=" + session.getState().name());
        if (session != null && flagInSessionOneTime) {
            session.connect();
            flagInSessionOneTime = false;
        }
    }

    private synchronized void outSession() {
        Log.e(TAG, "outSession session'state=" + session.getState().name());
        if (session != null) {
            session.disconnect();
        }

    }


    class SessionHandler implements Session.SessionObserver {

        @Override
        public void in(String s) {
            Log.e(TAG, "SessionHandler.in=" + s);
            session.sendTo(localStream, false, null, s);
        }

        @Override
        public void out(String s) {
            if (localVideoRenderer != null) {
                Log.e(TAG, "SessionHandler.out=" + s);
                sendMessage(5);
                VideoStuHouseActivity.this.finish();
            }
        }

        @Override
        public void connected(ArrayList arrayList) {
            Log.e(TAG, "SessionHandler.connected");
            sendMessage(6);
            session.send(localStream, false, null);
        }

        @Override
        public void remote(final Receiver receiver) {
            if (receiver == null) return;
            Log.e(TAG, "SessionHandler.remote");
            sendMessage(7);
            if (videoMediaPlayer != null)
                videoMediaPlayer.stopPlayer();
            receiver.addObserver(new Receiver.ReceiverObserver() {
                @Override
                public void stream(final RemoteStream remoteStream) {
                    if (remoteStream == null) return;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "SessionHandler.remote.ReceiverObserver.getFrom()" + receiver.getFrom());
                            remoteStream.play(localVideoRenderer);
                        }
                    });
                }

                @Override
                public void message(String s) {
                    Log.e(TAG, "SessionHandler.remote.ReceiverObserver.message");
                }

                @Override
                public void close() {
                    Log.e(TAG, "SessionHandler.remote.ReceiverObserver.close");
                    VideoStuHouseActivity.this.finish();
                }

                @Override
                public void error(Errors errors) {
                    Log.e(TAG, "SessionHandler.remote.ReceiverObserver.errors");
                    VideoStuHouseActivity.this.finish();
                }

                @Override
                public void log(JSONObject jsonObject) {
                    Log.e(TAG, "SessionHandler.remote.ReceiverObserver.log");
                }

                @Override
                public void file(File file) {
                    Log.e(TAG, "SessionHandler.remote.ReceiverObserver.file");
                }
            });
            receiver.response();
        }

        @Override
        public void local(final Sender sender) {
            if (sender == null) return;
            Log.e(TAG, "SessionHandler.local");
            sender.addObserver(new Sender.SenderObserver() {
                @Override
                public void close() {
                    Log.e(TAG, "SessionHandler.local.SenderObserver.close");
                    if (localStream != null && sender != null && session != null && session.getState() == Configs.ConnectState.CONNECTED) {
                        session.sendTo(localStream, false, null, sender.getTo());
                    }
                   // VideoStuHouseActivity.this.finish();
                }

                @Override
                public void error(Errors errors) {
                    Log.e(TAG, "SessionHandler.local.SenderObserver.error");
                    ToastUtil.showToast("视频通信状况出现了点问题呀~");
                    VideoStuHouseActivity.this.finish();
                }

                @Override
                public void log(JSONObject jsonObject) {
                    Log.e(TAG, "SessionHandler.local.SenderObserver.log");
                }

                @Override
                public void fileSending(int i) {
                    Log.e(TAG, "SessionHandler.local.SenderObserver.fileSending");
                }

                @Override
                public void fileFinished() {
                    Log.e(TAG, "SessionHandler.local.SenderObserver.fileFinished");
                }
            });
        }

        @Override
        public void message(String s, String s1) {
            Log.e(TAG, "SessionHandler.message");
        }

        @Override
        public void error(String s) {
            Log.e(TAG, "SessionHandler.error=" + s);
            ToastUtil.showToast("视频通信状况出现了点问题呀~");
            VideoStuHouseActivity.this.finish();
        }

        @Override
        public void close() {
            Log.e(TAG, "SessionHandler.close");
            VideoStuHouseActivity.this.finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        thread_flag = false;

        if (thread != null) {
            thread.interrupt();
        }

        if (videoMediaPlayer != null) {
            videoMediaPlayer.stopPlayer();
        }

        if (session != null) {
            session.disconnect();
        }

        if (localStream != null) {
            localStream.dispose();
            localStream = null;
        }

        if (localVideoRenderer != null) {
            localVideoRenderer.release();
            localVideoRenderer = null;
        }

        if (localVideoRenderer != null) {
            localVideoRendererme.release();
            localVideoRendererme = null;
        }

        if (cat != null) {
            cat.release();
            cat = null;
        }
        startGetStuTokenService();
        sendMessage(8);
    }

    @Override
    public void onBackPressed() {
        if (!flagTakePhone) {
            showShutdownWarning();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG, "audioManager.getMode()=" + audioManager.getMode());
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audioManager.adjustStreamVolume(audioManager.getMode(), AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audioManager.adjustStreamVolume(audioManager.getMode(), AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

/*    private void closeVideo() {
        OkHttpUtils.post()
                .url(Config.URL_CLOSE_VIDEO)
                .addParams("studentId", MyApplication.user.getId())
                .build()
                .execute(null);
    }*/

    @OnClick(R.id.bt_shutdown)
    public void takePhoneOnClick(View view) {
        if (flagTakePhone) {
            flagTakePhone = false;
            chatting();
            btShutdown.setImageResource(R.mipmap.video_off);
        } else {
            showShutdownWarning();
        }
    }

    private void showShutdownWarning() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("您是否要结束本次视频面试？")
                .setNegativeButton("取消", null)
                .setPositiveButton("结束", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //closeVideo();
                        VideoStuHouseActivity.this.finish();
                        overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    }
                })
                .show();
    }

    private void startGetStuTokenService() {
        Intent intent = new Intent(this, GetStuTokenService.class);
        startService(intent);
    }

    private void iniVideoWaining() {
        OkHttpUtils.post()
                .url(Config.URL_STUDENT_GETCOMPANY)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showNetError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Log.e(TAG, "response=" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            companyName = jsonObject.getString("companyName");
                            videoProgressView.setVisible(View.VISIBLE);
                            videoProgressView.setText(companyName + "企业邀请您进行视频面试...");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
