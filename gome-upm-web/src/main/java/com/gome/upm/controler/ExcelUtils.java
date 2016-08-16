package com.gome.upm.controler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.gome.upm.common.exception.ExcelException;
import com.gome.upm.common.util.DateUtils;

/**
 * Excel导入工具类 使用方法：在需要导入或者导出的属性上添加@XmlElement(name = "xxx")xxx代表列名
 *
 * @author chenyuan
 *
 */
@SuppressWarnings("restriction")
public class ExcelUtils {

    /**
     *
     * 注此类现在只能对单表作用，以后会完善此功能 导入方法 通过反射自动注入
     * @param entityClass List中对象的类型（Excel中的每一行都要转化为该类型的对象）
     * @param excel
     * @param uniqueFields
     * @return
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> List<T> excelImport(Class<T> entityClass, Workbook excel) throws ExcelException, ParseException, IllegalAccessException, InstantiationException {
        List<T> resultList = new ArrayList<T>();
        Sheet sheet = excel.getSheetAt(0);
        Field field[] = entityClass.getDeclaredFields();

        //获取工作表的有效行数
        int realRows=0;
        for(int i=0;i<sheet.getPhysicalNumberOfRows();i++){
            Row row = sheet.getRow(i);
            if(row == null){
                continue;
            }
            int columnSize = row.getPhysicalNumberOfCells();
            int nullCols=0;
            for(int j = 0;j < columnSize;j++){
                org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
                if(cell == null){
                    nullCols++;
                }
            }

            if(nullCols == columnSize){
                break;
            }else{
                realRows++;
            }
        }
        //如果Excel中没有数据则提示错误
        if(realRows<=1){
            throw new ExcelException("Excel文件中没有任何数据");
        }

        int m = 0;
        while(sheet.getRow(m) == null){
            m++;
        }
        Row firstRow=sheet.getRow(m);
        int columnSize = firstRow.getPhysicalNumberOfCells();

        String[] excelFieldNames=new String[columnSize];

        //获取Excel中的列名
        for(int i=0;i<columnSize;i++){
            firstRow.getCell(i).setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            excelFieldNames[i]=firstRow.getCell(i).getStringCellValue().trim();
        }

        //判断需要的字段在Excel中是否都存在 key:excel中的列名,value:类中的列名
        Map<String,String> fieldMap = new HashMap<String,String>();
        boolean isExist=true;
        for(int j = 0; j < field.length; j++){
            field[j].setAccessible(true);
            Annotation annotation = field[j]
                    .getAnnotation(XmlElement.class);
            if(annotation != null){
                XmlElement xmlElement = (XmlElement) annotation;

                if(!xmlElement.name().equals("##default")){
                    fieldMap.put(xmlElement.name(),field[j].getName());
                }else{
                    fieldMap.put(field[j].getName(),field[j].getName());
                }
            }

        }

        List<String> excelFieldList= Arrays.asList(excelFieldNames);
        for(String cnName : fieldMap.keySet()){
            if(!excelFieldList.contains(cnName)){
                isExist=false;
                break;
            }
        }

        //如果有列名不存在，则抛出异常，提示错误
        if(!isExist){
            throw new ExcelException("Excel中缺少必要的字段或字段名称有误");
        }

        //将列名和列号放入Map中,这样通过列名就可以拿到列号
        Map<String, Integer> colMap=new HashMap<String, Integer>();
        for(int i=0;i<excelFieldNames.length;i++){
            colMap.put(excelFieldNames[i], firstRow.getCell(i).getColumnIndex());
        }

        //判断是否有重复行
        //1.获取uniqueFields指定的列
        /*org.apache.poi.ss.usermodel.Cell [][] uniqueCells=new org.apache.poi.ss.usermodel.Cell[uniqueFields.length][];
        for(int i=0;i<uniqueFields.length;i++){
            int col=colMap.get(uniqueFields[i]);
            org.apache.poi.ss.usermodel.Cell[] columnCell = new org.apache.poi.ss.usermodel.Cell[columnSize];
            for(int j = 0 ; j < sheet.getPhysicalNumberOfRows() ; j ++){
                Row row = sheet.getRow(j);
                columnCell[j] = row.getCell(col);
            }
            uniqueCells[i] = columnCell;
        }*/

        //2.从指定列中寻找重复行
//        for(int i=1;i<realRows;i++){
//            int nullCols=0;
//            for(int j=0;j<uniqueFields.length;j++){
//                uniqueCells[j][i].setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
//                String currentContent = uniqueCells[j][i].getStringCellValue();
//                Cell sameCell=sheet.findCell(currentContent,
//                        uniqueCells[j][i].getColumn(),
//                        uniqueCells[j][i].getRow()+1,
//                        uniqueCells[j][i].getColumn(),
//                        uniqueCells[j][realRows-1].getRow(),
//                        true);
//                if(sameCell!=null){
//                    nullCols++;
//                }
//            }
//
//            if(nullCols == uniqueFields.length){
//                throw new ExcelException("Excel中有重复行，请检查");
//            }
//        }


        //将sheet转换为list
        for(int i=1;i<realRows;i++){
            //新建要转换的对象
            T entity=entityClass.newInstance();
            Row row = sheet.getRow(i);
            //给对象中的字段赋值
            for(Map.Entry<String, String> entry : fieldMap.entrySet()){
                //获取Excel中的列名(Excel 中文)
                String excelPropertiesName = entry.getKey();
                //获取类中的字段名(Class 英文)
                String classPropertiesName = entry.getValue();

                //根据中文字段名获取列号
                int col=colMap.get(excelPropertiesName);

                //获取当前单元格中的内容
                org.apache.poi.ss.usermodel.Cell cell = row.getCell(col);
//                    String content=sheet.getCell(col, i).getContents().toString().trim();

//                    System.out.println("***************ExcelUtils content:"+content);
                //给对象赋值
                setFieldValueByName(classPropertiesName, cell, entity);
            }
            resultList.add(entity);
        }
        return resultList;
    }



    /**
     * @MethodName  : setFieldValueByName
     * @Description : 根据字段名给对象的字段赋值
     * @param fieldName  字段名
     * @param cell    单元格
     * @param o 对象
     */
    private static void setFieldValueByName(String fieldName,Cell cell,Object o) throws IllegalAccessException, ParseException, ExcelException{

        String cellValue = null;
        if(cell == null){
            cellValue = "";
        }else{
            int cellType = cell.getCellType();
            switch(cellType) {
                case Cell.CELL_TYPE_STRING: //文本
                    cellValue = cell.getStringCellValue().trim();
                    break;
                case Cell.CELL_TYPE_NUMERIC: //数字、日期
                    if(DateUtil.isCellDateFormatted(cell)) {
                        cellValue = DateUtils.format(cell.getDateCellValue(),"yyyy-MM-dd HH:mi:ss"); //日期型
                    }
                    else {
                        DecimalFormat df = new DecimalFormat("#");
                        cellValue = df.format(cell.getNumericCellValue()); //数字
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN: //布尔型
                    cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                    break;
                case Cell.CELL_TYPE_BLANK: //空白
                    cellValue = cell.getStringCellValue().trim();
                    break;
                case Cell.CELL_TYPE_ERROR: //错误
                    throw new ExcelException("单元格数据类型错误!");
                case Cell.CELL_TYPE_FORMULA: //公式
                    throw new ExcelException("单元格数据类型错误!");
            }
        }


        Field field=getFieldByName(fieldName, o.getClass());



        if(field!=null){
            field.setAccessible(true);
            //获取字段类型
            Class<?> fieldType = field.getType();

            //根据字段类型给字段赋值
            if (String.class == fieldType) {
                System.out.println("*****************value:"+String.valueOf(cellValue));
                field.set(o, String.valueOf(cellValue));
            } else if ((Integer.TYPE == fieldType)
                    || (Integer.class == fieldType)) {
                field.set(o, Integer.parseInt(cellValue.toString()));
            } else if ((Long.TYPE == fieldType)
                    || (Long.class == fieldType)) {
                field.set(o, Long.valueOf(cellValue.toString()));
            } else if ((Float.TYPE == fieldType)
                    || (Float.class == fieldType)) {
                field.set(o, Float.valueOf(cellValue.toString()));
            } else if ((Short.TYPE == fieldType)
                    || (Short.class == fieldType)) {
                field.set(o, Short.valueOf(cellValue.toString()));
            } else if ((Double.TYPE == fieldType)
                    || (Double.class == fieldType)) {
                field.set(o, Double.valueOf(cellValue.toString()));
            } else if (Character.TYPE == fieldType) {
                if ((cellValue!= null) && (cellValue.toString().length() > 0)) {
                    field.set(o, Character
                            .valueOf(cellValue.toString().charAt(0)));
                }
            }else if(Date.class==fieldType){
                System.out.println("*****************************日期为:" + cellValue.toString());
                Date date = new SimpleDateFormat("yy-MM-dd").parse(cellValue.toString());
                String d = new SimpleDateFormat("yyyy-MM-dd").format(date);
                System.out.println("****************************格式化后日期为:"+d);
                field.set(o, date);
            }else{
                field.set(o, cellValue);
            }
        }else{
            throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名 "+fieldName);
        }
    }

    /**
     * @MethodName  : getFieldByName
     * @Description : 根据字段名获取字段
     * @param fieldName 字段名
     * @param clazz 包含该字段的类
     * @return 字段
     */
    private static Field getFieldByName(String fieldName, Class<?>  clazz){
        //拿到本类的所有字段
        Field[] selfFields=clazz.getDeclaredFields();

        //如果本类中存在该字段，则返回
        for(Field field : selfFields){
            if(field.getName().equals(fieldName)){
                return field;
            }
        }

        //否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz=clazz.getSuperclass();
        if(superClazz!=null  &&  superClazz !=Object.class){
            return getFieldByName(fieldName, superClazz);
        }

        //如果本类和父类都没有，则返回空
        return null;
    }

    //说明：parseTimestamp是一个转化时间的方法，因为考虑到日期的格式可能为2010/12/14，代码如下：
    //转换时间戳
    private static Timestamp parseTimestamp(String date) {
        String d = date;
        if (date.indexOf("/") > 0) {
            d = date.replace("/", "-");
        }
        String temp = d + " 00:00:00";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = df.parse(temp);
            return new Timestamp(time.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 1.创建 workbook
     * @return
     */
    public HSSFWorkbook getHSSFWorkbook(){
        return new HSSFWorkbook();
    }

    /**
     * 2.创建 sheet
     * @param hssfWorkbook
     * @param sheetName sheet 名称
     * @return
     */
    public HSSFSheet getHSSFSheet(HSSFWorkbook hssfWorkbook, String sheetName){
        return hssfWorkbook.createSheet(sheetName);
    }

    /**
     * 3.写入表头信息
     * @param hssfWorkbook
     * @param hssfSheet
     * @param headInfoList List<Map<String, Object>>
     *              key: title         列标题
     *                   columnWidth   列宽
     *                   dataKey       列对应的 dataList item key
     */
    public void writeHeader(HSSFWorkbook hssfWorkbook,HSSFSheet hssfSheet ,List<Map<String, Object>> headInfoList){
        HSSFCellStyle cs = hssfWorkbook.createCellStyle();
        HSSFFont font = hssfWorkbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setBoldweight(font.BOLDWEIGHT_BOLD);
        cs.setFont(font);
        cs.setAlignment(cs.ALIGN_CENTER);

        HSSFRow r = hssfSheet.createRow(0);
        r.setHeight((short) 380);
        HSSFCell c = null;
        Map<String, Object> headInfo = null;
        //处理excel表头
        for(int i=0, len = headInfoList.size(); i < len; i++){
            headInfo = headInfoList.get(i);
            c = r.createCell(i);
            c.setCellValue(headInfo.get("title").toString());
            c.setCellStyle(cs);
            if(headInfo.containsKey("columnWidth")){
                hssfSheet.setColumnWidth(i, (short)(((Integer)headInfo.get("columnWidth") * 8) / ((double) 1 / 20)));
            }
        }
    }

    /**
     * 4.写入内容部分
     * @param hssfWorkbook
     * @param hssfSheet
     * @param startIndex 从1开始，多次调用需要加上前一次的dataList.size()
     * @param headInfoList List<Map<String, Object>>
     *              key: title         列标题
     *                   columnWidth   列宽
     *                   dataKey       列对应的 dataList item key
     * @param dataList
     */
    public void writeContent(HSSFWorkbook hssfWorkbook,HSSFSheet hssfSheet ,int startIndex,
                             List<Map<String, Object>> headInfoList, List<Map<String, Object>> dataList){
        Map<String, Object> headInfo = null;
        HSSFRow r = null;
        HSSFCell c = null;
        //处理数据
        Map<String, Object> dataItem = null;
        Object v = null;
        for (int i=0, rownum = startIndex, len = (startIndex + dataList.size()); rownum < len; i++,rownum++){
            r = hssfSheet.createRow(rownum);
            r.setHeightInPoints(16);
            dataItem = dataList.get(i);
            for(int j=0, jlen = headInfoList.size(); j < jlen; j++){
                headInfo = headInfoList.get(j);
                c = r.createCell(j);
                v = dataItem.get(headInfo.get("dataKey").toString());

                if (v instanceof String) {
                    c.setCellValue((String)v);
                }else if (v instanceof Boolean) {
                    c.setCellValue((Boolean)v);
                }else if (v instanceof Calendar) {
                    c.setCellValue((Calendar)v);
                }else if (v instanceof Double) {
                    c.setCellValue((Double)v);
                }else if (v instanceof Integer
                        || v instanceof Long
                        || v instanceof Short
                        || v instanceof Float) {
                    c.setCellValue(Double.parseDouble(v.toString()));
                }else if (v instanceof HSSFRichTextString) {
                    c.setCellValue((HSSFRichTextString)v);
                }else {
                    c.setCellValue(v.toString());
                }
            }
        }
    }

    public void write2FilePath(HSSFWorkbook hssfWorkbook, OutputStream outputStream) throws IOException {
        try{
            hssfWorkbook.write(outputStream);
        }finally{
            if(outputStream != null){
                outputStream.close();
            }
        }
    }

}
