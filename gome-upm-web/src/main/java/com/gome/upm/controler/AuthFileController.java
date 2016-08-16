package com.gome.upm.controler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gome.upm.service.AuthFileMaintainService;
import com.gome.upm.service.SystemConfigMaintainService;

/**
 * 下载配置文件控制器
 * 
 * @author zhouyaliang
 * 
 */
@Controller
@RequestMapping("/usr/applications/authfile")
public class AuthFileController {

	private Logger logger = LoggerFactory.getLogger(AuthFileController.class);

	@Resource
	private SystemConfigMaintainService systemConfigMaintainService;

	@Resource
	private AuthFileMaintainService authFileMaintainService;

	@RequestMapping("/download/{applicationCode}")
	public void exportApplicationAuthFile(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("applicationCode") String applicationCode, String authType) {
		if (applicationCode == "" || applicationCode == null) {
			return;
		}
		// 初始化变量
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		OutputStream os = null;
		FileInputStream inputStream = null;

		// 得到当前登录人
		String userName = (String) request.getSession().getAttribute("userName");
		String filepath = userName + "-" + applicationCode + ".zip";
		try {
			response.reset();
			response.setContentType("application/octet-stream");
			String fileName = URLDecoder.decode(filepath, "utf-8");
			java.net.URLEncoder.encode(fileName, "utf-8");
			response.addHeader("Content-Disposition", "attachment;" + "filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"");
			Properties properties = authFileMaintainService.queryAuthKeysToProperties(authType);
			String propertyValue;
			for (Map.Entry<Object, Object> value : properties.entrySet()) {
				propertyValue = String.valueOf(value.getValue());
				if (propertyValue.startsWith("#")) {
					logger.info("{}", propertyValue.substring(1));
					value.setValue(systemConfigMaintainService.querySystemConfigByKey(propertyValue.substring(1)).getConfValue());
					continue;
				}

				if (propertyValue.startsWith("$")) {
					if (request.getParameter((propertyValue.substring(1))) != null) {
						value.setValue(request.getParameter(propertyValue.substring(1)));
					}
				}
				
	            if (propertyValue.startsWith("%")) {
	            	logger.info("%withstart:{}", propertyValue.substring(1));
	            	value.setValue("");
	            	continue;
	            }
			}
			properties.setProperty("gtrace.user_id", "userId");
			properties.setProperty("gtrace.application_code", applicationCode);

			File file = new File(request.getServletContext().getRealPath("/") + File.separator + "download" + File.separator + fileName);
			file.delete();
			// 生成zipFile
			JarOutputStream stream = new JarOutputStream(new FileOutputStream(file));
			// JarEntry entry = new JarEntry("gtrace.auth");
			ZipEntry entry = new ZipEntry("gtrace.auth");
			stream.putNextEntry(entry);
			properties.store(stream, "");
			stream.flush();
			stream.close();
			inputStream = new FileInputStream(file);
			os = response.getOutputStream();
			byte[] bytes = new byte[1024];
			while ((inputStream.read(bytes)) != -1) {
				os.write(bytes);
			}
		} catch (Exception e) {
			logger.error("Failed to download the auth file.", e);
		} finally {
			try {
				if(os != null){
					os.flush();
					os.close();
					inputStream.close();
				}
				if (input != null) {
					input.close();
				}
				if (output != null) {
					output.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return;
	}
}
