package com.hzu.jpg.commonwork.enity;

import java.util.List;

/**
 * Created by cimcitech on 2017/8/30.
 */

public class CompanyVideoVo {
    /**
     * data : [{"pCompanyId":29,"videoDescript":"","videoId":28,"videoImg":"/HRM/upload/image/video/1503539470734.jpg","videoTitle":"测试","videoUrl":"/HRM/upload/video/1503539470734.flv"}]
     * message : 获取成功
     * status : 1
     */

    private String message;
    private int status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pCompanyId : 29
         * videoDescript :
         * videoId : 28
         * videoImg : /HRM/upload/image/video/1503539470734.jpg
         * videoTitle : 测试
         * videoUrl : /HRM/upload/video/1503539470734.flv
         */

        private int pCompanyId;
        private String videoDescript;
        private int videoId;
        private String videoImg;
        private String videoTitle;
        private String videoUrl;

        public int getPCompanyId() {
            return pCompanyId;
        }

        public void setPCompanyId(int pCompanyId) {
            this.pCompanyId = pCompanyId;
        }

        public String getVideoDescript() {
            return videoDescript;
        }

        public void setVideoDescript(String videoDescript) {
            this.videoDescript = videoDescript;
        }

        public int getVideoId() {
            return videoId;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }

        public String getVideoImg() {
            return videoImg;
        }

        public void setVideoImg(String videoImg) {
            this.videoImg = videoImg;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }
    }
}
