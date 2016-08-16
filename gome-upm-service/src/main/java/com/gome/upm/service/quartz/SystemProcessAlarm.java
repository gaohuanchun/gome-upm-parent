package com.gome.upm.service.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.gome.upm.common.web.httpClient.HttpClientUtils;
import com.gome.upm.domain.ServerInfo;
import com.gome.upm.service.SystemProcessService;

/**
 * 定时任务查询系统进程为异常的数据(已取消)
 * @author qiaowentao
 *
 */
public class SystemProcessAlarm {
	
	private final static Logger logger = LoggerFactory.getLogger(SystemProcessAlarm.class);

	@Resource
	private SystemProcessService systemProcessService;
	
	/**
	 * 1分钟后执行，频率2分钟
	 */
	@Scheduled(initialDelay=1000*60,fixedRate = 1000*120)
	public void work(){
		
		try{
			List<ServerInfo> serverInfoList = systemProcessService.getInfomationWithBjzt();
			if(serverInfoList.size() > 0){
				
				//报警
				String url="http://10.58.56.72:8081/alarmplatform/alarm";
				Map<String, String> paramMap=new HashMap<>();
				paramMap.put("type", "process");
				paramMap.put("mail", "qiaowentao@yolo24.com");
				paramMap.put("subject", "系统进程有异常");
				paramMap.put("content", serverInfoList.toString());
				
				String result=HttpClientUtils.post(url, paramMap);
				System.out.println("返回值：" + result);
			}
			
		}catch(Exception e){
			logger.error("error",e);
		}
	}
	
}
