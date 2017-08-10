package com.hzu.jpg.commonwork.widgit;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.utils.NumberPickerHandler;

/**
 * Created by Administrator on 2017/3/13.
 */

public class MyPickerDialog extends Dialog {

    public MyPickerDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    public MyPickerDialog(Context context, int themeResId) {
        super(context, R.style.MyDialog);
    }

    public static class Builder{
        String title;
        View contentView;
        Context context;
        Button btSure;
        Button btCancel;
        NumberPicker npHours;
        NumberPicker npMinutes;
        OnDialogClickListener listener;
        TextView tvTitle;
        int hours=0;
        int minutes=0;
        int height;
        int width;

        public Builder(Context context){
            this.context=context;
            contentView= LayoutInflater.from(context).inflate(R.layout.dialog_number_picker,null);
            npHours= (NumberPicker) contentView.findViewById(R.id.np_add_ot_dialog_picker_hours);
            npMinutes= (NumberPicker) contentView.findViewById(R.id.np_add_ot_dialog_picker_minutes);
            btSure= (Button) contentView.findViewById(R.id.bt_add_record_dialog_picker_sure);
            btCancel= (Button) contentView.findViewById(R.id.bt_add_record_dialog_picker_cancel);
            tvTitle= (TextView) contentView.findViewById(R.id.tv_picker_dialog_title);
        }

        public void setTitle(String title){
            this.title=title;
        }
        public void create(){
            final MyPickerDialog dialog=new MyPickerDialog(context);
            Window window=dialog.getWindow();
            window.setContentView(contentView);
            tvTitle.setText(title);
            NumberPickerHandler.setNumberPickerDividerColor(npHours);
            NumberPickerHandler.setNumberPickerDividerColor(npMinutes);
            npHours.setMaxValue(23);
            npHours.setMinValue(0);
            npHours.setValue(hours);
            npMinutes.setMaxValue(59);
            npMinutes.setMinValue(0);
            npMinutes.setValue(minutes);
            btSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnPositiveClick(dialog,npHours.getValue(),npMinutes.getValue());
                }
            });
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnCancelClick(dialog);
                }
            });
            dialog.show();
            window.setLayout(width,height);

        }
        public void setTime(int hours,int minutes){
            this.hours=hours;
            this.minutes=minutes;
        }

        public void setOnDialogClickListener(OnDialogClickListener listener){
            this.listener=listener;
        }

        public interface OnDialogClickListener{
            void OnPositiveClick(MyPickerDialog dialog, int hours, int minutes);

            void OnCancelClick(MyPickerDialog dialog);
        }

        public void setDisplay(Display display){
            width=((int) (display.getWidth()*0.9));
            height=((int) (display.getHeight()*0.5));
        }
    }
}
