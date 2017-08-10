package com.hzu.jpg.commonwork.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/13.
 */

public class BitmapUtil {

    public static void bitmapCutByQuality(String path,int maxSize){
        Bitmap image= BitmapFactory.decodeFile(path);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while ( os.toByteArray().length  > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            Log.d("os size",String.valueOf(os.toByteArray().length));
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }

        // Generate compressed image file
        File file=new File(path);
        try {
            if(file.delete()){
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(os.toByteArray());
                fos.flush();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
