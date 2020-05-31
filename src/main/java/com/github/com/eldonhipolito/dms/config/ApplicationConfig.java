package com.github.com.eldonhipolito.dms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

	@Autowired
	private Environment env;

	public String getFileStoreLocation() {
		return env.getProperty("application.files.storage");
	}
}
