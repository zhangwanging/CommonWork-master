package com.hzu.jpg.commonwork.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.StrictMode;
import android.preference.PreferenceActivity;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieStore;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by cimcitech on 2017/3/7.
 */

public class HttpClientTool {


    public String TIME_OUT = "操作超时";
    public int time = 15000;
    private static CookieStore uCookie = null;
    private Context context;

    public HttpClientTool(Context context) {
        this.context = context;
    }

    public CookieStore getuCookie() {
        return uCookie;
    }

    public void setuCookie(CookieStore uCookie) {
        this.uCookie = uCookie;
    }

    /**
     * 请求数据--带param参数.
     *
     * @param params
     * @param url
     * @return
     */
    public String doPost(List<NameValuePair> params, String url) {
        network();
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        //httpPost.setHeaders(this.getHeader());
        System.out.println("---->url=" + url);
        DefaultHttpClient httpClient = null;

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  //允许所有主机的验证

            HttpParams httpParams = new BasicHttpParams();

            HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(httpParams,
                    HTTP.DEFAULT_CONTENT_CHARSET);
            HttpProtocolParams.setUseExpectContinue(httpParams, true);

            // 设置连接管理器的超时
            ConnManagerParams.setTimeout(httpParams, 10000);
            // 设置连接超时
            HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
            // 设置socket超时
            HttpConnectionParams.setSoTimeout(httpParams, 10000);

            // 设置http https支持
            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            schReg.register(new Scheme("https", sf, 443));

            ClientConnectionManager conManager = new ThreadSafeClientConnManager(
                    httpParams, schReg);

            httpClient = new DefaultHttpClient(conManager, httpParams);

            /** 保持会话Session **/
            /** 设置Cookie **/
            MyHttpCookies li = new MyHttpCookies(context);
            Constants.li = new MyHttpCookies(context);
            li.AddCookies(httpPost);
            Constants.li.AddCookies(httpPost);
            /** 保持会话Session end **/

            HttpResponse httpResp = httpClient.execute(httpPost);
            if (httpResp.getStatusLine().getStatusCode() == 200) {
                li.saveCookies(httpResp);
                Constants.li.saveCookies(httpResp);
                result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
                Log.i("HttpPost", "HttpPost方式请求成功，返回数据如下：");
                Log.i("result", result);
                /** 执行成功之后得到 **/
                /** 成功之后把返回成功的Cookis保存APP中 **/
                // 请求成功之后，每次都设置Cookis。保证每次请求都是最新的Cookis
                //li.setuCookie(httpClient.getCookieStore());
                //Constants.li.setuCookie(httpClient.getCookieStore());
                /** 设置Cookie end **/
            } else {
                Log.i("HttpPost", "HttpPost方式请求失败");
                System.out.println("0000===>" + EntityUtils.toString(httpResp.getEntity(), "UTF-8"));
                result = "{success:false,msg:'请求失败'}";
            }
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            result = "{success:false,msg:'" + TIME_OUT + "'}";
        } catch (UnsupportedEncodingException e) {
            result = "{success:false,msg:'请求失败'}";
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            result = "{success:false,msg:'请求失败'}";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }



    class SSLSocketFactoryEx extends SSLSocketFactory {

        SSLContext sslContext = SSLContext.getInstance("TLS");

        public SSLSocketFactoryEx(KeyStore truststore)
                throws NoSuchAlgorithmException, KeyManagementException,
                KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {

                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {

                }
            };

            sslContext.init(null, new TrustManager[]{tm}, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port,
                                   boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port,
                    autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }


    /**
     * 请求数据,未带参数.
     *
     * @param url
     * @return
     */
    public String doPost(String url) {
        network();
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        //httpPost.setHeaders(this.getHeader());
        System.out.println("---->url=" + url);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {

            /** 保持会话Session **/
            /** 设置Cookie **/
            MyHttpCookies li = new MyHttpCookies(context);
            CookieStore cs = li.getuCookie();
            /** 第一次请求App保存的Cookie为空，所以什么也不做，只有当APP的Cookie不为空的时。把请请求的Cooke放进去 **/
            li.AddCookies(httpPost);
            /** 保持会话Session end **/
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, time);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, time);
            HttpResponse httpResp = httpClient.execute(httpPost);
            if (httpResp.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
                Log.i("HttpPost", "HttpPost方式请求成功，返回数据如下：");
                Log.i("result", result);
                li.saveCookies(httpResp);
                /** 执行成功之后得到 **/
                /** 成功之后把返回成功的Cookis保存APP中 **/
                // 请求成功之后，每次都设置Cookis。保证每次请求都是最新的Cookis
                // li.setuCookie(httpClient.getCookieStore());
                /** 设置Cookie end **/
            } else {
                Log.i("HttpPost", "HttpPost方式请求失败");
                System.out.println("0000===>" + EntityUtils.toString(httpResp.getEntity(), "UTF-8"));
                result = "{success:false,msg:'请求失败'}";
            }
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            result = "{success:false,msg:'" + TIME_OUT + "'}";
        } catch (UnsupportedEncodingException e) {
            result = "{success:false,msg:'请求失败'}";
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            result = "{success:false,msg:'请求失败'}";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get 请求构造url
     *
     * @param url
     * @param params
     * @return
     */
    public String doGet(List<NameValuePair> params, String url) {
        /** 建立HTTPGet对象 **/
        String paramStr = "";
        if (params == null)
            params = new ArrayList<NameValuePair>();
        /** 迭代请求参数集合 **/
        for (NameValuePair obj : params) {
            paramStr += paramStr = "&" + obj.getName() + "=" + URLEncoder.encode(obj.getValue());
        }
        if (!paramStr.equals("")) {
            paramStr = paramStr.replaceFirst("&", "?");
            url += paramStr;
        }
        System.out.println("do_get_url ==== >" + url);
        return doGet(url);
    }

    /**
     * 提供GET形式的访问网络请求 doGet 参数示例：
     *
     * @param url 请求地址
     * @return 返回 String jsonResult;
     */
    public String doGet(String url) {
        System.out.println("url===>" + url);
        network();
        /** 创建HttpGet对象 **/
        HttpGet httpGet = new HttpGet(url);
        //httpGet.setHeaders(this.getHeader());

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String result;
        try {
            /** 保持会话Session **/
            /** 设置Cookie **/
            MyHttpCookies li = new MyHttpCookies(context);
            /** 第一次请求App保存的Cookie为空，所以什么也不做，只有当APP的Cookie不为空的时。把请请求的Cooke放进去 **/
            li.AddCookies(httpGet);
            /** 保持会话Session end **/
            /* 发送请求并等待响应 */
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, time);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, time);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            /* 若状态码为200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                li.saveCookies(httpResponse);
                /* 读返回数据 */
                result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                Log.i("HttpPost", "HttpGet方式请求成功，返回数据如下：");
                Log.i("result", result);
                //li.setuCookie(httpClient.getCookieStore());
            } else {
                Log.i("HttpPost", "HttpGet方式请求失败");
                result = "{success:false,msg:'请求失败'}";
                System.out.println("0000===>" + EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
            }
        } catch (ClientProtocolException e) {
            result = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            result = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            result = e.getMessage();
            httpGet.abort();
            // 销毁HttpClient
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }

    @SuppressLint("NewApi")
    private void network() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
                .detectNetwork().build());
    }
}


/**
 * http请求的缓存和一些公用的参数
 *
 * @author zhutao
 */
class MyHttpCookies {
    /**
     * 每页数据显示最大数
     */
    private int pageSize = 10;
    /**
     * 当前会话后的cokie信息
     */
    private CookieStore uCookie = null;
    /**
     * 公用的HTTP提示头信息
     */
    private PreferenceActivity.Header[] httpHeader;
    /**
     * HTTP连接的网络节点
     */
    private String httpProxyStr;
    /**
     * 上下文对象
     **/
    Context context;
    private SharedPreferences sp;// 存储cookies;
    HashMap<String, String> cookiesMap = new HashMap<String, String>();

    public MyHttpCookies(Context context) {
        this.context = context;
        /**
         * 预加载本地保存之cookie信息
         */
        sp = context.getSharedPreferences(Constants.KEY_LOGIN_AUTO, context.MODE_PRIVATE);
        String cookies = sp.getString(Constants.COOKIES, "");
        String[] cookieKayValue = cookies.split(";");
        for (int i = 0; i < cookieKayValue.length; i++) {
            if (!"".equals(cookieKayValue[i])) {
                String headerCookie[] = cookieKayValue[i].split("=");
                if (headerCookie.length == 1)
                    continue;
                cookiesMap.put(headerCookie[0], headerCookie[1]);
            }
        }
    }

    public void saveCookies(HttpResponse httpResponse) {

        StringBuffer stringBuffer = new StringBuffer();
        Header[] headers = httpResponse.getHeaders("Set-cookie");
        if (headers == null)
            return;
        for (int i = 0; i < headers.length; i++) {
            String cookie = headers[i].getValue();
            String[] cookievalues = cookie.split(";");
            for (int n = 0; n < cookievalues.length; n++) {
                String[] keyPair = cookievalues[n].split("=");
                String key = keyPair[0].trim();
                String value = keyPair.length > 1 ? keyPair[1].trim() : "";
                cookiesMap.put(key, value);
            }
        }
        Iterator<Map.Entry<String, String>> iter = cookiesMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            stringBuffer.append(key);
            stringBuffer.append("=");
            stringBuffer.append(val);
            stringBuffer.append(";");
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constants.COOKIES, stringBuffer.toString());
        editor.commit();
    }

    /**
     * 添加cookies至请求头get
     *
     * @param request
     */
    public void AddCookies(HttpGet request) {
        String cookies = sp.getString(Constants.COOKIES, "");
        request.addHeader("cookie", cookies);
    }

    /**
     * 添加cookies至请求头
     *
     * @param request
     */
    public void AddCookies(HttpPost request) {
        String cookies = sp.getString(Constants.COOKIES, "");
        request.addHeader("cookie", cookies);
    }

    /**
     * 增加自动选择网络，自适应cmwap、CMNET、wifi或3G
     */
    @SuppressWarnings("static-access")
    public void initHTTPProxy() {
        WifiManager wifiManager = (WifiManager) (context.getSystemService(context.WIFI_SERVICE));
        if (!wifiManager.isWifiEnabled()) {
            Uri uri = Uri.parse("content://telephony/carriers/preferapn"); // 获取当前正在使用的APN接入点
            Cursor mCursor = context.getContentResolver().query(uri, null, null, null, null);
            if (mCursor != null) {
                mCursor.moveToNext(); // 游标移至第一条记录，当然也只有一条
                httpProxyStr = mCursor.getString(mCursor.getColumnIndex("proxy"));
            }
        } else {
            httpProxyStr = null;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public CookieStore getuCookie() {
        return uCookie;
    }

    public void setuCookie(CookieStore uCookie) {
        this.uCookie = uCookie;
    }

    public String getHttpProxyStr() {
        return httpProxyStr;
    }

    public PreferenceActivity.Header[] getHttpHeader() {
        return httpHeader;
    }

}
