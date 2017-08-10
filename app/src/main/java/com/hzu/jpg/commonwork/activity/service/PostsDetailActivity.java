package com.hzu.jpg.commonwork.activity.service;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.action.RequestAction;
import com.hzu.jpg.commonwork.activity.LoginActivity;
import com.hzu.jpg.commonwork.adapter.service.PostsAdapter;
import com.hzu.jpg.commonwork.adapter.service.PostsImageAdapter;
import com.hzu.jpg.commonwork.adapter.service.ReplyAdapter;
import com.hzu.jpg.commonwork.enity.service.PostsVo;
import com.hzu.jpg.commonwork.enity.service.ReplyVo;
import com.hzu.jpg.commonwork.utils.Constants;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.utils.Utility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class PostsDetailActivity extends AppCompatActivity {

    private ImageView iv_image;
    private TextView nickname, content, classfy, addtime;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private PostsVo.Posts posts;
    private PostsImageAdapter adapter;
    private ListView listContent, listComment;
    private RequestAction action;
    private int startPage = 1;
    private Handler uiHandler = null;
    private final int INIT_DATA_VIEW = 1001;
    private ReplyVo replyVo;
    private ReplyAdapter replyAdapter;
    private EditText search_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_detail);
        initHandler();
        posts = (PostsVo.Posts) this.getIntent().getSerializableExtra("data");
        getXmlView();
    }

    private void getXmlView() {
        action = new RequestAction(this);
        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listContent = (ListView) this.findViewById(R.id.listContent);
        listComment = (ListView) this.findViewById(R.id.listComment);
        search_et = (EditText) this.findViewById(R.id.search_et);
        this.findViewById(R.id.search_close_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.isLogin) {
                    if (!search_et.getText().toString().trim().equals("")) {
                        new addCommentThread().startThread();
                    } else
                        ToastUtil.showToast("请输入评论内容");
                } else
                    startActivity(new Intent(PostsDetailActivity.this, LoginActivity.class));
            }
        });
        options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.image_bg_default)
                .showImageForEmptyUri(R.mipmap.image_bg_default).showImageOnFail(R.mipmap.image_bg_default)
                .cacheInMemory()
                .cacheOnDisc()
                .bitmapConfig(Bitmap.Config.ALPHA_8)
                .build();
        iv_image = (ImageView) this.findViewById(R.id.iv_image);
        nickname = (TextView) this.findViewById(R.id.nickname);
        content = (TextView) this.findViewById(R.id.content);
        classfy = (TextView) this.findViewById(R.id.classfy);
        addtime = (TextView) this.findViewById(R.id.addtime);
        if (posts != null && !posts.equals("")) {
            imageLoader.displayImage(Constants.imageUrl + posts.getUserinfo().getIcno(), iv_image, options);
            nickname.setText(posts.getNickname());
            content.setText(posts.getContent());
            if (posts.getClassfy() == 1)
                classfy.setText("美食");
            if (posts.getClassfy() == 2)
                classfy.setText("情感");
            if (posts.getClassfy() == 3)
                classfy.setText("工作");
            if (posts.getClassfy() == 4)
                classfy.setText("生活");
            if (posts.getClassfy() == 5)
                classfy.setText("拼车");
            addtime.setText(posts.getAddtime());
            if (!posts.getPictures().equals("")) {
                listContent.setVisibility(View.VISIBLE);
                String str[] = posts.getPictures().split(";");
                adapter = new PostsImageAdapter(PostsDetailActivity.this, str);
                listContent.setAdapter(adapter);
                new Utility().setListViewHeightBasedOnChildren(listContent);
            }
            new getReplayThread().startThread();
        }
    }

    class getReplayThread implements Runnable {
        private Thread rthread = null;// 监听线程.

        @Override
        public void run() {
            //method=android&classfy=1&pid=4&pageNo=1&pageSize=8
            NameValuePair method_app = new BasicNameValuePair("method", "android");
            NameValuePair classfy_app = new BasicNameValuePair("classfy", posts.getClassfy() + "");//posts.getClassfy() + ""
            NameValuePair pid_app = new BasicNameValuePair("pid", posts.getId() + "");//posts.getId() + ""
            NameValuePair pageNo_app = new BasicNameValuePair("pageNo", startPage + "");
            NameValuePair pageSize_app = new BasicNameValuePair("pageSize", "10");
            List<NameValuePair> params = new ArrayList<>();
            params.add(method_app);
            params.add(classfy_app);
            params.add(pageNo_app);
            params.add(pid_app);
            params.add(pageSize_app);
            replyVo = action.getReplayAction(params);
            uiHandler.sendEmptyMessage(INIT_DATA_VIEW);
        }

        public void startThread() {
            if (rthread == null) {
                rthread = new Thread(this);
                rthread.start();
            }
        }
    }

    private void initHandler() {
        uiHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case INIT_DATA_VIEW:
                        if (replyVo != null && !replyVo.equals(""))
                            if (replyVo.getReply().size() > 0) {
                                replyAdapter = new ReplyAdapter(PostsDetailActivity.this, replyVo.getReply());
                                listComment.setAdapter(replyAdapter);
                                new Utility().setListViewHeightBasedOnChildren(listComment);
                            }
                        break;
                }
            }
        };
    }

    class addCommentThread implements Runnable {
        private Thread rthread = null;// 监听线程.

        @Override
        public void run() {
            NameValuePair method_app = new BasicNameValuePair("method", "pc");
            NameValuePair classfy_app = new BasicNameValuePair("classfy", posts.getClassfy() + "");
            NameValuePair pid_app = new BasicNameValuePair("pid", posts.getId() + "");
            NameValuePair comments_app = new BasicNameValuePair("comments", search_et.getText().toString().trim());
            NameValuePair cookie_app = new BasicNameValuePair("cookie", "");
            List<NameValuePair> params = new ArrayList<>();
            params.add(method_app);
            params.add(classfy_app);
            params.add(pid_app);
            params.add(comments_app);
            params.add(cookie_app);
            String string = action.addCommentAction(params);
            uiHandler.sendEmptyMessage(INIT_DATA_VIEW);
        }

        public void startThread() {
            if (rthread == null) {
                rthread = new Thread(this);
                rthread.start();
            }
        }
    }
}
