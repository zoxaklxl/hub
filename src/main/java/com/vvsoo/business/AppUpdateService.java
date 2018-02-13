package com.vvsoo.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vvsoo.common.Config;
import com.vvsoo.common.ResponseDataManager;

import lombok.Cleanup;

@SpringBootApplication
@EnableConfigurationProperties(Config.class)
@Controller
public class AppUpdateService implements EmbeddedServletContainerCustomizer  {
	@Autowired
	private Config config;
	
	@RequestMapping(value="/hyb/update/{appVersion:.+}")
	@ResponseBody
	public String checkUpdate(@PathVariable("appVersion") String appVersion) {
		String newVersion = config.getVersion();
//		File file = ResourceUtils.getFile("classpath:update/version.txt");
		System.out.println("=======newVersion="+newVersion);
		System.out.println("=======appVersion="+appVersion);
		if (appVersion.equals(newVersion)) {
			return ResponseDataManager.getUpdateResponseJSON(-1, newVersion, "");
		}
		String url = config.getUrl();
		String responseData = ResponseDataManager.getUpdateResponseJSON(200, newVersion, url);
		return responseData;
	}
	@RequestMapping(value="/hyb/download/{appName:.+}",method=RequestMethod.GET)
	public void downloadAPP(@PathVariable String appName,HttpServletRequest request,HttpServletResponse response) {
		try {
			//File file = ResourceUtils.getFile("classpath:download/"+appName);
			File file = ResourceUtils.getFile(config.getLocalurl()+appName);
			@Cleanup InputStream myStream = new FileInputStream(file);
			IOUtils.copy(myStream, response.getOutputStream());  
		    response.flushBuffer();  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
       
	}
	@RequestMapping(value="/hyb/report/{uuid}")
	public void reportInfo(@PathVariable String uuid) {
		System.out.println("=======uuid="+uuid);
		
	}
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AppUpdateService.class, args);
	}
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8081);
	}
}
