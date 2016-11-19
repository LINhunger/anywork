package com.test.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 获得文件名工具类
 * Created by zggdczfr on 2016/11/18.
 */
public class TravelFileUtil {

    /**
     * 获得路径下文件夹内所有文件名的集合
     * @param path
     * @return
     */
    public static List<String> travelFile(String path){
        List<String> fileList = new ArrayList<String>();
        File file = new File(path);
        if (file.exists()){
            File[] files = file.listFiles();
            if (files != null){
                for (int i=0; i<files.length; i++){
                    String fileName = files[i].getName();
                    if (!files[i].isDirectory()){
                        fileList.add(fileName);
                    }
                }
            }
        }
        return fileList;
    }

}
