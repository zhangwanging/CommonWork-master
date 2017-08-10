package com.hzu.jpg.commonwork.enity.service;

import java.util.ArrayList;

/**
 * Created by cimcitech on 2017/5/31.
 */

public class ReplyVo {

    private ArrayList<Reply> reply;

    public ArrayList<Reply> getReply() {
        return reply;
    }

    public void setReply(ArrayList<Reply> reply) {
        this.reply = reply;
    }

    public class Reply {
        private String addtime;
        private int check_status;
        private int classfy;
        private String content;
        private String failureInfo;
        private int id;
        private int is_del;
        private String nickname;
        private String pictures;
        private int pid;
        private int user_id;
        private String userinfo;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getCheck_status() {
            return check_status;
        }

        public void setCheck_status(int check_status) {
            this.check_status = check_status;
        }

        public int getClassfy() {
            return classfy;
        }

        public void setClassfy(int classfy) {
            this.classfy = classfy;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFailureInfo() {
            return failureInfo;
        }

        public void setFailureInfo(String failureInfo) {
            this.failureInfo = failureInfo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(String userinfo) {
            this.userinfo = userinfo;
        }
    }
}
