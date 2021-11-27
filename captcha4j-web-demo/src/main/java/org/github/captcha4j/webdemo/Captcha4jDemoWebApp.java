package org.github.captcha4j.webdemo;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class Captcha4jDemoWebApp {

	public static void main(String[] args) {
		SpringApplication.run(Captcha4jDemoWebApp.class, args);
	}

	@Bean
	public AES256TextEncryptor getAES256TextEncryptor(@Value("${captcha.encryption.pwd}") String pwd){
		AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
		textEncryptor.setPassword(pwd);
		return textEncryptor;
	}
}
