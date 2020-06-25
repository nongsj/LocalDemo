package com.layui.util;

import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

public class DownActionUtil {

    public static void downFile(HttpServletRequest request, HttpServletResponse response,String serverPath, String path, String filename) throws Exception{
        fileHandle(request,response,serverPath,path,filename);
    }

    /**
     *
     * @param request
     * @param response
     * @param serverPath 服务器文件的路径
     * @param path 文件的相对路径
     * @param filename 文件名
     * @throws Exception
     */
    private static void fileHandle (HttpServletRequest request, HttpServletResponse response,String serverPath, String path, String filename) throws Exception{
        File file = new File(serverPath+"\\"+path+"\\"+filename);
        if(file.exists()){
            String minType = request.getServletContext().getMimeType(filename);
            response.setContentType(minType);

            String agent = request.getHeader("user-agent");
            if(agent.contains("MSIE")){
                //IE浏览器
                filename = URLEncoder.encode(filename, "utf-8");
            }else if (agent.contains("Firefox")) {
                // 火狐浏览器
                BASE64Encoder base64Encoder = new BASE64Encoder();
                filename = "=?utf-8?B?"
                        + base64Encoder.encode(filename.getBytes("utf-8"))
                        + "?=";
            } else {
                // 其它浏览器
                filename = URLEncoder.encode(filename, "utf-8");
            }

            // 下载注意事项2--永远是下载 设置以附件的形式进行打开下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode(filename, "UTF-8"));

            FileInputStream fis = new FileInputStream(file); // 读取要下载文件的内容
            OutputStream os = response.getOutputStream();// 将要下载的文件内容通过输出流写回到浏览器
            int len = -1;
            byte[] b = new byte[1024 * 100];

            while ((len = fis.read(b)) != -1) {
                os.write(b, 0, len);
                os.flush();
            }
            os.close();
            fis.close();
        }else{
            throw new RuntimeException("下载资源不存在");
        }
    }
}
