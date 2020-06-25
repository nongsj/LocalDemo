package com.layui.user;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hpsf.Array;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.layui.entity.TestExcel;
import com.layui.entity.User;
import com.layui.mapper.UserDao;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Users {
	@Autowired
	UserDao userDao;
	
	@Test
	public void list() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setUserName("123");
		userDao.insertSelective(user);
		
	}
	
	@Test
	public void bba() {
		
		User user = new User();
		List<User> aa =userDao.findList(user);
		for (User user2 : aa) {
			System.err.println(user2);
		}
	}
	
	@Test
	public void bb() {
		ExcelReader reader = ExcelUtil.getReader("D:\\aaa.xlsx");
		List<List<Object>> readAll = reader.read();
		for (List<Object> list : readAll) {
			System.err.println(list);
		}
	}
	
	@Test
	public void cc() {
		ExcelReader reader = ExcelUtil.getReader("d:/test.xlsx");
		//修改key值
		Map<String,String> map = new HashMap<>();
		map.put("id", "id");
		map.put("姓名", "userName");
		reader.setHeaderAlias(map);
		List<User> readAll = reader.read(3,4,User.class);
		for (User testExcel : readAll) {
			System.err.println(testExcel);
		}
	}
	
	@Test
	public void ExprotExcel() throws IOException {
		List<Map<String,Object>> list = userDao.excelData();
		//写入到指定盘符
        
        LocalDateTime now = LocalDateTime.now();
        String formatTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formatTime2 = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Map<String , Object> model=new HashMap<String , Object>();
        model.put("userList", list);
        model.put("nowdate",formatTime);
        model.put("nowdate2",formatTime2);
        //获取模板位置
        InputStream inputStream = Users.class.getClassLoader().getResourceAsStream("templates/userExcelTble.xlsx");
        Context context = new Context();
		if (model != null) {
			for (String key : model.keySet()) {
				context.putVar(key, model.get(key));
			}
		}
		OutputStream os = new FileOutputStream("D:/t22.xlsx");
		JxlsHelper jxlsHelper = JxlsHelper.getInstance();
		Transformer transformer = jxlsHelper.createTransformer(inputStream, os);
		jxlsHelper.processTemplate(context, transformer);
        os.close();
	}
}
