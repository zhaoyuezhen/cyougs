package com.cyougs.mail.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cyougs.mail.Form.MailServersForm;
import com.cyougs.mail.common.StreamManage;
import com.cyougs.mail.service.MailServersServices;

@Controller
public class MailServersController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MessageSource messageSource;
	@Autowired
	MailServersServices mailServersServices;

	@ModelAttribute("mailServersForm")
	public MailServersForm mailServersForm() {
		logger.info("--- MailServersController.mailServersForm() start ---");
		return new MailServersForm();
	}

	@RequestMapping("/mail_servers")
	public ModelAndView mailServers(ModelAndView modelView) {
		logger.info("--- MailServersController.mailServers() start ---");
		Map<String, String> lineMap = monitorMailServer();
		modelView.addObject("mailServerState", lineMap.get("STATE"));
		return modelView;
	}

	@RequestMapping(value = "/mail_servers", params = "action=start")
	public ModelAndView mailServersStart(ModelAndView modelView) {
		modelView.setViewName("/mail_servers");
		// 启动邮件服务器
		startMailServer();
		// 查看邮件服务器状态
		Map<String, String> lineMap = monitorMailServer();
		modelView.addObject("mailServerState", lineMap.get("STATE"));
		if("4".equals(lineMap.get("STATE").substring(0,1))) {
			modelView.addObject("validationMessage", "启动成功");
		}else if("1".equals(lineMap.get("STATE").substring(0,1))){
			modelView.addObject("validationMessage", "启动失败");	
		}else {
			modelView.addObject("validationMessage", "处理中...");
		}
		return modelView;
	}

	@RequestMapping(value = "/mail_servers", params = "action=stop")
	public ModelAndView mailServersStop(ModelAndView modelView) {
		modelView.setViewName("/mail_servers");
		// 停止邮件服务器
		stopMailServer();
		// 查看邮件服务器状态
		Map<String, String> lineMap = monitorMailServer();
		modelView.addObject("mailServerState", lineMap.get("STATE"));
		if("1".equals(lineMap.get("STATE").substring(0,1))) {
			modelView.addObject("validationMessage", "停止成功");
		}else if("4".equals(lineMap.get("STATE").substring(0,1))){
			modelView.addObject("validationMessage", "停止失败");	
		}else{
			modelView.addObject("validationMessage", "处理中...");
		}
		return modelView;
	}

	/**
	 * 邮件服务器状态监视
	 */
	private Map<String, String> monitorMailServer() {
		// dos 命令
		String cmd = "sc query \"James 2.3.2.1\"";
		return executeCmd(cmd);
	}
	/**
	 * 启动邮件服务器
	 */
	private void startMailServer() {
		String cmd = "sc start \"James 2.3.2.1\"";// dos 命令
		Runtime rt = Runtime.getRuntime(); // 运行时系统获取
		try {
			// Process proc = rt.exec(cmd);// 执行命令
			File filePath = new File("src\\main\\resources\\static\\bat\\runStart.bat");
			Process proc = rt.exec(filePath.toString());
			StreamManage errorStream = new StreamManage(proc.getErrorStream(), "Error");
			StreamManage outputStream  = new StreamManage(proc.getInputStream(), "Output");
			errorStream.start();
			outputStream.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 停止服务器状态
	 */
	private void stopMailServer() {
		String cmd = "sc stop \"James 2.3.2.1\"";// dos 命令
		Runtime rt = Runtime.getRuntime(); // 运行时系统获取
		try {
			//Process proc = rt.exec(cmd);// 执行命令
			File filePath = new File("src\\main\\resources\\static\\bat\\runStop.bat");
			Process proc = rt.exec(filePath.toString()); 
			StreamManage errorStream = new StreamManage(proc.getErrorStream(), "Error");
			StreamManage outputStream = new StreamManage(proc.getInputStream(), "Output");
			errorStream.start();
			outputStream.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** cmd命令执行
	 * @param cmd
	 * @return
	 */
	private Map<String, String> executeCmd(String cmd) {
		Runtime rt = Runtime.getRuntime(); // 运行时系统获取
		Map<String, String> lineMap = new HashMap<String, String>();//存放返回值
		try {
			Process proc = rt.exec(cmd);// 执行命令
			InputStream stderr = proc.getInputStream();//执行结果 得到进程的标准输出信息流
			InputStreamReader isr = new InputStreamReader(stderr);//将字节流转化成字符流
			BufferedReader br = new BufferedReader(isr);//将字符流以缓存的形式一行一行输出
			String line = null;
			while ((line = br.readLine()) != null) { 
				if (!StringUtils.isEmpty(line)) {
					String[] strLine = line.split(":");
					if(strLine.length>=2) {
						lineMap.put(strLine[0].trim(), strLine[1].trim());
					}
					
				}
			}
			proc.waitFor();
			br.close();
			isr.close();
			stderr.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return lineMap;
	}

}
