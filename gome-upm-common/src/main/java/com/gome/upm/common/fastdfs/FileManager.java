package com.gome.upm.common.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.gome.upm.common.fastdfs.common.MyException;
import com.gome.upm.common.fastdfs.common.NameValuePair;
import com.gome.upm.common.fastdfs.csource.fastdfs.ClientGlobal;
import com.gome.upm.common.fastdfs.csource.fastdfs.StorageClient;
import com.gome.upm.common.fastdfs.csource.fastdfs.StorageServer;
import com.gome.upm.common.fastdfs.csource.fastdfs.TrackerClient;
import com.gome.upm.common.fastdfs.csource.fastdfs.TrackerServer;

/**
 * fastdfs 工具类
 * 
 * @author liyujian
 *
 */
public class FileManager implements FileManagerConfig {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(FileManager.class);

	private static TrackerClient trackerClient;
	private static TrackerServer trackerServer;
	private static StorageServer storageServer;
	private static StorageClient storageClient;

	static { // Initialize Fast DFS Client configurations

		try {
			ClassPathResource cpr = new ClassPathResource("fdfs_client.properties");
			ClientGlobal.init(cpr.getClassLoader().getResource("fdfs_client.properties").getPath());
			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getConnection();
			storageClient = new StorageClient(trackerServer, storageServer);
		} catch (Exception e) {
			logger.info(e.getStackTrace());
		}
	}
	
	/**
	 * 上传文件
	 * 
	 * @param file
	 * @return
	 */
	public static String upload(FastDFSFile file) {
		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("width", "120");
		meta_list[1] = new NameValuePair("heigth", "120");
		meta_list[2] = new NameValuePair("author", "gome");

		long startTime = System.currentTimeMillis();
		String[] uploadResults = null;
		try {
			uploadResults = storageClient.upload_file(file.getContent(),
					file.getExt(), meta_list);
		} catch (IOException e) {
			logger.error("IO Exception when uploadind the file: " + file.getName(),e);
		} catch (Exception e) {
			logger.error("Non IO Exception when uploadind the file: "+ file.getName(), e);
		}
		logger.info("upload_file time used: "+ (System.currentTimeMillis() - startTime) + " ms");

		if (uploadResults == null) {
			logger.error("upload file fail, error code: " + storageClient.getErrorCode());
		}

		String groupName = uploadResults[0];
		String remoteFileName = uploadResults[1];

		String fileAbsolutePath = PROTOCOL
				+ FASTDFS_HOST
				+ SEPARATOR + groupName + SEPARATOR + remoteFileName;

		logger.info("upload file successfully!  " + "group_name: "
				+ groupName + ", remoteFileName:" + " " + remoteFileName);

		return fileAbsolutePath;

	}

	/**
	 * 下载文件
	 * 
	 * @param groupName
	 * @param remoteFileName
	 * @return
	 */
	public static byte[] getFile(String groupName, String remoteFileName) {
		try {
			return storageClient.download_file(groupName, remoteFileName);
		} catch (IOException e) {
			logger.error("IO Exception: Get File from Fast DFS failed", e);
		} catch (Exception e) {
			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
		}
		return null;
	}

	/**
	 * 删除文件
	 * 
	 * @param groupName
	 * @param remoteFileName
	 * @throws Exception
	 */
	public static void deleteFile(String groupName, String remoteFileName)
			throws Exception {
		storageClient.delete_file(groupName, remoteFileName);
	}

	/*
	 * public static StorageServer[] getStoreStorages(String groupName) throws
	 * IOException { return trackerClient.getStoreStorages(trackerServer,
	 * groupName); }
	 * 
	 * public static ServerInfo[] getFetchStorages(String groupName, String
	 * remoteFileName) throws IOException { return
	 * trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
	 * }
	 */
}
