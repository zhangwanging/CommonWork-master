package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hzu.jpg.commonwork.Presenter.CompanyInfoEditPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.utils.BitmapUtil;
import com.hzu.jpg.commonwork.utils.DialogUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.widgit.MyEditLayout;

import java.io.File;
import java.io.IOException;


public class CompanyInfoEditActivity extends AppCompatActivity {

    MyEditLayout etName;
    MyEditLayout etLabel;
    EditText etDescribes;
    EditText etProvince;
    EditText etCity;
    EditText etRegion;
    MyEditLayout etDetails;
    MyEditLayout etLinkMan;
    MyEditLayout etLinkPhone;
    MyEditLayout etEmail;

    ImageView ib;
    ImageButton ibBack;
    ImageButton ivLicense;
    Button btSubmit;

    Uri imageUri = null;
    Uri tempUri = null;

    Uri licenseTempUri = null;

    //Uri taxTempUri = null;


    public static final int PICTURE_CAPTURE_HEAD = 2;//从相册选择
    public static final int PICTURE_CAPTURE_LICENSE = 3;//从相册选择
    public static final int PICTURE_CAPTURE_TAX = 4;//从相册选择

    public static final int ZOOM_AFTER_PICTURE_CAPTURE_HEAD = 5;
    public static final int ZOOM_AFTER_PICTURE_CAPTURE_LICENSE = 6;
    public static final int ZOOM_AFTER_PICTURE_CAPTURE_TAX = 7;

    CompanyInfoEditPresenter presenter;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info_edit);

        if (presenter==null)
            presenter=new CompanyInfoEditPresenter(this);

        etDescribes = (EditText) findViewById(R.id.et_company_info_edit_describes);
        etDetails = (MyEditLayout) findViewById(R.id.et_company_info_edit_details);
        etEmail = (MyEditLayout) findViewById(R.id.et_company_info_edit_email);
        etLabel = (MyEditLayout) findViewById(R.id.et_company_info_edit_label);
        etLinkMan = (MyEditLayout) findViewById(R.id.et_company_info_edit_linkman);
        etLinkPhone = (MyEditLayout) findViewById(R.id.et_company_info_edit_link_phone);
        etName = (MyEditLayout) findViewById(R.id.et_company_info_edit_name);
        etProvince = (EditText) findViewById(R.id.et_company_info_edit_province);
        etCity = (EditText) findViewById(R.id.et_company_info_edit_city);
        etRegion = (EditText) findViewById(R.id.et_company_info_edit_region);

        ivLicense= (ImageButton) findViewById(R.id.ib_company_info_edit_license);
        //ivTax= (ImageButton) findViewById(R.id.ib_company_info_edit_tax);

        ibBack= (ImageButton) findViewById(R.id.ib_company_info_edit_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ib = (ImageView) findViewById(R.id.ib_company_info_edit);
        btSubmit= (Button) findViewById(R.id.bt_cpmpany_info_edit_submit);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, PICTURE_CAPTURE_HEAD);
            }
        });
        ivLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, PICTURE_CAPTURE_LICENSE);
            }
        });
//        ivTax.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");//相片类型
//                startActivityForResult(intent, PICTURE_CAPTURE_TAX);
//            }
//        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.upload();
            }
        });
        presenter.setData();

        Glide.with(this).load(Config.IP+ MyApplication.user.getIcno())
                .error(R.mipmap.ic_head_default)
                .into(ib);
        Glide.with(this).load(Config.IP+MyApplication.user.getLicense())
                .error(R.mipmap.add_picture)
                .into(ivLicense);
//        Glide.with(this).load(Config.IP+MyApplication.user.getTax())
//                .error(R.mipmap.add_picture)
//                .into(ivTax);

    }

    public String getName() {
        return etName.getText();
    }

    public void setName(String data) {
        this.etName.setText(data);
    }

    public String getLabel() {
        return etLabel.getText();
    }

    public void setLabel(String data) {
        this.etLabel.setText(data);
    }

    public String getDescribes() {
        return etDescribes.getText().toString();
    }

    public void setDescribes(String data) {
        this.etDescribes.setText(data);
    }

    public String getProvince() {
        return etProvince.getText().toString();
    }

    public void setProvince(String data) {
        this.etProvince.setText(data);
    }

    public String getCity() {
        return etCity.getText().toString();
    }

    public void setCity(String data) {
        this.etCity.setText(data);
    }

    public String getRegion() {
        return etRegion.getText().toString();
    }

    public void setRegion(String data) {
        this.etRegion.setText(data);
    }

    public String getDetails() {
        return etDetails.getText();
    }

    public void setDetails(String data) {
        this.etDetails.setText(data);
    }

    public String getLinkMan() {
        return etLinkMan.getText();
    }

    public void setLinkMan(String data) {
        this.etLinkMan.setText(data);
    }

    public String getLinkPhone() {
        return etLinkPhone.getText();
    }

    public void setLinkPhone(String data) {
        this.etLinkPhone.setText(data);
    }

    public String getEmail() {
        return etEmail.getText();
    }

    public void setEmail(String data) {
        this.etEmail.setText(data);
    }

    public String getIcon() {
        if (tempUri != null)
            return tempUri.getPath();
        return "";
    }
    public String getLicense() {
        if (licenseTempUri != null)
            return licenseTempUri.getPath();
        return "";
    }
//    public String getTax() {
//        if (taxTempUri != null)
//            return taxTempUri.getPath();
//        return "";
//    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICTURE_CAPTURE_HEAD:
                    imageUri = data.getData();
                   tempUri=onBitmapPick(ib,ZOOM_AFTER_PICTURE_CAPTURE_HEAD);
                    break;
                case PICTURE_CAPTURE_LICENSE:
                    imageUri = data.getData();
                    licenseTempUri=imageUri;
                    //licenseTempUri=onBitmapPick(ivLicense,ZOOM_AFTER_PICTURE_CAPTURE_LICENSE);
                    break;
//                case PICTURE_CAPTURE_TAX:
//                    imageUri = data.getData();
//                    taxTempUri=onBitmapPick(ivTax,ZOOM_AFTER_PICTURE_CAPTURE_TAX);
//                    break;
                case ZOOM_AFTER_PICTURE_CAPTURE_HEAD:
                    // 拿到从相册选择截取后的剪切数据
                    onBitmapZoom(tempUri,ib);
                    break;
                case ZOOM_AFTER_PICTURE_CAPTURE_LICENSE:
                    // 拿到从相册选择截取后的剪切数据
                    onBitmapZoom(licenseTempUri,ivLicense);
                    break;
//                case ZOOM_AFTER_PICTURE_CAPTURE_TAX:
//                    // 拿到从相册选择截取后的剪切数据
//                    onBitmapZoom(taxTempUri,ivTax);
//                    break;
                default:
                    break;
            }
        }
    }

    public Uri onBitmapPick(ImageView iv,int ZOOM_CODE){
        Uri tempUri=null;
        if (imageUri != null) {
            try {
                File file = new File(Environment.getExternalStorageDirectory()+"/jiongzhiw/", System.currentTimeMillis()+"temp.jpg");
                file.getParentFile().mkdirs();
                if (file.createNewFile()){
                    tempUri = Uri.fromFile(file);
                }
                else
                    ToastUtil.showToast("分配文件失败");
            } catch (IOException e) {
                e.printStackTrace();
            }
            startPhotoZoom(imageUri, ZOOM_CODE, tempUri, iv.getMeasuredWidth(), iv.getMeasuredHeight());
        } else {
            ToastUtil.showToast("选择图片失败");
        }
        return tempUri;
    }
    public void onBitmapZoom(Uri uri,ImageView iv){
        if (uri != null) {
            BitmapUtil.bitmapCutByQuality(uri.getPath(),50*1024);
            Glide.with(this).load(uri)
                    .error(R.mipmap.ic_head_default)
                    .into(iv);
        }
    }

    private void startPhotoZoom(Uri uri, int i, Uri tempUri, int width, int height) {
        if (uri == null) {
            Toast.makeText(getApplicationContext(), "选择图片出错！", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        //如果为true,则通过 Bitmap bmap = data.getParcelableExtra("data")取出数据
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(intent, i);
    }

    public void showDialog(){
        dialog= DialogUtil.showLoadingDialog(this);
    }

    public void dismiss(){
        if (dialog!=null)
            dialog.dismiss();
    }
}
