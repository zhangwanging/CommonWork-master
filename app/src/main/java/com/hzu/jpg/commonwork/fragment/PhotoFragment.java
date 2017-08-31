package com.hzu.jpg.commonwork.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

public class PhotoFragment extends Fragment {

    private View view;
    private PhotoView mphotoview;
    private PhotoViewAttacher mattacher;
    private String photourl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.photo_view_fg, null);
        photourl = getArguments().getString("photoUrl");
        initView();
        //setlistener();
        return view;
    }

    private void initView() {
        mphotoview = (PhotoView) view.findViewById(R.id.photo_view_img);
        mattacher = new PhotoViewAttacher(mphotoview);

        Glide.with(this)
//                .load(Config.IP + photourl)
                .load(photourl)
                .into(mphotoview);
    }
}
