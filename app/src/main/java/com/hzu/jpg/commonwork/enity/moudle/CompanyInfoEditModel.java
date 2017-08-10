package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.Bean.CompanyInfoBean;

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
 * Created by Administrator on 2017/4/10.
 */

public class CompanyInfoEditModel {


    private final static int UPLOAD_FAIL = 1;
    private final static int UPLOAD_SUCCESS = 2;

    private Handler handler;

    MyInfoEditModel.OnUploadListener listener;

    public CompanyInfoEditModel(Context context) {
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

    public User getData() {
        return MyApplication.user;
    }

    public void uploadOkHttp3(final CompanyInfoBean bean, MyInfoEditModel.OnUploadListener listener) {
        this.listener = listener;
        String url = Config.IP + "/HRM/register/comSupplement.html";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (!bean.getIcon().equals("")) {
            File file = new File(bean.getIcon());
            if (file.exists())
                builder.addFormDataPart("icon", file.getName(), RequestBody.create(MediaType.parse("image/jpg"), file));

        }else{
            Log.e("put null","null");
            builder.addFormDataPart("icon", "");
        }
        if (!bean.getLicense() .equals("")) {
            File file = new File(bean.getLicense());
            if (file.exists())
                builder.addFormDataPart("license", file.getName(), RequestBody.create(MediaType.parse("image/jpg"), file));
        }else{
            Log.e("put null","null");
            builder.addFormDataPart("license", "");
        }
//        if (!bean.getTax().equals("")) {
//            File file = new File(bean.getTax());
//            if (file.exists()){
//                builder.addFormDataPart("tax", file.getName(), RequestBody.create(MediaType.parse("image/jpg"), file));
//            }
//        }else{
//            Log.e("put null","null");
//            builder.addFormDataPart("tax", "");
//        }
        Class<CompanyInfoBean> clazz = CompanyInfoBean.class;
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String fieldName = field.getName();
                if (!fieldName.equals("icon") && !fieldName.equals("license") && !fieldName.equals("tax")) {
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
        Request request = new Request.Builder()
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
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String receive = response.body().string();
                Log.e("com receive","aaa"+receive);
                if (receive != null && !receive.equals("")) {
                    Gson gson = new Gson();
                    OnBitmapUpload onBitmapUpload = gson.fromJson(receive, OnBitmapUpload.class);
                    if (onBitmapUpload.getStatus().equals("1")) {
                        bean.setIcon(onBitmapUpload.getIcon());
                        bean.setLicense(onBitmapUpload.getLicense());
                        bean.setTax(onBitmapUpload.getTax());
                    }
                }
                Message message = handler.obtainMessage();
                message.what = UPLOAD_SUCCESS;
                message.obj = bean;
                handler.sendMessage(message);
            }
        });
    }

    private class OnBitmapUpload {
        String status;
        String icon;
        String license;
        String tax;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }
    }
}
