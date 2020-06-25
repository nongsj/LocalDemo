package com.layui;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.layui.mapper")
public class LayuiserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LayuiserviceApplication.class, args);
	}

}
