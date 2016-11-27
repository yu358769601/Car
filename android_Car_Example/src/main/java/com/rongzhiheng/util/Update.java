package com.rongzhiheng.util;

import java.io.Serializable;

/**
 * 应用程序更新实体类
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class Update implements Serializable{
	
	public final static String UTF8 = "UTF-8";
	public final static String NODE_ROOT = "oschina";
	
	private String versionCode;
	private String versionName;
	private String downloadUrl;
	private String updateLog;
	
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getUpdateLog() {
		return updateLog;
	}
	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}
	

}
