package com.hzu.jpg.commonwork.action;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.AddressInfo;
import com.hzu.jpg.commonwork.enity.AddressStatus;
import com.hzu.jpg.commonwork.enity.goods.GoodsVo;
import com.hzu.jpg.commonwork.enity.home.JobVo;
import com.hzu.jpg.commonwork.enity.service.NewsVo;
import com.hzu.jpg.commonwork.enity.service.PostsVo;
import com.hzu.jpg.commonwork.enity.service.ReplyVo;
import com.hzu.jpg.commonwork.utils.Constants;
import com.hzu.jpg.commonwork.utils.GjsonUtil;
import com.hzu.jpg.commonwork.utils.HttpClientTool;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cimcitech on 2017/5/26.
 */

public class RequestAction {

    private HttpClientTool hct;

    private String getShopData = "https://www.jiongzhiw.com/HRM/life/getShopData.html?method=android&startPage=1&pageSize=10";
    private String checkPoint = "https://www.jiongzhiw.com/HRM/life/checkPoint.html";
    private String newsListData = "https://www.jiongzhiw.com/HRM/news/listUI.html";
    private String getComment = "https://www.jiongzhiw.com/HRM/life/getComment.html";
    private String getReplay = "https://www.jiongzhiw.com/HRM/life/getReplay.html";
    private String addComment = "https://www.jiongzhiw.com/HRM/life/addComment.html";
    private String showSimpleInfoA = "https://www.jiongzhiw.com/HRM/job/showSimpleInfoA.html";
    private String andrlgByQq = "https://www.jiongzhiw.com/HRM/thirdPart/andrlgByQq.html";
    private String getAddressList = "https://www.jiongzhiw.com/HRM/gAddr/getGAddrList.html";
    private String addOrModifyAddress = "https://www.jiongzhiw.com/HRM/gAddr/upGAddr.html";

    public RequestAction(Context context) {
        hct = new HttpClientTool(context);
    }

    /**
     * 获取商品数据
     *
     * @param params
     * @return
     */
    public ArrayList<GoodsVo> getShopDataAction(List<NameValuePair> params) {
        ArrayList<GoodsVo> goodsVos = new ArrayList<>();
        String result = hct.doPost(params, getShopData);
        try {
            goodsVos = GjsonUtil.jsonToArrayList(result, GoodsVo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodsVos;
    }

    /**
     * 积分检测
     *
     * @param params
     * @return
     */
    public String checkPointAction(List<NameValuePair> params) {
        String result = hct.doPost(params, checkPoint);
        return result;
    }

    /**
     * 新闻列表
     *
     * @param params
     * @return
     */
    public NewsVo getNewsListDataAction(List<NameValuePair> params) {
        NewsVo newsVo = new NewsVo();
        String result = hct.doPost(params, newsListData);
        try {
            newsVo = GjsonUtil.parseJsonWithGson(result, NewsVo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsVo;
    }

    /**
     * 获取帖子列表
     *
     * @param params
     * @return
     */
    public PostsVo getCommentAction(List<NameValuePair> params) {
        PostsVo postsVo = new PostsVo();
        String result = hct.doPost(params, getComment);
        try {
            postsVo = GjsonUtil.parseJsonWithGson(result, PostsVo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postsVo;
    }

    /**
     * 获取帖子评论列表
     *
     * @param params
     * @return
     */
    public ReplyVo getReplayAction(List<NameValuePair> params) {
        ReplyVo replyVo = new ReplyVo();
        String result = hct.doPost(params, getReplay);
        try {
            replyVo = GjsonUtil.parseJsonWithGson(result, ReplyVo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return replyVo;
    }

    /**
     * 评论帖子
     *
     * @param params
     * @return
     */
    public String addCommentAction(List<NameValuePair> params) {
        String result = hct.doPost(params, addComment);
        return result;
    }

    /**
     * 获取首页招聘信息
     *
     * @param params
     * @return
     */
    public JobVo showSimpleInfoAction(List<NameValuePair> params) {
        JobVo jobVo = new JobVo();
        String result = hct.doPost(params, showSimpleInfoA);
        if (!TextUtils.isEmpty(result)) {
            try {
                jobVo = GjsonUtil.parseJsonWithGson(result, JobVo.class);
            } catch (Exception e) {

            }
        }
        return jobVo;
    }

    /**
     * QQ登录
     *
     * @param params
     * @return
     */
    public String andrlgByQqAction(List<NameValuePair> params) {
        String result = hct.doPost(params, andrlgByQq);
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("qqLgStatus")) {
                Constants.isOneQQlogin = true;
            } else {
                Constants.isOneQQlogin = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 收货地址列表
     *
     * @param params
     * @return
     */
    public AddressInfo getAddressInfo() {
        AddressInfo addrVo = new AddressInfo();
        String result = hct.doGet(getAddressList);
        try {
            addrVo = GjsonUtil.parseJsonWithGson(result, AddressInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addrVo;
    }

    /**
     * 添加修改收货地址
     *
     * @param params
     * @return
     */
    public AddressStatus addOrModifyAddress(List<NameValuePair> params) {
        AddressStatus addrVo = new AddressStatus();
        String result = hct.doPost(params, addOrModifyAddress);
        try {
            addrVo = GjsonUtil.parseJsonWithGson(result, AddressStatus.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addrVo;
    }

    public String userLogin(List<NameValuePair> params) {
        String result = hct.doPost(params, Config.URL_STUDENT_LOGIN);
        return result;
    }
}
