package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.Bean.MyInfoBean;
import com.hzu.jpg.commonwork.utils.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/4/5.
 */

public class MyInfoEditModel {

    Context context;
    private Handler handler;
    OnUploadListener listener;

    private final static int UPLOAD_FAIL = 1;
    private final static int UPLOAD_SUCCESS = 2;


    public MyInfoEditModel(Context context) {
        this.context = context;
        Looper looper = context.getMainLooper();
        handler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UPLOAD_FAIL:
                        Call call = (Call) msg.obj;
                        listener.onUploadFail(call);
                        break;
                    case UPLOAD_SUCCESS:
                        listener.onUploadSuccess(msg.obj);
                        break;
                }
            }
        };
    }


    public void uploadOkHttp3(final MyInfoBean bean, OnUploadListener listener) {
        this.listener = listener;
        String url = Config.IP + "/HRM/register/stuSupplement.html";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (bean.getIcon() != null && !bean.getIcon().isEmpty()) {
            File file = new File(bean.getIcon());
            if (file.exists()) {
                builder.addFormDataPart("usericno", file.getName(), RequestBody.create(MediaType.parse("image/jpg"), file));
            }
        }
        Class<MyInfoBean> clazz = MyInfoBean.class;
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String fieldName = field.getName();
                if (!fieldName.equals("icon")) {
                    String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                    Method method = clazz.getDeclaredMethod(methodName);
                    String sField = (String) method.invoke(bean);
                    if (sField != null) {
                        builder.addFormDataPart(fieldName, sField);
                        Log.e(fieldName, sField);
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = builder.build();
        final Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url(url)
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what = UPLOAD_FAIL;
                message.obj = call;
                handler.sendMessage(message);
                if (e != null)
                    e.printStackTrace();
                Log.e("onFailure", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String receive = response.body().string();
                if(!receive.isEmpty()) {
                    Gson gson = new Gson();
                    OnBitmapUpload onBitmapUpload = gson.fromJson(receive, OnBitmapUpload.class);
                    if (onBitmapUpload.getStatus().equals("1")) {
                        bean.setIcon(onBitmapUpload.getIcon());
                    }
                }
                Message message = handler.obtainMessage();
                message.what = UPLOAD_SUCCESS;
                message.obj = bean;
                handler.sendMessage(message);
            }
        });
    }

    public interface OnUploadListener {
        void onUploadSuccess(Object obj);

        void onUploadFail(Call call);
    }

    private class OnBitmapUpload{
        String icon;
        String status;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
