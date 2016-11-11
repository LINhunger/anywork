package com.test.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.test.enums.Level;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * http请求工具类
 * 通过http的get请求向微信服务器获取时效性为7200秒的token
 * Created by zggdczfr on 2016/11/5.
 */
@Controller
@RequestMapping("/deprecation")
public class HttpUtils {

    private static final Logger LOGGER = Logger.getLogger(HttpUtils.class);

    /**
     * http get 请求共用方法
     * @param reqUrl
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static String sendGet(String reqUrl, Map<String, String> params) throws URISyntaxException, IOException {
        InputStream inputStream = null;
        HttpGet request = new HttpGet();
        try {
            String url = buildUrl(reqUrl, params);
            HttpClient client = new DefaultHttpClient();
            //请求头
            //gzip是一种数据格式，默认使用deflate算法压缩data部分，此法用于压缩传输
            request.setHeader("Accept-Encoding", "gzip");
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);

            inputStream = response.getEntity().getContent();
            String result = getJsonStringFromGZIP(inputStream);
            LOGGER.log(Level.DEBUG, "HTTP Get请求URL:{0} 请求结果:{1}", reqUrl, result);
            return result;
        } finally {
            if (inputStream != null){
                inputStream.close();
            }
            request.releaseConnection();
        }
    }

    /**
     * http post 请求共用方法
     * @param reqUrl
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("resource")
    public static String sendPost(String reqUrl, Map<String, String> params) throws Exception {
        try {
            Set<String> ketSet = params.keySet();
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (String key : ketSet){
                list.add(new BasicNameValuePair(key, params.get(key)));
                if (list.size() > 0){
                    String result = "";
                    InputStream inPut = null;
                    try {
                        HttpClient client = new DefaultHttpClient();
                        HttpPost request = new HttpPost(reqUrl);

                        request.setHeader("Accept-Encoding", "gzip");
                        request.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));

                        HttpResponse response = client.execute(request);

                        inPut = response.getEntity().getContent();

                        result = getJsonStringFromGZIP(inPut);
                        LOGGER.log(Level.DEBUG, "HTTP POST请求URL:{0} 请求结果:{1}", reqUrl, result);
                        return result;
                    } catch (Exception e) {
                        LOGGER.log(Level.ERROR, "网络连接错误提示信息 : {0}", e.getMessage());
                        throw new Exception("HTTP POST请求 网络连接失败，请连接网络后重试!");
                    } finally {
                        inPut.close();
                    }
                } else {
                    LOGGER.log(Level.ERROR, "HTTP POST请求 错误提示 : 参数不全");
                    throw new Exception("参数不全，请稍后重试!");
                }
            }
        } catch (Exception e){
            LOGGER.log(Level.ERROR, "HTTP POST请求 未知错误提示 : {0}", e.getMessage());
            throw new Exception("发送未知异常");
        }
        return null;
    }

    /**
     * http post 请求json数据
     * @param urls
     * @param params
     * @return
     * @throws IOException
     */
    public static String sendPostBuffers(String urls, String params) throws IOException {
        HttpPost request = new HttpPost(urls);

        StringEntity sEntity = new StringEntity(params, HTTP.UTF_8);
        request.setEntity(sEntity);
        //发送请求
        @SuppressWarnings("resource")
        HttpResponse httpResponse = new DefaultHttpClient().execute(request);
        //得到应答的JSON格式字符串
        String result = EntityUtils.toString(httpResponse.getEntity());
        request.releaseConnection(); //释放连接
        return result;
    }

    /**
     * http post 请求发送xml内容
     * @param urlStr
     * @param xmlInfo
     * @return
     */
    public static String sendXmlPost(String urlStr, String xmlInfo){
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            //设置该URL可用于输入输出(这个是Post请求)
            connection.setDoOutput(true);
            connection.setRequestProperty("Pragma:", "no-cache"); //高速缓存编译
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Content-Type", "text/xml");
            OutputStreamWriter outPut = new OutputStreamWriter(connection.getOutputStream());
            outPut.write(new String(xmlInfo.getBytes("utf-8")));
            outPut.flush();
            outPut.close();
            //获得输入流
            BufferedReader buffered = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String lines= "";
            String line = "";
            while ((line = buffered.readLine()) != null){
                lines += line;
            }
            return lines; //返回请求详情
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    /**
     * 从GZIP编码格式输入流中获取json数据
     * @param inPut
     * @return
     */
    public static String getJsonStringFromGZIP(InputStream inPut){
        String jsonString = null;
        try {
            BufferedInputStream bufferedInPut = new BufferedInputStream(inPut);
            bufferedInPut.mark(2);
            //取前两个字节
            byte[] header = new byte[2];
            int result = bufferedInPut.read(header);
            //reset输入流到开始位置
            bufferedInPut.reset();
            //判断是否为GIZP格式，注意：GIZP流的前两个字节是 0x1f8b
            int headerData = getShort(header);
            if (result != -1 && headerData == 0x1f8b){
                inPut =  new GZIPInputStream(bufferedInPut);
            } else {
                inPut = bufferedInPut;
            }
            InputStreamReader reader = new InputStreamReader(inPut, "utf-8");
            char[] data = new char[100];
            int readSize;
            StringBuffer strBuffer = new StringBuffer();
            while ((readSize = reader.read(data)) > 0){
                strBuffer.append(data, 0, readSize);
            }
            jsonString = strBuffer.toString();
            bufferedInPut.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    private static int getShort(byte[] data){
        return (data[0] << 8) | data[1] & 0xFF;
    }

    /**
     * 构建get方法的Url
     * @param reqUrl url路径
     * @param params 查询参数
     * @return get方法访问路径
     */
    public static String buildUrl(String reqUrl, Map<String, String> params){
        StringBuilder query = new StringBuilder();
        Set<String> set = params.keySet();
        for (String key : set){
            query.append(String.format("%s=%s&", key, params.get(key)));
        }
        return reqUrl + "?" + query.toString();
    }
}
