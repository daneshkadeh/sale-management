package com.hbsoft.ssm.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainProgram {
	public static Log s_logger = LogFactory.getLog(MainProgram.class);

	public static void main(String[] args) {
		// Not find solution to get class path from ssm-core.
		// String classpath = MainProgram.class.getClassLoader().get
		DOMConfigurator.configure("src/main/resources/log4j.xml");
		s_logger.info("Testing project Hibernate-Spring-log4j-Swing");
		ApplicationContext appContext = new ClassPathXmlApplicationContext("config/BeanLocations.xml");
	}
}
