package com.layui.controlle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.layui.util.DownActionUtil;
import com.layui.util.ResultStatus;
import com.layui.util.ResultUtil;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.lang.UUID;

@RestController
@RequestMapping("/file")
public class UploadAttachment{
	
	//文件保存的路径
	@Value("${file.upload.path}")
	private String fileUploadPath;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Map<String, Object> uploadFile(@RequestParam(value = "file")  MultipartFile file) throws IORuntimeException, IOException{
		//自定义指定盘符下的 xx 文件夹
		String yyyyMMdd = fileUploadPath + File.separator + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + File.separator;
		//检查文件或目录是否存在
		if (!FileUtil.exist(yyyyMMdd)) {
			//如果不存在则创建文件夹
            FileUtil.mkdir(yyyyMMdd);
        }
		//储存到盘符时的名字,加入UUID防止文件相同无法上传
		String fileName = UUID.randomUUID().toString() + "@" + file.getOriginalFilename();
		//获取文件的后缀
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		//计算文件的大小
		String fileSize = filesize(file.getSize());
		//创建文件
		File file1 = FileUtil.writeBytes(file.getBytes(), yyyyMMdd + fileName);
		//响应需要的属性
		List< Map<String, String>> pathList = new ArrayList<>();
		if (file1.length()>0){
            Map<String, String> map = new HashMap<>();
            map.put("filename", file.getOriginalFilename());
            map.put("suffix", suffix);
            map.put("filepath", yyyyMMdd.replaceAll("\\\\","/")/* + fileName*/);
            map.put("filesize",fileSize);
            pathList.add(map);
        }
		return ResultUtil.combinationResult(ResultStatus.SUCCESS, pathList);
	}
	
	
	public static String filesize(long fileSize){
    	//如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (fileSize < 1024) {
            return String.valueOf(fileSize) + "B";
        } else {
        	fileSize = fileSize / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (fileSize < 1024) {
            return String.valueOf(fileSize) + "KB";
        } else {
        	fileSize = fileSize / 1024;
        }
        if (fileSize < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
        	fileSize = fileSize * 100;
            return String.valueOf((fileSize / 100)) + "." + String.valueOf((fileSize % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
        	fileSize = fileSize * 100 / 1024;
            return String.valueOf((fileSize / 100)) + "." + String.valueOf((fileSize % 100)) + "GB";
        }
    }
	
	
	@RequestMapping("/downloadFile")
	public String downloadFile(HttpServletResponse response,HttpServletRequest request,String path,String name) throws UnsupportedEncodingException {
		String downloadFilePath = path;//被下载的文件在服务器中的路径,
        //String fileName = "abc哈哈.xls";//被下载文件的名称
        File file = new File(downloadFilePath);
        if(file.exists()) {
        	String fileName = new String(name.getBytes(),"ISO-8859-1");
        	response.setContentType("application/force-download");// 设置强制下载不打开 
        	response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        	byte[] buffer = new byte[1024];
        	FileInputStream fis = null;
        	BufferedInputStream bis = null;
        	try {
        		fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while(i != -1) {
                	outputStream.write(buffer, 0, i);
                	i = bis.read(buffer);
                }
                return "下载成功";
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
        }
		return "下载失败";
	}
}
