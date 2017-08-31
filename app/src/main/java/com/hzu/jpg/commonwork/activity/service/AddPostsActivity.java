package com.hzu.jpg.commonwork.activity.service;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.utils.GlideImageLoader2;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class AddPostsActivity extends AppCompatActivity {

    @Bind(R.id.back)
    TextView back;
    @Bind(R.id.relsase_title_name_new)
    TextView relsaseTitleNameNew;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.image_view_1)
    ImageView imageView1;
    @Bind(R.id.image_view_2)
    ImageView imageView2;
    @Bind(R.id.image_view_3)
    ImageView imageView3;
    @Bind(R.id.image_view_4)
    ImageView imageView4;
    @Bind(R.id.image_view_5)
    ImageView imageView5;
    @Bind(R.id.tv_context)
    EditText tvContext;
    @Bind(R.id.get_image)
    ImageView getImage;
    @Bind(R.id.submit)
    Button submit;

    private int classfy = 1;
    private ArrayList<ImageItem> imageItems;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_posts);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.layout_view_1, R.id.layout_view_2, R.id.layout_view_3,
            R.id.layout_view_4, R.id.layout_view_5, R.id.back, R.id.get_image, R.id.submit})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.layout_view_1:
                showImageView(imageView1);
                classfy = 1;
                break;
            case R.id.layout_view_2:
                showImageView(imageView2);
                classfy = 2;
                break;
            case R.id.layout_view_3:
                showImageView(imageView3);
                classfy = 3;
                break;
            case R.id.layout_view_4:
                showImageView(imageView4);
                classfy = 4;
                break;
            case R.id.layout_view_5:
                showImageView(imageView5);
                classfy = 5;
                break;
            case R.id.get_image:
                selectImage();
                break;
            case R.id.submit:
                ArrayList<File> files = new ArrayList<>();
                if (imageItems != null && imageItems.size() > 0) {
                    for (int i = 0; i < imageItems.size(); i++) {
                        files.add(new File(imageItems.get(i).path));
                        file = new File(imageItems.get(i).path);
                    }
                }
                if (!tvContext.getText().toString().trim().equals(""))
                    submitContext();
                else
                    ToastUtil.showToast("请输入帖子内容");
                break;
        }
    }

    public void selectImage() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader2());
        imagePicker.setMultiMode(false);   //多选
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setSelectLimit(1);    //最多选择9张
        imagePicker.setCrop(false);       //不进行裁剪
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 100);
    }

    private void showImageView(ImageView imageView) {
        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView4.setVisibility(View.INVISIBLE);
        imageView5.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
    }

    public void submitContext() {
        OkHttpUtils
                .post()
                .url(Config.IP + "/HRM/life/addComment.html")
                .addFile("", "", file)
                .addParams("classfy", classfy + "")
                .addParams("method", "pc")
                .addParams("comments", tvContext.getText().toString())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showNetError();
            }

            @Override
            public void onResponse(String response, int id) {
                if (response != null || !response.isEmpty()) {
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("status") == 2) {
                            ToastUtil.showToast("帖子添加成功，审核中");
                            finish();
                        } else if (json.getInt("status") == 3)
                            ToastUtil.showToast("请先登录");
                        else if (json.getInt("status") == 0)
                            ToastUtil.showToast("帖子添加失败");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String url = "";
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (imageItems != null && imageItems.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < imageItems.size(); i++) {
                        if (i == imageItems.size() - 1) {
                            sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path);
                            url = imageItems.get(i).path.toString();
                        } else {
                            sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path).append("\n");
                            url = imageItems.get(i).path.toString();
                        }
                    }
                    Bitmap bmp = BitmapFactory.decodeFile(url);
                    getImage.setImageBitmap(bmp);
                    //Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "--", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
