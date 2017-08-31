package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzu.jpg.commonwork.Presenter.MyInfoEditPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.utils.BitmapUtil;
import com.hzu.jpg.commonwork.utils.DialogUtil;
import com.hzu.jpg.commonwork.utils.StringUtils;
import com.hzu.jpg.commonwork.utils.TimeUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.widgit.CircleImageView;
import com.hzu.jpg.commonwork.widgit.MyEditLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MyInfoEditActivity extends AppCompatActivity {

    public static final int ZOOM_AFTER_PICTURE_CAPTURE = 1;//从照片选择后截图
    public static final int PICTURE_CAPTURE = 2;//从照片选择后截图
    public static final int LOGIN = 3;

    MyEditLayout etUsername;
    MyEditLayout etRealName;
    MyEditLayout etIdCard;
    MyEditLayout etSchool;
    MyEditLayout etMajor;
    MyEditLayout etProvince;
    MyEditLayout etCity;
    MyEditLayout etRegion;
    MyEditLayout etRequires;
    MyEditLayout etEntryTime;
    MyEditLayout etBankCard;
    MyEditLayout etLinkPhone;

    CheckBox cbMale;
    CheckBox cbFemale;

    Button btSubmit;

    CircleImageView circleImageView;

    Uri imageUri = null;
    Uri tempUri = null;
    File file = null;

    List<File> files = new ArrayList<>();

    MyInfoEditPresenter presenter;

    AlertDialog dialog;

    Spinner spYear;
    Spinner spMonth;
    Spinner spDay;

    int year = 0;
    int month = 0;
    int day = 0;

    List<Integer> days = new ArrayList<>();
    ArrayAdapter<Integer> adapterDay;

    @Bind(R.id.ib_statistics_add_back)
    ImageButton ibStatisticsAddBack;
    @Bind(R.id.tv_statistics_add)
    TextView tvStatisticsAdd;
    @Bind(R.id.iv_my_info_edit_head)
    CircleImageView ivMyInfoEditHead;
    @Bind(R.id.et_my_info_edit_username)
    MyEditLayout etMyInfoEditUsername;
    @Bind(R.id.cb_my_info_edit_male)
    CheckBox cbMyInfoEditMale;
    @Bind(R.id.cb_my_info_edit_female)
    CheckBox cbMyInfoEditFemale;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.sp_my_info_edit_year)
    Spinner spMyInfoEditYear;
    @Bind(R.id.sp_my_info_edit_month)
    Spinner spMyInfoEditMonth;
    @Bind(R.id.sp_my_info_edit_day)
    Spinner spMyInfoEditDay;
    @Bind(R.id.et_my_info_edit_link_phone)
    MyEditLayout etMyInfoEditLinkPhone;
    @Bind(R.id.et_my_info_edit_id_card)
    MyEditLayout etMyInfoEditIdCard;
    @Bind(R.id.et_my_info_edit_school)
    MyEditLayout etMyInfoEditSchool;
    @Bind(R.id.et_my_info_edit_major)
    MyEditLayout etMyInfoEditMajor;
    @Bind(R.id.et_my_info_edit_real_name)
    MyEditLayout etMyInfoEditRealName;
    @Bind(R.id.et_my_info_edit_province)
    MyEditLayout etMyInfoEditProvince;
    @Bind(R.id.et_my_info_edit_city)
    MyEditLayout etMyInfoEditCity;
    @Bind(R.id.et_my_info_edit_region)
    MyEditLayout etMyInfoEditRegion;
    @Bind(R.id.et_my_info_edit_required)
    MyEditLayout etMyInfoEditRequired;
    @Bind(R.id.et_my_info_edit_entry_time)
    MyEditLayout etMyInfoEditEntryTime;
    @Bind(R.id.et_my_info_edit_bank_card)
    MyEditLayout etMyInfoEditBankCard;
    @Bind(R.id.bt_my_info_edit_submit)
    Button btMyInfoEditSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_edit);
        ButterKnife.bind(this);
        findView();
        setView();
        setSpinner();
        if (presenter == null)
            presenter = new MyInfoEditPresenter(this);
        presenter.setData(MyApplication.user);
    }

    @OnClick(R.id.ib_statistics_add_back)
    public void onclick() {
        finish();
    }

    public void findView() {
        etBankCard = (MyEditLayout) findViewById(R.id.et_my_info_edit_bank_card);
        etCity = (MyEditLayout) findViewById(R.id.et_my_info_edit_city);
        etEntryTime = (MyEditLayout) findViewById(R.id.et_my_info_edit_entry_time);
        etIdCard = (MyEditLayout) findViewById(R.id.et_my_info_edit_id_card);
        etMajor = (MyEditLayout) findViewById(R.id.et_my_info_edit_major);
        etProvince = (MyEditLayout) findViewById(R.id.et_my_info_edit_province);
        etRealName = (MyEditLayout) findViewById(R.id.et_my_info_edit_real_name);
        etRegion = (MyEditLayout) findViewById(R.id.et_my_info_edit_region);
        etRequires = (MyEditLayout) findViewById(R.id.et_my_info_edit_required);
        etSchool = (MyEditLayout) findViewById(R.id.et_my_info_edit_school);
        etUsername = (MyEditLayout) findViewById(R.id.et_my_info_edit_username);
        cbFemale = (CheckBox) findViewById(R.id.cb_my_info_edit_female);
        cbMale = (CheckBox) findViewById(R.id.cb_my_info_edit_male);
        btSubmit = (Button) findViewById(R.id.bt_my_info_edit_submit);
        etLinkPhone = (MyEditLayout) findViewById(R.id.et_my_info_edit_link_phone);

        circleImageView = (CircleImageView) findViewById(R.id.iv_my_info_edit_head);

    }

    public void setView() {
        if (MyApplication.user != null) {
            Glide.with(this).load(Config.IP + MyApplication.user.getIcno())
                    .error(R.mipmap.ic_head_default)
                    .into(circleImageView);

            if (MyApplication.user.getSex().equals("女"))
                cbFemale.setChecked(true);
            else
                cbMale.setChecked(true);
        }

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, PICTURE_CAPTURE);
            }
        });

        cbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbMale.setChecked(false);
                } else {
                    cbMale.setChecked(true);
                }
            }
        });

        cbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbFemale.setChecked(false);
                } else {
                    cbFemale.setChecked(true);
                }
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.upload();
            }
        });


    }

    public void setSpinner() {
        String birthday = MyApplication.user.getBirthday();
        Integer nYear = null;
        Integer nMonth = null;
        Integer nDay = null;
        if (!StringUtils.isEmpty(birthday)) {
            String[] split = birthday.split("-");
            nYear = Integer.parseInt(split[0]);
            nMonth = Integer.parseInt(split[1]);
            nDay = Integer.parseInt(split[2]);
        }

        spYear = (Spinner) findViewById(R.id.sp_my_info_edit_year);
        final List<Integer> years = new ArrayList<>();
        for (int i = 1900; i < Integer.parseInt(TimeUtil.getDateYM().substring(0, 4)); i++) {
            years.add(i);
        }
        final ArrayAdapter<Integer> adapterYear = new ArrayAdapter<>(this, R.layout.my_spinner_item, years);
        spYear.setAdapter(adapterYear);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (nYear != null) {
            spYear.setSelection(years.indexOf(nYear));
            year = nYear;
        } else {
            spYear.setSelection(years.size() / 2);
            year = years.get(years.size() / 2);
        }
        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = years.get(position);
                updateDay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spMonth = (Spinner) findViewById(R.id.sp_my_info_edit_month);
        final Integer[] months = new Integer[12];
        for (int i = 1; i <= 12; i++) {
            months[i - 1] = i;
        }
        final ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<>(this, R.layout.my_spinner_item, months);
        spMonth.setAdapter(adapterMonth);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (nMonth != null) {
            spMonth.setSelection(nMonth - 1);
            month = nMonth;
        } else {
            spMonth.setSelection(0);
            month = 1;
        }
        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = months[position];
                updateDay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Log.d("day", String.valueOf(nDay));
        spDay = (Spinner) findViewById(R.id.sp_my_info_edit_day);
        int numberOfDay = TimeUtil.getDaysOfMonth(year, month);
        for (int i = 1; i <= numberOfDay; i++) {
            days.add(i);
        }
        adapterDay = new ArrayAdapter<>(this, R.layout.my_spinner_item, days);
        spDay.setAdapter(adapterDay);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (nDay != null) {
            spDay.setSelection(days.indexOf(nDay));
            day = nDay;
        } else {
            spDay.setSelection(0);
            day = 1;
        }
        spDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = days.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void updateDay() {
        days.clear();
        int numberOfDay = TimeUtil.getDaysOfMonth(year, month);
        for (int i = 1; i <= numberOfDay; i++) {
            days.add(i);
        }
        if (adapterDay != null)
            adapterDay.notifyDataSetChanged();
        spDay.setSelection(0);
    }

    public String getUsername() {
        return etUsername.getText();
    }

    public String getRealName() {
        return etRealName.getText();
    }

    public String getBirthday() {

        if (year == 0 || month == 0 || day == 0) {
            return "";
        }
        return year + "-" + month + "-" + day;
    }

    public String getLinkPhone() {
        return etLinkPhone.getText();
    }

    public String getIdCard() {
        return etIdCard.getText();
    }

    public String getSchool() {
        return etSchool.getText();
    }

    public String getMajor() {
        return etMajor.getText();
    }

    public String getProvince() {
        return etProvince.getText();
    }

    public String getCity() {
        return etCity.getText();
    }

    public String getRequires() {
        return etRequires.getText();
    }

    public String getRegion() {
        return etRegion.getText();
    }

    public String getBankCard() {
        return etBankCard.getText();
    }

    public String getSex() {
        return cbFemale.isChecked() ? "女" : "男";
    }

    public String getIcon() {
        if (file != null) {
            return file.getAbsolutePath();
        }
        return "";
    }

    public String getEntryTime() {
        return etEntryTime.getText();
    }


    public void setUsername(String data) {
        this.etUsername.setText(data);
    }

    public void setRealName(String data) {
        this.etRealName.setText(data);
    }

    public void setIdCard(String data) {
        this.etIdCard.setText(data);
    }

    public void setSchool(String data) {
        this.etSchool.setText(data);
    }

    public void setProvince(String data) {
        this.etProvince.setText(data);
    }

    public void setBankCard(String data) {
        this.etBankCard.setText(data);
    }

    public void setEntryTime(String data) {
        this.etEntryTime.setText(data);
    }

    public void setLinkPhone(String data) {
        etLinkPhone.setText(data);
    }

    public void setRequires(String data) {
        this.etRequires.setText(data);
    }

    public void setRegion(String data) {
        this.etRegion.setText(data);
    }

    public void setCity(String data) {
        this.etCity.setText(data);
    }

    public void setMajor(String data) {
        this.etMajor.setText(data);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICTURE_CAPTURE:
                    imageUri = data.getData();
                    if (imageUri != null) {
                        try {
                            file = new File(Environment.getExternalStorageDirectory() + "/jiongzhiw/", System.currentTimeMillis() + "temp.jpg");
                            file.getParentFile().mkdirs();
                            if (file.exists()) {
                                file.delete();
                            }
                            if (file.createNewFile()) {
                                tempUri = Uri.fromFile(file);
                                files.add(file);
                            } else
                                ToastUtil.showToast("分配文件失败");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        startPhotoZoom(imageUri, ZOOM_AFTER_PICTURE_CAPTURE, tempUri, circleImageView.getMeasuredWidth(), circleImageView.getMeasuredHeight());
                    } else {
                        ToastUtil.showToast("选择图片失败");
                    }
                    break;
                case ZOOM_AFTER_PICTURE_CAPTURE:
                    // 拿到从相册选择截取后的剪切数据
                    if (tempUri != null) {
                        BitmapUtil.bitmapCutByQuality(file.getPath(), 100 * 1024);
                        Glide.with(this).load(tempUri)
                                .error(R.mipmap.ic_head_default)
                                .into(circleImageView);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void startPhotoZoom(Uri uri, int i, Uri tempUri, int width, int height) {
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

    public void dismiss() {
        if (dialog != null)
            dialog.dismiss();
    }

    public void showDialog() {
        dialog = DialogUtil.showLoadingDialog(this);
    }

    public void clearList() {
        if (files.size() >= 2) {
            for (int i = 0; i < files.size() - 1; i++) {
                files.get(i).delete();
            }
        }
    }
}
