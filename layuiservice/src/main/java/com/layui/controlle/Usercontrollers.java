package com.layui.controlle;

import com.layui.service.UserService;
import com.layui.util.ResultStatus;
import com.layui.util.ResultUtil;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import com.layui.entity.User;
import com.layui.service.IUserService;
import com.layui.service.SuperService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class Usercontrollers extends BsaeController<User, String> {

@Autowired
private UserService userService;

@Autowired
private IUserService iUserService;

@Override
protected SuperService getService() {
return userService;
}


	@RequestMapping("/exprotUser")
	public void ExprotUserExcel(HttpServletResponse response) throws IOException {
		List<Map<String,Object>> list = iUserService.getExcelData();
		LocalDateTime now = LocalDateTime.now();
        String formatTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formatTime2 = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Map<String , Object> model=new HashMap<String , Object>();
        model.put("userList", list);
        model.put("nowdate",formatTime);
        model.put("nowdate2",formatTime2);
        //获取模板位置
        InputStream inputStream = Usercontrollers.class.getClassLoader().getResourceAsStream("templates/userExcelTble.xlsx");
        String fileName = "test";
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Content-disposition","attachment;filename=\""+new String((fileName+".xlsx").getBytes("GBK"),"ISO8859-1")+"\"");
        OutputStream os = response.getOutputStream();
        Context context = new Context();
		if (model != null) {
			for (String key : model.keySet()) {
				context.putVar(key, model.get(key));
			}
		}
		JxlsHelper jxlsHelper = JxlsHelper.getInstance();
		Transformer transformer = jxlsHelper.createTransformer(inputStream, os);
		jxlsHelper.processTemplate(context, transformer);
        os.close();
	}
	
	@RequestMapping("/importUser")
	public Map<String, Object> importUserExcel(HttpServletRequest request,@RequestParam(value = "file")  MultipartFile file) throws IOException {
		ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
		//修改key值
		Map<String,String> map = new HashMap<>();
		//key = excel表中的标题     value = 实体类中的字段
		map.put("id", "id");
		map.put("姓名", "userName");
		reader.setHeaderAlias(map);
		//3 = 标题所在的行   4 = 内容开始的行	User = 用户实体类
		List<User> userAll = reader.read(3,4,User.class);
		for (User user : userAll) {
			add(user);
		}
		return ResultUtil.combinationResult(ResultStatus.SUCCESS, "ok");
	}
}
