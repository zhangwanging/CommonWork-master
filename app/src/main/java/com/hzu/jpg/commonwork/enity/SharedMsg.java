package com.hzu.jpg.commonwork.enity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/16.
 */

public class SharedMsg {

    private String UserName;
    private String UserImgHead;
    private String msg;
    private List<String> imgUrls;
    private int praiseNum;
    private int commentsNum;
    private boolean isPraise;
    private List<Comment> comments;

    public SharedMsg() {
    }

    public SharedMsg(String userName, String userImgHead, String msg
            ,List<String> imgUrls, int praiseNum
            , int commentsNum, boolean isPraise
            ,List<Comment> comments) {
        UserName = userName;
        UserImgHead = userImgHead;
        this.msg = msg;
        this.imgUrls = imgUrls;
        this.praiseNum = praiseNum;
        this.commentsNum = commentsNum;
        this.isPraise = isPraise;
        this.comments = comments;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserImgHead() {
        return UserImgHead;
    }

    public void setUserImgHead(String userImgHead) {
        UserImgHead = userImgHead;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    public boolean isPraise() {
        return isPraise;
    }

    public void setPraise(boolean praise) {
        isPraise = praise;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "SharedMsg{" +
                "UserName='" + UserName + '\'' +
                ", UserImgHead='" + UserImgHead + '\'' +
                ", msg='" + msg + '\'' +
                ", imgUrls=" + imgUrls +
                ", praiseNum=" + praiseNum +
                ", commentsNum=" + commentsNum +
                ", isPraise=" + isPraise +
                ", comments=" + comments +
                '}';
    }
}
