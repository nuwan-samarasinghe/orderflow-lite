package com.interviewprep.orderflow_lite;

import org.springframework.boot.SpringApplication;

public class TestOrderflowLiteApplication {

	public static void main(String[] args) {
		SpringApplication.from(OrderflowLiteApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
