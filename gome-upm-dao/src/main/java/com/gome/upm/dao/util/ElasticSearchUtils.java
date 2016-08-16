package com.gome.upm.dao.util;

import static gome.gtrace.internal.Util.checkNotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.common.net.HostAndPort;

/**
 * es的连接类
 * 
 * @author zhouyaliang
 *
 */
@Repository("esUtils")
public class ElasticSearchUtils {
	private Logger logger = LoggerFactory.getLogger(ElasticSearchUtils.class);

	@Value("${elasticsearch.cluster}")
	private String clusterName;
	@Value("${elasticsearch.hosts}")
	private String hosts;
	@Value("${elasticsearch.index}")
	private String index;

	public Client getEsClient() {
		// es 2.0版本写法
		 Settings settings = Settings.builder().put("cluster.name", clusterName).put("lazyClient.transport.sniff", true).build();
		
		// es 1.6版本有效写法
		// Settings settings = ImmutableSettings

		TransportClient client = TransportClient.builder().settings(settings).build();
		List<String> hostsList = clusterNodesList(hosts);
		for (String host : hostsList) {
			HostAndPort hostAndPort = HostAndPort.fromString(host);
			try {
				client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostAndPort.getHostText()), hostAndPort.getPort()));
			} catch (UnknownHostException e) {
				logger.error("host:" + host + " UnknownHost!", e.getMessage());
				continue;
			}
		}
		return client;
	}

	public String getIndex() {
		return index;
	}

	@SuppressWarnings("unchecked")
	private List<String> clusterNodesList(String str) {
		str = checkNotNull(str, str + " is Null or blank");

		if (str == null || str.length() == 0)
			return Collections.EMPTY_LIST;

		return Arrays.asList(str.split(","));
	}
}
