package com.layui;

import com.layui.entity.YmlObj;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LayuiserviceApplicationTests {

	@Autowired
	private YmlObj ymlObj;

	@Test
	void contextLoads() {
		System.out.println(ymlObj.getItem());
	}



}
