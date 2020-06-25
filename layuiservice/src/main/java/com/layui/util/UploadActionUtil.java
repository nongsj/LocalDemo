package com.layui.util;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @author yux
 * 文件上传控制器
 */
public class UploadActionUtil {

    public static List<Map<String,String>> uploadFile(HttpServletRequest request,String saveFilePath) throws Exception {
        return savePath(request,saveFilePath);
    }

    /**
     * 得年月日的文件夹名称
     *
     * @return
     */
    public static String getCurrentFilderName()  throws Exception{
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) + "" + (now.get(Calendar.MONTH) + 1) + "" + now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 创建文件夹
     *
     * @param filderName
     */
    public static void createFilder(String filderName) throws Exception {
        File file = new File(filderName);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String extFile(String fileName)  throws Exception{
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 当前日期当文件夹名
     *
     * @return
     */
    public static String folderName() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = sdf.format(new Date());
        return str;
    }

    public static String filesize(MultipartFile file){
    	long size  = file.getSize();
    	//如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
        
    }
    

    private static List<Map<String,String>> savePath(HttpServletRequest request,String saveFilePath)throws Exception {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
            Iterator<String> iterator = multiRequest.getFileNames();
            while (iterator.hasNext()) {
            	Map<String,String> map = new HashMap<String,String>();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iterator.next());
                if (file != null) {
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为空 说明该文件存在 否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        String fileTyps = myFileName.substring(myFileName.lastIndexOf("."));
                        // String tempName="demo"+fileTyps;
                        String tempName = UUID.randomUUID().toString() + fileTyps;
                        // 创建文件夹
                        String folderPath = saveFilePath + File.separator + folderName();
                        File fileFolder = new File(folderPath);
                        if (!fileFolder.exists() && !fileFolder.isDirectory()) {
                            fileFolder.mkdirs();
                        }
                        File uploadFile = new File(folderPath + File.separator + tempName);
                        file.transferTo(uploadFile);
                        String filepath = folderName() + File.separator + tempName;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String filesize = filesize(file);
                        map.put("filesize", filesize);
                        map.put("filename", myFileName);
                        map.put("filepath",filepath);
                        map.put("uploadtime",sdf.format(new Date()));
                    }
                }
                list.add(map);
            }
        }
        return list;
    }
}
