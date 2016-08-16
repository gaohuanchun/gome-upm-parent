package com.gome.upm.controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
/**
 * 
 *
 * @author fangjinwei
 */
public abstract class AbsBaseController {
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat sdf_ymd = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat sdf_ymdh = new SimpleDateFormat("yyyy-MM-dd HH:");
	/**
	 * 通过PrintWriter将响应数据写入response，ajax可以接受到这个数据
	 * 
	 * @param response
	 * @param data
	 */
	public void renderData(HttpServletResponse response, String data) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.print(data);
		} catch (IOException ex) {
			// Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE,
			// null, ex);
		} finally {
			if (null != printWriter) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}

	/**
	 * 根据当前时间获取到5分钟后的时间
	 * 
	 * @param startTime
	 * @return
	 * @throws ParseException
	 */
	public static String getFiveAfterTime(String startTime) throws ParseException {
		if (startTime == null) {
			return null;
		}
		Date afterDate = new Date(formatter.parse(startTime).getTime() + 300000);
		return formatter.format(afterDate);
	}
	
	public static Date getYesterDay() throws ParseException{
		long tt=sdf_ymd.parse(sdf_ymd.format(new Date())).getTime()-1000*60*60*24;
		return new Date(tt);
	}
	public static Date getToday() throws ParseException{
		long tt=sdf_ymd.parse(sdf_ymd.format(new Date())).getTime();
		return new Date(tt);
	}
	/**
	 * 当前时间   半小时  00/30
	 * @return
	 * @throws ParseException
	 */
	public static Date getHalfHourForToday() throws ParseException{
		Date date=new Date();
		String minute=date.getMinutes()>29?"30:00":"00:00";
		long tt=formatter.parse(sdf_ymdh.format(date)+minute).getTime();
		return new Date(tt);
	}
}
