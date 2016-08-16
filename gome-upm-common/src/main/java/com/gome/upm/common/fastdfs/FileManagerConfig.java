package com.gome.upm.common.fastdfs;

import java.io.Serializable;

/**
 * @author liyujian
 *
 */
public interface FileManagerConfig extends Serializable {

	  public static final String FILE_DEFAULT_WIDTH 	= "120";
	  public static final String FILE_DEFAULT_HEIGHT 	= "120";
	  public static final String FILE_DEFAULT_AUTHOR 	= "gome";
	  
	  public static final String PROTOCOL = "http://";
	  public static final String SEPARATOR = "/";
	  public static final String FASTDFS_HOST="cloudphoto.gome.com.cn";
	  public static final String TRACKER_NGNIX_PORT 	= "8080";
	  
	  public static final String CLIENT_CONFIG_FILE   = "fdfs_client.conf";
	  
	  
	}