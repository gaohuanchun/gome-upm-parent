package com.gome.upm.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpClientUtil {
	
	private final static Log log = LogFactory.getLog(HttpClientUtil.class);
	private static final String timeout = "60000";
	private static final int MIN_ERROR_REQUEST_SPEND_TIME = 200;

	/**
	 * 发送多参数的post请求
	 * 
	 * @param url
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	public static String sendHttpRequestByParams(String url,
			NameValuePair[] data) throws IOException {
		return sendHttpRequestByParams(url, data, false, 30000);
	}

	/**
	 * 发送多参数的post请求
	 * 
	 * @param url
	 * @param data
	 * @return
	 * @throws IOException 
	 * @noEncode 中文参数是否encode过 true否 false 是
	 */
	public static String sendHttpRequestByParams(String url,
			NameValuePair[] data, boolean noEncode) throws IOException {
		return sendHttpRequestByParams(url, data, noEncode, 30000);
	}

	/**
	 * 发送多参数的post请求
	 * 
	 * @param url
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	public static String sendHttpRequestByParams(String url,
			NameValuePair[] data, boolean noEncode, int timeoutTime) throws IOException {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		log.info(url);
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Connection", "close");
		// 如果中文参数没有 urlEncode 则需要设置头信息
		if (noEncode) {
			postMethod.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
		}
		postMethod.addParameters(data);

		// 使用系统提供的默认的恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 定义超时
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(timeoutTime);
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(timeoutTime);
		// 定义一个输入流
		InputStream ins = null;
		// 定义文件流
		BufferedReader br = null;
		try {

			// 执行postMethod
			long timeBeforeExecute = System.currentTimeMillis();
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("method failure: " + postMethod.getStatusLine()
						+ ",url-----------" + url + "params:" + toJson(data));
			}

			long timeAfterExecute = System.currentTimeMillis();
			long executeSpendTime = timeAfterExecute - timeBeforeExecute;
			if (executeSpendTime > MIN_ERROR_REQUEST_SPEND_TIME) {
				log.warn("请求url：" + url + " 花费了太长时间：" + executeSpendTime
						+ "ms!");
			}
			// 使用getResponseBodyAsStream读取页面内容，这个方法对于目标地址中有大量数据需要传输是最佳的。
			ins = postMethod.getResponseBodyAsStream();
			String charset = postMethod.getResponseCharSet();
			if (charset.toUpperCase().equals("ISO-8859-1")) {
				charset = "utf-8";
			}
			// 按服务器编码字符集构建文件流，这里的CHARSET要根据实际情况设置
			br = new BufferedReader(new InputStreamReader(ins,
					postMethod.getResponseCharSet()));
			StringBuffer sbf = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sbf.append(line);
			}
			String result = new String(sbf.toString().getBytes(
					postMethod.getResponseCharSet()), charset);
			return result;
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题 \
			log.error("ulr------" + url + toJson(data));
			log.error("please check your http url address！", e);
			throw e;
		} catch (IOException e) {
			log.error("ulr------" + url + toJson(data));
			log.error("network exception is happening", e);
			throw e;
		} catch (Exception e) {
			log.error("ulr------" + url + toJson(data));
			log.error("Exception  is happening", e);
		} finally {
			// 关闭流，释放连接
			try {
				if (br != null) {
					br.close();
				}
				if (ins != null) {
					ins.close();
				}
				if (postMethod != null) {
					try {
						postMethod.releaseConnection();
						httpClient.getHttpConnectionManager()
								.closeIdleConnections(0);
					} catch (Exception e) {
						log.error("ulr------" + url + toJson(data));
						log.error("close http connetion failure", e);
					}
				}
			} catch (IOException e) {
				log.error("ulr------" + url + toJson(data));
				log.error("stream connection close failure", e);
			} catch (Exception e) {
				log.error("ulr------" + url + toJson(data));
				log.error("Exception", e);
			}
		}
		return null;
	}

	/**
	 * 将NameValuePair数组转换为json格式
	 * 
	 * @param data
	 *            NameValuePair 数组
	 * @return
	 */
	private static String toJson(NameValuePair[] data) {

		StringBuffer buffer = new StringBuffer("{");
		if (data != null) {
			for (NameValuePair nvp : data) {
				buffer.append(nvp.getName());
				buffer.append(":");
				buffer.append(nvp.getValue());
				buffer.append(",");
			}
		}
		buffer.append("}");

		return buffer.toString();
	}
	
	public static String sendHttpRequest(String url) {
        return sendHttpRequest(url, null);
    }
	
	public static String sendHttpRequest(String url, String param) {
        return sendHttpRequest(url, param, Integer.parseInt(timeout));
    }

	public static String sendHttpRequest(String url, String param, int timeoutTime) {
        //构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        log.info(url);
        PostMethod getMethod = new PostMethod(url);
        getMethod.setRequestHeader("Connection", "close");
        if (StringUtils.isNotEmpty(param)) {
            getMethod.addParameter("param", param);
        }

        /*if (StringUtils.isNotEmpty(param)) {
            getMethod.addParameter("key", "666a8EN3oIijHY+KjS+2mg==");
        }*/
        //使用系统提供的默认的恢复策略
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        //定义五秒钟的超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeoutTime);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeoutTime);
        // getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        //定义一个输入流
        InputStream ins = null;
        //定义文件流
        BufferedReader br = null;
        try {

            long timeBeforeExecute = System.currentTimeMillis();
            //执行getMethod
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                log.error("method failure: " + getMethod.getStatusLine() + ",url:-----------" + url + ",params:" + param);
                log.error("method failure: " + getMethod.getStatusLine() + "-----------" + url + ",params:" + param);
            }
            long timeAfterExecute = System.currentTimeMillis();
            long executeSpendTime = timeAfterExecute - timeBeforeExecute;
            if (executeSpendTime > MIN_ERROR_REQUEST_SPEND_TIME) {
                log.error("请求url： " + url + " 花费了太长时间：" + executeSpendTime + "ms");
            }
            //使用getResponseBodyAsStream读取页面内容，这个方法对于目标地址中有大量数据需要传输是最佳的。
            //   Thread.sleep(2000l);//测试需要，线程暂停10s
            ins = getMethod.getResponseBodyAsStream();
            String charset = getMethod.getResponseCharSet();
            if (charset.toUpperCase().equals("ISO-8859-1")) {
                charset = "utf-8";
            }

            //按服务器编码字符集构建文件流，这里的CHARSET要根据实际情况设置
            br = new BufferedReader(new InputStreamReader(ins, getMethod.getResponseCharSet()));
            StringBuffer sbf = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sbf.append(line);
            }
            String result = new String(sbf.toString().getBytes(getMethod.getResponseCharSet()), charset);
            return result;
        } catch (HttpException e) {
            //发生致命的异常，可能是协议不对或者返回的内容有问题  \
            log.error("ulr------" + url + param);
            log.error("please check your http url address！", e);
        } catch (IOException e) {
            log.error("ulr------" + url + param);
            log.error("network exception is happening", e);
        } catch (Exception e) {
            log.error("ulr------" + url + param);
            log.error("Exception  is happening", e);
        } finally {
            //关闭流，释放连接
            try {
                if (br != null) {
                    br.close();
                }
                if (ins != null) {
                    ins.close();
                }
                if (getMethod != null) {
                    try {
                        getMethod.releaseConnection();
                        httpClient.getHttpConnectionManager().closeIdleConnections(0);
                    } catch (Exception e) {
                        log.error("ulr------" + url + param);
                        log.error("close http connetion failure", e);
                    }
                }
            } catch (IOException e) {
                log.error("ulr------" + url + param);
                log.error("stream connection close failure", e);
            } catch (Exception e) {
                log.error("ulr------" + url + param);
                log.error("Exception", e);
            }
        }
        return null;
    }

}
