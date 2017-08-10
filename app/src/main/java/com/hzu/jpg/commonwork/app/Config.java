package com.hzu.jpg.commonwork.app;

import android.content.Context;

/**
 * Created by Administrator on 2017/1/21.
 */

public class Config {
    //    public static final String IP = "http://192.168.1.113:8080";
    public static final String IP = "https://www.jiongzhiw.com";
    //获取地址信息
    public static final String URL_SHOW_LOCATION = IP + "/HRM/area/showLocation.html";
    //显示工作的简略信息
    public static final String URL_JOB_MSG_SIMPLE = IP + "/HRM/job/showSimpleInfoA.html";
    //显示工作的全部信息
    public static final String URL_JOB_MSG_DETAIL = IP + "/HRM/job/showAllInfoA.html";
    //一键求职
    public static final String URL_ONE_KEY_APPLY_JOB = IP + "/HRM/UserApply/insertA.html";
    //具体求职
    public static final String URL_DETAIL_APPLY_JOB = IP + "/HRM/uac/insertA.html";
    //显示公司信息
    public static final String URL_COMPANY_MSG = IP + "/HRM/company/android/showInfo.html";
    //学生登录
    public static final String URL_STUDENT_LOGIN = IP + "/HRM/login/login.html";
    //显示当前区域求职的人数
    public static final String URL_SHOW_NUMBER = IP + "/HRM/area/number.html";
    //发布一键招聘
    public static final String URL_RECRUITMENT = IP + "/HRM/capply/issue.html";
    //获取验证码
    public static final String URL_GET_CODE = IP + "/HRM/msg/sendMsg.html";
    //学生注册
    public static final String URL_STUDENT_REGISTER = IP + "/HRM/register/stuRegister.html?method=stuRegister";
    //企业注册
    public static final String URL_COMPANY_REGISTER = IP + "/HRM/register/comRegister.html?method=comRegister";
    //重置密码
    public static final String URL_RESET_PWD = IP + "/HRM/register/findpass.html?method=findpass";


    //video:学生获取应聘企业
    public static final String URL_STUDENT_GETCOMPANY = IP + "/HRM/interview/getCompanyByStudent.html";
    //video:获取token
    public static final String URL_GETTOKEN = IP + "/HRM/interview/getVadio.html";
    //video:获取视频状态
    public static final String URL_GETSTATUS = IP + "/HRM/interview/getInterViewTooken.html";
    //video:关闭视频
    public static final String URL_CLOSEVIDEO = IP + "/HRM/interview/closeSession.html";
    //video:企业获取学生信息
    public static final String URL_STUDENT_MESSAGE = IP + "/HRM/interview/getStudentMsg.html";

    public static final String URL_UPDATE = IP + "/HRM/systemParams/appVersion.html";
    //video:新版本学生获取token
    public static final String URL_GET_STUDENT_TOKEN = IP + "/HRM/interview/getStudentVadio.html";
    //video:新版本获取视频状态
    public static final String URL_GET_VIDEO_STATUS = IP + "/HRM/interview/getVadioStatus.html";
    //video:新版本企业获取学生
    public static final String URL_GET_STUDENT_LIST = IP + "/HRM/interview/getStudent.html";
    //video:新版本企业获取token
    public static final String URL_GET_COMPANY_TOKEN = IP + "/HRM/interview/getCompanyVadio.html";
    //video:新版本关闭视频
    public static final String URL_CLOSE_VIDEO = IP + "/HRM/interview/closeVadio.html";

    public static final String APP_ID = "wxf23ad2f43650def0";
    public static final String APP_SECRET = "e8a5b8eebe5d16a1f249d5614aa88839";

    public static final String QQ_APP_ID = "101411644";
    public static final String QQ_APP_KEY = "989b53b631b556a908d752f7f2684563";

    public static final String KEY_DATA = "data";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_STATU = "statu";
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAIL = 0;
    public static final String KEY_CLASSIFY = "classify";
    public static final String KEY_CITY = "city";
    public static final String KEY_REGION = "region";
    public static final String KEY_LABEL = "label";
    public static final String KEY_LOW_PRICE = "lowPrice";
    public static final String KEY_HIGH_PRICE = "highPrice";
    public static final String KEY_PAGE = "page";
    public static final String KEY_FROM_JOB_MSG = "job";
    public static final int VALUE_FROM_JOB_MSG = 0;
    public static final String KEY_TELEPHONE = "telephone";
    public static final String KEY_JOB_ID = "jobId";
    public static final String ID = "id";
    public static final String KEY_IS_GET_C_R_MSG = "isGetAllLocationMsg";
    public static final String KEY_UER_REAL_NAME = "userRealName";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PROVINCE = "province";
    public static final String KEY_DETAIL = "detail";
    public static final String KEY_DESCRIBES = "describes";
    public static final String KEY_DETAILS = "details";
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USER_MSG = "userMsg";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_MEN = "men";
    public static final String KEY_WOMEN = "women";
    public static final String KEY_AGE_BEGIN = "ageBegin";
    public static final String KEY_AGE_END = "ageEnd";
    public static final String KEY_SALARY = "salary";
    public static final String KEY_UNIT = "unit";
    public static final String DATE = "date";
    public static final String KEY_ICON = "icno";
    public static final String KEY_STU_MSG = "stuMsg";
    public static final String KEY_ROLE = "role";
    public static final String KEY_ENTERPRISE_NAME = "enterpriseName";
    public static final String KEY_ACCOUNT_CLASS = "accountClass";
    public static final String KEY_FORGET_CODE = "forgetCode";
    public static final String URL_PICTURE = IP + "/HRM/adv/showPicture.html";
    public static final String URL_COMPLAINTS = IP + "/HRM/advice/insertA.html";
    public static final String KEY_VERSION = "version";
    public static final String KEY_UPDATE_URL = "updateUrl";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_LINK_PHONE = "linkPhone";
    public static final String KEY_COM_MSG = "comMsg";

    public static String LOCATION_CITY = "";
    public static String SELECTED_CITY = "";
    public static Context CONTEXT;


    public final static int LOGIN = 1;
    public final static int INFO_EDIT = 2;
    public final static int EXIT = 3;

    public static boolean hourWork = false;


}
