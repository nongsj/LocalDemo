package com.layui.file;


import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.layui.entity.Attachment;
import com.layui.mapper.AttachmentMapper;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFileUpload {
	@Value("${fileUploadPath}")
	private String fileUploadPath;
	
	@Autowired
	private AttachmentMapper aa;
	
	@Test
	public void testFile() {
		//声明
		String yyyyMMdd = fileUploadPath + File.separator + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + File.separator;
		System.err.println(yyyyMMdd);
	}
	
	@Test
	public void exportExcel() {
		System.err.println("aa");
	}
}
