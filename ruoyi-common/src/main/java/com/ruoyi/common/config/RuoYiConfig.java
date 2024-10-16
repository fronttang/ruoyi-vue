package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 读取项目相关配置
 * 
 * @author ruoyi
 */
@Data
@Component
@ConfigurationProperties(prefix = "ruoyi")
public class RuoYiConfig {
	/** 项目名称 */
	private String name;

	/** 版本 */
	private String version;

	/** 版权年份 */
	private String copyrightYear;

	/** 上传路径 */
	private static String profile;

	/** 获取地址开关 */
	private static boolean addressEnabled;

	/** 验证码类型 */
	private static String captchaType;

	private static Echarts echarts = new Echarts();

	@Data
	public static class Echarts {

		/**
		 * echarts 渲染接口
		 */
		private String api;
	}

	public static Echarts getEcharts() {
		return echarts;
	}

	public void setEcharts(Echarts echarts) {
		RuoYiConfig.echarts = echarts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCopyrightYear() {
		return copyrightYear;
	}

	public void setCopyrightYear(String copyrightYear) {
		this.copyrightYear = copyrightYear;
	}

	public static String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		RuoYiConfig.profile = profile;
	}

	public static boolean isAddressEnabled() {
		return addressEnabled;
	}

	public void setAddressEnabled(boolean addressEnabled) {
		RuoYiConfig.addressEnabled = addressEnabled;
	}

	public static String getCaptchaType() {
		return captchaType;
	}

	public void setCaptchaType(String captchaType) {
		RuoYiConfig.captchaType = captchaType;
	}

	/**
	 * 获取导入上传路径
	 */
	public static String getImportPath() {
		return getProfile() + "/import";
	}

	/**
	 * 获取头像上传路径
	 */
	public static String getAvatarPath() {
		return getProfile() + "/avatar";
	}

	/**
	 * 获取下载路径
	 */
	public static String getDownloadPath() {
		return getProfile() + "/download/";
	}

	/**
	 * 获取上传路径
	 */
	public static String getUploadPath() {
		return getProfile() + "/upload";
	}
}
