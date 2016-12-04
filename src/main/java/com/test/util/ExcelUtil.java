package com.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.model.Textpaper;
import com.test.model.Topic;
import org.apache.poi.hssf.record.StandardRecord;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 操作Excel表格的工具类
 * Created by zggdczfr on 2016/11/23.
 */
public class ExcelUtil {
    /**
     * 读取报表， 备用
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public <T> List<T> readReport(InputStream inp,  Class<T> clazz, Map<Integer,String> map) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException  {

        List<T> list = new ArrayList<T>();

        try {

            Workbook wb = WorkbookFactory.create(inp);

            Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets

            Field field = null ;

            //从第一行开始读取数据
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i); // 获取行(row)对象
                T t = clazz.newInstance() ;
                T t1 = clazz.newInstance() ;

                if (row == null) {
                    // row为空的话,不处理
                    continue;
                }

                for (int j = 0; j < row.getLastCellNum()-1; j++) {
                    field = clazz.getDeclaredField(map.get(j+1)) ;
                    field.setAccessible(true);
                    // 获得单元格(cell)对象
                    Cell cell = row.getCell(j);
                    // 将单元格的数据添加至一个对象对应的属性中
                    t = addingT(field, t1, cell);
                }
                // 将添加数据后的对象填充至list中
                list.add(t);

                //获取最后一个单元格
                //Cell cell2 = row.getCell(row.getLastCellNum()-1);
                //System.out.println(123);
                //System.out.println(cell2.getStringCellValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (inp != null) {
                try {
                    inp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;

    }

    /**
     * 调用入口
     * @param input
     * @return
     * @throws Exception
     */
    public static List<Topic> getTextpaper(InputStream input) throws Exception {
        ExcelUtil excelUtil = new ExcelUtil();
        Map<Integer, String> map0 = new HashMap<Integer, String>();
        Map<Integer, String> map1 = new HashMap<Integer, String>();
        map1.put(1, "content");
        map1.put(2, "A");
        map1.put(3, "B");
        map1.put(4, "C");
        map1.put(5, "D");
        map1.put(6, "score");
        map1.put(7, "key");
        Map<Integer, String> map2 = new HashMap<Integer, String>();
        map2.put(1, "content");
        map2.put(2, "score");
        map2.put(3, "isTrue");
        //填空题和问答题的配置模板
        Map<Integer, String> map3 = new HashMap<Integer, String>();
        map3.put(1, "content");
        map3.put(2, "score");
        map3.put(3, "key");
        Map<Integer, String> map4 = new HashMap<Integer, String>();

        return excelUtil.read(input, Textpaper.class, map0, map1, map2, map3, map3);
    }




    /**
     * 根据输入流解析Excel文件，并封装为对象
     * @param input  输入流
     * @param clazz 对象模板
     * @param map0 表头信息
     * @param map1 选择题
     * @param map2 判断题
     * @param map3 填空题
     * @param map4 问答题
     * @param <T> 范性
     * @return
     * @throws Exception 解析Excel失败
     */
    public  <T> List<Topic>  read(InputStream input, Class<T> clazz,
                                             Map<Integer, String> map0,
                                             Map<Integer, String> map1,
                                             Map<Integer, String> map2,
                                             Map<Integer, String> map3,
                                             Map<Integer, String> map4) throws Exception{

        Textpaper textpaper = new Textpaper();
        List<Topic> topics = new ArrayList<Topic>();

        try {

            Workbook wb = WorkbookFactory.create(input);
            Sheet sheet = wb.getSheetAt(0);//取得第一个sheets
            Field field = null; //存储中转站
            Class tempClazz = Topic.class;

            //读取表头信息
            /**
             *
             */

            //从第二行开始，一行行读取数据
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (null == row) {
                    //空行不处理
                    continue;
                }

                Map<Integer, String> map;
                //获取每一行的第一个单元格
                Cell cell = row.getCell(0);
                //储存类型
                String status = cell.getStringCellValue();
                if (status.startsWith("A")){
                    //解析选择题
                    map = map1;
                } else if (status.startsWith("B")){
                    //解析判断题
                    map = map2;
                } else if (status.startsWith("C")){
                    //解析填空题
                    map = map3;
                } else if (status.startsWith("D")){
                    //解析问答题
                    map = map4;
                } else {
                    //其他提示消息，不做处理
                    continue;
                }

                T t = (T) tempClazz.newInstance();
                T t1 = (T) tempClazz.newInstance();

                //从第二个单元格开始读取
               for (int j=1; j<row.getLastCellNum(); j++){
                   //获取存储的类型
                   field = tempClazz.getDeclaredField(map.get(j));
                   field.setAccessible(true);
                   //获得单元格对象
                   Cell realCell = row.getCell(j);
                   t = addingT(field, t1, realCell);
               }

               //将对象t转化为Topic对象并存入List
                Topic topic = (Topic) t;
                //如果是只有标识符的空行
                if (null == topic.getContent()){
                    continue;
                }

                if (status.startsWith("A")){
                    topic.setType(1);
                } else if (status.startsWith("B")){
                    topic.setType(2);
                } else if (status.startsWith("C")){
                    topic.setType(3);
                } else if (status.startsWith("D")){
                    topic.setType(4);
                }
                topics.add(topic);
            }
        }finally {
            if (input != null){
                try {
                    input.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return topics;
    }

    private <T> T addingT(Field field, T t, Cell cell) throws IllegalArgumentException, IllegalAccessException {

        switch (cell.getCellType()) {

            case Cell.CELL_TYPE_STRING:
                // 读取String
                field.set(t, cell.getStringCellValue());
                break;


            case Cell.CELL_TYPE_BOOLEAN:
                // 得到Boolean对象的方法
                field.set(t, cell.getBooleanCellValue());
                break;

            case Cell.CELL_TYPE_NUMERIC:

                // 先看是否是日期格式
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 读取日期格式

                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                    //field.set(t, df.format(cell.getDateCellValue()));
                    field.set(t, cell.getDateCellValue());
                } else {
                    // 读取数字

                    Integer longVal = (int) Math.round(cell.getNumericCellValue());
                    double doubleVal = Math.round(cell.getNumericCellValue());

                    if(Double.parseDouble(longVal + ".0") == doubleVal){
                        String type = field.getType().toString() ;
                        if (type.equals("class java.lang.String")){
                            field.set(t, longVal+"") ;
                        }else
                            field.set(t, longVal) ;  }
                    else
                        field.set(t, doubleVal);
                }
                break;

            case Cell.CELL_TYPE_FORMULA:
                // 读取公式
                field.set(t,cell.getCellFormula());
                break;

            case HSSFCell.CELL_TYPE_BLANK: // 空值
                break;

            case HSSFCell.CELL_TYPE_ERROR: // 故障
                break;
        }

        return t;
    }
}
