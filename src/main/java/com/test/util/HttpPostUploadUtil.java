package com.test.util;

import com.test.enums.Level;
import com.test.web.util.GlobalConstants;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * 多媒体上传
 * 模拟表单的方式上传资源到腾讯服务器
 * Created by zggdczfr on 2016/11/5.
 */
public class HttpPostUploadUtil {

    /**
     * 重难点：对于request的参数设置，表单上传的设置
     */

    private static final Logger LOGGER = Logger.getLogger(HttpPostUploadUtil.class);

    public String urlStr;

    /**
     * 构造器
     */
    public HttpPostUploadUtil() {
        urlStr = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="
                + GlobalConstants.getInterfaceUrl("access_token")+"&type=image";
    }

    /**
     * 上传图片
     * @param textMap
     * @param fileMap
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String formUpload(Map<String, String> textMap,  Map<String, String> fileMap){
        String res = "";
        HttpURLConnection connection = null;
        //boundary就是request头和上传文件内容的分隔符
        String BOUNDARY = "---------------------------123821742118716";

        try {
            URL url = new URL(urlStr);
            //对于request的参数设置
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(30000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //设置不使用缓存
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream outPut = new DataOutputStream(connection.getOutputStream());

            /**
             * test
             */
            if (textMap != null){
                StringBuffer strBuffer = new StringBuffer();
                Iterator<?> iterator = textMap.entrySet().iterator();
                while (iterator.hasNext()){
                    //同时从Map中取得关键字和相应的值
                    Map.Entry entry = (Map.Entry)iterator.next();
                    String inPutName = (String) entry.getKey();
                    String inPutValue = (String) entry.getValue();
                    if (inPutValue == null){
                        continue;
                    }
                    strBuffer.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuffer.append("Content-Disposition: form-data; name=\""
                            + inPutName + "\"\r\n\r\n");
                    strBuffer.append(inPutValue);
                }
                outPut.write(strBuffer.toString().getBytes());
            }

            /**
             * file
             */
            if (fileMap != null){
                Iterator iterator = fileMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String inPutName = (String) entry.getKey();
                    String inPutValue = (String) entry.getValue();
                    if (inPutValue == null){
                        continue;
                    }
                    File file = new File(inPutValue);
                    String filename = file.getName();
                    //MimetypesFileTypeMap() 扩展 FileTypeMap 并通过其文件扩展名提供文件的数据分类
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    if (filename.endsWith(".jpg")) {
                        contentType = "image/jpg";
                    }
                    if (contentType == null || contentType.equals("")){
                        contentType = "application/octet-stream";
                    }

                    StringBuffer strBuffer = new StringBuffer();
                    strBuffer.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuffer.append("Content-Disposition: form-data; name=\""
                            + inPutName + "\"; filename=\"" + filename + "\"\r\n");
                    strBuffer.append("Content-Type:" + contentType + "\r\n\r\n");

                    outPut.write(strBuffer.toString().getBytes());

                    DataInputStream inPut = new DataInputStream(new FileInputStream(file));
                     int line = 0;
                    byte[] bufferOut = new byte[1024];
                    while((line = inPut.read(bufferOut)) != -1){
                        outPut.write(bufferOut, 0, line);
                    }
                    inPut.close();
                }
            }

            byte[] endDate = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            outPut.write(endDate);
            outPut.flush();
            outPut.close();

            //读取返回数据
            StringBuffer strBuffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null){
                strBuffer.append(line).append("\n");
            }
            res = strBuffer.toString();
            reader.close();
            reader = null;
        } catch (MalformedURLException e) {
            LOGGER.log(Level.ERROR, "URL {0} 发生Post请求发生错误。错误提示：{1}", urlStr, e);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "URL {0} 发生Post请求发生错误。错误提示：{1}", urlStr, e);
        } finally {
            if (connection != null){
                connection.disconnect();
                connection = null;
            }
        }
        return res;
    }
}
